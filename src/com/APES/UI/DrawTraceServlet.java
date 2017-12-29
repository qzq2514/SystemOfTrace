package com.APES.UI;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.APES.Model.Point;
import com.APES.Model.Trace;
import com.APES.Utils.DBUtils;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class DrawTraceServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, 
	    HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String traceId=request.getParameter("traceId");
		int Id=Integer.valueOf(traceId.substring(1));
		String tablename="";
		if(traceId.startsWith("Y"))
			tablename="pointswithtime";
		else 
			tablename="points";
		Trace trace=DBUtils.GetTraceById(Id,tablename);
		List<Point> points=trace.getPoints();
		String jsonstr = new Gson().toJson(points);        //将轨迹点集合转为json
		//System.out.println(traceId+"::"+jsonstr);
		response.getWriter().write(jsonstr);
	}


	
}
