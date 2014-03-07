package pos.main.ui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.text.DecimalFormat;

import pos.main.adapters.CustomAutoCompleteTextView;
import pos.main.database.Items_Database;
import pos.main.model.Global;
import pos.main.model.Items;
import pos.main.model.ReturnItemsOrder;
import pos.main.model.Voucher;

import pos.main.syncConnection.OrderConfirmed;
import pos.main.syncConnection.Transaction_Added;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.InputType;
import android.text.Selection;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.View.OnClickListener;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;



public class MainActivity extends Activity implements OnClickListener {
	
	
	private static String KEY_ITEM_NAME = "item_name";
	private static final String KEY_DECS2 = "description2";
	private static final String KEY_DECS3 = "description3";
	
	private static String KEY_ITEM_CODE = "item_code";
	private static String KEY_ITEM_CODE_ID = "item_cms_id";
	
	private static String KEY_ITEM_PRICE = "item_price";
	private static String KEY_ITEM_SOLD_PRICE = "sold_price";
	
	private static String KEY_ITEM_DISCOUNT_TYPE = "item_discount_type";
	private static String KEY_ITEM_DISCOUNT_VALUE = "item_discount_value";
	private static String KEY_ITEM_AVAILABLE_QUANTITY = "available_quantity";
	private static String KEY_ITEM_QUANTITY = "quantity";
	
	private static final String KEY_TAX = "taxation_code";
	private static String KEY_ITEM_IMAGE = "item_image";
	
	
	//private static final String KEY_ITEM_ID = "item_id";
	
	private static final String KEY_SUPPLIER_NO = "supplier_number";
	private static final String KEY_SUPPLIER_ITEM_NO = "supplier_item_number";
	private static final String KEY_BARCODE = "ean";// barcode
	private static final String KEY_BUY_PRICE = "buying_price";
	//private static final String KEY_SELL_PRICE = "selling_price";
	
	private static final String KEY_GROUP_ID = "group_id";
	private static final String KEY_COLOR_ID = "color";
	
	private static final String KEY_SIZE_ID = "size";
	
	
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
	
	Context context;
	
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
			tab_returned_selected=false;//, login_pop= false;
	
	
    int trHeight = 0;
    int  size=15;
    DecimalFormat df = new DecimalFormat("0.00");
	
	String item_code="";
	
	int imagearr[]={R.drawable.item_image_s};//={ R.drawable.bag, R.drawable.bag2,R.drawable.breslets,R.drawable.cuflink,R.drawable.earing2,
					//R.drawable.earing4, R.drawable.earing5, R.drawable.earing6,R.drawable.note_book,R.drawable.stainless,
					//R.drawable.clip,R.drawable.breslets};
	
	ArrayList<HashMap<String, String>> item_list = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> searchResults, order_items= new ArrayList<HashMap<String, String>>();
	
	int salecounter=0, salecounter_returned=0;
	

	TableLayout tl, tl_return;	 TableRow tr_head;
	boolean countr= false;
	String itemcode_selected="code", 
			itemname_selected="name", decs2, decs3,  
			
			item_price="price", 
			
			item_discount="discount", item_discount_type,
			item_quantity="", available_quantity,
			item_size, group, color, 
			buying_price, 
			//selling_price , 
			supp_no, supp_item_no, 
			barcode, tax_code, cms_id;
	
	
	
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
	
	String[] from = {KEY_ITEM_CODE,KEY_ITEM_NAME, KEY_ITEM_IMAGE };
    // Ids of views in listview_layout
    int[] to = {R.id.item_code_list ,R.id.item_name_list, R.id.item_image_list};
    
	/// index for discount 2 views use
	int discount_tag_index=0, row_selector=0;
	int deleteRowIndex=0;
	SimpleAdapter adapter;
	double totalAmount=0.00;
	double submit_amount=0.00;
	String submit_t="";
	String pincode_concat="";
	String paymentAlternate="";
	//String transaction_id;
	
	
	Transaction_Added add_Data;
	
	String order_id, payment_type1, payment_1, payment_type2, payment_2;
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context= getApplicationContext();
		Bundle b= getIntent().getExtras();
		System.out.println("mainscreen active user id is:..........."+Global.active_user);
		if(b!= null){
			order_id= b.getString("order_id");
			System.out.println(".order id........"+order_id);
		}
		
