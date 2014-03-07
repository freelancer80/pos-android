package pos.main.model;

public class Transaction {
	int id;
	
	String transaction_type_id, receipt_number_id, order_number_id,
	status_id, parent_type, parent_type_id, sold_price, discount_value, discount_type_id;
	
	
	String decs1, decs2, decs3, supplier_number, supplier_item_number,
	barcode, buying_price, selling_price, taxation_code, available_quantity,
	quantity,  created_at, updated_at , size_id, color_id, group_id, item_id, user_id, item_cms_id, isSynced;

	
	
	public String getIsSynced() {
		return isSynced;
	}
	public void setIsSynced(String isSynced) {
		this.isSynced = isSynced;
	}


	public Transaction(String transaction_type_id, String quantity, String item_id,
						String receipt_number_id, String order_number_id, 
						String status_id,  String created_at,String updated_at,
						String parent_type,String parent_type_id, 
						String sold_price, String discount_value,String discount_type_id, 
						
						String decs1, String decs2, String decs3,
						String supplier_number, String supplier_item_number,String barcode, 
						String group_id, String color_id, String size_id,
						String buying_price, String selling_price,String taxation_code) {
		super();
		this.transaction_type_id = transaction_type_id;
		this.item_id=item_id;
		
		this.receipt_number_id = receipt_number_id;
		this.order_number_id = order_number_id;
		this.status_id = status_id;
		this.parent_type = parent_type;
		this.parent_type_id = parent_type_id;
		this.sold_price = sold_price;
		this.discount_value = discount_value;
		this.discount_type_id = discount_type_id;
		this.decs1 = decs1;
		this.decs2 = decs2;
		this.decs3 = decs3;
		this.supplier_number = supplier_number;
		this.supplier_item_number = supplier_item_number;
		this.barcode = barcode;
		this.buying_price = buying_price;
		this.selling_price = selling_price;
		this.taxation_code = taxation_code;
		this.quantity = quantity;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.size_id = size_id;
		this.color_id = color_id;
		this.group_id = group_id;
		//this.item_id = item_id;
	}


	public String getAvailable_quantity() {
		return available_quantity;
	}
	public void setAvailable_quantity(String available_quantity) {
		this.available_quantity = available_quantity;
	}
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getItem_cms_id() {
		return item_cms_id;
	}


	public void setItem_cms_id(String item_cms_id) {
		this.item_cms_id = item_cms_id;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTransaction_type_id() {
		return transaction_type_id;
	}


	public void setTransaction_type_id(String transaction_type_id) {
		this.transaction_type_id = transaction_type_id;
	}


	public String getReceipt_number_id() {
		return receipt_number_id;
	}


	public void setReceipt_number_id(String receipt_number_id) {
		this.receipt_number_id = receipt_number_id;
	}


	public String getOrder_number_id() {
		return order_number_id;
	}


	public void setOrder_number_id(String order_number_id) {
		this.order_number_id = order_number_id;
	}


	public String getStatus_id() {
		return status_id;
	}


	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}


	public String getParent_type() {
		return parent_type;
	}


	public void setParent_type(String parent_type) {
		this.parent_type = parent_type;
	}


	public String getParent_type_id() {
		return parent_type_id;
	}


	public void setParent_type_id(String parent_type_id) {
		this.parent_type_id = parent_type_id;
	}


	public String getSold_price() {
		return sold_price;
	}


	public void setSold_price(String sold_price) {
		this.sold_price = sold_price;
	}


	public String getDiscount_value() {
		return discount_value;
	}


	public void setDiscount_value(String discount_value) {
		this.discount_value = discount_value;
	}


	public String getDiscount_type_id() {
		return discount_type_id;
	}


	public void setDiscount_type_id(String discount_type_id) {
		this.discount_type_id = discount_type_id;
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


	public String getDecs3() {
		return decs3;
	}


	public void setDecs3(String decs3) {
		this.decs3 = decs3;
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


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
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


	public String getItem_id() {
		return item_id;
	}


	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	
	
	
	

}
