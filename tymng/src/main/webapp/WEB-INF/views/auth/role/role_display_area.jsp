<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/common/header.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <script>
        $(document).ready(function () {
            initPage();
        });

        var grid;

        function initPage() {
            grid = $('#dg').datagrid({
                width: 'auto',
                height: 400,
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/auth/role/list',
                queryParams: {parentId:  ${parentId} },
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        { field: 'roleName', title: '角色名称', align: 'center', width: 100},
                        { field: 'levels', title: '层级', align: 'center', width: 200 },
                        { field: 'parentRoleName', title: '父角色名称', align: 'center', width: 200 },
                        { field: 'createTime', title: '创建时间', align: 'center', width: 200 },
                        { field: 'updateTime', title: '修改时间', align: 'center', width: 200 },
                        { field: 'roleId', hidden: true }
                    ]
                ],
                pagination: true,
                toolbar: '#toolbar'
            });
        }

        function addrow() {
            $('#dlg').dialog('open').dialog('setTitle', '新建角色');
            $('#fm').form('clear');
        }


        function saverow() {
            $('#parentId').val('${parentId}');
            $('#fm').form('submit', {
                url: '<%=basePath%>/tymng/auth/role/insert',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.ret == -1) {
                        $.messager.alert('错误', result.message);
                    } else {
                        $.messager.alert('成功', result.message);
                        $('#dlg').dialog('close');
                        $('#dg').datagrid('reload');
                        parent.frames['leftFrame'].location.reload(true);
                    }
                }
            });
        }


        function editrow(obj) {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#dlg1').dialog('open').dialog('setTitle', '编辑角色');
                $('#fm1').form('load', row);
            }
        }

        function updaterow() {
            $('#fm1').form('submit', {
                url: '<%=basePath%>/tymng/auth/role/update',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                data: {"roleId": $('#roleId').val(), "roleName": $('#roleName').val()},
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.ret == -1) {
                        $.messager.alert('错误', result.message);
                    } else {
                        $('#dlg1').dialog('close');
                        $('#dg').datagrid('reload');
                        parent.frames['leftFrame'].location.reload(true);
                    }
                }
            });
        }


        function deleterow() {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $.messager.confirm('提示', '确定要角色[' + row.roleName + ']及其所有子角色么?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/tymng/auth/role/delete', {roleId: row.roleId}, function (result) {
                            if (result.ret == -1) {
                                $.messager.alert('错误', res.message);
                            } else {
                                //$('#dg').datagrid('reload');
                                window.self.location.reload(true);
                                parent.frames['leftFrame'].location.reload(true);
                            }
                        }, 'json');
                    }
                });
            }
        }
    </script>
</head>
<body>
<div id="dg"></div>
<div id="toolbar">
    <div>
        <table>
            <tr>
                <th>
                    <shiro:hasPermission name="system_role_add">
                        <a href="#" class="easyui-linkbutton" id="add" onclick="addrow()">新建</a>
                    </shiro:hasPermission>
                </th>
                <th>
                    <shiro:hasPermission name="system_role_update">
                        <a href="#" class="easyui-linkbutton" id="update" onclick="editrow(this)">修改</a>
                    </shiro:hasPermission>
                </th>
                <th>
                    <shiro:hasPermission name="system_role_delete">
                        <a href="#" class="easyui-linkbutton" id="delete" onclick="deleterow()">删除</a>
                    </shiro:hasPermission>
                </th>
            </tr>
        </table>
    </div>
</div>
<div id="dlg" class="easyui-dialog"
     style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
    <div class="ftitle">新建角色</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>角色名称:</label> <input name="roleName" id="addRoleName" class="easyui-validatebox" required="true">
            <input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

<div id="dlg1" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
     buttons="#dlg1-buttons">
    <div class="ftitle">修改角色</div>
    <br/>

    <form id="fm1" method="post" novalidate>
        <input type="hidden" id="roleId" name="roleId" value="${roleId}"/>

        <div class="fitem">
            <label>角色名称:</label> <input name="roleName" id="roleName" class="easyui-validatebox" required="true">
        </div>
    </form>
</div>
<div id="dlg1-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updaterow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg1').dialog('close')">取消</a>
</div>
</body>
</html>