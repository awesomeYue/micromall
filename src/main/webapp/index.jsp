<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<body>
<h2>This is my mall</h2>
SpringMVC上传文件
<form name="form1" action="${pageContext.request.contextPath}/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input name="upload_file" type="file"/>
    <input name="SpringMVC上传文件" type="submit"/>
</form>
富文本上传文件
<form name="form1" action="${pageContext.request.contextPath}/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input name="upload_file" type="file"/>
    <input name="富文本上传文件" type="submit"/>
</form>
</body>
</html>
