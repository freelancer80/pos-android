package pos.main.syncConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import pos.main.database.Items_Database;
import pos.main.database.Order_Database;
import pos.main.database.Transaction_Tables;
import pos.main.model.Global;
import pos.main.model.Order;
import pos.main.model.Transaction;

public class OrderConfirmed {
	
	boolean internet_available= false;
	Order_Database order_db;
	Transaction_Tables trans_db;
	
	Items_Database items_db;
	ArrayList<HashMap<String, String>> order_items;
	long receipt_id=0;
	String order_id="0";
	Order order;
	Transaction trans;
	String order_discount_value, order_discount_type;
	String sub_total_amount="0", total_amount= "0";
	
	String payment_type1, payment_1, payment_type2, payment_2;
	String current_date;
	
	public OrderConfirmed(Context context, ArrayList<HashMap<String, String>> order_items, String order_id, 
			String sub_total, String total, String dis_value, String dis_type, 
			String payment_type1,String  payment_1, String payment_type2, String payment_2) {
		super();
		
		//this.context= context;
		
		order_db= new Order_Database(context);
		trans_db= new Transaction_Tables(context);
		items_db= new Items_Database(context);
		this.order_items=order_items;
		this.order_id= order_id;
		sub_total_amount= sub_total;
		total_amount= total;
		order_discount_type=dis_type;
		order_discount_value= dis_value;
		this.payment_1= payment_1;
		this.payment_2= payment_2;
		this.payment_type1= payment_type1;
		this.payment_type2= payment_type2;
		current_date= getDateTime();
		
		
		
		
		
	}

	public void createReceipt() {
		receipt_id=order_db.createReceipt();
		if(receipt_id>0){
			createOrder(""+receipt_id);
		}
		
	}
	JSONArray server_json_order= new JSONArray();
	JSONArray server_json_trans= new JSONArray();
	JSONArray server_json_payment= new JSONArray();
	

