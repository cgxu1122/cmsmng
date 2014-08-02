<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/common/header.jsp" %>
    <title></title>
    <script type="text/javascript">
        <!--
        function initPage() {
            $("#searchbtn").click(function () {
                search();
            });

            $('#dgg').datagrid({
                height: '522',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/auth/productauth/userList',
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                fitColumns: true,
                rownumbers: true,
                fit: true,
                columns: [
                    [
                        {field: 'loginName', title: '登录名', align: 'center', width: 110},
                        {field: 'realName', title: '真实姓名', align: 'center', width: 110},
                        {field: 'userId', hidden: true}
                    ]
                ],
                toolbar: "#toolBar"
            });
        }


        /**
         *根据条件查询查询staff列表
         */
        function search() {
            var value = $('#searchValue').val();
            $('#dgg').datagrid({
                url: "<%=basePath%>/tymng/auth/productauth/userList",
                queryParams: { 'searchValue': value}
            });
        }

        $(document).ready(function () {
            initPage();
            $('#dgg').datagrid({onClickRow: function () {
                var row = $('#dgg').datagrid('getSelected');
                parent.frames['mainFrame'].location = "<%=basePath%>/tymng/auth/productauth/productIndex?userId=" + row.userId;
            }});
        });
        -->
    </script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="searchValue" id="searchValue" placeholder="登录名/真实姓名"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dgg"></div>
</body>
</html>
