
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="/WEB-INF/views/layout/header.jsp" />

<script type="text/javascript">
$(document).ready(function() {
	
	//글쓰기 버튼 누르면 이동
	$("#btnWrite").click(function() {
		location.href="/board/boast/write";
	});
	
	$("#btnSearch").click(function() {
		location.href="/board/boast/list?select=" +$("#select").val()+"&search="+$("#search").val();
	});

	
});
</script>

<style type="text/css">
table, th {
	text-align: center;
}

/* 글쓰기 버튼 위치 설정 */
#pagingBox {
	position: relative;
}
#btnBox {
	top: 0;
	bottom: 0;
	
	
	margin-left: 1050px;
}




table, th {
	text-align: center;
}

.container {
	border-left : 1px solid #eee;
	border-right : 1px solid #eee;
}


</style>

<div class="wrap">

<div class="container" style="margin-top:40px;">
<div style="margin-top : 100px; ">

<h1>자랑게시판</h1>
</div>
<hr>

<div id="btnBox" >
	<button id="btnWrite" class="btn btn-primary">글쓰기</button>
</div>

<table class="table table-hover table-condensed" style="margin-top:20px;">

<thead style="background: #337AB7; color: white;" >
	<tr>
		<th style="width: 10%;">번호</th>
		<th style="width: 45%;">제목</th>
		<th style="width: 15%;">작성자</th>
		<th style="width: 10%;">조회수</th>
		<th style="width: 20%;">작성일</th>
	</tr>
</thead>

<tbody>
<c:forEach items="${list}" var="i">
	<tr>
		<td>${i.boast_board_no }</td>
		<td><a href="/board/boast/view?boast_board_no=${i.boast_board_no }">${i.boast_board_title }</a></td>
		<td>${i.boast_board_writer }</td>
		<td>${i.boast_board_hit }</td>
		<td><fmt:formatDate value="${i.boast_board_written_date }" pattern="yyyy-MM-dd" /></td>
	</tr>
</c:forEach>
</tbody>

</table>

<div id="pagingBox">
<c:import url="/WEB-INF/views/layout/boastpaging/paging.jsp" />


</div>

<div class="form-inline text-center">
<select class="form-control form-control-sm" name="select" id="select">
	<option value="boast_board_title" selected>제목</option>
	<option value="boast_board_content">내용</option>
</select>
	<input class="form-control" type="text" id="search" />
	<button id="btnSearch" class="btn">검색</button>
</div> 




  
<c:import url="/WEB-INF/views/layout/footer.jsp" />
