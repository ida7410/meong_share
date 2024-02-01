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
	
	<div class="col-10">
		
		<div class="d-flex flex-wrap">
			<div class="product bg-primary col-3 p-3 mr-2">
				<!-- img -->
				<div class="product-img-box w-100 bg-info mb-3">
					<img src="" alt="">
				</div>
				
				<!-- title -->
				<h4 class="font-weight-bold">제목이 들어가는 곳</h4>
				
				<h5>18000원</h5>
				
				<h7>1일 전</h7>
				
				<h6>닉네임뭐하지</h6>
				<h6>설명ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ</h6>
			</div>
			
			<div class="product bg-primary col-3 p-3 mr-2">
				<!-- img -->
				<div class="product-img-box w-100 bg-info mb-3">
					<img src="" alt="">
				</div>
				
				<!-- title -->
				<h4 class="font-weight-bold">제목이 들어가는 곳</h4>
				
				<h5>18000원</h5>
				
				<h7>1일 전</h7>
				
				<h6>닉네임뭐하지</h6>
				<h6>설명ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ</h6>
			</div>
		</div>

		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<li class="page-item disabled"><a class="page-link" href="#" tabindex="-1">Previous</a></li>
				<li class="page-item"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">Next</a></li>
			</ul>
		</nav>
	</div>
</div>