package pos.main.model;

public class ReturnItemsOrder {
	
	String order_id;
	String array_order[];

	public ReturnItemsOrder(String order_id) {
		super();
		this.order_id = order_id;
	}

	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String [] getOrderDetails() {
		
		if(order_id.equals("12345")){
			array_order= new String[5];
			array_order[0]="0";
			array_order[1]="1";
			array_order[2]="2";
			array_order[3]="3";
			array_order[4]="4";
		}
		return array_order;
	}
}
