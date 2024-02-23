<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex pt-4 pb-5">
	<div class="col-2 bg-primary p-3">
		<div class="profile-img-box">
		<c:if test="${user.profileImagePath ne null}">
			<img src="${user.profileImagePath}" class="crop-img" width="100%">
		</c:if>
		<c:if test="${user.profileImagePath eq null}">
			<img src="/static/img/empty-profile.jpg" class="crop-img" width="100%">		
		</c:if>
		</div>
		
		<h3 class="font-weight-bold">${user.nickname}</h3>
		<div>
			<h5>거래 횟수: ${tradeCount}</h5>
			<h5>추천 횟수: ${recommendCount}</h5>
		</div>
	</div>
	
	<div class="col-10">
		<nav>
			<ul class="nav">
				<li class="nav-item"><a id="incompleted" class="nav-link">전체</a></li>
				<li class="nav-item"><a id="completed" class="nav-link">거래 완료</a></li>
			</ul>
		</nav>
		<jsp:include page="../product/productList.jsp" />
	</div>
</div>


<script>
	$(document).ready(function() {
		$(".product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
		
		$("#incompleted").on("click", function() {
			/* $.ajax({
				
			}) */
		})
	})
</script>