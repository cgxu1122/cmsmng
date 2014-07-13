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
        });

        function importImei() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/hzfmng/imeiQuery/exportImei.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.alert('错误', result.errorMsg);
                    } else {
                        initPage();
                    }
                }
            });
        }

        function initPage() {
            var startDate = $('#startDate').datebox('getValue');
            var endDate = $('#endDate').datebox('getValue');
            $('#dg').datagrid({
                fitColumns: true,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/hzfmng/imeiQuery/list.do',
                queryParams: {startDate: startDate, endDate: endDate},
                loadMsg: '数据加载中请稍后……',
                rownumbers: true,
                columns: [
                    [
                        {field: 'imei', title: 'imei', align: 'center', width: 200},
                        {field: 'modelName', title: '机型名称', align: 'center', width: 200},
                        {field: 'channelName', title: '仓库名称', align: 'center', width: 200},
                        {field: 'processDate', title: '安装日期', align: 'center', width: 200,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'uploadDate', title: '上传日期', align: 'center', width: 200,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        }
                    ]
                ]
            });
        }
        function exportData() {
            window.location.href = "<%=basePath%>/hzfmng/imeiQuery/exportData.do";
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
                       onclick="importImei()">导入imei</a>
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