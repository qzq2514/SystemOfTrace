var map = new BMap.Map("page-wrapper"); // 百度地图API功能
	var point = new BMap.Point(118.92761, 31.92705);
					map.centerAndZoom(point, 10);
					map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
					map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
					map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
					map.addControl(new BMap.MapTypeControl()); //添加地图类型控件
					map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
					map.setCurrentCity("南京"); // 仅当设置城市信息时，MapTypeControl的切换功能才能可用
	
	
	