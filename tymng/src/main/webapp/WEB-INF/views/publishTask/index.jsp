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
var addModelList;
var addChannelList;
function addrow() {
    $('#dlg').dialog('open').dialog('setTitle', '新增');
    $('#fm').form('clear');
    addModelList = new Array();
    addChannelList = new Array();
    $("#addModelList").empty();
    $("#addChannelList").empty();
    var data = $("#groupId").combobox("getData");
    $('#groupId').combobox("select", data[0].value);
    $('#groupId').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue != oldValue) {
                addModelList = new Array();
                addChannelList = new Array();
                $("#addModelList").empty();
                $("#addChannelList").empty();
            }
        }
    });
    $('#effectTime').datebox('setValue', getCurrrentDateStr());
}
function saverow() {
    $('#fm').form('submit', {
        url: '<%=basePath%>/tymng/publishTask/insert',
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
        $('#updatedlg').dialog('open').dialog('setTitle', '详情');
        $('#upfm').form('clear');
        $('#updateChannelDiv').show();
        $('#updateModelDiv').show();
        $('#updateChannelList').empty();
        $('#updateModelList').empty();
        row.effectTime = new Date(row.effectTime).formate("yyyy-MM-dd");
        $('#upfm').form('load', row);
        $("#upPackageName").val(row.packageName);
        var pubChlList = row.pubChlList;
        var pubModList = row.pubModList;
        if (pubChlList != null && pubChlList.length > 0) {
            $("#upGroupName").val(pubChlList[0].groupName);
            for (var i = 0; i < pubChlList.length; i++) {
                if (pubChlList[i].channelName) {
                    var chlHtml = "<tr>" +
                            "<td><input type='text' value='" + pubChlList[i].channelName + "' readonly='readonly'></td>" +
                            "</tr>";
                    $('#updateChannelList').append(chlHtml);
                } else {
                    $('#updateChannelDiv').hide();
                }
            }
        }
        if (pubModList != null && pubModList.length > 0) {
            for (var i = 0; i < pubModList.length; i++) {
                if (pubModList[i]) {
                    var modHtml = "<tr>" +
                            "<td><input type='text' value='" + pubModList[i].modelName + "' readonly='readonly'></td>" +
                            "</tr>";
                    $('#updateModelList').append(modHtml);
                } else {
                    $('#updateModelDiv').hide();
                }
            }
        }
    }
}

function delrow() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示', '确定要删除[' + row.publishId + ']?', function (r) {
            if (r) {
                $.post('<%=basePath%>/tymng/publishTask/delete', {publishId: row.publishId}, function (result) {
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
        url: "<%=basePath%>/tymng/publishTask/list",
        queryParams: {packageNameCondition: value}
    });
}

function initPage() {
    $('#dg').datagrid({
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/publishTask/list',
        queryParams: {type: '${type}'},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        columns: [
            [
                {field: 'publishId', title: '任务编号', align: 'center', width: 150},
                {field: 'packageName', title: '安装包名称', align: 'center', width: 500},
                {field: 'effectTime', title: '发布日期', align: 'center', width: 200,
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

function showPackageDialog() {
    $('#packagedlg').dialog('open').dialog('setTitle', '选择安装包');
    $('#packagedg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/packageInfo/list',
        queryParams: {},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'packageName', title: '安装包名称', align: 'center', width: 150},
                {field: 'type', title: '安装包类型', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        if ("N" == value) {
                            return "普通包";
                        }
                        if ("Y" == value) {
                            return "通用包";
                        }
                    }
                },
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectPackage('" + row.packageId + "','" + row.packageName + "','" + row.type + "','" + row.groupId + "')>选择</a>";
                    }
                }
            ]
        ]
    });
}
function searchPackageEvt() {
    var value = $('#searchPackageValue').val();
    var searchPackageGroupIdValue = $('#searchPackageGroupIdValue').combobox('getValue');
    $('#packagedg').datagrid({
        url: "<%=basePath%>/tymng/packageInfo/list",
        queryParams: {packageNameCondition: value, groupIdCondition: searchPackageGroupIdValue}
    });
}
function selectPackage(packageId, packageName, pkgType, groupId) {
    $("#packageId").val(packageId);
    $("#packageName").val(packageName);
    $("#pkgType").val(pkgType);
    if ("Y" == pkgType) {
        //$("#groupId").combobox('select', groupId);
        $("#groupId").combobox('disable');
        $("#channelDiv").hide();
        $("#addChannelList").empty();
        $("#modelDiv").hide();
        $("#addModelList").empty();
        $("#groupId").combobox('select', groupId);
        $("input[name=groupId]").removeAttr("disabled");
    } else {
        $("#groupId").combobox('enable');
        $("#channelDiv").show();
        $("#modelDiv").show();
    }
    $('#packagedlg').dialog('close');
}

