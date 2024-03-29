<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h3 class="font-weight-bold mt-4 p-2">이건 어떠세요?</h3>
<div class="recommend-product-box d-flex">
	<c:forEach items="${recommendProductList}" var="product">
	<div class="recommend-product d-flex col-4 p-3 pointer bg-hover border rounded mr-2" data-product-id="${product.id}">
		<div class="recommend-product-img-box col-3 p-0">
			<img src="${product.imagePath}" class="crop-img border rounded" alt="" width="100%">
		</div>
		<div class="product-desc col-9 pt-2">
			<h5 class="font-weight-bold">${product.name}</h5>
			<h6 class="text-break">
			<c:if test="${fn:length(product.description) > 50}">
				${fn:substring(product.description, 0, 50)}...
			</c:if>
					
			<c:if test="${fn:length(product.description) <= 50}">
				${product.description}
			</c:if>
			</h6>
		</div>
	</div>
	</c:forEach>
</div>


<script>
	$(document).ready(function() {
		$(".recommend-product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
	})
</script>