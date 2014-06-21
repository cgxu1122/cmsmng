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
            $('#fm').form('submit', {
                url: '<%=basePath%>/settleInfo/insert',
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
                row.startTime = new Date(row.startTime).formate("yyyy-MM-dd");
                row.endTime = new Date(row.endTime).formate("yyyy-MM-dd");
                $('#upfm').form('load', row);
            }
        }
        function saveUpdate() {
            $('#upfm').form('submit', {
                url: '<%=basePath%>/settleInfo/update',
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
                        $.post('<%=basePath%>/settleInfo/delete', {settleId: row.settleId}, function (result) {
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
                url: "<%=basePath%>/settleInfo/list",
                queryParams: {modelNameCondition: value}
            });
        }

        function initPage() {
            $('#dg').datagrid({
                width: 'auto',
                height: 'auto',
                striped: true,
                singleSelect: true,
                url: '<%=basePath%>/settleInfo/list',
                queryParams: {groupId: '${groupId}'},
                loadMsg: '数据加载中请稍后……',
                pagination: true,
                pageSize: 100,
                pageList: [50, 100, 200],
                rownumbers: true,
                columns: [
                    [
                        {field: 'modelName', title: '机型名称', align: 'center', width: 100},
                        {field: 'startTime', title: '开始时间', align: 'center', width: 140,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'endTime', title: '结束时间', align: 'center', width: 140,
                            formatter: function (value) {
                                return new Date(value).formate("yyyy-MM-dd");
                            }
                        },
                        {field: 'price', title: '价格', align: 'center', width: 100},
                        {field: 'remark', title: '备注', align: 'center', width: 200},
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
                    <input type="text" name="searchValue" id="searchValue" placeholder="机型名称"/>
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
     buttons="#dlg-buttons">
    <div class="ftitle"></div>
    <br/>

    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label><font color="red">*</font>机型名称:</label>
            <input id="modelName" name="modelName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>开始时间:</label>
            <input type="text" id="startTime" name="startTime" class="easyui-datebox">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>结束时间:</label>
            <input type="text" id="endTime" name="endTime" class="easyui-datebox">
        </div>
        <div class="fitem" style="margin-left:22px">
            <label><font color="red">*</font>单价:</label>
            <input id="price" name="price" class="easyui-validatebox" required="true" maxlength="20">
        </div>
        <div class="fitem" style="margin-left:22px">
            <label><font color="red">*</font>备注:</label>
            <input id="remark" name="remark" class="easyui-validatebox" required="true" maxlength="20">
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
        <input type="hidden" id="settleId" name="settleId"/>

        <div class="fitem">
            <label><font color="red">*</font>机型名称:</label>
            <input id="upmodelName" name="modelName" class="easyui-validatebox" required="true" maxlength="50">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>开始时间:</label>
            <input type="text" id="upstartTime" name="startTime" class="easyui-datebox">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>结束时间:</label>
            <input type="text" id="upendTime" name="endTime" class="easyui-datebox">
        </div>
        <div class="fitem" style="margin-left:22px">
            <label><font color="red">*</font>单价:</label>
            <input id="upprice" name="price" class="easyui-validatebox" required="true" maxlength="20">
        </div>
        <div class="fitem" style="margin-left:22px">
            <label><font color="red">*</font>备注:</label>
            <input id="upremark" name="remark" class="easyui-validatebox" required="true" maxlength="20">
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