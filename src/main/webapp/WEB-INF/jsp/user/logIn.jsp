<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-3">
		<h2 class="text-center font-weight-bold mt-5 mb-4">Log In</h2>
		
		<form method="post" action="/user/logIn" id="log-in-form">
			<h5 class="font-weight-bold">ID</h5>
			<input type="text" id="id" name="id" class="form-control">
	
			<h5 class="font-weight-bold mt-3">PASSWORD</h5>
			<input type="password" id="password" name="password" class="form-control">
			
			<div class="d-flex justify-content-end">
				<a href="/find-id">Find ID</a>
				&nbsp; / &nbsp;
				<a href="/find-pw">Passsword</a>
			</div>
			
			<button id="log-in-btn" type="submit" class="btn btn-primary form-control mt-4 my-2">Log In</button>
		</form>
		
		<a id="mv-sign-up-btn" href="/sign-up" class="btn btn-secondary form-control mt-2">Sign Up</a>
	</div>
</div>


<script>
	$(document).ready(function() {
		$("#log-in-form").on("submit", function(e) {
			e.preventDefault();
			
			let id = $("#id").val().trim();
			let password = $("#password").val().trim();
			
			if (!id) {
				alert("Please enter ID.");
				return false;
			}
			if (!password) {
				alert("Please enter password.");
				return false;
			}
			
			let url = $(this).attr("action");
			
			$.ajax({
				type:"post"
				,url:url
				,data:{"id":id, "password":password}
				
				,success:function(data) {
					if (data.code == 200) {
						alert("Welcome!");
						location.href = "/home";
					}
					else {
						alert(data.error_message);
					}
				}
				,error:function(request, status, error) {
					alert("Failed to log in. Please contact through Customer service.");
				}
			});
		});
	});
</script>