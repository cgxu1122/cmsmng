<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/common/header.jsp" %>
    <title></title>
    <script>
        $(document).ready(function () {
            initPage();
        });


        function chkself(obj) {
            var _type = "";
            if (obj.checked == true) {
                _type = "Y";
            } else {
                _type = "N";
            }

            $.post('<%=basePath%>/tymng/auth/productauth/productAuth', {userId: ${userId}, productId: obj.value, type: _type}, function (result) {
                alert("授权成功")
            });
        }

        function initPage() {
            $.post('<%=basePath%>/tymng/auth/productauth/productList', {userId: ${userId}}, function (result) {
                for (var out in result) {
                    var baseEle = document.getElementById("showDiv");
                    var header = '<fieldset><legend><strong>' + out + '</strong></legend>';
                    var full = header;

                    for (var i = 0; i < result[out].length; i++) {
                        var obj = result[out][i];
                        var checked = "";
                        if (obj.hasAuth == true) {
                            checked = "checked";
                        }
                        var contenthead = '<div style="float:left;overflow:hidden;margin-top: 5px;margin-left: 5px"><shiro:hasPermission name="product_auth_btn"><input type="checkbox" onclick="chkself(this)" value="' + obj.productId + '" title="' + obj.active + '" name="productId" ' + checked + '/></shiro:hasPermission><label><strong>' + obj.productName + '</strong></label></div>'
                        full += contenthead;
                    }

                    var footer = '</fieldset><br/>';
                    full += footer;
                    $(baseEle).append(full);
                }
            }, 'json');
        }
    </script>
</head>
<body>
<div id="showDiv">
</div>
</body>
</html>
