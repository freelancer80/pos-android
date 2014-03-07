package pos.main.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.text.DecimalFormat;

import pos.main.adapters.CustomAutoCompleteTextView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class MainActivity1 extends Activity implements OnClickListener {
	
	
	private static int KEY_COLUMN_SR = 0;
	private static int KEY_COLUMN_ITEM_CODE =1;
	private static int KEY_COLUMN_ITEM_NAME =2;
	private static int KEY_COLUMN_RATE = 3;
	private static int KEY_COLUMN_DIS_TYP = 4;
	private static int KEY_COLUMN_DIS = 5;
	private static int KEY_COLUMN_QTY = 6;
	private static int KEY_COLUMN_AMOUNT = 7;
	private static int KEY_COLUMN_LENGHT = 8;
	private static String KEY_DIS_TAG = "€";
	
	String active_user;
	
	
	CustomAutoCompleteTextView pincode;
	Button one, two, three, four, five, six, seven, eight, nine, zero, z_zero;
	Button confirm, del, back;
	
	Button park, unpark, discount, search, comma;
	TextView subtotal, t_discount,tax, payment, payment_text,alternate, alternate_text, change, total_payment;
	TextView l_heading;
	
	boolean discount_amount_selected=false;
	boolean discount_percent_selected=false;
	boolean item_code_selected=false, item_code_selected_receipt= false;
	
	boolean paid_confirmed=false;
	boolean tab_cash_selected=false, 
			tab_card_selected=false, 
			tab_voucher_selected=false, 
			tab_returned_selected=false, login_pop= false;
	
	
    int trHeight = 0;
    int  size=15;
    DecimalFormat df = new DecimalFormat("0.00");
	
	String item_code="";
	String[] items_code = { "1109876000", "1123456789", "1023454567", "1432165428", "1232165428",
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
	
	ArrayList<HashMap<String, String>> item_list = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> searchResults;
	
	int salecounter=0, salecounter_returned=0;
	

	TableLayout tl, tl_return;		TableRow tr_head;
	boolean countr= false;
	String itemcode_selected="code", itemname_selected="name", item_price="price", item_discount="discount", item_quantity="";
	int item_img;
	LinearLayout linear;
	
	LinearLayout inflateLayout;		LinearLayout qty_layout; 	LinearLayout discount_layout; 		RelativeLayout layout;
	RelativeLayout layoutPayment;	LinearLayout cashLayout, cardLayout, voucher_layout, returned_layout;
	LinearLayout paid_withchange_Layout, paid_Layout;
	
	RelativeLayout layoutPaymentPaid;
	
	Button tab_cash, tab_card, tab_giftvoucher, tab_returned;
	Button cash_05, cash_10, cash_20, cash_50, cash_100, cash_200;
	Button visa, solo, maestro, mastercard, visaelectron, delta;
	Button cash_v1, cash_v2, cash_v3, card_v1, card_v2, card_v3;
	Button q_inc, q_dec, d_rupee, d_percentage;
	
	TableRow r_total, r_remaining; TextView voucher_total, voucher_remaining, voucher_heading, return_receipt;
	ImageView item_image;
	EditText quantity;
	
	/// index for discount 2 views use
	int discount_tag_index=0, row_selector=0;
	int deleteRowIndex=0;
	SimpleAdapter adapter;
	double totalAmount=0.00;
	double submit_amount=0.00;
	String submit_t="";
	String pincode_concat="";
	String paymentAlternate="";
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		countDownTimer = new MyCountDownTimer(startTime, interval);
		// counter start...
		
		final float scale = getResources().getDisplayMetrics().density; 
        trHeight = (int) (34 * scale + 0.5f);
        
		linear= (LinearLayout) findViewById(R.id.linearLayout3);
		inflateLayout = (LinearLayout)View.inflate(this, R.layout.qty_discount, null);
		layoutPayment = (RelativeLayout)View.inflate(this, R.layout.currency, null);
		layoutPaymentPaid = (RelativeLayout)View.inflate(this, R.layout.paid_message, null);
		pincode= (CustomAutoCompleteTextView) findViewById(R.id.itemcode);
		pincode.setText("");
		
		
        init();
        createTable();
        keypad_block();
        block_back_del_confirm();
		comma.setClickable(false);
		comma.setEnabled(false);
		comma.setBackgroundResource(R.drawable.bt_comma_disable);
	}
	
	
    // 1st
	private void init() { 
		
		tl = (TableLayout) findViewById(R.id.purchase_items);
        tl.removeAllViews();
        
		one 	=(Button)findViewById(R.id.one);
		two 	=(Button)findViewById(R.id.two);
		three 	=(Button)findViewById(R.id.three);
		four 	=(Button)findViewById(R.id.four);
		five 	=(Button)findViewById(R.id.five);
		six 	=(Button)findViewById(R.id.six);
		seven 	=(Button)findViewById(R.id.seven);
		eight 	=(Button)findViewById(R.id.eight);
		nine 	=(Button)findViewById(R.id.nine);
		zero	=(Button)findViewById(R.id.zero);
		z_zero	=(Button)findViewById(R.id.zero_zero);
		comma 	=(Button)findViewById(R.id.comma);
		
		
		confirm = (Button) findViewById(R.id.confirm);
		del 	= (Button) findViewById(R.id.del);
		back  	= (Button) findViewById(R.id.back);
		
		park 	= (Button) findViewById(R.id.park);
		unpark	= (Button) findViewById(R.id.unpark);
		discount= (Button) findViewById(R.id.discount);
		search	= (Button) findViewById(R.id.search);
		
		
		one.setOnClickListener(this);
		two.setOnClickListener(this); 
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		six.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		zero.setOnClickListener(this);
		z_zero.setOnClickListener(this);
		
	//	del.setOnClickListener(this);
	//	confirm.setOnClickListener(this);
	//	back.setOnClickListener(this);
		discount.setEnabled(false);
		discount.setClickable(false);
		
		park.setEnabled(false);
		park.setClickable(false);
		
		unpark.setEnabled(false);
		unpark.setClickable(false);
		search.setEnabled(false);
		search.setClickable(false);
		
		//park.setOnClickListener(this);
		//unpark.setOnClickListener(this);
		//discount.setOnClickListener(this);
		//search.setOnClickListener(this);
		comma.setOnClickListener(this);
		/*
		subtotal=		(TextView) findViewById(R.id.subtotal);
		t_discount=		(TextView) findViewById(R.id.discount_value);
		tax=			(TextView) findViewById(R.id.sale_tax);
		payment=		(TextView) findViewById(R.id.payment);
		payment_text=	(TextView) findViewById(R.id.payment_head);
		alternate=		(TextView) findViewById(R.id.alternate);
		alternate_text=		(TextView) findViewById(R.id.alternate_head);
		change=			(TextView) findViewById(R.id.change);
		total_payment=	(TextView) findViewById(R.id.total_payment);*/
	}
	//2nd
	@SuppressLint("ResourceAsColor")
	@SuppressWarnings("deprecation")
	private void createTable() {

        tl = (TableLayout) findViewById(R.id.purchase_items);
        
        tl.removeAllViews();
        tr_head = new TableRow(this);
        
        TextView sr1 = new TextView(this);
        sr1.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        sr1.setId(size);
        sr1.setTextSize(size);
        sr1.setText("Sr.");
        sr1.setTextColor(Color.parseColor("#800080"));
        sr1.setTypeface(null, Typeface.BOLD);
        sr1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        sr1.setPadding(5, 5, 5, 5);
        sr1.setHeight(trHeight);
        //tr_head.setPadding(1, 1, 1, 1);
        tr_head.addView(sr1);// add the column to the table row here

        TextView item_code = new TextView(this);
        item_code.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        item_code.setId(size);// define id that must be unique
        item_code.setText("Item Code"); // set the text for the header 
        item_code.setTextColor(Color.parseColor("#800080"));
        item_code.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        item_code.setPadding(5, 5, 5, 5);
        item_code.setTextSize(size);
        item_code.setHeight(trHeight);
        item_code.setTypeface(null, Typeface.BOLD);
        tr_head.addView(item_code); // add the column to the table row here
        
        
        TextView item_name = new TextView(this);
        item_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        item_name.setId(size);
        item_name.setText("Item Name ");  
        item_name.setTextColor(Color.parseColor("#800080"));
        item_name.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        item_name.setPadding(5, 5, 5, 5);
        item_name.setTypeface(null, Typeface.BOLD);
        item_name.setHeight(trHeight);
        item_name.setTextSize(size);
        
        tr_head.addView(item_name);
         
        TextView rate = new TextView(this);
        rate.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        rate.setId(size);
        rate.setText(" Rate € ");  
        rate.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        rate.setTextColor(Color.parseColor("#800080"));
        rate.setPadding(5, 5, 5, 5);
        rate.setHeight(trHeight);
        rate.setTextSize(size);
        rate.setTypeface(null, Typeface.BOLD);
        tr_head.addView(rate);
        
        TextView discount_type =  new TextView(this);
        discount_type.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        discount_type.setId(size);
        discount_type.setText(" Disc "); 
        discount_type.setTextColor(Color.parseColor("#800080")); 
        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        discount_type.setPadding(5, 5, 5, 5); 
        discount_type.setHeight(trHeight);
        discount_type.setTextSize(size);
        discount_type.setTypeface(null, Typeface.BOLD);
        tr_head.addView(discount_type);
        
        TextView discount =  new TextView(this);
        discount.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        discount.setId(size);
        discount.setText("Disc-Amt"); 
        discount.setTextColor(Color.parseColor("#800080")); 
        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        discount.setPadding(0, 5, 0, 5); 
        discount.setHeight(trHeight);
        discount.setTextSize(size);
        discount.setTypeface(null, Typeface.BOLD);
        tr_head.addView(discount);
        
        TextView qty = new TextView(this);
        qty.setId(size);
        qty.setText(" Qty "); 
        qty.setTextColor(Color.parseColor("#800080")); 
        qty.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        qty.setPadding(5, 5, 5, 5);
        qty.setHeight(trHeight);
        qty.setTextSize(size);
        qty.setTypeface(null, Typeface.BOLD);
        qty.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        tr_head.addView(qty);
        
        TextView amount = new TextView(this);
        amount.setId(size);
        amount.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        amount.setText(" Amount € "); 
        amount.setTextColor(Color.parseColor("#800080")); 
        amount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        amount.setPadding(5, 5, 5, 5); 
        amount.setHeight(trHeight);
        amount.setTextSize(size);
        amount.setTypeface(null, Typeface.BOLD);
        tr_head.addView(amount);
        
        tl.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
        
        for(int r=1; r<14; r++){
        	
        	tr_head = new TableRow(MainActivity1.this);
        	tr_head.setId(r);
        	tr_head.setTag(r);
	 		
        	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
        		
        		TextView text = new TextView(this);
        		text.setHeight(trHeight);
        		text.setTextSize(size);
        		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		if(r%2==1){
        			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
            	}else{
            		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
            	}
        		text.setTag(""+r);
        		if(col==KEY_COLUMN_ITEM_CODE){ 
        			text.setText("0987654321");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_DIS_TYP){
        			text.setText("198%");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_DIS){
        			text.setText("198.00");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_QTY){
        			text.setText("123");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_RATE|| col== KEY_COLUMN_AMOUNT){
        			text.setText("1000,00");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_ITEM_NAME){
        			text.setText("");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else{
        			text.setText("");
        			text.setTextColor(Color.parseColor("#800080"));
            		text.setTag(""+r);
        		}
        		
        		if(col==KEY_COLUMN_DIS || col==KEY_COLUMN_QTY);
        			text.setPadding(5, 5, 5, 5); 
        		if(col==KEY_COLUMN_SR);
        		else
        			text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		tr_head.addView(text);
			}
        	tl.addView(tr_head);
  	 	}
	}
	
	private void counter_reset() {
		
		if (!timerHasStarted) {
			countDownTimer.start();
			timerHasStarted = true;
			   
		} else{
			countDownTimer.cancel();
			countDownTimer.start();
		}
	}
	//24th
	private void block_back_del_confirm() {
		confirm.setBackgroundResource(R.drawable.bt_confirm_disable);
		
		back.setText("");
		back.setBackgroundResource(R.drawable.bt_back_disable);
		back.setClickable(false);
		back.setEnabled(false);
		confirm.setEnabled(false);
		confirm.setClickable(false);
		del.setBackgroundResource(R.drawable.bt_delete_disable);
		del.setText("");
		del.setEnabled(false);
		del.setClickable(false);
		
		
	}
	
	//26th
	private void keypad_block() {
		one.setEnabled(false);
		one.setClickable(false);
		two.setEnabled(false);
		three.setEnabled(false);
		four.setEnabled(false);
		five.setEnabled(false);
		six.setEnabled(false);
		seven.setEnabled(false);
		eight.setEnabled(false);
		nine.setEnabled(false);
		zero.setEnabled(false);
		z_zero.setEnabled(false);
		
		two.setClickable(false);
		three.setClickable(false);
		four.setClickable(false);
		five.setClickable(false);
		six.setClickable(false);
		seven.setClickable(false);
		eight.setClickable(false);
		nine.setClickable(false);
		zero.setClickable(false);
		z_zero.setClickable(false);
		
		comma.setClickable(false);
		comma.setEnabled(false);
		comma.setBackgroundResource(R.drawable.bt_comma_disable);
		
		one.setBackgroundResource(R.drawable.bt_1_disable);
		two.setBackgroundResource(R.drawable.bt_1_disable2);
		three.setBackgroundResource(R.drawable.bt_1_disable2);
		four.setBackgroundResource(R.drawable.bt_1_disable2);
		five.setBackgroundResource(R.drawable.bt_1_disable2);
		six.setBackgroundResource(R.drawable.bt_1_disable2);
		seven.setBackgroundResource(R.drawable.bt_1_disable2);
		eight.setBackgroundResource(R.drawable.bt_1_disable2);
		nine.setBackgroundResource(R.drawable.bt_1_disable2);
		zero.setBackgroundResource(R.drawable.bt_1_disable2);
		z_zero.setBackgroundResource(R.drawable.bt_1_disable2);
		
		
		
		one.setTextColor(Color.parseColor("#e8d6e7"));
		two.setTextColor(Color.parseColor("#e8d6e7"));
		three.setTextColor(Color.parseColor("#e8d6e7"));
		four.setTextColor(Color.parseColor("#e8d6e7"));
		five.setTextColor(Color.parseColor("#e8d6e7"));
		six.setTextColor(Color.parseColor("#e8d6e7"));
		seven.setTextColor(Color.parseColor("#e8d6e7"));
		eight.setTextColor(Color.parseColor("#e8d6e7"));
		nine.setTextColor(Color.parseColor("#e8d6e7"));
		z_zero.setTextColor(Color.parseColor("#e8d6e7"));
		zero.setTextColor(Color.parseColor("#e8d6e7"));
		
		
	}
	//27th
	private void keypad_reset() {
		
		one.setOnClickListener(this);
		two.setOnClickListener(this); 
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		six.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		zero.setOnClickListener(this);
		z_zero.setOnClickListener(this);
		
		one.setEnabled(true);
		one.setClickable(true);
		two.setEnabled(true);
		three.setEnabled(true);
		four.setEnabled(true);
		five.setEnabled(true);
		six.setEnabled(true);
		seven.setEnabled(true);
		eight.setEnabled(true);
		nine.setEnabled(true);
		zero.setEnabled(true);
		z_zero.setEnabled(true);
		
		two.setClickable(true);
		three.setClickable(true);
		four.setClickable(true);
		five.setClickable(true);
		six.setClickable(true);
		seven.setClickable(true);
		eight.setClickable(true);
		nine.setClickable(true);
		zero.setClickable(true);
		z_zero.setClickable(true);
		
		comma.setClickable(true);
		comma.setEnabled(true);
		
		
		one.setBackgroundResource(R.drawable.main_1);
		two.setBackgroundResource(R.drawable.main_2);
		three.setBackgroundResource(R.drawable.main_2);
		four.setBackgroundResource(R.drawable.main_2);
		five.setBackgroundResource(R.drawable.main_2);
		six.setBackgroundResource(R.drawable.main_2);
		seven.setBackgroundResource(R.drawable.main_2);
		eight.setBackgroundResource(R.drawable.main_2);
		nine.setBackgroundResource(R.drawable.main_2);
		zero.setBackgroundResource(R.drawable.main_2);
		z_zero.setBackgroundResource(R.drawable.main_2);
		
		comma.setBackgroundResource(R.drawable.main_comma);
		
		one.setTextColor(Color.TRANSPARENT);
		two.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		three.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		four.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		five.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		six.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		seven.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		eight.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		nine.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		z_zero.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		zero.setTextColor(getResources().getColorStateList(R.drawable.button_text_change));
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onPause() {
		//delayhandler.removeCallbacksAndMessages(null);
	    super.onPause();
	}
	public void onResume() {
		super.onResume();
		//counter_reset();
	}
	@Override
    protected void onDestroy() {
		System.out.println("counter stop when app exit");
        countDownTimer.cancel();
        System.gc();
        super.onDestroy();
	}
	
	public static Handler mHandler;
	
	
	
	private CountDownTimer countDownTimer;
 	private boolean timerHasStarted = false;
 	private final long startTime = 180 * 1000;
 	private final long interval = 1 * 1000;
 	
	public class MyCountDownTimer extends CountDownTimer {
		
		public MyCountDownTimer(long startTime, long interval) {
			 super(startTime, interval);
		}
		@Override
		public void onFinish() {
			System.out.println("callllll......"+ login_pop);
			//Login_POPUP myObject = new Login_POPUP(MainActivity1.this, Global.active_user);
			//boolean a=myObject.showDialog();
			
		}
		@Override
		public void onTick(long millisUntilFinished) {
			System.out.println("lock remaining time...");
			System.out.println("" + millisUntilFinished / 1000);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}