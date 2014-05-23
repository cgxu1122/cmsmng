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
                        {field: 'channelId', title: '渠道id', align: 'center', width: 50},
                        {field: 'channelName', title: '渠道名称', align: 'center', width: 100},
                        {field: 'groupName', title: '渠道组织', align: 'center', width: 100},
                        {field: 'username', title: '用户名', align: 'center', width: 100},
                        {field: 'password', title: '密码', align: 'center', width: 100},
                        {field: 'address', title: '地址', align: 'center', width: 100},
                        {field: 'contact', title: '联系人', align: 'center', width: 100},
                        {field: 'phone', title: '联系方式', align: 'center', width: 100},
                        {field: 'laowuName', title: '劳务公司', align: 'center', width: 100},
                        {field: 'createTime', title: '创建日期', align: 'center', width: 100,
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

        function showLaowuDialog(type, upLaowuId) {
            $('#laowudlg').dialog('open').dialog('setTitle', '选择劳务公司');
            $('#laowudg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/channelInfo/list',
                queryParams: {groupId: '4'},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'channelId', title: '劳务id', align: 'center', width: 50},
                        {field: 'channelName', title: '劳务名称', align: 'center', width: 200},
                        {field: 'createTime', title: '创建日期', align: 'center', width: 150,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'action', title: '操作', align: 'center', width: 100,
                            formatter: function (value, row, index) {
                                return "<a href='javascript:void(0)' onclick=javascript:selectLaowu('" + row.channelId + "','" + row.channelName + "','" + type + "')>选择</a>";
                            }
                        }
                    ]
                ]
            });
        }
        function searchLaowuEvt() {
            var value = $('#searchLaowuValue').val();
            $('#laowudg').datagrid({
                url: "<%=basePath%>/channelInfo/list",
                queryParams: {groupId: '4', channelNameCondition: value}
            });
        }
        function selectLaowu(laowuId, laowuName, type) {
            if (type == 2) {//修改
                $("#upLaowuId").val(laowuId);
                $("#upLaowuName").val(laowuName);
            } else if (type == 1) {//新增
                $("#laowuId").val(laowuId);
                $("#laowuName").val(laowuName);
            }
            $('#laowudlg').dialog('close');
        }
    </script>
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
        <div class="fitem" style="margin-left:13px">
            <label><font color="red">*</font>用户名:</label>
            <input id="username" name="username" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>密码:</label>
            <input id="password" name="password" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:31px">
            <label>地址:</label>
            <input id="address" name="address" class="easyui-validatebox" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:13px">
            <label><font color="red">*</font>联系人:</label>
            <input id="contact" name="contact" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:6px">
            <label>联系方式:</label>
            <input id="phone" name="phone" class="easyui-validatebox" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:13px">
            <label>负责人:</label>
            <input id="mngId" name="mngId" class="easyui-validatebox" maxlength="50">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>劳务公司:</label>
            <input type="hidden" id="laowuId" name="laowuId">
            <input type="text" id="laowuName" name="laowuName" readonly="readonly">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="showLaowuDialog(1)">选择劳务公司</a>
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
        <div class="fitem" style="margin-left:13px">
            <label><font color="red">*</font>用户名:</label>
            <input type="text" name="username" readonly="readonly">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>密码:</label>
            <input type="text" name="password" class="easyui-validatebox"
                   required="true">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>地址:</label>
            <input type="text" name="address" class="easyui-validatebox"
                   required="true">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>联系人:</label>
            <input type="text" name="contact" class="easyui-validatebox"
                   required="true">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>联系方式:</label>
            <input type="text" name="phone" class="easyui-validatebox"
                   required="true">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>负责人:</label>
            <input type="text" name="mngId" class="easyui-validatebox"
                   required="true">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>劳务公司:</label>
            <input type="hidden" name="laowuId" id="upLaowuId">
            <input type="text" name="laowuName" id="upLaowuName" readonly="readonly">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="javascript:showLaowuDialog(2,$('#upLaowuId').val())">选择劳务公司</a>
        </div>
    </form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">取消</a>
</div>

<div id="laowudlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
     buttons="#laowudlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchLaowuValue" id="searchLaowuValue" placeholder="劳务名称"/>
                    </td>
                    <td align="center">
                        <a id="searchLaowubtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                           onclick="searchLaowuEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="laowudg"></div>
</div>
<div id="laowudlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#laowudlg').dialog('close')">取消</a>
</div>

</body>
</html>