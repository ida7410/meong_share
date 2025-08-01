<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">ID</h5>
	<div class="inside-group col-5 p-0">
		<input type="text" id="id" class="form-control">
		<button type="button" id="check-dup-btn" class="inside-btn btn btn-info py-1 px-2">Check Duplicates</button>
	</div>
	<div id="id-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div id="pw-box" class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">Password</h5>
	<input type="password" id="password" class="form-control col-5">
	<div id="pw-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div id="pw-check-box" class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">Retype Password</h5>
	<input type="password" id="passwordCheck" class="form-control col-5">
	<div id="pwc-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">Nickname</h5>
	<input type="text" id="nickname" class="form-control col-5">
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">Name</h5>
	<input type="text" id="name" class="form-control col-5">
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">Phonenumber</h5>
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
	<h5 class="font-weight-bold col-3 d-flex align-items-center">Email</h5>
	<div class="inside-group col-5 p-0">
		<input type="text" id="email" name="email" class="form-control">
		<button type="button" id="send-code-btn" class="inside-btn btn btn-info py-1 px-2">Verify</button>
	</div>
	<div id="email-desc" class="small d-flex align-items-center pl-3"></div>
</div>

<div class="d-flex mb-3">
	<h5 class="font-weight-bold col-3 d-flex align-items-center">Verificatino Code</h5>
	<div class="inside-group col-5 p-0">
		<input type="text" id="email-check" name="email-check" class="form-control" disabled>
		<button type="button" id="email-check-btn" class="inside-btn btn btn-info py-1 px-2" disabled>Check</button>
	</div>
	<div id="email-check-desc" class="small d-flex align-items-center pl-3"></div>
</div>

