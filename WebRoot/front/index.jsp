<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="APES"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>基于GIS的轨迹相似度分析系统</title>
<!-- Bootstrap Styles-->
<link href="front/assets/css/bootstrap.css" rel="stylesheet" />
<!-- FontAwesome Styles-->
<link href="front/assets/css/font-awesome.css" rel="stylesheet" />
<!-- Custom Styles-->
<link href="front/assets/css/custom-styles.css" rel="stylesheet" />
<!-- Google Fonts-->
<link href='http://fonts.useso.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />
<link rel="stylesheet"
	href="front/assets/js/Lightweight-Chart/cssCharts.css">
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=Pj1i7pD9uj7QwZpVnFHZFCkfYT7SL7Rk">
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
					<li><a href="front/index"><i class="fa fa-sitemap"></i>比较两条轨迹相似度<span
							class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a href="#">不带时间轨迹<span class="fa arrow"></span></a>
								<ul class="nav nav-third-level">
									<form action="CalculateSimilarityServlet" mehtod="get">
										<APES:forEach var="num" items="${TraceListWithoutTime}">
											<li><a class="trace" id="N${num}" href="index">轨迹${num}</a>
												<input type="checkbox" name="choices" value="N${num}" /></li>
										</APES:forEach>
										<input class="btn btn-default" type="submit"
											value="计算选中轨迹的相似度" />
									</form>
								</ul></li>
							<li><a href="#">带时间轨迹<span class="fa arrow"></span></a>
								<ul class="nav nav-third-level">
									<form action="CalculateSimilarityServlet" mehtod="get">
										<APES:forEach var="num" items="${TraceListWithTime}">
											<li><a class="traceTime" id="Y${num}" href="index">轨迹${num}</a>
												<input class="btn btn-default" type="checkbox"
												name="choices" value="Y${num}" /></li>
										</APES:forEach>
										<input class="btn btn-default" type="submit"
											value="计算选中轨迹的相似度" />
									</form>
								</ul></li>
						</ul></li>


					<li><a href="front/index"><i class="fa fa-sitemap"></i>相似轨迹查询<span
							class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a href="#">不带时间轨迹<span class="fa arrow"></span></a>
								<ul class="nav nav-third-level">
									<form action="QueryTraceServlet" mehtod="post">
										<APES:forEach var="num" items="${TraceListWithoutTime}">
											<li><a class="trace" id="N${num}" href="index">轨迹${num}</a>
												<input class="btn btn-default" type="radio" name="choice"
												value="N${num}" /></li>
										</APES:forEach>
										<input class="btn btn-default" type="submit" value="查询相似轨迹" />
									</form>
								</ul></li>
							<li><a href="#">带时间轨迹<span class="fa arrow"></span></a>
								<ul class="nav nav-third-level">
									<form action="QueryTraceServlet" mehtod="post">
										<APES:forEach var="num" items="${TraceListWithTime}">
											<li><a class="traceTime" id="Y${num}" href="index">轨迹${num}</a>
												<input class="btn btn-default" type="radio" name="choice"
												value="Y${num}" /></li>
										</APES:forEach>
										<input class="btn btn-default" type="submit" value="查询相似轨迹" />
									</form>
								</ul></li>
						</ul></li>



					<li><a href="front/index"><i class="fa fa-sitemap"></i>自定义不带时间轨迹<span
							class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
						<input class="btn btn-default" id="myDraw" type="submit" value="绘制轨迹" />
							<li><a href="#">选择已有轨迹进行比较<span class="fa arrow"></span></a>
								<ul class="nav nav-third-level">
									<form action="MYCalculateSimilarityServlet" mehtod="post">
										<APES:forEach var="num" items="${TraceListWithoutTime}">
											<li><a class="trace" id="N${num}" href="index">轨迹${num}</a>
												<input class="btn btn-default" type="radio" name="choice"
												value="N${num}" /></li>
										</APES:forEach>
										<input type="text" id="jsonIdC" name="jsonId" value="" style="display:none;">
										<input class="btn btn-default" type="submit" value="计算相似度" />
									</form>
								</ul></li>
							<li><a href="#">在已有轨迹中查询相似轨迹<span class="fa arrow"></span></a>
								<ul class="nav nav-third-level">
									<form action="MYQueryTraceServlet" mehtod="post">
										<input type="text" id=jsonIdQ name="jsonId" value="" style="display:none;">
										<input class="btn btn-default" type="submit" value="查询相似轨迹" />
									</form>
								</ul>
						</ul></li>
					<li><form action="fileuploadServlet" method="post"
							enctype="multipart/form-data">
							<a href="javascript:;" class="a-upload">
                        <input type="file" name="csvfile">点击这里选择文件
                    </a>
                    <a href="javascript:;" class="a-upload">
                    <input class="btn btn-default" type="submit">上传
                    </a>

						</form></li>
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
	<script src="front/mapJS/myDraw.js"></script>
	<script type="text/javascript">
		jQuery(function() {
			var idInfo = "${traceId}";
			if (idInfo)
				search(idInfo);
		})
		function search(idinfo) {
			//alert(idinfo);
			jQuery.ajax({
				url : 'DrawTraceServlet',
				data : {
					traceId : idinfo
				},
				type : "post",
				cache : false,
				success : function(data) {
					var result = eval('(' + data + ')');
					if (result.length == 0) {
						alert('没有坐标位置数据');
					}
					map.clearOverlays();
					var points = [];
					for (i = 0; i < result.length; i++) {
						var longitude = result[i].lon;
						var latitude = result[i].lat;

						var pointp = new BMap.Point(longitude, latitude);
						if (i == 0) {
							map.setCenter(pointp);
						}
						//alert("QZQ");
						//  alert(idinfo.startWith('Y')); 
						if (idinfo.substring(0, 1) == "Y") {
							var time = result[i].time + ""; // 签到时间
							addMarker(pointp, map, time);
						}

						points.push(pointp);
					}
					var polyline = new BMap.Polyline(points);
					map.addOverlay(polyline);
				}
			});
			return false;
		}
		function addMarker(point, map, tips) {
			var marker = new BMap.Marker(point);
			map.addOverlay(marker);
			//为标注添加文字信息
			var label = new BMap.Label(tips, {
				offset : new BMap.Size(20, -10)
			});
			marker.setLabel(label);
		}
	</script>

</body>
</html>
