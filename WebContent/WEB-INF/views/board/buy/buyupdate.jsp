<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="/WEB-INF/views/layout/header.jsp" />

<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.js"></script>
<!-- summer note korean language pack -->
<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>


<script type="text/javascript">
$(document).ready(function() {
	
	$('#summernote').summernote({
		height: 300,          // 기본 높이값
	    minHeight: null,      // 최소 높이값(null은 제한 없음)
	    maxHeight: null,      // 최대 높이값(null은 제한 없음)
	    focus: true,          // 페이지가 열릴때 포커스를 지정함
	    lang: 'ko-KR'         // 한국어 지정(기본값은 en-US)
	});
	
	
	//작성버튼 동작
	$("#btnUpdate").click(function() {
		
		
		
		//form submit 수행
		$("form").submit();
	});
	
	//취소버튼 동작
	$("#btnCancel").click(function() {
		history.go(-1);
	});
});
</script>

<style>
#btnUpdate{
	width: 100px;
	height: 40px;
	background: #474e60;
	font-size: 13px;
	color: #fff;
	border: solid 2px;
	border-radius: 1px;
}
#btnCancel{
   width:100px;
   height: 40px;
   background: #a7a7a2;
   font-size: 13px;
   color: #fff;
   border: solid 2px;
   border-radius: 1px;
}
</style>





<h3>수정하기</h3>
<hr>

<div>
<form action="/buy/update" method="post" enctype="multipart/form-data">
<input type="hidden" name="boardno" value="${viewBoard.boardno }" />

<table class="table table-bordered">
<tr >
<td class="info" >아이디</td><td>${member_id }</td> 
<td class="info" >거래유형</td>
	<td>
	<select class="form-control " name="select" id="select" style="width:60%">
		<option value="direct" selected>직접 거래</option>
		<option value="delivery">택배 거래</option>
	</select> </td>
	<td class="info">거래가격 : <input type="text" name="price" style="width:30%"/> </td> 
</tr>
<tr><td class="info">닉네임</td><td colspan="4">${member_nick }</td>
	
	
</tr>
<tr><td class="info" style="width:30%">제목</td>

<td colspan="5"><input type="text" name="title" value="${viewBoard.title }" style="width:30%"/></td>




</tr>
<tr><td class="info" colspan="5">본문</td></tr>
	<td colspan="5">
	<textarea id="summernote" name="content" rows="10" cols="100">${viewBoard.content }</textarea>
</td>


<tr>

<td rowspan="4" style="font-size: 20px; text-align: center;"> <br>작성자 정보 </td>
<td colspan="5">${member_name } &nbsp;&nbsp; ${member_phone }</td>

</tr>

<tr>

<td colspan="4">${member_email }  개인정보보호를 위해 KH메일만 사용 가능합니다</td>

</tr>
<tr>
<td colspan="4"><label><input type="checkbox" name="phoneAgree" value="T"> 연락처 노출 동의 </label></td>

</tr>
</table>




<label>기존 썸네일 : </label>${boardFile.originName }<br>
<label>첨부파일 : <input type="file" name="file" /></label>

</form>
</div>



<div class="text-center">	
	<button type="button" id="btnUpdate">수정 적용</button>
	<button type="button" id="btnCancel">취소</button>
</div>





<c:import url="/WEB-INF/views/layout/footer.jsp" />





