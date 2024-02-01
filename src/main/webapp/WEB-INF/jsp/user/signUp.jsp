<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-7">
		<h2 class="text-center font-weight-bold my-5">회원가입</h2>

		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3">아이디</h5>
			<input type="text" id="id" class="form-control col-5">
			<small id="id-desc" class="d-flex align-items-center pl-3">아이디를 4자 이상 입력해주세요.</small>
		</div>

		<div class=" d-flex mb-3">
			<h5 class="font-weight-bold col-3">비밀번호</h5>
			<input type="password" id="password" class="form-control col-5">
			<small id="pw-desc" class="d-flex align-items-center pl-3">대문자 소문자 특수문자 추가</small>
		</div>

		<div class=" d-flex mb-3">
			<h5 class="font-weight-bold col-3">비밀번호 확인</h5>
			<input type="password" id="passwordCheck" class="form-control col-5">
			<small id="pwc-desc" class="d-flex align-items-center pl-3">비밀번호가 일치하지 않습니다.</small>
		</div>

		<div class=" d-flex mb-3">
			<h5 class="font-weight-bold col-3">닉네임</h5>
			<input type="text" id="nickname" class="form-control col-5">
			<small id="pwc-desc" class="d-flex align-items-center pl-3">비밀번호가 일치하지 않습니다.</small>
		</div>

		<div class=" d-flex mb-3">
			<h5 class="font-weight-bold col-3">이름</h5>
			<input type="text" id="name" class="form-control col-5">
		</div>

		<div class=" d-flex mb-3">
			<h5 class="font-weight-bold col-3">전화번호</h5>
			<div class="d-flex justify-content-between col-5 p-0 ">
				<select id="phone-number-first" class="form-control col-3 mr-2">
					<option selected>010</option>
					<option>011</option>
					<option>016</option>
				</select>
				
				<input type="text" id="phone-number-second" class="form-control col-4 mr-2">
				<input type="text" id="phone-number-third" class="form-control col-4">
			</div>
		</div>

		<div class=" d-flex mb-3">
			<h5 class="font-weight-bold col-3">이메일</h5>
			<input type="text" id="email" name="email" class="form-control col-5">
		</div>

		<button id="sign-up-btn" type="button" class="btn btn-primary form-control mt-4">가입하기</button>
	</div>
</div>


<script>
	$(document).ready(function() {
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
				alert("아이디를 입력해주세요.");
				return;
			}
			if (!password || !passwordCheck) {
				alert("비밀번호를 입력해주세요.");
				return;
			}
			if (!nickname) {
				alert("닉네임을 입력해주세요.");
				return;
			}
			if (!name) {
				alert("이름을 입력해주세요.");
				return;
			}
			if (!phoneNumberFirst || !phoneNumberSecond || !phoneNumberThird) {
				alert("전화번호를 입력해주세요.");
				return;
			}
			if (!email) {
				alert("이메일을 입력해주세요.");
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
						alert("회원 가입에 성공했습니다! 로그인해주세요.");
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