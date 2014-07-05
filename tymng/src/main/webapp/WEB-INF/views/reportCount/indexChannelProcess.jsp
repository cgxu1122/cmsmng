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
    $("#startDate").datebox({
        value: getCurrrentDateStr()
    });
    $("#endDate").datebox({
        value: getCurrrentDateStr()
    });
    initPage();
});
function searchEvt() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    var ua = $('#ua').val();
    var channelId = $('#channelId').val();
    var deviceCode = $('#deviceCode').val();
    $('#dg').datagrid({
        url: "<%=basePath%>/tymng/reportCount/listLogStat",
        queryParams: {groupId: 2, startDate: startDate, endDate: endDate, ua: ua, channelId: channelId, deviceCode: deviceCode}
    });
}

function resetEvt() {
    $('#ua').val("");
    $('#modelName').val("");
    $('#channelId').val("");
    $('#channelName').val("");
    $('#deviceCode').val("");
}

function initPage() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    $('#dg').datagrid({
        width: 'auto',
        height: 'auto',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/reportCount/listLogStat',
        queryParams: {groupId: 2, startDate: startDate, endDate: endDate},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        columns: [
            [
                {field: 'processDate', title: '日期', align: 'center', width: 200,
                    formatter: function (value) {
                        if (value == null) {
                            return "合计"
                        }
                        return new Date(value).formate("yyyy-MM-dd");
                    }
                },
                {field: 'channelName', title: '渠道商名称', align: 'center', width: 200},
                {field: 'modelName', title: '机型全称', align: 'center', width: 200},
                {field: 'deviceCode', title: '设备编码', align: 'center', width: 200},
                {field: 'devicePrsDayNum', title: '装机数量', align: 'center', width: 200,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)'>" + value + "</a>";
                    }
                }
                /*{field: 'deviceUpdDayNum', title: '装机到达数量', align: 'center', width: 200,
                 formatter: function (value, row, index) {
                 return "<a href='javascript:void(0)'>" + value + "</a>";
                 }
                 },
                 {field: 'prsActiveTotalNum', title: '累计到达数量', align: 'center', width: 200,
                 formatter: function (value, row, index) {
                 //return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.processDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "')>"+value+"</a>";
                 return "<a href='javascript:void(0)'>" + value + "</a>";
                 }
                 },
                 {field: 'prsActiveValidNum', title: '有效到达数量', align: 'center', width: 200,
                 formatter: function (value, row, index) {
                 return "<a href='javascript:void(0)'>" + value + "</a>";
                 }
                 },
                 {field: 'prsActiveInvalidNum', title: '无效到达数量', align: 'center', width: 200,
                 formatter: function (value, row, index) {
                 return "<a href='javascript:void(0)'>" + value + "</a>";
                 }
                 },
                 {field: 'prsInvalidReplaceNum', title: '替换数量', align: 'center', width: 200,
                 formatter: function (value, row, index) {
                 return "<a href='javascript:void(0)'>" + value + "</a>";
                 }
                 },
                 {field: 'prsInvalidUninstallNum', title: '卸载数量', align: 'center', width: 200,
                 formatter: function (value, row, index) {
                 return "<a href='javascript:void(0)'>" + value + "</a>";
                 }
                 }*/
            ]
        ]
    });
}
function showDeviceDialog() {
    $('#devicedlg').dialog('open').dialog('setTitle', '选择设备');
    $('#devicedg').datagrid({
        width: 'auto',
        height: 'auto',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/deviceInfo/list',
        queryParams: {groupId: 2},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'deviceCode', title: '设备编码', align: 'center', width: 200},
                {field: 'action', title: '操作', align: 'center', width: 200,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectDevice('" + row.deviceCode + "')>选择</a>";
                    }
                }
            ]
        ]
    });
}
function searchDeviceEvt() {
    var value = $('#searchDeviceValue').val();
    $('#devicedg').datagrid({
        url: "<%=basePath%>/tymng/deviceInfo/list",
        queryParams: {deviceCodeCondition: value, groupId: 2}
    });
}
function selectDevice(deviceCode) {
    $("#deviceCode").val(deviceCode);
    $('#devicedlg').dialog('close');
}
function showModelDialog() {
    $('#modeldlg').dialog('open').dialog('setTitle', '选择机型');
    $('#modeldg').datagrid({
        width: 'auto',
        height: 'auto',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/modelInfo/list',
        queryParams: {groupId: 2},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'modelName', title: '机型名称', align: 'center', width: 150},
                {field: 'ua', title: 'UA', align: 'center', width: 150},
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectModel('" + row.ua + "','" + row.modelName + "')>选择</a>";
                    }
                }
            ]
        ]
    });
}
function searchModelEvt() {
    var value = $('#searchModelValue').val();
    $('#modeldg').datagrid({
        url: "<%=basePath%>/tymng/modelInfo/list",
        queryParams: {modelNameCondition: value, groupId: 2}
    });
}
function selectModel(ua, modelName) {
    $("#modelName").val(modelName);
    $("#ua").val(ua);
    $('#modeldlg').dialog('close');
}
function showChannelDialog() {
    $('#channeldlg').dialog('open').dialog('setTitle', '选择仓库');
    $('#channeldg').datagrid({
        width: 'auto',
        height: 'auto',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/channelInfo/listAll',
        queryParams: {groupId: 2},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'channelName', title: '仓库名称', align: 'center', width: 150},
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectChannel('" + row.channelId + "','" + row.channelName + "')>选择</a>";
                    }
                }
            ]
        ]
    });
}
function searchChannelEvt() {
    var value = $('#searchChannelValue').val();
    $('#channeldg').datagrid({
        url: "<%=basePath%>/tymng/channelInfo/listChannelByLW",
        queryParams: {channelNameCondition: value, groupId: 2}
    });
}
function selectChannel(channelId, channelName) {
    $("#channelName").val(channelName);
    $("#channelId").val(channelId);
    $('#channeldlg').dialog('close');
}
function exportData() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    var ua = $('#ua').val();
    var channelId = $('#channelId').val();
    var deviceCode = $('#deviceCode').val();
    window.location.href = "<%=basePath%>/tymng/reportCount/exportData?groupId=2&exportType=2&startDate=" + startDate + "&endDate=" + endDate + "&ua=" + ua + "&channelId=" + channelId + "&deviceCode=" + deviceCode;
}
</script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="deviceCode" id="deviceCode" placeholder="选择设备" readonly="readonly"
                           onclick="showDeviceDialog()"/>
                </td>
                <td>
                    <input type="text" name="modelName" id="modelName" placeholder="选择机型" readonly="readonly"
                           onclick="showModelDialog()"/>
                    <input type="hidden" name="ua" id="ua"/>
                </td>
                <td>
                    <input type="text" name="channelName" id="channelName" placeholder="选择仓库" readonly="readonly"
                           onclick="showChannelDialog()"/>
                    <input type="hidden" name="channelId" id="channelId"/>
                </td>
                <td>
                    <input type="text" name="startDate" id="startDate" placeholder="开始时间"/>
                </td>
                <td>
                    <input type="text" name="endDate" id="endDate" placeholder="结束时间"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="searchEvt()">查询</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="resetEvt()">重置</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportData()">导出</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="devicedlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#devicedlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchDeviceValue" id="searchDeviceValue" placeholder="设备编码"/>
                    </td>
                    <td align="center">
                        <a id="searchDeviceBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchDeviceEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="devicedg"></div>
</div>
<div id="devicedlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#devicedlg').dialog('close')">关闭</a>
</div>
<div id="modeldlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#modeldlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchModelValue" id="searchModelValue" placeholder="机型名称"/>
                    </td>
                    <td align="center">
                        <a id="searchModelBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchModelEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="modeldg"></div>
</div>
<div id="modeldlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#modeldlg').dialog('close')">关闭</a>
</div>
<div id="channeldlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
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
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#channeldlg').dialog('close')">关闭</a>
</div>
</body>
</html>