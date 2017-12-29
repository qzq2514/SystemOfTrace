    var editable=false;
    var myDraw=document.getElementById("myDraw");
    var mypoints;
    var jsonString;
    myDraw.onclick=function()
	   {
		 map.clearOverlays(); 
		 alert("单击地图绘制路径，双击绘制完成!");
		 mypoints=[];//用来存储折线的点
		 editable=true;
	   };
    var polyline;

    map.addEventListener("click", function (e) {//当鼠标单击时
        if (editable==true) {//判断是否绘制曲线完毕
        	var point=new BMap.Point(e.point.lng, e.point.lat);
            mypoints.push(point) //存储曲线上每个点的经纬度
            if (polyline) {
                polyline.setPath(mypoints);//如果曲线存在，则获取折线上的点
            }
            else {
                polyline = new BMap.Polyline(mypoints);//如果折线不存在，就增加此点
            }
            if (mypoints.length < 2) {//当折线上的点只有一个时，不绘制
                return;
            }
            map.addOverlay(polyline); //绘制曲线
          }
    });
    map.addEventListener("dblclick", function (e2) { //当鼠标双击时：结束绘制
        jsonString=JSON.stringify(mypoints);
        jQuery(function() {
        	 if (editable==true)
        		 { alert("绘制完成");
                 editable= false;
       		  search(jsonString);}
    	})
        function search(jsonstring) {
			var id=this.id;
			jQuery.ajax({
				url : 'initMyDraw',
				data : {jsontrace:jsonstring},
				type : "post", 
				cache : false,
				success : function(data) {
					document.getElementById("jsonIdC").value=data;
			        document.getElementById("jsonIdQ").value=data;
				}
			});
			return false;
		}
        
    });