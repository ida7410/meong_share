<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-7">
		<h2 class="text-center font-weight-bold my-5">Sign Up</h2>
		<jsp:include page="user.jsp" />
		
		<button id="sign-up-btn" type="button" class="btn btn-primary form-control mt-4">Sign Up</button>
	</div>
</div>


<script>
	$(document).ready(function() {
		let duplicateId = true;
		let idChecked = false;
		let passwordReg = false;
		let passwordChecked = false;
		let emailChecked = false;
		let emailCode = "";
		
		$("#id").on("input", function() {
			let id = $(this).val();
	
	        $("#id-desc").removeClass("text-danger");
	        $("#id-desc").removeClass("text-success");
			
			if (id.length < 4) {
				$("#id-desc").text("ID must be longer than 3 characters");
				$("#id-desc").addClass("text-danger");
				
				idChecked = false;
				return;
			}
			
			if (!idChecked) {
				$("#id-desc").text("Please check ID duplicates");
				$("#id-desc").addClass("text-danger");
				
				idChecked = false;
				return;
			}
			
		});
		
		$("#check-dup-btn").on("click", function() {
			let id = $("#id").val().trim();
			
			$.ajax({
				type:"post"
				,url:"/user/check-duplicated-id"
				,data:{"id":id}
				
				,success:function(data) {
					if (data.code == 200) {
						duplicateId = data.isDuplicateId;
						if (!duplicateId){
							$("#id-desc").text("Available ID");
							$("#id-desc").removeClass("text-danger");
							$("#id-desc").addClass("text-success");
							
							idChecked = true;
						}
						else {
							$("#id-desc").text("ID already exists");
						}
					}
					else {
						alert(data.error_message);
					}
				}
				,error:function(request, status, error) {
					alert("Failed to check ID duplicates. Please contact through Customer service.")
				}
			})
		});
		
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
	        	$("#pw-desc").text("사용 가능한 비밀번호입니다.");
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
		
		$("#send-code-btn").on("click", function() {
			let email = $("#email").val().trim();
			if (!email) {
				alert("이메일을 입력해주세요.");
				return;
			}
			console.log(email)
			
			$.ajax({
				type:"post"
				,url:"/user/check-email-code"
				,data:{"email":email}
				,success:function(data) {
					if (data.code == 200) {
						$("#email-desc").text("Verification code has been sent!");
						$("#email-check").prop("disabled", false);
						$("#email-check-btn").prop("disabled", false);
						emailCode = data.randomChar;
					}
				}
				,error:function(request, status, error) {
					alert("Failed to send the code. Please send an inquiry.");
				}
			})
		})
		
		$("#email-check").on("input", function() {
			$("#email-check-desc").removeClass("text-danger");
	        $("#email-check-desc").removeClass("text-success");
	        $("#email-check-desc").text("");
	        emailChecked = false;
		})
		
		$("#email-check-btn").on("click", function() {
			let code = $("#email-check").val();
			
			$("#email-check-desc").removeClass("text-danger");
	        $("#email-check-desc").removeClass("text-success");
	        
			if (code != emailCode) {
				$("#email-check-desc").addClass("text-danger");
				$("#email-check-desc").text("Verification code does not match.");
				return;
			}
			
			$("#email-check-desc").addClass("text-success");
			$("#email-check-desc").text("Successfully verificed Email");
			
			emailChecked = true;
		})
		
		$("#sign-up-btn").on("click", function() {
			let id = $("#id").val().trim();
			let password = $("#password").val().trim();
			let passwordCheck = $("#passwordCheck").val().trim();
			let nickname = $("#nickname").val().trim();
			let name = $("#name").val().trim();
			let phoneNumberFirst = $("#phone-number-first > option:selected").val();
			let phoneNumberSecond = $("#phone-number-second").val().trim();
			let phoneNumberThird = $("#phone-number-third").val().trim();
			let phoneNumber = phoneNumberFirst + phoneNumberSecond + phoneNumberThird;
			let email = $("#email").val().trim();
			
			if (!id) {
				alert("Please enter ID.");
				return;
			}
			if (duplicateId) {
				alert("Please proceed with ID duplication checking.");
				return;
			}
			if (!idChecked) {
				alert("Unavailable ID");
				return;
			}
			if (!password || !passwordCheck) {
				alert("Please enter password.");
				return;
			}
			if (!passwordChecked) {
				alert("Unavailalbe password");
				return;
			}
			if (!nickname) {
				alert("Please enter your nickname.");
				return;
			}
			if (!name) {
				alert("Please enter your name.");
				return;
			}
			if (!phoneNumberFirst || !phoneNumberSecond || !phoneNumberThird) {
				alert("Please enter phonenumber.");
				return;
			}
			if (!email) {
				alert("Please enter Email.");
				return;
			}
			if (!emailChecked) {
				alert("Please proceed with Email verification.");
				return;
			}
			
			let formData = new FormData();
			formData.append("id", id);
			formData.append("password", password);
			formData.append("nickname", nickname);
			formData.append("name", name);
			formData.append("phoneNumber", phoneNumber);
			formData.append("email", email);
			
			$.ajax({
				type:"post"
				,url:"/user/signUp"
				,data:formData
				, enctype:"multipart/form-data" // 파일 업로드를 위한 필수 설정
					, processData:false // 파일 업로드를 위한 필수 설정
					, contentType:false //
				
				,success:function(data) {
					if (data.code == 200) {
						alert("Succuessfully sign up! You may proceed to Log in.");
						location.href = "/log-in"
					}
				}
				,error:function(request, status, error) {
					alert(data.error_message);
				}
			});
		})
	});
</script>