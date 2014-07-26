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
            var productId = $('#productId').val();
            $('#dg').datagrid({
                url: "<%=basePath%>/hzfmng/partnerQuery/listProductStat",
                queryParams: {productId: productId, startDate: startDate, endDate: endDate}
            });
        }

        function initPage() {
            var startDate = $('#startDate').datebox('getValue');
            var endDate = $('#endDate').datebox('getValue');
            $('#dg').datagrid({
                fitColumns: true,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/hzfmng/partnerQuery/listProductStat',
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
                                }
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'modelName', title: '机型名称', align: 'center', width: 300},
                        {field: 'productName', title: '产品名称', align: 'center', width: 300},
                        {field: 'productPrsDayNum', title: '装机数量', align: 'center', width: 200,
                            formatter: function (value, row, index) {
                                if (row.processDate == null) {
                                    return value;
                                } else if (row.queryImeiSource == 'N') {
                                    return value;
                                } else {
                                    return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.processDate + "','" + row.ua + "','" + row.productId + "','" + row.modelName + "','" + row.groupId + "')>" + value + "</a>";
                                }
                            }
                        }
                    ]
                ]
            });
        }
        function showProductDialog() {
            $('#productdlg').dialog('open').dialog('setTitle', '选择产品');
            $('#productdg').datagrid({
                width: 'auto',
                height: 'auto',
                fitColumns: true,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/hzfmng/productInfo/list',
                queryParams: {groupId: 1},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'productName', title: '产品名称', align: 'center', width: 300},
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
                url: "<%=basePath%>/hzfmng/productInfo/list",
                queryParams: {productNameCondition: value}
            });
        }
        function selectProduct(productId, productName) {
            $("#productName").val(productName);
            $("#productId").val(productId);
            $('#productdlg').dialog('close');
        }

        var processDateCur;
        var uaCur;
        var productIdCur;
        var modelNameCur;
        var groupIdCur;
        function showIMEIDialog(processDate, ua, productId, modelName, groupId) {
            processDateCur = processDate;
            uaCur = ua;
            productIdCur = productId;
            modelNameCur = modelName;
            groupIdCur = groupId;
            $('#imeidlg').dialog('open').dialog('setTitle', 'imei列表');
            $('#imeidg').datagrid({
                width: 'auto',
                height: 'auto',
                fitColumns: true,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/hzfmng/partnerQuery/listImei',
                queryParams: {processDate: processDate, ua: ua, productId: productId, modelName: modelName, groupId: groupId},
                loadMsg: '数据加载中请稍后……',
                rownumbers: true,
                columns: [
                    [
                        {field: 'processDate', title: '日期', align: 'center', width: 150,
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
                url: "<%=basePath%>/hzfmng/partnerQuery/exportImei?userType=cp&processDate=" + processDateCur + "&ua=" + uaCur + "&productId=" + productIdCur + "&modelName=" + modelNameCur + "&groupId=" + groupIdCur,
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
            var productId = $('#productId').val();
            $("body").showLoading();
            $.ajax({
                url: "<%=basePath%>/hzfmng/partnerQuery/exportProductData?startDate=" + startDate + "&endDate=" + endDate + "&productId=" + productId,
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
            $('#productId').val("");
            $('#productName').val("");
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