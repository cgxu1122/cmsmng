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
            $("input[name=file]").val("");
        }
        function saverow() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/apkInfo/insert',
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
                row.effectiveTime = new Date(row.effectiveTime).formate("yyyy-MM-dd");
                $('#upfm').form('clear');
                $('#upfm').form('load', row);
                $("input[name=file]").val("");
            }
        }
        function saveUpdate() {
            $('#upfm').form('submit', {
                url: '<%=basePath%>/apkInfo/update',
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
                        $.post('<%=basePath%>/apkInfo/delete', {apkId: row.apkId}, function (result) {
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
                url: "<%=basePath%>/apkInfo/list",
                queryParams: {productNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/apkInfo/list',
                queryParams: {},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'productName', title: '产品名称', align: 'center', width: 150},
                        {field: 'apkName', title: '软件名称', align: 'center', width: 300},
                        {field: 'ftpPath', title: 'ftp路径', align: 'center', width: 400},
                        {field: 'createTime', title: '创建日期', align: 'center', width: 200,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        }
                    ]
                ]
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
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">设备系统</div>
    <br/>

    <form id="fm" method="post" enctype="multipart/form-data" novalidate>
        <div class="fitem" style="margin-left:15px">
            <label><font color="red">*</font>产品名称:</label>
            <input id="productName" name="productName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>上传文件:</label>
            <input type="file" name="file"/>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px" closed="true"
     buttons="#update-buttons">
    <form id="upfm" method="post" enctype="multipart/form-data" novalidate>
        <input type="hidden" id="apkId" name="apkId"/>

        <div class="fitem" style="margin-left:15px">
            <label><font color="red">*</font>产品名称:</label>
            <input type="text" name="productName" class="easyui-validatebox" required="true"
                    >
        </div>
        <div class="fitem">
            <label><font color="red">*</font>上传文件:</label>
            <input type="file" name="file"/>
        </div>
</div>
</form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">取消</a>
</div>

</body>
</html>