function showModelDialog() {
    $('#modeldlg').dialog('open').dialog('setTitle', '选择机型');
    var groupId = $('#groupId').combobox('getValue');
    $('#modeldg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/modelInfo/list',
        queryParams: {groupId: groupId},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'modelName', title: '机型名称', align: 'center', width: 150},
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectModel('" + row.modelId + "','" + row.modelName + "')>选择</a>";
                    }
                }
            ]
        ]
    });
}
function searchModelEvt() {
    var value = $('#searchModelValue').val();
    var groupId = $('#groupId').combobox('getValue');
    $('#modeldg').datagrid({
        url: "<%=basePath%>/tymng/modelInfo/list",
        queryParams: {modelNameCondition: value, groupId: groupId}
    });
}
function selectModel(modelId, modelName) {
    var modelHtml = "<tr>" +
            "<input type='hidden' name='modelId' value='" + modelId + "'>" +
            "<td><input type='text' name='modelName' value='" + modelName + "' readonly='readonly'></td>" +
            "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();addModelList.pop(" + modelId + ");'>删除</a></td>" +
            "</tr>";
    if (!addModelList.in_array(modelId)) {
        addModelList.push(modelId);
        $('#addModelList').append(modelHtml);
    }
}

function showChannelDialog() {
    $('#channeldlg').dialog('open').dialog('setTitle', '选择渠道');
    var groupId = $('#groupId').combobox('getValue');
    $('#tt').tree({
        checkbox: true,
        url: "<%=basePath%>/tymng/channelInfo/listTreeAll?groupId=" + groupId
    });
}
function selectTreeChannels() {
    var nodes = $('#tt').tree('getChecked');
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id != -1) {
            selectChannel(nodes[i].id, nodes[i].text);
        }
    }
    $('#channeldlg').dialog('close')
}
function selectChannel(channelId, channelName) {
    var channelHtml = "<tr>" +
            "<input type='hidden' name='channelId' value='" + channelId + "'>" +
            "<td><input type='text' name='channelName' value='" + channelName + "' readonly='readonly'></td>" +
            "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();addChannelList.pop(" + channelId + ");'>删除</a></td>" +
            "</tr>";
    if (!addChannelList.in_array(channelId)) {
        addChannelList.push(channelId);
        $('#addChannelList').append(channelHtml);
    }
}

