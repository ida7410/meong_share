<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-8 pl-5">
	<div id="pw-box" class="d-flex mb-3">
		<h5 class="font-weight-bold col-3 d-flex align-items-center">Current Password</h5>
		<input type="password" id="og-password" class="form-control col-5">
	</div>
	
	<div id="pw-box" class="d-flex mb-3">
		<h5 class="font-weight-bold col-3 d-flex align-items-center">New Password</h5>
		<input type="password" id="password" class="form-control col-5">
		<div id="pw-desc" class="small d-flex align-items-center pl-3"></div>
	</div>
	
	<div id="pw-check-box" class="d-flex mb-3">
		<h5 class="font-weight-bold col-3 d-flex align-items-center">Retype Password</h5>
		<input type="password" id="passwordCheck" class="form-control col-5">
		<div id="pwc-desc" class="small d-flex align-items-center pl-3"></div>
	</div>
	
	<button id="update-pw-btn" type="button" class="btn btn-primary form-control mt-4">Update Password</button>
</div>


<script>
	$(document).ready(function() {

		let passwordReg = false;
		let passwordChecked = false;
		

		$("#password").on("input", function() {
	        let pw = $("#password").val();
	        let reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
	        
	        $("#pw-desc").removeClass("text-danger");
	        $("#pw-desc").removeClass("text-success");
	        $("#passwordCheck").val("");
			
	        if (pw.length < 8) {
	        	$("#pw-desc").text("Password should be longer than 7 characters.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	        	return;
	        }
	        if (pw.search(/\s/) != -1) {
	        	$("#pw-desc").text("Password must not include spaces.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	            return;
	        }
	        
	        if (!reg.test(pw)) {
	        	$("#pw-desc").text("Password must include numbers/uppsercase/lowercase/special characters.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	        	return;
	        } else {
	        	$("#pw-desc").text("Available Passwrod!");
	        	$("#pw-desc").addClass("text-success");
	        	
	        	passwordReg = true;
	        }
		});
		
		$("#passwordCheck").on("input", function() {
			let pw = $("#password").val();
			let pwc = $(this).val();
			
			$("#pwc-desc").removeClass("text-danger");
			
			if (pw != pwc) {
				$("#pwc-desc").text("Password does not match.");
				$("#pwc-desc").addClass("text-danger");
				
				passwordChecked = false;
				return;
			}
			else if (passwordReg){
				$("#pwc-desc").text("");
				$("#pwc-desc").removeClass("text-danger");
				
				passwordChecked = true;
			}
		});
		
		$("#update-pw-btn").on("click", function() {
			let og_password = $("#og-password").val();
			let password = $("#password").val().trim();
			let passwordCheck = $("#passwordCheck").val().trim();
			
			if (!og_password || !password || !passwordCheck) {
				alert("Please enter the password.");
				return;
			}
			if (!passwordChecked) {
				alert("Unavailable Password");
				return;
			}

			$.ajax({
				type:"post"
				,url:"/user/update-pw"
				,data:{"password":og_password, "newPassword":password}
				
				,success:function(data) {
					if (data.code == 200) {
						alert("You have updated your password. Please log in again.");
						location.href = "/log-out"
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.reload();
						}
					}
				}
				,error:function(request, status, error) {
					alert("Failed to update the password. Please contact through Customer service.");
				}
			});
		})
	})
	
</script>