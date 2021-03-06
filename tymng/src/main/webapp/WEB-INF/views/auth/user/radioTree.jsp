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
        var setting = {
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "all"
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick,
                onCheck: onCheck
            }
        };
        var zNodes = {};
        function onClick(event, treeId, treeNode, clickFlag) {
            var roleName = treeNode.name;
            var roleId = treeNode.id;
            treeNode.checked = true;
            parent.document.getElementById("roleName").value = roleName;
            parent.document.getElementById("roleId").value = roleId;
            parent.document.getElementById("updateRoleName").value = roleName;
            parent.document.getElementById("updateRoleId").value = roleId;
            parent.$('#roleTree_dialog').dialog('close');
        }

        function onCheck(event, treeId, treeNode) {
            var roleName = treeNode.name;
            var roleId = treeNode.id;
            parent.document.getElementById("roleName").value = roleName;
            parent.document.getElementById("roleId").value = roleId;
            parent.document.getElementById("updateRoleName").value = roleName;
            parent.document.getElementById("updateRoleId").value = roleId;
            parent.$('#roleTree_dialog').dialog('close');
        }

        function expandNode(e) {
            var zTree = $.fn.zTree.getZTreeObj("treeboxbox_tree"),
                    type = e.data.type;

            if (type == "expandAll") {
                zTree.expandAll(true);
            } else if (type == "collapseAll") {
                zTree.expandAll(false);
            }
        }

        $(document).ready(function () {
            createTree();
            $("#expandAllBtn").bind("click", {type: "expandAll"}, expandNode);
            $("#collapseAllBtn").bind("click", {type: "collapseAll"}, expandNode);
        });


        function createTree() {
            var zNodes;
            $.ajax({
                type: 'POST',
                url: '<%=basePath%>/tymng/auth/user/initRoleTree',
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
        //-->
    </SCRIPT>
</head>

<body>
<div>
    <p>
        [ <a id="expandAllBtn" href="#" title="全部展开" onclick="return false;">全部展开</a> ]
        [ <a id="collapseAllBtn" href="#" title="全部关闭" onclick="return false;">全部关闭</a> ]
    </p>
</div>
<div id="treeboxbox_tree" class="ztree"></div>

</body>
</html>