<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/header.jsp" %>

<title>后台用户管理</title>
<script type="text/javascript">
$(document).ready(function () {
    initPage();
});


function addrow() {

    $('#dlg').dialog('open').dialog('setTitle', '新建用户');

    $('#fm').form('clear');
    $("#enable")[0].checked = true;
}


function editrow(obj) {

    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#dlg1').dialog('open').dialog('setTitle', '编辑用户');
        $('#fm1').form('load', row);
    }
}

function updatePassword(obj) {

    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#updatePassworddlg').dialog('open').dialog('setTitle', '修改用户密码');
        $('#upfm').form('load', row);
        document.getElementById("passwordUp").value = "";
        document.getElementById("comfirmPasswordUp").value = "";
    }
}


function saverow() {
    var cp = $('#comfirmPassword').val();
    var p = $('#password').val();
    var nickname = $('#loginName').val().trim();
    if (nickname == null || nickname == "" || nickname.length > 15) {
        $.messager.alert('提示', "输入正确姓名!");
        return;
    }
    if (p != cp) {
        $.messager.alert('提示', "两次输入密码不一致!");
        return;
    }
    if (cp.length > 16) {
        $.messager.alert('提示', "密码长度过长!");
        return;
    }

    alert();
    $('#fm').form('submit', {
        url: '<%=basePath%>/user/insert',
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

function saveUpdatePassword() {
    var cp = $('#comfirmPasswordUp').val();
    var p = $('#passwordUp').val();
    if (p != cp) {
        $.messager.alert('提示', "两次输入密码不一致!");
        return;
    }
    $('#upfm').form('submit', {
        url: '<%=basePath%>/auth/updatePassowrd',
        onSubmit: function () {
            return $(this).form('validate');
        },

        data: {"id": $('#id').val, "password": $('#passwordUp').val},
        success: function (result) {
            r
            var result = eval('(' + result + ')');
            if (result.code == -1) {
                $.messager.alert('失败', result.message);
            } else {
                $.messager.alert('成功', result.message);
                $('#updatePassworddlg').dialog('close');
                $('#dg').datagrid('reload');
            }
        }
    });

}
function updaterow() {

    var nickname = $('#nicknameUp').val().trim();
    if (nickname == null || nickname == "" || nickname.length > 15) {
        $.messager.alert('提示', "输入正确姓名!");
        return;
    }

    var email = $('#emailUp').val();
    if (email.length > 23) {
        $.messager.alert('提示', "邮箱长度过长!");
        return;
    }


    $('#fm1').form('submit', {
        url: '<%=basePath%>/auth/update',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.code == -1) {
                $.messager.alert('失败', result.message);
            } else {
                $.messager.alert('成功', result.message);
                $('#dlg1').dialog('close');
                $('#dg').datagrid('reload');
            }
        }
    });
}
function deleterow() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示', '确定要删除[' + row.name + ']?', function (r) {
            if (r) {
                $.post('<%=basePath%>/auth/delete', {id: row.id}, function (result) {
                    if (result.success) {
                        $('#dg').datagrid('reload');
                    } else {
                        $.messager.alert('错误', result.errorMsg);
                        /* $.messager.show({
                         title: 'Error',
                         msg: result.errorMsg
                         }); */
                    }
                }, 'json');
            }
        });
    }
}

function initPage() {
    $("#searchbtn").click(function () {
        search();
    });

    $('#dg').datagrid({
        height: '522',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/user/getAll',
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        fitColumns: true,
        rownumbers: true,
        fit: true,
        columns: [
            [
                {field: 'roleName', title: '系统角色', align: 'center', width: 200},
                {field: 'nickname', title: '姓名', align: 'center', width: 100},
                {field: 'email', title: '邮箱', align: 'center', width: 100},
                {field: 'cellphone', title: '手机', align: 'center', width: 200},
                {field: 'telephone', title: '座机', align: 'center', width: 100},
                {field: 'createTime', title: '创建时间', align: 'center', width: 200},
                {field: 'status', title: '系统状态', align: 'center', width: 200, formatter: function (value) {
                    if (value == 1) {
                        return "启用";
                    }
                    return "禁用";
                }
                },
                {field: 'id', title: 'ID', align: 'center', width: 100},
                {field: 'id', hidden: true}


            ]
        ],
        toolbar: "#toolBar"
    });

    //设置角色选择下拉框去获取所有角色列表
    $("#allRoles").combobox({
        url: "<%=basePath%>/user/getAllRole",
        valueField: "id",
        textField: "roleName"
    });
}


/**
 *根据条件查询查询staff列表
 */
function search() {
    var value = $('#searchValue').val();
    $('#dg').datagrid({
        url: "<%=basePath%>/auth/getAll",
        queryParams: { 'searchValue': value}
    });
}

