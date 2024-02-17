<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-8">
		<h2 class="text-center font-weight-bold my-5">상품 등록</h2>

		<div class="d-flex">

			<!-- img -->
			<div class="col-6">
				<div id="preview" class="w-100"></div>
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


<script>
	$(document).ready(function() {
		$.datepicker.setDefaults({
            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'] // 요일을 한글로 변경
            , dateFormat: 'yy-mm-dd'
        });
		
		// 오늘 날짜로 이동하는 함수
        $.datepicker._gotoToday = function(id) {
            $(id).datepicker('setDate', new Date()).datepicker('hide').blur();
        };
        
		$("#boughtDate").datepicker({
			showButtonPanel:true
			,maxDate:0
		})
		
		$("#productImageFile").on("change", function(event) {
			var reader = new FileReader();
			
			reader.onload = function(event){
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				img.setAttribute("width", "100%");
				document.querySelector("div#preview").appendChild(img);
			};
			
			reader.readAsDataURL(event.target.files[0]);
		});
		
		$("#create-btn").on("click", function() {
			let fileName = $("#productImageFile").val();
			let name = $("#name").val().trim();
			let company = $("#company").val().trim();
			let price = $("#price").val().trim();
			let boughtDate = $("#boughtDate").val().trim();
			let description = $("#description").val().trim();
			
			if (!fileName) {
				alert("파일을 선택해주세요.");
				return;
			}
			if (!name) {
				alert("제목을 입력해주세요.");
				return;
			}
			if (!company) {
				alert("제조업체를 입력해주세요.");
				return;
			}
			if (!price) {
				alert("가격을 입력해주세요.");
				return;
			}
			if (!Number.isInteger(+price)) {
				alert("가격이 유효하지 않습니다.");
				return;
			}
			if (!boughtDate) {
				alert("구매날짜을 입력해주세요.");
				return;
			}
			if (!description) {
				alert("설명을 입력해주세요.");
				return;
			}
			
			let formData = new FormData();
			formData.append("productImageFile", $("#productImageFile")[0].files[0]);
			formData.append("name", name);
			formData.append("company", company);
			formData.append("price", price);
			formData.append("boughtDate", boughtDate);
			formData.append("description", description);
			
			$.ajax({
				type:"post"
				,url:"/product/create"
				,data:formData
				,enctype:"multipart/form-data"
				,processData:false
				,contentType:false
				
				,success:function(data) {
					if (data.code == 200) {
						alert("물품 등록에 성공했습니다.");
						location.href = "/product/" + data.insertedProductId;
					}
					else {
						alert(data.error_message);
					}
				}
				,error:function(request, status, error) {
					alert("물품 등록에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		});
	})
</script>