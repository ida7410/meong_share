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
			<img src="${chatMessage.imagePath}" width="100%">
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

