<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ERROR</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <style>
*{margin:0;padding:0;outline:none;font-family:\5FAE\8F6F\96C5\9ED1,宋体;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;-khtml-user-select:none;user-select:none;cursor:default;font-weight:lighter;}
.center{margin:0 auto;}
.whole{width:100%;height:100%;line-height:100%;position:fixed;bottom:0;left:0;z-index:-1000;overflow:hidden;}
.whole img{width:100%;height:100%;}
.mask{width:100%;height:100%;position:absolute;top:0;left:0;background:#000;opacity:0.6;filter:alpha(opacity=60);}
.b{width:100%;text-align:center;height:400px;position:absolute;top:50%;margin-top:-230px}.a{width:150px;height:50px;margin-top:30px}.a a{display:block;float:left;width:150px;height:50px;background:#fff;text-align:center;line-height:50px;font-size:18px;border-radius:25px;color:#333}.a a:hover{color:#000;box-shadow:#fff 0 0 20px}
p{color:#fff;margin-top:40px;font-size:24px;}
#num{margin:0 5px;font-weight:bold;}
</style>
<script type="text/javascript">
	var num=11;
	function redirect(){
		num--;
		document.getElementById("num").innerHTML=num;
		if(num<0){
			document.getElementById("num").innerHTML=0;
			location.href="http://localhost:8080/SystemOfTrace/index";
			}
		}
	setInterval("redirect()", 1000);
</script>

<body onLoad="redirect();">
<div class="whole">
	<img src="images/back.jpg" />
    <div class="mask"></div>
</div>
<div class="b">
		<p>
			 ${errormessage} <br/><span id="num"></span>秒后自动跳转到主页<br/>
			<!--  //www.qzq2514.com -->
    <a href="http://localhost:8080/SystemOfTrace/index">
    <font color="white">http://localhost:8080/SystemOfTrace/index</font></a>
            
		</p>
	</div>

</body>
</html>
