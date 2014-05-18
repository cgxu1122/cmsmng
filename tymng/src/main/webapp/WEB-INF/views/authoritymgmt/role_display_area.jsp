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
                url: '<%=basePath%>/role/getById/${id}',
                //queryParams:{},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {
                            field: 'roleName',
                            title: '角色名称',
                            align: 'center',
                            width: 100
                        },
                        {
                            field: 'levels',
                            title: '层级',
                            align: 'center',
                            width: 100
                        },
                        {
                            field: 'fullPath',
                            title: '路径',
                            align: 'center',
                            width: 100
                        },
                        {
                            field: 'ext',
                            title: '扩展',
                            align: 'center',
                            width: 100
                        },
                        {
                            field: 'createTime',
                            title: '添加时间',
                            align: 'center',
                            width: 100
                        },
                        {
                            field: 'parentName',
                            title: '父角色名称',
                            align: 'center',
                            width: 100
                        },
                        {
                            field: 'icon',
                            title: '图片',
                            align: 'center',
                            width: 100
                        },
                        {
                            field: 'id',
                            hidden: true
                        }
                    ]
                ],
                pagination: true,
                toolbar: [
                    {
                        text: '新增',
                        iconCls: 'icon-add',
                        handler: function () {
                            addrow();
                        }
                    },
                    '-',
                    {
                        text: '修改',
                        handler: function () {
                            editrow(this);
                        }
                    },
                    '-',
                    {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () {
                            deleterow();
                        }
                    }
                ]
            });
        }

        function addrow() {
            $('#dlg').dialog('open').dialog('setTitle', '新建角色');
            $('#fm').form('clear');
        }


        function saverow() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/role/insert/${parentId}',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.alert('错误', result.errorMsg);
                        /*   $.messager.show({
                         title: 'Error',
                         msg: result.errorMsg
                         }); */
                    } else {
                        $('#dlg').dialog('close');
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
                url: '<%=basePath%>/role/update',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                data: {"id": $('#id').val(), "roleName": $('#roleName').val(), "icon": $('#icon').val()},
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
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
                $.messager.confirm('提示', '确定要角色[' + row.roleName + ']?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/role/delete', {id: row.id}, function (result) {
                            if (result.success) {
                                $('#dg').datagrid('reload');
                                parent.frames['leftFrame'].location.reload(true);
                            } else {
                                $.messager.show({
                                    title: 'Error',
                                    msg: result.errorMsg
                                });
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
<div id="dlg" class="easyui-dialog"
     style="width: 400px; height: 280px; padding: 10px 20px" closed="true" buttons="#dlg-buttons">
    <div class="ftitle">新建角色</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <input type="hidden" id="parentId" name="parentId" value="${parentId}"/>

        <div class="fitem">
            <label>角色名称:</label> <input name="roleName" class="easyui-validatebox"
                                        required="true">
        </div>
        <div class="fitem">
            <label>图片路径:</label> <input name="icon" class="easyui-validatebox">
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

<div id="dlg1" class="easyui-dialog"
     style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
     buttons="#dlg1-buttons">
    <div class="ftitle">新建角色</div>
    <br/>

    <form id="fm1" method="post" novalidate>
        <input type="hidden" id="id" name="id" value="${id}"/>

        <div class="fitem">
            <label>角色名称:</label> <input name="roleName" id="roleName" class="easyui-validatebox"
                                        required="true">
        </div>
        <div class="fitem">
            <label>图片路径:</label> <input name="icon" id="icon" class="easyui-validatebox">
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