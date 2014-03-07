package pos.main.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import pos.main.database.Items_Database;
import pos.main.model.Items;

public class Items_FetchingDatabase {
	Context context;
	Items_Database db_items;
	
	
	public Items_FetchingDatabase(Context context) {
		super();
		this.context= context;
		db_items = new Items_Database(context.getApplicationContext());
		
	}

	public ArrayList<HashMap<String, String>> GetItems() {
		List<Items> allItems = db_items.getAllItems("0","50");
		
		for (final Items items : allItems) {
			
			System.out.println("1."+items.getBarcode()
					+"\n.........."+ items.getId()
					+"\n.........."+ items.getAvailable_quantity()
					+"\n.........."+ items.getBuying_price()
					+"\n.........."+ items.getCreated_at()
					+"\n.........."+ items.getUpdated_at()
					+"\n.........."+ items.getSelling_price()
					+"\n.........."+ items.getLarge_pic()
					+"\n.........."+ items.getSmall_pic()
					);
			
			
		}
		
		
		db_items.closeDB();
		
		
		
		
		
		
		
		return null;
		
	}

	public int insertItems() {
		return 0;
		
	}
	
	public void getItem(String itemcode) {
		
	}
	
}
