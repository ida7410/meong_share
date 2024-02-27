<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<hr>

<div id="chat-area-box" data-chat-list-id="" class="py-4">
<c:forEach items="${chatCard.cml}" var="chatMessage">
	<c:if test="${chatMessage.senderId ne userId}">
	<div class="chat-area d-flex pb-2">
		<div class="chat bg-danger p-2 px-3 d-flex align-items-center">
		<c:if test="${chatMessage.message != '거래완료신청'}">
			${chatMessage.message}
		</c:if>
		
		<c:if test="${chatMessage.message == '거래완료신청'}">
			<c:if test="${chatCard.product.completed == false}">
			<div>
				거래를 완료하시겠습니까?<br>
				<button type="button" class="complete-trade-btn form-control mt-1  btn btn-primary">거래 완료</button>
			</div>
			</c:if>
				
			<c:if test="${chatCard.product.completed == true}">
			거래가 완료되었습니다.
			</c:if>
		</c:if>
		</div>
	</div>
	</c:if>

	<c:if test="${chatMessage.senderId eq userId}">
	<div class="chat-area d-flex justify-content-end pb-2">
		<div class="chat my-chat bg-info p-2 px-3 d-flex align-items-center">
		<c:if test="${chatMessage.message != '거래완료신청'}">
			${chatMessage.message}
		</c:if>
		<c:if test="${chatMessage.message == '거래완료신청'}">
			<c:if test="${chatCard.product.completed == false}">
			거래 완료를 요청했습니다.
			</c:if>
			
			<c:if test="${chatCard.product.completed == true}">
			거래가 완료되었습니다.
			</c:if>
		</c:if>
		</div>
	</div>
	</c:if>
</c:forEach>

	<div class="d-flex justify-content-center my-3 mt-4 text-center">
		<div id="recommend-box">
		<c:if test="${chatCard.product.completed == true}">
			<c:choose>
			<c:when test="${userId == chatCard.product.buyerId}">
				<c:if test="${chatCard.recommended == false}">
				상품 거래가 완료되었습니다.<br>
				거래는 어떠셨나요? ${chatCard.owner.nickname}님을 추천하시겠나요?
				<br>
				<div class="mt-2">
					<span id="recommend" class="pointer">예</span>
					&nbsp; | &nbsp;
					<span id="not-recommend" class="pointer">아니오</span>
				</div>
				</c:if>
				
				<c:if test="${chatCard.recommended == true}">
				상대를 추천했습니다.
				</c:if>
			</c:when>
			
			<c:when test="${userId == chatCard.product.ownerId}">
				상품 거래가 완료되었습니다.
			</c:when>
			
			<c:otherwise>
				이미 상품이 거래되었습니다.
			</c:otherwise>
			</c:choose>
		</c:if>
		</div>
	</div>

</div>

<div id="chat-input-box" class="input-group input-group-lg">
	<input type="text" id="chat-input" class="form-control">
	<div class="input-group-append">
		<button type="button" id="send-btn" class="btn btn-light">전송</button>
	</div>
</div>
