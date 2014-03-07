package pos.main.syncConnection;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.StrictMode;

import pos.main.model.Global;


public class GetFile_Server {
	
	String DownloadUrl =Global.domain+"/uploads/images/thumbs/";
	public void getFile() {
		int size;
	      URL url;
	      
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			url = new URL(DownloadUrl);
			URLConnection conn;
			conn = url.openConnection();
			size = conn.getContentLength();
			if (size < 0)
				System.out.println("Could not determine file size.");
	      	else
	      		System.out.println("The size of the file is = "+size + "bytes");
		      
				conn.getInputStream().close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}


}
