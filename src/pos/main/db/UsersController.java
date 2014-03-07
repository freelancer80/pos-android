package pos.main.db;

import java.util.ArrayList;

import pos.main.db.DAO.UsersDAO;
import pos.main.model.Users;
import android.content.Context;

public class UsersController {

	Context context;
	
	UsersDAO usersDAO;
	static UsersController usersController=null;
	
	public UsersController(Context context){
		this.context=context;
		usersDAO=new UsersDAO(context);
	}
	
	
	public static UsersController getInstance(Context context){
		if(usersController==null)
			usersController=new UsersController(context);
		
		return usersController;
	}
	
	public boolean addUser(Users user){
		
		boolean success=false;
		
		try{
			DBLock.getInstance().getDBLock();
			success=usersDAO.insert(user);
			
			if(success){
				// operation was successfull. Keep a log of this change in a separate table. Later while syncing, we'll get log of changes from that table one by one and sync it with server.			}
			}			
		}catch (InterruptedException e){
			e.printStackTrace();
		}finally{
			DBLock.getInstance().releaseDBLock();
		}
		
		return success;
	}
	
		
}
