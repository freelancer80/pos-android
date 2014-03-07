package pos.main.ui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pos.main.adapters.CustomAutoCompleteTextView;
import pos.main.adapters.TransparentProgressDialog;
import pos.main.database.DeliveryDatabase;
import pos.main.database.Items_Database;
import pos.main.database.Transaction_Tables;
import pos.main.model.DeliveryNotes;
import pos.main.model.Global;
import pos.main.model.Items;
import pos.main.model.Transaction;
import pos.main.syncConnection.MySqlConnection;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class Items_Notes extends Activity implements OnClickListener {
	
	
	private static final String KEY_USER_ID = "user_id";
	private static final String KEY_NOTE_ID = "note_id";
	private static final String KEY_CMS_ID = "cms_id";// barcode
	private static final String KEY_STATUS_ID = "status_id";
	
	private static final String KEY_QTY = "quantity";
	private static final String KEY_ITEM_ID = "item_id";
	
	
	private static final String KEY_DELIVERY_DATE= "delivery_date";
	private static final String KEY_SERVER_RESPONCE = "server_response";
	private static final String KEY_RECEIVED_QTY = "received_quantity";
	private static final String KEY_COMMENT = "comment";
	private static final String KEY_COMPANY_NUMBER = "company_number";
	private static final String KEY_BRANCH_NUMBER = "branch_number";
	
	
	private static int KEY_COLUMN_SR = 0;
	private static int KEY_COLUMN_NOTE_ID = 1;
	private static int KEY_COLUMN_DELIVERY_DATE = 2;
	private static int KEY_COLUMN_STATUS =3;
	//private static int KEY_COLUMN_BRANCH_NUMBER =2;
	
	//private static int KEY_COLUMN_QTY = 4;
	
	
	private static int KEY_COLUMN_LENGHT = 4;
	
	
	String active_user; int start_counter=0;
	
	
	CustomAutoCompleteTextView pincode;
	Button one, two, three, four, five, six, seven, eight, nine, zero, z_zero;
	Button confirm, del, back;
	
	Button park, unpark, discount, search, comma;
	TextView subtotal, t_discount,tax, payment, payment_text,alternate, alternate_text, change, total_payment;
	TextView l_heading;
	
	boolean discount_amount_selected=false;
	boolean discount_percent_selected=false;
	boolean item_code_selected=false, item_code_selected_receipt= false;
	
	boolean
			tab_delivery_selected=false, 
			tab_returned_selected=false, login_pop= false;
	
	Button deliveries_icon, returns_icon, date_to, date_from;//, time_from, time_to;
	
	
    int trHeight = 0;
    int  size=15;
    DecimalFormat df = new DecimalFormat("0.00");
	String item_code="";
	
	ArrayList<HashMap<String, String>> item_list = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> searchResults;
	
	
	TableLayout tl, tl_delivery;		TableRow tr_head;
	boolean countr= false;
	String itemcode_selected="code", itemname_selected="name", item_price="price", item_discount="discount", item_quantity="";
	int item_img;
	LinearLayout linear;
	
	LinearLayout inflateLayout;		LinearLayout qty_layout; 	LinearLayout discount_layout; 		RelativeLayout layout;
	RelativeLayout layoutPayment;	LinearLayout cashLayout, cardLayout, voucher_layout, returned_layout;
	LinearLayout paid_withchange_Layout, paid_Layout;
	
	RelativeLayout layoutPaymentPaid;
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
	
	DeliveryDatabase db_items;
	private Calendar mCalen;
    private int day, min_day;
    private int month, min_month;
    private int year,min_year;
    
    String date_to1, date_from1;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		pd = new TransparentProgressDialog(getApplicationContext(), R.drawable.spinner2);
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		};
		
		countDownTimer = new MyCountDownTimer(startTime, interval);
		db_items = new DeliveryDatabase(getApplicationContext());
		
		mCalen = Calendar.getInstance();
        day = mCalen.get(Calendar.DAY_OF_MONTH);
        month = mCalen.get(Calendar.MONTH);
        year = mCalen.get(Calendar.YEAR);
		
		final float scale = getResources().getDisplayMetrics().density; 
        trHeight = (int) (34 * scale + 0.5f);
        
		linear= (LinearLayout) findViewById(R.id.linearLayout3);
		//inflateLayout = (LinearLayout)View.inflate(this, R.layout.qty_discount, null);
		//layoutPayment = (RelativeLayout)View.inflate(this, R.layout.currency, null);
		//layoutPaymentPaid = (RelativeLayout)View.inflate(this, R.layout.paid_message, null);
		pincode= (CustomAutoCompleteTextView) findViewById(R.id.itemcode);
		pincode.setText("");
		pincode.setInputType(InputType.TYPE_NULL);
		
        init();
       // createTable();
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
		
		item_image= (ImageView)findViewById(R.id.payment_head);
		item_image.setBackgroundResource(R.drawable.notes_heading);
		
		returns_icon= (Button) findViewById(R.id.returns_icon);
		deliveries_icon= (Button) findViewById(R.id.deliveries_icon);
		
		date_from= (Button) findViewById(R.id.date_from);
		date_to= (Button) findViewById(R.id.date_to);
		
		//time_from= (Button) findViewById(R.id.time_from);
		//time_to= (Button) findViewById(R.id.time_to);
		
		date_from.setOnClickListener(this);
		date_to.setOnClickListener(this);
		
		//time_from.setOnClickListener(this);
		//time_to.setOnClickListener(this);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd , yyyy");
		Calendar calendar = new GregorianCalendar(year,month,day);	
		
		System.out.println("Date : " + sdf.format(calendar.getTime()));
		
		date_to.setText(""+sdf.format(calendar.getTime()));
		date_from.setText(""+sdf.format(calendar.getTime()));
		month= month+1;
		System.out.println(String.format("%02d", month));
		
		String dateS=String.format("%02d", month);//""+year+"-"+monthOfYear+"-"+(dayOfMonth)+" 23:59:59";
		String date_d=String.format("%02d", day);
		
		
		date_to.setTag(""+year+"-"+dateS+"-"+(date_d)+" 23:59:59");
		date_from.setTag(""+year+"-"+dateS+"-"+(date_d)+" 00:00:00");
		System.out.println("Date : " + date_to.getTag().toString());
		
		
		deliveries_icon.setBackgroundResource(R.drawable.deliveries_icon_sel);
		returns_icon.setBackgroundResource(R.drawable.returns_icon);
		item_image= null;
		item_image= (ImageView)findViewById(R.id.payment_head);
		item_image.setBackgroundResource(R.drawable.deliveries_notes_heading);
		
		displayDelivery(date_from.getTag().toString(), date_to.getTag().toString());
		
		setTableValues();
		
		
		
		
		
		//time_from.setText("00:00:00");
		//time_to.setText("23:59:59");
		
		//returns_icon.setOnClickListener(this);
		deliveries_icon.setOnClickListener(this);
		
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

      
        TextView rate = new TextView(this);
        rate.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        rate.setId(size);
        rate.setText("Deleivery No.");  
        rate.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        rate.setTextColor(Color.parseColor("#800080"));
        rate.setPadding(5, 5, 5, 5);
        rate.setHeight(trHeight);
        rate.setTextSize(size);
        rate.setTypeface(null, Typeface.BOLD);
        tr_head.addView(rate);
        
       
        TextView discount =  new TextView(this);
        discount.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        discount.setId(size);
        discount.setText("Delivery Date"); 
        discount.setTextColor(Color.parseColor("#800080")); 
        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        discount.setPadding(0, 5, 0, 5); 
        discount.setHeight(trHeight);
        discount.setTextSize(size);
        discount.setTypeface(null, Typeface.BOLD);
        tr_head.addView(discount);
        
        discount =  new TextView(this);
        discount.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
        discount.setId(size);
        discount.setText("Status"); 
        discount.setTextColor(Color.parseColor("#800080")); 
        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        discount.setPadding(0, 5, 0, 5); 
        discount.setHeight(trHeight);
        discount.setTextSize(size);
        discount.setTypeface(null, Typeface.BOLD);
        tr_head.addView(discount);
       
        tl.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
        
        for(int r=1; r<17; r++){
        	
        	tr_head = new TableRow(Items_Notes.this);
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
        		if(col==KEY_COLUMN_NOTE_ID){
        			text.setText("198.00");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_DELIVERY_DATE){
        			text.setText("1000,00");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_STATUS){
        			text.setText("1000,00");
        			text.setTextColor(Color.TRANSPARENT);
        			text.setTag(""+r);
        		}
        		
        		if(col==KEY_COLUMN_SR);
        		else
        			text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		tr_head.addView(text);
			}
        	tl.addView(tr_head);
  	 	}
	}
	//3rd
	private void counter_reset() {
		
		if (!timerHasStarted) {
			countDownTimer.start();
			timerHasStarted = true;
			   
		} else{
			countDownTimer.cancel();
			countDownTimer.start();
		}
	}
	//4th
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
	//5th
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
	
	Dialog dialog;
	private TransparentProgressDialog pd;
	Runnable r;
	Button save;
	
	private void popUp(String index) {
		
		dialog = new  Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
		dialog.getWindow().setContentView(R.layout.delivery_received);
		
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		save = (Button) dialog.findViewById(R.id.save);
		save.setText("Save");
		save.setClickable(true);
		save.setEnabled(true);
		save.setBackgroundResource(R.drawable.returned_qty_box_confirm_bt);
		Button cancel= (Button) dialog.findViewById(R.id.cancel);
		TextView branchNo, companyNo;
		final TextView note_id;
		TextView delivery_date;
		
		branchNo= (TextView) dialog.findViewById(R.id.branch_no);
		companyNo= (TextView) dialog.findViewById(R.id.company_no);
		note_id= (TextView) dialog.findViewById(R.id.note_id);
		delivery_date= (TextView) dialog.findViewById(R.id.delivery_date);
		
		tl_delivery= (TableLayout) dialog.findViewById(R.id.purchase_items);
		tl_delivery.removeAllViews();
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		View child = tl.getChildAt(Integer.parseInt(index));
		TableRow row = (TableRow) child;
		String itemcode=((TextView)row.getChildAt(KEY_COLUMN_NOTE_ID)).getText().toString();
		String status_id="0";
		
		db_items = new DeliveryDatabase(getApplicationContext());
		//item_list= null;
		final ArrayList<HashMap<String, String>> item_list = new ArrayList<HashMap<String, String>>();
		List<DeliveryNotes> allItems = db_items.getSelectedNoteID(itemcode);
		
		for (final DeliveryNotes items : allItems) {
			
			System.out.println("1."+items.getNote_id()+"\n.........."+ items.getId()
					+"\n.........."+ items.getItem_id()
					+"\n.........."+ items.getCms_id()
					+"\n.........."+ items.getNote_id()
					+"\n.........."+ items.getItem_id()
					+"\n.......... status id   "+ items.getStatus_id()
					+"\n.........."+ items.getCompany_number()
					+"\n.........."+ items.getDelivery_date()
					+"\n.........."+ items.getBranch_number()
					+"\n.........."+ items.getQuantity());
					
			status_id=items.getStatus_id();
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", ""+items.getId());// del num
			map.put(KEY_NOTE_ID, items.getNote_id());// del num
			map.put(KEY_ITEM_ID, items.getItem_id());
			map.put(KEY_USER_ID, items.getUser_id());
			map.put(KEY_RECEIVED_QTY, items.getReceived_quantity());
			map.put(KEY_COMMENT, items.getComment());
			map.put(KEY_CMS_ID, items.getCms_id());
			map.put(KEY_STATUS_ID, items.getStatus_id());
			map.put(KEY_COMPANY_NUMBER,items.getCompany_number());
			map.put(KEY_DELIVERY_DATE, items.getDelivery_date());
			map.put(KEY_BRANCH_NUMBER, items.getBranch_number());
			map.put(KEY_QTY, items.getQuantity());
			
			item_list.add(map);
		}
		if(status_id.equals("1")){
			cancel.setText("Cancel");
			cancel.setBackgroundResource(R.drawable.returned_qty_box_cancel_bt);
			save.setBackgroundResource(R.drawable.returned_qty_box_confirm_bt);
			save.setVisibility(View.VISIBLE);
			
		}else if(status_id.equals("3")){
			cancel.setText("");
			cancel.setBackgroundResource(R.drawable.deliveries_cross_icon);
			save.setVisibility(View.INVISIBLE);
		}
		
		System.out.println(""+ item_list.size());
		
		branchNo.setText(""+ item_list.get(0).get(KEY_BRANCH_NUMBER));
		companyNo.setText(""+ item_list.get(0).get(KEY_COMPANY_NUMBER));
		note_id.setText(""+ item_list.get(0).get(KEY_NOTE_ID));
		delivery_date.setText(""+ item_list.get(0).get(KEY_DELIVERY_DATE));
		
		 qty1 = new ArrayList<EditText>();
		 comment1= new ArrayList<EditText>();
		 
		 setPopUPTable(item_list, status_id);
		 
		 
		 save.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				


				 InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				 mgr.hideSoftInputFromWindow(save.getWindowToken(), 0);


				// InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				//	imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					
				 save.setText("Saving");
				 save.setClickable(false);
				 save.setEnabled(false);
				 save.setBackgroundResource(R.drawable.delivery_note_detail_save_sel);
				 
				 System.out.println(".... save....");
				 
				 notes_qty_receive= false;
				 //pd.show();
				 System.out.println(".... size...."+qty1.size());
				 
				 for(int i=0; i < qty1.size(); i++){
					 String a=qty1.get(i).getText().toString();
					 String comnt= comment1.get(i).getText().toString();
					 if(a.length()>0){
						 int qrt_rev =  Integer.parseInt(a);
						 int qty_sent= Integer.parseInt(item_list.get(i).get(KEY_QTY));
						
						 System.out.println(qrt_rev+".... values ...."+qty_sent+" &&  comnt.length()>0" + comnt.length());
						 
						 if((qty_sent< qrt_rev || qty_sent > qrt_rev ) && comnt.length() == 0){
							 System.out.println(qrt_rev+".... values wrong...."+qty_sent);
							 notes_qty_receive= true;
					//		 save.setText("Save");
						//	 save.setClickable(true);
						//		save.setEnabled(true);
								//save.setBackgroundResource(R.drawable.returned_qty_box_confirm_bt);
							 invalidUserPopup(""+(i+1), "");
							 break;
						 }		
					 }
				 }
				
				 Transaction_Tables db_trans= new Transaction_Tables(getApplicationContext());
				 
				 JSONArray server_json= new JSONArray();
				 JSONArray server_json_trans= new JSONArray();
				 JSONObject obj = null;
				 String [] ids_= new String[qty1.size()];
				 
				 // String server_request="";
				 
				 if(!notes_qty_receive){
					 
				 System.out.println(".... size...."+qty1.size());
				
				 for(int i=0; i < qty1.size(); i++){
					 
					 
					 String qrt_rev =qty1.get(i).getText().toString();
					 String comny_rec= comment1.get(i).getText().toString();
					 String id=item_list.get(i).get("id");
					 String date_time= getDateTime1();
					 String cms_id=item_list.get(i).get(KEY_CMS_ID);
					 String item_id=item_list.get(i).get(KEY_ITEM_ID);
					 
					 if(qrt_rev.equals(""))
						 qrt_rev="0";
					 
					 System.out.println(i+"\n..qty.."+qrt_rev+"\n  comment   "+ comny_rec+ "\n id....."+ id+ "\n......."+date_time +"\n cms id"+ cms_id);
					 
					 DeliveryNotes delvery= new DeliveryNotes(Integer.parseInt(id), qrt_rev,comny_rec, date_time);
					 int ids=db_items.updateItems(delvery);
					 
					 if(ids>0){
						 Items_Database db_item= new Items_Database(getApplicationContext());
	//2003021200					
						 Items items_data= db_item.getItemRow(item_id);
						 
						 String available_qty1=items_data.getAvailable_quantity();
						 String t_qty=items_data.getTotal_qty();
						 
						 int available_qty= Integer.parseInt(qrt_rev)+Integer.parseInt(available_qty1);
						 int total_qty= Integer.parseInt(qrt_rev)+Integer.parseInt(t_qty);
						 
						 int ids2=  db_item.updateQuantity(""+available_qty, ""+total_qty, item_id);
						 db_item.closeDB();
						 
						 if(ids2>0){
							 //transaction type id is 1
							 // inward and out ward is 2
							Transaction items_trans= new Transaction("1", qrt_rev , item_id, 
									"","", "3", date_time, date_time, "delivery_notes", id,// delivery table note id 
									"", "", "", 
									items_data.getDecs1(),items_data.getDecs2(), items_data.getDecs3() ,
									items_data.getSupplier_number(), items_data.getSupplier_item_number(),
									items_data.getBarcode(),
									items_data.getGroup_id(),items_data.getColor_id(), items_data.getSize_id(),
									items_data.getBuying_price(),items_data.getSelling_price(),items_data.getTaxation_code());
							
							items_trans.setUser_id(""+Global.user_id);
							items_trans.setItem_cms_id(""+items_data.getId());
							
							ids_[i]= item_list.get(i).get(KEY_CMS_ID);
							if(qrt_rev.equals("0")){
								obj = new JSONObject();
								try{
									obj.put("user_id", Global.user_id);
									obj.put("cms_id", item_list.get(i).get(KEY_CMS_ID));
									obj.put("received_at", date_time);
									obj.put("received_quantity", qrt_rev);
									obj.put("comments", comny_rec);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								server_json.put(obj);
							}else{
								 
								long t_ids=db_trans.createItems(items_trans);
								if(t_ids>0){
									try{
										obj = new JSONObject();
										obj.put("pos_id", t_ids);
										obj.put("transaction_type_id", "1");
										obj.put("quantity", qrt_rev);
										obj.put("item_id", item_id);
										obj.put("item_cms_id", ""+items_data.getId());
										obj.put("user_id", Global.user_id);
										obj.put("receipt_number_id", "");
										obj.put("order_number_id", "");
										obj.put("status_id", "3");
										
										obj.put("created_at", date_time);
										obj.put("updated_at", date_time);
										
										obj.put("parent_type", "delivery_notes");
										obj.put("parent_type_id", id);
										
										obj.put("sold_price", "");
										obj.put("discount_value", "");
										obj.put("discount_type_id", "");
							                
										obj.put("description1", items_data.getDecs1() );
										obj.put("description2", items_data.getDecs2() );
										obj.put("description3", items_data.getDecs3() );
							                
										obj.put("supplier_number", items_data.getSupplier_number());
										obj.put("supplier_item_number", items_data.getSupplier_item_number());
										
										obj.put("ean", items_data.getBarcode());
										
										obj.put("group", items_data.getGroup_id());
										obj.put("color", items_data.getColor_id());
										obj.put("size", items_data.getSize_id());
							                
										obj.put("buying_price", items_data.getBuying_price());
										obj.put("selling_price", items_data.getSelling_price());
										obj.put("taxation_code", items_data.getTaxation_code()); 
										// obj.put("comments", comny_rec);
										server_json_trans.put(obj);
							             
										obj = new JSONObject();
										obj.put("cms_id", item_list.get(i).get(KEY_CMS_ID));
										obj.put("received_at", date_time);
										obj.put("received_quantity", qrt_rev);
										obj.put("comments", comny_rec);
										obj.put("user_id", Global.user_id);
										server_json.put(obj);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
							        }
									db_items.closeDB();
								}
							 }
						}
				    }
				}////////////////////
				
				
				if(server_json.length()>0){
					
					ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
					 
					postparameters2send.add(new BasicNameValuePair("shop_id",Global.shop_id));
					postparameters2send.add(new BasicNameValuePair("response_json", ""+server_json));
					postparameters2send.add(new BasicNameValuePair("server_json_trans", ""+server_json_trans));
					 
					System.out.println("json array..........."+server_json);
					System.out.println("json array..........."+server_json_trans);
					 // postparameters2send.add(new BasicNameValuePair("comments", comny_rec));
					 // postparameters2send.add(new BasicNameValuePair("cms_id", item_list.get(i).get(KEY_CMS_ID)));
					 // postparameters2send.add(new BasicNameValuePair("received_at", date_time));
					 // postparameters2send.add(new BasicNameValuePair("user_id", "1"));
					 //item_list.get(i).get(KEY_USER_ID)
					 
					 String result = "";
					 try {
						 result = MySqlConnection.executeHttpPost(Global.domain+"/b2c.php/pScripts/syncDeliveryNotesReceived", postparameters2send);
					 } catch(Exception e){
						 e.printStackTrace();
					 }
					 if(result.length()>0){
						 
						int ids1=db_trans.isSyncedTrans(result);
						System.out.println("responce id..........."+ids1);
						db_trans.closeDB();
						
						String a=  Arrays.toString(ids_);
						a= a.substring(1, a.length()-1);
						System.out.println("responce id..........."+a);
						ids1=db_items.updateItemsServer(a);
						System.out.println("responce id..........."+ids1);
						
						db_trans.closeDB();
						
						if(ids1!=0){
							successfully_save_notes();
						}
					}
				}
				dialog.dismiss();
				 System.out.println(".... save...."+save.getText().toString());
				}
				
			 } 
				// pd.dismiss();
		});
		 
		 
		db_items.closeDB();
	}
	
	private void displayDelivery(String date_from, String date_to) {
		db_items = new DeliveryDatabase(getApplicationContext());
		item_list= null;
		item_list = new ArrayList<HashMap<String, String>>();
		List<DeliveryNotes> allItems = db_items.getAllItems(date_from, date_to);//etAllItems(""+start_Limit, ""+end_Limit);
		
		for (final DeliveryNotes items : allItems) {
			
					System.out.println("1."+items.getNote_id()+"\n.........."+ items.getId()
					+"\n.........."+ items.getItem_id()
					+"\n.........."+ items.getStatus_id()
					+"\n.........."+ items.getCompany_number()
					+"\n. synced........."+ items.getIsSynced()
					+"\n..net value ........"+ items.getNetValue()
					+"\n...received qty......."+ items.getReceived_quantity()
					+"\n...qtyn ......."+ items.getQuantity());
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", ""+items.getId());// del num
			map.put(KEY_NOTE_ID, items.getNote_id());// del num
			map.put(KEY_ITEM_ID, items.getItem_id());
			map.put(KEY_COMPANY_NUMBER,items.getCompany_number());
			
			map.put("net",items.getNetValue());
			map.put(KEY_SERVER_RESPONCE, items.getIsSynced());
			
			map.put(KEY_BRANCH_NUMBER, items.getBranch_number());
			map.put(KEY_STATUS_ID, items.getStatus_id());
			map.put(KEY_COMMENT, items.getComment());
			map.put(KEY_RECEIVED_QTY, items.getReceived_quantity());
			map.put(KEY_QTY, items.getQuantity());
			
			String date=items.getDelivery_date();
			System.out.println(date);
			Date date1;
			
			SimpleDateFormat  format = new SimpleDateFormat("y-M-d HH:mm:ss");
			try {
			    date1 = format.parse(date);  
			    System.out.println(date1.toString());  
			    SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd , yyyy");	
					
				
				String a=sdf1.format(date1);
				map.put(KEY_DELIVERY_DATE, a);
				System.out.println(a);
			    
			    
			} catch (ParseException e) {  
			    // TODO Auto-generated catch block  
			    e.printStackTrace();  
			}
			//map.put(KEY_ITEM_SIZE, ""+imagearr[items.getId()-1]);
			
			item_list.add(map);
		}
		
		db_items.closeDB();
		System.out.println(" list size...."+ item_list.size());
	}
	
	@SuppressWarnings("deprecation")
	private void setTableValues() {
		start_counter=0;
		
		System.out.println(" list size...."+ item_list.size());
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

	        TextView rate = new TextView(this);
	        rate.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        rate.setId(size);
	        rate.setText("Deleivery No.");  
	        rate.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        rate.setTextColor(Color.parseColor("#800080"));
	        rate.setPadding(5, 5, 5, 5);
	        rate.setHeight(trHeight);
	        rate.setTextSize(size);
	        rate.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(rate);
	        
	        TextView discount =  new TextView(this);
	        discount.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        discount.setId(size);
	        discount.setText("Delivery Date"); 
	        discount.setTextColor(Color.parseColor("#800080")); 
	        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount.setPadding(0, 5, 0, 5); 
	        discount.setHeight(trHeight);
	        discount.setTextSize(size);
	        discount.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount);
	        
	        discount =  new TextView(this);
	        discount.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        discount.setId(size);
	        discount.setText("Status"); 
	        discount.setTextColor(Color.parseColor("#800080")); 
	        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount.setPadding(0, 5, 0, 5); 
	        discount.setHeight(trHeight);
	        discount.setTextSize(size);
	        discount.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount);
	        tl.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
	        
	        
		int length= item_list.size();
		if(length<17)
			length= 17;
	    
        for(int r=1; r<length; r++){
        	
        	tr_head = new TableRow(Items_Notes.this);
        	tr_head.setId(r);
        	tr_head.setTag(r);
	 		
        	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
        		
        		TextView text = new TextView(this);
        		text.setHeight(trHeight);
        		text.setTextSize(size);
        		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		
        		/*
        		 * iemt in table insert......
        		 */
        		
        		int i= r-1;
        		if(i<item_list.size()){
        			
        			if(item_list.get(i).get(KEY_STATUS_ID).equals("1")){
        				text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_red));
        			
        			}else if(item_list.get(i).get(KEY_STATUS_ID).equals("3")){
        				
        				if(item_list.get(i).get("net").equals("0"))
        					text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_green));
        				else 
        					text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_yellow));
        			}
        			
        			
        			text.setTextColor(Color.parseColor("#800080"));
        			text.setId(Integer.parseInt(item_list.get(i).get("id")));
        			text.setTag(""+i);
        			
        			
        			if(col== KEY_COLUMN_NOTE_ID || col== KEY_COLUMN_DELIVERY_DATE || col==KEY_COLUMN_SR){
        				
        				if(col==KEY_COLUMN_NOTE_ID){
                			text.setText(""+item_list.get(i).get(KEY_NOTE_ID));
                			text.setTag("delivery"+r);
                			
                		}else if(col==KEY_COLUMN_DELIVERY_DATE){
                			text.setText(""+item_list.get(i).get(KEY_DELIVERY_DATE));
                			text.setTag("delivery"+r);
                		
                		}else{
                			text.setText(""+r);
                			text.setTag("delivery"+r);
                		}
        				if(col==KEY_COLUMN_SR);
                		else
                			text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        				
        				if(col== KEY_COLUMN_DELIVERY_DATE ||col== KEY_COLUMN_NOTE_ID||col==KEY_COLUMN_SR)
    	        			text.setOnClickListener(Items_Notes.this);
        				
        				tr_head.addView(text);
        				
        				
        			}else if(col==KEY_COLUMN_STATUS){
        				ImageView image= new ImageView(this);
        				
        				if(item_list.get(i).get(KEY_STATUS_ID).equals("1")){
        					image.setPadding(0, 4, 0, 3);
        					image.setImageResource(R.drawable.yellow_cauation);
        					image.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_red));
        					
        				}else if(item_list.get(i).get(KEY_STATUS_ID).equals("3")){
        					image.setPadding(0, 2, 0, 2);
        					if(item_list.get(i).get("net").equals("0")){
        						image.setImageResource(R.drawable.green);
        						image.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_green));
        					}else {
        						image.setImageResource(R.drawable.yellow);
        						image.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_yellow));
        					}
        						
        				}
        				//image.setOnClickListener(this);
        				image.setTag(""+r);
        				tr_head.addView(image);
        				
        			}
        			
        			
        			
        		}else{
        			
        			if(r%2==1){
            			text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient_pink));
                	}else{
                		text.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_gradient));
                	}
        			if(col==KEY_COLUMN_NOTE_ID){
            			text.setText("198.00");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_DELIVERY_DATE){
            			text.setText("1000,00");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_STATUS){
            			text.setText("1000,00");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            		}else{
            			//text.setText(""+start_counter);
            			text.setTag(""+r);
            		}
        			
            		if(col==KEY_COLUMN_SR);
            		else
            			text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
            		tr_head.addView(text);
            		}
        		}
        	tl.addView(tr_head);
        	start_counter++;
        	//System.out.println("start counter........."+start_counter);
  	 	}
	
	}
	
	EditText qty, comment;
	List<EditText> qty1, comment1;
	boolean notes_qty_receive= false;
	
	@SuppressWarnings("deprecation")
	private void setPopUPTable(final ArrayList<HashMap<String, String>> item_list , String status_id ) {
		
		start_counter=0;
		tl_delivery= (TableLayout) dialog.findViewById(R.id.purchase_items);
		tl_delivery.removeAllViews();
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
	        item_name.setText("Sent Quantity");  
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
	        rate.setText("Received Quantity");  
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
	        discount_type.setText("Comments"); 
	        discount_type.setTextColor(Color.parseColor("#800080")); 
	        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount_type.setPadding(5, 5, 5, 5); 
	        discount_type.setHeight(trHeight);
	        discount_type.setTextSize(size);
	        discount_type.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount_type);
	        tl_delivery.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
	        
		int length= item_list.size();
		System.out.println("length......"+ length);
		if(length<11)
			length= 11;
	    
        for(int r=1; r<length+1; r++){
        	tr_head = new TableRow(Items_Notes.this);
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
        		/*
        		 * iemt in table insert......
        		 */
        		
        		final int i= r-1;
        		if(i<item_list.size()){
        			text.setTextColor(Color.parseColor("#800080"));
        			text.setTag(""+i);
    			
    			if(col==1 ||col==2){
    				
    				if(col==1){
    					text.setText(""+item_list.get(i).get(KEY_ITEM_ID));
            			text.setTag(""+r);
            		}else if(col==2){
            			text.setText(""+item_list.get(i).get(KEY_QTY));
            			text.setTag(""+r);
            		}
    				tr_head.addView(text);
    				
        		}else if(col==3|| col== 4){
        			//String status=item_list.get(i).get(KEY_QTY);
        			if(status_id.equals("1")){
        				//System.out.println("status.................. "+status_id);
        				if(col==3){
            				qty= new EditText(this);
            				qty.setInputType(InputType.TYPE_CLASS_NUMBER);
            				qty.setId(r);
            				
            				if(r==1){
            					qty.requestFocus();
            				}
            				
            				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
							
                	       /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                	        imm.hideSoftInputFromWindow(qty.getWindowToken(), 0);
                			
                			qty.setOnFocusChangeListener(new OnFocusChangeListener() {
        						@Override
        						public void onFocusChange(View v, boolean hasFocus) {
        							System.out.println("edite text focus");
        							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        							imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        							//System.out.println(item_list.get(i).get(KEY_QTY)+"value..............."+ qty.getText().toString());
        							
        							if(i>0){
        								
        								System.out.println(item_list.get(i-1).get(KEY_QTY)+".........value..............."
        							+ qty1.get(i-1).getText().toString());
        								
        								
        							}
        						}
        					});*/
                	        
            				qty.setText("");
            				qty.setTag(""+r);
            				qty.setBackgroundResource(R.drawable.deliveries_note_qty_input_field);
            				qty1.add(qty);
            				tr_head.addView(qty);
            				
            			}else if(col==4){
            				comment= new EditText(this);
                			comment.setInputType(InputType.TYPE_CLASS_TEXT);
                			comment.setBackgroundResource(R.drawable.deliveries_note_commt_input_field);
                			comment.setText("");
                			comment.setTag(""+r);
                			comment1.add(comment);
                			tr_head.addView(comment);
                			
                		}
        			}else{
        				///System.out.println("status.................. "+status_id);
        				if(col==3){
        					text.setText(""+item_list.get(i).get(KEY_RECEIVED_QTY));
                			text.setTag(""+r);
                		}else if(col==4){
                			text.setText(""+item_list.get(i).get(KEY_COMMENT));
                			text.setTag(""+r);
                		}
        				
        				tr_head.addView(text);
        			}
        			text.setTag(""+r);
        			
        		}else{
        			text.setText(""+r);
        			text.setTag(""+r);
        			tr_head.addView(text);
        		}
        		if(col==KEY_COLUMN_SR);
        		else
        			text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		
        		if( col== KEY_COLUMN_DELIVERY_DATE ||col== KEY_COLUMN_NOTE_ID ||col==KEY_COLUMN_SR)
        			;
    				//text.setOnClickListener(Items_Notes.this);
    			}
        		else{
        			if(col==KEY_COLUMN_NOTE_ID){
            			text.setText("198.00");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_DELIVERY_DATE){
            			text.setText("1000,00");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else{
            			//text.setText(""+start_counter);
            			text.setTag(""+r);
            		}
        			
            		if(col==KEY_COLUMN_SR);
            		else
            			text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
            		tr_head.addView(text);
            		}
        		}
        	tl_delivery.addView(tr_head);
        	start_counter++;
        	System.out.println("start counter........."+start_counter);
  	 	}
	
	
	}
	
	View view;
	DatePickerDialog mTimePicker= null;
	String pm="";
	 
	@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
	@Override
	public void onClick(View v) {
		
		if(v== back){
			counter_reset();
			pincode.setHint("");
			//pincode.setAdapter(adapter);
			pincode.setFocusable(true);
			pincode.setFocusableInTouchMode(true);
			pincode.requestFocus();
			
		}else if(v== confirm){
			counter_reset();
			
		}else if(v== del){
			counter_reset();
			item_code=pincode.getText().toString();
			item_code= item_code.substring(0, item_code.length()-1);
			pincode.setText(item_code);
			if(item_code.equals("")){
				block_back_del_confirm();
				item_code_selected= false;
			}
			
		}
		else if(v== deliveries_icon){
			deliveries_icon.setBackgroundResource(R.drawable.deliveries_icon_sel);
			returns_icon.setBackgroundResource(R.drawable.returns_icon);
			item_image= null;
			item_image= (ImageView)findViewById(R.id.payment_head);
			item_image.setBackgroundResource(R.drawable.deliveries_notes_heading);
			
			displayDelivery(date_from.getTag().toString(), date_to.getTag().toString());
			setTableValues();
			
		}else if(v== returns_icon){
			
			deliveries_icon.setBackgroundResource(R.drawable.deliveries_icon);
			returns_icon.setBackgroundResource(R.drawable.returns_icon_sel);
			item_image.setBackgroundResource(R.drawable.deliveries_notes_heading);
			
		}else if(v== date_from){
			// Process to get Current Date
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            	@SuppressLint("DefaultLocale")
				@Override
	            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            		
            		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd , yyyy");
            		
        			Calendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
        			System.out.println("Date : " + sdf.format(calendar.getTime()));
        			System.out.println("Date : " + year+monthOfYear+dayOfMonth);

        			//01-28 15:06:25.066: I/System.out(15012): 2013-10-19 00:00:00

        			date_from.setText(""+sdf.format(calendar.getTime()));
        			monthOfYear= monthOfYear+1;
        			
        			String dateS=String.format("%02d", monthOfYear);//""+year+"-"+monthOfYear+"-"+(dayOfMonth)+" 23:59:59";
        			
        			String date_d=String.format("%02d", dayOfMonth);
        			
        			min_day= Integer.parseInt(date_d);
        			min_month= Integer.parseInt(dateS);
        			min_year= year;
        		    
        			date_from.setTag(""+year+"-"+dateS+"-"+(date_d)+" 00:00:00");
        			
        			System.out.println("Date from: " + date_from.getTag().toString());
        			
        			 displayDelivery(date_from.getTag().toString(), date_to.getTag().toString());
        				setTableValues();
            		
            		//date_to.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
	            }
            }, year, month, day);
            
            dpd.show();
           
		}else if(v== date_to){
			// Process to get Current Date
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
 
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            	@SuppressLint("DefaultLocale")
				@Override
	            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            		
            		
            		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd , yyyy");
            		Calendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);	
        			
        			System.out.println("Date : " + sdf.format(calendar.getTime()));
        			System.out.println("Date : " + sdf.format(calendar.getTime()));
        			
        			date_to.setText(""+sdf.format(calendar.getTime()));
        			monthOfYear= monthOfYear+1;
        			
        			System.out.println(String.format("%02d", monthOfYear));
        			
        			String dateS=String.format("%02d", monthOfYear);//""+year+"-"+monthOfYear+"-"+(dayOfMonth)+" 23:59:59";
        			String date_d=String.format("%02d", dayOfMonth);
        			date_to.setTag(""+year+"-"+dateS+"-"+(date_d)+" 23:59:59");
        			System.out.println("Date : " + date_to.getTag().toString());
        			
        			displayDelivery(date_from.getTag().toString(), date_to.getTag().toString());
        			setTableValues();
        			
	            }
            	
            	//SELECT * FROM delivery_notes WHERE delivery_date between 
            	//'2014-02-3 00:00:00' and '2014-02-4 23:59:59' GROUP BY note_id

            }, year, month, day);/*{
            	@Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear,
                        int dayOfMonth) {
            		
            		
            		
            		System.out.println("----------onDateChanged()-----------"+ year + "--" + min_year);
                    System.out.println("----------onDateChanged()-----------"+ monthOfYear + "--" + min_month);
                    System.out.println("----------onDateChanged()-----------"+ dayOfMonth + "--" + min_day);

                    if ((min_year < year) || ((min_month < monthOfYear) && (min_year == year)) || ((min_day < dayOfMonth) && (min_year == year) && (min_month == monthOfYear))) {
                        view.updateDate(min_year, min_month - 1, min_day);

                    }

					//if ((maxYear > year) || ((maxMonth > monthOfYear) && (maxYear == year)) || ((maxDay > dayOfMonth) && (maxYear == year) && (maxMonth == monthOfYear))) {
                    //    view.updateDate(maxYear, maxMonth - 1, maxDay);
                    //}

            //Do the check here for the year
            		if ( year <= maxYear && month + 1 <= maxMonth && date <= maxDate ) //meets your criteria
                    {
                        view.updateDate(year, month, date); //update the date picker to selected date
                    }
                    else
                    {
                        view.updateDate(maxYear, maxMonth - 1, maxDate); // or you update the date picker to previously selected date
                    }

            }
            	
            };*/
            dpd.show();
            Calendar currentDate = Calendar.getInstance();
            long d= currentDate.getTimeInMillis();
            System.out.println("max date..........."+d);
            
            /*
             *  private TextView mDateDisplay;
    private int mYear;
    private int year1;
    private int mMonth;
    private int mDay;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 1;
    Button pickDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        pickDate = (Button) findViewById(R.id.pickDate);
        pickDate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("hello3");
                showDialog(DATE_DIALOG_ID);

            }
        });

        System.out.println("hello1");
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
        year1 = mYear;
        month = mMonth;
        day = mDay;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        DatePickerDialog _date = null;
        switch (id) {

        case DATE_DIALOG_ID:
            _date = new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                    mDay) {
                @Override
                public void onDateChanged(DatePicker view, int year,
                        int monthOfYear, int dayOfMonth) {
                    System.out.println("----------onDateChanged()-----------"
                            + mYear + "--" + year);
                    System.out.println("----------onDateChanged()-----------"
                            + mMonth + "--" + monthOfYear);
                    System.out.println("----------onDateChanged()-----------"
                            + mDay + "--" + dayOfMonth);
*/
                    /*
                     * These lines of commented code used for only setting the
                     * maximum date on Date Picker..
                     * 
                     * if (year > mYear && year) view.updateDate(mYear, mMonth,
                     * mDay);
                     * 
                     * if (monthOfYear > mMonth && year == mYear )
                     * view.updateDate(mYear, mMonth, mDay);
                     * 
                     * if (dayOfMonth > mDay && year == mYear && monthOfYear ==
                     * mMonth) view.updateDate(mYear, mMonth, mDay);
                     */

                    // these below lines of code used for setting the maximum as
                    // well as minimum dates on Date Picker Dialog..

              /*      if ((mYear > year)
                            || ((mMonth > monthOfYear) && (mYear == year))
                            || ((mDay > dayOfMonth) && (mYear == year) && (mMonth == monthOfYear))) {
                        view.updateDate(year1, month, day);

                    }

                }
            };

        }
        System.out.println("hello5");
        return _date;
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {

        case DATE_DIALOG_ID:
            System.out.println("hello6");

            ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
            break;
        }
    }

    private void updateDisplay() {
        System.out.println("hello2");
        mDateDisplay.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mMonth + 1).append("-").append(mDay).append("-")
                .append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            System.out.println("hello7");

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            System.out.println("year=" + year);
            System.out.println("month=" + monthOfYear);
            System.out.println("day=" + dayOfMonth);
            updateDisplay();
        }
    };
}
             */
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            /*
             * Calendar calendar=Calendar.getInstance();
    int mDay = calendar.get(Calendar.DAY_OF_MONTH), mMonth = calendar.get(Calendar.MONTH), mYear = calendar.get(Calendar.YEAR);


    int minDay = calendar.get(Calendar.DAY_OF_MONTH), minMonth = calendar.get(Calendar.MONTH), minYear = calendar.get(Calendar.YEAR)-12;

int maxDay = calendar.get(Calendar.DAY_OF_MONTH), maxMonth = calendar.get(Calendar.MONTH), maxYear = calendar.get(Calendar.YEAR)-12;


	public void onDate(View view) {
        System.out.println("hello");
        DialogFragment fragment = new SelectDateFragment();
        fragment.show(getFragmentManager(), "Date Picker");
    }

    class SelectDateFragment extends DialogFragment implements  DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstanceState) {


            System.out.println("entrering on create dialog");;

            return new DatePickerDialog(getActivity(), this, mYear, mMonth,
                    mDay) {

                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                
                    System.out.println("----------onDateChanged()-----------"+ year + "--" + year);
                    System.out.println("----------onDateChanged()-----------"+ monthOfYear + "--" + monthOfYear);
                    System.out.println("----------onDateChanged()-----------"+ dayOfMonth + "--" + dayOfMonth);

                    if ((minYear < year) || ((minMonth < monthOfYear) && (minYear == year)) || ((minDay < dayOfMonth) && (minYear == year) && (minMonth == monthOfYear))) {
                        view.updateDate(minYear, minMonth - 1, minDay);

                    }

					if ((maxYear > year) || ((maxMonth > monthOfYear) && (maxYear == year)) || ((maxDay > dayOfMonth) && (maxYear == year) && (maxMonth == monthOfYear))) {
                        view.updateDate(maxYear, maxMonth - 1, maxDay);
                    }

                }
            };

        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year,
                int monthOfYear, int dayOfMonth) {
            System.out.println("year=" + year + "day=" + dayOfMonth + "month="
                    + monthOfYear);
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;

            onPopulateSet(year, monthOfYear + 1, dayOfMonth);

        }


        private void onPopulateSet(int year, int i, int dayOfMonth) {
            EditText et_setDate;
            et_setDate = (EditText) findViewById(R.id.register_et_dob);
            et_setDate.setText(dayOfMonth + "/" + i + "/" + year);
            System.out.println("enetring on populate Set");

        }

    }
             */
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
          //  dpd.getDatePicker().setMinDate(d);
            
            
            /*
            
            
            dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            		
            		
            		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd , yyyy");
            		Calendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);	
        			
        			System.out.println("Date : " + sdf.format(calendar.getTime()));
        			System.out.println("Date : " + sdf.format(calendar.getTime()));
        			
        			date_to.setText(""+sdf.format(calendar.getTime()));
        			monthOfYear= monthOfYear+1;
        			
        			System.out.println(String.format("%02d", monthOfYear));
        			
        			String dateS=String.format("%02d", monthOfYear);//""+year+"-"+monthOfYear+"-"+(dayOfMonth)+" 23:59:59";
        			String date_d=String.format("%02d", dayOfMonth);
        			date_to.setTag(""+year+"-"+dateS+"-"+(date_d)+" 23:59:59");
        			System.out.println("Date : " + date_to.getTag().toString());
        			
        			displayDelivery(date_from.getTag().toString(), date_to.getTag().toString());
        			setTableValues();
        			
            		//date_to.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
	            
                }
            }, year, month, day);
            {
                @Override
                public void onDateChanged(DatePicker view, int year, int month, int day)
                {
                    if ( year <= maxYear && month + 1 <= maxMonth && date <= maxDate ) //meets your criteria
                    {
                        view.updateDate(year, month, date); //update the date picker to selected date
                    }
                    else
                    {
                        view.updateDate(maxYear, maxMonth - 1, maxDate); // or you update the date picker to previously selected date
                    }
                }
            };
            */
		}
		/*}else if(v== time_from){
			
			final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            //mHour = c.get(Calendar.SECOND);
            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            	@Override
            	public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
            		// Display Selected time in textbox
            		updateTime(hourOfDay,minute, time_from);
            		//time_from.setText(hourOfDay + ":" + minute);
            	}
            }, mHour, mMinute, false);
            
            tpd.show();
            
		}else if(v== time_to){
			
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            //mHour = c.get(Calendar.SECOND);
            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            	@Override
            	public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
            		// Display Selected time in textbox
            		//updateTime(hourOfDay,minute, time_to);
            		time_to.setText(hourOfDay + ":" + minute);
            	}
            }, mHour, mMinute, false);
            tpd.show();
            
		}*/
		
		else if(v== park){
			
		}else if(v== unpark){
			
		}else if(v== discount){
			
		}else if(v== search){
			
		}else{
			counter_reset();
			//del.setBackgroundResource(R.drawable.btn_delete);
			//del.setText("Delete");
			//del.setOnClickListener(this);
			//del.setEnabled(true);
			//del.setClickable(true);
			//item_code_selected= true;
			//01-23 00:01:34.892: I/System.out(9225): pin......delivery5

			String s = v.getTag().toString();
			System.out.println("pin......"+s+""+ v.getId());
			
			String s1=s.substring(8, 9);
			System.out.println("pin......"+s+"...."+ s1);
			popUp(s1);
			
			
		}
	}
	
	private String getDateTime1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	private static final int DATE_PICKER_ID = 0;
	
	@Deprecated
    protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:
			return new DatePickerDialog(this, datePickerListener,  year, month, day);
        }
        return null;
    }

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		// while dialog box is closed, below method is called.
		@SuppressLint("SimpleDateFormat")
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			System.out.println("......... date part1 .....");
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");	
			 
			Calendar calendar = new GregorianCalendar(year,month,day);	
			System.out.println("Date : " + sdf.format(calendar.getTime()));
		 
			//add one month
			calendar.add(Calendar.MONTH, 1);
			System.out.println("Date : " + sdf.format(calendar.getTime()));
			
			
			// Set the Date String in Button
			updateDate();
			
        }
	};
	
	
	
	
	@SuppressWarnings("deprecation")
	protected void updateDate() {
		System.out.println("......... date part 2.....");
		int localMonth = (month + 1);
	
		String monthString = localMonth < 10 ? "0" + localMonth : Integer.toString(localMonth);
		
		String localYear = Integer.toString(year);
		//String date= ""+new StringBuilder().append(day).append("/").append(monthString).append("/").append(localYear).append(" ");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
		Calendar calendar = new GregorianCalendar(2013,10,28);	
		System.out.println("Date : " + sdf.format(calendar.getTime()));
		//add one month
		calendar.add(Calendar.MONTH, 1);
		System.out.println("Date : " + sdf.format(calendar.getTime()));
		//subtract 10 days
		calendar.add(Calendar.DAY_OF_MONTH, -10);
		System.out.println("Date : " + sdf.format(calendar.getTime()));
		if(view == date_to){
			
			date_to.setText(new StringBuilder().append(day).append("/").append(monthString).append("/").append(localYear).append(" "));
		
		}else if(view ==date_from){
			
			date_from.setText(new StringBuilder().append(day).append("/").append(monthString).append("/").append(localYear).append(" "));
		}
		System.out.println("......... date part 2....."+new StringBuilder().append(day).append("/").append(monthString).append("/").append(localYear).append(" "));
		// Month is 0 based so add 1
		showDialog(DATE_PICKER_ID);
	}
	
	private void invalidUserPopup(String row, String message ) {
		
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
		
		det.setText("Add comments on row  "+ row);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				save.setText("Save");
				save.setClickable(true);
				save.setEnabled(true);
				save.setBackgroundResource(R.drawable.returned_qty_box_confirm_bt);
				dialog.dismiss();
		    }});
	}
	
	
	
	private void successfully_save_notes() {
		
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
		
		det.setText("Delivery Note Successfully \nReceived.");
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				save.setText("Save");
				save.setClickable(true);
				save.setEnabled(true);
				save.setBackgroundResource(R.drawable.returned_qty_box_confirm_bt);
				
				displayDelivery(date_from.getTag().toString(), date_to.getTag().toString());
				
				setTableValues();
				
				dialog.dismiss();
		    }});
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
		//item_image= (ImageView)findViewById(R.id.payment_head);
		//item_image.setBackgroundResource(R.drawable.notes_heading);
		//deliveries_icon.setBackgroundResource(R.drawable.deliveries_icon);
		
	    super.onPause();
	}
	public void onResume() {
		super.onResume();
		deliveries_icon.setBackgroundResource(R.drawable.deliveries_icon_sel);
		returns_icon.setBackgroundResource(R.drawable.returns_icon);
		item_image= null;
		item_image= (ImageView)findViewById(R.id.payment_head);
		item_image.setBackgroundResource(R.drawable.deliveries_notes_heading);
		
		displayDelivery(date_from.getTag().toString(), date_to.getTag().toString());
		
		setTableValues();
		
		System.out.println("counter stop when app  resume");
		//counter_reset();
	}
	@Override
    protected void onDestroy() {
		System.out.println("counter stop when app exit");
        countDownTimer.cancel();
        db_items.close();
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
		//	System.out.println("lock remaining time...");
		//	System.out.println("" + millisUntilFinished / 1000);
		}
	}

}