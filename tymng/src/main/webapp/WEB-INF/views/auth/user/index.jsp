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
    $("#normal")[0].checked = true;
}


function editrow(obj) {
    var row = $('#dgg').datagrid('getSelected');
    if (row) {
        $('#dlg1').dialog('open').dialog('setTitle', '编辑用户');
        $('#fm1').form('load', row);
    }
}

function updatePassword(obj) {
    var row = $('#dgg').datagrid('getSelected');
    if (row) {
        $('#userIdUpPw').val(row.userId);
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

    $('#fm').form('submit', {
        method: "post",
        url: '<%=basePath%>/tymng/auth/user/insert',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            result = eval('(' + result + ')');
            if (result.ret == -1) {
                $.messager.alert('错误', result.message);
            } else {
                $.messager.alert('成功', result.message);
                $('#dlg').dialog('close');
                $('#dgg').datagrid('reload');
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
        method: "post",
        url: '<%=basePath%>/tymng/auth/user/updatePassword',
        onSubmit: function () {
            return $(this).form('validate');
        },
        data: {"userId": $('#userIdUpPw').val(), "password": $('#passwordUp').val},
        success: function (result) {
            result = eval('(' + result + ')');
            if (result.code == -1) {
                $.messager.alert('错误', result.message);
            } else {
                $.messager.alert('成功', result.message);
                $('#updatePassworddlg').dialog('close');
                $('#dgg').datagrid('reload');
            }
        }
    });

}
function updaterow() {

    var cellphone = $('#cellphoneUp').val();
    if (cellphone == null || cellphone == "" || cellphone.length > 15) {
        $.messager.alert('提示', "输入手机号!");
        return;
    }

    var address = $('#addressUp').val();
    if (address.length > 100) {
        $.messager.alert('提示', "地址长度过长!");
        return;
    }


    $('#fm1').form('submit', {
        method: "post",
        url: '<%=basePath%>/tymng/auth/user/update',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (result) {
            result = eval('(' + result + ')');
            if (result.code == -1) {
                $.messager.alert('错误', result.message);
            } else {
                $.messager.alert('成功', result.message);
                $('#dlg1').dialog('close');
                $('#dgg').datagrid('reload');
            }
        }
    });
}
function disablerow() {
    var row = $('#dgg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示', '确定要禁用[' + row.roleName + ']?', function (r) {
            if (r) {
                $.post('<%=basePath%>/tymng/auth/user/updateStatus', {userId: row.userId, active: "N"}, function (result) {
                    if (result.ret == 1) {
                        $('#dgg').datagrid('reload');
                    } else {
                        $.messager.alert('错误', result.errorMsg);
                    }
                }, 'json');
            }
        });
    }
}

function enablerow() {
    var row = $('#dgg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示', '确定要启用[' + row.roleName + ']?', function (r) {
            if (r) {
                $.post('<%=basePath%>/tymng/auth/user/updateStatus', {userId: row.userId, active: "Y"}, function (result) {
                    if (result.ret == 1) {
                        $('#dgg').datagrid('reload');
                    } else {
                        $.messager.alert('错误', result.errorMsg);
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

    $("#updateUser").combogrid({
        panelWidth: 300,
        idField: 'roleId',
        textField: 'roleName',
        url: '<%=basePath%>/tymng/user/getAllRole',
        method: 'post',
        columns: [
            [
                {field: 'roleId', hidden: true},
                {field: 'roleName', title: '角色名称', width: 120}
            ]
        ],
        fitColumns: true
    });


    $('#dgg').datagrid({
        height: '522',
        striped: true,
        singleSelect: true,
        url: '<%=basePath%>/tymng/auth/user/list',
        loadMsg: '数据加载中请稍后……',
        pagination: true,
        pageSize: 100,
        pageList: [50, 100, 200],
        fitColumns: true,
        rownumbers: true,
        fit: true,
        columns: [
            [
                {field: 'loginName', title: '登录名', align: 'center', width: 100},
                {field: 'realName', title: '真实姓名', align: 'center', width: 100},
                {field: 'roleName', title: '角色', align: 'center', width: 100},
                {field: 'cellPhone', title: '手机', align: 'center', width: 200},
                {field: 'address', title: '地址', align: 'center', width: 200},
                {field: 'createTime', title: '创建时间', align: 'center', width: 200,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd HH:mm:ss");
                    }
                },
                {field: 'updateTime', title: '修改时间', align: 'center', width: 200,
                    formatter: function (value) {
                        return new Date(value).formate("yyyy-MM-dd HH:mm:ss");
                    }
                },
                {field: 'active', title: '状态', align: 'center', width: 100, formatter: function (value) {
                    if (value == "Y") {
                        return "启用";
                    }
                    return "禁用";
                }
                },
                {field: 'userId', hidden: true}
            ]
        ],
        toolbar: "#toolBar"
    });
}


/**
 *根据条件查询查询staff列表
 */
function search() {
    var value = $('#searchValue').val();
    $('#dgg').datagrid({
        url: "<%=basePath%>/tymng/auth/user/list",
        queryParams: { 'searchValue': value}
    });
}


function selectRole() {
    var url = '<%=basePath%>/tymng/auth/user/tree';
    $('#roleTree_dialog').html("<iframe width='300' id='roleForm' height='400' frameborder=0 scrolling=no src='" + url + "'></iframe>");
    $('#roleTree_dialog').dialog({
        title: '选择角色',
        modal: true,
        width: "auto",
        height: "auto"
    });
}
;
</script>
<script type="text/javascript" src="<%= basePath %>/common/js/validateExtends.js"></script>
</head>
<body>
<div id="toolBar">
    <div>
        <table>
            <tr>
                <td>
                    <input type="text" name="searchValue" id="searchValue" placeholder="登录名/真实姓名"/>
                </td>
                <td align="center">
                    <a id="searchbtn" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
                <shiro:hasPermission name="system_user_add">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addrow()">添加</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="system_user_update">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editrow()">修改用户信息</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="system_user_disable">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="disablerow()">禁用用户</a>
                    </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="system_user_enable">
                <td align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="enablerow()">启用用户</a>
                </td>
                </shiro:hasPermission>
                <shiro:hasPermission name="system_user_update_pw">
                    <td align="center">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="updatePassword()">修改用户密码</a>
                    </td>
                </shiro:hasPermission>
            </tr>
        </table>
    </div>
</div>
<div id="dgg"></div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">
    <div class="ftitle">用户</div>
    <br/>

    <form id="fm" method="post" novalidate>
        <div class="fitem" style="margin-left:11px">
            <label><font color="red">*</font>登录名:</label>
            <input id="loginName" name="loginName" class="easyui-validatebox" required="true" placeholder="只包含字母和数字">
        </div>
        <div class="fitem">
            <label><font color="red">*</font>真实姓名:</label>
            <input id="realName" name="realName" class="easyui-validatebox" required="true">
        </div>
        <div class="fitem" style="margin-left:24px">
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
            <input name="cellPhone" class="easyui-numberbox" validType="digits">
        </div>
        <div class="fitem" style="margin-left:30px">
            <label>地址:</label>
            <input name="address" class="easyui-numberbox" maxlength="200">
        </div>
        <div class="fitem" style="margin-left:26px">
            <label><font color="red">*</font>角色:</label>
            <input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
            <input type="text" id="roleName" name="roleName" readonly required="true" style="width:160px"
                   value="${roleName}"/>
            <a href="javascript:void(0)" onclick="selectRole()">选择角色</a>
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
            <input type="hidden" name="userId"/>

            <div class="fitem" style="margin-left:25px">
                <label><font color="red">*</font>密码:</label>
                <input id="passwordUp" type="password" name="password" class="easyui-validatebox" required="true"
                       validType="isPasswd">
            </div>
            <div class="fitem">
                <label><font color="red">*</font>确认密码:</label>
                <input type="password" id="comfirmPasswordUp" name="confirmPwd" class="easyui-validatebox"
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
            <input type="hidden" id="userId" name="userId"/>

            <div class="fitem" style="margin-left:1px">
                <label><font color="red">*</font>真实姓名:</label>
                <input id="realNameUp" name="realName" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem" style="margin-left:25px">
                <label>手机:</label>
                <input id="cellphoneUp" name="cellPhone" class="easyui-numberbox" validType="digits">
            </div>
            <div class="fitem" style="margin-left:25px">
                <label>地址:</label>
                <input id="addressUp" name="address" class="easyui-validatebox">
            </div>
            <div class="fitem" style="margin-left:26px">
                <label><font color="red">*</font>角色:</label>
                <input type="hidden" id="updateRoleId" name="roleId" value="${roleId}"/>
                <input type="text" id="updateRoleName" name="roleName" readonly required="true" style="width:160px"
                       value="${roleName}"/>
                <a href="javascript:void(0)" onclick="selectRole()">选择角色</a>
            </div>
        </form>
    </div>
    <div id="dlg1-buttons" style="text-align: center;">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updaterow()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#dlg1').dialog('close')">取消</a>
    </div>
</div>


<div id="roleTree_dialog"/>
</body>
</html>