<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="d-flex pt-5">
	<nav class="side-nav navbar col-2 d-none">
		<ul class="navbar-nav w-100">
			<li class="nav-item"><a href="" class="nav-link"></a></li>
		</ul>
	</nav>
	
	<div class="col-2">
		<h4 class="font-weight-bold">상품 검색</h4>
		<div class="input-group mb-5">
			<input type="text" id="keyword" class="form-control">
			<div class="input-group-append">
				<button type="button" class="btn btn-light">검색</button>
			</div>
		</div>
		
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
</div>