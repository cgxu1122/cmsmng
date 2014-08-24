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
    var data = $('#groupId').combobox('getData');
    $("#groupId").combobox('select', data[0].value);
    initPage();
});
function searchEvt() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    var ua = $('#ua').val();
    var groupId = $('#groupId').combobox('getValue');
    var productId = $('#productId').val();
    $('#dg').datagrid({
        url: "<%=basePath%>/tymng/reportCountNew/listProductStat",
        queryParams: {groupId: groupId, startDate: startDate, endDate: endDate, ua: ua, productId: productId}
    });
}

function resetEvt() {
    $('#ua').val("");
    $('#modelName').val("");
    $('#productId').val("");
    $('#productName').val("");
    $("#groupId").combobox('select', "");
}

function initPage() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    $('#dg').datagrid({
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/reportCountNew/listProductStat',
        queryParams: {startDate: startDate, endDate: endDate},
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
                        } else {
                            return new Date(value).formate("yyyy-MM-dd");
                        }
                    }
                },
                {field: 'modelName', title: '机型名称', align: 'center', width: 200},
                {field: 'productName', title: '产品名称', align: 'center', width: 200},
                {field: 'groupName', title: '渠道组织', align: 'center', width: 200},
                {field: 'productPrsDayNum', title: '装机数量', align: 'center', width: 200,
                    formatter: function (value, row, index) {
                        if (row.processDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.processDate + "','" + row.ua + "','" + row.groupId + "','" + row.modelName + "','" + row.groupName + "','" + row.productId + "','" + row.productName + "',1)>" + value + "</a>";
                        }
                    }
                },
                {field: 'prsActiveTotalNum', title: '装机到达数量', align: 'center', width: 200,
                    formatter: function (value, row, index) {
                        if (row.processDate == null) {
                            return value;
                        } else {
                            return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.processDate + "','" + row.ua + "','" + row.groupId + "','" + row.modelName + "','" + row.groupName + "','" + row.productId + "','" + row.productName + "',3)>" + value + "</a>";
                        }
                    }
                }
            ]
        ]
    });
}


var processDateCur;
var uaCur;
var groupIdCur;
var modelNameCur;
var groupNameCur;
var productIdCur;
var productNameCur;
var queryTypeCur;
function showIMEIDialog(processDate, ua, groupId, modelName, groupName, productId, productName, queryType) {
    processDateCur = processDate;
    uaCur = ua;
    groupIdCur = groupId;
    modelNameCur = modelName;
    groupNameCur = groupName;
    productIdCur = productId;
    productNameCur = productName;
    queryTypeCur = queryType;
    $('#imeidlg').dialog('open').dialog('setTitle', 'imei列表,只显示前1000条，查看全部Imei请导出');
    $('#imeidg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/reportCountNew/listImei',
        queryParams: {processDate: processDate, ua: ua, groupId: groupId, modelName: modelName, groupName: groupName, productId: productId, productName: productName, queryType: queryType},
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
                {field: 'groupName', title: '仓库名称', align: 'center', width: 150},
                {field: 'productName', title: '产品名称', align: 'center', width: 150},
                {field: 'imei', title: 'IMEI号', align: 'center', width: 200}
            ]
        ]
    });
}
function exportImeiEvt() {
    $("body").showLoading();
    $.ajax({
        url: "<%=basePath%>/tymng/reportCountNew/exportImei?exportType=3&processDate=" + processDateCur + "&ua=" + uaCur + "&groupId=" + groupIdCur + "&modelName=" + modelNameCur + "&groupName=" + groupNameCur + "&productId=" + productIdCur + "&productName=" + productNameCur + "&queryType=" + queryTypeCur,
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
        queryParams: {},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'groupName', title: '渠道组名称', align: 'center', width: 100},
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
        queryParams: {modelNameCondition: value}
    });
}
function selectModel(ua, modelName) {
    $("#modelName").val(modelName);
    $("#ua").val(ua);
    $('#modeldlg').dialog('close');
}
function showProductDialog() {
    $('#productdlg').dialog('open').dialog('setTitle', '选择产品');
    $('#productdg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/productInfo/viewList',
        queryParams: {},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'productName', title: '仓库名称', align: 'center', width: 150},
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectProduct('" + row.productId + "','" + row.productName + "')>选择</a>";
                    }
                }
            ]
        ]
    });
}
function searchProductEvt() {
    var value = $('#searchProductValue').val();
    $('#productdg').datagrid({
        url: "<%=basePath%>/tymng/productInfo/list",
        queryParams: {productNameCondition: value}
    });
}
function selectProduct(productId, productName) {
    $("#productName").val(productName);
    $("#productId").val(productId);
    $('#productdlg').dialog('close');
}
function exportData() {
    var startDate = $('#startDate').datebox('getValue');
    var endDate = $('#endDate').datebox('getValue');
    var ua = $('#ua').val();
    var groupId = $('#groupId').combobox('getValue');
    var productId = $('#productId').val();
    $("body").showLoading();
    $.ajax({
        url: "<%=basePath%>/tymng/reportCountNew/exportProductData?groupId=" + groupId + "&startDate=" + startDate + "&endDate=" + endDate + "&ua=" + ua + "&productId=" + productId,
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
                    <input type="text" name="productName" id="productName" placeholder="选择产品" readonly="readonly"
                           onclick="showProductDialog()"/>
                    <input type="hidden" name="productId" id="productId"/>
                </td>
                <td>
                    <input type="text" name="modelName" id="modelName" placeholder="选择机型" readonly="readonly"
                           onclick="showModelDialog()"/>
                    <input type="hidden" name="ua" id="ua"/>
                </td>
                <td>
                    <select class="easyui-combobox" name="groupId" id="groupId" style="width:150px;">
                        <option value="">全部渠道组</option>
                        <option value="1">天音渠道</option>
                        <option value="2">地包渠道</option>
                        <option value="3">其他渠道</option>
                    </select>
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
<div id="productdlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#productdlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchProductValue" id="searchProductValue" placeholder="产品名称"/>
                    </td>
                    <td align="center">
                        <a id="searchProductBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchProductEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="productdg"></div>
</div>
<div id="productdlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#productdlg').dialog('close')">关闭</a>
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