	private void createOrder(String teceipt_id) {
		
		JSONObject obj = new JSONObject();
		sub_total_amount= sub_total_amount.replace(",", ".");
		total_amount= total_amount.replace(",", ".");
		order= new Order();
		order.setId(Integer.parseInt(order_id));
		order.setDiscount_type_id(order_discount_type);
		order.setDiscount_value(order_discount_value);
		order.setReceipt_number_id(""+receipt_id);
		order.setCreated_at(current_date);
		order.setTotal_amount(sub_total_amount);
		order.setTotal_amount_sold(total_amount);
		order.setStatus_id("3");
		
		try {
			obj.put("shop_order_id", order_id);
			obj.put("shop_user_id", Global.user_id);
			obj.put("order_discount_type", order_discount_type);
			obj.put("order_discount_value", order_discount_value);
			obj.put("shop_receipt_id", receipt_id);
			obj.put("total_amount", sub_total_amount);
			obj.put("sold_total_amount", total_amount);
			obj.put("sold_total_amount", total_amount);
			obj.put("status_id", "3");
			
			server_json_order.put(obj);
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		int i=order_db.updateOrder(order);
		if(i>0){
			try {
				createTransactions();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private static String KEY_DECS1 = "item_name";
	private static final String KEY_DECS2 = "description2";
	private static final String KEY_DECS3 = "description3";
	
	private static String KEY_ITEM_ID = "item_code";
	private static String KEY_ITEM_CODE_ID = "item_cms_id";
	
	private static String KEY_ITEM_PRICE = "item_price";
	private static String KEY_ITEM_SOLD_PRICE = "sold_price";
	
	private static String KEY_ITEM_DISCOUNT_TYPE = "item_discount_type";
	private static String KEY_ITEM_DISCOUNT_VALUE = "item_discount_value";
	private static String KEY_ITEM_AVAILABLE_QUANTITY = "available_quantity";
	private static String KEY_ITEM_QUANTITY = "quantity";
	
	private static final String KEY_TAX = "taxation_code";
	
	private static final String KEY_SUPPLIER_NO = "supplier_number";
	private static final String KEY_SUPPLIER_ITEM_NO = "supplier_item_number";
	private static final String KEY_BARCODE = "ean";// barcode
	private static final String KEY_BUY_PRICE = "buying_price";
	//private static final String KEY_SELL_PRICE = "selling_price";
	
	private static final String KEY_GROUP_ID = "group_id";
	private static final String KEY_COLOR_ID = "color";
	
	private static final String KEY_SIZE_ID = "size";
	
	
	
	private void createTransactions() throws JSONException {
		
		String trans_ids="";
		JSONObject obj = new JSONObject();
		for(int i=0; i< order_items.size(); i++){
			trans= new Transaction();
			
			trans.setTransaction_type_id("2");// outward
			trans.setQuantity(""+order_items.get(i).get(KEY_ITEM_QUANTITY));// buy qty
			trans.setItem_id(""+order_items.get(i).get(KEY_ITEM_ID));// 10 digit code
			trans.setReceipt_number_id(""+receipt_id);
			trans.setCreated_at( current_date);
			trans.setUpdated_at(current_date);
			trans.setOrder_number_id(order_id);
			trans.setStatus_id("3");// fix
			
			trans.setUser_id(""+Global.user_id);
			trans.setParent_type("receipt_numbers");
			trans.setParent_type_id(""+receipt_id);
			
			String a= order_items.get(i).get(KEY_ITEM_SOLD_PRICE);
			a= a.replace(",", ".");
			
			trans.setSold_price(""+a);
			trans.setDiscount_type_id(""+order_items.get(i).get(KEY_ITEM_DISCOUNT_TYPE));
			trans.setDiscount_value(""+order_items.get(i).get(KEY_ITEM_DISCOUNT_VALUE));
			trans.setItem_cms_id(""+order_items.get(i).get(KEY_ITEM_CODE_ID));
			trans.setDecs1(""+order_items.get(i).get(KEY_DECS1));
			trans.setDecs2(""+order_items.get(i).get(KEY_DECS2));
			trans.setDecs3(""+order_items.get(i).get(KEY_DECS3));
			
			trans.setSupplier_item_number(""+order_items.get(i).get(KEY_SUPPLIER_ITEM_NO));
			trans.setSupplier_number(""+order_items.get(i).get(KEY_SUPPLIER_NO));
			trans.setBarcode(""+order_items.get(i).get(KEY_BARCODE));
			
			trans.setColor_id(""+order_items.get(i).get(KEY_COLOR_ID));
			trans.setGroup_id(""+order_items.get(i).get(KEY_GROUP_ID));
			trans.setSize_id(""+order_items.get(i).get(KEY_SIZE_ID));
			
			a= order_items.get(i).get(KEY_BUY_PRICE);
			a= a.replace(",", ".");
			
			trans.setBuying_price(""+a);
			
			a= order_items.get(i).get(KEY_ITEM_PRICE);
			a= a.replace(",", ".");
			trans.setSelling_price(""+a);
			
			trans.setTaxation_code(""+order_items.get(i).get(KEY_TAX));
			trans.setAvailable_quantity(""+order_items.get(i).get(KEY_ITEM_AVAILABLE_QUANTITY));
			
			System.out.println(" available qutinty:...."+order_items.get(i).get(KEY_ITEM_AVAILABLE_QUANTITY));
			System.out.println("item sold price:......."+order_items.get(i).get(KEY_ITEM_SOLD_PRICE));
			System.out.println(" buying price.........."+order_items.get(i).get(KEY_BUY_PRICE));
			System.out.println("selling price:........."+order_items.get(i).get(KEY_ITEM_PRICE));
			System.out.println(" discount type id :...."+order_items.get(i).get(KEY_ITEM_DISCOUNT_TYPE));
			System.out.println("discount value:........"+order_items.get(i).get(KEY_ITEM_DISCOUNT_VALUE));
			System.out.println("item quantity:........."+order_items.get(i).get(KEY_ITEM_QUANTITY));// buy qty
			System.out.println("item id:..............."+order_items.get(i).get(KEY_ITEM_ID));// 10 digit code
			System.out.println("CMS id:................"+order_items.get(i).get(KEY_ITEM_CODE_ID));
			System.out.println(" decs1 :..............."+order_items.get(i).get(KEY_DECS1));
			System.out.println("");
			
			
			trans.setIsSynced("0");
			
			long t=trans_db.createItems(trans);
			
			items_db.updateSaleQuantity(order_items.get(i).get(KEY_ITEM_AVAILABLE_QUANTITY), 
					order_items.get(i).get(KEY_ITEM_QUANTITY), order_items.get(i).get(KEY_ITEM_CODE_ID));
			
			obj = new JSONObject();
			if(t>0){
				 obj = new JSONObject();
	                obj.put("pos_id", t);
	                obj.put("transaction_type_id", "2");
	                obj.put("quantity", ""+order_items.get(i).get(KEY_ITEM_QUANTITY));// buy qty
	                obj.put("item_id", ""+order_items.get(i).get(KEY_ITEM_ID));// 10 digit code
	                obj.put("item_cms_id", ""+order_items.get(i).get(KEY_ITEM_CODE_ID));
	                obj.put("user_id", Global.user_id);
	                
	                obj.put("receipt_number_id", ""+receipt_id);
	                obj.put("order_number_id", ""+order_id);
	                obj.put("status_id", "3");
	                
	                obj.put("created_at", current_date);
	                obj.put("updated_at", current_date);
	                
	                obj.put("parent_type", "receipt_numbers");
	                obj.put("parent_type_id", ""+receipt_id);
	                
	                a= order_items.get(i).get(KEY_ITEM_SOLD_PRICE);
	    			a= a.replace(",", ".");
	    			
	                obj.put("sold_price", ""+a);
	                obj.put("discount_value", ""+order_items.get(i).get(KEY_ITEM_DISCOUNT_VALUE));
	                obj.put("discount_type_id", ""+order_items.get(i).get(KEY_ITEM_DISCOUNT_TYPE));
	                
	                obj.put("description1", ""+order_items.get(i).get(KEY_DECS1));
	                obj.put("description2", ""+order_items.get(i).get(KEY_DECS2));
	                obj.put("description3", ""+order_items.get(i).get(KEY_DECS3));
	                
	                obj.put("supplier_number", ""+order_items.get(i).get(KEY_SUPPLIER_NO));
	                obj.put("supplier_item_number", ""+order_items.get(i).get(KEY_SUPPLIER_ITEM_NO));
	                
	                obj.put("ean", ""+order_items.get(i).get(KEY_BARCODE));
	                
	                obj.put("group", ""+order_items.get(i).get(KEY_GROUP_ID));
	                obj.put("color", ""+order_items.get(i).get(KEY_COLOR_ID));
	                obj.put("size", ""+order_items.get(i).get(KEY_SIZE_ID));
	                
	                a= order_items.get(i).get(KEY_BUY_PRICE);
	    			a= a.replace(",", ".");
	    			obj.put("buying_price", a);
	    			
	    			a= order_items.get(i).get(KEY_ITEM_PRICE);
	    			a= a.replace(",", ".");
	    			
	                obj.put("selling_price" , a);
	                obj.put("taxation_code", ""+order_items.get(i).get(KEY_TAX)); 
	               // obj.put("comments", comny_rec);
	                server_json_trans.put(obj);
			}
			trans_ids= trans_ids+""+ t;
		}
		payment_insert();
		System.out.println(""+trans_ids);
	}
	
	private void payment_insert() {
		
		JSONObject obj = new JSONObject();
		payment_1= payment_1.replace(",", ".");
		payment_2= payment_2.replace(",", ".");
		
		long i=order_db.create_order_payments(order_id, payment_type1, total_amount);
		try {
			obj.put("shop_order_id", order_id);
			obj.put("shop_order_payment_id", i);
			obj.put("shop_order_user_id", Global.user_id);
			obj.put("payment_type_id", payment_type1);
			obj.put("created_at", current_date);
			obj.put("total_amount", total_amount);
			
			server_json_payment.put(obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!payment_2.startsWith("0")){
		//	i=order_db.create_order_payments(order_id, payment_type2, payment_2);
		}
		
		
		send_Sale_Server();
	}
	
	 String result1 = "";
	private void send_Sale_Server() {
		
		
		
		final ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
		
		postparameters2send.add(new BasicNameValuePair("shop_id",Global.shop_id));
		postparameters2send.add(new BasicNameValuePair("server_json_order", ""+server_json_order));
		postparameters2send.add(new BasicNameValuePair("server_json_trans", ""+server_json_trans));
		postparameters2send.add(new BasicNameValuePair("server_json_orderpayment", ""+server_json_payment));
		
		Handler h = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {

		        if (msg.what != 1) { // code if not connected
		        	System.out.println("internet not availability ...........");
		        	internet_available= false;
		        } else { // code if connected
		        	System.out.println("internet  availability ...........");
		        	internet_available= true;
		        	if(internet_available){
		   			 try {
		   				 result1 = MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncSalesTransaction", postparameters2send);
		   				 System.out.println("result..........."+result1);
		   				 
		   			 } catch(Exception e){
		   				 e.printStackTrace();
		   			 }
		   			 
		   			 
		   			 if(result1.length()>0){
		   				 
		   				 String [] a= result1.split("::");
		   				 System.out.println("responce id..........."+a.length+"..."+ a[0]+"............."+ a[1]+"............."+ a[2]);
		   				 
		   				 int a1=trans_db.isSyncedTrans(a[1]);
		   				 int b=order_db.is_synced_order(a[0]);
		   				 int c= order_db.is_synced_order_payment(a[2]);
		   				 System.out.println("responce id..........."+a1+"..."+ b+"............."+ c+".............");
		   				 
		   				 //...........8::5,6::8
		   			 }
		        	}else{
		        	}
		        }
		    }
		};
		Internet_Available.isNetworkAvailable( h, 500);

	//	 System.out.println("json array..........."+server_json_order);
	//	System.out.println("json array..........."+server_json_trans);
	//	System.out.println("json array..........."+server_json_payment);

		trans_db.closeDB();
		order_db.closeDB();
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	
	
}















