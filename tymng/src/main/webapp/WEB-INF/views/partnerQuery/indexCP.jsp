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
                url: "<%=basePath%>/tymng/partnerQuery/listProductStat",
                queryParams: {productId: productId, startDate: startDate, endDate: endDate}
            });
        }

        function initPage() {
            var startDate = $('#startDate').datebox('getValue');
            var endDate = $('#endDate').datebox('getValue');
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/partnerQuery/listProductStat',
                queryParams: {startDate: startDate, endDate: endDate},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'processDate', title: '日期', align: 'center', width: 300,
                            formatter: function (value) {
                                if (value == null) {
                                    return "合计"
                                }
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'modelName', title: '机型名称', align: 'center', width: 400},
                        {field: 'productPrsDayNum', title: '装机数量', align: 'center', width: 200,
                            formatter: function (value, row, index) {
                                //return "<a href='javascript:void(0)' onclick=javascript:showIMEIDialog('" + row.processDate + "','" + row.ua + "','" + row.channelId + "','" + row.modelName + "')>"+value+"</a>";
                                return "<a href='javascript:void(0)'>" + value + "</a>";
                            }
                        }
                    ]
                ]
            });
        }
        function showProductDialog() {
            $('#productdlg').dialog('open').dialog('setTitle', '选择 产品');
            $('#productdg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/productInfo/list',
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
            var productId = $('#productId').val();
            window.location.href = "<%=basePath%>/tymng/partnerQuery/exportProductData?startDate=" + startDate + "&endDate=" + endDate + "&productId=" + productId;
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
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="exportData()">导出</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="productdlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
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
</body>
</html>