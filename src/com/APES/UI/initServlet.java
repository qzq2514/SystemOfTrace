package com.APES.UI;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.APES.Model.Point;
import com.APES.Model.Trace;
import com.APES.Utils.DBUtils;
import com.APES.Utils.JDBCUtils;

public class initServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int countWithoutTime = DBUtils.GetTraceNum("points");
		String traceId = request.getParameter("traceId");
		int countWithTime = DBUtils.GetTraceNum("pointswithtime");
		List<Integer> TraceListWithoutTime = new LinkedList<Integer>();
		List<Integer> TraceListWithTime = new LinkedList<Integer>();
		for (int i = 1; i <= countWithoutTime; i++) {
			TraceListWithoutTime.add(i);
		}

		for (int i = 1; i <= countWithTime; i++) {
			TraceListWithTime.add(i);
		}
		this.getServletContext().removeAttribute("traceId");
		this.getServletContext().setAttribute("TraceListWithoutTime",
				TraceListWithoutTime);
		this.getServletContext().setAttribute("TraceListWithTime",
				TraceListWithTime);
		if (traceId != null && !traceId.equals(""))
			this.getServletContext().setAttribute("traceId", traceId);
		request.getRequestDispatcher("/front/index.jsp").forward(request,
				response);

	}

}
