package pos.main.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pos.main.model.Global;
import pos.main.model.Order;
import pos.main.model.Transaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Order_Database extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "POS";

	public Order_Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	// creating required tables
		System.out.println(CREATE_TABLE_ORDER);
		db.execSQL(CREATE_TABLE_ORDER);
		db.execSQL(CREATE_TABLE_ORDER_PAYMENTS);
		db.execSQL(CREATE_TABLE_RECEIPT);
		db.execSQL(CREATE_TABLE_PAYMENTS_TYPE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_PAYMENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIPTS_NUMBER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTS_TYPE);
		// create new tables
		onCreate(db);
	}
	/*
	 * TABLES NAME
	 */
	
	
	private static final String TABLE_ORDER = "orders";
	private static final String TABLE_ORDER_PAYMENTS = "orders_payments";
	private static final String TABLE_RECEIPTS_NUMBER = "receipt_numbers";
	private static final String TABLE_PAYMENTS_TYPE = "payments_type";
	
	private static final String KEY_ID = "id";
	
	/*
	 *  table FEILDS transaction
	 */
	
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_RECEIPT_NUMBER = "receipt_number_id";
	private static final String KEY_ORDER_ID = "order_number_id";// barcode
	private static final String KEY_STATUS_ID = "status_id";
	
	private static final String KEY_DISCOUNT_VALUE = "discount_value";
	private static final String KEY_DISCOUNT_TYPE_ID = "discount_type_id";
	private static final String KEY_CREATED = "created_at";
	private static final String KEY_IS_SYNCED = "isSynced";
	
	
	/*
	 * table transaction types...
	 */
	private static final String KEY_TITLE = "title";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_PAYMENT_TYPE_ID = "payment_type_id";
	private static final String KEY_TOTALAMOUNT = "total_amount";
	private static final String KEY_TOTALAMOUNT_SOLD = "total_sold_amount";
	
	
	private static final String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " 
			+KEY_CREATED + " DATETIME, "+ 
			KEY_TOTALAMOUNT + " REAL DEFAULT '0', "+ 
			KEY_RECEIPT_NUMBER + " INTEGER , "+
			KEY_STATUS_ID + " INTEGER DEFAULT '1', "+ 
			KEY_TOTALAMOUNT_SOLD + " INTEGER DEFAULT '0', "+
			KEY_DISCOUNT_VALUE + " REAL DEFAULT '0', "+ 
			KEY_DISCOUNT_TYPE_ID + " INTEGER DEFAULT '0', "+
			KEY_USER_ID + " INTEGER , "+KEY_IS_SYNCED + " INTEGER DEFAULT '0')";
	
	private static final String CREATE_TABLE_ORDER_PAYMENTS = "CREATE TABLE " + TABLE_ORDER_PAYMENTS
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " +
			KEY_ORDER_ID + " INTEGER , "+ 
			KEY_PAYMENT_TYPE_ID + " INTEGER , "+
			KEY_AMOUNT + " REAL DEFAULT '0', "+ KEY_IS_SYNCED  + " INTEGER DEFAULT '0')";
	
	private static final String CREATE_TABLE_RECEIPT = "CREATE TABLE " + TABLE_RECEIPTS_NUMBER
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " 
			+KEY_CREATED + " DATETIME );";
	
	private static final String CREATE_TABLE_PAYMENTS_TYPE = "CREATE TABLE " + TABLE_PAYMENTS_TYPE
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";
	
	public long createOrder(Order items){
		//, long[] tag_ids) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_TOTALAMOUNT, items.getTotal_amount());
		values.put(KEY_RECEIPT_NUMBER, items.getReceipt_number_id());
		values.put(KEY_TOTALAMOUNT_SOLD, items.getTotal_amount_sold());
		values.put(KEY_USER_ID, items.getReceipt_number_id());
		values.put(KEY_STATUS_ID, items.getStatus_id());
		values.put(KEY_DISCOUNT_VALUE, items.getDiscount_value());
		values.put(KEY_DISCOUNT_TYPE_ID, items.getDiscount_type_id());
		values.put(KEY_CREATED, getDateTime());
		
		// insert row
		long item_ids = db.insert(TABLE_ORDER, null, values);
		
		Log.e("items transaction added  ids ....", "items transaction added  ids ...."+item_ids);
		return item_ids;
	}
	
	public long create_order_id() {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_CREATED, getDateTime());
		long item_ids = db.insert(TABLE_ORDER, null, values);
		Log.e("order id ....", "i ...."+item_ids);
		return item_ids;
	}
	
	public void name() {
		
	}
	
	public long createReceipt(){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_CREATED, getDateTime());
		long item_ids = db.insert(TABLE_RECEIPTS_NUMBER, null, values);
		Log.e("items receipt  added  ids ....", "items receipt added  ids ...."+item_ids);
		
		return item_ids;
	}
	
	/**
	 * getting all todos
	 * */
	public List<Order> getAllItems() {
		List<Order> items = new ArrayList<Order>();
		String selectQuery = "SELECT  * FROM " + TABLE_ORDER;//+" where "+ KEY_STATUS_ID+" = '1'";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				Order item = new Order();
				
				item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				item.setTotal_amount((c.getString(c.getColumnIndex(KEY_TOTALAMOUNT))));
				item.setReceipt_number_id((c.getString(c.getColumnIndex(KEY_RECEIPT_NUMBER))));
				
				item.setTotal_amount_sold((c.getString(c.getColumnIndex(KEY_TOTALAMOUNT_SOLD))));
				item.setUser_id((c.getString(c.getColumnIndex(KEY_USER_ID))));
				item.setStatus_id((c.getString(c.getColumnIndex(KEY_STATUS_ID))));
				item.setDiscount_value((c.getString(c.getColumnIndex(KEY_DISCOUNT_VALUE))));
				item.setDiscount_type_id((c.getString(c.getColumnIndex(KEY_DISCOUNT_TYPE_ID))));
				item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));
				
				// adding to todo list
				items.add(item);
			} while (c.moveToNext());
		}

		return items;
	}
	
	public String get_Sale_Order_id() {
		String selectQuery = "SELECT  * FROM " + TABLE_ORDER+" where "+ KEY_STATUS_ID+" = '3'"+" and "
							+KEY_IS_SYNCED +" = '0'";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		String id = "";
		if (c.moveToFirst()) {
			do {
				id=id+""+(c.getString((c.getColumnIndex(KEY_ID))));
				
			} while (c.moveToNext());
		}

		return id;
	}
	
	// Updating single contact
	public int updateOrder(Order items) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_TOTALAMOUNT, items.getTotal_amount());
		values.put(KEY_TOTALAMOUNT_SOLD, items.getTotal_amount_sold());
		values.put(KEY_STATUS_ID, items.getStatus_id());
		values.put(KEY_RECEIPT_NUMBER, items.getReceipt_number_id());
		values.put(KEY_CREATED, items.getCreated_at());
		values.put(KEY_USER_ID, Global.user_id);
		
		values.put(KEY_DISCOUNT_VALUE, items.getDiscount_value());
		values.put(KEY_DISCOUNT_TYPE_ID, items.getDiscount_type_id());
		
		System.out.println(items.getId());
		
	    // updating row
	    return db.update(TABLE_ORDER, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(items.getId()) });
	
	}
	
	public long create_order_payments(String order_id, String payment_type_id, String amount) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ORDER_ID, order_id);
		values.put(KEY_PAYMENT_TYPE_ID, payment_type_id);
		values.put(KEY_AMOUNT, amount);
		
		return db.insert(TABLE_ORDER_PAYMENTS, null, values);
		
	}
	
	public void create_payment_types() {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, "1");
		values.put(KEY_TITLE, "cash");
		long item_ids = db.insert(TABLE_PAYMENTS_TYPE, null, values);
		
		values = new ContentValues();
		values.put(KEY_ID, "2");
		values.put(KEY_TITLE, "card");
		item_ids = db.insert(TABLE_PAYMENTS_TYPE, null, values);
		
		values = new ContentValues();
		values.put(KEY_ID, "3");
		values.put(KEY_TITLE, "voucher");
		item_ids = db.insert(TABLE_PAYMENTS_TYPE, null, values);
		
		values = new ContentValues();
		values.put(KEY_ID, "4");
		values.put(KEY_TITLE, "return");
		item_ids = db.insert(TABLE_PAYMENTS_TYPE, null, values);
	
	}
	
	public int  is_synced_order(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_IS_SYNCED, "1");
		
		return db.update(TABLE_ORDER, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });
	
	}
	
	public int  is_synced_order_payment(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_IS_SYNCED, "1");
		
		//return db.update(TABLE_ORDER_PAYMENTS, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });
		return db.update(TABLE_ORDER_PAYMENTS, values, KEY_ID + " = ?", new String[] { String.valueOf(id) });
	
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
}
