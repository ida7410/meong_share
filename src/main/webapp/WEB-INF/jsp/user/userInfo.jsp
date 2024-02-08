<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex">
	<div class="col-3 bg-primary p-3">
		<div>
			<img src="">
		</div>
		
		<h3 class="font-weight-bold">${userName}</h3>
		<div>
			<h5>거래 횟수: </h5>
			<h5>추천 횟수: </h5>
		</div>
	</div>
	
	<div class="d-flex flex-wrap col-9">
		<c:forEach items="${cardList}" var="card">
		<div class="product bg-danger col-3 p-3" data-product-id="${card.product.id}">
			<!-- img -->
			<div class="product-img-box w-100 bg-info mb-3">
				<img src="${card.product.imagePath}" alt="" width="100%">
			</div>
			
			<!-- title -->
			<h4 class="font-weight-bold">${card.product.name}</h4>
			
			<h5>${card.product.price}원</h5>
			
			<span>1일 전</span>
			
			<h6>${card.user.name}</h6>
			<h6>${card.product.description}</h6>
		</div>
		</c:forEach>
	</div>
</div>


<script>
	$(document).ready(function() {
		$(".product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
	})
</script>