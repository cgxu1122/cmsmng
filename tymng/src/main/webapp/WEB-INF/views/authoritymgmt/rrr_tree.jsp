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
    tree.loadXML("<%=basePath%>/tymng/rrr/loadrole", function () {
        $(".standartTreeRow").css({"text-align": "left"});
    });
//    var  s = '{"id":0,"item":[{"id":1,"item":[{"id":2,"open":"1","text":"2-"},{"id":3,"item":[{"id":5,"item":[{"id":7,"open":"1","text":"7-"}],"open":"1","text":"5-"},{"id":6,"open":"1","text":"6-"}],"open":"1","text":"3-"},{"id":4,"open":"1","text":"4-"}],"open":"1","text":"1-"}]}';
//    var result = eval('(' + s + ')');
//    result = eval('(' + result + ')');
//    tree.loadJSONObject({"id":0,"item":[{"id":1,"item":[{"id":2,"open":"1","text":"2-"},{"id":3,"item":[{"id":5,"item":[{"id":7,"open":"1","text":"7-"}],"open":"1","text":"5-"},{"id":6,"open":"1","text":"6-"}],"open":"1","text":"3-"},{"id":4,"open":"1","text":"4-"}],"open":"1","text":"1-"}]});
    tree.setOnClickHandler(function () {
        parent.frames['mainFrame'].location = "<%=basePath%>/tymng/rrr/showdetail/" + tree.getSelectedItemId();
    });

</script>
</body>
</html>