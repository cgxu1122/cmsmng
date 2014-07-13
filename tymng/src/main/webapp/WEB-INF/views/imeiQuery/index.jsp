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
            initPage(null);
        });

        var imeiPath;
        function importImei() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/tymng/imeiQuery/list.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.alert('错误', result.errorMsg);
                    } else {
                        imeiPath = result.imeiPath;
                        initPage(result.rows);
                    }
                }
            });
        }

        function initPage(rows) {
            $('#dg').datagrid({
                fitColumns: true,
                striped: true,
                singleSelect: true,
                data: rows,
                loadMsg: '数据加载中请稍后……',
                rownumbers: true,
                columns: [
                    [
                        {field: 'imei', title: 'imei', align: 'center', width: 200},
                        {field: 'modelName', title: '机型名称', align: 'center', width: 200},
                        {field: 'channelName', title: '仓库名称', align: 'center', width: 200},
                        {field: 'processTime', title: '安装日期', align: 'center', width: 200,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        }
                    ]
                ]
            });
        }
        function exportData() {
            $("body").showLoading();
            $.ajax({
                url: "<%=basePath%>/tymng/imeiQuery/exportData.do?imeiPath=" + imeiPath,
                success: function (result) {
                    $("body").hideLoading();
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.alert('错误', result.errorMsg);
                    } else {
                        window.location.href = "<%=basePath%>/tymng/downloadFile/downloadFile?path=" + result.path;
                    }
                }
            });
        }
    </script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <form id="fm" method="post" enctype="multipart/form-data" novalidate>
                        <input type="file" name="excelFile"/>
                    </form>
                </td>
                <td align="center">
                    <a id="importImeiBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="importImei()">根据imei文件查询</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportData()">导出</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>

</body>
</html>