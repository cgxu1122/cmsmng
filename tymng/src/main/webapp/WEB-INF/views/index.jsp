<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <style>
        .iheader {
            background: #fafafa;
            color: #2d5593;
            height: 44px;
            overflow: hidden;
        }

        .iheadercont {
            clear: both;
        }

        .iheadertit {
            float: left;
            line-height: 40px;
            margin-left: 10px;
            color: #0E2D5F;
            font-size: 16px;
            font-weight: 700;
        }

        .iheadermsg {
            float: right;
            margin-right: 60px;
            font-size: 15px;
            color: #0E2D5F;
            font-size: 16px;
            font-weight: 700;
        }

        .iheadername {
            display: inline-block;
            height: 0px;
            padding-top: 10px;
            vertical-align: middle;
        }

        .iheaderout {
            border: 0px;
            display: inline-block;
            margin-right: 5px;
            width: 20px;
            height: 20px;
            background: url(image/tabicons.gif) no-repeat -0px -160px;
            overflow: hidden;
            vertical-align: middle;
            text-indent: 50px;
            text-align: center;
            color: rgba(255, 255, 255, 0);
        }

        .iheaderout:hover {
            width: 30px;
            color: #f00;
            text-indent: 0px;
            vertical-align: middle;
            background: none;
            -webkit-transition: all 0.3s linear;
            transition: all 0.3s linear;
        }
    </style>
    <script type="text/javascript">
        jQuery.ajaxSetup({
            cache: false
        });//ajax不缓存

        function addTab(title, href, icon) {
            var tt = $('#main-center');
            icon = icon || 'menu_icon_service';
            if (tt.tabs('exists', title)) {
                tt.tabs('select', title);
                var currTab = tt.tabs('getTab', title);
                tt.tabs('update', {tab: currTab, options: {content: content, closable: true}});
            } else {
                if (href) {
                    var content = '<iframe  scrolling="no" frameborder="0"  src="' + href + '" style="width:100%;height:100%;"></iframe>';
                } else {
                    var content = '未实现';
                }
                tt.tabs('add', {
                    title: title,
                    content: content,
                    closable: true,
                    iconCls: icon
                });
            }
        }


        function restp() {
            $('#dlg').dialog('open').dialog('setTitle', '修改密码');
            $('#fm').form('clear');
        }

        function update() {
            var oldPassword = $('#oldPassword').val();
            var newPassword = $('#newPassword').val();
            var confirmPassword = $('#confirmPassword').val();
            if (oldPassword == null || oldPassword == "") {
                alert("请输入原密码！");
                return;
            }

            if (newPassword == null || newPassword == "") {
                alert("请输入新密码！");
                return;
            }

            if (confirmPassword == null || confirmPassword == "") {
                alert("请输入确认密码！");
                return;
            }

            if (confirmPassword != newPassword) {
                alert("新密码与确认密码不一致！");
                return;
            }


            $.ajax({
                method: 'post',
                url: '<%=basePath%>/tymng/mpd',
                data: {
                    "oldPassword": oldPassword,
                    "newPassword": newPassword,
                    "confirmPassword": confirmPassword
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.alert('错误', result.errorMsg);
                    } else {
                        $.messager.alert('成功', "更新成功");
                        $('#dlg').dialog('close');
                    }
                }
            });
        }
    </script>
    <title>内置业务管理系统</title>
</head>
<body class="easyui-layout">
<div region="north" class="iheader">
    <div class="iheadercont">
        <div class="iheadertit">内置业务管理系统</div>
        <div class="iheadermsg">
            <span class="iheadername">欢迎：<shiro:principal></shiro:principal></span>
            <a class="easyui-linkbutton" href="<%=basePath%>/tymng/logout">退出</a>
            <a class="easyui-linkbutton" href="#" onclick="restp()">修改密码</a>
            </span>
        </div>
    </div>
</div>
<div region="south">
    <div style="text-align:center;padding:5px 0px; color: #999;">Copyright &copy; 2013 power by yitianxindong</div>