		/*Handler h = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {

		        if (msg.what != 1) { // code if not connected
		        	System.out.println("internet not availability ...........");
		        	//boolean internet_available= false;
		        } else { // code if connected
		        	System.out.println("internet  availability ...........");
		        	//boolean internet_available= true;
		        }   
		    }
		};
		Internet_Available.isNetworkAvailable( h, 200);
		
		*/
		db_items = new Items_Database(getApplicationContext());
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		countDownTimer = new MyCountDownTimer(startTime, interval);
		final float scale = getResources().getDisplayMetrics().density; 
        trHeight = (int) (34 * scale + 0.5f);
        linear= (LinearLayout) findViewById(R.id.linearLayout3);
		inflateLayout = (LinearLayout)View.inflate(this, R.layout.qty_discount, null);
		layoutPayment = (RelativeLayout)View.inflate(this, R.layout.currency, null);
		layoutPaymentPaid = (RelativeLayout)View.inflate(this, R.layout.paid_message, null);
		int i=0;
		List<Items> allItems = db_items.getAllItems("0", "10");
		imagearr= new int[allItems.size()];
		for (final Items items : allItems) {
			
			/*System.out.println("1."+items.getBarcode()
					+"\n.........."+ items.getId()
					+"\n.........."+ items.getAvailable_quantity()
					+"\n.........."+ items.getGroup_id()
					+"\n.........."+ items.getSize_id()
					+"\n.........."+ items.getColor_id()
					+"\n.........."+ items.getSelling_price()
					+"\n.........."+ items.getLarge_pic()
					+"\n.........."+ items.getDecs1());
			*/
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_ITEM_CODE, items.getItem_id());
			map.put(KEY_ITEM_NAME, items.getDecs1());
			map.put(KEY_ITEM_PRICE, items.getSelling_price());
			map.put(KEY_ITEM_DISCOUNT_TYPE, "1");// NONE
			map.put(KEY_ITEM_DISCOUNT_VALUE, "0");
			imagearr[i]=R.drawable.item_image_s;
			i++;
			map.put(KEY_ITEM_IMAGE, Integer.toString(imagearr[0]));
			item_list.add(map);
		}
		System.out.println("length....."+item_list.size());
		db_items.closeDB();
		
		searchResults=new ArrayList<HashMap<String,String>>(item_list);
		System.out.println(item_list.size());
		pincode= (CustomAutoCompleteTextView) findViewById(R.id.itemcode);
		pincode.setText("");
		pincode.setHint("");
		pincode.setInputType(InputType.TYPE_NULL);
		
		int position = pincode.length();
		Selection.setSelection(pincode.getText(), position);
		
		pincode.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&  (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
					//Toast.makeText(MainActivity.this, pincode.getText(), Toast.LENGTH_LONG).show();
					item_call(pincode.getText().toString());
					if(item_list.size()>0){
						
						reset_back_del_confirm();
						setDisplay_value() ;
					}
					System.out.println("value..............."+ pincode.getText().toString());
					return true;
				}
				return false;
			}
		});
		
		adapter = new SimpleAdapter(getBaseContext(), searchResults, R.layout.list_item_code, from, to);
		
		/** Setting the itemclick event listener */
        pincode.setOnItemClickListener(itemClickListener);
        
        pincode.setAdapter(adapter);
        init();
        createTable();
        init_Dist_Qty();
		paymentShows();
		block_back_del_confirm();
		comma.setClickable(false);
		comma.setEnabled(false);
		comma.setBackgroundResource(R.drawable.bt_comma_disable);
		item_image.setBackgroundResource(R.drawable.item_image);
		
	}
	
	Items_Database db_items;
	
	OnItemClickListener itemClickListener = new OnItemClickListener() {
    	@SuppressLint("NewApi")
		@Override
    	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
    		/** Each item in the adapter is a HashMap object. 
    		 *  So this statement creates the currently clicked hashmap object
    		 * */
    		
    		@SuppressWarnings("unchecked")
			HashMap<String, String> hm = (HashMap<String, String>) arg0.getAdapter().getItem(position);
    		
    		itemcode_selected=hm.get(KEY_ITEM_CODE);
    		cms_id=hm.get(KEY_ITEM_CODE_ID);
    		
    		itemname_selected=hm.get(KEY_ITEM_NAME);
    		decs2=hm.get(KEY_DECS2);
    		decs3=hm.get(KEY_DECS3);
    		
    		item_discount=hm.get(KEY_ITEM_DISCOUNT_VALUE);
    		available_quantity=hm.get(KEY_ITEM_AVAILABLE_QUANTITY);
    		
    		group=hm.get(KEY_GROUP_ID);
    		item_size=hm.get(KEY_SIZE_ID);
    		color=hm.get(KEY_COLOR_ID);
    		
    		buying_price=hm.get(KEY_BUY_PRICE);
    		item_price=hm.get(KEY_ITEM_PRICE);//selling_price=hm.get(KEY_SELL_PRICE);
    		
    		supp_item_no=hm.get(KEY_SUPPLIER_ITEM_NO);
    		supp_no=hm.get(KEY_SUPPLIER_NO);
    		
    		barcode=hm.get(KEY_BARCODE);
    		tax_code=hm.get(KEY_TAX);
    		
    		reset_pincode(itemcode_selected, available_quantity);
    		//pincode.setText(itemcode_selected);
		}
    };
	
    private void setDisplay_value() {
    	//item_list.get(0).get(KEY_ITEM_ID)
    	itemcode_selected=    	item_list.get(0).get(KEY_ITEM_CODE);
		cms_id=item_list.get(0).get(KEY_ITEM_CODE_ID);
		
		itemname_selected=item_list.get(0).get(KEY_ITEM_NAME);
		decs2=item_list.get(0).get(KEY_DECS2);
		decs3=item_list.get(0).get(KEY_DECS3);
		
		item_discount=item_list.get(0).get(KEY_ITEM_DISCOUNT_VALUE);
		available_quantity=item_list.get(0).get(KEY_ITEM_AVAILABLE_QUANTITY);
		
		group=item_list.get(0).get(KEY_GROUP_ID);
		item_size=item_list.get(0).get(KEY_SIZE_ID);
		color=item_list.get(0).get(KEY_COLOR_ID);
		
		buying_price=item_list.get(0).get(KEY_BUY_PRICE);
		item_price=item_list.get(0).get(KEY_ITEM_PRICE);//selling_price=hm.get(KEY_SELL_PRICE);
		
		supp_item_no=item_list.get(0).get(KEY_SUPPLIER_ITEM_NO);
		supp_no=item_list.get(0).get(KEY_SUPPLIER_NO);
		
		barcode=item_list.get(0).get(KEY_BARCODE);
		tax_code=item_list.get(0).get(KEY_TAX);
		
		reset_pincode(itemcode_selected, available_quantity);
		pincode.setText("");
	}
    
    private void reset_pincode( String itemcode_selected, String available_quantity) {
    	System.out.println(available_quantity+"....available qty");
		
		linear.removeAllViews();
		linear.addView(inflateLayout);
		
		countr= false;
		//setTableVaules();
		int index_itemcode_avail=item_row_update_index(itemcode_selected, available_quantity);
		
		
		if(index_itemcode_avail== 0)
			setTableVaules();
		else if(index_itemcode_avail== -1)
		;
		else{
			System.out.println(index_itemcode_avail+"....index of order");
			String item_qty= order_items.get(index_itemcode_avail-1).get(KEY_ITEM_QUANTITY);
			int qty= Integer.parseInt(item_qty);
			qty=qty+1;
			
			//index_itemcode_avail++;
			
			System.out.println(index_itemcode_avail+"....index of table");
			setIncQty(index_itemcode_avail, ""+qty);
		}
			
		//block_back_del_confirm();
		/*
		confirm.setOnClickListener(this);
		confirm.setBackgroundResource(R.drawable.main_confirm);
		confirm.setEnabled(true);
		confirm.setClickable(true);
		*/
		init_Dist_Qty();
		paymentShows();
		
		
		//////////////////////////////////////////////
		countr= false;
		
		pincode.requestFocus();
		//System.out.println(position+"....item selected from adapter"+pincode.getText().toString());
		item_code= "";
		itemcode_selected="";
		itemname_selected="";
		item_price="";
		item_discount="";
		pincode.setAdapter(adapter);
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
		
		subtotal=		(TextView) findViewById(R.id.subtotal);
		t_discount=		(TextView) findViewById(R.id.discount_value);
		tax=			(TextView) findViewById(R.id.sale_tax);
		payment=		(TextView) findViewById(R.id.payment);
		payment_text=	(TextView) findViewById(R.id.payment_head);
		alternate=		(TextView) findViewById(R.id.alternate);
		alternate_text=		(TextView) findViewById(R.id.alternate_head);
		change=			(TextView) findViewById(R.id.change);
		total_payment=	(TextView) findViewById(R.id.total_payment);
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
        	
        	tr_head = new TableRow(MainActivity.this);
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
	//3rd
	@SuppressLint("ResourceAsColor")
	@SuppressWarnings("deprecation")
	private void setTableVaules() {
		
		
		System.out.println("current array list size..........."+order_items.size());
		counter_reset();
		int qty=1;
		
		if(row_selector>0)
			reset_highlight_Selected_Row(row_selector);
		if(salecounter<tl.getChildCount()-1){
			
			for(int i = 0; i < tl.getChildCount(); i++) {
				View child = tl.getChildAt(i);
				if (child instanceof TableRow) {
					TableRow row = (TableRow) child;
					
					for(int x = 0; x < 1; x++) {
						TextView t= (TextView) row.getChildAt(x);
						String r1=t.getText().toString();
						
						if(r1.equals("") && countr==false){
							tl.removeView(row);
							row = new TableRow(MainActivity.this);
							
							HashMap<String, String> map = new HashMap<String, String>();
							for(int col=0; col<KEY_COLUMN_LENGHT; col++){
			            		
								TextView text = new TextView(this);
								text.setHeight(trHeight);
			            		text.setTextSize(size-1);
			            		text.setTypeface(null, Typeface.BOLD);
			            		text.setPadding(5, 5, 5, 5);
			            		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
			            		
			            		if(i%2==1){
		            				text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select_pink));
		                    	}else{
		                    		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
		                    	}
			            		if(col==KEY_COLUMN_SR){
			            			text.setText(""+i);
			            			text.setTag("itemcode"+i);
			            			//text.setTag("itemcode"+i+"/"+trans_id);
			            			System.out.println("tag"+ text.getTag().toString());
			            			
			            			map.put("id", ""+i);
			            			map.put(KEY_ITEM_CODE_ID, ""+cms_id);
			            			
			            			
			            		}else if(col==KEY_COLUMN_ITEM_CODE){
			            			pincode.setText(itemcode_selected);
			            			row_selector=i;
			            			if(i%2==1){
			            				text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select_pink));
			                    	}else{
			                    		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
			                    	}
			            			text.setText(""+itemcode_selected);
			            			text.setTextColor(Color.parseColor("#ffffff"));
			            			text.setTypeface(null, Typeface.BOLD);
			            			//text.setTextSize(size-1);
			            			text.setTag("itemcode"+i);
			            			map.put(KEY_ITEM_CODE, itemcode_selected);
			            			
			            		}else if(col==KEY_COLUMN_ITEM_NAME){
			            			
			            			text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
			            			if(tab_returned_selected){
			            				String s=return_receipt.getText().toString();
			            				s= s.replace("#", "");
			            				String styledText = "<small>" +s + "</small>"+ 
			            						" "+itemname_selected ;
			            				text.setText(Html.fromHtml(styledText));
			            			}else 
			            				text.setText(""+itemname_selected);
			            			text.setTag("itemcode"+i );
			            			
			            			map.put(KEY_ITEM_NAME, itemname_selected);
			            			map.put(KEY_DECS2, decs2);
			            			map.put(KEY_DECS3, decs3);
			            			
			            			map.put(KEY_GROUP_ID, itemname_selected);
			            			map.put(KEY_SIZE_ID, itemname_selected);
			            			map.put(KEY_COLOR_ID, itemname_selected);
			            			
			            			map.put(KEY_BARCODE, barcode);
			            			map.put(KEY_TAX, tax_code);
			            			
			            			map.put(KEY_SUPPLIER_ITEM_NO, supp_item_no);
			            			map.put(KEY_SUPPLIER_NO, supp_no);
			            			
			            		}else if(col== KEY_COLUMN_RATE){
			            			
			            			map.put(KEY_ITEM_PRICE, item_price);
			            			map.put(KEY_BUY_PRICE, buying_price);
			            			
			            			text.setText(""+item_price.replace(".", ","));
			            			text.setTag("itemcode"+i);
			            			
			            		}else if(col== KEY_COLUMN_DIS){
			            			double dis= Double.parseDouble(item_discount);
			            			String item_disc= df.format(dis);
			            			item_disc= item_disc.replace(".", ",");
			            			text.setText(""+item_disc);
			            			text.setTag("discount"+i);
			            			System.out.println("tag"+ text.getTag().toString());
			            			
			            		}else if(col==KEY_COLUMN_QTY){
			            			if(tab_returned_selected){
			            				text.setText(""+item_quantity);
			            				qty=Integer.parseInt(item_quantity);
			            			}else
			            				text.setText("1");
			            			
			            			text.setTag("qty"+i);
			            			System.out.println("tag"+ text.getTag().toString());
			            			System.out.println(available_quantity+"....available qty");
			            			map.put(KEY_ITEM_QUANTITY ,"1");
			            			map.put(KEY_ITEM_AVAILABLE_QUANTITY, available_quantity);
			            			
			            		}else if(col==KEY_COLUMN_AMOUNT){
			            			String s=item_price;
			            			s= s.replace(",", ".");
			            			double a= Double.parseDouble(s);
			            			a= (Double.parseDouble(s) - Double.parseDouble(item_discount))*qty;
			            			String aa= df.format(a);
			            			aa= aa.replace(".", ",");
			            			if(tab_returned_selected)
			            				text.setText("-"+aa);
			            			else 
			            				text.setText(""+aa);
			            			
			            			System.out.println(aa+"....amount double");
			            			
			            			text.setTag(""+i);
			            			map.put(KEY_ITEM_SOLD_PRICE, ""+aa);
			            			
			            		}else if(col== KEY_COLUMN_DIS_TYP){
			            			
			            			text.setText(""+item_discount+"€");
			            			text.setTag("discount"+i);
			            			
			            			map.put(KEY_ITEM_DISCOUNT_VALUE,item_discount);
			            			map.put(KEY_ITEM_DISCOUNT_TYPE, "3");// NONE
			            		
			            		}
			            		text.setTextColor(Color.parseColor("#ffffff"));
			            		if(tab_returned_selected){
			            			if(col==KEY_COLUMN_QTY );
			    	        			//text.setOnClickListener(MainActivity.this);
			    	        		
			            		}else{
			            			if(col==KEY_COLUMN_ITEM_CODE|| col== KEY_COLUMN_ITEM_NAME ||col== KEY_COLUMN_RATE
			            					|| col==KEY_COLUMN_DIS || col==KEY_COLUMN_DIS_TYP || col==KEY_COLUMN_QTY
			    	        				||col==KEY_COLUMN_SR)
			    	        			text.setOnClickListener(MainActivity.this);
			    	        	}
			            		row.addView(text);
			    			}
							order_items.add(map);
							System.out.println("current array list size..........."+order_items.size());
							System.out.println("current array list size..........."+order_items.get(i-1).get(KEY_ITEM_CODE));
			            	tl.addView(row,i);
			            	salecounter++;
			            	countr= true;
			            }else{
			            	
			            }
			        }
			    }
			}
		}else{
			System.out.println("sale counter greater....."+ salecounter);
			TableRow row = new TableRow(MainActivity.this);
			HashMap<String, String> map = new HashMap<String, String>();
        	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
        		
        		TextView text = new TextView(this);
        		text.setTextSize(size-1);
        		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		text.setHeight(trHeight);
        		
        		if(salecounter%2==1){
    				text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select_pink));
            	}else{
            		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
            	}
        		
        		if(col==KEY_COLUMN_SR){
        			text.setText(""+(salecounter+1));
        			row_selector=(salecounter+1);
        			text.setTag("itemcode"+(salecounter+1));
        			map.put("id", ""+(salecounter+1));
        			map.put(KEY_ITEM_CODE_ID, ""+cms_id);
        			
        		}else if(col==KEY_COLUMN_ITEM_CODE){
        			text.setText(""+itemcode_selected);
        			text.setTag("itemcode"+(salecounter+1));
        			map.put(KEY_ITEM_CODE, itemcode_selected);
        			
        		}else if(col==KEY_COLUMN_ITEM_NAME){
        			text.setText(""+itemname_selected);
        			text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
        			text.setTag("itemcode"+(salecounter+1));
        			
        			map.put(KEY_ITEM_NAME, itemname_selected);
        			map.put(KEY_DECS2, decs2);
        			map.put(KEY_DECS3, decs3);
        			
        			map.put(KEY_GROUP_ID, itemname_selected);
        			map.put(KEY_SIZE_ID, itemname_selected);
        			map.put(KEY_COLOR_ID, itemname_selected);
        			
        			map.put(KEY_BARCODE, barcode);
        			map.put(KEY_TAX, tax_code);
        			
        			map.put(KEY_SUPPLIER_ITEM_NO, supp_item_no);
        			map.put(KEY_SUPPLIER_NO, supp_no);
        			
        		}else if(col==KEY_COLUMN_RATE){
        			text.setText(""+item_price.replace(".", ","));
        			text.setTag("itemcode"+(salecounter+1));
        			map.put(KEY_ITEM_PRICE, item_price);
        			map.put(KEY_BUY_PRICE, buying_price);
        			
        		}else if(col==KEY_COLUMN_DIS){
        			
        			double dis= Double.parseDouble(item_discount);
        			String item_disc= df.format(dis);
        			item_disc= item_disc.replace(".", ",");
        			text.setText(""+item_disc);
        			text.setTag("discount"+(salecounter+1));
        			
        		}else if(col==KEY_COLUMN_QTY){
        			text.setText("1");
        			text.setTag("qty"+(salecounter+1));
        			
        			map.put(KEY_ITEM_QUANTITY ,"1");
        			map.put(KEY_ITEM_AVAILABLE_QUANTITY, available_quantity);
        			
        		}else if(col==KEY_COLUMN_AMOUNT){
        			qty=1;
        			String s=item_price;
        			s= s.replace(",", ".");
        			double a= Double.parseDouble(s);
        			a= (Double.parseDouble(s) - Double.parseDouble(item_discount))*qty;
        			String aa= df.format(a);
        			aa= aa.replace(".", ",");
        			text.setText(""+aa);
        			text.setTag(""+(salecounter+1));
        			
        			map.put(KEY_ITEM_SOLD_PRICE, ""+aa);
        			
        		}else if(col==KEY_COLUMN_DIS_TYP){
        			text.setText(""+item_discount+"€");
        			text.setTag("discount"+(salecounter+1));
        			map.put(KEY_ITEM_DISCOUNT_TYPE, "3");
        			map.put(KEY_ITEM_DISCOUNT_VALUE,item_discount);
        			
        			
        			//text.setTag("discount"+(salecounter+1)+"/"+trans_id);
        		}
        		text.setTypeface(null, Typeface.BOLD);
        		text.setTextColor(Color.parseColor("#ffffff"));
        		
        		if(tab_returned_selected){
        			//System.out.println("tab selected....."+tab_returned_selected);
        			if(col==KEY_COLUMN_QTY );
	        			//text.setOnClickListener(MainActivity.this);
	        	}else{
        			if(col==KEY_COLUMN_ITEM_CODE|| col== KEY_COLUMN_ITEM_NAME ||col== KEY_COLUMN_RATE
        					|| col==KEY_COLUMN_DIS || col==KEY_COLUMN_DIS_TYP || col==KEY_COLUMN_QTY
	        				||col==KEY_COLUMN_SR)
	        			text.setOnClickListener(MainActivity.this);
	        	}
        		text.setPadding(5, 5, 5, 5);
        		row.addView(text);
        	}
        	order_items.add(map);
        	tl.addView(row );
        	salecounter++;
        	countr= true;
		}
		
		if(tab_returned_selected){
			//System.out.println(submit_amount);
			calculatePayment(submit_amount);
		}else{
			String s=item_price.replace(",", ".");
			double a= (Double.parseDouble(s) - Double.parseDouble(item_discount))*qty;
			subTotal(a);
		}
		
		item_code_selected= true;
		row_selector= salecounter;
		System.out.println("row selected..." +row_selector);
		deleteRowIndex= row_selector;
		
	}
	
	private int item_row_update_index( String itemcode, String available_qty) {
		int index=0;
		System.out.println("order itemsss sieze....."+ order_items.size());
		//
		String avail_qty="0";
		String row_qty="0";
		
		String row_id="";
		if(order_items.size()>0){
			for(int i=0; i<order_items.size(); i++){
				
				 String item_code= order_items.get(i).get(KEY_ITEM_CODE);
				 avail_qty=order_items.get(i).get(KEY_ITEM_AVAILABLE_QUANTITY);
				 row_qty=order_items.get(i).get(KEY_ITEM_QUANTITY);
				 
				 System.out.println("   item code"+ item_code+"\n"+ avail_qty+"\n"+ row_selector);
				 
				 if(item_code.trim().equals(itemcode.trim())){
					 int qty= Integer.parseInt(row_qty);
					 int availble_qty= Integer.parseInt(avail_qty);
					 
					 if(qty< availble_qty){
						 row_id= order_items.get(i).get("id");
						 index= Integer.parseInt(row_id);
					 }else if(qty== availble_qty){
						 index=-1;
					 }
					 
				 }
			}
		}
		System.out.println("index....."+ index+"   item code"+ itemcode+"    row id"+row_id+" available qty"+ avail_qty+"............."+ available_qty);
		return index;
	}
	
	// 4th
	private void subTotal(double sub_total) {
		
		
		System.out.println("sub toal amountt"+sub_total);
		
		String s=subtotal.getText().toString();
		s= s.replace(",", ".");
		double a= Double.parseDouble(s);
		sub_total= a+sub_total;
		//DecimalFormat df = new DecimalFormat("00.00");
		String aa= ""+df.format(sub_total);
		aa= aa.replace(".", ",");
		System.out.println("sub toal amountt in comma"+aa+"...."+ s);
		
		//t_discount.setText("000,00");
		double tax1= (sub_total *0.19);
		String t= ""+df.format(tax1);
		t=t.replace(".", ",");
		tax.setText(""+t);
		//payment.setText("000,00");
		//alternate.setText("000,00");
		//change.setText("000,00");
		totalAmount= sub_total;
		//float a1= Float.parseFloat(aa);
		subtotal.setText(""+aa); 
		total_payment.setText(""+aa);
		
	}
	//5th
	private void showQuantity(int index, String s_quantity, String available_qty) {
		
		counter_reset();
		linear.removeAllViews();
		linear.addView(inflateLayout);
		pincode.setFocusable(false);
		pincode.setFocusableInTouchMode(false);
		l_heading= (TextView) inflateLayout.findViewById(R.id.qty_discount_heading);
		l_heading.setText("Quantity");
		q_dec.setTag(""+index);
		q_inc.setTag(""+index);
		
		quantity.setText(""+s_quantity);
		quantity.requestFocus();
		
		if(s_quantity.equals("1")){
			q_dec.setBackgroundResource(R.drawable.qty_m_bt_disabe);
			q_dec.setEnabled(false);
			q_dec.setClickable(false);
		}else{
			q_dec.setBackgroundResource(R.drawable.qty_dec);
			q_dec.setOnClickListener(this);
		}
		
		if(available_qty.length()>0 && available_qty.equals("0")){
			System.out.println("availabkle qty is lesss"+ available_qty);
			q_inc.setEnabled(false);
			q_inc.setClickable(false);
			q_inc.setBackgroundResource(R.drawable.qty_p_bt_disabe);
			
		}else if(available_qty.length()>0 || !available_qty.equals("0")){
			
			System.out.println("availabkle qty is more"+ available_qty);
			q_inc.setEnabled(true);
			q_inc.setClickable(true);
			q_inc.setBackgroundResource(R.drawable.qty_inc);
			q_inc.setOnClickListener(this);
		}
		
		layout= (RelativeLayout) inflateLayout.findViewById(R.id.main1);
		layout.setVisibility(View.VISIBLE);
		
		qty_layout= (LinearLayout) inflateLayout.findViewById(R.id.qty_layout);
		qty_layout.setVisibility(View.VISIBLE);
		
		discount_layout= (LinearLayout) inflateLayout.findViewById(R.id.discount_layout);
		discount_layout.setVisibility(View.INVISIBLE);
		//int index = Arrays.asList(list).indexOf('e');
		//int index1= Arrays.asList(items_code).indexOf(itemcode);
		item_image.setVisibility(View.VISIBLE);
		//item_image.setBackgroundResource(imagearr[index1]);
		
	}
	//6th
	private void showDiscount(String index, String itemcode, String type, String typefull, String rowfeild) {
		
		counter_reset();
		
		linear.removeAllViews();
		linear.addView(inflateLayout);
		l_heading.setText("Discount");
		
		if(row_selector>0)
			reset_highlight_Selected_Row(row_selector);
		
		discount_tag_index=Integer.parseInt(index);
		row_selector= discount_tag_index;
		
		highlight_Selected_Row(row_selector, rowfeild);
		
		layout= (RelativeLayout) inflateLayout.findViewById(R.id.main1);
		layout.setVisibility(View.VISIBLE);
		
		discount_layout= (LinearLayout) inflateLayout.findViewById(R.id.discount_layout);
		discount_layout.setVisibility(View.VISIBLE);
		
		qty_layout= (LinearLayout) inflateLayout.findViewById(R.id.qty_layout);
		qty_layout.setVisibility(View.INVISIBLE);
		qty_layout.setVisibility(View.INVISIBLE);
		//int index1= Arrays.asList(items_code).indexOf(itemcode);
		
		item_image.setVisibility(View.VISIBLE);
		//item_image.setBackgroundResource(imagearr[index1]);
		
		if(type.equals("%")){
			discount_percent_selected= true;
			discount_amount_selected=false;
			pincode.setAdapter(null);
			d_percentage.setBackgroundResource(R.drawable.dis_perc_bt_sel);
			d_rupee.setBackgroundResource(R.drawable.dis_bt);
			pincode.setHint("");
			System.out.println(type+"..........."+typefull);
			
		}else{
			discount_percent_selected= false;
			discount_amount_selected=true;
			pincode.setAdapter(null);
			d_percentage.setBackgroundResource(R.drawable.dis_perc_bt);
			d_rupee.setBackgroundResource(R.drawable.dis_bt_sel);
			pincode.setHint("");
			System.out.println(type+"..........."+typefull);
		}
	}
	//7th
	private void init_Dist_Qty() {
		
		//counter_reset();
		l_heading= (TextView) inflateLayout.findViewById(R.id.qty_discount_heading);
		discount_layout= (LinearLayout) inflateLayout.findViewById(R.id.discount_layout);
		qty_layout= (LinearLayout) inflateLayout.findViewById(R.id.qty_layout);
		layout= (RelativeLayout) inflateLayout.findViewById(R.id.main1);
		
		qty_layout.setVisibility(View.INVISIBLE);
		discount_layout.setVisibility(View.INVISIBLE);
		layout.setVisibility(View.INVISIBLE);
		
		item_image=(ImageView) inflateLayout.findViewById(R.id.item_image);
		//item_image.setBackgroundResource(item_img);
		item_image.setVisibility(View.VISIBLE);
		item_image.setBackgroundResource(R.drawable.item_image);
		
		q_dec =(Button) inflateLayout.findViewById(R.id.qty_decrement);
		q_inc =(Button) inflateLayout.findViewById(R.id.qty_incre);
		
		d_percentage=(Button) inflateLayout.findViewById(R.id.d_perc);
		d_rupee=(Button) inflateLayout.findViewById(R.id.d_ruepee);
		
		quantity= (EditText) inflateLayout.findViewById(R.id.quantity);
		quantity.setText("1");
		quantity.setInputType(InputType.TYPE_NULL);
		
	//	quantity.setFocusableInTouchMode(true);
	//	quantity.setFocusable(true);
		
		q_dec.setOnClickListener(this);
		q_inc.setOnClickListener(this);
		d_percentage.setOnClickListener(this);
		d_rupee.setOnClickListener(this);
	}
	//8th
	@SuppressWarnings("deprecation")
	private void setIncQty(int index, String value){
		
		System.out.println("items orer sett"+ order_items.size()+"..........."+ index);
		System.out.println("items orer sett"+index+"...."+ order_items.get(index-1).get(KEY_ITEM_CODE));
		System.out.println("items orer sett"+ order_items.get(index-1).get("item_qty"));
		
		counter_reset();
		
		final float scale = getResources().getDisplayMetrics().density;
        int trHeight = (int) (34 * scale + 0.5f);
		View child = tl.getChildAt(index);
		
		HashMap<String, String> map = new HashMap<String, String>();
		if (child instanceof TableRow) {
			
			TableRow row = (TableRow) child;
			
			String item_code =((TextView) row.getChildAt(KEY_COLUMN_ITEM_CODE)).getText().toString();
			String qty1=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getText().toString();
			String dis =((TextView)row.getChildAt(KEY_COLUMN_DIS)).getText().toString();
			String item_discount=((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getText().toString();

			String qty_tag=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getTag().toString();
			String item_name_tag= ((TextView)row.getChildAt(KEY_COLUMN_ITEM_NAME)).getTag().toString();
			String discount_tag=((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getTag().toString();
			
			String item_name=((TextView) row.getChildAt(KEY_COLUMN_ITEM_NAME)).getText().toString();
			String item_price=((TextView) row.getChildAt(KEY_COLUMN_RATE)).getText().toString();
			
			String item_amount=((TextView) row.getChildAt(KEY_COLUMN_AMOUNT)).getText().toString();
			String amount_new_s="";
			// parse discount
			
			dis= dis.replace(",", ".");
			double dist= Double.parseDouble(dis);
			//parse price
			item_price=item_price.replace(",", ".");
			
			System.out.println(dis+"parse dis and price"+ item_price);
			
		//	int qty= (Integer.parseInt(value)+Integer.parseInt(qty1));
			int qty= (Integer.parseInt(value));
			double amount=((Double.parseDouble(item_price)- dist)*qty);
			
			
			//double subtot=(Double.parseDouble(item_price)- Double.parseDouble(dis));
			
			System.out.println(item_code+"..."+ item_name+"...."+ item_price+"...."+ dis+"...."+ qty+"..."+amount);
			
			tl.removeView(row);
			row = new TableRow(MainActivity.this);
			
			for(int col=0; col<KEY_COLUMN_LENGHT; col++){
        		
				TextView text = new TextView(this);
				text.setHeight(trHeight);
				text.setTextSize(size);
				text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
				text.setTextColor(Color.parseColor("#ffffff"));
    			text.setTypeface(null, Typeface.BOLD);
    			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
				
				
				/*
				if(index%2==1){
	    			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
	        	}else{
	        		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
	        	}
				*/
				if(col==KEY_COLUMN_SR){
					text.setText(""+index);
					text.setTag(""+item_name_tag);
					
					map.put("id", ""+index);
					map.put(KEY_ITEM_CODE_ID, ""+order_items.get(index-1).get(KEY_ITEM_CODE_ID));
					
				}else if(col==KEY_COLUMN_ITEM_CODE){
					text.setText(""+item_code);
					text.setTag(""+item_name_tag);
					
					map.put(KEY_ITEM_CODE, item_code);
					
				}else if(col==KEY_COLUMN_ITEM_NAME){
					text.setText(""+item_name);
					text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
					text.setTag(""+item_name_tag);
					
					
					map.put(KEY_ITEM_NAME, item_name);
        			map.put(KEY_DECS2, ""+order_items.get(index-1).get(KEY_DECS2));
        			map.put(KEY_DECS3, ""+order_items.get(index-1).get(KEY_DECS3));
        			
        			map.put(KEY_GROUP_ID, ""+order_items.get(index-1).get(KEY_GROUP_ID));
        			map.put(KEY_SIZE_ID, ""+order_items.get(index-1).get(KEY_SIZE_ID));
        			map.put(KEY_COLOR_ID, ""+order_items.get(index-1).get(KEY_COLOR_ID));
        			
        			map.put(KEY_BARCODE, ""+order_items.get(index-1).get(KEY_BARCODE));
        			map.put(KEY_TAX,  ""+order_items.get(index-1).get(KEY_TAX));
        			
        			map.put(KEY_SUPPLIER_ITEM_NO, ""+order_items.get(index-1).get(KEY_SUPPLIER_ITEM_NO));
        			map.put(KEY_SUPPLIER_NO, ""+order_items.get(index-1).get(KEY_SUPPLIER_NO));
					
					
				}else if(col==KEY_COLUMN_RATE){
					item_price=item_price.replace(".", ",");
					text.setText(""+item_price);
					text.setTag(""+item_name_tag);
					
					map.put(KEY_ITEM_PRICE, item_price);
        			map.put(KEY_BUY_PRICE, ""+order_items.get(index-1).get(KEY_BUY_PRICE));
        			
				}else if(col==KEY_COLUMN_DIS){
					dis= dis.replace(".", ",");
					text.setText(""+dis);
					text.setTag(""+discount_tag);
					
				}else if(col==KEY_COLUMN_QTY){
					/*
					if(index%2==1){
        				text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select_pink));
                	}else{
                		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
                	}*/
    				
					text.setText(""+qty);
        			text.setTypeface(null, Typeface.BOLD);
					
					text.setTag(""+qty_tag);
					map.put(KEY_ITEM_QUANTITY,""+qty);
        			map.put(KEY_ITEM_AVAILABLE_QUANTITY,  ""+order_items.get(index-1).get(KEY_ITEM_AVAILABLE_QUANTITY));
        			
				}else if(col==KEY_COLUMN_AMOUNT){
					
					amount_new_s= df.format(amount);
					amount_new_s=amount_new_s.replace(".", ",");
					text.setText(""+amount_new_s);
					text.setTag(""+item_name_tag);
					map.put(KEY_ITEM_SOLD_PRICE, ""+amount_new_s);
					
				}else if(col==KEY_COLUMN_DIS_TYP){
					text.setText(""+item_discount);
					text.setTag(""+discount_tag);
					
					String t= item_discount.substring(item_discount.length()-1, item_discount.length() );
					item_discount= item_discount.substring(0,item_discount.length()-1 );
					System.out.println(t+""+ item_discount);
					
					if(t.equals("%"))
						map.put(KEY_ITEM_DISCOUNT_TYPE, "2");// NONE
					else 
						map.put(KEY_ITEM_DISCOUNT_TYPE, "3");// NONE
					
					map.put(KEY_ITEM_DISCOUNT_VALUE,item_discount);
				}
				
				if(col==KEY_COLUMN_ITEM_CODE|| col== KEY_COLUMN_ITEM_NAME ||col== KEY_COLUMN_RATE
        				||col==KEY_COLUMN_SR || col==KEY_COLUMN_DIS || col==KEY_COLUMN_DIS_TYP || col==KEY_COLUMN_QTY)
					text.setOnClickListener(MainActivity.this);// 
				
				text.setPadding(5, 5, 5, 5);
				row.addView(text);
			}
			tl.addView(row,index );	
			
			String s=subtotal.getText().toString();
			s= s.replace(",", ".");
			double sub_total_d= Double.parseDouble(s);
			
			item_amount= item_amount.replace(",", ".");
			double old_amount=Double.parseDouble(item_amount);
			amount_new_s= amount_new_s.replace(",", ".");
			double new_amount=Double.parseDouble(amount_new_s);
			
			
			sub_total_d= sub_total_d-old_amount+new_amount;
			
			System.out.println("sub toal amountt in comma"+sub_total_d);
			String aa= ""+df.format(sub_total_d);
			
			//DecimalFormat df = new DecimalFormat("00.00");
			
			aa= aa.replace(".", ",");
			System.out.println("sub toal amountt in comma"+aa+"...."+ s);
			
			//t_discount.setText("000,00");
			double tax1= (sub_total_d *0.19);
			String t= ""+df.format(tax1);
			t=t.replace(".", ",");
			tax.setText(""+t);
			//payment.setText("000,00");
			//alternate.setText("000,00");
			//change.setText("000,00");
			totalAmount= sub_total_d;
			//float a1= Float.parseFloat(aa);
			subtotal.setText(""+aa); 
			total_payment.setText(""+aa);
			
			
		//	String aa= value.substring(0,1);
			
		//	if(aa.equals("-"))
				//subTotal(-(subtot));
		//	else	
				//subTotal(subtot);
			
			
			System.out.println("index after....."+ index);
			
			order_items.remove(index-1);
			
			order_items.add(index-1, map);
			
			
			System.out.println("map..........."+ map.toString());
			
			System.out.println("items orer sett..........."+ order_items.get(index-1).get(KEY_ITEM_NAME));
			System.out.println("items orer sett"+ order_items.get(index-1).get("item_qty"));
			System.out.println("total  order lenght"+ order_items.size());
		}
	}
	//9th
	@SuppressWarnings("deprecation")
	private void setDiscount(int index, String value , String dis_type) {
		
		counter_reset();
		
		String type= dis_type.substring(dis_type.length()-1,dis_type.length());
		System.out.println("type..................."+type+"............"+value);
		
		int percent=0;
		
		if(type.equals("%")){
			if(value.equals("")){
				value="0";
			}
			percent= Integer.parseInt(value);
		}
			
			
		if(percent>100){
			
			invalid_discount_Popup("Discount should be less \n then 100%.");
			
		}else{
			
			View child = tl.getChildAt(index);
			HashMap<String, String> map = new HashMap<String, String>();
			
			if (child instanceof TableRow) {
				TableRow row = (TableRow) child;
				
				String item_code =((TextView) row.getChildAt(KEY_COLUMN_ITEM_CODE)).getText().toString();
				pincode.setText(""+item_code);
				String qty1_row=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getText().toString();
				String item_name=((TextView) row.getChildAt(KEY_COLUMN_ITEM_NAME)).getText().toString();
				String item_price=((TextView) row.getChildAt(KEY_COLUMN_RATE)).getText().toString();
				
				String qty_tag=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getTag().toString();
				String item_name_tag= ((TextView)row.getChildAt(KEY_COLUMN_ITEM_NAME)).getTag().toString();
				String discount_tag=((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getTag().toString();
				String item_amount=((TextView) row.getChildAt(KEY_COLUMN_AMOUNT)).getText().toString();
				
				if(value.equals("")){
					value="0.00";
					dis_type="0"+dis_type;
				}
				
				
				value= value.replace(",", ".");
				item_price= item_price.replace(",", ".");
				double amount_ = 0; double item_price1 = 0;
				
				
				if(type.equals("€")){
					
					 item_price1 = Double.parseDouble(item_price);
					 amount_= Double.parseDouble(value);
				}
				if(amount_> item_price1)
						invalid_discount_Popup("Discount should be less \n then Item Price.");
					// pop up display
				else{
					
					String amount_new_s="";
					double item_discount = 0;
					
					
					if(discount_amount_selected){
						item_discount=(Double.parseDouble(value));
						
					}else if(discount_percent_selected){
						double  v= Double.parseDouble(value);
						double p= v/100;
						item_discount=  (Double.parseDouble(item_price)*p);
						System.out.println(p+""+discount_percent_selected+item_discount);
					}
					
					int qty_row= Integer.parseInt(qty1_row);
					double amount=((Double.parseDouble(item_price)- item_discount )*qty_row);
					
					System.out.println(item_code+"..."+ item_name+"...."+ item_price+"...."+ item_discount+"...."+ qty_row+"..."+amount);
					
					tl.removeView(row);
					row = new TableRow(MainActivity.this);
					comma.setClickable(false);
					comma.setEnabled(false);
					comma.setBackgroundResource(R.drawable.bt_comma_disable);
					
					
					for(int col=0; col<KEY_COLUMN_LENGHT; col++){
						
						TextView text = new TextView(this);
			    		text.setHeight(trHeight);
			    		text.setPadding(5, 5, 5, 5);
			    		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
			    		text.setTextSize(size-1);
		    			text.setTextColor(Color.parseColor("#ffffff"));
		    			text.setTypeface(null, Typeface.BOLD);
		    			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
						
		    			if(col==KEY_COLUMN_SR){
		    				text.setText(""+index);
							text.setTag(""+item_name_tag);
							
							map.put("id", ""+index);
							map.put(KEY_ITEM_CODE_ID, ""+order_items.get(index-1).get(KEY_ITEM_CODE_ID));
							
							
		        		}else if(col==KEY_COLUMN_ITEM_CODE){
		        			text.setText(""+item_code);
		        			text.setTag(""+item_name_tag);
		        			map.put(KEY_ITEM_CODE, item_code);
		        		}else if(col==KEY_COLUMN_ITEM_NAME){
							
		        			text.setText(""+item_name);
							text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
							text.setTag(""+item_name_tag);
							map.put(KEY_ITEM_NAME, item_name);
							
		        			map.put(KEY_DECS2, ""+order_items.get(index-1).get(KEY_DECS2));
		        			map.put(KEY_DECS3, ""+order_items.get(index-1).get(KEY_DECS3));
		        			
		        			map.put(KEY_GROUP_ID, ""+order_items.get(index-1).get(KEY_GROUP_ID));
		        			map.put(KEY_SIZE_ID, ""+order_items.get(index-1).get(KEY_SIZE_ID));
		        			map.put(KEY_COLOR_ID, ""+order_items.get(index-1).get(KEY_COLOR_ID));
		        			
		        			map.put(KEY_BARCODE, ""+order_items.get(index-1).get(KEY_BARCODE));
		        			map.put(KEY_TAX,  ""+order_items.get(index-1).get(KEY_TAX));
		        			
		        			map.put(KEY_SUPPLIER_ITEM_NO, ""+order_items.get(index-1).get(KEY_SUPPLIER_ITEM_NO));
		        			map.put(KEY_SUPPLIER_NO, ""+order_items.get(index-1).get(KEY_SUPPLIER_NO));
							
						}else if(col==KEY_COLUMN_RATE){
							
							item_price= item_price.replace(".", ",");
							text.setText(""+item_price);
							text.setTag(""+item_name_tag);
							map.put(KEY_ITEM_PRICE, item_price);
							map.put(KEY_BUY_PRICE, ""+order_items.get(index-1).get(KEY_BUY_PRICE));
		        			
						}else if(col==KEY_COLUMN_DIS){
							
							String a1= df.format(item_discount);
							a1=a1.replace(".", ",");
							text.setText(""+a1);
							text.setTag(""+discount_tag);
							
							
						}else if(col==KEY_COLUMN_QTY){
							
							text.setText(""+qty_row);
							text.setTag(""+qty_tag);
							map.put(KEY_ITEM_QUANTITY,""+qty_row);
		        			map.put(KEY_ITEM_AVAILABLE_QUANTITY,  ""+order_items.get(index-1).get(KEY_ITEM_AVAILABLE_QUANTITY));
		        			
						}else if(col==KEY_COLUMN_AMOUNT){
							
							amount_new_s= df.format(amount);
							
							amount_new_s=amount_new_s.replace(".", ",");
							text.setText(""+amount_new_s);
							
							text.setTag(""+item_name_tag);
							//text.setTextColor(Color.parseColor("#800080"));
							
							map.put(KEY_ITEM_SOLD_PRICE, ""+amount_new_s);
							
						}else if(col== KEY_COLUMN_DIS_TYP){
							text.setText(""+dis_type);
							text.setTag(""+discount_tag);
							map.put(KEY_ITEM_DISCOUNT_TYPE, ""+dis_type);
							

							String t= dis_type.substring(dis_type.length()-1, dis_type.length() );
							dis_type= dis_type.substring(0,dis_type.length()-1 );
							
							System.out.println(t+""+ dis_type);
							
							if(t.equals("%"))
								map.put(KEY_ITEM_DISCOUNT_TYPE, "2");// NONE
							else 
								map.put(KEY_ITEM_DISCOUNT_TYPE, "3");// NONE
							
							map.put(KEY_ITEM_DISCOUNT_VALUE,dis_type);
						}
		    			
						if(col==KEY_COLUMN_ITEM_CODE|| col== KEY_COLUMN_ITEM_NAME ||col== KEY_COLUMN_RATE
		        				||col==KEY_COLUMN_SR || col==KEY_COLUMN_DIS || col==KEY_COLUMN_DIS_TYP|| col==KEY_COLUMN_QTY)
							text.setOnClickListener(MainActivity.this);
						
						text.setPadding(5, 5, 5, 5);
						row.addView(text);
					}
					
					tl.addView(row,index );
					order_items.remove(index-1);
					order_items.add(index-1, map);
					
					String s=subtotal.getText().toString();
					s= s.replace(",", ".");
					double sub_total_d= Double.parseDouble(s);
					
					item_amount= item_amount.replace(",", ".");
					double old_amount=Double.parseDouble(item_amount);
					amount_new_s= amount_new_s.replace(",", ".");
					double new_amount=Double.parseDouble(amount_new_s);
					
					
					sub_total_d= sub_total_d-old_amount+new_amount;
					
					System.out.println("sub toal amountt in comma"+sub_total_d);
					String aa= ""+df.format(sub_total_d);
					
					//DecimalFormat df = new DecimalFormat("00.00");
					
					aa= aa.replace(".", ",");
					System.out.println("sub toal amountt in comma"+aa+"...."+ s);
					
					//t_discount.setText("000,00");
					double tax1= (sub_total_d *0.19);
					String t= ""+df.format(tax1);
					t=t.replace(".", ",");
					tax.setText(""+t);
					//payment.setText("000,00");
					//alternate.setText("000,00");
					//change.setText("000,00");
					totalAmount= sub_total_d;
					//float a1= Float.parseFloat(aa);
					subtotal.setText(""+aa); 
					total_payment.setText(""+aa);
					
					
					System.out.println("sub toal amountt"+item_discount);
					
					
					discount_amount_selected= false;
					discount_percent_selected= false;
					layout.setVisibility(View.INVISIBLE);
					
					countr= false;
					item_code= "";
					submit_t="";
					pincode.setText("");
					pincode.setAdapter(adapter);
				}
				
			}
		}
		
	}
	//10th
	
	@SuppressWarnings("deprecation")
	private void deleteItemFromTable(int deleteRowIndex) {
		counter_reset();
		int countrow=0;
		System.out.println(deleteRowIndex+"del and counter........."+ salecounter);
		
		if(deleteRowIndex>0){
			View child = tl.getChildAt(deleteRowIndex);
			TableRow row = (TableRow) child;
			String itemcode = "";
			
			if(child instanceof TableRow) {
				
				String old_total= ((TextView) row.getChildAt(KEY_COLUMN_AMOUNT)).getText().toString();
				tl.removeView(row);
				System.out.println("before del size;....."+(deleteRowIndex-1)+"........."+ order_items.size());
				order_items.remove(deleteRowIndex-1);
				System.out.println("after del size....."+(deleteRowIndex-1)+"........."+ order_items.size());
				
				salecounter--;
				for(int i=deleteRowIndex; i <tl.getChildCount(); i++){
					
					System.out.println("count of rowsss....."+salecounter+"........."+ i);
					View child1 = tl.getChildAt(i);
					TableRow row1 = (TableRow) child1;
					TextView t= (TextView) row1.getChildAt(KEY_COLUMN_SR);
					String r1=t.getText().toString();
					
					if(r1.length()>0){
						
						String srNo		= ((TextView) row1.getChildAt(KEY_COLUMN_SR)).getText().toString();
						itemcode		=((TextView) row1.getChildAt(KEY_COLUMN_ITEM_CODE)).getText().toString();
						String item_name=((TextView) row1.getChildAt(KEY_COLUMN_ITEM_NAME)).getText().toString();
						String rate 	=((TextView) row1.getChildAt(KEY_COLUMN_RATE)).getText().toString();
						String dis		=((TextView) row1.getChildAt(KEY_COLUMN_DIS)).getText().toString();
						String qty		=((TextView) row1.getChildAt(KEY_COLUMN_QTY)).getText().toString();
						String amount	=((TextView) row1.getChildAt(KEY_COLUMN_AMOUNT)).getText().toString();
						String item_discount=((TextView)row1.getChildAt(KEY_COLUMN_DIS_TYP)).getText().toString();
						
						System.out.println(" dis type......"+item_discount);
						
						tl.removeView(row1);
						countrow= Integer.parseInt(srNo)-1;
						row1 = new TableRow(MainActivity.this);
		            	
		            	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
		            		
		            		TextView text = new TextView(this);
		            		text.setHeight(trHeight);
		            		
		            		if(salecounter== i){
		            			
		        	    		text.setPadding(5, 5, 5, 5);
		        	    		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		        	    		text.setTextSize(size-1);
		            			text.setTextColor(Color.parseColor("#ffffff"));
		            			text.setTypeface(null, Typeface.BOLD);
		            			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
		            			
		    					row_selector= salecounter;
		    					deleteRowIndex= row_selector;
		    					item_code_selected= true;
		    					
		    					System.out.println(" on delete selector......"+row_selector);
								
		            		}else {
		            			text.setTextColor(Color.parseColor("#800080"));
			            		text.setTextSize(size);
			            		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
			            		
			            		if(countrow%2==1){
			            			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
			    	        	}else{
			    	        		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
			    	        	}
		            		}
		            		
		            		if(col==KEY_COLUMN_SR){
		            			text.setText(""+countrow);
		            			text.setTag("itemcode"+countrow);
		            		
		            		}else if(col==KEY_COLUMN_ITEM_CODE){
		            			text.setText(""+itemcode);
		            			
		            			text.setTag("itemcode"+countrow);
		            			
		            		}else if(col==KEY_COLUMN_ITEM_NAME){
		            			text.setText(""+item_name);
		            			text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
		            			text.setTag("itemcode"+countrow);
		            			
		            		}else if(col==KEY_COLUMN_RATE){
		            			
		            			text.setText(""+rate);
		            			text.setTag("itemcode"+countrow);
		            			
		            		}else if(col==KEY_COLUMN_DIS){
		            			text.setText(""+dis);
		            			text.setTag("discount"+i);
		            			
		            		}else if(col==KEY_COLUMN_QTY){
		            			text.setText(""+ qty);
		            			text.setTag("qty"+i);
		            			
		            		}else if(col==KEY_COLUMN_AMOUNT){
		            			text.setText(""+amount);
		            			text.setTag(""+i);
		            			
		            		}else if(col==KEY_COLUMN_DIS_TYP){
		            			text.setText(""+item_discount);
		            			text.setTag("discount"+i);
		            		}
		            		if(col==KEY_COLUMN_ITEM_CODE|| col== KEY_COLUMN_ITEM_NAME ||col== KEY_COLUMN_RATE
		            				 ||col==KEY_COLUMN_SR || col==KEY_COLUMN_DIS  || col==KEY_COLUMN_DIS_TYP|| col==KEY_COLUMN_QTY)
		            			text.setOnClickListener(MainActivity.this);//
		            		
		            		text.setPadding(5, 5, 5, 5);
		            		row1.addView(text);
		    			}
		            	tl.addView(row1,i );
					}else{
						
						String srNo= ((TextView) row1.getChildAt(KEY_COLUMN_SR)).getTag().toString();
						tl.removeView(row1);
						countrow= Integer.parseInt(srNo)-1;
						//System.out.println("empty cell....."+countrow);
						
						row1 = new TableRow(MainActivity.this);
		            	///row1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		    	 		
		            	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
		            		
		            		TextView text = new TextView(this);
		            		text.setTextSize(size);
		            		text.setTextColor(Color.parseColor("#800080"));
		            		text.setHeight(trHeight);
		            		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		            		
		            		if(countrow%2==1){
		            			//System.out.println(" pink......"+countrow%2);
		    	    			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
		    	        	}else{
		    	        		//System.out.println("white....."+countrow%2);
		    	        		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
		    	        	}
		            		if(col==KEY_COLUMN_ITEM_CODE){ 
		            			text.setText("0987654321");
		            			text.setTextColor(Color.TRANSPARENT);
		            			text.setTag(""+countrow);
		            			
		            		}else if(col==KEY_COLUMN_DIS_TYP){
		            			text.setText("198%");
		            			text.setTextColor(Color.TRANSPARENT);
		            			text.setTag(""+countrow);
		            			
		            		}else if(col==KEY_COLUMN_DIS){
		            			text.setText("198.00");
		            			text.setTextColor(Color.TRANSPARENT);
		            			text.setTag(""+countrow);
		            			
		            		}else if(col==KEY_COLUMN_QTY){
		            			text.setText("123");
		            			text.setTextColor(Color.TRANSPARENT);
		            			text.setTag(""+countrow);
		            			
		            		}else if(col==KEY_COLUMN_RATE|| col== KEY_COLUMN_AMOUNT){
		            			text.setText("1000,00");
		            			text.setTextColor(Color.TRANSPARENT);
		            			text.setTag(""+countrow);
		            			
		            		}else if(col==KEY_COLUMN_ITEM_NAME){
		            			text.setText("");
		            			text.setTextColor(Color.TRANSPARENT);
		            			text.setTag(""+countrow);
		            			
		            		}else{
		            			text.setText("");
		            			text.setTextColor(Color.parseColor("#800080"));
		                		text.setTag(""+countrow);
		            		}
		            		text.setPadding(5, 5, 5, 5);
		            		row1.addView(text);
		    			}
		            	tl.addView(row1,i );
					}
				}
				
				System.out.println("count of table....."+tl.getChildCount()+ salecounter);
				if(tl.getChildCount()==13){
					for(int i=tl.getChildCount(); i<14; i++){
						row = new TableRow(MainActivity.this);
		            	
		    	 		for(int col=0; col<KEY_COLUMN_LENGHT; col++){
		            		
		            		TextView text = new TextView(this);
		            		text.setHeight(trHeight);
		            		text.setTextSize(size);
		            		text.setTextColor(Color.parseColor("#800080"));
		            		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		            		if(i%2==1){
		    	    			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
		    	        	}else{
		    	        		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
		    	        	}
		            		if(col==KEY_COLUMN_SR){
		            			text.setText("");
		            			text.setTag(""+i);
		            		}else if(col==KEY_COLUMN_ITEM_CODE){
		            			text.setText("");
		            			text.setTag(""+i);
		            		}else if(col==KEY_COLUMN_ITEM_NAME){
		            			text.setText("");
		            			text.setTag(""+i);
		            		}else if(col==KEY_COLUMN_RATE){
		            			
		            			text.setText("");
		            			text.setTag(""+i);
		            		}else if(col==KEY_COLUMN_DIS_TYP){
		            			
		            			text.setText("");
		            			text.setTag(""+i);
		            		}else if(col==KEY_COLUMN_DIS){
		            			text.setText("");
		            			
		            			text.setTag(""+i);
		            		}else if(col==KEY_COLUMN_QTY){
		            			
		            			text.setText("");
		            			text.setTag(""+i);
		            		}else if(col==KEY_COLUMN_AMOUNT){
		            			text.setText("");
		            			text.setTag(""+i);
		            		}
		            		text.setTextColor(Color.parseColor("#800080")); 
		            		
		            		text.setPadding(5, 5, 5, 5);
		            		row.addView(text);
		    			}
		            	tl.addView(row,i );
					}
				}
				
				if(tab_returned_selected){
					System.out.println(submit_amount);
					calculatePayment(submit_amount);
					
				}else{
					old_total= old_total.replace(",", ".");
					double old= Double.parseDouble(old_total);
					subTotal(-old);
					
					if(itemcode.equals("")){
						//if deleted is last row
						//pincode.setText(itemcode);
						System.out.println("nothing left"+ salecounter);
						row_selector= salecounter;
						System.out.println("last row of the table after delete......"+row_selector);
						item_image.setVisibility(View.INVISIBLE);
						if(salecounter>0){
							highlight_Selected_Row( row_selector, "itemcode");
						}
					}else{
						//int index1= Arrays.asList(items_code).indexOf(itemcode);
						//item_image.setBackgroundResource(imagearr[index1]);
						//pincode.setText(itemcode);
					}
				}
			}
		}
		//item_code_selected=false;
	}
	//11th
	private void paymentShows() {
		
		pincode.setText("");
		cashLayout= (LinearLayout) layoutPayment.findViewById(R.id.main1);
		cardLayout= (LinearLayout) layoutPayment.findViewById(R.id.main2);
		voucher_layout= (LinearLayout) layoutPayment.findViewById(R.id.main3);
		returned_layout= (LinearLayout) layoutPayment.findViewById(R.id.main4);
		
		cashLayout.setVisibility(View.INVISIBLE);
		cardLayout.setVisibility(View.INVISIBLE);
		voucher_layout.setVisibility(View.INVISIBLE);
		returned_layout.setVisibility(View.INVISIBLE);
		
		tab_cash =(Button) layoutPayment.findViewById(R.id.cash);
		tab_card =(Button) layoutPayment.findViewById(R.id.credit);
		tab_giftvoucher =(Button) layoutPayment.findViewById(R.id.gift_voucher);
		tab_returned =(Button) layoutPayment.findViewById(R.id.return_cash);
		
		
		tab_cash.setBackgroundResource(R.drawable.cash);
//		tab_card.setBackgroundResource(R.drawable.credit_card);
//		tab_giftvoucher.setBackgroundResource(R.drawable.gift_voucher);
//		tab_returned.setBackgroundResource(R.drawable.returned);
		
		cash_05 =(Button) layoutPayment.findViewById(R.id.cash_5);
		cash_10 =(Button) layoutPayment.findViewById(R.id.cash_10);
		cash_20 =(Button) layoutPayment.findViewById(R.id.cash_20);
		
		cash_50  =(Button) layoutPayment.findViewById(R.id.cash_50);
		cash_100 =(Button) layoutPayment.findViewById(R.id.cash_100);
		cash_200 =(Button) layoutPayment.findViewById(R.id.cash_200);
		
		visa =(Button) layoutPayment.findViewById(R.id.visa);
		solo  =(Button) layoutPayment.findViewById(R.id.solo);
		delta  =(Button) layoutPayment.findViewById(R.id.delta);
		maestro =(Button) layoutPayment.findViewById(R.id.maestro);
		mastercard =(Button) layoutPayment.findViewById(R.id.master_card);
		visaelectron =(Button) layoutPayment.findViewById(R.id.electron);
		
		card_v1=(Button) layoutPayment.findViewById(R.id.cash4);
		card_v2=(Button) layoutPayment.findViewById(R.id.cash5);
		card_v3=(Button) layoutPayment.findViewById(R.id.cash6);
		
		cash_v1=(Button) layoutPayment.findViewById(R.id.cash1);
		cash_v2=(Button) layoutPayment.findViewById(R.id.cash2);
		cash_v3=(Button) layoutPayment.findViewById(R.id.cash3);
		
		
		tab_cash.setOnClickListener(this);
		//tab_card.setOnClickListener(this);
	//	tab_giftvoucher.setOnClickListener(this);
	//	tab_returned.setOnClickListener(this);
		
		cash_05.setOnClickListener(this);
		cash_10.setOnClickListener(this);
		cash_20.setOnClickListener(this);
		cash_50.setOnClickListener(this);
		cash_100.setOnClickListener(this);
		cash_200.setOnClickListener(this);
		
		visa.setOnClickListener(this);
		solo.setOnClickListener(this);
		delta.setOnClickListener(this);
		maestro.setOnClickListener(this);
		mastercard.setOnClickListener(this);
		visaelectron.setOnClickListener(this);
		
		card_v1.setOnClickListener(this);
		card_v2.setOnClickListener(this);
		card_v3.setOnClickListener(this);
		
		cash_v1.setOnClickListener(this);
		cash_v2.setOnClickListener(this);
		cash_v3.setOnClickListener(this);
		////////////////////voucher
		
		r_total= (TableRow) voucher_layout.findViewById(R.id.row_1);
		r_remaining= (TableRow) voucher_layout.findViewById(R.id.row_2); 
		voucher_total= (TextView) voucher_layout.findViewById(R.id.row_12);
		voucher_remaining = (TextView) voucher_layout.findViewById(R.id.row_22);
		voucher_heading= (TextView) voucher_layout.findViewById(R.id.voucher_heading);
		
		tl_return = (TableLayout) returned_layout.findViewById(R.id.return_items);
		return_receipt=(TextView) returned_layout.findViewById(R.id.receipt_heading);
		
	}
	//12th
	private void showcash() {
		int inc=0;
		if(totalAmount>0.00){ 
			
			String firstdigitS="";
			String a= ""+totalAmount;
			int i= a.indexOf(".");
			//String hundereds="1";
			if(i==1){
				
			}else{
				if(i==4){
					firstdigitS= a.substring(0,2);
					
					firstdigitS=firstdigitS+"00";
					//System.out.println("zerooos........"+ firstdigitS);
					inc=100;
					//second_digitS= a.substring(1,2);
				}else if(i==3){
					firstdigitS= a.substring(0,1);
					firstdigitS=firstdigitS+"00";
					//System.out.println("zerooos........"+ firstdigitS);
					inc=100;
				}else if(i==5){
					firstdigitS= a.substring(0,3);
					firstdigitS=firstdigitS+"00";
					inc=100;
				}else if(i==6){
					firstdigitS= a.substring(0,4);
					firstdigitS=firstdigitS+"00";
					inc=100;
				}else if(i==7){
					firstdigitS= a.substring(0,5);
					firstdigitS=firstdigitS+"00";
					inc=100;
				}else if(i==2){
					
					firstdigitS= a.substring(0,1);
					firstdigitS=firstdigitS+"0";
					inc=10;
					//System.out.println("zerooos........"+ firstdigitS);
				}
				
				double firstdigit=0.00;
				if(a.substring(i-2, i).equals("00"))
					firstdigit= Integer.parseInt(firstdigitS);
				else
					firstdigit= Integer.parseInt(firstdigitS)+inc;
				
				double c1= firstdigit;
				
				firstdigit = (firstdigit+inc);
				
				double c2 = firstdigit;
				firstdigit = firstdigit+inc;
				double c3 = firstdigit;
				
				String s= (df.format(c1)).replace(".", ",");
				card_v1.setText(""+s);
				
				s= (df.format(c2)).replace(".", ",");
				card_v2.setText(""+s);
				
				s= (df.format(c3)).replace(".", ",");
				card_v3.setText(""+s);
				
				s= (df.format(c1)).replace(".", ",");
				cash_v1.setText(""+s);
				
				s= (df.format(c2)).replace(".", ",");
				cash_v2.setText(""+s);
				
				s= (df.format(c3)).replace(".", ",");
				cash_v3.setText(""+s);
			}
			
			
		}
	}
	//13th
	private void calculatePayment(double pay) {
		System.out.println(pay);
		double remain=  pay-totalAmount;
		String f1= df.format(pay);
		f1= f1.replace(".", ",");
		
		if(paymentAlternate.equals("")){
			payment.setText(""+f1);
			alternate.setText("0,00");
		}else{
			
			String p= payment.getText().toString();
			p= p.replace(",", ".");
			double p1= Double.parseDouble(p);
			f1= ""+(pay-p1);
			f1= f1.replace(".", ",");
			alternate.setText(""+f1);
		}
	/*	
		if(remain>0){
			String f= df.format(remain);
			f= f.replace(".", ",");
			change.setText(""+f);
			if(r_remaining.getVisibility() == View.VISIBLE)
				voucher_remaining.setText(""+f);
		}else{
			change.setText("0,00");
			if(r_remaining.getVisibility() == View.VISIBLE)
				voucher_remaining.setText("0,00");
		}
		*/
		String f= df.format(remain);
		f= f.replace(".", ",");
		change.setText(""+f);
		if(r_remaining.getVisibility() == View.VISIBLE)
			voucher_remaining.setText(""+f);
	}
	//14th
	private void lessPaymentPaid() {
		counter_reset();
		final AlertDialog  dialog = new AlertDialog.Builder(this).create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
		
		dialog.setContentView(R.layout.popup_less_payment);
		Button dialogButton = (Button) dialog.findViewById(R.id.pay);
		
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pincode.setText("");
				pincode.setHint("Select any tab");
				item_code="";
				pincode.setAdapter(null);
				submit_t="";
				linear.removeAllViews();
				qty_layout.setVisibility(View.INVISIBLE);
        		layout.setVisibility(View.INVISIBLE);
        		discount_layout.setVisibility(View.INVISIBLE);
        		
        		linear.addView(layoutPayment);
        		paymentShows();
        		paymentAlternate="Alternate";
				dialog.dismiss();
				
				
			}
		});
		
		Button confirm = (Button)dialog.findViewById(R.id.cancel);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}});
	}
	//15th
	private void initPaymentPaid() {
		payment_1=payment.getText().toString();
		payment_2=alternate.getText().toString();

		OrderConfirmed submit_order= new OrderConfirmed(context, order_items, order_id, 
				subtotal.getText().toString(), total_payment.getText().toString() , "0", "1",
				payment_type1, payment_1, payment_type2, payment_2);
		
		submit_order.createReceipt();
		
		counter_reset();
		paid_confirmed=true;
		
		linear.removeAllViews();
		linear.addView(layoutPaymentPaid);
		pincode.setText("");
		pincode.setHint("");
		pincode.setFocusable(false);
		pincode.setFocusableInTouchMode(false);
		paid_Layout= (LinearLayout) layoutPaymentPaid.findViewById(R.id.main1);
		paid_withchange_Layout= (LinearLayout) layoutPaymentPaid.findViewById(R.id.main2);
		TextView paymnt_t= (TextView) layoutPaymentPaid.findViewById(R.id.paid_ammount1);
		TextView pay_t= (TextView) layoutPaymentPaid.findViewById(R.id.paid_ammount);
		TextView change_t= (TextView) layoutPaymentPaid.findViewById(R.id.remaining_ammount);
		
		double c=  submit_amount-totalAmount;
		String s=df.format(c);
		s= s.replace(".", ",");
		String p= df.format(submit_amount);
		p= p.replace(".", ",");
		
		if(c>0){
			paid_withchange_Layout.setVisibility(View.INVISIBLE);
			paid_Layout.setVisibility(View.VISIBLE);
			pay_t.setText(""+p);
			if(r_remaining.getVisibility()== View.VISIBLE)
				change_t.setText("Remaining Balance : "+s);
			else 
				change_t.setText("Change : "+s);
		}else{
			paid_withchange_Layout.setVisibility(View.VISIBLE);
			paid_Layout.setVisibility(View.INVISIBLE);
			paymnt_t.setText(""+p);
		}
		
		
	/*	if(timerHasStarted) {
			countDownTimer.cancel();
			new CountDownTimer(10 * 1000, interval) {
				public void onTick(long millisUntilFinished) {
					System.out.println("payment finish counter");
				}
				public void onFinish() {
					
					Intent i= new Intent(MainActivity.this,HeadTabActivityAdmin.class);
					finish();
					startActivity(i);
					
				
					
				}
			}.start();
		}*/
		
		
		
		
		
		
	}
	// 16th
	private void initVoucher() {
		counter_reset();
		
		if(r_total.getVisibility()== View.INVISIBLE){
			r_total.setVisibility(View.VISIBLE);
			voucher_heading.setVisibility(View.VISIBLE);
			String a=pincode.getText().toString();
			Voucher v= new Voucher(a);
			String p= v.getVoucher_payment();
			voucher_total.setText(""+ p);
		
		}else{
			r_remaining.setVisibility(View.VISIBLE);
			String p= voucher_total.getText().toString();
			submit_t=p;
			submit_amount= Double.parseDouble(p);
			calculatePayment(submit_amount);
		}
	}
	//17th
	@SuppressWarnings("deprecation")
	private void setReturnTable(){
		
		counter_reset();
		pincode.setFocusable(true);
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
		
        tl_return.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
        
        for(int r=1; r<5; r++){
        	
        	tr_head = new TableRow(MainActivity.this);
        	tr_head.setId(r);
        	tr_head.setTag(r);
	 		
        	for(int col=0; col<5; col++){
        		
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
        		if(col== 1){
        			text.setText("0987654321");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==3){
        			text.setText("123");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col== 4){
        			text.setText("1000,00");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==2){
        			text.setText("nnnnnnnnnn");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else{
        			text.setText("");
        			text.setTextColor(Color.parseColor("#800080"));
            		text.setTag(""+r);
        		}
        		
        		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		tr_head.addView(text);
			}
        	tl_return.addView(tr_head);
  	 	}
	
	}
	//18th
	private void resetPaymentTabs() {
		tab_voucher_selected=false;
		tab_returned_selected=false;
		tab_card_selected= false;
		tab_cash_selected= false;
		
		
		tab_cash.setBackgroundResource(R.drawable.cash);
		//tab_card.setBackgroundResource(R.drawable.credit_card);
		//tab_giftvoucher.setBackgroundResource(R.drawable.gift_voucher);
		//tab_returned.setBackgroundResource(R.drawable.returned);
		
		cashLayout.setVisibility(View.INVISIBLE);
		cardLayout.setVisibility(View.INVISIBLE);
		voucher_layout.setVisibility(View.INVISIBLE);
		returned_layout.setVisibility(View.INVISIBLE);
		
		r_remaining.setVisibility(View.INVISIBLE);
		r_total.setVisibility(View.INVISIBLE);
		voucher_heading.setVisibility(View.INVISIBLE);
		return_receipt.setText("Receipt# 0000");
	}
	//..19th
	/*
	@SuppressWarnings("deprecation")
	private void setReturnTableValues(String index[]) {
		
		counter_reset();
		int qty=1;
		int l=tl_return.getChildCount()-1;
		tl_return.removeViews(1, l);
		
		for(int i=1; i<index.length+1; i++){
			TableRow row = new TableRow(MainActivity.this);
			
			for(int col=0; col<5; col++){
        		if(col== KEY_COLUMN_SR){
        			CheckBox sr1= new CheckBox(this);
        			sr1.setHeight(trHeight);
            		sr1.setTextSize(size);
            		sr1.setPadding(5, 5, 5, 5);
            		sr1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
            		
        			sr1.setText(""+i);
        			
        			if(i%2==1){
            			sr1.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
                	}else{
                		sr1.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
                	}
        			sr1.setTextColor(Color.parseColor("#800080"));
        			final int in=i;
        			sr1.setOnClickListener(new OnClickListener() {
        				@Override
        				public void onClick(View v) {
        					//is chkIos checked?
        					if (((CheckBox) v).isChecked()) {
        						itemcode_selected= 	items_code [in];
        						itemname_selected= 	items_name [in];
        						item_price=			items_price[in];
        						item_discount=		items_discount[in];
        						item_quantity= ""+in;
        						countr= false;
        						
        						
        						submit_t= items_price[in];
        						submit_t= submit_t.replace(",", ".");
		            			double a= Double.parseDouble(submit_t);
		            			
		            			a= (Double.parseDouble(submit_t) - Double.parseDouble(item_discount))*in;
		            			String aa= df.format(a);
		            			aa= aa.replace(".", ",");
		            			System.out.println("calculated amount "+a);
		            			
        						submit_amount= submit_amount+ a;
        						setTableVaules();
        						System.out.println("quantity...."+ item_quantity);
        						System.out.println(submit_t+"amount payment...."+itemcode_selected+".. code,,,,name"+ itemname_selected+"...price......."+ item_price+"  .... discount....."+ item_discount);
        					}else{
        						itemcode_selected= 	items_code [in];
        						System.out.println("amount payment...."+itemcode_selected);
        						returnRowIndex(itemcode_selected);
        					}
        				}
        			});
        			row.addView(sr1);
        			
        		}else{
        			
        			TextView text = new TextView(this);
            		text.setHeight(trHeight);
            		text.setTextSize(size);
            		text.setPadding(5, 5, 5, 5);
            		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
            		
        			if(i%2==1){
            			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
                	}else{
                		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
                	}
            		
            		if(col==KEY_COLUMN_ITEM_CODE){
            			text.setText(""+items_code[i]);
            			
            		}else if(col==KEY_COLUMN_ITEM_NAME){
            			text.setText(""+items_name[i]);
            			text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
            		}else if(col==3){
            			
            			text.setText(""+i);
            			item_discount=items_discount[i];
            			//double dis= Double.parseDouble(item_discount);
            			qty= i;
            			
            		}else if(col==4){
            			
            			String s=items_price[i];
            			s= s.replace(",", ".");
            			double a= Double.parseDouble(s);
            			a= (Double.parseDouble(s) - Double.parseDouble(item_discount))*qty;
            			String aa= df.format(a);
            			aa= aa.replace(".", ",");
            			text.setText(""+aa);
            		}
            		text.setTextColor(Color.parseColor("#800080")); 
            		row.addView(text);
        		}
        	}
			
			tl_return.addView(row );
		}
		
	}
	
	*/
	// 20th 
	private void returnRowIndex(String itemcode_selected) {
		
		counter_reset();
		
		for(int i = 0; i < tl.getChildCount(); i++) {
		    View child = tl.getChildAt(i);
		    
		    if (child instanceof TableRow) {
		        TableRow row = (TableRow) child;
		        
		       // for(int x = 1; x < 2; x++) {
		            TextView t= (TextView) row.getChildAt(1);
		            String t_item_name= ((TextView) row.getChildAt(2)).getText().toString();
		            String t_price= ((TextView) row.getChildAt(7)).getText().toString();
		            
		            //t.setHeight(trHeight);
		            String r1=t.getText().toString();
		            
		            if(r1.equals(itemcode_selected) && t_item_name.startsWith("Receipt")){
		            	deleteRowIndex= i;
		            	System.out.println(deleteRowIndex+" index of the receipt row");
		            	
		            	pincode.setText(""+itemcode_selected);
						
						int position = pincode.length();
						Selection.setSelection(pincode.getText(), position);
						deleteItemFromTable( deleteRowIndex);
						//submit_amount= ;
						submit_t= t_price;
						submit_t= submit_t.replace(",", ".");
						submit_amount= submit_amount+ Double.parseDouble(submit_t);
						System.out.println(submit_amount);
						calculatePayment(submit_amount);
		            }
		        //}
		    }
		}
		pincode.setText("");
	}
	//21st
	@SuppressWarnings("deprecation")
	private void highlight_Selected_Row(int index, String rowfeild) {
		counter_reset();
		
		View child = tl.getChildAt(index);
		TableRow row = (TableRow) child;
		
		String itemcode=((TextView)row.getChildAt(KEY_COLUMN_ITEM_CODE)).getText().toString();
		if(tab_returned_selected){
			
		}else{
			//int index1= Arrays.asList(items_code).indexOf(itemcode);
			item_image.setVisibility(View.VISIBLE);
			//item_image.setBackgroundResource(imagearr[index1]);
			//pincode.setText(""+itemcode);
		}
		String item_name=((TextView)row.getChildAt(KEY_COLUMN_ITEM_NAME)).getText().toString();
		String item_name_tag= ((TextView)row.getChildAt(KEY_COLUMN_ITEM_NAME)).getTag().toString();
		
		String sr=((TextView)row.getChildAt(KEY_COLUMN_SR)).getText().toString();
		String qty=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getText().toString();
		String qty_tag=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getTag().toString();
		
		String rate=((TextView)row.getChildAt(KEY_COLUMN_RATE)).getText().toString();
		String discount=((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getText().toString();
		String discount_tag=((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getTag().toString();
		
		String dis_amt=((TextView)row.getChildAt(KEY_COLUMN_DIS)).getText().toString();
		String amount=((TextView)row.getChildAt(KEY_COLUMN_AMOUNT)).getText().toString();
		
		tl.removeViewAt(index);
		row = new TableRow(MainActivity.this);
    	
    	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
    		
    		TextView text = new TextView(this);
    		text.setHeight(trHeight);
    		text.setPadding(5, 5, 5, 5);
    		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
    		
    		if(rowfeild.startsWith("itemcode")){
    			
    			comma.setClickable(false);
    			comma.setEnabled(false);
    			comma.setBackgroundResource(R.drawable.bt_comma_disable);
    			//item_img
    			text.setTextSize(size-1);
    			text.setTextColor(Color.parseColor("#ffffff"));
    			text.setTypeface(null, Typeface.BOLD);
    			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
    			
    			if(col==KEY_COLUMN_SR){
        			text.setText(""+sr);
        			text.setTag(""+item_name_tag);
        			
        		}else if(col==KEY_COLUMN_ITEM_CODE){
        			System.out.println("row.."+rowfeild);
        			text.setText(""+itemcode);
        			
            		text.setTag(""+item_name_tag);
        		}else if(col==KEY_COLUMN_ITEM_NAME){
        			text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
        			if(tab_returned_selected){
        				
        				String s=return_receipt.getText().toString();
        				s= s.replace("#", "");
        				String styledText = "<small>" +s + "</small>"+ 
        						" "+itemname_selected ;
        				
        				text.setText(Html.fromHtml(styledText));
        				//text.setText(""+itemname_selected);
        			}else 
        				text.setText(""+item_name);
        			text.setTag(""+item_name_tag);
        			 
        			
        		}else if(col==KEY_COLUMN_RATE){
        			text.setText(""+rate);
        			text.setTag(""+item_name_tag);
        			 
        		}else if(col==KEY_COLUMN_DIS){
        			text.setText(""+dis_amt);
        			text.setTag(""+discount_tag);
        		}else if(col==KEY_COLUMN_QTY){
        			text.setText(""+qty);
            		text.setTag(""+qty_tag);
        			
        		}else if(col==KEY_COLUMN_AMOUNT){
        			text.setText(""+amount);
        			text.setTag(""+item_name_tag);
        			
        		}else if(col== KEY_COLUMN_DIS_TYP){
        			text.setText(""+discount);
        			text.setTag(""+discount_tag);
        		}
        	}else{
    			
    			if(index%2==1){
        			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
            	}else{
            		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
            	}
    			text.setTextSize(size);
    			
    			
    			if(col==KEY_COLUMN_SR){
        			text.setText(""+sr);
        			text.setTag(""+item_name_tag);
        			text.setTextColor(Color.parseColor("#800080"));
        		}else if(col==KEY_COLUMN_ITEM_CODE){
        			
        			System.out.println("row.."+rowfeild);
        			text.setText(""+itemcode);
        			
            		text.setTag(""+item_name_tag);
            		text.setTextColor(Color.parseColor("#800080"));
        		}else if(col==KEY_COLUMN_ITEM_NAME){
        			text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
        			if(tab_returned_selected){
        				
        				String s=return_receipt.getText().toString();
        				s= s.replace("#", "");
        				String styledText = "<small>" +s + "</small>"+ 
        						" "+itemname_selected ;
        				
        				text.setText(Html.fromHtml(styledText));
        				//text.setText(""+itemname_selected);
        			}else 
        				text.setText(""+item_name);
        			text.setTag(""+item_name_tag);
        			text.setTextColor(Color.parseColor("#800080")); 
        			
        		}else if(col==KEY_COLUMN_RATE){
        			text.setText(""+rate);
        			text.setTag(""+item_name_tag);
        			text.setTextColor(Color.parseColor("#800080")); 
        		}else if(col==KEY_COLUMN_DIS){
        			
        			text.setText(""+dis_amt);
        			text.setTextColor(Color.parseColor("#800080")); 
        			text.setTag(""+discount_tag);
        			
        		}else if(col==KEY_COLUMN_QTY){
        			
        			if(rowfeild.startsWith("qty")){
        				if(index%2==1){
            				text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select_pink));
                    	}else{
                    		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
                    	}
        				text.setText(""+qty);
            			text.setTextColor(Color.parseColor("#ffffff"));
            			text.setTypeface(null, Typeface.BOLD);
            			pincode.setText("");
            		}else {
            			text.setText(""+qty);
            			text.setTextColor(Color.parseColor("#800080")); 
            		}
        			text.setTag(""+qty_tag);
        			
        		}else if(col==KEY_COLUMN_AMOUNT){
        			
        			text.setText(""+amount);
        			text.setTextColor(Color.parseColor("#800080")); 
        			text.setTag(""+item_name_tag);
        			
        		}else if(col== KEY_COLUMN_DIS_TYP){
        			
        			if(rowfeild.startsWith("discount")){
        				if(index%2==1){
            				text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select_pink));
                    	}else{
                    		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_select));
                    	}
        				text.setText(""+discount);
            			text.setTextColor(Color.parseColor("#ffffff"));
            			text.setTypeface(null, Typeface.BOLD);
            			//pincode.setAdapter(null);
            			String d= discount;
            			d= d.substring(0, d.length()-1);
            			pincode.setText(""+d);
            		}else {
            			text.setText(""+discount);
            			text.setTextColor(Color.parseColor("#800080")); 
            		}
        			text.setTag(""+discount_tag);
        		}
    		}
    		//System.out.println("tab selected....."+tab_returned_selected);
    		if(tab_returned_selected){
    			if(col==KEY_COLUMN_QTY );
        			//text.setOnClickListener(MainActivity.this);
        		
    		}else{
    			if(col==KEY_COLUMN_ITEM_CODE|| col== KEY_COLUMN_ITEM_NAME ||col== KEY_COLUMN_RATE
    					|| col==KEY_COLUMN_DIS || col==KEY_COLUMN_DIS_TYP || col==KEY_COLUMN_QTY
        				 ||col==KEY_COLUMN_SR)
        			text.setOnClickListener(MainActivity.this);//|| 
        	}
    		row.addView(text);
		}
    	tl.addView(row,index );
    	
    	item_code_selected=true;
    	deleteRowIndex= index;
	}
	//22nd
	@SuppressWarnings("deprecation")
	private void reset_highlight_Selected_Row(int index) {

		counter_reset();
		View child = tl.getChildAt(index);
		TableRow row = (TableRow) child;
		
		String itemcode=((TextView)row.getChildAt(KEY_COLUMN_ITEM_CODE)).getText().toString();
		pincode.setAdapter(null);
		//pincode.setText(""+itemcode);
		int position = pincode.length();
		Selection.setSelection(pincode.getText(), position);
		//pincode.setAdapter(null);
		if(tab_returned_selected){
			
		}else{
			item_image.setVisibility(View.VISIBLE);
			//item_image.setBackgroundResource(imagearr[index1]);
		}
		String item_name=((TextView)row.getChildAt(KEY_COLUMN_ITEM_NAME)).getText().toString();
		String item_name_tag= ((TextView)row.getChildAt(KEY_COLUMN_ITEM_NAME)).getTag().toString();
		
		String sr=((TextView)row.getChildAt(KEY_COLUMN_SR)).getText().toString();
		String qty=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getText().toString();
		String qty_tag=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getTag().toString();
		
		String rate=((TextView)row.getChildAt(KEY_COLUMN_RATE)).getText().toString();
		String discount=((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getText().toString();
		String discount_tag=((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getTag().toString();
		
		String dis_amt=((TextView)row.getChildAt(KEY_COLUMN_DIS)).getText().toString();
		String amount=((TextView)row.getChildAt(KEY_COLUMN_AMOUNT)).getText().toString();
		
		tl.removeViewAt(index);
		
		
		row = new TableRow(MainActivity.this);
    	
    	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
    		
    		TextView text = new TextView(this);
    		text.setHeight(trHeight);
    		text.setTextSize(size);
    		text.setPadding(5, 5, 5, 5);
    		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
    		
    		if(index%2==1){
    			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
        	}else{
        		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
        	}
    		
    		if(col==KEY_COLUMN_SR){
    			text.setText(""+sr);
    			text.setTag(""+item_name_tag);
    			
    		}else if(col==KEY_COLUMN_ITEM_CODE){
    			
    			text.setText(""+itemcode);
    			text.setTag(""+item_name_tag);
    			
    		}else if(col==KEY_COLUMN_ITEM_NAME){
    			text.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
    			if(tab_returned_selected){
    				
    				String s=return_receipt.getText().toString();
    				s= s.replace("#", "");
    				String styledText = "<small>" +s + "</small>"+ 
    						" "+itemname_selected ;
    				
    				text.setText(Html.fromHtml(styledText));
    				//text.setText(""+itemname_selected);
    			}else 
    				text.setText(""+item_name);
    			text.setTag(""+item_name_tag);
    		}else if(col==KEY_COLUMN_RATE){
    			text.setText(""+rate);
    			text.setTag(""+item_name_tag);
    		}else if(col==KEY_COLUMN_DIS){
    			text.setText(""+dis_amt);
    			text.setTag(""+discount_tag);
    		}else if(col==KEY_COLUMN_QTY){
    			text.setText(""+qty);
    			text.setTag(""+qty_tag);
    		}else if(col==KEY_COLUMN_AMOUNT){
    			text.setText(""+amount);
    			text.setTag(""+item_name_tag);
    		}else if(col== KEY_COLUMN_DIS_TYP){
    			text.setText(""+discount);
    			text.setTag(""+discount_tag);
    		}
    		
    		text.setTextColor(Color.parseColor("#800080")); 
    		//System.out.println("tab selected....."+tab_returned_selected);
    		if(tab_returned_selected){
    			if(col==KEY_COLUMN_QTY )
        			text.setOnClickListener(MainActivity.this);
        		
    		}else{
    			if(col==KEY_COLUMN_ITEM_CODE|| col== KEY_COLUMN_ITEM_NAME ||col== KEY_COLUMN_RATE
    					|| col==KEY_COLUMN_DIS || col==KEY_COLUMN_DIS_TYP || col==KEY_COLUMN_QTY
        				 ||col==KEY_COLUMN_SR)
        			text.setOnClickListener(MainActivity.this);//
        	}
    		row.addView(text);
		}
    	tl.addView(row,index );
	}
	//23rd
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
		
		back.setText("");
		back.setBackgroundResource(R.drawable.bt_back_disable);
		back.setClickable(false);
		back.setEnabled(false);
		
		confirm.setBackgroundResource(R.drawable.bt_confirm_disable);
		confirm.setEnabled(false);
		confirm.setClickable(false);
		
		del.setBackgroundResource(R.drawable.bt_delete_disable);
		del.setText("");
		del.setEnabled(false);
		del.setClickable(false);
		
	}
	//25th
	private void reset_back_del_confirm() {
		
		del.setBackgroundResource(R.drawable.btn_delete);
		del.setText("Delete");
		del.setOnClickListener(this);
		del.setEnabled(true);
		del.setClickable(true);
		
		confirm.setOnClickListener(this);
		confirm.setBackgroundResource(R.drawable.main_confirm);
		confirm.setEnabled(true);
		confirm.setClickable(true);
		
		back.setText("Back");
		back.setBackgroundResource(R.drawable.btn_back);
		back.setOnClickListener(this);
		back.setClickable(true);
		back.setEnabled(true);
		
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
	
	
	private void invalid_discount_Popup(String message ) {
		
		final AlertDialog  dialog = new AlertDialog.Builder(this).create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
				//Dialog(_activity, android.R.style.Theme_Translucent_NoTitleBar);
		
		dialog.setContentView(R.layout.invalid_user);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		Button confirm = (Button)dialog.findViewById(R.id.cancel);
		TextView heading= (TextView) dialog.findViewById(R.id.heading);
		TextView det= (TextView) dialog.findViewById(R.id.det);
		heading.setText("Result");
		
		det.setText(""+ message);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pincode.setText("");
				dialog.dismiss();
		    }});
	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void item_call(String item_code) {
		
		item_list= null;
		item_list = new ArrayList<HashMap<String, String>>();
		List<Items> allItems = db_items.getItemsSale(item_code);
		int i=0;
		imagearr= new int[allItems.size()];
		System.out.println("1."+ allItems.size());
		for (final Items items : allItems) {
			
			System.out.println("1."+items.getBarcode()
					+"\n.........."+ items.getId()
					+"\n.........."+ items.getAvailable_quantity()
					+"\n.........."+ items.getGroup_id()
					+"\n.........."+ items.getSize_id()
					+"\n.........."+ items.getColor_id()
					+"\n.........."+ items.getSelling_price()
					+"\n.........."+ items.getItem_id()
					+"\n.........."+ items.getDecs1());
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_ITEM_CODE, items.getItem_id());
			map.put(KEY_ITEM_NAME, items.getDecs1());
			map.put(KEY_ITEM_CODE_ID, ""+items.getId());
			
			map.put(KEY_ITEM_PRICE, items.getSelling_price());
			map.put(KEY_ITEM_AVAILABLE_QUANTITY, items.getAvailable_quantity());
			
			map.put(KEY_TAX, items.getTaxation_code());
			
			map.put(KEY_ITEM_DISCOUNT_TYPE, "1");
			map.put(KEY_ITEM_DISCOUNT_VALUE, "0");
			
			 //KEY_ITEM_ID = "item_id";
			
			map.put(KEY_DECS2, items.getDecs2());
			map.put(KEY_DECS3, items.getDecs3());
			
			map.put(KEY_SUPPLIER_NO, items.getSupplier_number());
			map.put(KEY_SUPPLIER_ITEM_NO, items.getSupplier_item_number());
			
			map.put(KEY_BARCODE, items.getBarcode());
			map.put(KEY_BUY_PRICE, items.getBuying_price());
			//map.put(KEY_SELL_PRICE, items.getSelling_price());
			
			map.put(KEY_SIZE_ID, items.getSize_id());
			map.put(KEY_GROUP_ID, items.getGroup_id());
			map.put(KEY_COLOR_ID, items.getColor_id());
			
			imagearr[i]=R.drawable.item_image_s;
			i++;
			map.put(KEY_ITEM_IMAGE, Integer.toString(imagearr[0]));
			
			item_list.add(map);
		}
		//db_items.closeDB();
		
		
		searchResults= null;
		adapter= null;
		pincode= null;
		pincode= (CustomAutoCompleteTextView) findViewById(R.id.itemcode);
		pincode.setText("");
		pincode.setHint("");
		pincode.setInputType(InputType.TYPE_NULL);
		searchResults=new ArrayList<HashMap<String,String>>(item_list);
		System.out.println("1."+ searchResults.toString());
		adapter = new SimpleAdapter(getBaseContext(), searchResults, R.layout.list_item_code, from, to);
		
		System.out.println("1."+ item_list.size());
				
		/** Setting the itemclick event listener */
		pincode.setOnItemClickListener(itemClickListener);
		pincode.setAdapter(adapter);
	}
	
	//24th
	
	int index_add_dec_qty=0;
	int add_dec_qty=0;
	
	
	@Override
	public void onClick(View v) {
		
		if(v== back){
			System.out.println(salecounter+"  sale counter is zero");
			counter_reset();
			pincode.setHint("");
			//pincode.setAdapter(adapter);
			pincode.setFocusable(true);
			pincode.setFocusableInTouchMode(true);
			pincode.requestFocus();
			pincode.setText("");
			//block_back_del_confirm();
			reset_back_del_confirm();
			back.setText("");
			back.setBackgroundResource(R.drawable.bt_back_disable);
			back.setClickable(false);
			back.setEnabled(false);
			
//			confirm.setOnClickListener(this);
//			confirm.setBackgroundResource(R.drawable.main_confirm);
//			confirm.setEnabled(true);
//			confirm.setClickable(true);
	//		reset_back_del_confirm();
			keypad_reset();
			
			if(salecounter==0){
				System.out.println(salecounter+"  sale counter is zero");
				block_back_del_confirm();
				del.setBackgroundResource(R.drawable.btn_delete);
				del.setText("Delete");
				del.setOnClickListener(this);
				del.setEnabled(true);
				del.setClickable(true);
				
			}else if(paid_confirmed){
				
			}else if(qty_layout.getVisibility()==0 || layout.getVisibility()==0 ||
					discount_layout.getVisibility()==0 || inflateLayout.getVisibility()== 0) {
				
				if(qty_layout.getVisibility()== View.VISIBLE){
					pincode.setAdapter(null);
				}
				pincode.setAdapter(null);
				pincode.setText("");
				System.out.println("back for quantity and discount");
				
				resetPaymentTabs();
				discount_amount_selected =false;discount_percent_selected=false;
				layout.setVisibility(View.INVISIBLE);
				
				if(row_selector>0){
					linear.removeAllViews();
					linear.addView(inflateLayout);
					highlight_Selected_Row( row_selector, "itemcode");
					pincode.setText("");
					
				}
				if(pincode.getAdapter() == null){
					System.out.println("null adapter....");
					pincode.setAdapter(adapter);
					
				}
				
				back.setText("");
				back.setBackgroundResource(R.drawable.bt_back_disable);
				back.setClickable(false);
				back.setEnabled(false);
				//pincode.setAdapter(adapter);
				//pincode.setFocusable(true);
				//pincode.setFocusableInTouchMode(true);
				
				System.out.println("back for pincode focusable"+ pincode.isFocusable());
				pincode.setHint("");
				change.setText("0,00");
				payment_text.setText("Payment");
				payment.setText("0,00");
				alternate.setText("0,00");
				
				submit_amount=0.00;
				submit_t="";
				item_code="";
				pincode_concat="";
				
			}else if( layoutPayment.getVisibility()==View.VISIBLE) {
				
				System.out.println("back for payment screen");
				resetPaymentTabs();
				
				if(row_selector>0){
					linear.removeAllViews();
					linear.addView(inflateLayout);
					highlight_Selected_Row( row_selector, "itemcode");
				}
				
				if(pincode.getAdapter() == null){
					System.out.println("null adapter....");
					pincode.setAdapter(adapter);
					
				}
				pincode.requestFocus();
				pincode.setAdapter(adapter);
				pincode.setFocusable(true);
				pincode.setFocusableInTouchMode(true);
				//item_code_selected=true;
				change.setText("0,00");
				payment_text.setText("Payment");
				payment.setText("0,00");
				alternate.setText("0,00");
				
				submit_amount=0.00;
				submit_t="";
				item_code="";
				pincode_concat="";
				discount_amount_selected =false;discount_percent_selected=false;
				
			}else{
				System.out.println(salecounter+"  sale counter is zero");
				//pincode.setAdapter(adapter);
				//pincode.setFocusable(true);
				//pincode.setFocusableInTouchMode(true);
				resetPaymentTabs();
				item_code="";
			//	pincode.setText(item_code);
				
				pincode.setText("");
				pincode_concat="";
				submit_amount=0.00;
				submit_t="";
				change.setText("0,00");
				payment.setText("0,00");
				alternate.setText("0,00");
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
				discount_amount_selected =false;discount_percent_selected=false;
			}
		}else if(v== confirm){
			
			counter_reset();
			if(index_add_dec_qty != 0){
				
				setIncQty(index_add_dec_qty, ""+add_dec_qty);
				index_add_dec_qty=0;
				add_dec_qty=0;
				
				//linear.removeAllViews();
				//linear.addView(inflateLayout);
				//block_back_del_confirm();
				reset_back_del_confirm();
				back.setText("");
				back.setBackgroundResource(R.drawable.bt_back_disable);
				back.setClickable(false);
				back.setEnabled(false);
				
				
				//linear.removeAllViews();
        		//linear.addView(inflateLayout);
        		qty_layout.setVisibility(View.INVISIBLE);
        		layout.setVisibility(View.INVISIBLE);
        		discount_layout.setVisibility(View.INVISIBLE);
        		//paymentShows();
        		keypad_reset();
        		pincode.setHint("");
    			//pincode.setAdapter(adapter);
    			pincode.setFocusable(true);
    			pincode.setFocusableInTouchMode(true);
    			pincode.requestFocus();
    			pincode.setText("");
        		
				
			}
			else if(!item_code.equals("") && submit_t.equals("")){
				
				if(discount_amount_selected || discount_percent_selected) {
					
					System.out.println(" discount box confirm");
					System.out.println(discount_tag_index+"......."+pincode.getText().toString()+"...................."+ 
					discount_amount_selected+discount_percent_selected);
					String dis_type_v="";
					
					View child = tl.getChildAt(discount_tag_index);
					if (child instanceof TableRow) { 
						TableRow row = (TableRow) child;
						dis_type_v =((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getText().toString();
					}
					
					String pin=pincode.getText().toString();
					String v1=pin;
					
					if(discount_amount_selected){
						v1= v1+""+KEY_DIS_TAG;
						if(dis_type_v.equals(v1)){
							
							item_code="";
							pincode.setAdapter(null);
							linear.removeAllViews();
			        		linear.addView(layoutPayment);
			        		qty_layout.setVisibility(View.INVISIBLE);
			        		layout.setVisibility(View.INVISIBLE);
			        		discount_layout.setVisibility(View.INVISIBLE);
			        		
			        		paymentShows();
			        		keypad_block();
			        		block_back_del_confirm();
			        		back.setText("Back");
			        		back.setBackgroundResource(R.drawable.btn_back);
			        		back.setOnClickListener(this);
			        		back.setClickable(true);
			        		back.setEnabled(true);
			        		
			        		pincode.setFocusable(false);
							pincode.setFocusableInTouchMode(false);
			        		pincode.setHint("Select Payment Method");
							//pincode.setText(""+item_code);
			        		System.out.println("value......"+ pincode.getText().toString());
			        		if(row_selector>0)
								reset_highlight_Selected_Row(row_selector);
						}else 
							setDiscount(discount_tag_index,pincode.getText().toString() ,pin+""+KEY_DIS_TAG);
						
						
					}else if(discount_percent_selected){
						
						v1= v1+""+"%";
						if(dis_type_v.equals(v1)){
							pincode.setHint("Select Payment Method");
							pincode.setText("");
							item_code="";
							pincode.setAdapter(null);
							pincode.setFocusable(false);
							pincode.setFocusableInTouchMode(false);
							linear.removeAllViews();
			        		linear.addView(layoutPayment);
			        		qty_layout.setVisibility(View.INVISIBLE);
			        		layout.setVisibility(View.INVISIBLE);
			        		discount_layout.setVisibility(View.INVISIBLE);
			        		keypad_block();
			        		block_back_del_confirm();
			        		back.setText("Back");
			        		back.setBackgroundResource(R.drawable.btn_back);
			        		back.setOnClickListener(this);
			        		back.setClickable(true);
			        		back.setEnabled(true);
			        		paymentShows();
			        		
			        		System.out.println("value......"+ pincode.getText().toString());
			        		if(row_selector>0)
								reset_highlight_Selected_Row(row_selector);
			        		
						}else 
							setDiscount(discount_tag_index,pincode.getText().toString() ,pin+"%");
					}
					
					
					
				}else{
				 
				System.out.println(" item code confirm");
				String itemcode= pincode.getText().toString();
				//int index= Arrays.asList(items_code).indexOf(itemcode);
				pincode.setAdapter(null);
				int index= item_list.indexOf(itemcode);
				
				
				
				//item_list.get(index).get(TAG_NAME)
				if(index>=0){
					
					itemcode_selected= 	item_list.get(index).get(KEY_ITEM_CODE);
	        		itemname_selected= 	item_list.get(index).get(KEY_ITEM_NAME);
	        		item_price=			item_list.get(index).get(KEY_ITEM_PRICE);
	        		item_discount=		item_list.get(index).get(KEY_ITEM_DISCOUNT_VALUE);
					
	        		System.out.println(" item code "+ itemcode_selected);
	        		init_Dist_Qty();
	        		
	        		paymentShows();
	        		
				//	item_image.setBackgroundResource(imagearr[index]);
					
					linear.removeAllViews();
	        		linear.addView(inflateLayout);
	        		
	        		//////////////////////////////////////////////
	        		
	        		countr= false;
					//pincode.setText(itemcode_selected);
					
					int position = pincode.length();
					Selection.setSelection(pincode.getText(), position);
					//pincode.requestFocus();
					System.out.println(position+"....item selected from adapter"+itemcode_selected);
					
					item_code= "";
					setTableVaules();
					itemcode_selected="";
	        		itemname_selected="";
	        		item_price="";
	        		item_discount="";
	        		pincode.setAdapter(adapter);
				}
				
				}
        		//pincode.setAdapter(adapter);	
			}else if(item_code.equals("") && submit_t.equals("") && salecounter>0){
				
				System.out.println(" payment show payment box");
				
				if(cashLayout.getVisibility()== View.VISIBLE){
					pincode.setFocusable(true);
					pincode.setFocusableInTouchMode(true);
					System.out.println(" payment show payment box");
					
				}else if(cardLayout.getVisibility()== View.VISIBLE){
					
					System.out.println(" payment show payment box");
				
				
				}else if(voucher_layout.getVisibility()== View.VISIBLE){
					pincode.setFocusable(true);
					pincode.setFocusableInTouchMode(true);
					if(pincode.getText().toString().length()>0){
						if( r_total.getVisibility()== View.INVISIBLE){
							initVoucher();
						}else if(r_total.getVisibility()== View.VISIBLE &&  r_remaining.getVisibility()== View.INVISIBLE){
							System.out.println(" payment show payment box voucher visible");
							initVoucher();
						}else if(r_remaining.getVisibility()== View.VISIBLE){
							System.out.println(" payment voucher submit");
							if(submit_amount== totalAmount|| submit_amount> totalAmount){
								initPaymentPaid();
								block_back_del_confirm();
								keypad_block();
								confirm.setOnClickListener(this);
								confirm.setBackgroundResource(R.drawable.main_confirm);
								confirm.setEnabled(true);
								confirm.setClickable(true);
								
							}else{
								lessPaymentPaid();
							}
						}
					}else ;
					
				}else if(returned_layout.getVisibility()== View.VISIBLE ){
					pincode.setFocusable(true);
					pincode.setFocusableInTouchMode(true);
					if(pincode.getText().toString().length()>0){
						// display order 
						ReturnItemsOrder r= new ReturnItemsOrder(pincode.getText().toString());
						String [] indexs= r.getOrderDetails();
						if(indexs.length>0){
							return_receipt.setText("Receipt# "+pincode.getText().toString());
							pincode.setText("");
							//setReturnTableValues(indexs);
							del.setBackgroundResource(R.drawable.bt_delete_disable);
							del.setText("");
							del.setEnabled(false);
							del.setClickable(false);
							
						}
					}else{
						//payment messge
						
						System.out.println(" payment returned submit");
						if(submit_amount== totalAmount|| submit_amount> totalAmount){
							initPaymentPaid();
							keypad_block();
							block_back_del_confirm();
							confirm.setOnClickListener(this);
							confirm.setBackgroundResource(R.drawable.main_confirm);
							confirm.setEnabled(true);
							confirm.setClickable(true);
							
						}else{
							lessPaymentPaid();
						}
					}
					item_code_selected=false;
					
				}else{
					if(row_selector>0)
						reset_highlight_Selected_Row(row_selector);
					
					pincode.setHint("Select Payment Method");
					pincode.setText("");
					
					item_code="";
					pincode.setAdapter(null);
					pincode.setFocusable(false);
					pincode.setFocusableInTouchMode(false);
					linear.removeAllViews();
	        		linear.addView(layoutPayment);
	        		qty_layout.setVisibility(View.INVISIBLE);
	        		layout.setVisibility(View.INVISIBLE);
	        		discount_layout.setVisibility(View.INVISIBLE);
	        		paymentShows();
	        		keypad_block();
	        		block_back_del_confirm();
	        		back.setText("Back");
	        		back.setBackgroundResource(R.drawable.btn_back);
	        		back.setOnClickListener(this);
	        		back.setClickable(true);
	        		back.setEnabled(true);
	        		item_code_selected=false;
	        	}
			}else if(submit_t.length()> 0 && !paid_confirmed){
				
				System.out.println(" payment show less payment box or payment submit message and submit"+submit_t);
				if(!pincode_concat.equals("")){
					pincode_concat= pincode_concat.replace(",", ".");
					double d= Double.parseDouble(pincode_concat);
					submit_amount= submit_amount+d;
					String f="";
					if(!paymentAlternate.equals("")){
						//f= df.format(d);
						//f= f.replace(".", ",");
						System.out.println(" total  submit value"+submit_t+" amount..."+ submit_amount);
						f=payment.getText().toString();
						f= f.replace(",", ".");
						double alter= Double.parseDouble(f);
						System.out.println(" total  payment value"+alter);
						d= submit_amount- alter;
						f= df.format(d);
						f= f.replace(".", ",");
						
						alternate.setText(""+f);
					}else{
						f= df.format(submit_amount);
						f= f.replace(".", ",");
						payment.setText(""+f);
					}
					double chang= submit_amount-totalAmount;
					f= df.format(chang);
					f=f.replace(".", ",");
					change.setText(""+f);
					
					pincode_concat="";
					
				}else{
					if(submit_amount== totalAmount|| submit_amount> totalAmount){
						initPaymentPaid();
						block_back_del_confirm();
						keypad_block();
						confirm.setOnClickListener(this);
						confirm.setBackgroundResource(R.drawable.main_confirm);
						confirm.setEnabled(true);
						confirm.setClickable(true);
						
					}else{
						lessPaymentPaid();
					}
				}
			}else if(salecounter>0 && paid_confirmed){
				countDownTimer.cancel();
				Intent i= new Intent(MainActivity.this,HeadTabActivityAdmin.class);
				finish();
				startActivity(i);
			}else if(salecounter==0 ){
				
			}else{
				countDownTimer.cancel();
				System.out.println("press empty 2");
				Intent i= new Intent(MainActivity.this,HeadTabActivityAdmin.class);
				finish();
				startActivity(i);
			}
		////////////////////////////////////////////////////////////////////////////////////////////////////////	
		}else if(v== del){
			counter_reset();
			System.out.println("pin code text.."+pincode.getText().toString()+" item code" +item_code+ item_code_selected);
			
			if(item_code_selected){
				
				if(item_code.equals("")){
					if(discount_amount_selected ||discount_percent_selected){
						item_code=pincode.getText().toString();
						if(item_code.length()>0)
							item_code= item_code.substring(0, item_code.length()-1);
						
						pincode.setText(item_code);
						item_code="";
						
					}else{
						pincode.setAdapter(null);
						System.out.println("size before del"+order_items.size()+"   delete row index"+deleteRowIndex);
						if(deleteRowIndex>0){
							deleteItemFromTable(deleteRowIndex);
							//deleteRowIndex--;
							item_code="";
						}
						
						//order_items.remove(deleteRowIndex-1);
						
						System.out.println("size after del"+order_items.size()+"   delete row index"+deleteRowIndex);
						if(salecounter<=0)
							block_back_del_confirm();
						
						pincode.setAdapter(adapter);
						pincode.setHint("");
					}
				}else{
					//item_code=pincode.getText().toString();
					item_code= item_code.substring(0, item_code.length()-1);
					pincode.setText(item_code);
					if(item_code.equals(""))
						reset_back_del_confirm();
					
					back.setText("");
					back.setBackgroundResource(R.drawable.bt_back_disable);
					back.setClickable(false);
					back.setEnabled(false);
					
					
					if(discount_amount_selected ||discount_percent_selected){
						item_code="";
					}
				}
				//pincode.setFocusable(true);
				//pincode.setFocusableInTouchMode(true);
				
			}else if(tab_voucher_selected || tab_returned_selected){
				item_code=pincode.getText().toString();
				item_code= item_code.substring(0, item_code.length()-1);
				pincode.setText(item_code);
				if(item_code.equals(""))
					block_back_del_confirm();
				item_code="";
			
			}else if(submit_t.length()>0){
				
				System.out.println("submit amount"+submit_amount);
				
				if(!pincode_concat.equals("")){
					double d= Double.parseDouble(pincode_concat);
					submit_amount= submit_amount+d;
					pincode_concat="";
				}
				//System.out.println("submit amount"+submit_amount);
				int s= submit_t.lastIndexOf("+");
				
				if(s>0){
					submit_t= submit_t.replace(",", ".");
					double min= Double.parseDouble(submit_t.substring(s+1, submit_t.length()));
					submit_amount= submit_amount-min;
					submit_t= submit_t.substring(0, s);
					submit_t= submit_t.replace(".", ",");
					//System.out.println("submit amount"+submit_amount);
				
				}else{
					submit_t= submit_t.replace(",", ".");
					double min= Double.parseDouble(submit_t);
					submit_amount= submit_amount-min;
					submit_t="";
					//System.out.println("submit amount"+submit_amount);
				}
				
				pincode.setText(submit_t);
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
				//System.out.println("submit amount"+submit_amount);
				String f="";
				if(!paymentAlternate.equals("")){
					f= payment.getText().toString();
					f= f.replace(",", ".");
					double d= Double.parseDouble(f);
					d= submit_amount-d;
					f= df.format(d);
					f= f.replace(".", ",");
					alternate.setText(""+f);
					
				}else{
					f= df.format(submit_amount);
					f= f.replace(".", ",");
					payment.setText(""+f);
				}
				
				double chang= submit_amount-totalAmount;
				f= df.format(chang);
				f=f.replace(".", ",");
				change.setText(""+f);
				
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////////
		}else if(v== park){
			
		}else if(v== unpark){
			
		}else if(v== discount){
			
		}else if(v== search){
			
		}else if(v== tab_cash){
			counter_reset();
			keypad_reset();
			back.setText("Back");
			back.setBackgroundResource(R.drawable.btn_back);
			back.setOnClickListener(this);
			back.setClickable(true);
			back.setEnabled(true);
			
			pincode.setHint("");
			pincode.setAdapter(null);
			pincode.requestFocus();
			pincode.setFocusable(true);
			pincode.setFocusableInTouchMode(true);
			linear.removeAllViews();
			linear.addView(layoutPayment);
			
			item_code_selected= false;
			resetPaymentTabs();
			
			tab_cash.setBackgroundResource(R.drawable.cash_sel);
			cashLayout.setVisibility(View.VISIBLE);
			
			if(paymentAlternate.equals("Alternate")){
				payment_type2= "1";
				alternate_text.setText("Alternate (Cash)");
			}else{
				payment_text.setText("Payment");
				payment_type1= "1";
			}
			showcash();
			
		}else if(v== tab_card){
			counter_reset();
			keypad_block();
			pincode.setHint("Select Card Type");
			//pincode.setAdapter(null);
			pincode.setFocusable(false);
			pincode.setFocusableInTouchMode(false);
			item_code_selected= false;
			
			linear.removeAllViews();
			linear.addView(layoutPayment);
			
			resetPaymentTabs();
			
			tab_card.setBackgroundResource(R.drawable.credit_card_sel);
			cardLayout.setVisibility(View.VISIBLE);
			
			if(paymentAlternate.equals("Alternate")){
				payment_type2= "2";
				alternate_text.setText("Alternate (Card)");
			}else{
				payment_text.setText("Payment (Card)");
				payment_type1= "2";
			}
			showcash();
			
		}else if(v== tab_giftvoucher){
			counter_reset();
			keypad_reset();
			
			back.setText("Back");
			back.setBackgroundResource(R.drawable.btn_back);
			back.setOnClickListener(this);
			back.setClickable(true);
			back.setEnabled(true);
			resetPaymentTabs();
			//reset_back_del_confirm();
			tab_voucher_selected=true;
			pincode.requestFocus();
			pincode.setHint("");
			//pincode.setAdapter(null);
			pincode.setFocusable(true);
			pincode.setFocusableInTouchMode(true);
			
			linear.removeAllViews();
			linear.addView(layoutPayment);
			item_code_selected= false;
			
			tab_giftvoucher.setBackgroundResource(R.drawable.gift_voucher_sel);
			voucher_layout.setVisibility(View.VISIBLE);
			
			if(paymentAlternate.equals("Alternate")){
				payment_type2= "3";
				alternate_text.setText("Alternate (Voucher)");
			}else{
				payment_text.setText("Payment (Voch.)");
				payment_type1= "3";
			}
		}else if(v== tab_returned){
			keypad_reset();
			counter_reset();
			resetPaymentTabs();
			tab_returned_selected=true;
			tab_returned.setBackgroundResource(R.drawable.returned_sel);
			returned_layout.setVisibility(View.VISIBLE);
			
			pincode.setAdapter(null);
			pincode.requestFocus();
			pincode.setFocusable(true);
			pincode.setFocusableInTouchMode(true);
			
			pincode.setHint("");
			
			
			if(paymentAlternate.equals("Alternate")){
				alternate_text.setText("Alternate (Returned)");
				payment_type2= "4";
			}else{
				payment_text.setText("Payment (Returned)");
				payment_type1= "4";
			}
			
			tl_return.removeAllViews();
			//ReturnedItems r= new ReturnedItems(this, tl_return);
			//r.createTable();
			
			setReturnTable();
		}
		else if(v== cash_200 || v== cash_100 || v== cash_50 || v== cash_20 || v== cash_10 || v== cash_05){
			counter_reset();
			
			reset_back_del_confirm();
			Button b = (Button)v;
			String buttonText = b.getText().toString();
			double cur= Double.parseDouble(buttonText);
			System.out.println(buttonText);
			if(submit_t.equals(""))
				submit_t=submit_t+buttonText;
			else
				submit_t=submit_t+"+"+buttonText;
			
			if(pincode_concat.equals(""))
				submit_amount= submit_amount+cur;
			else{
				double c= Double.parseDouble(pincode_concat);
				submit_amount= submit_amount+c+cur;
				pincode_concat="";
			}
			pincode.setText(submit_t);
			int position = pincode.length();
			Selection.setSelection(pincode.getText(), position);
			calculatePayment(submit_amount);
			
		}else if(v== cash_v1 || v== cash_v2 || v== cash_v3 || v== card_v1 || v== card_v2 || v== card_v3){
			reset_back_del_confirm();
			counter_reset();
			if(pincode.getHint().toString().equals("")){ 
				
				Button b = (Button)v;
				String buttonText = b.getText().toString();
				System.out.println("......"+submit_amount+"......"+buttonText+"..."+ submit_t+pincode_concat);
				buttonText= buttonText.replace(",", ".");
				
				Double cur= Double.parseDouble(buttonText);
				Integer bar = cur.intValue();
				if(submit_t.equals(""))
					submit_t=submit_t+bar;
				else
					submit_t=submit_t+"+"+bar;
				
				pincode.setText(submit_t);    
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
				
				if(pincode_concat.equals(""))
					submit_amount= submit_amount+cur;
				else{
					double c= Double.parseDouble(pincode_concat);
					submit_amount= submit_amount+c+cur;
					pincode_concat="";
				}
				calculatePayment(submit_amount);
			}
		}else if(v== visa){
			counter_reset();
			keypad_reset();
			reset_back_del_confirm();
			if(payment.getText().toString().equals("0,00")){
				pincode.setHint("");
				pincode.setText(""+total_payment.getText().toString());
				
				payment.setText(""+total_payment.getText().toString());
				String s= total_payment.getText().toString();
				submit_t=s;
				s= s.replace(",", ".");
				submit_amount= Double.parseDouble(s);
				s= s.replace(".", ",");
			}else{
				double pay=totalAmount- submit_amount;
				if(pay>0){
					submit_amount= submit_amount+pay;
					String s= df.format(pay);
					s=s.replace(".", ",");
					pincode.setText(""+s);
					//payment.setText(""+total_payment.getText().toString());
					alternate.setText(""+s);
					change.setText("0,00");
					submit_t=s;
					
				}else{
					pincode.setText(""+pay);
				}
			}
			int position = pincode.length();
			Selection.setSelection(pincode.getText(), position);
			
		}else if(v== visaelectron){
			
		}else if(v== solo){
			
		}else if(v== delta){
			
		}else if(v== maestro){
			
		}else if(v== mastercard){
			
		}else if(v== q_dec){
			
			String i= quantity.getText().toString();
			
			int index= Integer.parseInt(q_dec.getTag().toString());
			
			int a= Integer.parseInt(i);
			
			if(a==1){
				quantity.setText("1");
				q_dec.setBackgroundResource(R.drawable.qty_m_bt_disabe);
				q_dec.setEnabled(false);
				q_dec.setClickable(false);
			}
			else{
				int a1= (a-1);
				quantity.setText(""+a1);
				index_add_dec_qty= index;
				add_dec_qty= a1;
				
				if(quantity.getText().toString().equals("1")){
					q_dec.setBackgroundResource(R.drawable.qty_m_bt_disabe);
					q_dec.setEnabled(false);
					q_dec.setClickable(false);
				}
			}
			System.out.println("qty..........."+index);
			q_inc.setBackgroundResource(R.drawable.qty_inc);
			q_inc.setEnabled(true);
			q_inc.setClickable(true);
			q_inc.setOnClickListener(this);
			
		}else if(v== q_inc){
			
			int index= Integer.parseInt(q_inc.getTag().toString());
			
			System.out.println("index..........."+index);
			System.out.println("index..........."+order_items.get(index-1).get("id"));
			System.out.println("index..........."+order_items.get(index-1).get(KEY_ITEM_AVAILABLE_QUANTITY));
			
			String i= quantity.getText().toString();
			String available_qty= order_items.get(index-1).get(KEY_ITEM_AVAILABLE_QUANTITY);
			
			if(available_qty.length()>0){
				int avail_qty=Integer.parseInt(available_qty);
				int inc= (Integer.parseInt(i));
				
				if(inc<avail_qty){
					inc= inc+1;
					quantity.setText(""+ inc);
					index_add_dec_qty= index;
					add_dec_qty= inc;
					System.out.println("inc qty..........."+inc);
					if(inc== avail_qty){
						q_inc.setBackgroundResource(R.drawable.qty_p_bt_disabe);
						q_inc.setEnabled(false);
						q_inc.setClickable(false);
					}
				}else if(inc== avail_qty){
					q_inc.setBackgroundResource(R.drawable.qty_p_bt_disabe);
					q_inc.setEnabled(false);
					q_inc.setClickable(false);
				}
			}
			q_dec.setBackgroundResource(R.drawable.qty_dec);
			q_dec.setEnabled(true);
			q_dec.setClickable(true);
			q_dec.setOnClickListener(this);
			
		}else if(v== d_percentage){
			discount_percent_selected= true;
			discount_amount_selected=false;
			pincode.setAdapter(null);
			d_percentage.setBackgroundResource(R.drawable.dis_perc_bt_sel);
			d_rupee.setBackgroundResource(R.drawable.dis_bt);
			pincode.setHint("");
			//pincode.setInputType(InputType.TYPE_T);
			//discount_tag_index= Integer.parseInt(d_percentage.getTag().toString());
			
		}else if(v== d_rupee){
			//discount_tag_index= Integer.parseInt(d_rupee.getTag().toString());
			pincode.setAdapter(null);
			discount_percent_selected= false;
			discount_amount_selected=true;
			d_rupee.setBackgroundResource(R.drawable.dis_bt_sel);
			d_percentage.setBackgroundResource(R.drawable.dis_perc_bt);
			pincode.setHint("");
			
		}/*else if(v== one){
			if(pincode.getHint().toString().equals("")){
				item_code= item_code+"1";
				pincode.setText(item_code);
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
			}
			if(pincode.getAdapter() == null){
				submit_t= submit_t+"1";
				double t= Double.parseDouble(submit_t);
				String s1= df.format(t);
				submit_amount= Double.parseDouble(s1);
				
			}else
				linear.removeAllViews();
			
		}else if(v== comma){
			counter_reset();
			item_code= item_code+",";
			pincode.setText(item_code);
			int position = pincode.length();
			Selection.setSelection(pincode.getText(), position);
			if(pincode.getAdapter() == null);
			else
				linear.removeAllViews();
		}*/
		else{
			counter_reset();
			String s = v.getTag().toString();
			System.out.println("pin......"+s);
			
			if((tab_voucher_selected || tab_returned_selected )&& s.equals("pin") && pincode.getHint().toString().equals("")){
				Button b = (Button)v;
				String buttonText = b.getText().toString();
				pincode.setText(pincode.getText().toString()+buttonText);
				reset_back_del_confirm();
				
			}else if(s.equals("pin") && pincode.getHint().toString().equals("")){
				reset_back_del_confirm();
				
				back.setText("");
				back.setBackgroundResource(R.drawable.bt_back_disable);
				back.setClickable(false);
				back.setEnabled(false);
				
				Button b = (Button)v;
				String buttonText = b.getText().toString();
				pincode.setFocusable(true);
				pincode.setFocusableInTouchMode(true);
				
				if(pincode.getAdapter() == null && (!discount_amount_selected && !discount_percent_selected)){
					
					if(pincode_concat.equals("") && submit_t.equals("")){
						pincode_concat= pincode_concat+""+buttonText;
						submit_t= submit_t+""+pincode_concat;
					
					}else if(pincode_concat.equals("") && !submit_t.equals("")){
						pincode_concat= pincode_concat+""+buttonText;
						submit_t= submit_t+"+"+buttonText;
					
					}else{
						pincode_concat=pincode_concat+""+buttonText;
						submit_t=submit_t+buttonText;
					}
					pincode.setText(submit_t);
					back.setText("Back");
	        		back.setBackgroundResource(R.drawable.btn_back);
	        		back.setOnClickListener(this);
	        		back.setClickable(true);
	        		back.setEnabled(true);
					
				}else{
					if(discount_amount_selected ||discount_percent_selected){
						if(pincode.getText().toString().equals("0"))
							item_code= ""+buttonText;
						else 
							item_code=pincode.getText().toString()+""+buttonText;
						pincode.setText(""+item_code);
						
					}else{
						item_code= item_code+""+buttonText;
						System.out.println("pin......"+item_code);
						// call new item search
						item_call(item_code);
						
						pincode.setText(item_code);
						pincode.setAdapter(adapter);
						item_code_selected= true;
					}
					item_code_selected= true;
				}
				//System.out.println("value write in  null pin"+submit_t+ submit_amount+" pin concat"+pincode_concat);
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
				
			}else{
				
				if(s.startsWith("qty")){
					index_add_dec_qty= 0;
					add_dec_qty= 0;
					
					String tag_id= s.substring(3, s.length());
					System.out.println("row index....."+tag_id+""+  tag_id.length());
					
					item_code="";
					counter_reset();
					reset_back_del_confirm();
					keypad_block();
					
					del.setBackgroundResource(R.drawable.bt_delete_disable);
					del.setText("");
					del.setEnabled(false);
					del.setClickable(false);
					discount_amount_selected= false;discount_percent_selected= false;
					deleteRowIndex=0;
					
					if(tab_returned_selected){
						System.out.println("text qty returned row...");
					}else{
						
						resetPaymentTabs();
						
						int index_row= Integer.parseInt(tag_id);
						View child = tl.getChildAt(index_row);
						TableRow row = (TableRow) child;
						String itemcode=((TextView)row.getChildAt(KEY_COLUMN_ITEM_CODE)).getText().toString();
						String item_qty=((TextView)row.getChildAt(KEY_COLUMN_QTY)).getText().toString();
						pincode.setAdapter(null);
						//pincode.setText(""+itemcode);
						//int position = pincode.length();
						//Selection.setSelection(pincode.getText(), position);
						pincode.setHint("     ");
						
						
						String available_qty_row= order_items.get(index_row-1).get(KEY_ITEM_AVAILABLE_QUANTITY);
						System.out.println("row index....."+tag_id+" \nlist order row "+ index_row+"\n item selected qty"+ item_qty+"\n avail able qty"+ available_qty_row+"\n item_code: "+ itemcode);
						
						if(row_selector>0)
							reset_highlight_Selected_Row(row_selector);
						
						showQuantity(index_row, item_qty, available_qty_row);
						
						row_selector= index_row;
						highlight_Selected_Row(row_selector, s);
						
						itemcode="";
						if(!payment.getText().toString().equals("")){
							payment.setText("0,00");
							submit_amount=0.00;
							submit_t="";
							change.setText("0,00");
							pincode.setAdapter(adapter);
						}else{
							submit_amount=0.00;
							submit_t="";
						}
						payment_text.setText("Payment");
					}
					
				}else if(s.startsWith("discount")){
					
					String s1= s.substring(8, 9);
					System.out.println("index....."+s1+""+  s1.length());
					//transaction_id= s.substring(10, s.length());
					//System.out.println("trans....."+ transaction_id);
					
					
					counter_reset();
					reset_back_del_confirm();
					keypad_reset();
					deleteRowIndex=0;
					if(tab_returned_selected){
						System.out.println("text discount returned row...");
					
					}else{
						
						resetPaymentTabs();
						
						pincode.setFocusable(true);
						pincode.setFocusableInTouchMode(true);
						pincode.setAdapter(null);
						d_percentage.setBackgroundResource(R.drawable.dis_perc_bt);
						d_rupee.setBackgroundResource(R.drawable.dis_bt);
						int index= Integer.parseInt(s1);
						View child = tl.getChildAt(index);
						TableRow row = (TableRow) child;
						String type= ((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getText().toString();
						String typefull= ((TextView)row.getChildAt(KEY_COLUMN_DIS_TYP)).getText().toString();
						
						item_code=""+type.substring(0, type.length()-1);
						pincode.setText(""+item_code);
						//item_code="";
						type= type.substring(type.length()-1, type.length());
						//System.out.println(type+"..........."+typefull);
						
						int position = pincode.length();
						Selection.setSelection(pincode.getText(), position);
						
						item_code=((TextView)row.getChildAt(KEY_COLUMN_ITEM_CODE)).getText().toString();
						
						showDiscount(s1, item_code, type, typefull, s);
						//item_code="";
						
						if(!payment.getText().toString().equals("")){
							payment.setText("0,00");
							submit_amount=0.00;
							submit_t="";
							change.setText("0,00");
							//pincode.setAdapter(adapter);
						}else{
							submit_amount=0.00;
							submit_t="";
						}
						payment_text.setText("Payment");
					}
				}else if(s.startsWith("itemcode")){
					//itemcode1/6
					
					String s1= s.substring(8, s.length());
					System.out.println("index....."+s1+""+  s1.length());
					//transaction_id= s.substring(10, s.length());
					//System.out.println("trans....."+ transaction_id);
					counter_reset();
					resetPaymentTabs();
					reset_back_del_confirm();
					keypad_reset();
					discount_amount_selected= false;discount_percent_selected= false;
					
					if(row_selector>0)
						reset_highlight_Selected_Row(row_selector);
					
					
					
					deleteRowIndex= Integer.parseInt(s1);
					//System.out.println("index....."+deleteRowIndex);
					
					row_selector= deleteRowIndex;
					
					highlight_Selected_Row(row_selector, s);
					pincode.setFocusable(true);
					pincode.setFocusableInTouchMode(true);
					pincode.setAdapter(null);
					pincode.setHint("");
					
					linear.removeAllViews();
	        		linear.addView(inflateLayout);
	        		
					item_code_selected=true;
					item_code="";
					layout.setVisibility(View.INVISIBLE);
					
					if(!payment.getText().toString().equals("")){
						payment.setText("0,00");
						submit_amount=0.00;
						submit_t="";
						change.setText("0,00");
						pincode.setAdapter(adapter);
						
					}else{
						submit_amount=0.00;
						submit_t="";
					}
					payment_text.setText("Payment");
				}
			}
		}
	}// on click finish

	@Override
	public void onPause() {
		countDownTimer.cancel();
		//delayhandler.removeCallbacksAndMessages(null);
	    super.onPause();
	}
	public void onResume() {
		super.onResume();
		counter_reset();
	}
	@Override
    protected void onDestroy() {
		System.out.println("counter stop when app exit");
        countDownTimer.cancel();
        db_items.closeDB();
        System.gc();
        super.onDestroy();
	}
	
	public static Handler mHandler;
	
	
	
	
	
	/*private void initHandler(){
		
		mHandler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	        	System.out.println("init handler once call...");
	            
            	System.out.println("init handler1111111111...");
            	if (!timerHasStarted) {
        			countDownTimer.start();
        			timerHasStarted = true;
        			System.out.println("start counter in handler"+String.valueOf(startTime / 1000));
        		
            	}else{
					countDownTimer.cancel();
					timerHasStarted = false;
				//	System.out.println("stop again touch on button...");
					System.out.println("re start counter in handler ...................");
					countDownTimer.start();
					timerHasStarted = true;
				}
	        }
	    };
	}
	
	*/
	
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
			//System.out.println("callllll......");
			//Login_POPUP myObject = new Login_POPUP(MainActivity.this, Global.active_user);
			//boolean a=
			//		myObject.showDialog();
			
		}
		@Override
		public void onTick(long millisUntilFinished) {
			//System.out.println("lock remaining time...");
			//System.out.println("" + millisUntilFinished / 1000);
		}
	}
}