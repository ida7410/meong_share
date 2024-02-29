<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${empty recentTradeProductList}">
<div class="display-4 text-secondary pb-5 mb-5">최근 거래 내역이 없습니다.</div>
</c:if>

<c:if test="${not empty recentTradeProductList}">
<c:forEach items="${recentTradeProductList}" var="product">
<div class="recent-trade-product d-flex p-3 pointer bg-hover" data-product-id="${product.id}">
	<div class="product-img col-2 bg-danger p-0">
		<img src="${product.imagePath}" alt="" width="100%">
	</div>
	<div class="product-desc col-10 pt-2">
		<h4 class="font-weight-bold">${product.name}</h4>
		<h5>
		<c:if test="${fn:length(product.description) > 50}">
			${fn:substring(product.description, 0, 50)}...
		</c:if>
				
		<c:if test="${fn:length(product.description) <= 50}">
			${product.description}
		</c:if>
		</h5>
	</div>
</div>
</c:forEach>
</c:if>


<script>
	$(document).ready(function() {
		$(".recent-trade-product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		})
	})
</script>