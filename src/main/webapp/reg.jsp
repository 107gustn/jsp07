<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>reg.jsp<br>
	<% request.setCharacterEncoding("utf-8"); %>
	<jsp:useBean id="dto" class="testBoard.TestDTO" />
	<jsp:setProperty property="*" name="dto" /> <!-- 넘어온 모든 값을 dto에 자동으로 등록해줌 -->
	
	<jsp:useBean id="dao" class="testBoard.TestDAO" />
	${dao.insert(dto) } <!-- 사용자가 입력한 값 저장 -->
	<%
		response.sendRedirect("list.jsp"); /* 저장한 뒤 목록으로 이동 */
	%>

</body>
</html>