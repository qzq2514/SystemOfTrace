package com.APES.UI;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.APES.Utils.DBUtils;
import com.APES.Utils.IOUtils;

public class fileuploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);

		List<FileItem> list;
		try {
			list = fileUpload.parseRequest(request);
			for (FileItem item : list) {
				if (!item.isFormField()) {
					String filename = item.getName();
					if (filename == "" || !filename.endsWith("csv")) {   //文件为空或者文件格式不合格
						String errormessage = "出错了！上传的文件不能为空且必须为csv文件，请选择正确的csv文件<br>";
						request.setAttribute("errormessage", errormessage);
						request.getRequestDispatcher("Failure.jsp").forward(
								request, response);
						
					} else {
						String filepathInDisk = this.getServletContext()
								.getRealPath("/upload/" + filename);
						InputStream in = item.getInputStream();
						OutputStream out = new FileOutputStream(filepathInDisk);
						IOUtils.In2Out(in, out);
						IOUtils.close(in, out);
						String msg=DBUtils.CSV2DB(filepathInDisk);
						if(msg.equals("ERROR"))
						{
							String errormessage="出错了！文件上传失败，请选择内容格式正确的文件<br>";
							request.setAttribute("errormessage", errormessage);
							request.getRequestDispatcher("/Failure.jsp").forward(request, response);
						}
						else {
							DBUtils.InitUpload(msg);
							msg=msg.replaceAll("_", "");
							String successmessage = "文件上传成功!返回查看上传路径!<br>";
							request.setAttribute("successmessage", successmessage);
							this.getServletContext().setAttribute("traceId", msg);
							request.getRequestDispatcher("/Success.jsp").forward(
									request, response);	
						}
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

}
