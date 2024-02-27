<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex pt-4 pb-5">
	<div class="col-2 p-3">
		<div class="profile-img-box">
		<c:if test="${user.profileImagePath ne null}">
			<img src="${user.profileImagePath}" class="crop-img" width="100%">
		</c:if>
		<c:if test="${user.profileImagePath eq null}">
			<img src="/static/img/empty-profile.jpg" class="crop-img" width="100%">		
		</c:if>
		</div>
		
		<h3 class="font-weight-bold pt-2">${user.nickname}</h3>
		<div>
			<h5>거래 횟수: ${tradeCount}</h5>
			<h5>추천 횟수: ${recommendCount}</h5>
		</div>
	</div>
	
	<div class="col-10">
		<nav>
			<ul class="nav">
				<li class="nav-item pointer"><a id="incompleted" data-user-login-id="${user.loginId}" class="nav-link user-info-nav-link">전체</a></li>
				<li class="nav-item pointer"><a id="completed" data-user-login-id="${user.loginId}" class="nav-link user-info-nav-link">거래 완료</a></li>
			</ul>
		</nav>
		<div class="user-product-box">
			<jsp:include page="../product/productList.jsp" />
		</div>
	</div>
</div>


<script>
	$(document).ready(function() {
		$(".product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
		
		$("#incompleted").on("click", function() {
			let userLoginId = $(this).data("user-login-id");
			console.log(userLoginId);
			$.ajax({
				data:{"userLoginId":userLoginId, "completed":false}
				,url:"/user-product-list"
				,success:function(data) {
					$(".product-list-box").html(data);
				}
			})
		})
		
		$("#completed").on("click", function() {
			let userLoginId = $(this).data("user-login-id");
			console.log(userLoginId);
			$.ajax({
				data:{"userLoginId":userLoginId, "completed":true}
				,url:"/user-product-list"
				,success:function(data) {
					$(".product-list-box").html(data);
				}
			})
		})
	})
</script>