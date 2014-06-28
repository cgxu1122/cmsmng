<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/common/header.jsp" %>
    <title>Demo</title>
    <script type="text/javascript" src="<%= basePath %>/common/js/validateExtends.js"></script>
    <script type="text/javascript">

        $(document).ready(function () {
            initPage();
        });


        function addrow() {
            $('#dlg').dialog('open').dialog('setTitle', '新增');
            $('#fm').form('clear');
            var data = $('#queryDataSource').combobox('getData');
            $("#queryDataSource ").combobox('select', data[0].value);
        }
        function saverow() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/tymng/productInfo/insert',
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
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#updatedlg').dialog('open').dialog('setTitle', '修改');
                row.queryStartTime = new Date(row.queryStartTime).formate("yyyy-MM-dd");
                $('#upfm').form('clear');
                $('#upfm').form('load', row);
            }
        }
        function saveUpdate() {
            $('#upfm').form('submit', {
                url: '<%=basePath%>/tymng/productInfo/update',
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
                $.messager.confirm('提示', '确定要删除[' + row.productName + ']?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/tymng/productInfo/delete', {productId: row.productId}, function (result) {
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
            var value = $('#searchValue').val();
            $('#dg').datagrid({
                url: "<%=basePath%>/tymng/productInfo/list",
                queryParams: {productNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/productInfo/list',
                queryParams: {},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'productId', title: '产品id', align: 'center', width: 100},
                        {field: 'productName', title: '产品名称', align: 'center', width: 300},
                        {field: 'partnerName', title: '合作方', align: 'center', width: 200},
                        {field: 'queryDataSource', title: '查看权限', align: 'center', width: 100,
                            formatter: function (value) {
                                if (value == "Y")return "有权限";
                                if (value == "N")return "无权限";
                            }},
                        {field: 'queryStartTime', title: '查看开始时间', align: 'center', width: 150,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
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
        function showPartnerDialog(type, upPartnerId) {
            $('#partnerdlg').dialog('open').dialog('setTitle', '选择合作方');
            $('#partnerdg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/partnerInfo/list',
                queryParams: {},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'partnerId', title: '合作方id', align: 'center', width: 100},
                        {field: 'partnerName', title: '合作方名称', align: 'center', width: 150},
                        {field: 'createTime', title: '创建日期', align: 'center', width: 150,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'action', title: '操作', align: 'center', width: 100,
                            formatter: function (value, row, index) {
                                return "<a href='javascript:void(0)' onclick=javascript:selectPartner('" + row.partnerId + "','" + row.partnerName + "','" + type + "')>选择</a>";
                            }
                        }
                    ]
                ]
            });
        }
        function searchPartnerEvt() {
            var value = $('#searchPartnerValue').val();
            $('#channeldg').datagrid({
                url: "<%=basePath%>/tymng/partnerInfo/list",
                queryParams: {channelNameCondition: value}
            });
        }
        function selectPartner(partnerId, partnerName, type) {
            if (type == 2) {//修改
                $("#upPartnerId").val(partnerId);
                $("#upPartnerName").val(partnerName);
            } else if (type == 1) {//新增
                $("#partnerId").val(partnerId);
                $("#partnerName").val(partnerName);
            }
            $('#partnerdlg').dialog('close');
        }
    </script>
    <style type="text/css">
        .datagrid .datagrid-pager {
            position: relative;
        }
    </style>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="searchValue" id="searchValue" placeholder="产品名称"/>
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
<div id="dlg" class="easyui-dialog" style="width:400px;height:400px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">产品</div>
    <br/>

    <form id="fm" method="post" novalidate>

        <div class="fitem" style="margin-left:23px">
            <label><font color="red">*</font>产品名称:</label>
            <input id="productName" name="productName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:35px">
            <label><font color="red">*</font>合作方:</label>
            <input type="hidden" id="partnerId" name="partnerId">
            <input type="text" id="partnerName" name="partnerName" readonly="readonly">
            <a href="javascript:void(0)"
               onclick="showPartnerDialog(1)">选择合作方</a>
        </div>
        <div class="fitem" style="margin-left:23px">
            <label><font color="red">*</font>查看权限:</label>
            <select class="easyui-combobox" name="queryDataSource" id="queryDataSource" style="width:150px;">
                <option value="N">无权限</option>
                <option value="Y">有权限</option>
            </select>
        </div>
        <div class="fitem">
            <label><font color="red">*</font>查看开始时间:</label>
            <input type="text" id="queryStartTime" name="queryStartTime" class="easyui-datebox">
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:400px;height:400px;padding:10px 20px" closed="true"
     buttons="#update-buttons">
    <form id="upfm" method="post" novalidate>
        <input type="hidden" id="productId" name="productId"/>

        <div class="fitem" style="margin-left:23px">
            <label><font color="red">*</font>产品名称:</label>
            <input type="text" name="productName" class="easyui-validatebox" required="true">
        </div>
        <div class="fitem" style="margin-left:35px">
            <label><font color="red">*</font>合作方:</label>
            <input type="hidden" name="partnerId" id="upPartnerId">
            <input type="text" name="partnerName" readonly="readonly" id="upPartnerName">
            <a href="javascript:void(0)"
               onclick="showPartnerDialog(2,$('#upPartnerId').val())">选择合作方</a>
        </div>
        <div class="fitem" style="margin-left:23px">
            <label><font color="red">*</font>查看权限:</label>
            <select class="easyui-combobox" name="queryDataSource" id="upQueryDataSource" style="width:150px;">
                <option value="N">无权限</option>
                <option value="Y">有权限</option>
            </select>
        </div>
        <div class="fitem">
            <label><font color="red">*</font>查看开始时间:</label>
            <input type="text" id="upQueryStartTime" name="queryStartTime" class="easyui-datebox">
        </div>
    </form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">取消</a>
</div>

<div id="partnerdlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
     buttons="#partnerdlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchPartnerValue" id="searchPartnerValue" placeholder="合作方名称"/>
                    </td>
                    <td align="center">
                        <a id="searchPartnerbtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchPartnerEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="partnerdg"></div>
</div>
<div id="partnerdlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#partnerdlg').dialog('close')">取消</a>
</div>
</body>
</html>