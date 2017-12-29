package com.APES.UI;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.APES.Algorithm.Algorithm;

public class MYCalculateSimilarityServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonId=request.getParameter("jsonId");
		String choice=request.getParameter("choice");
		if(choice==null || choice.equals("") || jsonId==null || jsonId.equals(""))
		{
		  String errormessage="出错了！请先绘制自定义轨迹再选择已有轨迹进行计算。<br>";
		request.setAttribute("errormessage", errormessage);
		request.getRequestDispatcher("/Failure.jsp").forward(request, response);
		}
		else {
			int id1=Integer.parseInt(jsonId);
			int id2=Integer.parseInt(choice.substring(1));
			double Similarity=Algorithm.GetSimilarity(id1, id2,false);
			DecimalFormat df = new DecimalFormat("0.000000");
			Similarity=Math.pow(0.8,Similarity)*100;
			String successmessage= "新轨迹与原"+"<font color='blue' size='20'>轨迹"+id2+"</font> 的相似度为：<br>"+df.format(Similarity)+"%<br>";
			this.getServletContext().removeAttribute("traceId");
			request.setAttribute("successmessage", successmessage);
			this.getServletContext().setAttribute("traceId", "N"+id1);
			request.getRequestDispatcher("/Success.jsp").forward(
					request, response);	
		}
		
	}
	
}
