<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/common/header.jsp" %>
    <title>内置业务管理系统</title>
    <style>
        * {
            margin: 0;
            padding: 0
        }

        img {
            border: 0 none;
            vertical-align: top;
            display: block
        }

        body {
            width: 100%;
            font-family: "微软雅黑";
        }

        .loginbox {
            background: #2d2d2d;
            width: 570px;
            margin: 0 auto;
            border-radius: 10px;
            -moz-border-radius: 10px;
            -webkit-border-radius: 10px;
            -o-border-radius: 10px;
            margin-top: 120px
        }

        /*.com_name{text-align:center; font-size:26px; font-family: "微软雅黑"; color:#FFF; line-height:118px;height:118px;background:#313131;overflow: hidden;border-radius: 10px 10px 0 0;-moz-border-radius: 10px 10px 0 0;-webkit-border-radius: 10px 10px 0 0;-o-border-radius: 10px 10px 0 0;border-bottom:2px solid #363636;margin-bottom:20px;color:#d8d8d8;letter-spacing: 1px;}*/
        .com_name {
            text-align: center;
            font-size: 26px;
            font-family: "微软雅黑";
            color: #FFF;
            line-height: 118px;
            height: 118px;
            background: url(/image/land_img/title.jpg) no-repeat center center;
            overflow: hidden;
            border-radius: 10px 10px 0 0;
            -moz-border-radius: 10px 10px 0 0;
            -webkit-border-radius: 10px 10px 0 0;
            -o-border-radius: 10px 10px 0 0;
            border-bottom: 2px solid #363636;
            margin-bottom: 20px;
            color: #d8d8d8;
            letter-spacing: 1px;
        }

        .formBox {
            width: 420px;
            margin: 0 auto;
            overflow: hidden;
        }

        .input_box, .input_box2 {
            background-color: #FFF;
            background-image: url(/image/land_img/user.jpg);
            background-repeat: no-repeat;
            background-position: 13px 8px;
            border: 2px solid #000;
            height: 38px;
            margin-bottom: 20px;
        }

        .name_input, .password_input, .code_input {
            width: 360px;
            height: 22px;
            border: 0px;
            font-size: 14px;
            font-family: "Microsoft Yahei", "微软雅黑";
            background: transparent;
            color: #666;
            margin-top: 8px;
            margin-left: 40px
        }

        .identify {
            height: 38px;
            margin-bottom: 20px;
        }

        .input_box3 {
            width: 120px;
            float: left;
            border: 2px solid #000;
            background: #fff
        }

        .code_input {
            text-align: center;
            width: 100px;
            margin: 8px 10px;
        }

        .switchBtn {
            float: right;
            margin-left: 10px;
            vertical-align: middle;
            text-decoration: none;
            font-size: 12px;
            line-height: 42px;
            color: #3aa3e5;
        }

        .switchBtn:hover {
            color: #d8d8d8;
            text-decoration: underline;
        }

        .switchIdentify {
            float: right;
        }

        .identify_code {
            float: right;
            width: 80px;
            height: 30px;
            background: #fff;
            vertical-align: middle;
            margin-top: 6px;
        }

        .identify_code img {
            width: 80px;
            height: 30px;
        }

        .input_box2 {
            background-image: url(/image/land_img/password.jpg);
        }

        .submitBtn {
            border: 2px solid #000;
            background: #000;
        }

        .submit {
            background-color: #3bb17f;
            border: none;
            text-align: center;
            display: block;
            cursor: pointer;
            color: #fff;
            border-radius: 2px;
            -moz-border-radius: 2px;
            -webkit-border-radius: 2px;
            -o-border-radius: 2px;
            height: 44px;
            margin-top: 25px;
            margin: 0 auto;
            font-size: 20px;
            font-family: "Microsoft Yahei", "微软雅黑";
            width: 416px;
        }

        .login_bottom {
            height: 118px;
            background: #313131;
            border-radius: 10px 10px 0 0;
            -moz-border-radius: 0 0 10px 10px;
            -webkit-border-radius: 0 0 10px 10px;
            -o-border-radius: 0 0 10px 10px;
            border-top: 2px solid #363636;
            margin-top: 25px;
        }

        .form_name {
            line-height: 200px;
            font-size: 22px;
            height: 52px;
            overflow: hidden;
            background: url(/image/land_img/form_name.jpg) no-repeat left center;
        }

        .error {
            color: #ffffff;
        }
    </style>
</head>
<script>
    /* window.onload = function(){
     document.getElementsByTagName("body")[0].setAttribute("class","loaded");
     }*/

    function changeImg() {
        var imgSrc = $("#imgObj");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", chgUrl(src));
    }
    //时间戳
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        urlurl = url.substring(0, 17);
        if ((url.indexOf("&") >= 0)) {
            urlurl = url + "×tamp=" + timestamp;
        } else {
            urlurl = url + "?timestamp=" + timestamp;
        }
        return urlurl;
    }

    function valiSub() {
        var code = $('#captcha').val();
        ;
        if (!code) {
            alert("请输入验证码");
            return false;
        }
        var form = $('#form')[0];
        form.action = "<%=basePath%>/login";
        form.method = "post";
        form.submit();
    }
</script>
<body>
<form id="form" method="post" onsubmit="valiSub()">
    <div class="loginbox">
        <p class="com_name"></p>
        <%--<p class="com_name">预装业务数据平台</p>--%>
        <div class='formBox'>
            <p class='form_name'>用户登录</p>

            <div class="input_box">
                <input class="name_input" name="username" type="text" value="">
            </div>
            <div class="input_box2">
                <input class="password_input" name="password" type="password" value="">
            </div>
            <div class='identify'>
                <div class="input_box3">
                    <input class="code_input" name="captcha" id="captcha" type="text" value="">
                </div>

                <div class='switchIdentify'>
                    <a href='javascript:changeImg();' class='switchBtn'>看不清？换一张</a>
                    <span class='identify_code'><img id="imgObj" alt="" src="/vc/vc"/></span>
                </div>
            </div>
            <div class='submitBtn'>
                <input type="submit" id="btn" class="submit" value="提 交">
            </div>
            <div class="error">${error}</div>
        </div>
        <div class='login_bottom'></div>
    </div>
</form>
</body>
</html>
