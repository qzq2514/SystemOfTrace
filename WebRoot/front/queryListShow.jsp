<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="APES"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>基于GIS的轨迹相似度分析系统</title>
    <!-- Bootstrap Styles-->
    <link href="front/assets/css/bootstrap.css" rel="stylesheet"/>
    <!-- FontAwesome Styles-->
    <link href="front/assets/css/font-awesome.css" rel="stylesheet"/>
    <!-- Custom Styles-->
    <link href="front/assets/css/custom-styles.css" rel="stylesheet"/>
    <!-- Google Fonts-->
    <link href='http://fonts.useso.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
    <link rel="stylesheet" href="front/assets/js/Lightweight-Chart/cssCharts.css">
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=Pj1i7pD9uj7QwZpVnFHZFCkfYT7SL7Rk">
        //v2.0版本的引用方式：src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥"
        //v1.4版本及以前版本的引用方式：src="http://api.map.baidu.com/api?v=1.4&key=您的密钥&callback=initialize"
    </script>
</head>


<body>
	<div id="wrapper">
		<nav class="navbar navbar-default top-navbar" role="navigation">
		<div class="navbar-header">
			<a class="navbar-brand" href="index"><strong>轨迹相似度分析系统</strong></a>
			<div id="sideNav" href="">
				<i class="fa fa-caret-right"></i>
			</div>
		</div>
		</nav>
		<nav class="navbar-default navbar-side" role="navigation">
		<div class="sidebar-collapse">
			<ul class="nav" id="main-menu">
				<li><a href="front/index"><i class="fa fa-sitemap"></i>查询结果(相似度从高到低排列)<span
						class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
								<APES:if test="${isWithTime=='N'}">
									<li><a class="trace" id="N${thisId}" href="index">原始被查询轨迹</a>
										<APES:forEach var="queryitem" items="${QueryList}">
											<li><a class="trace" id="N${queryitem.queryId}"
												href="front/queryListShow.jsp">原始轨迹${queryitem.queryId}
												<br/><font color="#E0E0E0">相似度:${queryitem.similarity}%</font></a></li>
										</APES:forEach></li>
								</APES:if>
								<APES:if test="${isWithTime=='Y'}">
											<li><a class="traceTime" id="Y${thisId}" href="index">原始被查询轨迹</a>
												<APES:forEach var="queryitem" items="${QueryList}">
													<li><a class="traceTime" id="Y${queryitem.queryId}"
														href="front/queryListShow.jsp">原始轨迹${queryitem.queryId}
														<br/><font color="#E0E0E0">相似度:${queryitem.similarity}%</font></a></li>
												</APES:forEach></li>
								</APES:if>
					    </ul>
				</li>
		   </ul>
		</div>
		</nav>
		<div id="page-wrapper"></div>
	</div>
	<script src="front/assets/js/jquery-1.10.2.js"></script>
<script src="front/assets/js/bootstrap.min.js"></script>
<script src="front/assets/js/jquery.metisMenu.js"></script>
<script src="front/assets/js/easypiechart.js"></script>
<script src="front/assets/js/easypiechart-data.js"></script>
<script src="front/assets/js/Lightweight-Chart/jquery.chart.js"></script>
<script src="front/assets/js/custom-scripts.js"></script>

	<script src="front/mapJS/createMap.js"></script>
	<script src="front/mapJS/drawTraceWithoutTime.js"></script>
    <script src="front/mapJS/drawTraceWithTime.js"></script>
</body>
</html>
