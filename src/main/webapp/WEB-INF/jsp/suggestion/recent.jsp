<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3 class="font-weight-bold mt-4 p-2">가장 최근 올라온 물품</h3>
<div class="recent-product-box d-flex bg-danger">
	<c:forEach items="${recentProductList}" var="product">
	<div class="recent-product d-flex col-4 p-3 pointer" data-product-id="${product.id}">
		<div class="recent-product-img-box col-3 bg-warning p-0">
			<img src="${product.imagePath}" class="product-img" alt="" width="100%">
		</div>
		<div class="product-desc col-9 bg-info pt-2">
			<h5 class="font-weight-bold">${product.name}</h5>
			<h6>${product.description}</h6>
		</div>
	</div>
	</c:forEach>
</div>


<script>
	$(document).ready(function() {
		$(".recent-product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
	})
</script>