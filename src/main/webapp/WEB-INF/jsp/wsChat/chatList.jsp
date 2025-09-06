<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="/static/js/websocket-manager.js"></script>
<script>
	$(document).ready(function() {
		// let socket = new SockJS('/ws-chat');
		// let stompClient = Stomp.over(socket);
		let chatListCardJson = ${chatListCardJson};

		<%--function connect() {--%>
		<%--	let socket = new SockJS('/ws-chat');--%>
		<%--	stompClient = Stomp.over(socket);--%>

		<%--	// Send userId as connection header--%>
		<%--	let connectHeaders = {--%>
		<%--		'userId': `${userId}`  // Make sure you have userId available in your JSP--%>
		<%--	};--%>

		<%--	stompClient.connect(connectHeaders, function(frame) {--%>
		<%--		console.log("Connected: " + frame);--%>

		<%--		chatListCardList.forEach(function (chatListCard) {--%>
		<%--			stompClient.subscribe('/topic/chat/' + chatListCard.cl.id, function(message) {--%>
		<%--				showMessage(JSON.parse(message.body));--%>
		<%--			});--%>
		<%--		});--%>
		<%--	});--%>
		<%--}--%>

		ChatWebSocketManager.init('${userId}').then(function(stompClient) {
			// subscribe to all chat rooms
			chatListCardJson.forEach(function (chatListCard, index) {
				let topicUrl = '/topic/chat/' + chatListCard.cl.id
				let subscriptionUrl = 'chatList-' + chatListCard.cl.id
				console.log(subscriptionUrl);
				ChatWebSocketManager.subscribe(
						topicUrl,
						function(message) {
							updateLatestMessage(chatListCard.cl.id, JSON.parse(message.body));
						},
						subscriptionUrl // unique subscription id
				);
			});
		});

		function updateLatestMessage(chatListId, messageData) {
			let chatListElement = $(`.chat-list[data-chat-list-id="${chatListId}"]`);
			if (chatListElement.length > 0) {
				$(".chat-list-box").prepend(chatListElement)
				let latestCmElement = chatListElement.find('.latest-cm');
				let messageText = '';

				if (messageData.type === 'image') {
					messageText = 'photo';
				} else if (messageData.type === 'endTradeRequest') {
					messageText = 'End-trade Request';
				} else if (messageData.type === 'message') {
					if (messageData.message.length > 28) {
						messageText = messageData.message.substring(0, 28) + '...';
					} else {
						messageText = messageData.message;
					}
				}

				latestCmElement.text(messageText);
			}
		}

		$(".chat-list").on("click", function() {
			ChatWebSocketManager.unsubscribe('chatBox-${chatListId}');
			let getChatListId = $(this).data("chat-list-id");
			location.href = "/chat/" + getChatListId;
		})
	});
</script>


<div class="d-flex">
	<div class="col-3 chat-list-box p-0 border-right">
		<c:forEach items="${chatListCardList}" var="chatListCard">
			<div class="chat-list d-flex p-3 pointer bg-hover" data-chat-list-id="${chatListCard.cl.id}">
				<div class="col-2 chat-list-img-box">
					<img src="${chatListCard.product.imagePath}" class="crop-img" width="100%">
				</div>
				<div class="col-9">
					<h5 class="font-weight-bold">${chatListCard.product.name}</h5>
					<h6 class="latest-cm">
						<c:if test="${chatListCard.latestCM.type == 'image'}">
							photo
						</c:if>
						<c:if test="${chatListCard.latestCM.type == 'endTradeRequest'}">
							End-trade Request
						</c:if>
						<c:if test="${fn:length(chatListCard.latestCM.message) > 28}">
							${fn:substring(chatListCard.latestCM.message, 0, 28)}...
						</c:if>

						<c:if test="${fn:length(chatListCard.latestCM.message) <= 28}">
							${chatListCard.latestCM.message}
						</c:if>
					</h6>
				</div>
			</div>
		</c:forEach>
	</div>

	<jsp:include page="chatBox.jsp" />
</div>

<!-- Modal -->
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm modal-dialog-centered">
		<div class="modal-content text-center">
			<div id="preview-box" class="w-100">
				<img src="" id="preview" class="crop-img" width="100%">
			</div>
			<div class="border-bottom py-3">
				<div id="chat-image-send-btn" class="pointer">전송</div>
			</div>
			<div class="py-3">
				<div data-dismiss="modal" class="pointer">취소</div>
			</div>
		</div>
	</div>
</div>