package pos.main.db.DAO;

import pos.main.model.Users;
import android.content.ContentValues;
import android.content.Context;

public class UsersDAO extends BaseDAO{
	
	public static String TABLE="cached";
	
	public static final String ID="id";
	public static final String NAME="name";
	
	public static final String CREATE_STATEMENT = "CREATE TABLE "+TABLE+"("+
												  ID+" INTEGER PRIMARY KEY  NOT NULL ," +
												  NAME+" VARCHAR" +
												  ")";
	
	public UsersDAO(Context context) {
    	super(context);
    }
    
    /*
     * Write your functions here
     * */
    
	public boolean insert(Users user) {
		
		boolean inserted=true;
		
		try{
			
			openDB();
			mDb.beginTransaction();
			
			ContentValues contentValues=new ContentValues();
			contentValues.put(ID, user.getId()); 
			contentValues.put(NAME, user.getFirstName());
			mDb.insert(TABLE, null, contentValues);
			
			mDb.setTransactionSuccessful();
			
		}catch(Exception e){
			e.printStackTrace();
			inserted=false;
		}finally{
			mDb.endTransaction();
			closeDB();
		}
		
		return inserted;
    }
    
}
