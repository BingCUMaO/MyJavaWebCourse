<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		for (int i = 1;i < 10; i++) {
			for (int j = 1;j <= i; j++) {
				out.println(j);
			}
			out.println("<br/>");
		}
	%>
</body>
</html>