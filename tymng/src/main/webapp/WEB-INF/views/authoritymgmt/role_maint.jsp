<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="/common/header.jsp" %>
    <title></title>
</head>

<frameset rows="50,*" cols="*" frameborder="no" border="0" framespacing="0">
    <frame src="<%=basePath%>/role/top" name="topFrame" scrolling="no" noresize="noresize" id="topFrame"
           title="topFrame"/>
    <frameset cols="230,*" frameborder="yes" framespacing="0" border="1">
        <frame src="<%=basePath%>/role/tree" name="leftFrame" scrolling="yes" id="leftFrame" title="leftFrame"/>
        <frame src="<%=basePath%>/role/blank" name="mainFrame" id="mainFrame" title="mainFrame"/>
    </frameset>
</frameset>
<noframes>

    <body>
    </body>
</noframes>
</html>
