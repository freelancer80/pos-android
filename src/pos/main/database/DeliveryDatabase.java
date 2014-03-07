package pos.main.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pos.main.model.DeliveryNotes;
import pos.main.model.Global;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DeliveryDatabase extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "POS";

	public DeliveryDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	// creating required tables
		System.out.println(CREATE_TABLE_DELIVERYNOTE);
		db.execSQL(CREATE_TABLE_DELIVERYNOTE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERYNOTE);
		onCreate(db);
	}
	/*
	 * TABLES NAME
	 */
	
	
	private static final String TABLE_DELIVERYNOTE = "delivery_notes";
	
	private static final String KEY_ID = "id";
	
	/*
	 *  table FEILDS transaction
	 */
	
	
	//, , , , , , , ,
	//, user_id, , ,
	
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_NOTE_ID = "note_id";
	private static final String KEY_CMS_ID = "cms_id";// barcode
	private static final String KEY_STATUS_ID = "status_id";
	
	private static final String KEY_QTY = "quantity";
	private static final String KEY_ITEM_ID = "item_id";
	private static final String KEY_CREATED = "created_at";
	
	private static final String KEY_DELIVERY_DATE= "delivery_date";
	private static final String KEY_RECEIVED_AT = "received_at";
	private static final String KEY_RECEIVED_QTY = "received_quantity";
	private static final String KEY_COMMENT = "comment";
	private static final String KEY_COMPANY_NUMBER = "company_number";
	private static final String KEY_BRANCH_NUMBER = "branch_number";
	private static final String KEY_SERVER_RESPONCE = "server_response";
	
	
	
	
	private static final String CREATE_TABLE_DELIVERYNOTE = "CREATE TABLE " + TABLE_DELIVERYNOTE
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " 
			
			+KEY_CREATED + " DATETIME, "
			+KEY_DELIVERY_DATE + " DATETIME, "
			+KEY_RECEIVED_AT + " DATETIME, "
			+KEY_COMMENT + " TEXT , "+
			KEY_COMPANY_NUMBER + " TEXT , "+
			KEY_BRANCH_NUMBER + " TEXT , "+
			
			KEY_CMS_ID + " INTEGER , "+
			KEY_STATUS_ID + " INTEGER , "+ 
			KEY_QTY + " INTEGER DEFAULT '0' , "+
			KEY_NOTE_ID + " INTEGER , "+
			KEY_ITEM_ID + " TEXT , "+
			KEY_RECEIVED_QTY + " INTEGER DEFAULT '0' , "+
			KEY_SERVER_RESPONCE + " INTEGER , "+
			KEY_USER_ID + " INTEGER )";
	
	public long createItems(DeliveryNotes items){
		//, long[] tag_ids) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_ID, items.getCms_id());
		values.put(KEY_NOTE_ID, items.getNote_id());
		values.put(KEY_RECEIVED_QTY, items.getReceived_quantity());
		values.put(KEY_ITEM_ID, items.getItem_id());
		values.put(KEY_USER_ID, Global.user_id);
		values.put(KEY_STATUS_ID, "1");
		values.put(KEY_QTY, items.getQuantity());
		values.put(KEY_CMS_ID, items.getCms_id());
		values.put(KEY_BRANCH_NUMBER, items.getBranch_number());
		values.put(KEY_COMPANY_NUMBER, items.getCompany_number());
		values.put(KEY_COMMENT, items.getComment());
		values.put(KEY_RECEIVED_AT, "");
		values.put(KEY_DELIVERY_DATE, items.getDelivery_date());
		values.put(KEY_CREATED, ""+getDateTime());
		values.put(KEY_SERVER_RESPONCE, "0");
		// insert row
		long item_ids = db.insert(TABLE_DELIVERYNOTE, null, values);
		
		Log.e("items transaction added  ids ....", "items transaction added  ids ...."+item_ids);
		return item_ids;
	}
	
	/**
	 * getting all todos
	 * */
	public List<DeliveryNotes> getAllItems(String date_from, String date_to) {
		List<DeliveryNotes> items = new ArrayList<DeliveryNotes>();
		String selectQuery ="SELECT *, (sum("+KEY_QTY+" ) - sum("+ KEY_RECEIVED_QTY+")) as net FROM " + TABLE_DELIVERYNOTE +" WHERE "+KEY_DELIVERY_DATE+" between '"+date_from+"' and '"+date_to+
				"' GROUP BY "+ KEY_NOTE_ID + " ORDER by "+ KEY_DELIVERY_DATE +" DESC";
				//" GROUP BY "+ KEY_NOTE_ID;
				//+
				//
				//"SELECT  distinct note_id ,* FROM " + TABLE_DELIVERYNOTE;//+" where "+ KEY_STATUS_ID+" = '1'";
		Log.e("Query......", selectQuery);
		
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				DeliveryNotes item = new DeliveryNotes();
				
				item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				item.setNote_id((c.getString(c.getColumnIndex(KEY_NOTE_ID))));
				item.setReceived_quantity((c.getString(c.getColumnIndex(KEY_RECEIVED_QTY))));
				item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
				item.setUser_id((c.getString(c.getColumnIndex(KEY_USER_ID))));
				item.setStatus_id((c.getString(c.getColumnIndex(KEY_STATUS_ID))));
				item.setQuantity((c.getString(c.getColumnIndex(KEY_QTY))));
				item.setCms_id((c.getString(c.getColumnIndex(KEY_CMS_ID))));
				item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));
				item.setBranch_number(c.getString(c.getColumnIndex(KEY_BRANCH_NUMBER)));
				item.setCompany_number(c.getString(c.getColumnIndex(KEY_COMPANY_NUMBER)));
				item.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
				item.setReceived_at(c.getString(c.getColumnIndex(KEY_RECEIVED_AT)));
				item.setDelivery_date(c.getString(c.getColumnIndex(KEY_DELIVERY_DATE)));
				item.setIsSynced(c.getString(c.getColumnIndex(KEY_SERVER_RESPONCE)));
				item.setNetValue(c.getString(c.getColumnIndex("net")));
				
				// adding to todo list
				items.add(item);
			} while (c.moveToNext());
		}
		return items;
	}
	
	public List<DeliveryNotes> getSelectedNoteID(String note_id) {

		List<DeliveryNotes> items = new ArrayList<DeliveryNotes>();
		String selectQuery ="SELECT  * FROM " + TABLE_DELIVERYNOTE +" where "+ KEY_NOTE_ID+" = '"+note_id+"'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				DeliveryNotes item = new DeliveryNotes();
				
				item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				item.setNote_id((c.getString(c.getColumnIndex(KEY_NOTE_ID))));
				item.setReceived_quantity((c.getString(c.getColumnIndex(KEY_RECEIVED_QTY))));
				item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
				item.setUser_id((c.getString(c.getColumnIndex(KEY_USER_ID))));
				item.setStatus_id((c.getString(c.getColumnIndex(KEY_STATUS_ID))));
				item.setQuantity((c.getString(c.getColumnIndex(KEY_QTY))));
				item.setCms_id((c.getString(c.getColumnIndex(KEY_CMS_ID))));
				item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));
				item.setBranch_number(c.getString(c.getColumnIndex(KEY_BRANCH_NUMBER)));
				item.setCompany_number(c.getString(c.getColumnIndex(KEY_COMPANY_NUMBER)));
				item.setComment(c.getString(c.getColumnIndex(KEY_COMMENT)));
				item.setReceived_at(c.getString(c.getColumnIndex(KEY_RECEIVED_AT)));
				item.setDelivery_date(c.getString(c.getColumnIndex(KEY_DELIVERY_DATE)));
				
				// adding to todo list
				items.add(item);
			} while (c.moveToNext());
		}
		return items;
	}
	
	
	public String getEmptyOrder() {
		//List<Order> items = new ArrayList<Order>();
		String selectQuery = "SELECT  * FROM " + TABLE_DELIVERYNOTE+" where "+ KEY_STATUS_ID+" = '1'";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		String id = "0";
		if (c.moveToFirst()) {
			do {
				id=(c.getString((c.getColumnIndex(KEY_ID))));
				
			} while (c.moveToNext());
		}
		return id;
	}
	
	// Updating single contact
	public int updateItems(DeliveryNotes items) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_RECEIVED_QTY, items.getReceived_quantity());
		values.put(KEY_COMMENT, items.getComment());
		values.put(KEY_RECEIVED_AT, items.getReceived_at());
		
		values.put(KEY_STATUS_ID, "3");
		System.out.println("id of row.."+items.getId()+".......... 3"+"........");
		
	    // updating row
	    int i= db.update(TABLE_DELIVERYNOTE, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(items.getId()) });
	    return i;
	}
	
	public int updateItemsServer(String  id) {
		int id1=0;
		
		//for(int i=0; i<id.length; i++){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_SERVER_RESPONCE, "1");
			
		    id1= db.update(TABLE_DELIVERYNOTE, values, KEY_ID + " in ("+id+")" ,  null);
		//}
		return id1;
		
	    
	}
	
	
	public boolean getCmsId(String cms_id) {
		
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  "+KEY_CMS_ID+" FROM " + TABLE_DELIVERYNOTE + " WHERE "+ KEY_CMS_ID + " = '"+ cms_id+"'";
		Log.e("login id return..", selectQuery);
		
		Cursor c = db.rawQuery(selectQuery, null);
		String item_id="";
		try {
			if(c != null){
				c.moveToFirst();
				if(c.moveToFirst()){
					item_id=""+c.getString((c.getColumnIndex(KEY_CMS_ID)));
					System.out.println(item_id+".....item inserted id is...:"+ cms_id);
					
					if(item_id.equals(cms_id)){
						return true;
					}else{
						return false;
					}
				}
			}else  return false;
		} finally { 
			c.close();
		}
		return false;
	}
	
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	// closing database
	 		public void closeDB() {
	 			SQLiteDatabase db = this.getReadableDatabase();
	 			if (db != null && db.isOpen())
	 				db.close();
	 		}
	}