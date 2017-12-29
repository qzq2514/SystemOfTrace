package com.APES.UI;

import java.text.DecimalFormat;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.APES.Model.QuerySimilarity;
import com.APES.Utils.DBUtils;

public class MYQueryTraceServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonId=request.getParameter("jsonId");
		if(jsonId==null || jsonId.equals(""))
		{
		  String errormessage="出错了！请先绘制自定义轨迹再进行查询。<br>";
		request.setAttribute("errormessage", errormessage);
		request.getRequestDispatcher("/Failure.jsp").forward(request, response);
		}
		else {
			int id=Integer.parseInt(jsonId);
			Map<String, Integer> distMap=DBUtils.GetSimiliarTrace(id, "tracedist");
			List<QuerySimilarity> QueryList=new ArrayList<QuerySimilarity>(); 
		    DecimalFormat df = new DecimalFormat("00.000");
		    Set<Map.Entry<String,Integer>> set=distMap.entrySet();
		       Iterator<Map.Entry<String,Integer>> iterator=set.iterator();
		       while (iterator.hasNext()) {
					Map.Entry<String,Integer> entry=iterator.next();
					double dist=Double.parseDouble(entry.getKey().substring(0, entry.getKey().indexOf("_")));
					int queryId=entry.getValue();
						dist=Math.pow(0.8,dist)*100;
					QueryList.add(new QuerySimilarity(queryId,Double.parseDouble(df.format(dist))));
				}
		       this.getServletContext().setAttribute("isWithTime","N");
		       this.getServletContext().setAttribute("QueryList", QueryList);
		       this.getServletContext().setAttribute("thisId", id);
		       this.getServletContext().removeAttribute("traceId");
		       request.getRequestDispatcher("/front/queryListShow.jsp").forward(request, response);
		}
	}
}
