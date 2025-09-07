/**
 * Shared WebSocket Manager for Chat Application
 * Manages WebSocket connections and subscriptions across multiple pages
 */
window.ChatWebSocketManager = {
    stompClient: null,
    subscriptions: new Map(),
    connectionPromise: null, // Add this to track connection state

    init: function(userId) {
        // If already connected, return resolved promise
        if (this.stompClient !== null && this.stompClient.connected) {
            return Promise.resolve(this.stompClient);
        }

        // If connection is in progress, return existing promise
        if (this.connectionPromise) {
            return this.connectionPromise;
        }

        // Create new connection
        this.connectionPromise = new Promise((resolve, reject) => {
            let socket = new SockJS('/ws-chat');
            this.stompClient = Stomp.over(socket);

            let connectHeaders = {
                'userId': userId
            };

            this.stompClient.connect(connectHeaders, (frame) => {
                console.log("WebSocket connected: " + frame);
                // Small delay to ensure connection is fully established
                setTimeout(() => {
                    resolve(this.stompClient);
                }, 10);
            }, (error) => {
                console.error("WebSocket connection error: " + error);
                this.connectionPromise = null; // Reset promise on error
                reject(error);
            });
        });

        return this.connectionPromise;
    },

    subscribe: function(destination, callback, subscriptionId) {
        if (!this.stompClient || !this.stompClient.connected) {
            console.error("WebSocket not connected. Current state:",
                this.stompClient ? 'exists but not connected' : 'does not exist');
            return null;
        }

        // Avoid duplicate subscriptions
        if (this.subscriptions.has(subscriptionId || destination)) {
            console.log("Already subscribed to: " + destination);
            return this.subscriptions.get(subscriptionId || destination);
        }

        try {
            let subscription = this.stompClient.subscribe(destination, callback);
            this.subscriptions.set(subscriptionId || destination, subscription);
            console.log("Successfully subscribed to:", destination);
            return subscription;
        } catch (error) {
            console.error("Error subscribing to " + destination + ":", error);
            return null;
        }
    },

    unsubscribe: function(subscriptionId) {
        if (this.subscriptions.has(subscriptionId)) {
            try {
                this.subscriptions.get(subscriptionId).unsubscribe();
                this.subscriptions.delete(subscriptionId);
                console.log("Unsubscribed from:", subscriptionId);
            } catch (error) {
                console.error("Error unsubscribing from " + subscriptionId + ":", error);
            }
        }
    },

    send: function(destination, headers, body) {
        if (this.stompClient && this.stompClient.connected) {
            try {
                this.stompClient.send(destination, headers || {}, body);
            } catch (error) {
                console.error("Error sending message:", error);
            }
        } else {
            console.error("Cannot send message: WebSocket not connected");
        }
    },

    disconnect: function() {
        if (this.stompClient !== null) {
            this.subscriptions.clear();
            this.stompClient.disconnect();
            this.stompClient = null;
            this.connectionPromise = null;
        }
    },

    isConnected: function() {
        return this.stompClient !== null && this.stompClient.connected;
    }
};