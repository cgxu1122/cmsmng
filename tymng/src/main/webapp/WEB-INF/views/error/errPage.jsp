<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
    <%@ include file="/common/header.jsp" %>
</head>
<head>
    <title>error page</title>
    <script type="text/javascript">
        $(function () {
            $("#center-div").center(true);
        });
    </script>
</head>
<body style="margin: 0; padding: 0; background-color: #f5f5f5;">
<div id="center-div">
    <table style="height: 100%; width: 600px; text-align: center;">
        <tr>
            <td>
                ${exception}
                <p
                        style="line-height: 12px; color: #666666; font-family: Tahoma, '宋体'; font-size: 12px; text-align: left;">
                    <a href="javascript:history.go(-1);">返回</a>!!!
                </p>
            </td>
        </tr>
    </table>
</div>
</body>
</html>