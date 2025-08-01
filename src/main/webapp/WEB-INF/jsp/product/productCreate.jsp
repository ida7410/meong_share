<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-7">
		<h2 class="text-center font-weight-bold my-5">Upload Post</h2>

		<div class="d-flex">

			<!-- img -->
			<div class="col-6">
				<div id="preview-box" class="w-100">
					<img src="" id="preview" class="crop-img" width="100%">
				</div>
				<div class="d-flex justify-content-end">
					<input type="file" id="productImageFile" accept=".png, .jpg, .jpeg, .gif">
				</div>
			</div>
			
			<!-- basic info -->
			<div class="col-6">
				<h5 class="font-weight-bold">Title</h5>
				<input type="text" id="name" class="form-control">
				
				<h5 class="font-weight-bold mt-3">Company</h5>
				<input type="text" id="company" class="form-control">
				
				<h5 class="font-weight-bold mt-3">Price</h5>
				<input type="text" id="price" class="form-control">
				
				<h5 class="font-weight-bold mt-3">Bought At</h5>
				<input type="text" id="boughtDate" class="form-control">
			</div>
		</div>

		<div class="my-4">
			<textarea id="description" class="form-control" rows="7"></textarea>
			<div id="desc" class="d-flex justify-content-end">512</div>
		</div>
		
		<button id="create-btn" type="button" class="btn btn-primary form-control mt-2 mb-5">Upload</button>
	</div>
</div>


<script>
	$(document).ready(function() {
		$.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd'
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
				var preview = document.getElementById("preview");
				preview.setAttribute("src", event.target.result);
			};
			
			reader.readAsDataURL(event.target.files[0]);
		});
		
		$("#description").on("input", function() {
			let desc = $(this).val();
			let descLen = desc.length;
			
			$("#desc").removeClass("text-danger");
			
			if (descLen < 50 || descLen > 512) {
				$("#desc").addClass("text-danger");
			}
			
			$("#desc").text(512 - descLen)
		})
		
		$("#create-btn").on("click", function() {
			let fileName = $("#productImageFile").val();
			let name = $("#name").val().trim();
			let company = $("#company").val().trim();
			let price = $("#price").val().trim();
			let boughtDate = $("#boughtDate").val().trim();
			let description = $("#description").val().trim();
			
			if (!fileName) {
				alert("Please select a file.");
				return;
			}
			if (!name) {
				alert("Please enter the title.");
				return;
			}
			if (!company) {
				alert("Please enter which company you bought this product.");
				return;
			}
			if (!price) {
				alert("Please enter the price.");
				return;
			}
			if (!Number.isInteger(+price)) {
				alert("Unavailable price");
				return;
			}
			if (!boughtDate) {
				alert("Please provide the date you bought.");
				return;
			}
			if (!description) {
				alert("Please explain the product.");
				return;
			}
			if (description.length < 50) {
				alert("설명은 50자 이상이어야 합니다.");
				return;
			}
			if (description.length > 512) {
				alert("Description can be no more than 512 characters.");
				return;
			}
			
			let formData = new FormData();
			formData.append("productImageFile", $("#productImageFile")[0].files[0]);
			formData.append("name", name);
			formData.append("company", company);
			formData.append("price", price);
			formData.append("boughtDate", boughtDate);
			formData.append("description", description);
			console.log(formData);
			
			$.ajax({
				type:"post"
				,url:"/product/create"
				,data:formData
				,enctype:"multipart/form-data"
				,processData:false
				,contentType:false
				
				,success:function(data) {
					if (data.code == 200) {
						alert("Successfully uploaded your product!");
						location.href = "/product/" + data.insertedProductId;
					}
					else {
						alert(data.error_message);
					}
				}
				,error:function(request, status, error) {
					alert("Faield to upload. Please contact through Customer service.");
				}
			});
		});
	})
</script>