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
function moveUp(obj) {
    var pre = obj.prev();
    obj.insertBefore(pre);
}
function moveDown(obj) {
    var next = obj.next();
    next.insertBefore(obj);
}

var addApkList;
var updateApkList;
function addrow() {
    $('#dlg').dialog('open').dialog('setTitle', '新增');
    $('#fm').form('clear');
    $("#addApkList").empty();
    addApkList = new Array();
    if ("${type}" == "N") {
        $("#groupIdDiv").empty();
    } else if ("${type}" == "Y") {
        var data = $('#groupId').combobox('getData');
        $("#groupId ").combobox('select', data[0].value);
    }
}
function saverow() {
    $("#type").val("${type}");
    $('#fm').form('submit', {
        url: '<%=basePath%>/tymng/packageInfo/insert',
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
function copyrow() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', '复制');
        $('#fm').form('clear');
        $("#addApkList").empty();
        addApkList = new Array();
        if ("${type}" == "N") {
            $("#groupIdDiv").empty();
        } else if ("${type}" == "Y") {
            var data = $('#groupId').combobox('getData');
            $("#groupId ").combobox('select', data[0].value);
        }
        $('#fm').form('load', row);
        var packageApkRefList = row.packageApkRefList;
        if (packageApkRefList) {
            for (var i = 0; i < packageApkRefList.length; i++) {
                addApkList.push(packageApkRefList[i].apkId);
                var autoRunVal = packageApkRefList[i].autoRun;
                var desktopIconVal = packageApkRefList[i].desktopIcon;
                var autoRunMsg = "非自启动";
                var desktopIconMsg = "不创建快捷";
                if ("Y" == autoRunVal) {
                    autoRunMsg = "自启动";
                }
                if ("Y" == desktopIconVal) {
                    desktopIconMsg = "创建快捷";
                }
                var apkHtml = "<tr>" +
                        "<input type='hidden' name='apkId' value='" + packageApkRefList[i].apkId + "'>" +
                        "<input type='hidden' name='apkType' value='" + packageApkRefList[i].apkType + "'>" +
                        "<td><input type='text' name='apkName' value='" + packageApkRefList[i].apkName + "' readonly='readonly'></td>" +
                        "<td>" + autoRunMsg + "</td><td>" + desktopIconMsg + "</td>" +
                        "<input type='hidden' name='autoRun' value='" + autoRunVal + "'>" +
                        "<input type='hidden' name='desktopIcon' value='" + desktopIconVal + "'>" +
                        "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveUp($(this).parent().parent())'>上移</a></td>" +
                        "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveDown($(this).parent().parent())'>下移</a></td>" +
                        "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();addApkList.pop(" + packageApkRefList[i].apkId + ");'>删除</a></td>" +
                        "</tr>";
                $('#addApkList').append(apkHtml);
            }
        }
    }
}
function editrow() {
    $("#upApkList").empty();
    updateApkList = new Array();
    if ("${type}" == "N") {
        $("#upGroupIdDiv").empty();
    }
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#updatedlg').dialog('open').dialog('setTitle', '修改');
        $('#upfm').form('clear');
        $('#upfm').form('load', row);
        var packageApkRefList = row.packageApkRefList;
        if (packageApkRefList) {
            for (var i = 0; i < packageApkRefList.length; i++) {
                updateApkList.push(packageApkRefList[i].apkId);
                var autoRunVal = packageApkRefList[i].autoRun;
                var desktopIconVal = packageApkRefList[i].desktopIcon;
                var autoRunMsg = "非自启动";
                var desktopIconMsg = "不创建快捷";
                if ("Y" == autoRunVal) {
                    autoRunMsg = "自启动";
                }
                if ("Y" == desktopIconVal) {
                    desktopIconMsg = "创建快捷";
                }
                var apkHtml = "<tr>" +
                        "<input type='hidden' name='apkId' value='" + packageApkRefList[i].apkId + "'>" +
                        "<input type='hidden' name='apkType' value='" + packageApkRefList[i].apkType + "'>" +
                        "<td><input type='text' name='apkName' value='" + packageApkRefList[i].apkName + "' readonly='readonly'></td>" +
                        "<td>" + autoRunMsg + "</td><td>" + desktopIconMsg + "</td>" +
                        "<input type='hidden' name='autoRun' value='" + autoRunVal + "'>" +
                        "<input type='hidden' name='desktopIcon' value='" + desktopIconVal + "'>" +
                        "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveUp($(this).parent().parent())'>上移</a></td>" +
                        "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveDown($(this).parent().parent())'>下移</a></td>" +
                        "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();updateApkList.pop(" + packageApkRefList[i].apkId + ");'>删除</a></td>" +
                        "</tr>";
                $('#upApkList').append(apkHtml);
            }
        }
    }
}
function saveUpdate() {
    $('#upfm').form('submit', {
        url: '<%=basePath%>/tymng/packageInfo/update',
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
        $.messager.confirm('提示', '确定要删除[' + row.packageName + ']?', function (r) {
            if (r) {
                $.post('<%=basePath%>/tymng/packageInfo/delete', {packageId: row.packageId}, function (result) {
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
        url: "<%=basePath%>/tymng/packageInfo/list",
        queryParams: {packageNameCondition: value}
    });
}

function initPage() {
    $('#dg').datagrid({
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/packageInfo/list',
        queryParams: {type: '${type}'},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        columns: [
            [
                {field: 'packageName', title: '安装包名称', align: 'center', width: 150},
                {field: 'batchCode', title: '批次号', align: 'center', width: 300},
                {field: 'remark', title: '备注', align: 'center', width: 400},
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
function getBatchInfoByPackageName(packageName, type) {
    if (packageName == "")return null;
    if (packageName.indexOf("#") <= 0) {
        $.messager.alert('错误', "安装包名称中没有'#',无法匹配批次号！");
        return;
    }
    var batchCode = packageName.substring(packageName.lastIndexOf("#") + 1, packageName.length);
    $.ajax({
        url: "<%=basePath%>/tymng/batchInfo/getBatchInfoByCode?batchCode=" + batchCode,
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.errorMsg) {
                $.messager.alert('错误', result.errorMsg);
                if (type == 1) {
                    $("#batchId").val('');
                    $("#batchCode").val('');
                } else if (type == 2) {
                    $("#upbatchId").val('');
                    $("#upbatchCode").val('');
                }
            } else {
                if (type == 1) {
                    $("#batchId").val(result.batchInfo.batchId);
                    $("#batchCode").val(result.batchInfo.batchCode);
                } else if (type == 2) {
                    $("#upbatchId").val(result.batchInfo.batchId);
                    $("#upbatchCode").val(result.batchInfo.batchCode);
                }
            }
        }
    });
}

function showApkDialog(type) {
    $('#apkdlg').dialog('open').dialog('setTitle', '选择APK');
    $('#apkdg').datagrid({
        width: 'auto',
        height: 'auto',
        fitColumns: true,
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/apkInfo/chooseList',
        queryParams: {},
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        rownumbers: true,
        columns: [
            [
                {field: 'apkName', title: 'APK名称', align: 'center', width: 150},
                {field: 'desktopIcon', title: '是否创建快捷方式', align: 'center', width: 120,
                    formatter: function (value, row, index) {
                        return "<select id='desktopIcon" + row.apkId + "' name='desktopIcon' style='width:100px;'>"
                                + "<option value='N' selected=selected>不创建快捷</option>"
                                + "<option value='Y'>创建快捷</option>"
                                + "</select>";
                    }
                },
                {field: 'autoRun', title: '是否自动运行', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        if (row.type == 2) {
                            return "<select id='autoRun" + row.apkId + "' name='autoRun' style='width:80px;'>"
                                    + "<option value='Y' selected=selected>自启动</option>"
                                    + "</select>";
                        }
                        return "<select id='autoRun" + row.apkId + "' name='autoRun' style='width:80px;'>"
                                + "<option value='N' selected=selected>非自启动</option>"
                                + "<option value='Y'>自启动</option>"
                                + "</select>";
                    }
                },
                {field: 'action', title: '操作', align: 'center', width: 100,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:void(0)' onclick=javascript:selectApk('" + row.apkId + "','" + row.apkName + "','" + row.type + "','" + type + "')>添加</a>";
                    }
                }
            ]
        ]
    });
}
function searchApkEvt() {
    var value = $('#searchApkValue').val();
    $('#apkdg').datagrid({
        url: "<%=basePath%>/tymng/apkInfo/list",
        queryParams: {apkNameCondition: value}
    });
}

function selectApk(apkId, apkName, apkType, type) {
    var autoRunVal = $("#autoRun" + apkId).val();
    var desktopIconVal = $("#desktopIcon" + apkId).val();
    var autoRunMsg = "非自启动";
    var desktopIconMsg = "不创建快捷";
    if ("Y" == autoRunVal) {
        autoRunMsg = "自启动";
    }
    if ("Y" == desktopIconVal) {
        desktopIconMsg = "创建快捷";
    }
    if (type == 2) {//修改
        var apkHtml = "<tr>" +
                "<input type='hidden' name='apkId' value='" + apkId + "'>" +
                "<input type='hidden' name='apkType' value='" + apkType + "'>" +
                "<td><input type='text' name='apkName' value='" + apkName + "' readonly='readonly'></td>" +
                "<td>" + autoRunMsg + "</td><td>" + desktopIconMsg + "</td>" +
                "<input type='hidden' name='autoRun' value='" + autoRunVal + "'>" +
                "<input type='hidden' name='desktopIcon' value='" + desktopIconVal + "'>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveUp($(this).parent().parent())'>上移</a></td>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveDown($(this).parent().parent())'>下移</a></td>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();updateApkList.pop(" + apkId + ");'>删除</a></td>" +
                "</tr>";
        if (!updateApkList.in_array(apkId)) {
            updateApkList.push(apkId);
            $('#upApkList').append(apkHtml);
        }
    } else if (type == 1) {//新增
        var apkHtml = "<tr>" +
                "<input type='hidden' name='apkId' value='" + apkId + "'>" +
                "<input type='hidden' name='apkType' value='" + apkType + "'>" +
                "<td><input type='text' name='apkName' value='" + apkName + "' readonly='readonly'></td>" +
                "<td>" + autoRunMsg + "</td><td>" + desktopIconMsg + "</td>" +
                "<input type='hidden' name='autoRun' value='" + autoRunVal + "'>" +
                "<input type='hidden' name='desktopIcon' value='" + desktopIconVal + "'>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveUp($(this).parent().parent())'>上移</a></td>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='moveDown($(this).parent().parent())'>下移</a></td>" +
                "<td><a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().parent().remove();addApkList.pop(" + apkId + ");'>删除</a></td>" +
                "</tr>";
        if (!addApkList.in_array(apkId)) {
            addApkList.push(apkId);
            $('#addApkList').append(apkHtml);
        }
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
                <shiro:hasPermission name="publish_common_pkg_add">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                    </td>
                </shiro:hasPermission>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="copyrow()">复制</a>
                </td>
                <shiro:hasPermission name="publish_common_pkg_update">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">修改</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="publish_common_pkg_delete">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="delrow()">删除</a>
                    </td>
                </shiro:hasPermission>
            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="dlg" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#dlg-buttons">
    <div class="ftitle">安装包管理</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <input type="hidden" id="type" name="type" value="${type}"/>

        <div class="fitem" style="margin-left:7px">
            <label><font color="red">*</font>安装包名称:</label>
            <input id="packageName" name="packageName" class="easyui-validatebox" required="true" maxlength="50"
                   onblur="javascript:getBatchInfoByPackageName($(this).val(),1)">
        </div>
        <div class="fitem" style="margin-left:31px">
            <label><font color="red">*</font>批次号:</label>
            <input id="batchCode" name="batchCode" readonly="readonly">
            <input id="batchId" name="batchId" readonly="readonly" type="hidden">
        </div>
        <div class="fitem" style="margin-left:48px">
            <label>备注:</label>
            <textarea name="remark" cols="30" rows="4"></textarea>
        </div>
        <div class="fitem" id="groupIdDiv" style="margin-left:1px">
            <label>通用包渠道组:</label>
            <select class="easyui-combobox" name="groupId" id="groupId" style="width:150px;">
                <option value="1">天音渠道</option>
                <option value="2">地包渠道</option>
                <option value="3">其他渠道</option>
            </select>
        </div>
        <div class="fitem" style="margin-left:31px">
            <label>选择APK:</label>
            <a href="javascript:void(0)"
               onclick="showApkDialog(1)">选择</a>

            <table id="addApkList" style="border: solid thin">
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
        <input type="hidden" id="packageId" name="packageId"/>

        <div class="fitem" style="margin-left:8px">
            <label><font color="red">*</font>安装包名称:</label>
            <input id="uppackageName" type="text" name="packageName" class="easyui-validatebox" required="true"
                   maxlength="50"
                   onblur="javascript:getBatchInfoByPackageName($(this).val(),2)">
        </div>
        <div class="fitem" style="margin-left:31px">
            <label><font color="red">*</font>批次号:</label>
            <input id="upbatchCode" name="batchCode" readonly="readonly">
            <input id="upbatchId" name="batchId" readonly="readonly" type="hidden">
        </div>
        <div class="fitem" style="margin-left:48px">
            <label>备注:</label>
            <textarea name="remark" cols="30" rows="4"></textarea>
        </div>
        <div class="fitem" id="upGroupIdDiv">
            <label>通用包渠道组:</label>
            <input name="groupName" class="easyui-validatebox" maxlength="100" readonly="readonly">
        </div>
        <div class="fitem" style="margin-left:31px">
            <label>选择APK:</label>
            <a href="javascript:void(0)"
               onclick="showApkDialog(2)">选择</a>

            <table id="upApkList" style="border: solid thin">
            </table>
        </div>
    </form>
</div>

<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">关闭</a>
</div>


<div id="apkdlg" class="easyui-dialog" style="width:650px;height:500px;padding:10px 20px" closed="true"
     data-options="iconCls:'icon-save',resizable:true"
     buttons="#apkdlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchApkValue" id="searchApkValue" placeholder="apk名称"/>
                    </td>
                    <td align="center">
                        <a id="searchApkBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           iconCls="icon-search"
                           onclick="searchApkEvt()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="apkdg"></div>
</div>
<div id="apkdlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#apkdlg').dialog('close')">关闭</a>
</div>

</body>
</html>