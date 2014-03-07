package pos.main.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import pos.main.model.Items;
import pos.main.model.Transaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Transaction_Tables extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "POS";

	public Transaction_Tables(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		System.out.println(CREATE_TABLE_TRANSACTION);
		db.execSQL(CREATE_TABLE_TRANSACTION);
		db.execSQL(CREATE_TABLE_TRANSACTION_TYPE);
		db.execSQL(CREATE_TABLE_TRANSACTION_DISCOUNT);
		db.execSQL(CREATE_TABLE_STATUS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION_TYPES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION_DISCOUNT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
		// create new tables
		onCreate(db);
	}
	
	/*
	 * TABLES NAME
	 */
	private static final String TABLE_TRANSACTION = "transactions";
	private static final String TABLE_TRANSACTION_TYPES = "transaction_types";
	private static final String TABLE_TRANSACTION_DISCOUNT = "discount_types";
	private static final String TABLE_STATUS = "status";
	
	/*
	 *  table FEILDS
	 */
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	
	private static final String KEY_TRANSACTION_TYPE_ID = "transaction_type_id";
	private static final String KEY_ITEM_ID = "item_id";
	private static final String KEY_ITEM_CMS_ID = "item_cms_id";
	private static final String KEY_QTY = "quantity";
	private static final String KEY_RECEIPT_ID = "receipt_number_id";
	
	private static final String KEY_ORDER_NUMBER_ID = "order_number_id";
	
	private static final String KEY_STATUS_ID = "status_id";
	private static final String KEY_CREATED = "created_at";
	private static final String KEY_UPDATED = "updated_at";
	
	private static final String KEY_PARENT_TYPE = "parent_type";
	private static final String KEY_PARENT_TYPE_ID = "parent_type_id";
	
	private static final String KEY_SOLD_PRICE = "sold_price";
	private static final String KEY_DISCOUNT_VALUE = "discount_value";
	private static final String KEY_DISCOUNT_TYPE_ID = "discount_type_id";
	
	private static final String KEY_DECS1 = "description1";
	private static final String KEY_DECS2 = "description2";
	private static final String KEY_DECS3 = "description3";
	
	private static final String KEY_SUPPLIER_NO = "supplier_number";
	private static final String KEY_SUPPLIER_ITEM_NO = "supplier_item_number";
	private static final String KEY_BARCODE = "ean";// barcode
	
	private static final String KEY_GROUP = "group_id";
	private static final String KEY_COLOR = "color";
	private static final String KEY_SIZE = "size";
	
	private static final String KEY_BUY_PRICE = "buying_price";
	private static final String KEY_SELL_PRICE = "selling_price";
	private static final String KEY_TAX = "taxation_code";
	
	private static final String KEY_IS_SYNCED = "isSynced";
	private static final String KEY_USER_ID = "user_id";
	//private static final String KEY_LARGE_PIC = "large_pic";
	
	
	
	// Tag table create statement
		private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE " + TABLE_TRANSACTION
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " +
				KEY_TRANSACTION_TYPE_ID + " INTEGER , "+ 
				KEY_QTY + " INTEGER , "+ 
				KEY_ITEM_ID + " INTEGER , "+ 
				KEY_ITEM_CMS_ID + " INTEGER , "+
				KEY_RECEIPT_ID + " TEXT , "+
				KEY_ORDER_NUMBER_ID + " INTEGER, "+ 
				KEY_STATUS_ID + " INTEGER , "+ 
				
				KEY_PARENT_TYPE + " TEXT , "+ 
				KEY_PARENT_TYPE_ID + " INTEGER , "+
				KEY_SOLD_PRICE + " REAL DEFAULT '0', "+
				
				KEY_DISCOUNT_VALUE + " REAL , "+ 
				KEY_DISCOUNT_TYPE_ID + " INTEGER , "+
				
				KEY_DECS1 + " TEXT, "+
				KEY_DECS2 +" TEXT , "+ 
				KEY_DECS3 +" TEXT , "+
				
				KEY_SUPPLIER_NO + " TEXT  , "+ 
				KEY_SUPPLIER_ITEM_NO + " TEXT , "+ 
				
				KEY_BARCODE + " TEXT , "+ 
				
				KEY_COLOR + " TEXT , "+ 
				KEY_SIZE + " TEXT , "+ 
				KEY_GROUP + " TEXT , "+
				KEY_USER_ID+ " TEXT , "+
				KEY_BUY_PRICE + " REAL DEFAULT '0', "+ 
				KEY_SELL_PRICE + " REAL DEFAULT '0', "+
				
				KEY_TAX + " INTEGER , "+ 
				
				KEY_CREATED + " DATETIME, "+ 
				KEY_UPDATED + " DATETIME , "+
				
				KEY_IS_SYNCED  + " INTEGER DEFAULT '0')";

		private static final String CREATE_TABLE_TRANSACTION_TYPE = "CREATE TABLE " + TABLE_TRANSACTION_TYPES
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";

		private static final String CREATE_TABLE_TRANSACTION_DISCOUNT  = "CREATE TABLE " + TABLE_TRANSACTION_DISCOUNT
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";

		private static final String CREATE_TABLE_STATUS  = "CREATE TABLE " + TABLE_STATUS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";
		
		public long createItems(Transaction items){
			//, long[] tag_ids) {
			
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put(KEY_TRANSACTION_TYPE_ID, items.getTransaction_type_id());
			values.put(KEY_QTY, items.getQuantity());
			values.put(KEY_ITEM_ID, items.getItem_id());
			values.put(KEY_ITEM_CMS_ID, items.getItem_cms_id());
			values.put(KEY_USER_ID, items.getUser_id());
			values.put(KEY_RECEIPT_ID, items.getReceipt_number_id());
			values.put(KEY_ORDER_NUMBER_ID, items.getOrder_number_id());
			values.put(KEY_STATUS_ID, items.getStatus_id());
			values.put(KEY_PARENT_TYPE, items.getParent_type());
			values.put(KEY_PARENT_TYPE_ID, items.getParent_type_id());
			values.put(KEY_SOLD_PRICE, items.getSold_price());
			values.put(KEY_DISCOUNT_VALUE, items.getDiscount_value());
			values.put(KEY_DISCOUNT_TYPE_ID, items.getDiscount_type_id());
			
			values.put(KEY_DECS1, items.getDecs1());
			values.put(KEY_DECS2, items.getDecs2());
			values.put(KEY_DECS3, items.getDecs3());
			values.put(KEY_SUPPLIER_NO, items.getSupplier_number());
			values.put(KEY_SUPPLIER_ITEM_NO, items.getSupplier_item_number());
			values.put(KEY_BARCODE, items.getBarcode());
			values.put(KEY_COLOR, items.getColor_id());
			values.put(KEY_SIZE, items.getSize_id());
			values.put(KEY_GROUP, items.getGroup_id());
			values.put(KEY_BUY_PRICE, items.getBuying_price());
			values.put(KEY_SELL_PRICE, items.getSelling_price());
			values.put(KEY_TAX, items.getTaxation_code());
			values.put(KEY_UPDATED, items.getCreated_at());
			values.put(KEY_CREATED, items.getUpdated_at());
			values.put(KEY_IS_SYNCED, "0");

			// insert row
			long item_ids = db.insert(TABLE_TRANSACTION, null, values);
			Log.e("items transaction added  ids ....", "items transaction added  ids ...."+item_ids);
			
			//System.out.println("items transaction added  ids ...."+item_ids);
			// insert tag_ids
			//for (long tag_id : tag_ids) {
			//	createTodoTag(todo_id, tag_id);
			//}

			return item_ids;
		}
		
		
		
		public void create_SaleTransaction(ContentValues values) {
			
			SQLiteDatabase db = this.getWritableDatabase();
			// insert row
			long item_ids = db.insert(TABLE_TRANSACTION, null, values);
			Log.e("items transaction added  ids ....", "items transaction added  ids ...."+item_ids);
			
		}
		
		
		
		/**
		 * getting all todos
		 * */
		public List<Transaction> getAllItems() {
			List<Transaction> items = new ArrayList<Transaction>();
			String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION;

			//Log.e("Tag..", selectQuery);

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					Transaction item = new Transaction();
					
					item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
					
					item.setTransaction_type_id((c.getString(c.getColumnIndex(KEY_TRANSACTION_TYPE_ID))));
					item.setQuantity((c.getString(c.getColumnIndex(KEY_QTY))));
					
					item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
					item.setItem_cms_id((c.getString(c.getColumnIndex(KEY_ITEM_CMS_ID))));
					
					item.setReceipt_number_id((c.getString(c.getColumnIndex(KEY_RECEIPT_ID))));
					item.setOrder_number_id((c.getString(c.getColumnIndex(KEY_ORDER_NUMBER_ID))));
					item.setStatus_id((c.getString(c.getColumnIndex(KEY_STATUS_ID))));
					
					item.setParent_type((c.getString(c.getColumnIndex(KEY_PARENT_TYPE))));
					item.setParent_type_id((c.getString(c.getColumnIndex(KEY_PARENT_TYPE_ID))));
					
					item.setSold_price((c.getString(c.getColumnIndex(KEY_SOLD_PRICE))));
					
					item.setDiscount_value((c.getString(c.getColumnIndex(KEY_DISCOUNT_VALUE))));
					item.setDiscount_type_id((c.getString(c.getColumnIndex(KEY_DISCOUNT_TYPE_ID))));
					
					
					item.setDecs1((c.getString(c.getColumnIndex(KEY_DECS1))));
					item.setDecs2((c.getString(c.getColumnIndex(KEY_DECS2))));
					item.setDecs3((c.getString(c.getColumnIndex(KEY_DECS3))));
					
					item.setSupplier_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_NO))));
					item.setSupplier_item_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_ITEM_NO))));
					item.setBarcode((c.getString(c.getColumnIndex(KEY_BARCODE))));
					item.setBuying_price((c.getString(c.getColumnIndex(KEY_BUY_PRICE))));
					item.setSelling_price((c.getString(c.getColumnIndex(KEY_SELL_PRICE))));
					item.setTaxation_code((c.getString(c.getColumnIndex(KEY_TAX))));
					
					item.setSize_id((c.getString(c.getColumnIndex(KEY_SIZE))));
					item.setColor_id((c.getString(c.getColumnIndex(KEY_COLOR))));
					item.setGroup_id((c.getString(c.getColumnIndex(KEY_GROUP))));
					item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));
					item.setUpdated_at((c.getString(c.getColumnIndex(KEY_UPDATED))));

					// adding to todo list
					items.add(item);
				} while (c.moveToNext());
			}

			return items;
		}
		
		
		// Updating single contact
		public int updateItems(Transaction items) {
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_TRANSACTION_TYPE_ID, items.getTransaction_type_id());
			values.put(KEY_QTY, items.getQuantity());
			values.put(KEY_ITEM_ID, items.getItem_id());
			values.put(KEY_ITEM_CMS_ID, items.getItem_cms_id());
			values.put(KEY_RECEIPT_ID, items.getReceipt_number_id());
			values.put(KEY_ORDER_NUMBER_ID, items.getOrder_number_id());
			values.put(KEY_STATUS_ID, items.getStatus_id());
			values.put(KEY_PARENT_TYPE, items.getParent_type());
			values.put(KEY_PARENT_TYPE_ID, items.getParent_type_id());
			values.put(KEY_SOLD_PRICE, items.getSold_price());
			values.put(KEY_DISCOUNT_VALUE, items.getDiscount_value());
			values.put(KEY_DISCOUNT_TYPE_ID, items.getDiscount_type_id());
			
			values.put(KEY_DECS1, items.getDecs1());
			values.put(KEY_DECS2, items.getDecs2());
			values.put(KEY_DECS3, items.getDecs3());
			values.put(KEY_SUPPLIER_NO, items.getSupplier_number());
			values.put(KEY_SUPPLIER_ITEM_NO, items.getSupplier_item_number());
			values.put(KEY_BARCODE, items.getBarcode());
			values.put(KEY_COLOR, items.getColor_id());
			values.put(KEY_SIZE, items.getSize_id());
			values.put(KEY_GROUP, items.getGroup_id());
			values.put(KEY_BUY_PRICE, items.getBuying_price());
			values.put(KEY_SELL_PRICE, items.getSelling_price());
			values.put(KEY_TAX, items.getTaxation_code());
			values.put(KEY_UPDATED, getDateTime());
			values.put(KEY_CREATED, getDateTime());
			
			System.out.println(items.getId());
			
		    // updating row
		    return db.update(TABLE_TRANSACTION, values, KEY_ID + " = ?",
		            new String[] { String.valueOf(items.getId()) });
		
		}
		
		/*
		 * get single todo
		 */
		public String getItemId(String barcode) {
			SQLiteDatabase db = this.getReadableDatabase();
			
			String selectQuery = "SELECT  "+KEY_ID+" FROM " + TABLE_TRANSACTION + " WHERE "+ KEY_ID + " = '" 
			+ barcode+"'";
			Log.e("login id return..", selectQuery);

			Cursor c = db.rawQuery(selectQuery, null);
			String item_id="";
			if (c != null){
				c.moveToFirst();
				if(c.moveToFirst()){
					item_id=""+c.getInt((c.getColumnIndex(KEY_ID)));
				}
			}
						//	
		//	Items td = new Items();
		//	td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		//	td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
		//	td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

			return item_id;
		}
		
		/*
		 * insert discount values
		 */
		
		public int insertQuantity(String barcode, String qty) {
			SQLiteDatabase db = this.getReadableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_QTY, qty);
			//values.put(KEY_ITEM_ID, items.getItem_id());
			
		    // updating row
		    int i= db.update(TABLE_TRANSACTION, values, KEY_ID + " = ?",
		            new String[] { String.valueOf(barcode) });
			
		    Log.e("insert qty..", ""+i);
			return i;
		}
		
		/*
		 * insert discount values
		 */
		
		public int insert_Discount(String barcode,  String discount_value , String discount_type) {
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_DISCOUNT_VALUE, discount_value);
			values.put(KEY_DISCOUNT_TYPE_ID, discount_type);
			
			System.out.println(barcode);
			
		    // updating row
		    return db.update(TABLE_TRANSACTION, values, KEY_ID + " = ?",
		            new String[] { String.valueOf(barcode) });
		
		}
		
		public int isSyncedTrans(String id) {
			int id1=0;
			SQLiteDatabase db = this.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put(KEY_IS_SYNCED, "1");
				id1= db.update(TABLE_TRANSACTION, values, KEY_ID + " in ("+id+")" ,  null);
				
			return id1;
		}
		
		
		public void transaction_type_create() {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put(KEY_ID, "1");
			values.put(KEY_TITLE, "inward");
			long item_ids = db.insert(TABLE_TRANSACTION_TYPES, null, values);
			
			values = new ContentValues();
			
			values.put(KEY_ID, "2");
			values.put(KEY_TITLE, "outward");
			item_ids = db.insert(TABLE_TRANSACTION_TYPES, null, values);
		}
		
		
		
		public int insertReceivedItems(Transaction items) {
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_TRANSACTION_TYPE_ID, items.getTransaction_type_id());
			values.put(KEY_QTY, items.getQuantity());
			values.put(KEY_ITEM_ID, items.getItem_id());
			values.put(KEY_RECEIPT_ID, items.getReceipt_number_id());
			values.put(KEY_ORDER_NUMBER_ID, items.getOrder_number_id());
			values.put(KEY_STATUS_ID, items.getStatus_id());
			values.put(KEY_PARENT_TYPE, items.getParent_type());
			values.put(KEY_PARENT_TYPE_ID, items.getParent_type_id());
			values.put(KEY_SOLD_PRICE, items.getSold_price());
			values.put(KEY_DISCOUNT_VALUE, items.getDiscount_value());
			values.put(KEY_DISCOUNT_TYPE_ID, items.getDiscount_type_id());
			
			values.put(KEY_DECS1, items.getDecs1());
			values.put(KEY_DECS2, items.getDecs2());
			values.put(KEY_DECS3, items.getDecs3());
			values.put(KEY_SUPPLIER_NO, items.getSupplier_number());
			values.put(KEY_SUPPLIER_ITEM_NO, items.getSupplier_item_number());
			values.put(KEY_BARCODE, items.getBarcode());
			values.put(KEY_COLOR, items.getColor_id());
			values.put(KEY_SIZE, items.getSize_id());
			values.put(KEY_GROUP, items.getGroup_id());
			values.put(KEY_BUY_PRICE, items.getBuying_price());
			values.put(KEY_SELL_PRICE, items.getSelling_price());
			values.put(KEY_TAX, items.getTaxation_code());
			values.put(KEY_UPDATED, getDateTime());
			values.put(KEY_CREATED, getDateTime());
			values.put(KEY_DISCOUNT_VALUE, "");
			values.put(KEY_DISCOUNT_TYPE_ID, "");
			
			//System.out.println(barcode);
			
		    // updating row
		    return db.update(TABLE_TRANSACTION, values, KEY_ID + " = ?",
		            new String[] { String.valueOf("") });
		}
		
		public List<Transaction> get_Sale_trans( String order_id) {
			JSONObject trabs= null;
			
			String selectQuery = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE "+ KEY_ORDER_NUMBER_ID + " = '" + order_id+"'";
			Log.e("login id return..", selectQuery);
			
			List<Transaction> items = new ArrayList<Transaction>();
			SQLiteDatabase db = this.getReadableDatabase();
			
			Cursor c = db.rawQuery(selectQuery, null);
			if (c.moveToFirst()) {
				do {
					Transaction item = new Transaction();
					
					item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
					
					item.setTransaction_type_id((c.getString(c.getColumnIndex(KEY_TRANSACTION_TYPE_ID))));
					item.setQuantity((c.getString(c.getColumnIndex(KEY_QTY))));
					
					item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
					item.setItem_cms_id((c.getString(c.getColumnIndex(KEY_ITEM_CMS_ID))));
					
					item.setReceipt_number_id((c.getString(c.getColumnIndex(KEY_RECEIPT_ID))));
					item.setOrder_number_id((c.getString(c.getColumnIndex(KEY_ORDER_NUMBER_ID))));
					item.setStatus_id((c.getString(c.getColumnIndex(KEY_STATUS_ID))));
					
					item.setParent_type((c.getString(c.getColumnIndex(KEY_PARENT_TYPE))));
					item.setParent_type_id((c.getString(c.getColumnIndex(KEY_PARENT_TYPE_ID))));
					
					item.setSold_price((c.getString(c.getColumnIndex(KEY_SOLD_PRICE))));
					
					item.setDiscount_value((c.getString(c.getColumnIndex(KEY_DISCOUNT_VALUE))));
					item.setDiscount_type_id((c.getString(c.getColumnIndex(KEY_DISCOUNT_TYPE_ID))));
					
					
					item.setDecs1((c.getString(c.getColumnIndex(KEY_DECS1))));
					item.setDecs2((c.getString(c.getColumnIndex(KEY_DECS2))));
					item.setDecs3((c.getString(c.getColumnIndex(KEY_DECS3))));
					
					item.setSupplier_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_NO))));
					item.setSupplier_item_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_ITEM_NO))));
					item.setBarcode((c.getString(c.getColumnIndex(KEY_BARCODE))));
					item.setBuying_price((c.getString(c.getColumnIndex(KEY_BUY_PRICE))));
					item.setSelling_price((c.getString(c.getColumnIndex(KEY_SELL_PRICE))));
					item.setTaxation_code((c.getString(c.getColumnIndex(KEY_TAX))));
					
					item.setSize_id((c.getString(c.getColumnIndex(KEY_SIZE))));
					item.setColor_id((c.getString(c.getColumnIndex(KEY_COLOR))));
					item.setGroup_id((c.getString(c.getColumnIndex(KEY_GROUP))));
					item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));
					item.setUpdated_at((c.getString(c.getColumnIndex(KEY_UPDATED))));

					// adding to todo list
					items.add(item);
				} while (c.moveToNext());
			}
			return items;
		}
		
		
		
		
	/**
     * get datetime
     * */
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
