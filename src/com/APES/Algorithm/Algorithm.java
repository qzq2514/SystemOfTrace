package com.APES.Algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.APES.Model.Point;
import com.APES.Model.Trace;
import com.APES.Utils.DBUtils;

public class Algorithm {
	public static void main(String[] args) {
		double value=Math.pow(0.99, 708.2627);
		System.out.println(value*100);
	}
	public static double GetSimilarity(int id1,int id2,boolean isWithTime) {
		Trace t1,t2;
		String tablename=""; 
		
    if(isWithTime)
    	tablename="tracedistwithtime";
    else 
    	tablename="tracedist";
    double dist=DBUtils.GetSimilarityFromDB(id1, id2, tablename);
    if(dist<0)
    {
    	if(isWithTime)
        	tablename="pointswithtime";
        else 
        	tablename="points";
    	t1=DBUtils.GetTraceById(id1, tablename);
        t2=DBUtils.GetTraceById(id2, tablename);
        dist=TotalDist(t1, t2,0.8,0.4,isWithTime);
    }
	return dist;
}
	
	//根据编辑距离的算法计算总体轨迹的相似度
	public static double TotalDist(Trace trace1, Trace trace2,double Wdi,double Wda,boolean isWithTime) {
		List<Point> points1 = trace1.getPoints();
		List<Point> points2 = trace2.getPoints();

		int len1 = points1.size();
		int len2 = points2.size();
		double[][] step = new double[len1 + 1][len2 + 1];
		int k;
		for (k = 0; k < len1 + 1; k++) {
			step[k][0] = k * 10;
		}
		for (k = 0; k < len2 + 1; k++) {
			step[0][k] = k * 10;
		}

		for (int i = 0; i < len1; i++) {
			Point p1 = points1.get(i);
			for (int j = 0; j < len2; j++) {
				Point p2 = points2.get(j);

				double dist=0;
				if(isWithTime)
					dist= PointDist(p1, p2,Wdi,Wda);
				else 
					dist= DistanceDist(p1, p2);
				//System.out.println("("+p1.getLon()+","+p1.getLat()+"):"+"("+p2.getLon()+","+p2.getLat()+")="+dist);
				double replace = step[i][j] + dist;
				double insert = step[i][j + 1] + dist;
				double delete = step[i + 1][j] + dist;
				double min = replace > insert ? insert : replace;
				min = delete > min ? min : delete;
				step[i + 1][j + 1] = min;
			}
		}
		return step[len1][len2];
	}

	 //根据物理距离和时间距离分权计算带时间点或不带时间点的转化距离
	public static double PointDist(Point point1, Point point2,double Wdi,double Wda) {
		return DistanceDist(point1, point2)*Wdi+DateDist(point1, point2)/10000*Wda;
	}
	
	public static double DistanceDist(Point point1, Point point2) {
		return Math.hypot(point1.getLat() - point2.getLat(), point1.getLon()
				- point2.getLon());
	}
	
	public static double DateDist(Point point1, Point point2) {
		return DateSpan(point1.getTime(), point2.getTime());
	}
	
	public static double DateSpan(String date1, String date2)// 计算字符串形式下的日期差
	{
		date1 = date1.replaceAll("/", "-");
		date2 = date2.replaceAll("/", "-");
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date begin = null, end = null;
		double between;
		try {
			begin = dfs.parse(date1);
			end = dfs.parse(date2);
			between = Math.abs((end.getTime() - begin.getTime()) / 60000.0);// 除以60000是为了转换成分

		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("数据点日期转化异常!");
		}
		return between;
	}
}
