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

        function addrow() {
            $('#dlg').dialog('open').dialog('setTitle', '新增');
            $('#fm').form('clear');
            $("input[name=file]").val("");
            var data = $('#type').combobox('getData');
            $("#type").combobox('select', data[0].value);
        }
        function saverow() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/tymng/apkInfo/insert',
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
                $('#upfm').form('clear');
                $('#upfm').form('load', row);
                $("input[name=file]").val("");
            }
        }
        function saveUpdate() {
            $('#upfm').form('submit', {
                url: '<%=basePath%>/tymng/apkInfo/update',
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
                $.messager.confirm('提示', '确定要删除[' + row.apkName + ']?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/tymng/apkInfo/delete', {apkId: row.apkId}, function (result) {
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
                url: "<%=basePath%>/tymng/apkInfo/list",
                queryParams: {apkNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                fitColumns: true,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/apkInfo/list',
                queryParams: {},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'apkName', title: '产品名称', align: 'center', width: 150},
                        {field: 'softName', title: '软件名称', align: 'center', width: 200},
                        {field: 'downloadUrl', title: '下载路径', align: 'center', width: 400},
                        {field: 'type', title: 'apk类型', align: 'center', width: 80,
                            formatter: function (value) {
                                if ("1" == value) {
                                    return "普通Apk";
                                } else if ("2" == value) {
                                    return "计数器Apk";
                                } else if ("3" == value) {
                                    return "安装进度Apk";
                                }
                                return "";
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
                <shiro:hasPermission name="publish_apk_add">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="publish_apk_update">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">修改</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="publish_apk_delete">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="delrow()">删除</a>
                    </td>
                </shiro:hasPermission>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#dlg-buttons">
    <div class="ftitle">apk</div>
    <br/>

    <form id="fm" method="post" enctype="multipart/form-data" novalidate>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>产品名称:</label>
            <input id="apkName" name="apkName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>上传文件:</label>
            <input type="file" name="file"/>
        </div>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>Apk类型:</label>
            <select class="easyui-combobox" name="type" id="type" style="width:150px;">
                <option value="1">普通Apk</option>
                <option value="2">计数器Apk</option>
                <option value="3">安装进度Apk</option>
            </select>
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
    <form id="upfm" method="post" enctype="multipart/form-data" novalidate>
        <input type="hidden" id="apkId" name="apkId"/>

        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>产品名称:</label>
            <input type="text" name="apkName" class="easyui-validatebox" required="true"
                    >
        </div>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>上传文件:</label>
            <input type="file" name="file"/>
        </div>
        <div class="fitem" style="margin-left:3px">
            <label><font color="red">*</font>Apk类型:</label>
            <select class="easyui-combobox" name="type" id="upType" style="width:150px;">
                <option value="1">普通Apk</option>
                <option value="2">计数器Apk</option>
                <option value="3">安装进度Apk</option>
            </select>
        </div>
</div>
</form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">关闭</a>
</div>

</body>
</html>