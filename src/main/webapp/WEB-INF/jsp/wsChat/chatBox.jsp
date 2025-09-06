<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
			<div id="chat-area-box" data-chat-list-id="" class="pt-4 pb-2">
				<c:forEach items="${chatCard.cml}" var="chatMessage">
					<c:if test="${chatMessage.senderId ne userId}">
						<div class="chat-area d-flex align-items-end pb-2">
							<div class="chat received-chat p-2 px-3 mr-2 d-flex align-items-center">
								<c:if test="${chatMessage.type == 'image'}">
									<img src="${chatMessage.message}" width="100%">
								</c:if>

								<c:if test="${chatMessage.type == 'message'}">
									${chatMessage.message}
								</c:if>

								<c:if test="${chatMessage.type == 'endTradeRequest'}">
									<c:if test="${chatCard.product.completed == false}">
										<div>
											Complete This Trade?<br>
											<button type="button" class="complete-trade-btn form-control mt-1  btn btn-primary">거래 완료</button>
										</div>
									</c:if>

									<c:if test="${chatCard.product.completed == true}">
										This trade has been completed.
									</c:if>
								</c:if>
							</div>
							<fmt:formatDate value="${chatMessage.createdAt}" pattern="HH:mm" var="createdAt" />
								${createdAt}
						</div>
					</c:if>

					<c:if test="${chatMessage.senderId eq userId}">
						<div class="chat-area d-flex align-items-end justify-content-end pb-2 pr-3">
							<fmt:formatDate value="${chatMessage.createdAt}" pattern="HH:mm" var="createdAt" />
								${createdAt}
							<div class="chat my-chat p-2 px-3 ml-2 d-flex align-items-center">
								<c:if test="${chatMessage.type == 'image'}">
									<img src="${chatMessage.message}" width="100%">
								</c:if>

								<c:if test="${chatMessage.type == 'message'}">
									${chatMessage.message}
								</c:if>

								<c:if test="${chatMessage.type == 'endTradeRequest'}">
									<c:if test="${chatCard.product.completed == false}">
										You have requested to complete this trade.
									</c:if>

									<c:if test="${chatCard.product.completed == true}">
										This trade has been completed.
									</c:if>
								</c:if>
							</div>
						</div>
					</c:if>
				</c:forEach>

				<c:if test="${chatCard.product.completed == true}">
					<div class="d-flex justify-content-center text-center">
						<div id="recommend-box" class="my-3 mt-4">
							<c:choose>
								<c:when test="${userId == chatCard.product.buyerId}">
									<c:if test="${chatCard.recommended == false}">
										You have completed a trade.<br>
										How was it? Do you want to recommend ${chatCard.owner.nickname}?
										<br>
										<div class="mt-2">
											<button type="button" id="recommend" class="btn btn-primary">추천하기</button>
										</div>
									</c:if>

									<c:if test="${chatCard.recommended == true}">
										You have recommended ${chatCard.owner.nickname}.
									</c:if>
								</c:when>

								<c:when test="${userId == chatCard.product.ownerId}">
									This trade has been completed.
								</c:when>

								<c:otherwise>
									This product has already been sold.
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:if>

			</div>
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


