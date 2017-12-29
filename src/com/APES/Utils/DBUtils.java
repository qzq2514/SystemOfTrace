package com.APES.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.APES.Algorithm.Algorithm;
import com.APES.Model.Point;
import com.APES.Model.Trace;
import com.csvreader.CsvReader;

public class DBUtils {

	public static Trace GetTraceById(int id, String tablename) // 根据id值获得数据库中的轨迹
	{
		LinkedList<Point> points = new LinkedList<Point>();
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsResultSet = null;
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("select * from " + tablename
					+ " where id=?");
			psStatement.setInt(1, id);
			rsResultSet = psStatement.executeQuery();
			while (rsResultSet.next()) {
				double lon = Double.parseDouble(rsResultSet.getString("lon"));
				double lat = Double.parseDouble(rsResultSet.getString("lat"));
				String time = null;
				if (tablename.equals("pointswithtime"))
					time = rsResultSet.getString("time");
				points.add(new Point(lon, lat, time));
			}
			return new Trace(points, id);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(rsResultSet, connect, psStatement);
		}
		return null;
	}

	public static int GetTraceNum(String tablename) {
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsResultSet = null;
		int count = 0;
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("select distinct id from "
					+ tablename);
			rsResultSet = psStatement.executeQuery();
			while (rsResultSet.next()) {
				count++;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(rsResultSet, connect, psStatement);
		}
		return count;
	}

	public static List<Integer> GetTracesId(String tablename) {
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsResultSet = null;
		List<Integer> retList=new ArrayList<Integer>();
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("select distinct id from "
					+ tablename);
			rsResultSet = psStatement.executeQuery();
			while (rsResultSet.next()) {
				int temp=rsResultSet.getInt("id");
				retList.add(temp);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(rsResultSet, connect, psStatement);
		}
		return retList;
	}
	
	public static int GetTraceMaxNum(String tablename) {
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsResultSet = null;
		int max = 0;
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("select distinct id from "
					+ tablename);
			rsResultSet = psStatement.executeQuery();
			while (rsResultSet.next()) {
				int currentId = rsResultSet.getInt("id");
				if (currentId > max)
					max = currentId;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(rsResultSet, connect, psStatement);
		}
		return max;
	}

	public static String CSV2DB(String sourceFilePath) { // 将csv文件中的点坐标信息存储到数据库中
		
		Connection connect = null;
		PreparedStatement psStatement = null;
		String RetMsg = "";
		int id = 0;
		Savepoint sp=null;
		try {
			String[] stringList;
			CsvReader reader = new CsvReader(sourceFilePath); // 默认是逗号分隔符，UTF-8编码
			connect = JDBCUtils.getConnection();
			reader.readRecord();
			stringList = reader.getValues();
			String sql = "";

			if (stringList.length == 3) {
				id = GetTraceMaxNum("pointswithtime") + 1;
				RetMsg += "Y_" + id;
				sql = "insert into pointswithtime (lon,lat,time,id) values (?,?,?,?)";
			} else {
				id = GetTraceMaxNum("points") + 1;
				RetMsg += "N_" + id;
				sql = "insert into points (lon,lat,time,id) values (?,?,?,?)";
			}
			psStatement = connect.prepareStatement(sql);
			connect.setAutoCommit(false);
			sp=connect.setSavepoint();
			while (reader.readRecord()) {
				stringList = reader.getValues();
				psStatement.setString(1, stringList[0]);
				psStatement.setString(2, stringList[1]);
				if (stringList.length == 3) {
					if (TryDateParse(stringList[2]))
						psStatement.setString(3, stringList[2]);
					else 
						throw new RuntimeException("");
				} else
					psStatement.setString(3, null);
				psStatement.setInt(4, id);
				psStatement.executeUpdate();
			}
			connect.commit(); 
			reader.close();
		} catch (Exception e) {
			try {
				connect.rollback(sp);          //这里采用回滚机制，一旦出错，返回之前的回滚点
				connect.commit();
			} catch (SQLException e1) {
				return "ERROR";
			}
			return "ERROR";
		}
		return RetMsg;
	}

	public static int JSON2DB(String jsonString) {
		JSONArray array = JSONArray.fromObject(jsonString);
		
		System.out.println(array);
		Connection connect = null;
		PreparedStatement psStatement = null;
		int id= GetTraceMaxNum("points") + 1;
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("insert into points (lon,lat,time,id) values (?,?,?,?)");
			for(int  i=0;i<array.size();i++)
			{
				String json=array.get(i).toString();
				double lng=Double.parseDouble(json.substring(json.indexOf(":")+1,json.indexOf(",")));
				double lat=Double.parseDouble(json.substring(json.lastIndexOf(":")+1,json.length()-1));
				psStatement.setDouble(1, lng);
				psStatement.setDouble(2, lat);	
				psStatement.setString(3, null);
				psStatement.setInt(4, id);
				psStatement.executeUpdate();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(null, connect, psStatement);
		}
		return id;
		
	}
	public static boolean TryDateParse(String dateString) 
	{
		dateString = dateString.replaceAll("/", "-");
		try {
			DateLocaleConverter dl = new DateLocaleConverter();
			dl.convert(dateString, "yyyy-MM-dd HH:mm");
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public static Map<String, Integer> GetSimiliarTrace(int id, String tablename) {
		Map<String, Integer> DistMap = new TreeMap<String, Integer>();// Map<编辑距离,
																		// 查询的轨迹id值>
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsResultSet = null;
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("select * from " + tablename
					+ " where id1=? or id2=?");
			psStatement.setInt(1, id);
			psStatement.setInt(2, id);
			rsResultSet = psStatement.executeQuery();
			int currentId = 0;
			while (rsResultSet.next()) {
				int id1 = rsResultSet.getInt("id1");
				int id2 = rsResultSet.getInt("id2");
				if (id1 == id)
					currentId = id2;
				else
					currentId = id1;
				String dist = rsResultSet.getDouble("dist") + "_" + currentId;
				DistMap.put(dist, currentId);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(rsResultSet, connect, psStatement);
		}
		/*Set<Map.Entry<String, Integer>> set = DistMap.entrySet();
		Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = iterator.next();
		}*/
		return DistMap;
	}
	public static double GetSimilarityFromDB(int id1, int id2, String tablename) {

		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsResultSet = null;
		double retSimilarity = -1;
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("select * from " + tablename
					+ " where (id1=? and id2=?) or (id1=? and id2=?)");
			psStatement.setInt(1, id1);
			psStatement.setInt(2, id2);
			psStatement.setInt(3, id2);
			psStatement.setInt(4, id1);
			rsResultSet = psStatement.executeQuery();
			while (rsResultSet.next()) {
				retSimilarity = rsResultSet.getDouble("dist");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(rsResultSet, connect, psStatement);
		}
		return retSimilarity;
	}
	public static void InitUpload(String idInfo)
	{
		String tableName="points";
		String distTableName="tracedist";
		boolean isWithTime=false;
		int index=idInfo.indexOf("_");
		int id=Integer.parseInt(idInfo.substring(index+1));
		if(idInfo.startsWith("Y"))
		{
			distTableName="tracedistwithtime";
			tableName="pointswithtime";
			isWithTime=true;
		}
		
		List<Integer>  list=GetTracesId(tableName);
		list.remove((Object)id);
	
		Map<Integer, Double> SimilarityArr=new TreeMap<Integer, Double>();
		for(int temp:list)
		{
			Trace t1=DBUtils.GetTraceById(id, tableName);
			Trace t2=DBUtils.GetTraceById(temp, tableName);
			double Similarity=Algorithm.TotalDist(t1, t2,0.8,0.4,isWithTime);
			SimilarityArr.put(temp, Similarity);
		}
		Connection connect = null;
		PreparedStatement psStatement = null;
		ResultSet rsResultSet = null;
		try {
			connect = JDBCUtils.getConnection();
			psStatement = connect.prepareStatement("insert into "+distTableName+" (id1,id2,dist) values (?,?,?)");
			Set<Entry<Integer, Double>> set=SimilarityArr.entrySet();
		       Iterator<Entry<Integer, Double>> iterator=set.iterator();
		       while (iterator.hasNext()) {
					Entry<Integer, Double> entry=iterator.next();
					int otherId=entry.getKey();
					double dist=entry.getValue();
					psStatement.setInt(1, id);
					psStatement.setInt(2, otherId);
					psStatement.setDouble(3, dist);
					psStatement.executeUpdate();
				}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			JDBCUtils.close(rsResultSet, connect, psStatement);
		}
	}
}
