<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex">
	<div class="col-3 chat-list-box bg-primary p-0">
	<c:forEach items="${chatListCardList}" var="chatListCard">
		<div class="chat-list d-flex bg-info py-3" data-chat-list-id="${chatListCard.cl.id}">
			<div class=" col-3">
				<img src="${chatListCard.product.imagePath}" width="100%">
			</div>
			<div class="col-9">
				<h5 class="font-weight-bold">${chatListCard.product.name}</h5>
				<h6>${chatListCard.latestCM.message}</h6>
			</div>
		</div>
	</c:forEach>
	</div>

	<div id="chat-box" class="col-9 px-4 pb-4">
		<div class="bg-primary d-flex justify-content-center text-center py-3">
			<div class="col-3">
				<div>
					<img src="${chatCard.product.imagePath}" width="100%">
				</div>
				<h5 class="font-weight-bold mt-3">${chatCard.product.name}</h5>
				
				<h5>
				<c:if test="${userId == chatCard.product.ownerId}">
					<a href="/user/${chatCard.buyer.loginId}">${chatCard.buyer.nickname}</a>
				</c:if>
				<c:if test="${userId != chatCard.product.ownerId}">
					<a href="/user/${chatCard.owner.loginId}">${chatCard.owner.nickname}</a>
				</c:if>
				</h5>
				
			</div>
		</div>

		<div id="chat-area-box" data-chat-list-id="" class="py-4">
		<c:forEach items="${chatCard.cml}" var="chatMessage">
			<c:if test="${chatMessage.senderId ne userId}">
			<div class="chat-area d-flex pb-2">
				<div class="chat bg-danger p-3 px-4">${chatMessage.message}</div>
			</div>
			</c:if>
			
			<c:if test="${chatMessage.senderId eq userId}">
			<div class="chat-area d-flex justify-content-end pb-2">
				<div class="chat my-chat bg-primary p-3 px-4">${chatMessage.message}</div>
			</div>
			</c:if>
		</c:forEach>
		</div>

		<div id="chat-input-box" class="input-group input-group-lg">
			<input type="text" id="chat-input" class="form-control">
			<div class="input-group-append">
				<button type="button" id="send-btn" class="btn btn-light">전송</button>
			</div>
		</div>
	</div>
</div>


<script>
	$(document).ready(function() {
		let chatListId = ${chatListId};
		
		$(".chat-list").on("click", function() {
			let getChatListId = $(this).data("chat-list-id");
			location.href = "/chat/" + getChatListId;
		})
		
		$("#chat-input").on("keydown", function(key) {
			if (key.keyCode == 13) {
				$("#send-btn").click();
			}
		});
		
		$("#send-btn").on("click", function() {
			let message = $("#chat-input").val().trim();
			
			$.ajax({
				type:"POST"
				,url:"/chat/chatMessage/send"
				,data:{"chatListId":chatListId, "message":message}
			
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
			});
		});
	});
</script>