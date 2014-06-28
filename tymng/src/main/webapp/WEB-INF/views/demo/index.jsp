<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/common/header.jsp" %>
    <title>Demo</title>
    <script type="text/javascript">

        $(document).ready(function () {
            initPage();
        });


        function addrow() {
            alert("addrow");
        }

        function updaterow() {
            alert("updaterow");
        }


        function deleterow() {
            alert("deleterow");
        }

        function search() {
            alert("deleterow");
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 300,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/demo/list/',
                //queryParams:{},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'itemid', title: '来文号', align: 'center', width: 100}
                    ]
                ],
                pagination: true,
                toolbar: [
                    {
                        text: '新增',
                        iconCls: 'icon-add',
                        handler: function () {
                            addrow();
                        }
                    },
                    '-',
                    {
                        text: '修改',
                        handler: function () {
                            updaterow();
                        }
                    },
                    '-',
                    {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () {
                            deleterow();
                        }
                    }
                ]
            });
        }
    </script>
</head>
<body>
<form id="queryForm" style="margin:10;text-align: center;">
    <table width="100%">
        <tr>
            <td>文件号：<input name="name" style="width: 200"></td>
            <td align="center"><a href="#" onclick="search();" class="easyui-linkbutton" iconCls="icon-search">查询</a>
            </td>
        </tr>
    </table>
</form>
<div id="dg"></div>
</body>
</html>