<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex pt-5">
	<nav class="side-nav navbar col-2 d-none">
		<ul class="navbar-nav w-100">
			<li class="nav-item"><a href="" class="nav-link"></a></li>
		</ul>
	</nav>
	
	<div class="col-2">
		<h4 class="font-weight-bold">상품 검색</h4>
		<form method="GET" action="/search">
		<div class="input-group mb-5">
			<input type="text" id="keyword" name="keyword" class="form-control" value="${keyword}">
			<div class="input-group-append">
				<button id="search-btn" type="submit" class="btn btn-light">검색</button>
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
		
		<div class="d-flex flex-wrap">
			<c:forEach items="${cardList}" var="card">
			<div class="product bg-primary col-3 p-3" data-product-id="${card.product.id}">
				<!-- img -->
				<div class="product-img-box w-100 bg-info mb-3">
					<img src="${card.product.imagePath}" alt="" width="100%">
				</div>
				
				<!-- title -->
				<h4 class="font-weight-bold">${card.product.name}</h4>
				
				<h5>${card.product.price}원</h5>
				
				<h7>1일 전</h7>
				
				<h6>${card.user.name}</h6>
				<h6>${card.product.description}</h6>
			</div>
			</c:forEach>
		</div>

		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<li class="page-item disabled">
					<a class="page-link" href="#" tabindex="-1">Previous</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#">1</a>
				</li>
				<li class="page-item"><a class="page-link" href="?page=2">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">Next</a></li>
			</ul>
		</nav>
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