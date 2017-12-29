$('.traceTime').bind(
				'click',
				function() {
					var id=this.id;
					jQuery.ajax({
						url : 'DrawTraceServlet',
						data : {traceId:id},
						type : "post", 
						cache : false,
						success : function(data) {
							var result = eval('(' + data + ')');
										if(result.length==0)
										{
											alert('没有坐标位置数据');
										}
							map.clearOverlays(); 
							var points = [];
							for (i = 0; i < result.length; i++) {
								var longitude = result[i].lon; 
								var latitude = result[i].lat;
								var time = result[i].time+" "; // 签到时间
								var pointp = new BMap.Point(longitude, latitude); 
								if (i == 0) {
									map.setCenter(pointp); 
								}
								addMarker(pointp, map, time);
								points.push(pointp);
							}
							var polyline = new BMap.Polyline(points);
							map.addOverlay(polyline); 
						}
					});
					return false;
				});
	
function addMarker(point, map, tips) {
	var marker = new BMap.Marker(point);
	map.addOverlay(marker);
	//为标注添加文字信息
	var label = new BMap.Label(tips, {
		offset : new BMap.Size(20, -10)
	});
	marker.setLabel(label);
}