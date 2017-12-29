$('.trace').bind(
				'click',
				function() {
					var id=this.id;
					jQuery.ajax({
						url : 'DrawTraceServlet',
						data : {traceId:id},
						type : "post", 
						cache : false,
						success : function(data) {
							//alert('hehe');
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
								var pointp = new BMap.Point(longitude, latitude); 
								if (i == 0) {
									map.setCenter(pointp); 
								}
								points.push(pointp);
							}
							var polyline = new BMap.Polyline(points);
							map.addOverlay(polyline); 
						}
					});
					return false;
				});
	