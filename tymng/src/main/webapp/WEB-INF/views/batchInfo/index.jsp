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
    initPage();
});

var addProductList;
var updateProductList;
function addrow() {
    $('#dlg').dialog('open').dialog('setTitle', '新增');
    $('#fm').form('clear');
    $('#startTime').datebox('setValue', getCurrrentDateStr());
    $.ajax({
        url: "<%=basePath%>/tymng/batchInfo/getSeqByGroupId?groupId=${groupId}",
        success: function (result) {
            var result = eval('(' + result + ')');
            $("#batchCode").val(result.batchCode);
        }
    });
    $("#productList").empty();
    addProductList = new Array();
}
function saverow() {
    $("#groupId").val("${groupId}");
    $('#fm').form('submit', {
        url: '<%=basePath%>/tymng/batchInfo/insert',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.errorMsg) {
                $.messager.alert('错误', result.errorMsg);
            } else {
                $('#dlg').dialog('close');
                $('#dg').datagrid('reload');
            }
        }
    });
}

function editrow() {
    $("#upProductList").empty();
    updateProductList = new Array();
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#updatedlg').dialog('open').dialog('setTitle', '修改');
        row.startTime = new Date(row.startTime).formate("yyyy-MM-dd");
        row.templateBatchCode = "";
        $('#upfm').form('clear');
        $('#upfm').form('load', row);
        var productInfoList = row.productInfoList;
        if (productInfoList) {
            for (var i = 0; i < productInfoList.length; i++) {
                updateProductList.push(productInfoList[i].productId);
                var productHtml = "<tr>" +
                        "<input type='hidden' name='productId' value='" + productInfoList[i].productId + "'>" +
                        "<td><input type='text' name='productName' value='" + productInfoList[i].productName + "' readonly='readonly'></td>" +
                        "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();'>删除</a></td>" +
                        "</tr>";
                $('#upProductList').append(productHtml);
            }
        }
    }
}
function saveUpdate() {
    $('#upfm').form('submit', {
        url: '<%=basePath%>/tymng/batchInfo/update',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.errorMsg) {
                $.messager.alert('错误', result.errorMsg);
            } else {
                $('#updatedlg').dialog('close');
                $('#dg').datagrid('reload');
            }
        }
    });
}

function delrow() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示', '确定要删除[' + row.batchCode + ']?', function (r) {
            if (r) {
                $.post('<%=basePath%>/tymng/batchInfo/delete', {batchId: row.batchId}, function (result) {
                    if (result.errorMsg) {
                        $.messager.alert('错误', result.errorMsg);
                    } else {
                        $('#dg').datagrid('reload');
                    }
                }, 'json');
            }
        });
    }
}

function searchEvt() {
    var batchCodeCondition = $('#searchValue').val();
    var batchProductName = $('#searchProductNameValue').val();
    $('#dg').datagrid({
        url: "<%=basePath%>/tymng/batchInfo/list",
        queryParams: {groupId: '${groupId}', batchCodeCondition: batchCodeCondition, batchProductName: batchProductName}
    });
}

function initPage() {
    $('#dg').datagrid({
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/batchInfo/list',
        queryParams: {groupId: '${groupId}'},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        columns: [
            [
                {field: 'batchCode', title: '批次', align: 'center', width: 100},
                {field: 'groupName', title: '渠道组织', align: 'center', width: 150},
                {field: 'batchProductName', title: '产品名称', align: 'center', width: 450},
                {field: 'startTime', title: '开始时间', align: 'center', width: 100,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd");
                    }
                },
                {field: 'batchProductNum', title: '产品数量', align: 'center', width: 80},
                {field: 'createTime', title: '创建日期', align: 'center', width: 140,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd HH:mm:ss");
                    }
                },
                {field: 'updateTime', title: '修改日期', align: 'center', width: 140,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd HH:mm:ss");
                    }
                }
            ]
        ]
    });
}

