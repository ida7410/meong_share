<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-8">
		<h2 class="text-center font-weight-bold my-5">상품 등록</h2>

		<div class="d-flex">

			<!-- img -->
			<div class="col-6">
				<img src="/static/img/dog1.jpg" id="preview" width="100%" />
				<div class="d-flex justify-content-end">
					<input type="file" id="productImageFile" accept=".png, .jpg, .jpeg, .gif">
				</div>
			</div>

			<!-- basic info -->
			<div class="col-6">
				<h5 class="font-weight-bold">제목</h5>
				<input type="text" id="name" class="form-control">
				
				<h5 class="font-weight-bold mt-3">제조업체</h5>
				<input type="text" id="company" class="form-control">
				
				<h5 class="font-weight-bold mt-3">가격</h5>
				<input type="text" id="price" class="form-control">
				
				<h5 class="font-weight-bold mt-3">구매일</h5>
				<input type="text" id="boughtDate" class="form-control">
			</div>
		</div>

		<div class="my-4">
			<textarea id="description" class="form-control" rows="7"></textarea>
		</div>
		
		<button id="create-btn" type="button" class="btn btn-primary form-control mt-2 mb-5">등록하기</button>
	</div>
</div>
