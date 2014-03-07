package pos.main.syncConnection;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.os.Handler;

public class Internet_Available {
	
	DetailedState detail_state;
	NetworkInfo network_info;

	public Internet_Available() {
		super();
	}

	
	
	public static void isNetworkAvailable(final Handler handler, final int timeout) {
	    // ask fo message '0' (not connected) or '1' (connected) on 'handler'
	    // the answer must be send before before within the 'timeout' (in milliseconds)

	    new Thread() {
	        private boolean responded = false;
	        @Override
	        public void run() { 
	            // set 'responded' to TRUE if is able to connect with google mobile (responds fast) 
	            new Thread() {      
	                @Override
	                public void run() {
	                    HttpGet requestForTest = new HttpGet("http://m.google.com");
	                    try {
	                        new DefaultHttpClient().execute(requestForTest); // can last...
	                        responded = true;
	                       // System.out.println("internet  availability ..........."+ responded);
	                    } 
	                    catch (Exception e) {
	                    	
	                    }
	                } 
	            }.start();
	            
	            try {
	                int waited = 0;
	                while(!responded && (waited < timeout)) {
	                    sleep(100);
	                    if(!responded ) {
	                        waited += 15;
	                    }
	                }
	            }
	            catch(InterruptedException e) {} // do nothing 
	            finally { 
	                if (!responded) { 
	                	handler.sendEmptyMessage(0);
	                	//System.out.println("internet  availability ..........."+ responded);
	                } 
	                else { 
	                	handler.sendEmptyMessage(1);
	                	//System.out.println("internet  availability ..........."+ responded);
	                }
	            }
	        }
	    }.start();
	}
}
