package pos.main.model;

public class Voucher {
	
	String voucher_payment;
	String voucher_code;
	
	
	public String getVoucher_payment() {
		return voucher_payment;
	}
	public void setVoucher_payment(String voucher_payment) {
		this.voucher_payment = voucher_payment;
	}
	public String getVoucher_code() {
		return voucher_code;
	}
	public void setVoucher_code(String voucher_code) {
		this.voucher_code = voucher_code;
	}
	
	public Voucher(String voucher_code) {
		super();
		this.voucher_code = voucher_code;
		
		if(this.voucher_code.equals("12345")){
			voucher_payment="1000";
		}
	}
	
	

}
