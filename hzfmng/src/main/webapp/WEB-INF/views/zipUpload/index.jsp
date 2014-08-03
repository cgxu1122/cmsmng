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
        });
        function importZip() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/hzfmng/zipUpload/importZip.do',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (!result.ret) {
                        $.messager.alert('错误', result.errorMsg);
                    } else {
                        var html = "保存成功个数：" + result.Success + "<br>" +
                                "参数校验失败个数：" + result.Invalid + "<br>" +
                                "imei重复个数：" + result.Repeat + "<br>" +
                                "处理失败个数：" + result.Failure + "<br>";
                        $('#callback').html(html);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div id="toolBar">
    <div>
        <form id="fm" method="post" enctype="multipart/form-data" novalidate>
            <table>
                <tr>
                    <td>
                        <input type="text" name="channelName" value="${channelName}" readonly/>
                    </td>
                    <td>
                        <input id="dd"/>
                    </td>
                    <td>
                        <input type="file" name="zipFile" style="width: 150px"/>
                    </td>
                    <td align="center">
                        <a id="importImeiBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                           onclick="importZip()">导入zip</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="callback">

</div>
</body>
</html>