<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
	<body>	
		<c:if test="${list.size() > 0}">
			<c:forEach items="${list}" var="map">
				<c:if test="${map.image == 'true' }">
					<img width="300px" src="/file/${map.name}"/>
				</c:if>
				<c:if test="${map.image == 'false' }">
					${map.name}
					<button onclick="location.href='download?fileName=${map.name}'"> 다운로드</button>
				</c:if>			
				<button onclick="location.href='delete?file=${map.name}'"> 삭제</button>
				<hr>
			</c:forEach>
		</c:if>
		<c:if test="${list.size()==0}">업로드된 사진이 없습니다.</c:if>
		<p><a href="./">돌아가기</a></p>
	</body>
	<script>
	
	</script>
</html>