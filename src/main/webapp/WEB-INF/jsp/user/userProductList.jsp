<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<nav>
	<ul class="nav">
		<li class="nav-item pointer"><a id="incompleted" data-user-login-id="${user.loginId}" class="nav-link user-info-nav-link">전체</a></li>
		<li class="nav-item pointer"><a id="completed" data-user-login-id="${user.loginId}" class="nav-link user-info-nav-link">거래 완료</a></li>
	</ul>
</nav>
<div id="user-product-list-box">
	<jsp:include page="../product/productList.jsp" />
</div>

<script>
	$(document).ready(function() {
		$(".product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
	})
</script>