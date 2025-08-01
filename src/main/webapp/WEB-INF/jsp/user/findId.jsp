<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-3 pb-5">
		<h2 class="text-center font-weight-bold mt-5 mb-4">Find ID</h2>
		
		<h5 class="font-weight-bold">Name</h5>
		<input type="text" id="name" class="form-control">
	
		<h5 class="font-weight-bold mt-3">Email</h5>
		<input type="text" id="email" class="form-control">
			
		<button id="find-id-btn" type="button" class="btn btn-primary form-control mt-4 my-2">Find My ID</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#find-id-btn").on("click", function() {
			let name = $("#name").val().trim();
			let email = $("#email").val().trim();
			
			if (!name) {
				alert("Please enter your name.");
				return;
			}
			if (!email) {
				alert("Please enter Email.");
				return;
			}
			
			$.ajax({
				type:"post"
				,url:"/user/findId"
				,data:{"name":name, "email":email}
				
				,success:function(data) {
					if (data.code == 200) {
						alert(data.message);
						location.href = "/log-in";
					}
					else if(data.code == 300) {
						alert(data.message);
						location.reload();
					}
				}
			})
		});
	})
</script>