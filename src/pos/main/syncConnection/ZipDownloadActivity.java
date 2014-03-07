package pos.main.syncConnection;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import pos.main.model.Global;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class ZipDownloadActivity extends Activity {
	ZipDownloadAsync asyn;
	
	@Override
	protected void onCreate(Bundle b) {
	super.onCreate(b);
		//http://poscms.zap-itsolutions.com/uploads/images/thumbs/
		final String zipurl=Global.domain+"/uploads/images/thumbs/";
		asyn=new ZipDownloadAsync();
		asyn.execute(zipurl);
	}
	
	private class ZipDownloadAsync extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... zipUrls) {
			for (final String url : zipUrls) {
			
				InputStream in=null;
				AndroidHttpClient httpClient=null;
				try {
					httpClient = AndroidHttpClient.newInstance("Android");
					final HttpGet httpGet = new HttpGet(url);
					httpGet.setHeader("Content-Type", "application/octet-stream");
					final HttpResponse res = httpClient.execute(httpGet);
			
					if ( res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						final HttpEntity entity = res.getEntity();
						if ( entity != null ) {
							in = entity.getContent();
							final byte[] buffer = new byte[1024];
							for (int readLen; 0 < (readLen = in.read(buffer)); ) {
								//Do something the zip buffer. Write to SD Card?
							}
						}
					}
					
				} catch (Exception e) {
					Log.e("ZipDownloadAsync","Unable to get zip file " + url, e);
					
				} finally {
					if ( in != null ) {
						try {
							in.close();
						} catch (Exception e) {
							Log.i("ZipDownloadAsync", "Could not close inputstream", e);
						}
					}
					if ( httpClient != null ) { httpClient.close(); }
				}
				} // End for loop
				return null;
			}
		}
	
}
