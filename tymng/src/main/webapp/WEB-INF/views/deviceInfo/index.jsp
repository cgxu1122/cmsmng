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
    var data = $('#groupId').combobox('getData');
    $("#groupId ").combobox('select', data[0].value);
    $('#groupId').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue != oldValue) {
                $("#channelId").val("");
                $("#channelName").val("");
                reloadTree(newValue);
            }
        }
    });
}
function saverow() {
    $('#fm').form('submit', {
        url: '<%=basePath%>/deviceInfo/insert',
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
        $("#upGroupIdHidden").val(row.groupId);
        $('#upGroupId').combobox({
            onChange: function (newValue, oldValue) {
                $("#upGroupIdHidden").val(newValue);
                if (newValue != oldValue) {
                    $("#upChannelId").val("");
                    $("#upChannelName").val("");
                    reloadTree(newValue);
                }
            }
        });
    }
}
function saveUpdate() {
    $('#upfm').form('submit', {
        url: '<%=basePath%>/deviceInfo/update',
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
        $.messager.confirm('提示', '确定要删除[' + row.deviceCode + ']?', function (r) {
            if (r) {
                $.post('<%=basePath%>/deviceInfo/delete', {deviceId: row.deviceId}, function (result) {
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
        url: "<%=basePath%>/deviceInfo/list",
        queryParams: {deviceCodeCondition: value}
    });
}

function initPage() {
    $('#dg').datagrid({
        width: 'auto',
        height: 'auto',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/deviceInfo/list',
        queryParams: {},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        columns: [
            [
                {field: 'deviceId', title: '设备id', align: 'center', width: 100},
                {field: 'deviceCode', title: '设备编码', align: 'center', width: 300},
                {field: 'channelName', title: '设备所属仓库/渠道', align: 'center', width: 300},
                {field: 'groupName', title: '渠道组织', align: 'center', width: 200},
                {field: 'createTime', title: '开始日期', align: 'center', width: 200,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd HH:mm:ss");
                    }
                },
                {field: 'updateTime', title: '修改日期', align: 'center', width: 200,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd HH:mm:ss");
                    }
                }
            ]
        ]
    });
}
function showChannelDialog(type, upChannelId) {
    var groupId = 1;
    if (type == 1) {
        var selectGroupId = $("#groupId").combobox("getValue");
        if (selectGroupId == "") {
            $.messager.alert('错误', "请先选择渠道组织!");
            return;
        }
        groupId = selectGroupId;
    } else if (type == 2) {
        groupId = $("#upGroupIdHidden").val();
    }
    reloadTree(groupId);
    $("#searchGroupIdValue").val(groupId);
    $('#channeldlg').dialog('open').dialog('setTitle', '选择渠道/仓库');
    $('#channeldg').datagrid({
        width: 'auto',
        height: 'auto',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/channelInfo/listAll',
        queryParams: {groupId: groupId},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'channelId', title: '渠道/仓库id', align: 'center', width: 100},
                {field: 'channelName', title: '渠道/仓库名称', align: 'center', width: 150},
                {field: 'createTime', title: '创建日期', align: 'center', width: 150,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd");
                    }
                },
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectChannel('" + row.channelId + "','" + row.channelName + "','" + type + "')>选择</a>";
                    }
                }
            ]
        ]
    });
}
function reloadTree(groupId) {
    /* $('#tt').tree({
     url: "
    <%=basePath%>/channelInfo/listTree?groupId=" + groupId,
     onClick: function (node) {
     $('#parentIdCondition').val(node.id);
     searchChannelEvt();
     },
     onBeforeExpand: function (node, param) {
     $('#tt').tree('options').url = "
    <%=basePath%>/channelInfo/listTree?groupId=" + groupId + "&parentIdCondition=" + node.id;
     }
     });*/
    //需求改为：不加树结构，默认全部展现出来
    $('#searchGroupIdValue').val(groupId);
    searchChannelEvt();
}
function searchChannelEvt() {
    var value = $('#searchChannelValue').val();
    var groupId = $('#searchGroupIdValue').val();
    var parentIdCondition = $('#parentIdCondition').val();
    $('#channeldg').datagrid({
        url: "<%=basePath%>/channelInfo/listAll",
        queryParams: {groupId: groupId, channelNameCondition: value, parentIdCondition: parentIdCondition}
    });
}
function selectChannel(channelId, channelName, type) {
    if (type == 2) {//修改
        $("#upChannelId").val(channelId);
        $("#upChannelName").val(channelName);
    } else if (type == 1) {//新增
        $("#channelId").val(channelId);
        $("#channelName").val(channelName);
    }
    $('#channeldlg').dialog('close');
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
                    <input type="text" name="searchValue" id="searchValue" placeholder="设备编码"/>
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
<div id="dlg" class="easyui-dialog" style="width:450px;height:400px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">设备</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <div class="fitem" style="margin-left:55px">
            <label><font color="red">*</font>设备编码:</label>
            <input id="deviceCode" name="deviceCode" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem" style="margin-left:55px">
            <label><font color="red">*</font>渠道组织:</label>
            <select class="easyui-combobox" name="groupId" id="groupId" style="width:150px;">
                <option value="1">天音渠道</option>
                <option value="2">地包渠道</option>
                <option value="3">其他渠道</option>
            </select>
        </div>
        <div class="fitem">
            <label><font color="red">*</font>设备所属仓库/渠道:</label>
            <input id="channelId" name="channelId" type="hidden">
            <input id="channelName" name="channelName" readonly="readonly">
            <a href="javascript:void(0)" onclick="showChannelDialog(1)">选择所属仓库/渠道</a>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:450px;height:400px;padding:10px 20px" closed="true"
     buttons="#update-buttons">
    <form id="upfm" method="post" novalidate>
        <input type="hidden" id="deviceId" name="deviceId"/>

        <div class="fitem" style="margin-left:55px">
            <label><font color="red">*</font>设备编码:</label>
            <input type="text" name="deviceCode" class="easyui-validatebox" required="true"
                    >
        </div>
        <div class="fitem" style="margin-left:55px">
            <label><font color="red">*</font>渠道组织:</label>
            <input type="hidden" name="upGroupIdHidden" id="upGroupIdHidden"/>
            <select class="easyui-combobox" name="groupId" id="upGroupId" style="width:150px;">
                <option value="1">天音渠道</option>
                <option value="2">地包渠道</option>
                <option value="3">其他渠道</option>
            </select>
        </div>
        <div class="fitem">
            <label><font color="red">*</font>设备所属仓库/渠道:</label>
            <input id="upChannelId" name="channelId" type="hidden">
            <input id="upChannelName" name="channelName" readonly="readonly">
            <a href="javascript:void(0)"
               onclick="showChannelDialog(2,$('#upChannelId').val())">选择所属仓库/渠道</a>
        </div>
    </form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">取消</a>
</div>

<div id="channeldlg" class="easyui-dialog" style="width:800px;height:400px;padding:10px 20px"
     closed="true"
     buttons="#channeldlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchChannelValue" id="searchChannelValue" placeholder="渠道/仓库名称"/>
                        <input type="hidden" name="searchGroupIdValue" id="searchGroupIdValue"/>
                        <input type="hidden" name="parentIdCondition" id="parentIdCondition"/>
                    </td>
                    <td align="center">
                        <a id="searchChannelbtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchChannelEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="channeldg" region="center"></div>
</div>
<div id="channeldlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#channeldlg').dialog('close')">取消</a>
</div>

</body>
</html>