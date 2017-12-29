package com.APES.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	public static void In2Out(InputStream in, OutputStream out) {
          byte[] buffer=new byte[1024];
          int len=0;
          try {
			while((len=in.read(buffer))!=-1)
			  {
				  out.write(buffer,0,len);
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public static void close (InputStream in, OutputStream out)
	 {
		 if(in!=null)
		 {
			 try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 finally
			 {
				 in=null;
			 }
		 }
		 if(out!=null)
		 {
			 try {
				 out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 finally
			 {
				 out=null;
			 }
		 }
	 }
}
