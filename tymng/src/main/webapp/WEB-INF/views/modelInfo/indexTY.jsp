index.jsp
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
        }
        function saverow() {
            $("#groupId").val("${groupId}");
            $('#fm').form('submit', {
                url: '<%=basePath%>/tymng/modelInfo/insert',
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
            }
        }
        function saveUpdate() {
            $('#upfm').form('submit', {
                url: '<%=basePath%>/tymng/modelInfo/update',
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
                $.messager.confirm('提示', '确定要删除[' + row.modelName + ']?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/tymng/modelInfo/delete', {modelId: row.modelId}, function (result) {
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
                url: "<%=basePath%>/tymng/modelInfo/list",
                queryParams: {groupId: '${groupId}', modelNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                fitColumns: true,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/modelInfo/list',
                queryParams: {groupId: '${groupId}'},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'modelId', title: '机型id', align: 'center', width: 100},
                        {field: 'ua', title: 'UA', align: 'center', width: 200},
                        {field: 'modelName', title: '机型全名', align: 'center', width: 200},
                        {field: 'groupName', title: '渠道组织', align: 'center', width: 100},
                        {field: 'tagNum', title: '标签数量（个）', align: 'center', width: 120},
                        {field: 'tagPrice', title: '标签单价（元）', align: 'center', width: 120},
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
        /* function exportData() {
         var value = $('#searchValue').val();
         window.location.href = "<%=basePath%>/tymng/modelInfo/export?groupId=${groupId}&modelNameCondition=" + value;
         }*/
    </script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="searchValue" id="searchValue" placeholder="机型全名"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="searchEvt()">查询</a>
                </td>
                <shiro:hasPermission name="modle_ty_add">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="modle_ty_update">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">修改</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="modle_ty_delete">
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
    <div class="ftitle">机型</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <input type="hidden" id="groupId" name="groupId"/>

        <div class="fitem">
            <label><font color="red">*</font>机型全名:</label>
            <input id="modelName" name="modelName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:35px">
            <label><font color="red">*</font>UA:</label>
            <input id="ua" name="ua" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>标签数量:</label>
            <input id="tagNum" name="tagNum" class="easyui-validatebox" required="true" maxlength="20">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>标签单价:</label>
            <input id="tagPrice" name="tagPrice" class="easyui-validatebox" required="true" maxlength="20">
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
        <input type="hidden" id="modelId" name="modelId"/>

        <div class="fitem">
            <label><font color="red">*</font>机型全名:</label>
            <input type="text" name="modelName" class="easyui-validatebox" required="true"
                   maxlength="50">
        </div>
        <div class="fitem" style="margin-left:35px">
            <label><font color="red">*</font>UA:</label>
            <input type="text" name="ua" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>标签数量:</label>
            <input type="text" name="tagNum" class="easyui-validatebox"
                   required="true" maxlength="20">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>标签单价:</label>
            <input type="text" name="tagPrice" class="easyui-validatebox"
                   required="true" maxlength="20">
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