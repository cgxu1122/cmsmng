<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/common/header.jsp" %>
    <title>IQIANJIN后台管理系统</title>
<style>
	*{margin: 0px; padding: 0px;}
	.container{width: 960px; margin: 0px auto; color: #0697da;}
	.title{font-size: 60px; color: #0697da; margin: 100px auto 30px; text-align: center; text-shadow: 5px 2px 6px rgba(0,0,255,0.3);}
	.main{position: relative; width: 380px; padding: 50px 30px; margin: 0px auto; border: 1px solid #e0e0e0; box-shadow:0 0 6px 1px #efefef; border-radius: 10px;}
	.main .item{padding: 6px 0px;}
	.main .txt{width: 260px; border: 1px solid #e0e0e0; height: 20px; line-height: 20px;}
	.itembtn{text-align: center;margin-top: 10px;}
	.main .btn{border: 1px solid rgba(0,0,255,0.3); width: 100px; height: 38px; line-height: 38px; border-radius: 5px; cursor: pointer; background: #0697da;box-shadow: 0px 0px 6px rgba(0,0,255,0.3);   color: #fff; font-size: 16px;}
	.main .btn:hover{box-shadow: 0px 0px 6px rgba(0,0,255,0.6); }
	.main .error{position: absolute; left: 0px; top: 80px;  z-index: 1;  height: 30px; line-height: 30px; vertical-align: middle;border-radius: 3px; color: #f00; text-align: center; width: 100%;  }
	.loaded .error{top: 20px;-webkit-transition:all 0.3s linear;transition:all 0.3s linear;}
</style>
</head>
<body>
<div class="container">
		<div class="title">
			欢迎使用后台管理系统
		</div>
		<div class="main">
			<form id="form" onsubmit="valiSub()">
				<div class="item">
					<label>邮箱：</label>
					<input class="txt" id="username" name="username" type="text"/>
				</div>
				<div class="item">
					<label>密码：</label>
					<input class="txt" id="password" name="password" type="password"/>
				</div>
                <div class="item">
                    <label>验证码：</label>
                    <input id="captcha" name="captcha" type="text"/>
                    <img id="imgObj"  alt="" src="/vc/vc"/>
                    <a href="#" onclick="changeImg()">换一张</a>
                </div>
                <div class="item itembtn">
					<input id="btn" class="btn" type="submit" value="登录"/>
				</div>
                <div class="error">${error}</div>
            </form>
		</div>
	</div>
	<script>
		window.onload = function(){
            document.getElementsByTagName("body")[0].setAttribute("class","loaded");
        }

        function changeImg(){
            var imgSrc = $("#imgObj");
            var src = imgSrc.attr("src");
            imgSrc.attr("src",chgUrl(src));
        }
        //时间戳
        //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
        function chgUrl(url){
            var timestamp = (new Date()).valueOf();
            urlurl = url.substring(0,17);
            if((url.indexOf("&")>=0)){
                urlurl = url + "×tamp=" + timestamp;
            }else{
                urlurl = url + "?timestamp=" + timestamp;
            }
            return url;
        }

        function valiSub(){
            var code = $('#captcha').val();;
            if(!code){
                alert("请输入验证码");
                return false;
            }
            var form = $('#form')[0];
            form.action="<%=basePath%>/login";
            form.method = "post";
            form.submit();
        }
    </script>
</body>
</html>