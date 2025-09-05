<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div id="find-pw-box" class="col-3 pb-5">
		<h2 class="text-center font-weight-bold mt-5 mb-4">Find Password</h2>
		
		<h5 class="font-weight-bold">ID</h5>
		<input type="text" id="id" class="form-control">
	
		<h5 class="font-weight-bold mt-3">Email</h5>
		<input type="text" id="email" class="form-control">
	
		<button id="find-pw-btn" type="button" class="btn btn-primary form-control mt-4 my-2">Find My Password</button>
	</div>
	
	<div id="check-code-box" class="col-3 pb-5 d-none">
		<h2 class="text-center font-weight-bold mt-5 mb-4">Find Password</h2>
		
		<div class="mt-3">
			<h5 class="font-weight-bold mt-3">Verification Code</h5>
			<input type="text" id="code" class="form-control">
			<small id="code-desc">The code has been sent.</small>
		</div>
	
		<button id="check-code-btn" type="button" class="btn btn-primary form-control mt-4 my-2">Check</button>
	</div>
	
</div>

<script>
	$(document).ready(function() {
		var randomChar = "";
		var id = "";
		
		$("#find-pw-btn").on("click", function() {
			id = $("#id").val().trim();
			let email = $("#email").val().trim();
			
			if (!id) {
				alert("Please enter your ID.");
				return;
			}
			if (!email) {
				alert("Please enter your Email.");
				return;
			}
			
			$.ajax({
				type:"post"
				,url:"/user/findPw"
				,data:{"id":id, "email":email}
				
				,success:function(data) {
					if (data.code == 200) {
						$("#find-pw-box").addClass("d-none");
						$("#check-code-box").removeClass("d-none");
						randomChar = data.randomChar;
					}
					else if(data.code == 300) {
						alert(data.message);
						location.reload();
					}
				}
				,error:function(request, status, error) {
					alert("Failed to find user. Please contact through Customer service.");
				}
			})
		});
		
		$("#check-code-btn").on("click", function() {
			let code = $("#code").val();
			if (!code) {
				alert("Please enter the code.")
				return;
			}
			
			if(code != randomChar) {
				$("#code-desc").val("Verification code does not match.")
				return;
			}
			
			$.ajax({
				type:"post"
				,url:"/user/updateTempPw"
				,data:{"id":id}
				,success:function(data) {
					if (data.code == 200) {
						alert("Your new temporary password has been sent. Please Log in again and update the password.");
						location.href = "/log-in";
					}
				}
				,error:function(request, status, error) {
					alert("Failed to create temporary password. Please contact through Customer service.");
				}
			})
			
		})
		
	})
</script>