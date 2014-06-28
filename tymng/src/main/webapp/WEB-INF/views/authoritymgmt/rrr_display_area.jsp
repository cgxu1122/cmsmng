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
    tree.enableCheckBoxes(1);
    tree.enableThreeStateCheckboxes(true);
    tree.loadXML("<%=basePath%>/tymng/rrr/loadroleresourceref/${id}", function () {
        $(".standartTreeRow").css({"text-align": "left"});
    });


    function authorization() {
        var checkedNodes = tree.getAllCheckedBranches();
        var roleId = $('#roleId').val();
        <%--$.post('<%=basePath%>/rrr/authorization/' + roleId + '_' + checkedNodes, function (result) {--%>
        <%--if (result) {--%>
        <%--window.self.location.reload(true);--%>
        <%--} else {--%>
        <%--$.messager.alert('错误', result);--%>
        <%--}--%>
        <%--});--%>
        var _data = roleId + '_' + checkedNodes;
        var _url = '<%=basePath%>/tymng/rrr/authorization/';
        $.ajax({
            method: 'post',
            url: _url + _data,
            dataType: 'text',
            success: function (result) {
                alert(result);
            }
        });
    }
</script>
<div id="msg"></div>
<div id="dlg1-buttons">
    <input type="hidden" id="roleId" name="roleId" value="${id}"/>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="authorization()">授权</a>
</div>
</body>
</html>