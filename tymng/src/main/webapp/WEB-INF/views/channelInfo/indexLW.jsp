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
            var data = $('#queryImeiSource').combobox('getData');
            $("#queryImeiSource ").combobox('select', data[0].value);
        }
        function saverow() {
            $("#groupId").val("${groupId}");
            $('#fm').form('submit', {
                url: '<%=basePath%>/tymng/channelInfo/insert',
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
                url: '<%=basePath%>/tymng/channelInfo/update',
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
                $.messager.confirm('提示', '确定要删除[' + row.channelName + ']?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/tymng/channelInfo/delete', {channelId: row.channelId}, function (result) {
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
                url: "<%=basePath%>/tymng/channelInfo/list",
                queryParams: {groupId: '${groupId}', channelNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/tymng/channelInfo/list',
                queryParams: {groupId: '${groupId}'},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'channelId', title: '劳务id', align: 'center', width: 100},
                        {field: 'channelName', title: '劳务名称', align: 'center', width: 200},
                        {field: 'groupName', title: '渠道组织', align: 'center', width: 100},
                        {field: 'username', title: '用户名', align: 'center', width: 150},
                        {field: 'password', title: '密码', align: 'center', width: 150},
                        {field: 'queryImeiSource', title: '查看imei权限', align: 'center', width: 100,
                            formatter: function (value) {
                                if (value == "Y")return "有";
                                if (value == "N")return "无";
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
                    <input type="text" name="searchValue" id="searchValue" placeholder="仓库名称"/>
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
    <div class="ftitle">劳务</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <input type="hidden" id="groupId" name="groupId"/>

        <div class="fitem" style="margin-left:15px">
            <label><font color="red">*</font>劳务名称:</label>
            <input id="channelName" name="channelName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:28px">
            <label><font color="red">*</font>用户名:</label>
            <input id="username" name="username" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:40px">
            <label><font color="red">*</font>密码:</label>
            <input id="password" name="password" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem">
            <label>查看imei权限:</label>
            <select class="easyui-combobox" id="queryImeiSource" name="queryImeiSource" style="width:150px;">
                <option value="N">无</option>
                <option value="Y">有</option>
            </select>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#update-buttons">
    <form id="upfm" method="post" novalidate>
        <input type="hidden" id="channelId" name="channelId"/>

        <div class="fitem" style="margin-left:15px">
            <label><font color="red">*</font>劳务名称:</label>
            <input type="text" name="channelName" class="easyui-validatebox" required="true"
                    >
        </div>
        <div class="fitem" style="margin-left:28px">
            <label><font color="red">*</font>用户名:</label>
            <input type="text" name="username" readonly="readonly">
        </div>
        <div class="fitem" style="margin-left:40px">
            <label><font color="red">*</font>密码:</label>
            <input type="text" name="password" class="easyui-validatebox"
                   required="true">
        </div>
        <div class="fitem">
            <label>查看imei权限:</label>
            <select class="easyui-combobox" name="queryImeiSource" style="width:150px;">
                <option value="N">无</option>
                <option value="Y">有</option>
            </select>
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