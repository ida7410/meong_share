<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex pt-5">
	
	<div class="col-2 pl-4">
		<h4 class="font-weight-bold">상품 검색</h4>
		<form method="GET" action="/search">
		<div class="input-group mb-5">
			<input type="text" id="keyword" name="keyword" class="form-control" value="${keyword}">
			<div class="input-group-append">
				<button id="search-btn" type="button" class="btn btn-light">검색</button>
			</div>
		</div>
		</form>
		
		<h4 class="font-weight-bold">최근 검색 목록</h4>
		<div class="mb-5">
			<a href="/search?keyword" class="keywords">목줄</a><br>
			<a href="/search?keyword" class="keywords">목줄</a><br>
		</div>
		
		<h4 class="font-weight-bold">최근 검색 목록</h4>
		<div class="mb-5 col-9 p-0">
			<div class="recent-search-product bg-primary p-2">
				<div class="recent-search-product-img-box w-100 bg-danger mb-2">
					<img src="">
				</div>
				<h5 class="font-weight-bold">제목</h5>
			</div>
			<div class="recent-search-product bg-primary p-2">
				<div class="recent-search-product-img-box w-100 bg-danger mb-2">
					<img src="">
				</div>
				<h5 class="font-weight-bold">제목</h5>
			</div>
			<div class="recent-search-product bg-primary p-2">
				<div class="recent-search-product-img-box w-100 bg-danger mb-2">
					<img src="">
				</div>
				<h5 class="font-weight-bold">제목</h5>
			</div>
		</div>
	</div>
	
	<div class="col-10">
		<jsp:include page="productList.jsp" />
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