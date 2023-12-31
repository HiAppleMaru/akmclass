<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("UTF-8"); //폰트 깨지지 않게 인코딩 추가.

	String bookid = request.getParameter("bookid");
	String bookname = request.getParameter("bookname");
	String publisher = request.getParameter("publisher");
	String price = request.getParameter("price"); //request.getParameter로 소환하면 모두 String으로 되기 때문에
																		// 필요시 형변환 해야함.

	Class.forName("oracle.jdbc.OracleDriver");
	
	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "madang", "madang");
			
	PreparedStatement stmt = conn.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?)"); 
	//statement 와 preparedstatement -> statement 는 변함 없을때, preparestatement 는 ?, ?와 같이 변화해야될 것이 있을때...
	
	stmt.setString(1, bookid);
	stmt.setString(2, bookname);
	stmt.setString(3, publisher);
	stmt.setString(4, price);
	
	int result = stmt.executeUpdate();
	
	stmt.close();
	
	conn.close();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<div>
		<div>
			<h3><%= result %> 건이 등록 되었습니다.</h3>
		</div>
		<div>
			<a href="list">목록</a>
		</div>
	</div>
</body>
</html>