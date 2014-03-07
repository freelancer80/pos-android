package pos.main.syncConnection;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pos.main.database.DeliveryDatabase;
import pos.main.database.Items_Database;
import pos.main.model.DeliveryNotes;
import pos.main.model.Global;
import pos.main.model.Items;
import android.content.Context;
import android.util.Log;

public class SyncItems {
	
	String TAG= "";
	Items_Database db_items;
	DeliveryDatabase db_delivery;
	

	
	
	public SyncItems(Context context) {
		super();
		db_items = new Items_Database(context.getApplicationContext());
		db_delivery= new DeliveryDatabase(context.getApplicationContext());
	}

	/*
	*Result....[
		*{"id":"12843","item_id":"5666091601","description1":"test","description2":"testing","description3":
		*"Earring","supplier_number":"000","supplier_item_number":"000","ean":"3002093015",
		*"color":"Fuchsia","grop":"20","size":"No Size 00","buying_price":null,"selling_price":3.99,
		*"taxation_code":1,"created_at":"2014-01-23 12:17:12"},
		
		*{"id":"12844","item_id":"5666091601","description1":"test","description2":"testing","description3":"testEarring",
		*"supplier_number":"000","supplier_item_number":"000","ean":"3002093015","color":"Fuchsia","grop":"20",
		*"size":"No Size 00","buying_price":null,"selling_price":3.99,"taxation_code":1,"created_at":"2014-01-23 12:22:12"},
		*
		*{"id":"12845","item_id":"5666091601","description1":"test","description2":"testing","description3":"testEarring",
		*"supplier_number":"222","supplier_item_number":"214","ean":"3002093015","color":"Fuchsia","grop":"20","size":"No Size 00",
		*"buying_price":null,"selling_price":3.99,"taxation_code":1,"created_at":"2014-01-23 12:35:12"},
		
		*{"id":"12846","item_id":"5666091601","description1":"test","description2":"testing","description3":"testrring",
		*"supplier_number":"222","supplier_item_number":"214","ean":"3002093015","color":"Fuchsia","grop":"20",
		*"size":"No Size 00","buying_price":null,"selling_price":3.99,"taxation_code":1,"created_at":"2014-01-23 13:43:13"},
		
*/
	
	
	public String getItems_Count(String shop_id) {
		String result = "";
		ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
		postparameters2send.add(new BasicNameValuePair("shop_id", Global.shop_id));
		try {
			result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncItems", postparameters2send);
			//System.out.println("........."+result);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public String send_Shop(JSONArray jsonArray) {
		String result = "";
		
		try{
			for(int i = 0; i < jsonArray.length(); i++){
				
				JSONObject c = jsonArray.getJSONObject(i);
				
				String id = c.getString("id");
				String item_id = c.getString("item_id");
				String barcode = c.getString("ean");
				String item_decs = c.getString("description1");
				String item_decs2 = c.getString("description2");
				String item_decs3 = c.getString("description3");
				String item_size = c.getString("size");
				String item_color = c.getString("color");
				String item_group = c.getString("grop");
				String item_buying_price = c.getString("buying_price");
				String item_selling_price = c.getString("selling_price");
				String tax = c.getString("taxation_code");
				String created_at= c.getString("created_at");
				
				
				int item_id1 = db_items.getItemId(item_id);
				
				Items item= new Items(Integer.parseInt(id), item_id,  item_group, item_color, item_size, item_decs, item_decs2,item_decs3, "", "", 
						barcode, item_buying_price, item_selling_price, tax, "", "", "", created_at, "");
				
				System.out.println("json array..........."+item_id+"........."+ id+ item_decs+ item_color);
				
				
				ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
				postparameters2send.add(new BasicNameValuePair("id", id));
				
				if(item_id1>0){
					
					item.setId(item_id1);
					int update= db_items.updateItems(item);
					System.out.println("update item ..........."+update);
					if(update>0){
						result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncItemsUpdate", postparameters2send);
						if(result.equals("OK")){
						}
					}
					
				}else{
					long item_ids = db_items.createItems(item);
					System.out.println("new item inserted..........."+item_ids);
					if(item_ids>0){
						result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncItemsUpdate", postparameters2send);
						if(result.equals("OK")){
						}
					}
				}
			}
			if(result.length()>0){
				result="Item(s) Synced Successfully." ;
			}else{
				result="No Update Found.";
			}
			db_items.closeDB();
			
		}catch (JSONException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}*/
	
	public String send_shop1(int index, JSONArray jsonArray) {
		String result="";
		
		JSONObject c;
		try {
			c = jsonArray.getJSONObject(index);
			String id = c.getString("id");
			String item_id = c.getString("item_id");
			String barcode = c.getString("ean");
			String item_decs = c.getString("description1");
			String item_decs2 = c.getString("description2");
			String item_decs3 = c.getString("description3");
			String item_size = c.getString("size");
			String item_color = c.getString("color");
			String item_group = c.getString("grop");
			String item_buying_price = c.getString("buying_price");
			String item_selling_price = c.getString("selling_price");
			String tax = c.getString("taxation_code");
			String created_at= c.getString("created_at");
			String supplier_no= c.getString("supplier_number");
			String supplier_item_number= c.getString("supplier_item_number");
			
			
//<<<<<<< HEAD
//			int item_id1 = db_items.getItemId(item_id);
			
			Items item= new Items(Integer.parseInt(id),item_id, 
					item_group, item_color, item_size, 
					item_decs, item_decs2,item_decs3,
					supplier_no, supplier_item_number, 
					barcode, item_buying_price, item_selling_price, 
					tax, "0", "", "", created_at, "");
//=======
//			Items item= new Items(Integer.parseInt(id), item_id, item_group, item_color, item_size, item_decs, item_decs2, item_decs3, "", "", 
//					barcode, item_buying_price, item_selling_price, tax, "", "", "", created_at, "");
//>>>>>>> 193857e35754921313550ba29f46a12aa574b85d
			
			int item_id1 = db_items.getItemId(item_id);
			
			if(item_id1>0){
				item.setId(item_id1);
				int update= db_items.updateItems(item);
				System.out.println("update item ..........."+update);
				if(update>0){
					result=""+id;
					//result=MySqlConnection.executeHttpPost("http://poscms.zap-itsolutions.com/b2c.php/pScripts/syncItemsUpdate", postparameters2send);
					if(result.equals("OK")){
					}
				}
				
			}else{
				long item_ids = db_items.createItems(item);
				result=""+item_ids;
				System.out.println("new item inserted..........."+item_ids);
				if(item_ids>0){
					result=""+id;
				//	result=MySqlConnection.executeHttpPost("http://poscms.zap-itsolutions.com/b2c.php/pScripts/syncItemsUpdate", postparameters2send);
					if(result.equals("OK")){
						
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String sendItemUpdate(String [] id, String shop_id) {
		String result = "";
		try{
			String a= Arrays.toString(id);
			ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
			postparameters2send.add(new BasicNameValuePair("id", a));
			postparameters2send.add(new BasicNameValuePair("shop_id", Global.shop_id));
			
			result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncItemsUpdate", postparameters2send);
			System.out.println("result send server........."+a+ "......"+result);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*public String  getDeliveryNote(String shop_id) {

		String result = "";
		
		ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
		postparameters2send.add(new BasicNameValuePair("shop_id", Global.shop_id));
		
		[8:03:37 PM] Baran Khan: [{"id":"2","item_id":"2003371000",
		"branch_number":"4046108000036","company_number":"4046108000005","quantity":3}]
		try {
			result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncDeliveryNotes", postparameters2send);
			
			
			if(result.length()>0 && !result.equals("\"\"")){
				
				JSONArray jsonArray = new JSONArray(result);
				System.out.println(jsonArray.length());
				
				for(int i = 0; i < jsonArray.length(); i++){
					
					JSONObject c = jsonArray.getJSONObject(i);
					
					//String id = c.getString("id");
					String cms_id= c.getString("id");
					String item_id = c.getString("item_id");
					String branch_number = c.getString("branch_number");
					String company_number = c.getString("company_number");
					String quantity = c.getString("quantity");
					String delivery_date = c.getString("delivery_date");
					String note_id = c.getString("note_id");
					
					int item_id1 = db_items.getItemId(item_id);
					System.out.println("item inserted id is...:"+ item_id1);
					
					DeliveryNotes items= new DeliveryNotes(note_id, item_id, quantity, delivery_date, company_number, branch_number, cms_id);
					
					//DeliveryNotes items= new DeliveryNotes();
					
					items.setBranch_number(""+branch_number);
					items.setCms_id(""+cms_id);
					items.setComment("");
					items.setCompany_number(""+company_number);
					items.setCreated_at("");
					items.setDelivery_date(""+delivery_date);
					items.setItem_id(""+item_id);
					items.setNote_id(""+note_id);
					items.setQuantity(""+quantity);
					items.setReceived_at("");
					items.setReceived_quantity("");
					items.setStatus_id("");
					items.setUser_id("");
					
					long id=db_delivery.createItems(items);
					System.out.println("id"+id);
					
					System.out.println("json array..........."+cms_id);
					
					postparameters2send= null;
					postparameters2send = new ArrayList<NameValuePair>();
					
					postparameters2send.add(new BasicNameValuePair("id", cms_id));
					if(id>0){
						result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncDeliveryNotesUpdate", postparameters2send);
						System.out.println("json array..........."+result);
					}
				}
				result="Item(s) Synced Successfully." ;
			}else{
				result="No Update Found.";
			}
			
			db_delivery.closeDB();
			
		}catch (JSONException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;	
	}*/
	
	public String getIDelvery_Count(String shop_id) {
		String result = "";
		ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
		postparameters2send.add(new BasicNameValuePair("shop_id", Global.shop_id));
		try {
			result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncDeliveryNotes", postparameters2send);
			System.out.println("........."+result);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 02-05 16:17:13.233: I/System.out(26444): Result....[{"id":"34","item_id":"0000011000","branch_number":"4046108000036","company_number":"4046108000005","quantity":3,"delivery_date":"2014-01-28 00:00:00","note_id":"6000-1"},{"id":"35","item_id":"0000071500","branch_number":"4046108000036","company_number":"4046108000005","quantity":2,"delivery_date":"2014-01-28 00:00:00","note_id":"6000-1"},{"id":"36","item_id":"0000301500","branch_number":"4046108000036","company_number":"4046108000005","quantity":4,"delivery_date":"2014-01-28 00:00:00","note_id":"6000-1"},{"id":"37","item_id":"0000761400","branch_number":"4046108000036","company_number":"4046108000005","quantity":5,"delivery_date":"2014-01-28 00:00:00","note_id":"60002-3"},{"id":"38","item_id":"0000791300","branch_number":"4046108000036","company_number":"4046108000005","quantity":8,"delivery_date":"2014-01-28 00:00:00","note_id":"60002-3"},{"id":"39","item_id":"0000771100","branch_number":"4046108000036","company_number":"4046108000005","quantity":10,"delivery_date":"2014-01-28 00:00:00","note_id":"60002-3"},{"id":"40","item_id":"0001101100","branch_number":"4046108000036","company_number":"4046108000005","quantity":6,"delivery_date":"2014-01-28 00:00:00","note_id":"60002-3"},{"id":"385","item_id":"0001231100","branch_number":"4046108000036","company_number":"4046108000005","quantity":3,"delivery_date":"2014-01-29 00:00:00","note_id":"7000-1"},{"id":"386","item_id":"2003421400","branch_number":"4046108000036","company_number":"4046108000005","quantity":2,"delivery_date":"2014-01-29 00:00:00","note_id":"7000-1"},{"id":"387","item_id":"2003421000","branch_number":"4046108000036","company_number":"4046108000005","quantity":4,"delivery_date":"2014-01-29 00:00:00","note_id":"7000-1"},{"id":"388","item_id":"2003411400","branch_number":"4046108000036","company_number":"4046108000005","quantity":5,"delivery_date":"2014-01-29 00:00:00","note_id":"70002-3"},{"id":"389","item_id":"2003401400","branch_number":"4046108000036","company_number":"4046108000005","quantity":8,"delivery_date":"2014-01-29 00:00:00","note_id":"70002-3"},{"id":"390","item_id":"2003401000","branch_number":"4046108000036","company_number":"4046108000005","quantity":10,"delivery_date":"2014-01-29 00:00:00","note_id":"70002-3"},{"id":"391","item_id":"2003391400","branch_number":"4046108000036","company_number":"4046108000005","quantity":6,"delivery_date":"2014-01-29 00:00:00","note_id":"70002-3"},{"id":"398","item_id":"2003431600","branch_number":"4046108000036","company_number":"4046108000005","quantity":3,"delivery_date":"2014-01-30 00:00:00","note_id":"8000-1"},{"id":"399","item_id":"2003421400","branch_number":"4046108000036","company_number":"4046108000005","quantity":2,"delivery_date":"2014-01-30 00:00:00","note_id":"8000-1"},{"id":"400","item_id":"2003421000","branch_number":"4046108000036","company_number":"4046108000005","quantity":4,"delivery_date":"2014-01-30 00:00:00","note_id":"8000-1"},{"id":"401","item_id":"2003411400","branch_number":"4046108000036","company_number":"4046108000005","quantity":5,"delivery_date":"2014-01-30 00:00:00","note_id":"80002-3"},{"id":"402","item_id":"2003401400","branch_number":"4046108000036","company_number":"4046108000005","quantity":8,"delivery_date":"2014-01-30 00:00:00","note_id":"80002-3"},{"id":"403","item_id":"2003401000","branch_number":"4046108000036","company_number":"4046108000005","quantity":10,"delivery_date":"2014-01-30 00:00:00","note_id":"80002-3"},{"id":"404","item_id":"2003391400","branch_number":"4046108000036","company_number":"4046108000005","quantity":6,"delivery_date":"2014-01-30 00:00:00","note_id":"80002-3"},{"id":"405","item_id":"2003431600","branch_number":"4046108000036","company_number":"4046108000005","quantity":3,"delivery_date":"2014-01-30 00:00:00","note_id":"9000-1"},{"id":"406","item_id":"2003421400","branch_number":"4046108000037","company_number":"4046108000006","quantity":2,"delivery_date":"2014-01-30 00:00:00","note_id":"9000-1"},{"id":"407","item_id":"2003421000","branch_number":"4046108000037","company_number":"4046108000006","quantity":

	 */
	public String saveDeliveryItems(int index, JSONArray jsonArray) {
		JSONObject c;
		try {
			//get cms id from table 
			c = jsonArray.getJSONObject(index);
			String cms_id= c.getString("id");
			String item_id = c.getString("item_id");
			String branch_number = c.getString("branch_number");
			String company_number = c.getString("company_number");
			String quantity = c.getString("quantity");
			String delivery_date = c.getString("delivery_date");
			String note_id = c.getString("note_id");
			//String created_at= c.getString("created_at");
			int item_id1 = db_items.getItemId(item_id);
			System.out.println("item inserted id is...:"+ item_id1);
			boolean cmd_id_found=db_delivery.getCmsId(cms_id);
			
			System.out.println("found.........."+cmd_id_found);
			
			if(cmd_id_found){
				
			}else if(!cmd_id_found){
				
				DeliveryNotes items= new DeliveryNotes(note_id, item_id, quantity, delivery_date, company_number, branch_number, cms_id);
				
				items.setBranch_number(""+branch_number);
				items.setCms_id(""+cms_id);
				items.setComment("");
				items.setCompany_number(""+company_number);
				items.setCreated_at("");
				items.setDelivery_date(""+delivery_date);
				items.setItem_id(""+item_id);
				items.setNote_id(""+note_id);
				
				items.setQuantity(""+quantity);
				items.setReceived_at("");
				items.setReceived_quantity("0");
				
				items.setStatus_id("");
				items.setUser_id(""+Global.user_id);
				
				long id=db_delivery.createItems(items);
				System.out.println("id"+id);
				
				System.out.println("json array..........."+cms_id);
				
				//postparameters2send= null;
				//postparameters2send = new ArrayList<NameValuePair>();
				
				//postparameters2send.add(new BasicNameValuePair("id", cms_id));
				if(id>0){
				//	result=MySqlConnection.executeHttpPost("http://poscms.zap-itsolutions.com/b2c.php/pScripts/syncDeliveryNotesUpdate", postparameters2send);
				//	System.out.println("json array..........."+result);
				}
			}
			return cms_id;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean sendDeliveryReceivde(String [] id) {
		String result = "";
		try{
			String a= Arrays.toString(id);
			a= a.substring(1, a.length()-1);
			ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
			postparameters2send.add(new BasicNameValuePair("id", a));
			result=MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncDeliveryNotesUpdate", postparameters2send);
			System.out.println("result send server........."+a+ "......"+result);
			if(result.equals("null")){
				return false;
			}else {
				return true;
			}
			//return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	
	}
	
	
	
}
