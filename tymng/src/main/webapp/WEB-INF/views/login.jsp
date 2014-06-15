<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
%>
    <script language = "javascript" src ="/common/js/code.js"></script>
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
    .code{
        background-image:url(w1.jpg);
        font-family:Arial;
        font-style:italic;
        color:Red;
        border:0;
        padding:2px 3px;
        letter-spacing:3px;
        font-weight:bolder;
    }
</style>
</head>
<body>
<div class="container">
		<div class="title">
			欢迎使用后台管理系统
		</div>
		<div class="main">
			<form id="" action="<%=basePath%>/login" method="post">
				<div class="item">
					<label>邮箱：</label>
					<input class="txt" id="username" name="username" type="text"/>
				</div>
				<div class="item">
					<label>密码：</label>
					<input class="txt" id="password" name="password" type="password"/>
				</div>
                <div class="error">${error}</div>
                <div class="item">
                    <center>验证码：<input type="text" id="input1" />    
                    <input type="text" id="checkCode" class="code" style="width: 55px" /> <a href="#" onclick="createCode()">看不清楚</a>    
                </div>
                <div class="item itembtn">
					<input id="btn" class="btn" type="submit" onsubmit="validate()" value="登录"/>
				</div>
            </form>
		</div>
	</div>
	<script>
		window.onload = function(){
            createCode();
            document.getElementsByTagName("body")[0].setAttribute("class","loaded");
        }
	</script>
</body>
</html>