</script>
<script type="text/javascript" src="<%= basePath %>/common/js/validateExtends.js"></script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>

                <td>
                    <input type="text" name="searchValue" id="searchValue" placeholder="姓名/邮箱/角色名称"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>

                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                </td>

                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">修改用户信息</a>
                </td>

                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="updatePassword()">修改用户密码</a>
                </td>


            </tr>
        </table>
    </div>
</div>
<div id="dg"></div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">用户</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <div class="fitem" style="margin-left:11px">
            <label><font color="red">*</font>登录名:</label>
            <input id="loginName" name="loginName" class="easyui-validatebox" required="true">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>姓名:</label>
            <input id="realName" name="realName" class="easyui-validatebox" required="true">
        </div>
        <div class="fitem" style="margin-left:25px">
            <label><font color="red">*</font>密码:</label>
            <input type="password" id="password" name="password" class="easyui-validatebox" required="true"
                   validType="isPasswd">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>确认密码:</label>
            <input type="password" id="comfirmPassword" name="comfirmPassword" class="easyui-validatebox"
                   required="true">
        </div>
        <div class="fitem" style="margin-left:30px">
            <label>手机:</label>
            <input name="cellphone" class="easyui-numberbox" validType="digits">
        </div>
        <div class="fitem" style="margin-left:30px">
            <label>地址:</label>
            <input name="address" class="easyui-numberbox" maxlength="200">
        </div>
        <div class="fitem" style="margin-left:30px">
            <label>角色:</label>
            <select  name="roleId" class="easyui-combogrido" required="true" style="width:160px"
                    data-options="
		            panelWidth: 300,
		            idField: 'roleId',
		            textField: 'roleName',
		            url: '<%=basePath%>/user/getAllRole',
		            method: 'get',
		            columns: [[
		                {field:'roleId',hidden:true},
		                {field:'roleName',title:'角色名称',width:120}
		            ]],
		            fitColumns: true
		        ">
            </select>
        </div>
        <div class="fitem">
            <label><font color="red">*</font>状态:</label>
            <input type="radio" name="status" value="1" id="enable" checked="checked"/><span>启用</span>
            <input type="radio" name="status" value="2" id="disable"/><span>禁用</span>
        </div>
        <div class="fitem">
            <label><font color="red">*</font>用户类型:</label>
            <input type="radio" name="type" value="1" id="normal" checked="checked"/><span>普通用户</span>
            <input type="radio" name="type" value="2" id="manager"/><span>负责人</span>
        </div>
    </form>
</div>
<div id="dlg-buttons" style="text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saverow()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>
<div>

    <div id="updatePassworddlg" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px" closed="true"
         buttons="#updatePasswprd-buttons">
        <form id="upfm" method="post" novalidate>
            <input type="hidden" id="id" name="id"/>

            <div class="fitem" style="margin-left:25px">
                <label><font color="red">*</font>密码:</label>
                <input id="passwordUp" type="password" name="password" class="easyui-validatebox" required="true"
                       validType="isPasswd">
            </div>
            <div class="fitem">
                <label><font color="red">*</font>确认密码:</label>
                <input type="password" id="comfirmPasswordUp" name="comfirmPassword" class="easyui-validatebox"
                       required="true">
            </div>
        </form>
    </div>

    <div id="updatePasswprd-buttons" style="text-align: center;">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUpdatePassword()">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#updatePassworddlg').dialog('close')">取消</a>
    </div>


    <div id="dlg1" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true"
         buttons="#dlg1-buttons">
        <div class="ftitle">用户</div>
        <br/>

        <form id="fm1" method="post" novalidate>
            <input type="hidden" id="id" name="id"/>

            <div class="fitem" style="margin-left:25px">
                <label><font color="red">*</font>邮箱:</label>
                <input id="emailUp" name="email" class="easyui-validatebox" validType="email" required="true">
            </div>
            <div class="fitem" style="margin-left:25px">
                <label><font color="red">*</font>姓名:</label>
                <input id="nicknameUp" name="nickname" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem" style="margin-left:30px">
                <label>角色:</label>
                <select name="roleId" class="easyui-combogrid" required="true" style="width:160px"
                        data-options="
		            panelWidth: 300,
		            idField: 'roleId',
		            textField: 'roleName',
		            url: '<%=basePath%>/user/getAllRole',
		            method: 'get',
		            columns: [[
		                {field:'roleId',hidden:true},
		                {field:'roleName',title:'角色名称',width:120}
		            ]],
		            fitColumns: true
		        ">
                </select>
            </div>
            <div class="fitem">
                <label><font color="red">*</font>系统状态:</label>
                <input type="radio" name="status" value="1" ><span>启用</span>
                <input type="radio" name="status" value="2"/><span>禁用</span>
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