<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<div class="d-flex">
	<div class="col-3 chat-list-box p-0 border-right">
	<c:forEach items="${chatListCardList}" var="chatListCard">
		<div class="chat-list d-flex p-3 pointer bg-hover" data-chat-list-id="${chatListCard.cl.id}">
			<div class="col-2 chat-list-img-box">
				<img src="${chatListCard.product.imagePath}" class="crop-img" width="100%">
			</div>
			<div class="col-9">
				<h5 class="font-weight-bold">${chatListCard.product.name}</h5>
				<h6>
				<c:if test="${chatListCard.latestCM.type == 'image'}">
					사진
				</c:if>
				<c:if test="${chatListCard.latestCM.type == 'endTradeRequest'}">
					거래 완료 신청
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

	<div id="chat-box" class="col-9 px-4 pb-4">
	<c:if test="${not empty chatListCardList}">
		<div class="d-flex justify-content-center text-center py-3 border-bottom">
			<div class="col-2">
				<div class="chat-product-img-box pointer">
					<img src="${chatCard.product.imagePath}" class="crop-img" width="100%">
				</div>
				<h5 class="font-weight-bold mt-3 mb-2 pointer">
					<a href="/product/${chatCard.product.id}">
						${chatCard.product.name}
					</a>
				</h5>
		
				<h6 class="mb-2">
					<c:if test="${userId == chatCard.product.ownerId}">
					<a href="/user/${chatCard.buyer.loginId}">${chatCard.buyer.nickname}</a>
					</c:if>
					
					<c:if test="${userId != chatCard.product.ownerId}">
					<a href="/user/${chatCard.owner.loginId}">${chatCard.owner.nickname}</a>
					</c:if>
				</h6>
				
				<c:if test="${userId == chatCard.product.ownerId && chatCard.product.completed == false}">
				<button type="button" id="end-trade-btn" class="btn btn-primary">거래완료</button>
				</c:if>
			</div>
		</div>
		
		<div id="chat-area-div">
			<jsp:include page="chatBox.jsp" />
		</div>
		
		<div id="chat-input-box" class="input-group input-group-lg pt-3">
			<div id="chat-input-prepend" class="input-group-prepend border border-right-0 rounded-left">
				<button type="button" id="chat-image-btn" class="btn rounded-circle">
					<img src="/static/img/image.png" width="100%">
				</button>
				<input type="file" id="chat-image" class="d-none" accept=".jpg, .jpeg, .gif, .png">
			</div>
			<input type="text" id="chat-input" class="form-control border-left-0">
			<div class="input-group-append border rounded-right">
				<button type="button" id="send-btn" class="btn btn-light">전송</button>
			</div>
		</div>
	</c:if>
	
	<c:if test="${empty chatListCardList}">
	<h2 class="text-center py-5 text-secondary">채팅이 없습니다.</h2>
	</c:if>
	</div>
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


