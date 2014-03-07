package pos.main.syncConnection;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import pos.main.database.Order_Database;
import pos.main.database.Transaction_Tables;
import pos.main.model.Global;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class Sale_background_process {
	String result1="";
	Order_Database order_db;
	Transaction_Tables trans_db;
	
	
	
	public Sale_background_process(Context context) {
		super();
		order_db= new Order_Database(context);
		trans_db= new Transaction_Tables(context);
		
	}

	public void send_send_trans() {
		
	
		final ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
		postparameters2send.add(new BasicNameValuePair("shop_id",Global.shop_id));
		//postparameters2send.add(new BasicNameValuePair("server_json_order", ""+server_json_order));
		//postparameters2send.add(new BasicNameValuePair("server_json_trans", ""+server_json_trans));
		//postparameters2send.add(new BasicNameValuePair("server_json_orderpayment", ""+server_json_payment));
		
		
		Handler h = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {

		        if (msg.what != 1) { // code if not connected
		        	System.out.println("internet not availability ...........");
		        	
		        } else { // code if connected
		        	System.out.println("internet  availability ...........");
		        	
		        	//if(internet_available){
		   			 try {
		   				 
		   				 result1 = MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncSalesTransaction", postparameters2send);
		   				 System.out.println("result..........."+result1);
		   				 
		   			 } catch(Exception e){
		   				 e.printStackTrace();
		   			 }
		   			 
		   			 
		   			 if(result1.length()>0){
		   				 
		   				 String [] a= result1.split("::");
		   				 System.out.println("responce id..........."+a.length+"..."+ a[0]+"............."+ a[1]+"............."+ a[2]);
		   				 
		   				 //int a1=trans_db.isSyncedTrans(a[1]);
		   				// int b=order_db.is_synced_order(a[0]);
		   			// c= order_db.is_synced_order_payment(a[2]);
		   			///	 System.out.println("responce id..........."+a1+"..."+ b+"............."+ c+".............");
		   				 
		   				 //...........8::5,6::8

		   				
		   			}
		        	//}else{
		        	//}
		        }
		    }
		};
		Internet_Available.isNetworkAvailable( h, 500);
		
	}

	private void transaction_json() {
		
		String  order_i=order_db.get_Sale_Order_id();
		String [] order_list= order_i.split(",");
		System.out.println("orders......."+order_i);
		
		for(int i=0 ; i< order_list.length; i++ ){
			String order_id= order_list[i];
			
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