<script>
	$(document).ready(function() {
		$('#chat-area-div').scrollTop($('#chat-area-div')[0].scrollHeight);

		let chatListId = ${chatListId};
		let completed = ${chatCard.product.completed};
		let subjectId = ${chatCard.owner.id};
		let productId = ${chatCard.product.id};
		let selectedFile = null;

		if (completed) {
			$("#chat-input").attr("placeholder", "거래가 완료되어 채팅이 불가능합니다.");
			$("#chat-input").attr("disabled", true);
			$("#chat-image-send-btn").prop("disabled", true);
			$("#send-btn").prop("disabled", true);
			$("#chat-image-btn").prop("disabled", true);
		}

		ChatWebSocketManager.init('${userId}').then(function(stompClient) {
			// subscribe to current chat room
			ChatWebSocketManager.subscribe(
					'/topic/chat/${chatListId}',
					function(message) {
						let messageData = JSON.parse(message.body);
						showMessage(messageData);
					},
					'chatBox-${chstListId}'
			);
		});

		$(".chat-product-img-box").on("click", function() {
			location.href = "/product/" + ${chatCard.product.id}
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

		$("#end-trade-btn").on("click", function() {
			let msg = {
				type: "endTradeRequest"
			};
			ChatWebSocketManager.send('/app/ws-chat/' + chatListId + '/send', {}, JSON.stringify(msg));

			$("#chat-input").val("");
		});

		$("#chat-image-btn").on("click", function() {
			$("#chat-image").click();
		})

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
			const file = $("#chat-image")[0].files[0];
			if (!file) return;
			let ext = file.name.split('.').pop();
			let formData = new FormData();
			formData.append("file", file);
			formData.append("key", `${userLoginId}`);
			formData.append("ext", ext);
			formData.append("type", "chat-images");

			const uploadResponse = await fetch("/uploadToLocal", {
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
			ChatWebSocketManager.send('/app/ws-chat/' + chatListId + '/send', {}, JSON.stringify(msg));

			// Close modal and reset input
			$("#modal").modal("hide");
			$("#chat-image").val('');
			selectedFile = null;
		});

		function sendMessage() {
			let message = $("#chat-input").val().trim();
			let msg = {
				message: message,
				type: "message"
			};
			ChatWebSocketManager.send('/app/ws-chat/' + chatListId + '/send', {}, JSON.stringify(msg));
			$("#chat-input").val("");
		}

		function showMessage(message) {
			let content = message.message
			let chatDiv = '';
			if (message.type == "message") {
				if (message.senderId == ${userId}) {
					console.log(content)
					chatDiv = $(`
				    	<div class="chat-area d-flex align-items-end justify-content-end pb-2 pr-3">
				    		now
				    		<div class="chat my-chat p-2 px-3 ml-2 d-flex align-items-center">
				    		</div>
				    	</div>`)
				}
				else {
					chatDiv = $(`
				    	<div class="chat-area d-flex align-items-end pb-2">
				    		<div class="chat received-chat p-2 px-3 mr-2 d-flex align-items-center">
				    		</div>
				    		now
				    	</div>`);
				}
				chatDiv.find('.chat').text(content);
			}
			else if (message.type == "image") {
				if (message.senderId == ${userId}) {
					chatDiv = $(`
				    	<div class="chat-area d-flex align-items-end justify-content-end pb-2 pr-3">
				    		now
				    		<div class="chat my-chat p-2 px-3 ml-2 d-flex align-items-center">
				    			<img class="chat-img" width="100%">
				    		</div>
				    	</div>`);
				}
				else {
					chatDiv = $(`
				    	<div class="chat-area d-flex align-items-end pb-2">
				    		<div class="chat received-chat p-2 px-3 mr-2 d-flex align-items-center">
				    			<img class="chat-img" width="100%">
				    		</div>
				    		now
				    	</div>`);
				}
				chatDiv.find('.chat').find('.chat-img').attr('src', content);
			}
			else if (message.type == "endTradeRequest") {
				if (message.senderId == ${userId}) {
					chatDiv = $(`
				    	<div class="chat-area d-flex align-items-end justify-content-end pb-2 pr-3">
				    		now
				    		<div class="chat my-chat p-2 px-3 ml-2 d-flex align-items-center">
				    			You have requested to complete this trade.
				    		</div>
				    	</div>`);
				}
				else {
					chatDiv = $(`
				    	<div class="chat-area d-flex align-items-end pb-2">
				    		<div class="chat received-chat p-2 px-3 mr-2 d-flex align-items-center">
				    			<div>
									Complete This Trade?<br>
									<button type="button" class="complete-trade-btn form-control mt-1  btn btn-primary">
										Complete Trade
									</button>
								</div>
				    		</div>
				    		now
				    	</div>`);
				}
			}
			else if (message.type == "complatedTrade") {
				if (message.senderId != ${userId}) {
					location.reload();
				}
			}
			$("#chat-area-box").append(chatDiv);
			$("#chat-area-div").scrollTop($("#chat-area-div")[0].scrollHeight);
		}

		$(function() {
			$("#send-btn").click(sendMessage);
			$("#chat-input").on("keydown", function(e) {
				if (e.keyCode == 13 || e.which == 13) {
					e.preventDefault();
					sendMessage()
				}
			});
		});

		$(document).on('click',".complete-trade-btn", function () {
			let productId = ${chatCard.product.id};

			$.ajax({
				type:"put"
				,url:"/product/complete/" + productId

				,success:function(data) {
					if (data.code == 200) {
						let msg = {
							type: "complatedTrade"
						};
						ChatWebSocketManager.send('/app/ws-chat/' + chatListId + '/send', {}, JSON.stringify(msg));

						$("#chat-input").val("");
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
			});
		});
	});
</script>