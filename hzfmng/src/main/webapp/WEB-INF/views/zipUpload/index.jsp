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
            $("#processDate").datebox({
                value: getCurrrentDateStr()
            });
            var d = new Date();
            var nowDate = d.getFullYear() + '年' + (d.getMonth() + 1) + '月' + d.getDate() + '日';
            d.setDate(d.getDate() - 3);
            var m = d.getMonth() + 1;
            var oldDate = d.getFullYear() + '年' + m + '月' + d.getDate() + '日';
            $("#datetagmsg").html("日期只能选择" + oldDate + "到" + nowDate);
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
                        仓库名称：
                    </td>
                    <td align="left">
                        ${channelName}
                    </td>
                </tr>
                <tr>
                    <td align="left" colspan="2">
                        <font color="red" id="datetagmsg"></font>
                    </td>
                </tr>
                <tr>
                    <td>选择日期:</td>
                    <td align="left">
                        <input type="text" name="processDateStr" id="processDate" placeholder="日期"/>
                    </td>
                </tr>
                <tr>
                    <td>选择文件:</td>
                    <td align="left">
                        <input type="file" name="zipFile" style="width: 150px"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td align="left">
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