</script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="searchValue" id="searchValue" placeholder="安装包名称"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="searchEvt()">查询</a>
                </td>
                <shiro:hasPermission name="publish_task_add">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="publish_task_delete">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="delrow()">删除</a>
                    </td>
                </shiro:hasPermission>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">查看详细</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="dlg" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#dlg-buttons">
    <div class="ftitle"> 发布任务管理</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label><font color="red">*</font>选择安装包:</label>
            <input id="packageName" name="packageName" readonly="readonly">
            <input id="packageId" name="packageId" readonly="readonly" type="hidden">
            <input id="pkgType" name="pkgType" readonly="readonly" type="hidden">
            <a href="javascript:void(0)"
               onclick="showPackageDialog()">选择</a>
        </div>
        <div class="fitem" style="margin-left:12px">
            <label><font color="red">*</font>发布时间:</label>
            <input type="text" id="effectTime" name="effectTime" class="easyui-datebox">
        </div>
        <div class="fitem" id="groupIdDiv">
            <label><font color="red">*</font>选择渠道组:</label>
            <select class="easyui-combobox" name="groupId" id="groupId" style="width:150px;">
                <option value="1">天音渠道</option>
                <option value="2">地包渠道</option>
                <option value="3">其他渠道</option>
            </select>
        </div>
        <div class="fitem" id="channelDiv">
            <label><font color="red">*</font>选择安装仓库:</label>
            <a href="javascript:void(0)"
               onclick="showChannelDialog()">选择</a>

            <table id="addChannelList" style="border: solid thin">
            </table>
        </div>
        <div class="fitem" id="modelDiv">
            <label><font color="red">*</font>选择安装机型:</label>
            <a href="javascript:void(0)"
               onclick="showModelDialog()">选择</a>

            <table id="addModelList" style="border: solid thin">
            </table>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>

<div id="updatedlg" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#update-buttons">
    <form id="upfm" method="post" novalidate>
        <div class="fitem">
            <label>安装包名称:</label>
            <input id="upPackageName" name="packageName" readonly="readonly">
        </div>
        <div class="fitem" style="margin-left:12px">
            <label>发布时间:</label>
            <input type="text" id="upEffectTime" name="effectTime" class="easyui-datebox" readonly="readonly">
        </div>
        <div class="fitem" style="margin-left:23px">
            <label>渠道组:</label>
            <input id="upGroupName" name="groupName" readonly="readonly">
        </div>
        <div class="fitem" id="updateChannelDiv" style="margin-left:12px">
            <label>安装仓库:</label>

            <table id="updateChannelList" style="border: solid thin">
            </table>
        </div>
        <div class="fitem" style="margin-left:12px" id="updateModelDiv">
            <label>安装机型:</label>

            <table id="updateModelList" style="border: solid thin">
            </table>
        </div>
    </form>
</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">关闭</a>
</div>


<div id="packagedlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#packagedlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <select class="easyui-combobox" name="searchPackageGroupIdValue" id="searchPackageGroupIdValue"
                                style="width:150px;">
                            <option value="">全部渠道组</option>
                            <option value="1">天音渠道</option>
                            <option value="2">地包渠道</option>
                            <option value="3">其他渠道</option>
                        </select>
                    </td>
                    <td>
                        <input type="text" name="searchPackageValue" id="searchPackageValue" placeholder="安装包名称"/>
                    </td>
                    <td align="center">
                        <a id="searchPackageBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchPackageEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="packagedg"></div>
</div>
<div id="packagedlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#packagedlg').dialog('close')">关闭</a>
</div>


<div id="modeldlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#modeldlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchModelValue" id="searchModelValue" placeholder="机型名称"/>
                    </td>
                    <td align="center">
                        <a id="searchModelBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchModelEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="modeldg"></div>
</div>
<div id="modeldlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#modeldlg').dialog('close')">关闭</a>
</div>


<div id="channeldlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px"
     data-options="iconCls:'icon-save',resizable:true"
     closed="true"
     buttons="#channeldlg-buttons">
    <%-- <div>
         <div>
             <table>
                 <tr>
                     <td>
                         <input type="text" name="searchChannelValue" id="searchChannelValue" placeholder="渠道/仓库名称"/>
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
     </div>--%>
    <%--<div id="channeldg" region="center"></div>--%>
    <ul id="tt"></ul>
</div>
<div id="channeldlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="javascript:selectTreeChannels()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#channeldlg').dialog('close')">关闭</a>
</div>

</body>
</html>