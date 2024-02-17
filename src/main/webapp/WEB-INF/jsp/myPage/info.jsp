<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-8 pl-5">
	<jsp:include page="../user/user.jsp" />
	<button id="update-info-btn" type="button" class="btn btn-primary form-control mt-4">가입하기</button>
</div>

<script>
	$(document).ready(function() {
		let duplicateId = true;
		let idChecked = false;
		let passwordReg = false;
		let passwordChecked = false;
		
		$("#id").on("input", function() {
			let id = $(this).val();
	
	        $("#id-desc").removeClass("text-danger");
	        $("#id-desc").removeClass("text-success");
			
			if (id.length < 4) {
				$("#id-desc").text("아이디를 4자 이상 입력해주세요.");
				$("#id-desc").addClass("text-danger");
				
				idChecked = false;
				return;
			}
			
			if (!idChecked) {
				$("#id-desc").text("아이디 중복 확인을 실행해주세요.");
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
							$("#id-desc").text("사용 가능한 아이디입니다.");
							$("#id-desc").removeClass("text-danger");
							$("#id-desc").addClass("text-success");
							
							idChecked = true;
						}
					}
					else {
						alert(data.error_message);
					}
				}
				,error:function(request, status, error) {
					alert("아이디 중복 확인에 실패했습니다. 관리자에게 문의해주세요.")
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
	        	$("#pw-desc").text("비밀번호를 8자 이상 입력해주세요.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	        	return;
	        }
	        if (pw.search(/\s/) != -1) {
	        	$("#pw-desc").text("비밀번호는 공백 없이 입력해주세요.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	            return;
	        }
	        
	        if (!reg.test(pw)) {
	        	$("#pw-desc").text("비밀번호는 숫자/대문자/소문자/특수문자를 포함해야 합니다.");
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
				$("#pwc-desc").text("비밀번호가 일치하지 않습니다.");
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
		
		$("#update-info-btn").on("click", function() {
			let loginId = $("#id").val().trim();
			let password = $("#password").val().trim();
			let passwordCheck = $("#passwordCheck").val().trim();
			let nickname = $("#nickname").val().trim();
			let name = $("#name").val().trim();
			let phoneNumberFirst = $("#phone-number-first > option:selected").val();
			let phoneNumberSecond = $("#phone-number-second").val().trim();
			let phoneNumberThird = $("#phone-number-third").val().trim();
			let phoneNumber = phoneNumberFirst + phoneNumberSecond + phoneNumberThird;
			let email = $("#email").val().trim();
			
			if (loginId) {
				if (duplicateId) {
					alert("아이디 중복 확인을 실행해주세요.");
					return;
				}
				if (!idChecked) {
					alert("사용 불가능한 아이디입니다.");
					return;
				}
			}
			
			if ((password || passwordCheck) && !passwordChecked) {
				alert("사용 불가능한 비밀번호입니다.");
				return;
			}
			if ((phoneNumberSecond && !phoneNumberThird) || (!phoneNumberSecond && phoneNumberThird)) {
				alert("전화번호를 입력해주세요.");
				return;
			}
			
			if (!phoneNumberSecond && !phoneNumberThird) {
				phoneNumber = "";
			}
			console.log()
			let formData = new FormData();
			formData.append("loginId", loginId);
			formData.append("password", password);
			formData.append("nickname", nickname);
			formData.append("name", name);
			formData.append("phoneNumber", phoneNumber);
			formData.append("email", email);
			
			$.ajax({
				type:"post"
				,url:"/user/update"
				,data:formData
				, enctype:"multipart/form-data" // 파일 업로드를 위한 필수 설정
					, processData:false // 파일 업로드를 위한 필수 설정
					, contentType:false //
				
				,success:function(data) {
					if (data.code == 200) {
						alert("회원 정보 수정에 성공했습니다.");
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in"
						}
					}
				}
				,error:function(request, status, error) {
					alert(data.error_message);
				}
			});
		})
	});
</script>