<script>
	$(document).ready(function() {
		$('#chat-area-div').scrollTop($('#chat-area-div')[0].scrollHeight);
		
		let chatListId = ${chatListId};
		let completed = ${chatCard.product.completed};
		let subjectId = ${chatCard.owner.id};
		let productId = ${chatCard.product.id};
		
		if (completed) {
			$("#chat-input").attr("placeholder", "거래가 완료되어 채팅이 불가능합니다.");
			$("#chat-input").attr("disabled", true);
			$("#chat-image-send-btn").prop("disabled", true);
			$("#send-btn").prop("disabled", true);
			$("#chat-image-btn").prop("disabled", true);
		}
		
		$(".chat-product-img-box").on("click", function() {
			location.href = "/product/" + ${chatCard.product.id}
		})
		
		$(".chat-list").on("click", function() {
			let getChatListId = $(this).data("chat-list-id");
			location.href = "/chat/" + getChatListId;
		})
		
		$("#end-trade-btn").on("click", function() {
			let productId = ${chatCard.product.id};
			
			let buyerId = ${chatCard.buyer.id};
			let ownerId = ${chatCard.owner.id};
			
			$.ajax({
				type:"post"
				,url:"/chat/chatMessage/send"
				,data:{"chatListId":chatListId, "message":"거래완료신청", "type":"endTradeRequest"}
				
				,success:function(data) {
					if (data.code == 200) {
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in";
						}
					}
				}
				,error:function(request, status, error) {
					alert("채팅 전송에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		});
		
		$(".complete-trade-btn").on("click", function() {
			let productId = ${chatCard.product.id};
			console.log(productId)
			
			$.ajax({
				type:"put"
				,url:"/product/complete/" + productId
				
				,success:function(data) {
					if (data.code == 200) {
						alert("거래를 완료했습니다.");
						location.reload();
					}
					else {
						alert(data.error_message)
					}
				}
				,error:function(request, status, error) {
					alert("거래 완료 신청에 실패했습니다. 관리자에게 문의해주세요.")
				}
			})
		})
		
		$("#recommend").on("click", function() {
			alert("click")
			
			$.ajax ({
				type:"post"
				,url:"/like"
				,data:{"subjectId":subjectId, "type":productId}
				
				,success:function(data) {
					if (data.code == 200) {
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in";
						}
					}
				}
				,error:function(request, status, error) {
					alert("사용자 추천에 실패했습니다. 관리자에게 문의해주세요.");
				}
			})
		});
		
		$("#chat-image-btn").on("click", function() {
			$("#chat-image").click();
		})
		
		
		let selectedFile = null;

		// When a file is selected
		$("#chat-image").on("change", function(event) {
			selectedFile = event.target.files[0];
			if (!selectedFile) return;
		
		    var reader = new FileReader();
			reader.onload = function(event){
				$("#modal #preview").attr("src", event.target.result);
			};
			reader.readAsDataURL(selectedFile);
			$("#modal").modal("show");
		});

		$("#chat-image-send-btn").on("click", async function() {
		    const file = event.target.files[0];
		    if (!file) return;
		    let ext = file.name.split('.').pop();
			let formData = new FormData();
			formData.append("file", file);
			formData.append("key", `${userLoginId}`);
			formData.append("ext", ext);
			formData.append("type", "product-images");

			const uploadResponse = await fetch("/uploadToGcs", {
				method: "POST",
				body: formData
			});

			const uploadResult = await uploadResponse.json();
			if (uploadResult.code !== 200) {
				alert("Upload failed: " + uploadResult.error);
				return;
			}
		    const imageUrl = uploadResult.imageUrl;

		    const msg = {
				message: imageUrl,
				type: "image"
		    };
			stompClient.send(`/app/ws-chat/${chatListId}/send`, {}, JSON.stringify(msg));
			
			// Close modal and reset input
			$("#modal").modal("hide");
			$("#chat-image").val('');
			selectedFile = null;
		});
		
		
		// $("#chat-input").on("keydown", function(key) {
		// 	if (key.keyCode == 13) {
		// 		$("#send-btn").click(sendMessage);
		// 	}
		// });
		
		let stompClient = null;

		function connect() {
		    let socket = new SockJS('/ws-chat');
		    stompClient = Stomp.over(socket);

			// Send userId as connection header
			let connectHeaders = {
				'userId': `${userId}`  // Make sure you have userId available in your JSP
			};

		    stompClient.connect(connectHeaders, function(frame) {
		        console.log("Connected: " + frame);

		        // subscribe only to this room
		        stompClient.subscribe('/topic/chat/' + chatListId, function(message) {
		            showMessage(JSON.parse(message.body));
		        });
		    });
		}

		function sendMessage() {
			let message = $("#chat-input").val().trim();
		    let msg = {
		        content: message,
		        type: "message"
		    };
		    stompClient.send('/app/ws-chat/' + chatListId + '/send', {}, JSON.stringify(msg));
		}

		function showMessage(message) {
			if (message.senderId == ${userId}) {				
				if (message.type == "message") {
				    $("#chat-area-div").append(`
				    	now
				    	<div class="chat-area d-flex align-items-end justify-content-end pb-2 pr-3">
				    		<div class="chat my-chat p-2 px-3 ml-2 d-flex align-items-center">
				    			${message.content}
				    		</div>
				    	</div>`);				
				}
				else if (message.type == "image") {
				    $("#chat-area-div").append(`
				    	now
				    	<div class="chat-area d-flex align-items-end justify-content-end pb-2 pr-3">
				    		<div class="chat my-chat p-2 px-3 ml-2 d-flex align-items-center">
				    			<img src="${message.content}" width="100%">
				    		</div>
				    	</div>`);
				}
			}
			else {
				if (message.type == "message") {
				    $("#chat-area-div").append(`
				    	<div class="chat-area d-flex align-items-end pb-2">
				    		<div class="chat received-chat p-2 px-3 mr-2 d-flex align-items-center">
				    			${message.message}
				    		</div>
				    	</div>
				    	now`);
				}
				else if (message.type == "image") {
				    $("#chat-area-div").append(`
				    	<div class="chat-area d-flex align-items-end pb-2">
				    		<div class="chat received-chat p-2 px-3 mr-2 d-flex align-items-center">
				    			<img src="${message.message}" width="100%">
				    		</div>
				    	</div>
				    	now`);
				}
			}
			$("#chat-area-div").scrollTop($("#chat-area-div")[0].scrollHeight);
		}

		$(function() {
		    connect();
		    $("#send-btn").click(sendMessage);
			$("#chat-input").on("keydown", function(key) {
				if (key.keyCode == 13) {
					$("#send-btn").click(sendMessage);
				}
			});
		});

	});
</script>