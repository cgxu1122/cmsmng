<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/common/header.jsp" %>

    <style>
        .iheader {
            background: #fafafa;
            color: #2d5593;
            height: 40px;
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
            margin-right: 10px;
            font-size: 12px;
            padding-top: 20px;
        }

        .iheadername {
            display: inline-block;
            height: 20px;
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
    </script>
    <title>内置业务管理系统</title>
</head>
<body class="easyui-layout">
<div region="north" class="iheader">
    <div class="iheadercont">
        <div class="iheadertit">内置业务管理系统</div>
        <div class="iheadermsg">
            <span class="iheadername">欢迎：<shiro:principal></shiro:principal></span>
            <a class="iheaderout" style="" href="<%=basePath%>/logout">退出</a>
        </div>
    </div>
</div>
<div region="south">
    <div style="text-align:center;padding:5px 0px; color: #999;">Copyright &copy; 2013 power by iqianjin</div>
</div>
<div region="west" title="导航菜单" split="true" style="width: 150px;">
    <div class="easyui-accordion  i_accordion_menu" fit="true" border="false">
        <div title="渠道管理" selected="true" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('天音渠道','<%=basePath%>/channelInfo/index?groupId=1','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>天音渠道</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道','<%=basePath%>/channelInfo/index?groupId=2','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <!---->
                    <span>地包渠道</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('其他渠道','<%=basePath%>/channelInfo/index?groupId=3','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>其他渠道</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('劳务渠道','<%=basePath%>/channelInfo/index?groupId=4','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>劳务渠道</span>
                </a>
            </div>
        </div>
        <div title="机型管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('天音渠道机型','<%=basePath%>/modelInfo/index?groupId=1','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>天音渠道机型</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道机型','<%=basePath%>/modelInfo/index?groupId=2','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>地包渠道机型</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('其他渠道机型','<%=basePath%>/modelInfo/index?groupId=3','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>其他渠道机型</span>
                </a>
            </div>
        </div>
        <div title="工装管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('工装管理','<%=basePath%>/deviceInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>工装管理</span>
                </a>
            </div>
        </div>
        <div title="合作方管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('合作方管理','<%=basePath%>/partnerInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>合作方管理</span>
                </a>
            </div>
        </div>
        <div title="产品管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('产品管理','<%=basePath%>/productInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>产品管理</span>
                </a>
            </div>
        </div>
        <div title="批次管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('天音渠道批次','<%=basePath%>/batchInfo/index?groupId=1','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>天音渠道批次</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道批次','<%=basePath%>/batchInfo/index?groupId=2','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>地包渠道批次</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('其他渠道批次','<%=basePath%>/batchInfo/index?groupId=3','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>其他渠道批次</span>
                </a>
            </div>
        </div>
        <div title="设备系统管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('设备系统管理','<%=basePath%>/deviceSystem/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>设备系统管理</span>
                </a>
            </div>
        </div>
        <div title="APK管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('APK管理','<%=basePath%>/apkInfo/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>APK管理</span>
                </a>
            </div>
        </div>
        <div title="打包管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('普通包管理','<%=basePath%>/packageInfo/index?type=N','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>普通包管理</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('通用包管理','<%=basePath%>/packageInfo/index?type=Y','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>通用包管理</span>
                </a>
            </div>
        </div>
        <div title="合作方查询后台" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('天音渠道查询','<%=basePath%>/logCount/tianyin','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>天音渠道</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('地包渠道查询','<%=basePath%>/logCount/dibao','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>地包渠道</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('cp合作方查询','<%=basePath%>/logCount/cp','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>cp合作方</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('劳务渠道查询','<%=basePath%>/logCount/laowu','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>劳务渠道</span>
                </a>
            </div>
        </div>
        <div title="报表统计" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('按仓库查询','<%=basePath%>/reportCount/warehouseQuery','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>按仓库查询</span>
                </a>
            </div>

        </div>
        <div title="系统用户管理" style="overflow: auto;">
            <div class="nav-item">
                <a href="javascript:addTab('系统用户管理','<%=basePath%>/user/index','menu_icon_datadeal')">
                    <span class="menu_icon_datadeal"></span>
                    <span>系统用户管理</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="javascript:addTab('角色管理','<%=basePath%>/role/index','menu_icon_wjldgl')">
                    <span class="menu_icon_wjldgl"></span>
                    <span>角色管理</span>
                </a>
            </div>
            <%--<shiro:hasPermission name="A01">--%>
            <div class="nav-item">
                <a href="javascript:addTab('授权管理','<%=basePath%>/rrr/index','menu_icon_wjldgl')">
                    <span class="menu_icon_wjldgl"></span>
                    <span>授权管理</span>
                </a>
            </div>
            <%--</shiro:hasPermission>--%>
        </div>
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
</body>
</html>