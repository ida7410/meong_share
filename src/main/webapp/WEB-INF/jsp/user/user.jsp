<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">아이디</h5>
	<div class="inside-group col-5 p-0">
		<input type="text" id="id" class="form-control">
		<button type="button" id="check-dup-btn" class="inside-btn btn btn-info py-1 px-2">중복확인</button>
	</div>
	<div id="id-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div id="pw-box" class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">비밀번호</h5>
	<input type="password" id="password" class="form-control col-5">
	<div id="pw-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div id="pw-check-box" class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">비밀번호 확인</h5>
	<input type="password" id="passwordCheck" class="form-control col-5">
	<div id="pwc-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">닉네임</h5>
	<input type="text" id="nickname" class="form-control col-5">
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">이름</h5>
	<input type="text" id="name" class="form-control col-5">
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">전화번호</h5>
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

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">이메일</h5>
	<div class="inside-group col-5 p-0">
		<input type="text" id="email" name="email" class="form-control">
		<button type="button" id="send-code-btn" class="inside-btn btn btn-info py-1 px-2">인증하기</button>
	</div>
	<div id="email-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">이메일 인증번호</h5>
	<div class="inside-group col-5 p-0">
		<input type="text" id="email-check" name="email-check" class="form-control" disabled>
		<button type="button" id="email-check-btn" class="inside-btn btn btn-info py-1 px-2" disabled>확인</button>
	</div>
	<div id="email-check-desc" class="small d-flex align-items-center pl-3"></div>
</div>

