package pos.main.db;

import java.util.concurrent.Semaphore;

public class DBLock {
	
	private Semaphore dbLock = new Semaphore(1);
	
	private static class SingletonHolder{
		public static final DBLock INSTANCE = new DBLock();
	}
	
	public static DBLock getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	public void getDBLock() throws InterruptedException{
		dbLock.acquire();
	}
	
	public void releaseDBLock(){
		dbLock.release();
	}


}
