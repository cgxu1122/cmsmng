<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/common/header.jsp" %>
    <title></title>
    <script>
        function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        }
        var mUserId = getQueryString("userId");
    </script>
    <script type="text/javascript" src="<%= basePath %>/common/js/numberFormat.js"></script>
</head>
<body>

<input type="hidden" id="userId"/>

<div id="userInfoMess" class="easyui-tabs" style="width:900px;height:500px">
    <div title="基本信息" style="padding:10px">
        <%@include file="userInfoIndex.jsp" %>
    </div>
    <div title="资金流水" style="padding:10px">
        <%@ include file="pointDetail.jsp" %>
    </div>
    <div title="借入记录" style="padding:10px">
        <%@include file="borrowIndex.jsp" %>
    </div>
    <div title="备注" style="padding:10px">
        <%@ include file="remarksIndex.jsp" %>
    </div>
    <!--
        <div title="评论记录" style="padding:10px">
            <%@ include file="commentIndex.jsp" %>
        </div>
         -->
</div>
<script>
    $('#userInfoMess').tabs({
        onSelect: function (title) {

            if (title === "资金流水") {
                renderHeader();
                renderPointDataGrid();
            }

            if (title === "借入记录") {
                borrowIndexInit(mUserId);
            }

            if (title === "备注") {
                renderRemarksDataGrid();
            }

            if (title === "评论记录") {
                renderDataGrid();
            }
        }
    });
</script>
</body>
</html>