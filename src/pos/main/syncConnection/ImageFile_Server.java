package pos.main.syncConnection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.util.ByteArrayBuffer;

import android.os.Environment;
import android.util.Log;

public class ImageFile_Server {
	private String _zipFile; 
	private String _location; 
	
	public ImageFile_Server(String zipFile, String location) {
		
		//String zipFile1 = Environment.getExternalStorageDirectory() + "/files.zip"; 
		//String unzipLocation1 = Environment.getExternalStorageDirectory() + "/unzipped/"; 
		
		_zipFile =Environment.getExternalStorageDirectory() + "/files.zip"; 
		_location = Environment.getExternalStorageDirectory() + "/unzipped/"; 
		_dirChecker(""); 
	}
		 
	public void unzip() {
		try  { 
			FileInputStream fin = new FileInputStream(_zipFile); 
			ZipInputStream zin = new ZipInputStream(fin); 
			ZipEntry ze = null; 
			while ((ze = zin.getNextEntry()) != null) { 
				Log.v("Decompress", "Unzipping " + ze.getName()); 
				if(ze.isDirectory()) { 
					_dirChecker(ze.getName()); 
				} else { 
					FileOutputStream fout = new FileOutputStream(_location + ze.getName()); 
					for (int c = zin.read(); c != -1; c = zin.read()) { 
						fout.write(c); 
					} 
 
					zin.closeEntry(); 
					fout.close(); 
				} 
			}
			
			zin.close(); 
		} catch(Exception e) { 
			Log.e("Decompress", "unzip", e); 
		} 
	}
		 
	private void _dirChecker(String dir) { 
		File f = new File(_location + dir); 
		
		if(!f.isDirectory()) { 
			f.mkdirs(); 
		} 
	}
		
	
	
	
	
    String fileName = "myclock_db.db";

  // DownloadDatabase(DownloadUrl,fileName);

   // and the method is

public void DownloadDatabase(String DownloadUrl, String fileName) {
	
   try {
       File root = android.os.Environment.getExternalStorageDirectory();
       File dir = new File(root.getAbsolutePath() + "/myclock/databases");
       if(dir.exists() == false){
            dir.mkdirs();  
       }

       URL url = new URL("http://myexample.com/android/");
       File file = new File(dir,fileName);

       long startTime = System.currentTimeMillis();
       Log.d("DownloadManager" , "download url:" +url);
       Log.d("DownloadManager" , "download file name:" + fileName);

       URLConnection uconn = url.openConnection();
       //uconn.setReadTimeout(TIMEOUT_CONNECTION);
      // uconn.setConnectTimeout(TIMEOUT_SOCKET);

       InputStream is = uconn.getInputStream();
       BufferedInputStream bufferinstream = new BufferedInputStream(is);

       ByteArrayBuffer baf = new ByteArrayBuffer(5000);
       int current = 0;
       while((current = bufferinstream.read()) != -1){
           baf.append((byte) current);
       }

       FileOutputStream fos = new FileOutputStream( file);
       fos.write(baf.toByteArray());
       fos.flush();
       fos.close();
       Log.d("DownloadManager" , "download ready in" + ((System.currentTimeMillis() - startTime)/1000) + "sec");
       int dotindex = fileName.lastIndexOf('.');
       if(dotindex>=0){
           fileName = fileName.substring(0,dotindex);
       }
   }catch(IOException e) {
       Log.d("DownloadManager" , "Error:" + e);
   }

}
	
	public void getFile() {
		int size;
	      URL url;
		try {
			url = new URL("http://www.server.com");
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

	


	public void downloadFromUrl(String url, String outputFileName) {
		
		File dir = new File(Environment.getExternalStorageDirectory() + "/");
		if (dir.exists() == false) {
			dir.mkdirs();
	    }
		try {
			URL downloadUrl = new URL("http://poscms.zap-itsolutions.com/uploads/images/thumbs/"); // you can write any link here
			
			File file = new File(dir, outputFileName);          
	        /* Open a connection to that URL. */
			URLConnection ucon = downloadUrl.openConnection();
	
	        /*
	         * Define InputStreams to read from the URLConnection.
	         */
	        InputStream is = ucon.getInputStream();
	        BufferedInputStream bis = new BufferedInputStream(is);
	
	        /*
	         * Read bytes to the Buffer until there is nothing more to read(-1).
	         */
	        ByteArrayBuffer baf = new ByteArrayBuffer(5000);
	        int current = 0;
	        while ((current = bis.read()) != -1) {
	            baf.append((byte) current);
	        }
	
	        /* Convert the Bytes read to a String. */
	        FileOutputStream fos = new FileOutputStream(file);          
	        fos.write(baf.toByteArray());
	        fos.flush();
	        fos.close();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}



}
