package pos.main.db.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDAO {

	public static final String DATABASE_NAME = "pos.sqlite";
	protected final static int DATABASE_VERSION = 1;

	private final Context context;
	protected static DatabaseHelper dBHelper;
	protected static SQLiteDatabase mDb;
	Cursor cursor=null;

	public BaseDAO(Context ctx) {
		context = ctx;
	}
	
	public SQLiteDatabase openDB() {
		
		if (dBHelper == null)
        	dBHelper = new DatabaseHelper(context);
        
    	return dBHelper.getWritableDatabase();
    }

    public void closeDB() {
    	if (cursor != null && !cursor.isClosed()) {
	    	cursor.close();
	    }
    	mDb.close();
    	dBHelper.close();
    }

    protected static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
        public void onCreate(SQLiteDatabase db) {
			db.execSQL(UsersDAO.CREATE_STATEMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+UsersDAO.TABLE);
            onCreate(db);
        }

	}

}
