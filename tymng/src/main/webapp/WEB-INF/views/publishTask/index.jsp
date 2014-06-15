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
            $("#groupId").combobox('select', data[0].value);
        }
        function saverow() {
            $('#fm').form('submit', {
                url: '<%=basePath%>/publishTask/insert',
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
                        var apkHtml = "<div>" +
                                "<input type='hidden' name='apkId' value='" + packageApkRefList[i].apkId + "'>" +
                                "<input type='text' name='apkName' value='" + packageApkRefList[i].apkName + "' readonly='readonly'>" +
                                " " + autoRunMsg + " " + desktopIconMsg +
                                "<input type='hidden' name='autoRun' value='" + autoRunVal + "'>" +
                                "<input type='hidden' name='desktopIcon' value='" + desktopIconVal + "'>" +
                                "<a href='javascript:void(0)' class='easyui-linkbutton' onclick='javascript:$(this).parent().remove();'>删除</a>" +
                                "</div>";
                        $('#upApkList').append(apkHtml);
                    }
                }
            }
        }

        function delrow() {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $.messager.confirm('提示', '确定要删除[' + row.publishId + ']?', function (r) {
                    if (r) {
                        $.post('<%=basePath%>/publishTask/delete', {publishId: row.publishId}, function (result) {
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
                url: "<%=basePath%>/publishTask/list",
                queryParams: {packageNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/publishTask/list',
                queryParams: {type: '${type}'},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                rownumbers: true,
                columns: [
                    [
                        {field: 'publishId', title: '任务编号', align: 'center', width: 150},
                        {field: 'packageName', title: '安装包名称', align: 'center', width: 500},
                        {field: 'createTime', title: '创建日期', align: 'center', width: 200,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
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
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/packageInfo/list',
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
            $('#packagedg').datagrid({
                url: "<%=basePath%>/packageInfo/list",
                queryParams: {packageNameCondition: value}
            });
        }
        function selectPackage(packageId, packageName, pkgType, groupId) {
            $("#packageId").val(packageId);
            $("#packageName").val(packageName);
            $("#pkgType").val(pkgType);
            if ("Y" == pkgType) {
                $("#groupId").combobox('select', groupId);
                $("#groupId").attr("readonly", true);
                $("#channelDiv").hide();
            } else {
                $("#groupId").attr("readonly", false);
                $("#channelDiv").show();
            }
            $('#packagedlg').dialog('close');
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
                    <input type="text" name="searchValue" id="searchValue" placeholder="安装包名称"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="searchEvt()">查询</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                </td>
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">查看详细</a>
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
     buttons="#dlg-buttons">
    <div class="ftitle"> 发布任务管理</div>
    <br/>

    <form id="fm" novalidate>
        <div class="fitem">
            <label>选择安装包:</label>
            <input id="packageName" name="packageName" readonly="readonly">
            <input id="packageId" name="packageId" readonly="readonly" type="hidden">
            <input id="pkgType" name="pkgType" readonly="readonly" type="hidden">
            <a href="javascript:void(0)"
               onclick="showPackageDialog()">选择</a>
        </div>
        <div class="fitem" style="margin-left:12px">
            <label>发布时间:</label>
            <input type="text" id="effectTime" name="effectTime" class="easyui-datebox">
        </div>
        <div class="fitem" id="groupIdDiv">
            <label>选择渠道组:</label>
            <select class="easyui-combobox" name="groupId" id="groupId" style="width:150px;">
                <option value="1">天音渠道</option>
                <option value="2">地包渠道</option>
                <option value="3">其他渠道</option>
            </select>
        </div>
        <div class="fitem" id="channelDiv">
            <label>选择安装仓库:</label>
            <a href="javascript:void(0)"
               onclick="showChannelDialog()">选择</a>

            <div id="addChannelList">
            </div>
        </div>
        <div class="fitem">
            <label>选择安装机型:</label>
            <a href="javascript:void(0)"
               onclick="showModelDialog()">选择</a>

            <div id="addModelList">
            </div>
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
    <form id="upfm" novalidate>
        <input type="hidden" id="packageId" name="packageId"/>

        <div class="fitem">
            <label><font color="red">*</font>安装包名称:</label>
            <input type="text" name="packageName" class="easyui-validatebox" readonly="readonly">

            <div class="fitem" style="margin-left:23px">
                <label><font color="red">*</font>批次号:</label>
                <input name="batchCode" readonly="readonly">
            </div>
            <div class="fitem" style="margin-left:40px">
                <label>备注:</label>
                <input name="remark" class="easyui-validatebox" maxlength="100" readonly="readonly">
            </div>
            <div class="fitem" id="upGroupIdDiv">
                <label>通用包渠道组:</label>
                <input name="groupName" class="easyui-validatebox" maxlength="100" readonly="readonly">
            </div>
            <div class="fitem" style="margin-left:21px">
                <label>选择APK:</label>
                <a href="javascript:void(0)"
                   onclick="showApkDialog(2)">选择</a>

                <div id="upApkList">
                </div>
            </div>
        </div>
    </form>
</div>

</div>
<div id="update-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdate()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#updatedlg').dialog('close')">取消</a>
</div>


<div id="packagedlg" class="easyui-dialog" style="width:600px;height:400px;padding:10px 20px" closed="true"
     buttons="#packagedlg-buttons">
    <div>
        <div>
            <table>
                <tr>
                    <td>
                        <input type="text" name="searchPackageValue" id="searchPackageValue" placeholder="安装包名称"/>
                    </td>
                    <td align="center">
                        <a id="searchApkBtn" href="javascript:void(0)" class="easyui-linkbutton"
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
       onclick="javascript:$('#packagedlg').dialog('close')">取消</a>
</div>

</body>
</html>