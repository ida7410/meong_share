<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex">
	<div class="col-3 chat-list-box p-0 border-right">
	<c:forEach items="${chatListCardList}" var="chatListCard">
		<div class="chat-list d-flex p-3 pointer bg-hover" data-chat-list-id="${chatListCard.cl.id}">
			<div class="col-3 chat-list-img-box">
				<img src="${chatListCard.product.imagePath}" class="crop-img" width="100%">
			</div>
			<div class="col-9">
				<h5 class="font-weight-bold">${chatListCard.product.name}</h5>
				<h6>${chatListCard.latestCM.message}</h6>
			</div>
		</div>
	</c:forEach>
	</div>

	<div id="chat-box" class="col-9 px-4 pb-4 border-bottom">
		<div class="d-flex justify-content-center text-center py-3">
			<div class="col-2">
				<div class="chat-product-img-box">
					<img src="${chatCard.product.imagePath}" class="crop-img" width="100%">
				</div>
				<h5 class="font-weight-bold mt-3 mb-2">${chatCard.product.name}</h5>
		
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
		
		setInterval(function() {
			$("#chat-area-div").load(location.href + " #chat-area-box");
		}, 3000)
		
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
				,data:{"chatListId":chatListId, "message":"거래완료신청"}
				
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
			let subjectId = ${chatCard.owner.id};
			let productId = ${chatCard.product.id};
			
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
		
		$("#not-recommend").on("click", function() {
			
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