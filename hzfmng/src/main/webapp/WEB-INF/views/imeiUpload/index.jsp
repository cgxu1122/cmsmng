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
            $('#dd').datebox().datebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 3);
                    var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return d1 <= date && date <= d2;
                }
            });
        });

        function importImei() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/hzfmng/imeiUpload/exportImei.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.alert('错误', result.errorMsg);
                    } else {
                        $.messager.alert('提示', "导入成功！");
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
                    <input type="text" name="channelName" value="${channelName}" readonly/>
                    <input type="hidden" name="channelId" id="channelId" value="${channelId}"/>
                </td>
                <td>
                    <input id="dd"/>
                </td>
                <td>
                <form id="fm" method="post" enctype="multipart/form-data" novalidate>
                        <input type="file" name="excelFile"/>
                    </form>
                </td>
                <td align="center">
                    <a id="importImeiBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="importImei()">导入imei</a>
                </td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>