</div>
<div region="west" title="导航菜单" split="true" style="width: 150px;">
<div class="easyui-accordion  i_accordion_menu" fit="true" border="false">
<shiro:hasPermission name="channel_menu">
    <div title="渠道管理" selected="true" style="overflow: auto;">
        <shiro:hasPermission name="channel_ty">
            <div class="nav-item">
                <a href="javascript:addTab('天音渠道','<%=basePath%>/tymng/channelInfo/index?groupId=1','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>天音渠道</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="channel_db">
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道','<%=basePath%>/tymng/channelInfo/index?groupId=2','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <!---->
                    <span>地包渠道</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="channel_qt">
            <div class="nav-item">
                <a href="javascript:addTab('其他渠道','<%=basePath%>/tymng/channelInfo/index?groupId=3','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>其他渠道</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="channel_lw">
            <div class="nav-item">
                <a href="javascript:addTab('劳务渠道','<%=basePath%>/tymng/channelInfo/index?groupId=4','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>劳务渠道</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="model_menu">
    <div title="机型管理" style="overflow: auto;">
        <shiro:hasPermission name="model_ty">
            <div class="nav-item">
                <a href="javascript:addTab('天音渠道机型','<%=basePath%>/tymng/modelInfo/index?groupId=1','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>天音渠道机型</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="model_db">
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道机型','<%=basePath%>/tymng/modelInfo/index?groupId=2','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>地包渠道机型</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="model_qt">
            <div class="nav-item">
                <a href="javascript:addTab('其他渠道机型','<%=basePath%>/tymng/modelInfo/index?groupId=3','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>其他渠道机型</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="device_menu">
    <div title="工装管理" style="overflow: auto;">
        <shiro:hasPermission name="device">
            <div class="nav-item">
                <a href="javascript:addTab('工装管理','<%=basePath%>/tymng/deviceInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>工装管理</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="partner_menu">
    <div title="合作方管理" style="overflow: auto;">
        <shiro:hasPermission name="partner">
            <div class="nav-item">
                <a href="javascript:addTab('合作方管理','<%=basePath%>/tymng/partnerInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>合作方管理</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="product_menu">
    <div title="产品管理" style="overflow: auto;">
        <shiro:hasPermission name="product">
            <div class="nav-item">
                <a href="javascript:addTab('产品管理','<%=basePath%>/tymng/productInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>产品管理</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="batch_menu">
    <div title="批次管理" style="overflow: auto;">
        <shiro:hasPermission name="batch_ty">
            <div class="nav-item">
                <a href="javascript:addTab('天音渠道批次','<%=basePath%>/tymng/batchInfo/index?groupId=1','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>天音渠道批次</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="batch_db">
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道批次','<%=basePath%>/tymng/batchInfo/index?groupId=2','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>地包渠道批次</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="batch_qt">
            <div class="nav-item">
                <a href="javascript:addTab('其他渠道批次','<%=basePath%>/tymng/batchInfo/index?groupId=3','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>其他渠道批次</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="devicesystem_menu">
    <div title="设备系统管理" style="overflow: auto;">
        <shiro:hasPermission name="devicesystem">
            <div class="nav-item">
                <a href="javascript:addTab('设备系统管理','<%=basePath%>/tymng/deviceSystem/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>设备系统管理</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="publish_menu">
    <div title="发布管理" style="overflow: auto;">
        <shiro:hasPermission name="publish_apk">
            <div class="nav-item">
                <a href="javascript:addTab('APK管理','<%=basePath%>/tymng/apkInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>APK管理</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="publish_normal_pkg">
            <div class="nav-item">
                <a href="javascript:addTab('普通包管理','<%=basePath%>/tymng/packageInfo/index?type=N','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>普通包管理</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="publish_common_pkg">
            <div class="nav-item">
                <a href="javascript:addTab('通用包管理','<%=basePath%>/tymng/packageInfo/index?type=Y','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>通用包管理</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="publish_task">
            <div class="nav-item">
                <a href="javascript:addTab('发布任务管理','<%=basePath%>/tymng/publishTask/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>发布任务管理</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>

