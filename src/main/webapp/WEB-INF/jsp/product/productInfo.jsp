<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="p-5">
	<div class="d-flex p-4 bg-danger">
		<div class="col-4 bg-primary mr-5">
			<img src="${card.product.imagePath}" width="100%">
		</div>
		
		<div>
			<h2 class="font-weight-bold">${card.product.name}</h2>
			<div class="d-flex mb-3">
				<div>
					<h6>${card.product.company}</h6>
					<h5>${card.product.price}</h5>
					<h6 class="d-flex align-items-center">
						<img id="heart" src="/static/img/${card.liked}heart-icon.png" width="15px" class="pointer">
						&nbsp; 찜 ${card.likeCount}개
					</h6>
				</div>
				
				<div class="ml-5">
					<h5><a href="/user/${card.user.loginId}">${card.user.name}</a></h5>
					<span>추천 횟수: ${card.recommendCount }</span><br>
					<span>거래 횟수: </span>
				</div>
			</div>
			
			<pre id="product-desc">${card.product.description}</pre>
			
			<div>
				구매일: ${card.product.boughtDate}
				<br>
				업로드: ${card.product.createdAt}
			</div>
			
			<c:if test="${userId ne card.user.id}">
			<button type="button" id="start-chat-btn" class="btn btn-primary">문의하기</button>
			</c:if>
		</div>
	</div>
	
	<jsp:include page="../suggestion/recommend.jsp" />
</div>


<script>
	$(document).ready(function() {
		$("#start-chat-btn").on("click", function() {
			let productId = ${card.product.id};
			let ownerId = ${card.user.id};
			
			$.ajax({
				type:"POST"
				,url:"/chat/chatList/create"
				,data:{"productId":productId, "ownerId":ownerId}
			
				,success:function(data) {
					if (data.code == 200) {
						location.href = "/chat/" + data.chatListId;
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in";
						}
					}
				}
				,error:function(request, status, error) {
					alert("채팅방 생성에 실패했습니다. 관리자에게 문의해주세요.");
				}
			})
		});
		
		$("#heart").on("click", function() {
			let productId = ${card.product.id};
			console.log(productId);
			
			$.ajax({
				type:"post"
				,url:"/like"
				,data:{"subjectId":productId, "type":"like"}
			
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
					alert("찜에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		});
	});
</script>