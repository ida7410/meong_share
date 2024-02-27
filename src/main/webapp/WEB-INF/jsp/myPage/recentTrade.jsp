<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${empty recentTradeProductList}">
<div class="display-4 text-secondary pb-5 mb-5">최근 거래 내역이 없습니다.</div>
</c:if>

<c:if test="${not empty recentTradeProductList}">
<c:forEach items="${recentTradeProductList}" var="product">
<div class="d-flex p-3 pointer bg-primary" data-product-id="${product.id}">
	<div class="product-img col-3 bg-danger p-0">
		<img src="${product.imagePath}" alt="" width="100%">
	</div>
	<div class="product-desc col-9 bg-success pt-2">
		<h5 class="font-weight-bold">${product.name}</h5>
		<h6>
		<c:if test="${fn:length(product.description) > 50}">
			${fn:substring(product.description, 0, 50)}...
		</c:if>
				
		<c:if test="${fn:length(product.description) <= 50}">
			${product.description}
		</c:if>
		</h6>
	</div>
</div>
<div class="recent-trade-product d-flex p-3 pointer bg-primary" data-product-id="${product.id}">
	<div class="product-img col-3 bg-danger p-0">
		<img src="${product.imagePath}" alt="" width="100%">
	</div>
	<div class="product-desc col-9 bg-success pt-2">
		<h5 class="font-weight-bold">${product.name}</h5>
		<h6>${product.price}원</h6>
		<h6>${product.description}</h6>
	</div>
</div>
</c:forEach>
</c:if>


<script>
	$(document).ready(function() {
		$(".recent-trade-product").on("click", function() {
			let productId = $(this).data("product-id");
			
			
		})
	})
</script>