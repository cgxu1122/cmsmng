<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/common/header.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <link href="<%=basePath %>/plug/dhtmlxTree/codebase/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath %>/plug/dhtmlxTree/codebase/dhtmlxcommon.js"></script>
    <script type="text/javascript" src="<%=basePath %>/plug/dhtmlxTree/codebase/dhtmlxtree.js"></script>

</head>

<body>
<table>
    <tr>
        <td valign="top">
            <div id="treeboxbox_tree"></div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    tree = new dhtmlXTreeObject("treeboxbox_tree", "100%", "100%", 0);
    tree.setImagePath("<%=basePath%>/plug/dhtmlxTree/codebase/imgs/csh_bluebooks/");
    tree.loadXML("<%=basePath%>/role/loadrole", function () {
        $(".standartTreeRow").css({"text-align": "left"});
    });
    tree.setOnClickHandler(function () {
        parent.frames['mainFrame'].location = "<%=basePath%>/role/showdetail/" + tree.getSelectedItemId();
    });

</script>
</body>
</html>