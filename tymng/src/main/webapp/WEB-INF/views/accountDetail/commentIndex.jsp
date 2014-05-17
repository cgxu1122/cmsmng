<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: luyujian
  Date: 14-1-21
  Time: 下午8:33
  To change this template use File | Settings | File Templates.

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/common/header.jsp" %>
    <title></title>
</head>
<body>--%>
<div id="commentGrid"></div>

<script>
    $(function () {
        renderDataGrid();
    });

    function renderDataGrid() {
        $("#commentGrid").datagrid({
            url: "<%=basePath%>account/queryCommentList?userId=" + mUserId,
            loadMsg: "loading data...",
            pagination: false,
            rownumbers: false,
            pageList: [1, 10, 20],
            columns: [
                [
                    {field: 'id', title: 'ID', align: 'center', width: 100},
                    {field: 'loanId', title: '被评论交易号', align: 'center', width: 100},
                    {field: 'content', title: '评论内容', align: 'center', width: 250},
                    {field: 'createTime', title: '评论时间', align: 'center', width: 250}

                ]
            ]
        })
    }
</script>
<%--
</body>
</html>
--%>