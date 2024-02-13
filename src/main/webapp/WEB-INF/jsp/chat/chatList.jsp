<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex">
	<div class="col-3 chat-list-box bg-primary p-0">
	<c:forEach items="${chatListCardList}" var="chatListCard">
		<div class="chat-list d-flex bg-info py-3" data-chat-list-id="${chatListCard.cl.id}">
			<div class="col-3">
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
		<jsp:include page="chatBox.jsp" />
	</div>
</div>


<script>
	$(document).ready(function() {
		let chatListId = ${chatListId};
		
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
				,data:{"chatListId":chatListId, "message":"거래를 완료하시겠습니까?"}
				
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