<shiro:hasPermission name="settle_menu">
    <div title="地包渠道结算条款" style="overflow: auto;">
        <shiro:hasPermission name="settle">
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道结算条款','<%=basePath%>/tymng/settleInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>地包渠道结算条款</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="report_menu">
    <div title="报表统计" style="overflow: auto;">
        <shiro:hasPermission name="report_store">
            <div class="nav-item">
                <a href="javascript:addTab('按仓库查询','<%=basePath%>/tymng/reportCount/indexStore','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>按仓库查询</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="report_channel_device">
            <div class="nav-item">
                <a href="javascript:addTab('按渠道加工数据','<%=basePath%>/tymng/reportCount/indexChannelProcess','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>按渠道加工数据</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="report_channel_counter">
            <div class="nav-item">
                <a href="javascript:addTab('按渠道到达数据','<%=basePath%>/tymng/reportCount/indexChannelCounter','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>按渠道到达数据</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="report_porduct">
            <div class="nav-item">
                <a href="javascript:addTab('按产品查询','<%=basePath%>/tymng/reportCount/indexProduct','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>按产品查询</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="system_menu">
    <div title="系统用户管理" style="overflow: auto;">
        <shiro:hasPermission name="system_user">
            <div class="nav-item">
                <a href="javascript:addTab('系统用户管理','<%=basePath%>/tymng/auth/user/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>系统用户管理</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="system_role">
            <div class="nav-item">
                <a href="javascript:addTab('角色管理','<%=basePath%>/tymng/auth/role/index','menu_icon_wjldgl')">
                    <span class="menu_icon_wjldgl"></span>
                    <span>角色管理</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="system_auth">
            <div class="nav-item">
                <a href="javascript:addTab('授权管理','<%=basePath%>/tymng/auth/sysauth/index','menu_icon_wjldgl')">
                    <span class="menu_icon_wjldgl"></span>
                    <span>授权管理</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="product_auth">
            <div class="nav-item">
                <a href="javascript:addTab('产品授权管理','<%=basePath%>/tymng/auth/productauth/index','menu_icon_wjldgl')">
                    <span class="menu_icon_wjldgl"></span>
                    <span>产品授权管理</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
<shiro:hasPermission name="tymng_help_menu">
    <div title="辅助功能" style="overflow: auto;">
        <shiro:hasPermission name="tymng_help_queryimei">
            <div class="nav-item">
                <a href="javascript:addTab('查询Imei','<%=basePath%>/hzfmng/imeiQuery/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>查询Imei</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="tymng_help_uploadimei">
            <div class="nav-item">
                <a href="javascript:addTab('上传Imei','<%=basePath%>/hzfmng/imeiUpload/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>上传Imei</span>
                </a>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="tymng_help_uploadzip">
            <div class="nav-item">
                <a href="javascript:addTab('上传Zip文件','<%=basePath%>/hzfmng/zipUpload/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>上传Zip文件</span>
                </a>
            </div>
        </shiro:hasPermission>
    </div>
</shiro:hasPermission>
</div>
</div>
<div region="center">
    <div id="main-center" class="easyui-tabs" fit="true" border="false">
        <div title="首页" data-options="iconCls:'menu_icon_home',closable : true" style="padding: 20px;">
            <div style="margin-top: 20px;">
                <h1>内置业务管理系统</h1>
            </div>
        </div>
    </div>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:220px;padding:10px 20px" closed="true"
     buttons="#dlg-buttons">

    <form id="fm" method="post" novalidate>
        <table>
            <tr>
                <td>旧密码:</td>
                <td><input class="easyui-validatebox" name="oldPassword" id="oldPassword" type="password"
                           required="true"/><span style="color:red">*</span></td>
            </tr>
            <tr>
                <td>新密码:</td>
                <td><input class="easyui-validatebox" name="newPassword" id="newPassword" type="password"
                           required="true" validType="isPasswd"/><span style="color:red">*</span></td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input class="easyui-validatebox" name="confirmPassword" id="confirmPassword" type="password"
                           required="true"/><span style="color:red">*</span></td>
            </tr>
            <tr>
                <td></td>
                <td><a class="easyui-linkbutton" type="button" onclick="update()"
                       data-options="iconCls:'icon-save'">确定</a></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>