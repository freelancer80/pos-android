package pos.main.model;

public class DeliveryNotes {
	
	int id;
	String note_id, item_id, quantity, delivery_date, created_at, status_id, received_at, received_quantity,
	comment, user_id, company_number, branch_number,cms_id, server_response, netValue;
	
	
	String isSynced;
	
	
	
	public String getIsSynced() {
		return isSynced;
	}
	public void setIsSynced(String isSynced) {
		this.isSynced = isSynced;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNote_id() {
		return note_id;
	}
	public void setNote_id(String note_id) {
		this.note_id = note_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getStatus_id() {
		return status_id;
	}
	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}
	public String getReceived_at() {
		return received_at;
	}
	public void setReceived_at(String received_at) {
		this.received_at = received_at;
	}
	public String getReceived_quantity() {
		return received_quantity;
	}
	public void setReceived_quantity(String received_quantity) {
		this.received_quantity = received_quantity;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCompany_number() {
		return company_number;
	}
	public void setCompany_number(String company_number) {
		this.company_number = company_number;
	}
	public String getBranch_number() {
		return branch_number;
	}
	public void setBranch_number(String branch_number) {
		this.branch_number = branch_number;
	}
	
	public DeliveryNotes(String note_id, String item_id, String quantity,
			String delivery_date, String created_at, String status_id,
			String received_at, String received_quantity, String comment,
			String user_id, String company_number, String branch_number,
			String cms_is) {
		super();
		this.note_id = note_id;
		this.item_id = item_id;
		this.quantity = quantity;
		this.delivery_date = delivery_date;
		this.created_at = created_at;
		this.status_id = status_id;
		this.received_at = received_at;
		this.received_quantity = received_quantity;
		this.comment = comment;
		this.user_id = user_id;
		this.company_number = company_number;
		this.branch_number = branch_number;
		this.cms_id = cms_is;
	}
	public DeliveryNotes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeliveryNotes(String note_id, String item_id, String quantity,
			String delivery_date, String company_number, String branch_number,
			String cms_id) {
		super();
		this.note_id = note_id;
		this.item_id = item_id;
		this.quantity = quantity;
		this.delivery_date = delivery_date;
		this.company_number = company_number;
		this.branch_number = branch_number;
		this.cms_id = cms_id;
	}
	
	public String getNetValue() {
		return netValue;
	}
	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}
	public String getCms_id() {
		return cms_id;
	}
	public void setCms_id(String cms_id) {
		this.cms_id = cms_id;
	}
	public String getServer_response() {
		return server_response;
	}
	public void setServer_response(String server_response) {
		this.server_response = server_response;
	}
	public DeliveryNotes(int id, String received_quantity, String comment, String rec) {
		super();
		this.id = id;
		this.received_quantity = received_quantity;
		this.comment = comment;
	this.received_at= rec;
	}
	
	
	

}
