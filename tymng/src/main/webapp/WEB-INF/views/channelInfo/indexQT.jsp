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
        }
        function saverow() {
            $("#groupId").val("${groupId}");
            $("#parentId").val($("#parentIdCondition").val());
            $('#fm').form('submit', {
                url: '<%=basePath%>/channelInfo/insert',
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
                        reloadTree();
                    }
                }
            });
        }

        function editrow() {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#updatedlg').dialog('open').dialog('setTitle', '修改');
                $('#upfm').form('load', row);
            }
        }
        function saveUpdate() {
            $('#upfm').form('submit', {
                url: '<%=basePath%>/channelInfo/update',
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
                        reloadTree();
                    }
                }
            });
        }

        function delrow() {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $.messager.confirm('提示', '确定要删除[' + row.channelName + ']?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/channelInfo/delete', {channelId: row.channelId}, function (result) {
                            if (result.errorMsg) {
                                $.messager.alert('错误', result.errorMsg);
                            } else {
                                $('#dg').datagrid('reload');
                                reloadTree();
                            }
                        }, 'json');
                    }
                });
            }
        }

        function searchEvt() {
            var value = $('#searchValue').val();
            var parentIdCondition = $('#parentIdCondition').val();
            $('#dg').datagrid({
                url: "<%=basePath%>/channelInfo/list",
                queryParams: {groupId: '${groupId}', channelNameCondition: value, parentIdCondition: parentIdCondition}
            });
        }

        function initPage() {
            reloadTree();
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/channelInfo/list',
                queryParams: {groupId: '${groupId}'},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'channelId', title: '渠道id', align: 'center', width: 150},
                        {field: 'channelName', title: '渠道名称', align: 'center', width: 200},
                        {field: 'groupName', title: '渠道组织', align: 'center', width: 200},
                        {field: 'createTime', title: '创建日期', align: 'center', width: 200,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        }
                    ]
                ]
            });
        }

        function reloadTree() {
            $('#tt').tree({
                url: "<%=basePath%>/channelInfo/listTree?groupId=${groupId}",
                onClick: function (node) {
                    $('#parentIdCondition').val(node.id);
                    searchEvt();
                },
                onBeforeExpand: function (node, param) {
                    $('#tt').tree('options').url = "<%=basePath%>/channelInfo/listTree?groupId=${groupId}&parentIdCondition=" + node.id;
                }
            });
        }
    </script>
    <style type="text/css">
        .datagrid .datagrid-pager {
            position: relative;
        }
    </style>
</head>
<body class="easyui-layout">
<div class="easyui-panel" region="west" style="padding:5px;width: 200px;">
    <ul id="tt"></ul>
</div>
<div class="easyui-panel" region="center" style="padding:5px;">
    <div id="toolBar">
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchValue" id="searchValue" placeholder="渠道名称"/>
                        <input type="hidden" name="parentIdCondition" id="parentIdCondition"/>
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
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">渠道</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <input type="hidden" id="groupId" name="groupId"/>
        <input type="hidden" id="parentId" name="parentId"/>

        <div class="fitem">
            <label><font color="red">*</font>渠道名称:</label>
            <input id="channelName" name="channelName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     buttons="#update-buttons">
    <form id="upfm" method="post" novalidate>
        <input type="hidden" id="channelId" name="channelId"/>

        <div class="fitem">
            <label><font color="red">*</font>渠道名称:</label>
            <input type="text" name="channelName" class="easyui-validatebox" required="true"
                    >
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