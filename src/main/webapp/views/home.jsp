<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<style>

</style>
</head>
<body>
	<p><a href="fileList">파일 리스트 보기</a></p>
	<h3>단일 파일 업로드</h3>
	<form action="upload" method="post" enctype="multipart/form-data">
		<input type="file" name="uploadFile"/>
		<input type="submit" value="전송"/>
	</form>
	<hr/>
	<h3>멀티 파일 업로드</h3>
	<form action="multiUpload" method="post" enctype="multipart/form-data">
		<input type="file" multiple="multiple" name="files"/>
		<input type="submit" value="전송"/>
	</form>
</body>
<script>

</script>
</html>