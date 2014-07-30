<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/common/header.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <link href="<%=basePath %>/plug/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath %>/plug/ztree/js/jquery.ztree.all-3.5.min.js"></script>
    <SCRIPT type="text/javascript">
        <!--
        var roleId = '${roleId}'
        var setting = {
            check: {
                enable: true,
                chkStyle: "checkbox"
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        var zNodes = {};

        $(document).ready(function () {
            createTree();
        });


        function createTree() {
            var zNodes;
            $.ajax({
                type: 'POST',
                url: '/tymng/auth/sysauth/initResourceTree',
                data: {roleId: roleId},
                dataType: "json",
                ContentType: "application/json; charset=utf-8",
                success: function (data) {
                    zNodes = data;
                    $.fn.zTree.init($("#treeboxbox_tree"), setting, zNodes);
                },
                error: function (msg) {
                    alert("加载失败");
                }
            });
        }

        function sysAuth() {
            var zTree = $.fn.zTree.getZTreeObj("treeboxbox_tree");
            var nodes = zTree.getCheckedNodes(true);
            var array = new Array();
            for (var i = 0; i < nodes.length; i++) {
                array[i] = nodes[i].id;
            }

            $.ajax({
                method: 'post',
                url: '<%=basePath%>/tymng/auth/sysauth/auth',
                data: {roleId: roleId, resIdList: array},
                dataType: "json",
                ContentType: "application/json; charset=utf-8",
                success: function (data) {
                    alert(data)
                }
            });
        }
        //-->
    </SCRIPT>
</head>

<body>
<div id="treeboxbox_tree" class="ztree"></div>

</body>
</html>