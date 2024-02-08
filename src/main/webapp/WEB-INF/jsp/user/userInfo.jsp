<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex">
	<div class="col-2 bg-primary p-3">
		<div>
			<img src="">
		</div>
		
		<h3 class="font-weight-bold">${userName}</h3>
		<div>
			<h5>거래 횟수: </h5>
			<h5>추천 횟수: </h5>
		</div>
	</div>
	
	<div class="col-10">
		<jsp:include page="../product/productList.jsp" />
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