package com.APES.Utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	private static Properties properties=null;
	static
	{
		properties=new Properties();
		//直接getResourceAsStream获得资源本身,这里是相对于src下一级
		InputStream inputStream=JDBCUtils.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			properties.load(inputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("读取资源文件失败!");
		}
;	}
  private JDBCUtils(){}
  public static Connection getConnection() throws SQLException, ClassNotFoundException
  {
	  
	  Class.forName(properties.getProperty("driver"));
		return DriverManager.getConnection(properties.getProperty("url"),
				properties.getProperty("user"),properties.getProperty("password"));
  }
  
  public static void close(ResultSet rs,Connection conn,Statement statement)
  {
	  
	  if(rs!=null)
		{
			try {
				rs.close();            
			} catch (Exception e) {   
				e.printStackTrace();  
			}                         
			finally{                 
				rs=null;
			}
		}
		if(statement!=null)
		{
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				statement=null;
			}
		}
		
		if(conn!=null)
		{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				conn=null;
			}
		}
  }
}
