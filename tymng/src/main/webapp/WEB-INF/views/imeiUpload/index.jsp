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
        });
        function importImei() {
            $('#callback').html("");
            var channelId = $("#channelId").val();
            if (channelId == null || channelId == "") {
                $.messager.alert('错误', "请选择仓库！");
                return;
            }
            $("body").showLoading();
            $('#fm').form('submit', {
                url: '<%=basePath%>/tymng/imeiUpload/exportImei.do',
                success: function (result) {
                    $("body").hideLoading();
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

        function showChannelDialog() {
            $('#channeldlg').dialog('open').dialog('setTitle', '选择仓库');
            $('#channeldg').datagrid({
                width: 'auto',
                height: 'auto',
                fitColumns: true,
                striped: true,
                url: '<%=basePath%>/tymng/zipUpload/channelList',
                queryParams: {groupId: 1},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                //idField: 'channelId',
                columns: [
                    [
                        {field: 'groupName', title: '渠道组名称', align: 'center', width: 150},
                        {field: 'channelName', title: '仓库名称', align: 'center', width: 150},
                        {field: 'channelId', hidden: 'true'},
                        {field: 'ck', checkbox: true}
                    ]
                ]
            });
        }
        function searchChannelEvt() {
            var value = $('#searchChannelValue').val();
            $('#channeldg').datagrid({
                url: "<%=basePath%>/tymng/zipUpload/channelList",
                queryParams: {channelNameCondition: value}
            });
        }
        function selectChannel() {
            var rows = $('#channeldg').datagrid('getSelections');
            for (var i = 0; i < rows.length; i++) {
                $("#channelId").val(rows[i].channelId);
                $("#channelName").val(rows[i].channelName);
            }
            $('#channeldlg').dialog('close')
        }
        function downloadImeiTemplate() {
            window.location.href = "<%=basePath%>/tempExport/ImeiUploadFile.xls";
        }
    </script>
</head>
<body>
<div id="toolBar">
    <div>
        <form id="fm" method="post" enctype="multipart/form-data" novalidate>
            <table>
                <tr>
                    <td align="left" colspan="2">
                        <a href="javascript:void(0)" class="easyui-linkbutton"
                           onclick="downloadImeiTemplate()">下载模板文件</a>
                    </td>
                </tr>
                <tr>
                    <td>选择仓库：</td>
                    <td align="left">
                    <input type="text" name="channelName" id="channelName" placeholder="选择仓库" readonly="readonly"
                           onclick="showChannelDialog()"/>
                    <input type="hidden" name="channelId" id="channelId"/>
                </td>
                </tr>
                <tr>
                    <td align="left" colspan="2">
                        <font color="red">注意：Imei上传的最大值为1000</font>
                    </td>
                </tr>
                <tr>
                    <td>选择日期：</td>
                    <td align="left">
                        <input type="text" name="processDateStr" id="processDate" placeholder="日期"/>
                    </td>
                </tr>
                <tr>
                    <td>选择文件：</td>
                    <td align="left">
                    <input type="file" name="excelFile"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td align="left">
                    <a id="importImeiBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="importImei()">导入imei</a>
                </td>
            </tr>
        </table>
        </form>
    </div>
</div>
<div id="channeldlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#channeldlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchChannelValue" id="searchChannelValue" placeholder="仓库名称"/>
                    </td>
                    <td align="center">
                        <a id="searchChannelBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchChannelEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="channeldg"></div>
</div>
<div id="channeldlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="javascript:selectChannel();">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#channeldlg').dialog('close')">关闭</a>
</div>
<div id="callback"></div>
</body>
</html>