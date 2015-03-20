<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Books</title>
</head>
<body>
  <%
      String ctxPath = request.getContextPath();
  %>
  <h2>List of Books</h2>
  <ul>
    <li><a href="<%=ctxPath%>">Clojureによる、初めての関数型プログラミング</a></li>
    <li><a href="<%=ctxPath%>">Clojureでトップダウン開発</a></li>
    <li><a href="<%=ctxPath%>">Clojure Essentials</a></li>
    <li><a
      href="<%= ctxPath %>/coverpage?key=${initParam.jslKey}"
    >${initParam.jslTitle}</a></li>
  </ul>
</body>
</html>