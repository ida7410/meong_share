<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="chat-area-box" data-chat-list-id="" class="pt-4 pb-2">
<c:forEach items="${chatCard.cml}" var="chatMessage">
	<c:if test="${chatMessage.senderId ne userId}">
	<div class="chat-area d-flex align-items-end pb-2">
		<div class="chat received-chat p-2 px-3 mr-2 d-flex align-items-center">
		<c:if test="${chatMessage.type == 'image'}">
			<img src="${chatMessage.imagePath}" width="100%">
		</c:if>
		
		<c:if test="${chatMessage.type == 'message'}">
			${chatMessage.message}
		</c:if>
		
		<c:if test="${chatMessage.type == 'endTradeRequest'}">
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
			<img src="${chatMessage.imagePath}" width="100%">
		</c:if>
		
		<c:if test="${chatMessage.type == 'message'}">
			${chatMessage.message}
		</c:if>
		
		<c:if test="${chatMessage.type == 'endTradeRequest'}">
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

	<c:if test="${chatCard.product.completed == true}">
	<div class="d-flex justify-content-center text-center">
		<div id="recommend-box" class="my-3 mt-4">
			<c:choose>
			<c:when test="${userId == chatCard.product.buyerId}">
				<c:if test="${chatCard.recommended == false}">
				상품 거래가 완료되었습니다.<br>
				거래는 어떠셨나요? ${chatCard.owner.nickname}님을 추천하시겠나요?
				<br>
				<div class="mt-2">
					<button type="button" id="recommend" class="btn btn-primary">추천하기</button>
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
		</div>
	</div>
	</c:if>

</div>

