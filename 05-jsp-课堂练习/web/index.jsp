<%@ page import="top.bingcu.Student" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020-11-16
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>BinGCU</title>

  </head>
  <body>
  <%! Student s = new Student("1913420129", "BinGCU"); %>
  <h1>姓名：<%=s.getName()%></h1>
  <h1>学号：<%=s.getId()%></h1>
  </body>
</html>
