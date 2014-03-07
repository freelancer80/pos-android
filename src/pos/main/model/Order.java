package pos.main.model;

public class Order {

	int id;
	String total_amount,receipt_number_id,
	status_id, total_amount_sold, 
	discount_value, discount_type_id,
	user_id,  order_number_id, created_at;
	
	public Order(String total_amount, String receipt_number_id,
			String status_id, String total_amount_sold, String discount_value,
			String discount_type_id, String user_id, String order_number_id) {
		super();
		this.total_amount = total_amount;
		this.receipt_number_id = receipt_number_id;
		this.status_id = status_id;
		this.total_amount_sold = total_amount_sold;
		this.discount_value = discount_value;
		this.discount_type_id = discount_type_id;
		this.user_id = user_id;
		this.order_number_id = order_number_id;
	}
	public Order() {
		super();
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getReceipt_number_id() {
		return receipt_number_id;
	}
	public void setReceipt_number_id(String receipt_number_id) {
		this.receipt_number_id = receipt_number_id;
	}
	public String getStatus_id() {
		return status_id;
	}
	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}
	public String getTotal_amount_sold() {
		return total_amount_sold;
	}
	public void setTotal_amount_sold(String total_amount_sold) {
		this.total_amount_sold = total_amount_sold;
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
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOrder_number_id() {
		return order_number_id;
	}
	public void setOrder_number_id(String order_number_id) {
		this.order_number_id = order_number_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	

}
