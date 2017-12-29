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

public class initMyDraw extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString=request.getParameter("jsontrace");
		int id=DBUtils.JSON2DB(jsonString);
		DBUtils.InitUpload("N_"+id);
		response.getWriter().write(id+"");

	}

}
