<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String left = request.getParameter("left");
	String op = request.getParameter("op");
	String right = request.getParameter("right");
	
	int result = 0;
	
	if("pls".equals(op))
		result = Integer.parseInt(left) + Integer.parseInt(right);
	else if("min".equals(op))
		result = Integer.parseInt(left) - Integer.parseInt(right);
	else if("mul".equals(op))
		result = Integer.parseInt(left) * Integer.parseInt(right);
	else if("div".equals(op))
		result = Integer.parseInt(left) / Integer.parseInt(right);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계산결과</title>
</head>
<body>
	<div>
		<div>
			<label>좌항:</label><%=left  %>
		</div>
		<div>
			<label>연산자:</label><%=op %>
		</div>
		<div>
			<label>우항:</label><%=right %>
		</div>
		<div>
			<label>계산 결과:</label><%=result %>
		</div>
		<div>
			<a href="CalcServlet">처음으로</a> <!-- 링크주소를 calcservlet으로 -->
		</div>
	</div>
</body>
</html>