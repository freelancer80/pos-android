package pos.main.database;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Create_Tables extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "POS";

	public Create_Tables(Context context) {
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
		
		System.out.println("table............"+CREATE_TABLE_LOGIN);
		db.execSQL(CREATE_TABLE_LOGIN);
		db.execSQL(CREATE_TABLE_LOGIN_USERTYPE);
		db.execSQL(CREATE_TABLE_SYSTEM_CONFIG);
		
		db.execSQL(CREATE_TABLE_TRANSACTION);
		db.execSQL(CREATE_TABLE_TRANSACTION_TYPE);
		db.execSQL(CREATE_TABLE_TRANSACTION_DISCOUNT);
		db.execSQL(CREATE_TABLE_STATUS);
		
		db.execSQL(CREATE_TABLE_ORDER);
		db.execSQL(CREATE_TABLE_ORDER_PAYMENTS);
		db.execSQL(CREATE_TABLE_RECEIPT);
		db.execSQL(CREATE_TABLE_PAYMENTS_TYPE);
		
		db.execSQL(CREATE_TABLE_DELIVERYNOTE);
		
		
		System.out.println("table............"+CREATE_TABLE_DELIVERYNOTE);
		
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
	private static final String TABLE_LOGIN = "users";
	private static final String TABLE_USER_TYPE = "user_type";
	private static final String TABLE_SYSTEM_CONFIG = "system_config";
	
	/*
	 *  table FEILDS
	 */
	private static final String KEY_ID = "id";
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
	
	private static final String KEY_USER = "login";
	//private static final String KEY_USER_ID = "user_id";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_FIRSTNAME = "first_name";
	private static final String KEY_LASTNAME = "last_name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_MOBILE = "mobile";
	private static final String KEY_USERTYPE_ID = "user_type_id";
	private static final String KEY_STATUS_ID = "status_id";
	
	
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_PARENT_TYPE_ID = "parent_type_id";
	private static final String KEY_TOTALAMOUNT = "total_amount";
	private static final String KEY_TOTALAMOUNT_SOLD = "total_sold_amount";
	
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_RECEIPT_NUMBER = "receipt_number_id";
	private static final String KEY_ORDER_ID = "order_number_id";// barcode
	private static final String KEY_DISCOUNT_VALUE = "discount_value";
	private static final String KEY_DISCOUNT_TYPE_ID = "discount_type_id";
	
	
	private static final String KEY_NOTE_ID = "note_id";
	private static final String KEY_CMS_ID = "cms_id";// barcode
	
	
	private static final String KEY_QTY = "quantity";
	private static final String KEY_ITEM_ID = "item_id";
	
	private static final String KEY_SERVER_RESPONCE = "server_response";
	
	
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

		private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + 
				"(" + KEY_ID + " INTEGER PRIMARY KEY , " 
				+ KEY_USER + " TEXT, " 
				+ KEY_PASSWORD+ " TEXT , "
				+ KEY_FIRSTNAME + " TEXT," 
				+ KEY_LASTNAME + " TEXT," 
				+ KEY_EMAIL + " TEXT, "
				+ KEY_MOBILE + " TEXT ," 
				+KEY_USERTYPE_ID + " INTEGER)";
		
		private static final String CREATE_TABLE_LOGIN_USERTYPE  = "CREATE TABLE "
				+ TABLE_USER_TYPE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_TITLE + " TEXT)";
		
		private static final String CREATE_TABLE_SYSTEM_CONFIG  = "CREATE TABLE "
				+ TABLE_SYSTEM_CONFIG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				+ " key TEXT unique, value TEXT)";
		
 
		
		//////////////////////////////////////////////////////
		private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE transactions " 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " +
				"transaction_type_id INTEGER , "+ 
				"quantity INTEGER , "+ 
				"item_id" + " INTEGER , "+ 
				"item_cms_id" + " INTEGER , "+
				KEY_RECEIPT_NUMBER + " TEXT , "+
				KEY_ORDER_ID + " INTEGER, "+ 
				KEY_STATUS_ID +  " INTEGER , "+ 
				"parent_type" + " TEXT , "+ 
				KEY_PARENT_TYPE_ID + " INTEGER , "+
				"sold_price" + " REAL DEFAULT '0', "+ 
				KEY_DISCOUNT_VALUE + " REAL , "+ 
				KEY_DISCOUNT_TYPE_ID + " INTEGER , "+
				
				KEY_DECS1 + " TEXT, "+
				KEY_DECS2 +" TEXT , "+ 
				KEY_DECS3 +" TEXT , "+
				KEY_SUPPLIER_NO + " TEXT  , "+ 
				KEY_SUPPLIER_ITEM_NO + " TEXT , "+ 
				KEY_BARCODE + " TEXT , "+ 
				KEY_COLOR_ID + " TEXT , "+ 
				KEY_SIZE_ID + " TEXT , "+ 
				KEY_GROUP_ID + " TEXT , "+
				KEY_USER_ID+ " TEXT , "+
				
				KEY_BUY_PRICE + " REAL DEFAULT '0', "+ 
				KEY_SELL_PRICE + " REAL DEFAULT '0', "+
				KEY_TAX + " INTEGER , "+
				
				KEY_CREATED + " DATETIME, "+ 
				KEY_UPDATED + " DATETIME, isSynced " + " INTEGER DEFAULT '0' )";
		
		private static final String CREATE_TABLE_TRANSACTION_TYPE = "CREATE TABLE transaction_types" 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";

		private static final String CREATE_TABLE_TRANSACTION_DISCOUNT  = "CREATE TABLE discount_types"
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";

		private static final String CREATE_TABLE_STATUS  = "CREATE TABLE status" 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";
		
		
		/////////////////////////////////////
		
		private static final String CREATE_TABLE_ORDER = "CREATE TABLE orders " 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " 
				+KEY_CREATED + " DATETIME, "+ 
				KEY_TOTALAMOUNT + " REAL DEFAULT '0', " + 
				KEY_RECEIPT_NUMBER + " INTEGER , " +
				KEY_STATUS_ID + " INTEGER DEFAULT '1', "+ 
				KEY_TOTALAMOUNT_SOLD + " INTEGER DEFAULT '0', " +
				KEY_DISCOUNT_VALUE + " REAL DEFAULT '0', "+ 
				KEY_DISCOUNT_TYPE_ID + " INTEGER DEFAULT '0', " +
				KEY_USER_ID + " INTEGER , isSynced " + " INTEGER DEFAULT '0')";
		
		
		private static final String CREATE_TABLE_ORDER_PAYMENTS = "CREATE TABLE orders_payments " 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " +
				KEY_ORDER_ID + " INTEGER , "+ 
				"payment_type_id" + " INTEGER , "+
				KEY_AMOUNT + " REAL DEFAULT '0', isSynced " + " INTEGER DEFAULT '0');";
		
		private static final String CREATE_TABLE_RECEIPT = "CREATE TABLE receipt_numbers " 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " +KEY_CREATED + " DATETIME  );";
		
		private static final String CREATE_TABLE_PAYMENTS_TYPE = "CREATE TABLE payments_type "
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_TITLE+ " TEXT );";
		
		private static final String CREATE_TABLE_DELIVERYNOTE = "CREATE TABLE delivery_notes "
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " 
				
				+KEY_CREATED + " DATETIME, "
				+"delivery_date " + " DATETIME, "
				+"received_at" + " DATETIME, "
				+"comment" + " TEXT , "+
				"company_number" + " TEXT , "+
				"branch_number" + " TEXT , "+
				
				KEY_CMS_ID + " INTEGER , "+
				KEY_STATUS_ID + " INTEGER , "+ 
				KEY_QTY + " INTEGER DEFAULT '0', "+
				KEY_NOTE_ID + " INTEGER , "+
				KEY_ITEM_ID + " TEXT , "+
				"received_quantity" + " INTEGER DEFAULT '0' , "+
				KEY_SERVER_RESPONCE + " INTEGER , "+
				KEY_USER_ID + " INTEGER )";
		
		
		// closing database
 		public void closeDB() {
 			SQLiteDatabase db = this.getReadableDatabase();
 			if (db != null && db.isOpen())
 				db.close();
 		}

}
