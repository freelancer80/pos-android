package pos.main.model;



public class Items {
	
	int id;
	String decs1, decs2, decs3, 
	supplier_number, supplier_item_number, 
	barcode,
	buying_price, selling_price,
	taxation_code,
	available_quantity, total_qty, return_qty,
	small_pic, large_pic, 
	created_at, updated_at ,
	size_id, color_id, group_id, item_id;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSize_id() {
		return size_id;
	}
	public void setSize_id(String size_id) {
		this.size_id = size_id;
	}
	public String getColor_id() {
		return color_id;
	}
	public void setColor_id(String color_id) {
		this.color_id = color_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getDecs1() {
		return decs1;
	}
	public void setDecs1(String decs1) {
		this.decs1 = decs1;
	}
	public String getDecs2() {
		return decs2;
	}
	public void setDecs2(String decs2) {
		this.decs2 = decs2;
	}
	public void setDecs3(String decs2) {
		this.decs3 = decs2;
	}
	public String getDecs3() {
		return decs3;
	}
	public String getSupplier_number() {
		return supplier_number;
	}
	public void setSupplier_number(String supplier_number) {
		this.supplier_number = supplier_number;
	}
	public String getSupplier_item_number() {
		return supplier_item_number;
	}
	public void setSupplier_item_number(String supplier_item_number) {
		this.supplier_item_number = supplier_item_number;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBuying_price() {
		return buying_price;
	}
	public void setBuying_price(String buying_price) {
		this.buying_price = buying_price;
	}
	public String getSelling_price() {
		return selling_price;
	}
	public void setSelling_price(String selling_price) {
		this.selling_price = selling_price;
	}
	public String getTaxation_code() {
		return taxation_code;
	}
	public void setTaxation_code(String taxation_code) {
		this.taxation_code = taxation_code;
	}
	public String getAvailable_quantity() {
		return available_quantity;
	}
	public void setAvailable_quantity(String available_quantity) {
		this.available_quantity = available_quantity;
	}
	public String getSmall_pic() {
		return small_pic;
	}
	public void setSmall_pic(String small_pic) {
		this.small_pic = small_pic;
	}
	public String getLarge_pic() {
		return large_pic;
	}
	public void setLarge_pic(String large_pic) {
		this.large_pic = large_pic;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
	
	
	public String getTotal_qty() {
		return total_qty;
	}
	public void setTotal_qty(String total_qty) {
		this.total_qty = total_qty;
	}
	public String getReturn_qty() {
		return return_qty;
	}
	public void setReturn_qty(String return_qty) {
		this.return_qty = return_qty;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public Items(int id,String item_id,String group_id,  String color_id, String size_id, String decs1,
			String decs2,String decs3, 
			String supplier_number, String supplier_item_number,
			
			String barcode, String buying_price, String selling_price,
			String taxation_code, String available_quantity, String small_pic,
			String large_pic, String created_at, String updated_at) {
		
		super();
		this.id = id;
		this.item_id = item_id;
		this.size_id = size_id;
		this.color_id = color_id;
		this.group_id = group_id;
		this.decs1 = decs1;
		this.decs2 = decs2;
		this.decs3 = decs3;
		this.supplier_number = supplier_number;
		this.supplier_item_number = supplier_item_number;
		this.barcode = barcode;
		this.buying_price = buying_price;
		this.selling_price = selling_price;
		this.taxation_code = taxation_code;
		this.available_quantity = available_quantity;
		this.small_pic = small_pic;
		this.large_pic = large_pic;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public Items() {
		super();
	}
	public Items(String id) {
		super();
		this.item_id = id;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*String[] items_code = { "1109876000", "1123456789", "1023454567", "1432165428", "1232165428",
			"1332165428", "1345176534", "1234584567" ,"2314498765","1329382654",
			"1213386356","2332165428"};

	String[] items_name = { "Bag","Bag","Braslet","Cufflink","Earing",
				"Earing", "Earing", "Earing","Notebooks","Stainless Steel Set",
				"Tie Clip","braslet" };
	
	String[] items_price = { "800,00", "70,00", "150,00", "60,00", "200,00",
				 "800,00","100,00","150,00","150,00","200,00",
				 "800,00","100,00" };
	
	String[] items_discount = { "30", "7", "15","0","0",
					"0", "6", "10","0","0",
					"0","0" };
	
	int [] imagearr={ R.drawable.bag, R.drawable.bag2,R.drawable.breslets,R.drawable.cuflink,R.drawable.earing2,
		R.drawable.earing4, R.drawable.earing5, R.drawable.earing6,R.drawable.note_book,R.drawable.stainless,
		R.drawable.clip,R.drawable.breslets};

	public String getItems_code(int index) {
		return items_code[index];
	}

	public void setItems_code(String[] items_code) {
		this.items_code = items_code;
	}

	public String getItems_name(int index) {
		return items_name[index];
	}

	public void setItems_name(String[] items_name) {
		this.items_name = items_name;
	}

	public String getItems_price(int index) {
		return items_price[index];
	}

	public void setItems_price(String[] items_price) {
		this.items_price = items_price;
	}

	public String getItems_discount(int index) {
		return items_discount[index];
	}

	public void setItems_discount(String[] items_discount) {
		this.items_discount = items_discount;
	}

	public int[] getImagearr() {
		return imagearr;
	}

	public void setImagearr(int[] imagearr) {
		this.imagearr = imagearr;
	}*/

}
