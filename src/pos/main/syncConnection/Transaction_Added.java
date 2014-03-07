package pos.main.syncConnection;

import android.content.Context;
import pos.main.database.Items_Database;
import pos.main.database.Transaction_Tables;
import pos.main.model.Items;
import pos.main.model.Transaction;

public class Transaction_Added {
	
	
	String item_code, selling_price, decs1;
	Items_Database db_items;
	Transaction_Tables db;
	
	Context context;
	
	
	public Transaction_Added(String item_code, Context context) {
		super();
		this.item_code = item_code;
		this.context = context;
		
		db= new Transaction_Tables(context);
		db_items= new Items_Database(context);
	}
	
	public long addTransaction() {
		
		Items  item= db_items.getItemRow(item_code);
		/*
		 * String transaction_type_id, String quantity,
			String item_id,
			String receipt_number_id, String order_number_id, 
			String status_id,  String created_at,String updated_at,
			String parent_type,String parent_type_id, 
			String sold_price, String discount_value,String discount_type_id, 
			
			String decs1, String decs2, String decs3,
			
			String supplier_number, String supplier_item_number,String barcode, 
			
			String group_id, String color_id, String size_id,
			
			String buying_price, String selling_price,String taxation_code
		 */// out ward
		Transaction transaction= new Transaction("2",item.getAvailable_quantity(),
				""+item.getId(),"", "1","1", "", "", 
				"item_order", "","","", "",
				item.getDecs1(), item.getDecs2(),item.getDecs3(),
				item.getSupplier_number(), item.getSupplier_item_number(),item.getBarcode(),
				item.getGroup_id(),item.getColor_id(), item.getSize_id(),
				item.getBuying_price(), item.getSelling_price(), item.getTaxation_code());
		
		return db.createItems(transaction);
	}
	
	
	public void qty_added(String trans_id, String qty) {
		db.insertQuantity(trans_id, qty);
	}
	
	public void discount_added(String trans_id, String dis_type_id, String discount) {
		int i=db.insert_Discount(trans_id, discount, dis_type_id);
		System.out.println("insert discount........."+i);
	}
	

}
