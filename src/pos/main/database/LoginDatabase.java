package pos.main.database;


import pos.main.model.Users;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LoginDatabase extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "POS";

	public LoginDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		System.out.println("table............"+CREATE_TABLE_LOGIN);
		db.execSQL(CREATE_TABLE_LOGIN);
		db.execSQL(CREATE_TABLE_LOGIN_USERTYPE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_TYPE);
		// create new tables
		onCreate(db);
	}
	/*
	 * TABLES NAME
	 */
	
	private static final String TABLE_LOGIN = "users";
	private static final String TABLE_USER_TYPE = "user_type";
	
	private static final String KEY_ID = "id";
	//private static final String KEY_USERTYPE_ID = "user_type_id";
	
	/*
	 * login table
	 */
	private static final String KEY_USER = "login";
	//private static final String KEY_USER_ID = "user_id";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_FIRSTNAME = "first_name";
	private static final String KEY_LASTNAME = "last_name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_MOBILE = "mobile";
	
	/*
	 * user type 
	 */
	private static final String KEY_USERTYPE_ID = "user_type_id";
	private static final String KEY_TITLE = "title";
	
	
	private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + 
			"(" + KEY_ID + " INTEGER PRIMARY KEY , " 
			+ KEY_USER + " TEXT, " 
			+ KEY_PASSWORD+ " TEXT , "
			+ KEY_FIRSTNAME + " TEXT," 
			+ KEY_LASTNAME + " TEXT," 
			+ KEY_EMAIL + " TEXT, "
			+ KEY_MOBILE + " TEXT ," 
			+KEY_USERTYPE_ID + " INTEGER)";
	
	
	/*private static final String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY , " + KEY_ROLE+ " TEXT );";
*/
	private static final String CREATE_TABLE_LOGIN_USERTYPE  = "CREATE TABLE "
			+ TABLE_USER_TYPE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_TITLE + " TEXT)";
	
	
	public long createUser(Users new_user) {
		
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER, new_user.getUser());
		values.put(KEY_PASSWORD, new_user.getPassword());
		values.put(KEY_FIRSTNAME, new_user.getFirstName());
		values.put(KEY_LASTNAME, new_user.getLastName());
		values.put(KEY_EMAIL, new_user.getEmail());
		values.put(KEY_MOBILE, new_user.getMobile());
		String type= new_user.getType();
		
		
		values.put(KEY_USERTYPE_ID, type);
		// insert row
		long item_ids = db.insert(TABLE_LOGIN, null, values);
		System.out.println("items added  ids ...."+item_ids);
	//	String type_title = null;
		
		if(type.equals("1")){
		//	type_title= "admin";
		}
		
		//ContentValues values1 = new ContentValues();
		
		//values1.put(KEY_TITLE, type_title);

		// insert row
		//long tag_id = db.insert(TABLE_USER_TYPE, null, values1);
		
		
		//System.out.println("login submit"+ tag_id+"..."+ item_ids);
		return item_ids;
		
	}
	
	
	public String searchUser(Users new_user) {
		
		String user= new_user.getUser();
		String password=new_user.getPassword();
		
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE "
				+ KEY_USER + " = '" + user+"' and "+ KEY_PASSWORD+"= '"+password+"'";

		Log.e("login id return..", selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		//Users td = new Users();
	//	td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		return null;
		
	}
	
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
}
