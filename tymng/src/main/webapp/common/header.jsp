<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/plug/easyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>/plug/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>/common/css/icommon.css">
<script type="text/javascript" src="<%=basePath %>/plug/easyUI/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/plug/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/js/dot.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/plug/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/js/validateExtends.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/js/dateCommon.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/js/utilExtentds.js"></script>
<style>
    .datagrid .datagrid-pager {
        position: relative;
    }

    .datagrid-row-selected {
        background: #3CB371;
        color: #000000;
    }

    table {
        text-align: center !important;
    }
</style>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
