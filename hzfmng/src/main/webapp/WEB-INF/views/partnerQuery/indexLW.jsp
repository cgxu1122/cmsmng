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
    $('#dg').datagrid({
        url: "<%=basePath%>/hzfmng/partnerQuery/listLogStat",
        queryParams: {groupId: 4, startDate: startDate, endDate: endDate, ua: ua, channelId: channelId}
    });
}

function initPage() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    $('#dg').datagrid({
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/hzfmng/partnerQuery/listLogStat',
        queryParams: {groupId: 4, startDate: startDate, endDate: endDate},
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
                {field: 'deviceCode', title: '设备编码', align: 'center', width: 200},
                {field: 'channelName', title: '仓库名称', align: 'center', width: 300},
                {field: 'modelName', title: '机型名称', align: 'center', width: 200},
                {field: 'devicePrsDayNum', title: '装机数量', align: 'center', width: 200,
                    formatter: function (value, row, index) {
                        if (row.processDate == null) {
                            return value;
                        } else if (row.queryImeiSource == 'N') {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.processDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "','" + row.deviceCode + "')>" + value + "</a>";
                        }
                    }
                }
            ]
        ]
    });
}
function showModelDialog() {
    $('#modeldlg').dialog('open').dialog('setTitle', '选择机型');
    $('#modeldg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/hzfmng/modelInfo/list',
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
        url: "<%=basePath%>/hzfmng/modelInfo/list",
        queryParams: {modelNameCondition: value, groupId: 2}
    });
}
function selectModel(ua, modelName) {
    $("#modelName").val(modelName);
    $("#ua").val(ua);
    $('#modeldlg').dialog('close');
}
function showChannelDialog() {
    $('#channeldlg').dialog('open').dialog('setTitle', '选择渠道商');
    $('#channeldg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/hzfmng/channelInfo/listChannelByLW',
        queryParams: {},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'channelName', title: '渠道商名称', align: 'center', width: 150},
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
        url: "<%=basePath%>/hzfmng/channelInfo/listChannelByLW",
        queryParams: {channelNameCondition: value}
    });
}
function selectChannel(channelId, channelName) {
    $("#channelName").val(channelName);
    $("#channelId").val(channelId);
    $('#channeldlg').dialog('close');
}
var processDateCur;
var uaCur;
var channelIdCur;
var modelNameCur;
var deviceCodeCur;
function showIMEIDialog(processDate, ua, channelId, modelName, deviceCode) {
    processDateCur = processDate;
    uaCur = ua;
    channelIdCur = channelId;
    modelNameCur = modelName;
    deviceCodeCur = deviceCode;
    $('#imeidlg').dialog('open').dialog('setTitle', 'imei列表,只显示前1000条，查看全部Imei请导出');
    $('#imeidg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/hzfmng/partnerQuery/listImei',
        queryParams: {processDate: processDate, ua: ua, channelId: channelId, modelName: modelName, deviceCode: deviceCode},
        loadMsg: '数据加载中请稍后……',
        rownumbers: true,
        columns: [
            [
                {field: 'a', title: '日期', align: 'center', width: 150,
                    formatter: function (value) {
                        return new Date(parseFloat(processDateCur)).formate("yyyy-MM-dd");
                    }
                },
                {field: 'modelName', title: '机型', align: 'center', width: 150},
                {field: 'imei', title: 'IMEI号', align: 'center', width: 200}
            ]
        ]
    });
}
function exportImeiEvt() {
    $("body").showLoading();
    $.ajax({
        url: "<%=basePath%>/hzfmng/partnerQuery/exportImei?userType=lw&processDate=" + processDateCur + "&ua=" + uaCur + "&channelId=" + channelIdCur + "&modelName=" + modelNameCur + "&deviceCode=" + deviceCodeCur,
        success: function (result) {
            $("body").hideLoading();
            var result = eval('(' + result + ')');
            if (result.errorMsg) {
                $.messager.alert('错误', result.errorMsg);
            } else {
                window.location.href = "<%=basePath%>/hzfmng/downloadFile/downloadFile?path=" + result.path;
            }
        }
    });
}

function exportData() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    var ua = $('#ua').val();
    var channelId = $('#channelId').val();
    $("body").showLoading();
    $.ajax({
        url: "<%=basePath%>/hzfmng/partnerQuery/exportData?groupId=4&startDate=" + startDate + "&endDate=" + endDate + "&ua=" + ua + "&channelId=" + channelId,
        success: function (result) {
            $("body").hideLoading();
            var result = eval('(' + result + ')');
            if (result.errorMsg) {
                $.messager.alert('错误', result.errorMsg);
            } else {
                window.location.href = "<%=basePath%>/hzfmng/downloadFile/downloadFile?path=" + result.path;
            }
        }
    });
}
function resetEvt() {
    $('#ua').val("");
    $('#modelName').val("");
    $('#channelId').val("");
    $('#channelName').val("");
}
</script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="modelName" id="modelName" placeholder="选择机型" readonly="readonly"
                           onclick="showModelDialog()"/>
                    <input type="hidden" name="ua" id="ua"/>
                </td>
                <td>
                    <input type="text" name="channelName" id="channelName" placeholder="选择渠道商" readonly="readonly"
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
<div id="modeldlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
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

<div id="channeldlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#channeldlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchChannelValue" id="searchChannelValue" placeholder="渠道商名称"/>
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
<div id="imeidlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#imeidlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td align="center">
                        <a id="exportImeiBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           onclick="exportImeiEvt()">导出</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="imeidg"></div>
</div>
<div id="imeidlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#imeidlg').dialog('close')">关闭</a>
</div>
</body>
</html>