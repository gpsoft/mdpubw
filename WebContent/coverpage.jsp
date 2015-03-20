<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
</head>
<body>
  <img src="${imgUrl}" alt="no cover image"
    style="max-width: 150px; height: auto; float: left;"
  />
  <form action="publish" method="post">
    <label><input type="checkbox" name="pull" value="1" />Pull
      from repo before publishing.</label>
    <input type="hidden" name="bookUrl" value="${bookUrl}" />
    <input type="hidden" name="IN-dir" value="${inDir}" />
    <input type="hidden" name="OUT-dir" value="${outDir}" />
    <%
        for (String subDir : (String[])request.getAttribute("subDirs")) {
    %>
    <input type="hidden" name="subDirs" value="<%=subDir%>" />
    <%
        }
    %>
    <input type="submit" value="Publish" />
  </form>
</body>
</html>