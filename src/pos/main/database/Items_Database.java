package pos.main.database;

import pos.main.model.Items;
import pos.main.model.Order;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Items_Database extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "POS";

	public Items_Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		System.out.println(CREATE_TABLE_ITEMS);
		db.execSQL(CREATE_TABLE_ITEMS);
		db.execSQL(CREATE_TABLE_COLOR);
		db.execSQL(CREATE_TABLE_GROUP);
		db.execSQL(CREATE_TABLE_SIZE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS_SIZE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS_GROUP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS_COLORS);
		// create new tables
		onCreate(db);
	}
	
	/*
	 * TABLES NAME
	 */
	private static final String TABLE_ITEMS = "items";
	private static final String TABLE_ITEMS_SIZE = "sizes";
	private static final String TABLE_ITEMS_GROUP = "groups";
	private static final String TABLE_ITEMS_COLORS = "colors";
	//private static final String TABLE_LOGIN = "users";
	//private static final String TABLE_USER_TYPE = "user_type";
	
	/*
	 *  table FEILDS
	 */
	private static final String KEY_ID = "id";
	private static final String KEY_ITEM_ID = "item_id";
	private static final String KEY_DECS1 = "description1";
	private static final String KEY_DECS2 = "description2";
	private static final String KEY_DECS3 = "description3";
	private static final String KEY_SUPPLIER_NO = "supplier_number";
	private static final String KEY_SUPPLIER_ITEM_NO = "supplier_item_number";
	private static final String KEY_BARCODE = "ean";// barcode
	private static final String KEY_BUY_PRICE = "buying_price";
	private static final String KEY_SELL_PRICE = "selling_price";
	private static final String KEY_TAX = "taxation_code";
	private static final String KEY_AVAILABLE_QTY = "available_quantity";
	private static final String KEY_RETURN_QTY = "return_quantity";
	private static final String KEY_TOTAL_QTY = "total_quantity";
	
	
	
	private static final String KEY_SMALL_PIC = "small_pic";
	private static final String KEY_LARGE_PIC = "large_pic";
	private static final String KEY_CREATED = "created_at";
	private static final String KEY_UPDATED = "updated_at";
	
	private static final String KEY_TITLE = "title";
	private static final String KEY_GROUP_ID = "group_id";
	private static final String KEY_COLOR_ID = "color";
	//private static final String KEY_GROUP = "group";
	private static final String KEY_SIZE_ID = "size";
	
	
	// Tag table create statement
		private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " +
				KEY_ITEM_ID + " TEXT, "+
				KEY_DECS1 + " TEXT, "+
				KEY_DECS2 +" TEXT , "+ 
				KEY_DECS3 +" TEXT , "+
				KEY_SUPPLIER_NO + " TEXT  , "+ 
				KEY_SUPPLIER_ITEM_NO + " TEXT , "+ 
				KEY_BARCODE + " TEXT , "+ 
				
				KEY_BUY_PRICE + " REAL , "+ 
				KEY_SELL_PRICE + " REAL , "+
				KEY_TAX + " INTEGER , "+ 
				KEY_AVAILABLE_QTY + " INTEGER DEFAULT '0' , "+ 
				
				KEY_RETURN_QTY + " INTEGER DEFAULT '0' , "+
				KEY_TOTAL_QTY + " INTEGER DEFAULT '0' , "+
				KEY_SMALL_PIC + " TEXT , "+ 
				KEY_LARGE_PIC + " TEXT , "+ 
				KEY_CREATED + " DATETIME, "+ 
				
				KEY_COLOR_ID + " TEXT , "+ 
				KEY_SIZE_ID + " TEXT , "+ 
				KEY_GROUP_ID + " TEXT , "+
				
				KEY_UPDATED + " DATETIME)";

		private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_ITEMS_GROUP
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";

		private static final String CREATE_TABLE_COLOR  = "CREATE TABLE " + TABLE_ITEMS_COLORS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";

		private static final String CREATE_TABLE_SIZE  = "CREATE TABLE " + TABLE_ITEMS_SIZE
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";

		public long createItems(Items items){
			//, long[] tag_ids) {
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_ID, items.getId());
			values.put(KEY_ITEM_ID, items.getItem_id());
			
			values.put(KEY_DECS1, items.getDecs1());
			values.put(KEY_DECS2, items.getDecs2());
			values.put(KEY_DECS3, items.getDecs3());
			
			values.put(KEY_SUPPLIER_NO, items.getSupplier_number());
			values.put(KEY_SUPPLIER_ITEM_NO, items.getSupplier_item_number());
			
			values.put(KEY_BARCODE, items.getBarcode());
			values.put(KEY_BUY_PRICE, items.getBuying_price());
			values.put(KEY_SELL_PRICE, items.getSelling_price());
			values.put(KEY_TAX, items.getTaxation_code());
		//	values.put(KEY_AVAILABLE_QTY, "0");
		//	values.put(KEY_RETURN_QTY, "0");
		///	values.put(KEY_TOTAL_QTY, "0");
			values.put(KEY_SMALL_PIC, items.getSmall_pic());
			values.put(KEY_LARGE_PIC, items.getLarge_pic());
			
			values.put(KEY_GROUP_ID, items.getGroup_id());
			values.put(KEY_COLOR_ID, items.getColor_id());
			values.put(KEY_SIZE_ID, items.getSize_id());
			values.put(KEY_UPDATED, getDateTime());
			values.put(KEY_CREATED, items.getCreated_at());

			// insert row
			long item_ids = db.insert(TABLE_ITEMS, null, values);
			System.out.println("items added  ids ...."+item_ids);
			// insert tag_ids
			//for (long tag_id : tag_ids) {
			//	createTodoTag(todo_id, tag_id);
			//}

			return item_ids;
		}
		
		
		/**
		 * getting all todos
		 * */
		public List<Items> getAllItems(String limit, String endlimit) {
			List<Items> items = new ArrayList<Items>();
			String selectQuery = "SELECT * FROM " + TABLE_ITEMS+" LIMIT "+ limit+",  "+endlimit;

			Log.e("Tag..", selectQuery);

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					Items item = new Items();
					item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
					item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
					item.setDecs1((c.getString(c.getColumnIndex(KEY_DECS1))));
					item.setDecs2((c.getString(c.getColumnIndex(KEY_DECS2))));
					item.setDecs3((c.getString(c.getColumnIndex(KEY_DECS3))));
					
					item.setSupplier_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_NO))));
					item.setSupplier_item_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_ITEM_NO))));
					item.setBarcode((c.getString(c.getColumnIndex(KEY_BARCODE))));
					item.setBuying_price((c.getString(c.getColumnIndex(KEY_BUY_PRICE))));
					item.setSelling_price((c.getString(c.getColumnIndex(KEY_SELL_PRICE))));
					item.setLarge_pic((c.getString(c.getColumnIndex(KEY_LARGE_PIC))));
					item.setSmall_pic((c.getString(c.getColumnIndex(KEY_SMALL_PIC))));
					item.setTaxation_code((c.getString(c.getColumnIndex(KEY_TAX))));
					
					item.setAvailable_quantity((c.getString(c.getColumnIndex(KEY_AVAILABLE_QTY))));
					item.setReturn_qty((c.getString(c.getColumnIndex(KEY_RETURN_QTY))));
					item.setTotal_qty((c.getString(c.getColumnIndex(KEY_TOTAL_QTY))));
					
					item.setUpdated_at((c.getString(c.getColumnIndex(KEY_UPDATED))));
					
					item.setSize_id((c.getString(c.getColumnIndex(KEY_SIZE_ID))));
					item.setColor_id((c.getString(c.getColumnIndex(KEY_COLOR_ID))));
					item.setGroup_id((c.getString(c.getColumnIndex(KEY_GROUP_ID))));
					item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));

					// adding to todo list
					items.add(item);
				} while (c.moveToNext());
			}

			return items;
		}
		
		// Updating single contact
		public int updateItems(Items items) {
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_ITEM_ID, items.getItem_id());
			
			values.put(KEY_DECS1, items.getDecs1());
			values.put(KEY_DECS2, items.getDecs2());
			values.put(KEY_DECS3, items.getDecs3());
			
			values.put(KEY_SUPPLIER_NO, items.getSupplier_number());
			values.put(KEY_SUPPLIER_ITEM_NO, items.getSupplier_item_number());
			
			values.put(KEY_BARCODE, items.getBarcode());
			
			values.put(KEY_BUY_PRICE, items.getBuying_price());
			values.put(KEY_SELL_PRICE, items.getSelling_price());
			
			values.put(KEY_TAX, items.getTaxation_code());
			
			//values.put(KEY_AVAILABLE_QTY, items.getAvailable_quantity());
			
			values.put(KEY_SMALL_PIC, items.getSmall_pic());
			values.put(KEY_LARGE_PIC, items.getLarge_pic());
			
			values.put(KEY_GROUP_ID, items.getGroup_id());
			values.put(KEY_COLOR_ID, items.getColor_id());
			values.put(KEY_SIZE_ID, items.getSize_id());
			
			values.put(KEY_UPDATED, getDateTime());
			values.put(KEY_CREATED, items.getCreated_at());
			System.out.println(items.getId());
			
		    // updating row
		    return db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
		            new String[] { String.valueOf(items.getId()) });
		}

		/*
		 * get single todo
		 */
		public int getItemId(String barcode) {
			SQLiteDatabase db = this.getReadableDatabase();
			String selectQuery = "SELECT  "+KEY_ITEM_ID+", "+KEY_ID +" FROM " + TABLE_ITEMS + " WHERE "+ KEY_ITEM_ID + " = '"+ barcode+"'";
			Log.e("login id return..", selectQuery);

			Cursor c = db.rawQuery(selectQuery, null);
			String item_id="";
			int id=0; 
			
			try {
				if(c != null){
					c.moveToFirst();
					if(c.moveToFirst()){
						
						item_id=""+c.getString((c.getColumnIndex(KEY_ITEM_ID)));
						System.out.println(item_id+".....item inserted id is...:"+ barcode);
						
						if(item_id.equals(barcode)){
							id=c.getInt((c.getColumnIndex(KEY_ID)));
							System.out.println(id+".....item row id is...:");
						}
					}
				}
			} finally { 
				c.close();}
			
			
			return id;
		}
		
		public Items getItemRow(String barcode) {
			Items item = new Items();
			SQLiteDatabase db = this.getReadableDatabase();
			
			String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE "+ KEY_ITEM_ID + " = '"+ barcode+"'";
			
			Log.e("login id return..", selectQuery);

			Cursor c = db.rawQuery(selectQuery, null);
			//String item_id="";
			
			if (c != null){
				c.moveToFirst();
				
				if(c.moveToFirst()){
					
					item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
					item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
					
					item.setDecs1((c.getString(c.getColumnIndex(KEY_DECS1))));
					item.setDecs2((c.getString(c.getColumnIndex(KEY_DECS2))));
					item.setDecs3((c.getString(c.getColumnIndex(KEY_DECS3))));
					
					item.setSupplier_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_NO))));
					item.setSupplier_item_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_ITEM_NO))));
					
					item.setBarcode((c.getString(c.getColumnIndex(KEY_BARCODE))));
					
					item.setBuying_price((c.getString(c.getColumnIndex(KEY_BUY_PRICE))));
					item.setSelling_price((c.getString(c.getColumnIndex(KEY_SELL_PRICE))));
					
					item.setLarge_pic((c.getString(c.getColumnIndex(KEY_LARGE_PIC))));
					item.setSmall_pic((c.getString(c.getColumnIndex(KEY_SMALL_PIC))));
					
					item.setTaxation_code((c.getString(c.getColumnIndex(KEY_TAX))));
					
					item.setTotal_qty((c.getString(c.getColumnIndex(KEY_TOTAL_QTY))));
					item.setReturn_qty((c.getString(c.getColumnIndex(KEY_RETURN_QTY))));
					item.setAvailable_quantity((c.getString(c.getColumnIndex(KEY_AVAILABLE_QTY))));
					
					item.setUpdated_at((c.getString(c.getColumnIndex(KEY_UPDATED))));
					item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));
					
					item.setSize_id((c.getString(c.getColumnIndex(KEY_SIZE_ID))));
					item.setColor_id((c.getString(c.getColumnIndex(KEY_COLOR_ID))));
					item.setGroup_id((c.getString(c.getColumnIndex(KEY_GROUP_ID))));
					
					Log.e("login id return..", ""+c.getInt(c.getColumnIndex(KEY_ID))+""+c.getString((c.getColumnIndex(KEY_AVAILABLE_QTY))));
				}
			}
			return item;
		}
		
		
		public List<Items> getItems(String barcode, String decs) {
			barcode= barcode+"%";
			List<Items> items = new ArrayList<Items>();
			String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE "
			+ KEY_ITEM_ID + " LIKE '"+ barcode+"' or "
			+KEY_DECS1  +   " LIKE '"+ decs+"'   LIMIT 25";

			Log.e("Tag..", selectQuery);

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					Items item = new Items();
					item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
					item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
					item.setDecs1((c.getString(c.getColumnIndex(KEY_DECS1))));
					item.setDecs2((c.getString(c.getColumnIndex(KEY_DECS2))));
					item.setDecs3((c.getString(c.getColumnIndex(KEY_DECS3))));
					
					item.setSupplier_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_NO))));
					item.setSupplier_item_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_ITEM_NO))));
					item.setBarcode((c.getString(c.getColumnIndex(KEY_BARCODE))));
					item.setBuying_price((c.getString(c.getColumnIndex(KEY_BUY_PRICE))));
					item.setSelling_price((c.getString(c.getColumnIndex(KEY_SELL_PRICE))));
					item.setLarge_pic((c.getString(c.getColumnIndex(KEY_LARGE_PIC))));
					item.setSmall_pic((c.getString(c.getColumnIndex(KEY_SMALL_PIC))));
					item.setTaxation_code((c.getString(c.getColumnIndex(KEY_TAX))));
					
					item.setAvailable_quantity((c.getString(c.getColumnIndex(KEY_AVAILABLE_QTY))));
					item.setTotal_qty((c.getString(c.getColumnIndex(KEY_TOTAL_QTY))));
					item.setReturn_qty((c.getString(c.getColumnIndex(KEY_RETURN_QTY))));
					
					item.setUpdated_at((c.getString(c.getColumnIndex(KEY_UPDATED))));
					
					
					item.setSize_id((c.getString(c.getColumnIndex(KEY_SIZE_ID))));
					item.setColor_id((c.getString(c.getColumnIndex(KEY_COLOR_ID))));
					item.setGroup_id((c.getString(c.getColumnIndex(KEY_GROUP_ID))));
					item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));

					// adding to todo list
					items.add(item);
				} while (c.moveToNext());
			}

			return items;
		}
		
		public List<Items> getItemsSale(String barcode) {
			barcode= barcode+"%";
			List<Items> items = new ArrayList<Items>();
			String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE  "+KEY_AVAILABLE_QTY +" > 0 and "+ KEY_ITEM_ID + " LIKE '"+
			barcode+"' LIMIT 25";

			Log.e("Tag..", selectQuery);

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					Items item = new Items();
					item.setId(c.getInt((c.getColumnIndex(KEY_ID))));
					item.setItem_id((c.getString(c.getColumnIndex(KEY_ITEM_ID))));
					item.setDecs1((c.getString(c.getColumnIndex(KEY_DECS1))));
					item.setDecs2((c.getString(c.getColumnIndex(KEY_DECS2))));
					item.setDecs3((c.getString(c.getColumnIndex(KEY_DECS3))));
					
					item.setSupplier_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_NO))));
					item.setSupplier_item_number((c.getString(c.getColumnIndex(KEY_SUPPLIER_ITEM_NO))));
					item.setBarcode((c.getString(c.getColumnIndex(KEY_BARCODE))));
					item.setBuying_price((c.getString(c.getColumnIndex(KEY_BUY_PRICE))));
					item.setSelling_price((c.getString(c.getColumnIndex(KEY_SELL_PRICE))));
					item.setLarge_pic((c.getString(c.getColumnIndex(KEY_LARGE_PIC))));
					item.setSmall_pic((c.getString(c.getColumnIndex(KEY_SMALL_PIC))));
					item.setTaxation_code((c.getString(c.getColumnIndex(KEY_TAX))));
					
					item.setAvailable_quantity((c.getString(c.getColumnIndex(KEY_AVAILABLE_QTY))));
					item.setTotal_qty((c.getString(c.getColumnIndex(KEY_TOTAL_QTY))));
					item.setReturn_qty((c.getString(c.getColumnIndex(KEY_RETURN_QTY))));
					
					item.setUpdated_at((c.getString(c.getColumnIndex(KEY_UPDATED))));
					
					item.setSize_id((c.getString(c.getColumnIndex(KEY_SIZE_ID))));
					item.setColor_id((c.getString(c.getColumnIndex(KEY_COLOR_ID))));
					item.setGroup_id((c.getString(c.getColumnIndex(KEY_GROUP_ID))));
					item.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED)));

					// adding to todo list
					items.add(item);
				} while (c.moveToNext());
			}

			return items;
		}
		
		public String countItem() {
			
			SQLiteDatabase db = this.getReadableDatabase();
			String selectQuery = "select count(*)  as count from items "+ TABLE_ITEMS;

			Log.e("login id return..", selectQuery);

			Cursor c = db.rawQuery(selectQuery, null);
			String item_id="";
			if (c != null){
				c.moveToFirst();
				if(c.moveToFirst()){
					item_id=""+c.getInt((c.getColumnIndex("count")));
				}
			}
			
			System.out.println(item_id);
			return item_id;
		}
		
		public int updateQuantity(String available_qty, String total_qty, String id) {
			System.out.println(available_qty+"............"+ total_qty+".........."+ id);
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			
				
				values.put(KEY_AVAILABLE_QTY, ""+available_qty);
				values.put(KEY_TOTAL_QTY, ""+total_qty);
				return db.update(TABLE_ITEMS, values, KEY_ITEM_ID + " = ?",  new String[] { String.valueOf(id) });
			
				
		}
		
		
		public int updateSaleQuantity(String available_qty, String total_qty, String id) {
			
			System.out.println(available_qty+"............"+ total_qty+".........."+ id);
			SQLiteDatabase db = this.getReadableDatabase();
			String rec_avail="";
			
			String selectQuery = "SELECT  "+KEY_AVAILABLE_QTY +" FROM "+TABLE_ITEMS +" where "+ KEY_ID+" = '"+id+"'";
			Cursor c = db.rawQuery(selectQuery, null);
			
			if (c.moveToFirst()) {
				do {
					rec_avail=(c.getString((c.getColumnIndex(KEY_AVAILABLE_QTY))));
					
				} while (c.moveToNext());
			}

			if(rec_avail.length()>0)
			{
				available_qty=""+((Integer.parseInt(rec_avail)- (Integer.parseInt(total_qty))));
			}
			
			
			
			ContentValues values = new ContentValues();
			values.put(KEY_AVAILABLE_QTY, ""+available_qty);
			
			int aaa= db.update(TABLE_ITEMS, values, KEY_ID + " = ?",  new String[] { String.valueOf(id) });
		
			System.out.println("update............"+aaa);
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*
			
			
			ContentValues values = new ContentValues();
			String available_qty_rec="";
			String selectQuery = "SELECT  "+KEY_AVAILABLE_QTY + " FROM " + TABLE_ITEMS + " WHERE "+ KEY_ITEM_ID + " = '"+ id+"'";
			Log.e("login id return..", selectQuery);
			
			String s= "update "+TABLE_ITEMS+" SET "+ KEY_AVAILABLE_QTY +"  = "+ KEY_AVAILABLE_QTY+" - "+ total_qty+" where "+ KEY_ITEM_ID+"= "+id;
			Log.e("update ", s);
			db.execSQL(s);
			*/
			

			return 0;
			
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