function showProductDialog(type) {
    $('#productdlg').dialog('open').dialog('setTitle', '选择产品');
    $('#productdg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/productInfo/list',
        queryParams: {},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'productId', title: '产品id', align: 'center', width: 100},
                {field: 'productName', title: '产品名称', align: 'center', width: 150},
                {field: 'createTime', title: '创建日期', align: 'center', width: 150,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd");
                    }
                },
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectProduct('" + row.productId + "','" + row.productName + "','" + type + "')>添加</a>";
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
function selectProduct(productId, productName, type) {
    if (type == 2) {//修改
        var productHtml = "<tr>" +
                "<input type='hidden' name='productId' value='" + productId + "'>" +
                "<td><input type='text' name='productName' value='" + productName + "' readonly='readonly'></td>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();updateProductList.pop(" + productId + ");'>删除</a></td>" +
                "</tr>";
        if (!updateProductList.in_array(productId)) {
            updateProductList.push(productId);
            $('#upProductList').append(productHtml);
        }
    } else if (type == 1) {//新增
        var productHtml = "<tr>" +
                "<input type='hidden' name='productId' value='" + productId + "'>" +
                "<td><input type='text' name='productName' value='" + productName + "' readonly='readonly'></td>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();addProductList.pop(" + productId + ");'>删除</a></td>" +
                "</tr>";
        if (!addProductList.in_array(productId)) {
            addProductList.push(productId);
            $('#productList').append(productHtml);
        }
    }
}

function importTemplateBatch(type) {
    var batchCode;
    if (type == 2) {
        batchCode = $("#uptemplateBatchCode").val();
    } else if (type == 1) {
        batchCode = $("#templateBatchCode").val();
    }
    if (batchCode == "") {
        $.messager.alert('错误', "请输入批次号!");
        return;
    }
    $.ajax({
        url: "<%=basePath%>/tymng/batchInfo/importTemplateBatch?groupId=${groupId}&batchCode=" + batchCode,
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.errorMsg) {
                $.messager.alert('错误', result.errorMsg);
            } else {
                var productInfoList = result.batchInfo.productInfoList;
                if (productInfoList) {
                    for (var i = 0; i < productInfoList.length; i++) {
                        selectProduct(productInfoList[i].productId, productInfoList[i].productName, type);
                    }
                }
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
                    <input type="text" name="searchValue" id="searchValue" placeholder="批次"/>
                </td>
                <td>
                    <input type="text" name="searchProductNameValue" id="searchProductNameValue" placeholder="商品名称"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="searchEvt()">查询</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">修改</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="delrow()">删除</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#dlg-buttons">
    <div class="ftitle">批次</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <input type="hidden" id="groupId" name="groupId"/>

        <div class="fitem" style="margin-left:30px">
            <label><font color="red">*</font>批次号:</label>
            <input type="text" id="batchCode" name="batchCode" readonly="readonly">
        </div>
        <div class="fitem" style="margin-left:18px">
            <label><font color="red">*</font>开始时间:</label>
            <input type="text" id="startTime" name="startTime" class="easyui-datebox">
        </div>
        <div class="fitem">
            <label>原批次号模板:</label>
            <input type="text" id="templateBatchCode" name="templateBatchCode">
            <a href="javascript:void(0)"
               onclick="importTemplateBatch(1)">导入</a>
        </div>
        <div class="fitem" style="margin-left:25px">
            <label>选择产品:</label>
            <a href="javascript:void(0)"
               onclick="showProductDialog(1)">选择</a>

            <table id="productList" style="border: solid thin">
            </table>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#update-buttons">
    <form id="upfm" method="post" novalidate>
        <input type="hidden" id="batchId" name="batchId"/>

        <div class="fitem" style="margin-left:30px">
            <label><font color="red">*</font>批次号:</label>
            <input type="text" name="batchCode" readonly="readonly">
        </div>
        <div class="fitem" style="margin-left:18px">
            <label><font color="red">*</font>开始时间:</label>
            <input type="text" id="upStartTime" name="startTime" class="easyui-datebox">
        </div>
        <div class="fitem">
            <label>原批次号模板:</label>
            <input type="text" id="uptemplateBatchCode" name="templateBatchCode">
            <a href="javascript:void(0)"
               onclick="importTemplateBatch(2)">导入</a>
        </div>
        <div class="fitem" style="margin-left:30px">
            <label>选择产品:</label>
            <a href="javascript:void(0)"
               onclick="showProductDialog(2)">选择</a>

            <table id="upProductList" style="border: solid thin">
            </table>
        </div>
    </form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">关闭</a>
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

</body>
</html>