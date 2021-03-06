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
    var channelIdCondition = $('#channelIdCondition').val();
    $('#dg').datagrid({
        url: "<%=basePath%>/tymng/reportCountNew/listLogArriveStat",
        queryParams: {groupId: 2, startDate: startDate, endDate: endDate, ua: ua, channelIdCondition: channelIdCondition}
    });
}

function resetEvt() {
    $('#ua').val("");
    $('#modelName').val("");
    $('#channelName').val("");
    $('#channelIdCondition').val("");
    $('#channeldg').datagrid("uncheckAll");
}

function initPage() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    $('#dg').datagrid({
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/reportCountNew/listLogArriveStat',
        queryParams: {groupId: 2, startDate: startDate, endDate: endDate},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        columns: [
            [
                {field: 'statDate', title: '日期', align: 'center', width: 200,
                    formatter: function (value) {
                        if (value == null) {
                            return "合计"
                        }
                        return new Date(value).formate("yyyy-MM-dd");
                    }
                },
                {field: 'modelName', title: '机型全称', align: 'center', width: 300},
                {field: 'channelName', title: '渠道商名称', align: 'center', width: 200},
                {field: 'totalNum', title: '累计到达', align: 'center', width: 150,
                    formatter: function (value, row, index) {
                        if (row.statDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.statDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "','" + row.channelName + "',21)>" + value + "</a>";
                        }
                    }
                },
                {field: 'validNum', title: '有效到达', align: 'center', width: 150,
                    formatter: function (value, row, index) {
                        if (row.statDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.statDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "','" + row.channelName + "',22)>" + value + "</a>";
                        }
                    }
                },
                {field: 'deductionValidNum', title: '有效结算', align: 'center', width: 150},
                {field: 'invalidNum', title: '无效到达', align: 'center', width: 150,
                    formatter: function (value, row, index) {
                        if (row.statDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.statDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "','" + row.channelName + "',23)>" + value + "</a>";
                        }
                    }
                },
                {field: 'deductionInvalidNum', title: '无效结算', align: 'center', width: 150},
                {field: 'replaceNum', title: '替换', align: 'center', width: 150,
                    formatter: function (value, row, index) {
                        if (row.statDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.statDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "','" + row.channelName + "',24)>" + value + "</a>";
                        }
                    }
                },
                {field: 'uninstallNum', title: '卸载', align: 'center', width: 150,
                    formatter: function (value, row, index) {
                        if (row.statDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.statDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "','" + row.channelName + "',25)>" + value + "</a>";
                        }
                    }
                },
                {field: 'unAndReNum', title: '替换加卸载', align: 'center', width: 200,
                    formatter: function (value, row, index) {
                        if (row.statDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.statDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "','" + row.channelName + "',26)>" + value + "</a>";
                        }
                    }
                }
            ]
        ]
    });
}

var processDateCur;
var uaCur;
var channelIdCur;
var modelNameCur;
var channelNameCur;
var queryTypeCur;
function showIMEIDialog(processDate, ua, channelId, modelName, channelName, queryType) {
    processDateCur = processDate;
    uaCur = ua;
    channelIdCur = channelId;
    modelNameCur = modelName;
    channelNameCur = channelName;
    queryTypeCur = queryType;
    $('#imeidlg').dialog('open').dialog('setTitle', 'imei列表,只显示前1000条，查看全部Imei请导出');
    $('#imeidg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/reportCountNew/listImei',
        queryParams: {processDate: processDate, ua: ua, channelId: channelId, modelName: modelName, channelName: channelName, queryType: queryType},
        loadMsg: '数据加载中请稍后……',
        rownumbers: true,
        columns: [
            [
                {field: 'processDate', title: '日期', align: 'center', width: 150,
                    formatter: function (value) {
                        return new Date(parseFloat(processDateCur)).formate("yyyy-MM-dd");
                    }
                },
                {field: 'modelName', title: '机型名称', align: 'center', width: 150},
                {field: 'channelName', title: '仓库名称', align: 'center', width: 150},
                {field: 'imei', title: 'IMEI号', align: 'center', width: 200}
            ]
        ]
    });
}
function exportImeiEvt() {
    $("body").showLoading();
    $.ajax({
        url: "<%=basePath%>/tymng/reportCountNew/exportImei?exportType=2&processDate=" + processDateCur + "&ua=" + uaCur + "&channelId=" + channelIdCur + "&modelName=" + modelNameCur + "&channelName=" + channelNameCur + "&queryType=" + queryTypeCur,
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
function showModelDialog() {
    $('#modeldlg').dialog('open').dialog('setTitle', '选择机型');
    $('#modeldg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
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
        fitColumns: true,
        striped: true,
        url: '<%=basePath%>/tymng/channelInfo/listAll',
        queryParams: {groupId: 2},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        idField: 'channelId',
        columns: [
            [
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
        url: "<%=basePath%>/tymng/channelInfo/listAll",
        queryParams: {channelNameCondition: value, groupId: 2}
    });
}
function selectChannel() {
    var ids = [];
    var names = [];
    var rows = $('#channeldg').datagrid('getSelections');
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].channelId);
        names.push(rows[i].channelName);
    }
    $("#channelName").val(names.join(','));
    $("#channelIdCondition").val(ids.join(','));
    $('#channeldlg').dialog('close')
}
function exportData() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    var ua = $('#ua').val();
    var channelIdCondition = $('#channelIdCondition').val();
    $("body").showLoading();
    $.ajax({
        url: "<%=basePath%>/tymng/reportCountNew/exportArriveData?groupId=2&exportType=2&startDate=" + startDate + "&endDate=" + endDate + "&ua=" + ua + "&channelIdCondition=" + channelIdCondition,
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
                    <input type="text" name="modelName" id="modelName" placeholder="选择机型" readonly="readonly"
                           onclick="showModelDialog()"/>
                    <input type="hidden" name="ua" id="ua"/>
                </td>
                <td>
                    <input type="text" name="channelName" id="channelName" placeholder="选择仓库" readonly="readonly"
                           onclick="showChannelDialog()"/>
                    <input type="hidden" name="channelIdCondition" id="channelIdCondition"/>
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
<div id="devicedlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
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