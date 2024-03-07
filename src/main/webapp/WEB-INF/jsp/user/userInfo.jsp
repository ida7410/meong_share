<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex pt-4 pb-5">
	<div class="col-2 p-3 border-right">
		<div class="profile-img-box">
		<c:if test="${user.profileImagePath ne null}">
			<img src="${user.profileImagePath}" class="crop-img" width="100%">
		</c:if>
		<c:if test="${user.profileImagePath eq null}">
			<img src="/static/img/empty-profile.png" width="100%">		
		</c:if>
		</div>
		
		<h3 class="font-weight-bold pt-2">${user.nickname}</h3>
		<div>
			<h5>거래 횟수: ${tradeCount}</h5>
			<h5>추천 횟수: ${recommendCount}</h5>
		</div>
	</div>
	
	<div id="user-proudct-list-large-box" class="col-10">
		<jsp:include page="./userProductList.jsp" />
	</div>
</div>

