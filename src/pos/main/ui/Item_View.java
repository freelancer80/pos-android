package pos.main.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.DecimalFormat;


import pos.main.adapters.CustomAutoCompleteTextView;
import pos.main.adapters.TransparentProgressDialog;
import pos.main.database.Items_Database;
import pos.main.model.Items;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.Selection;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



public class Item_View extends Activity implements OnClickListener {
	
	
	private static String KEY_ITEM_NAME = "decs";
	private static String KEY_ITEM_CODE = "item_code";
	private static String KEY_ITEM_PRICE = "item_price";
	private static String KEY_ITEM_GROUP = "group";
	private static String KEY_ITEM_COLOR = "color";
	private static String KEY_ITEM_SIZE = "size";
	private static String KEY_ITEM_QTY = "available_quantity";
	private static String KEY_ITEM_QTY_TOTAL = "total_quantity";
	private static String KEY_ITEM_SOLD_QTY = "sold_qty";
	private static String KEY_ITEM_RETURN_QTY = "ret_qty";
	private static String KEY_ITEM_REM_QTY = "rem_qty";
	
	
	private static int KEY_COLUMN_SR = 0;
	private static int KEY_COLUMN_ITEM_CODE =1;
	private static int KEY_COLUMN_ITEM_NAME =2;
	private static int KEY_COLUMN_GROUP = 3;
	private static int KEY_COLUMN_COLOR = 4;
	private static int KEY_COLUMN_SIZE = 5;
	private static int KEY_COLUMN_QTY = 6;
	private static int KEY_COLUMN_SOLD_QTY = 7;
	private static int KEY_COLUMN_RETURN_QTY = 8;
	private static int KEY_COLUMN_REM_QTY = 9;
	
	private static int KEY_COLUMN_LENGHT = 10;
	
	String active_user;
	
	CustomAutoCompleteTextView pincode;
	Button one, two, three, four, five, six, seven, eight, nine, zero, z_zero;
	Button confirm, del, back;
	Button park, unpark, discount, search, comma;
	Button back_list, next_list, first, last;
	
	TextView count;
	
	int start_Limit=0, end_Limit= 15, total_pages=0, first_num=0;
	int start_counter= start_Limit;
	
	boolean item_code_selected=false;
	
    int trHeight = 0;
    int  size=15;
    DecimalFormat df = new DecimalFormat("0.00");
	String item_code="";
	
	ArrayList<HashMap<String, String>> item_list = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> searchResults;
	
	int salecounter=0;
	TableLayout tl, tl_return;		TableRow tr_head;
	
	boolean countr= false;
	
	String itemcode_selected="code", itemname_selected="name", item_price="price", item_discount="discount", 
			 item_group, item_size, item_color, item_available, return_qty, remaining_qty, total_qty, sold_qty;
	
	LinearLayout linear;
	
	/// index for discount 2 views use
	
	
	SimpleAdapter adapter;
	
	Items_Database db_items;
	String[] from = {KEY_ITEM_CODE,KEY_ITEM_NAME };
    // Ids of views in listview_layout
    int[] to = {R.id.item_code_list ,R.id.item_name_list};
    int total_count_values=0;
    LinearLayout ll;
    
    private TransparentProgressDialog pd;
	Runnable r;
	LinearLayout bottom;
    
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		countDownTimer = new MyCountDownTimer(startTime, interval);
		pd = new TransparentProgressDialog(this, R.drawable.spinner2);
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		};
		
		final float scale = getResources().getDisplayMetrics().density;
        trHeight = (int) (34 * scale + 0.5f);
        
		linear= (LinearLayout) findViewById(R.id.linearLayout3);
		pincode= (CustomAutoCompleteTextView) findViewById(R.id.itemcode);
		bottom=  (LinearLayout) findViewById(R.id.bottom);
		pincode.setText("");
		
		pincode.setHint("");
		pincode.setInputType(InputType.TYPE_NULL);
		int position = pincode.length();
		Selection.setSelection(pincode.getText(), position);
		ll = (LinearLayout)findViewById(R.id.btnLay);
		
		pincode.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&  (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
					
					item_search(pincode.getText().toString(), pincode.getText().toString());
					if(item_list.size()>0){
						setDisplay_value() ;
						pincode.setText("");
						//reset_back_del_confirm();
						//setDisplay_value() ;
						//setTableVaules();
					}
					System.out.println("value..............."+ pincode.getText().toString());
					return true;
				}
				return false;
			}
		});
		
		searchResults=new ArrayList<HashMap<String,String>>(item_list);
		adapter = new SimpleAdapter(getBaseContext(), searchResults, R.layout.list_item_code, from, to);
		/** Setting the itemclick event listener */
        pincode.setOnItemClickListener(itemClickListener);
        pincode.setAdapter(adapter);
		
        init();
        block_back_del_confirm();
		comma.setClickable(false);
		comma.setEnabled(false);
		comma.setBackgroundResource(R.drawable.bt_comma_disable);
		Btnfooter();
		
	}
	
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
    		itemname_selected=hm.get(KEY_ITEM_NAME);
    		item_price=hm.get(KEY_ITEM_PRICE);
    		
    		item_group=hm.get(KEY_ITEM_GROUP);
    		item_size=hm.get(KEY_ITEM_SIZE);
    		item_color=hm.get(KEY_ITEM_COLOR);
    		item_available=hm.get(KEY_ITEM_QTY); 
    		total_qty= hm.get(KEY_ITEM_QTY_TOTAL);
    		return_qty=hm.get(KEY_ITEM_RETURN_QTY);
    		remaining_qty=hm.get(KEY_ITEM_REM_QTY);
    		sold_qty=hm.get(KEY_ITEM_SOLD_QTY);
    		
    		linear.removeAllViews();
    		countr= false;
    		setTableVaules();
    		
    	//	init_Dist_Qty();
    	//	paymentShows();
    		//pincode.setText(itemcode_selected);
    		
    		//////////////////////////////////////////////
    		countr= false;
			
    		position = pincode.length();
			Selection.setSelection(pincode.getText(), position);
			pincode.requestFocus();
			//System.out.println(position+"....item selected from adapter"+pincode.getText().toString());
			pincode.setText("");
			item_code= "";
			
			itemcode_selected="";
    		itemname_selected="";
    		item_price="";
    		item_discount="";
    		pincode.setAdapter(adapter);
		}
    };
	
    private void setDisplay_value() {
    	
    	//item_list.get(0).get(KEY_ITEM_ID)
    	
    	itemcode_selected=item_list.get(0).get(KEY_ITEM_CODE);
		itemname_selected=item_list.get(0).get(KEY_ITEM_NAME);
		item_price=item_list.get(0).get(KEY_ITEM_PRICE);
		
		item_group=item_list.get(0).get(KEY_ITEM_GROUP);
		item_size=item_list.get(0).get(KEY_ITEM_SIZE);
		item_color=item_list.get(0).get(KEY_ITEM_COLOR);
		item_available=item_list.get(0).get(KEY_ITEM_QTY); 
		total_qty= item_list.get(0).get(KEY_ITEM_QTY_TOTAL);
		return_qty=item_list.get(0).get(KEY_ITEM_RETURN_QTY);
		remaining_qty=item_list.get(0).get(KEY_ITEM_REM_QTY);
		sold_qty=item_list.get(0).get(KEY_ITEM_SOLD_QTY);
		
		linear.removeAllViews();
		countr= false;
		setTableVaules();
		
	//	init_Dist_Qty();
	//	paymentShows();
	//	pincode.setText(itemcode_selected);
		
		//////////////////////////////////////////////
		countr= false;
		pincode.requestFocus();
		//System.out.println(position+"....item selected from adapter"+pincode.getText().toString());
		
		item_code= "";
		
		itemcode_selected="";
		itemname_selected="";
		item_price="";
		item_discount="";
		//pincode.setAdapter(adapter);	
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
		
		next_list = (Button) findViewById(R.id.next);
		back_list 	= (Button) findViewById(R.id.back_list);
		
		first = (Button) findViewById(R.id.first);
		last = (Button) findViewById(R.id.last);
		count= (TextView) findViewById(R.id.total_row_count);
		
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
		
		back_list.setOnClickListener(this);
		next_list.setOnClickListener(this);
		
		first.setOnClickListener(this);
		last.setOnClickListener(this);
	
		discount.setEnabled(false);
		discount.setClickable(false);
		
		park.setEnabled(false);
		park.setClickable(false);
		
		unpark.setEnabled(false);
		unpark.setClickable(false);
		search.setEnabled(false);
		search.setClickable(false);
		
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
	        item_name.setText("Decs");  
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
	        rate.setText("Group");  
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
	        discount_type.setText("Color"); 
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
	        discount.setText("Size"); 
	        discount.setTextColor(Color.parseColor("#800080")); 
	        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount.setPadding(0, 5, 0, 5); 
	        discount.setHeight(trHeight);
	        discount.setTextSize(size);
	        discount.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount);
			
			
			TextView qty = new TextView(this);
	        qty.setId(size);
	        qty.setText("Total Qty "); 
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
	        amount.setText("Sold Qty"); 
	        amount.setTextColor(Color.parseColor("#800080")); 
	        amount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        amount.setPadding(5, 5, 5, 5); 
	        amount.setHeight(trHeight);
	        amount.setTextSize(size);
	        amount.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(amount);
			
			
			TextView reQty = new TextView(this);
			reQty.setId(size);
			reQty.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
			reQty.setText("Return Qty"); 
			reQty.setTextColor(Color.parseColor("#800080")); 
			reQty.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
			reQty.setPadding(5, 5, 5, 5); 
			reQty.setHeight(trHeight);
			reQty.setTextSize(size);
			reQty.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(reQty);
	        
		
			TextView remQty = new TextView(this);
			remQty.setId(size);
			remQty.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
			remQty.setText("Rem Qty"); 
			remQty.setTextColor(Color.parseColor("#800080")); 
			remQty.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
			remQty.setPadding(5, 5, 5, 5); 
			remQty.setHeight(trHeight);
			remQty.setTextSize(size);
			remQty.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(remQty);
	    
        tl.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
        
		int length= item_list.size();
		if(length<end_Limit)
			length= end_Limit;
	    
		//int sold=0;
		start_counter= start_Limit;
		System.out.println("start counter........."+start_counter+".........."+length);
        for(int r=1; r<length+1; r++){
        	
        	tr_head = new TableRow(Item_View.this);
        	tr_head.setId(r);
        	tr_head.setTag(r);
	 		
        	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
        		
        		TextView text = new TextView(this);
        		
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
        		
        		int i= r-1;
        		if(i<item_list.size()){
        			
   //     			if(item_list.get(i).get(KEY_ITEM_CODE).length()>30)
            		text.setHeight(trHeight);
        			
        			text.setTextColor(Color.parseColor("#800080"));
        			
        			text.setTag(""+i);
            		if(col==KEY_COLUMN_ITEM_CODE){ 
            			
            			text.setText(""+item_list.get(i).get(KEY_ITEM_CODE));
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_ITEM_NAME){
            			text.setWidth(70);
            			text.setText(""+item_list.get(i).get(KEY_ITEM_NAME));
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_QTY){
            			System.out.println("item......."+item_list.get(i).get(KEY_ITEM_QTY_TOTAL));
            			text.setText(""+item_list.get(i).get(KEY_ITEM_QTY_TOTAL));
            			//sold=Integer.parseInt(item_list.get(i).get(KEY_ITEM_QTY_TOTAL));
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_REM_QTY){
            			//item_available
            			text.setWidth(30);
            			text.setText(""+item_list.get(i).get(KEY_ITEM_QTY));
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_SOLD_QTY){
            			text.setWidth(30);
            			text.setText(""+ item_list.get(i).get(KEY_ITEM_SOLD_QTY));
            			text.setTag(""+r);
            		}
            		else if(col==KEY_COLUMN_RETURN_QTY){
            			text.setWidth(30);
            			text.setText(""+item_list.get(i).get(KEY_ITEM_RETURN_QTY));
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_GROUP ){
            			text.setText(""+item_list.get(i).get(KEY_ITEM_GROUP));
            			text.setTag(""+r);
            			
            		}else if( col== KEY_COLUMN_SIZE  ){
            			text.setText(""+item_list.get(i).get(KEY_ITEM_SIZE));
            			text.setTag(""+r);
            			
            		}else if( col== KEY_COLUMN_COLOR ){
            			text.setText(""+item_list.get(i).get(KEY_ITEM_COLOR));
            			text.setTag(""+r);
            			
            		}else{
            			text.setText(""+(start_counter+1));
            			text.setTag(""+r);
            		}

            		if(col==KEY_COLUMN_SR);
            		else
            			text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
            		tr_head.addView(text);
            		
        		}else{
        			text.setTag(""+r);
        			text.setHeight(trHeight);
            		if(col==KEY_COLUMN_ITEM_CODE){
            			text.setText("0987654321");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_REM_QTY||col==KEY_COLUMN_RETURN_QTY || 
            				col==KEY_COLUMN_QTY|| col==KEY_COLUMN_SOLD_QTY){
            			text.setText("198");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_GROUP|| col== KEY_COLUMN_SIZE ||  col== KEY_COLUMN_COLOR ){
            			text.setText("1000,00");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else if(col==KEY_COLUMN_ITEM_NAME){
            			text.setText("");
            			text.setTextColor(Color.TRANSPARENT);
            			text.setTag(""+r);
            			
            		}else{
            			text.setText("");
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
        	System.out.println("start counter........."+start_counter);
  	 	}
        
        if(item_list.size()>0){
        	search.setOnClickListener(this);
        	search.setEnabled(true);
    		search.setClickable(true);
    		search.setBackgroundResource(R.drawable.bt_search);
        }
	}
	//3rd
	@SuppressWarnings("deprecation")
	private void setTableVaules() {
		
		System.out.println("set values and back enable");
		counter_reset();
		back_list.setVisibility(View.INVISIBLE);
		next_list.setVisibility(View.INVISIBLE);
		first.setVisibility(View.INVISIBLE);
		last.setVisibility(View.INVISIBLE);
		ll.setVisibility(View.INVISIBLE);
		block_back_del_confirm();
		
		back.setText("Back");
		back.setBackgroundResource(R.drawable.btn_back);
		back.setOnClickListener(this);
		back.setClickable(true);
		back.setEnabled(true);
		
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
		        item_name.setText("Decs");  
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
		        rate.setText("Group");  
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
		        discount_type.setText("Color"); 
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
		        discount.setText("Size"); 
		        discount.setTextColor(Color.parseColor("#800080")); 
		        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		        discount.setPadding(0, 5, 0, 5); 
		        discount.setHeight(trHeight);
		        discount.setTextSize(size);
		        discount.setTypeface(null, Typeface.BOLD);
		        tr_head.addView(discount);
				
				
				TextView qty = new TextView(this);
		        qty.setId(size);
		        qty.setText("Total Qty "); 
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
		        amount.setText("Sold Qty"); 
		        amount.setTextColor(Color.parseColor("#800080")); 
		        amount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
		        amount.setPadding(5, 5, 5, 5); 
		        amount.setHeight(trHeight);
		        amount.setTextSize(size);
		        amount.setTypeface(null, Typeface.BOLD);
		        tr_head.addView(amount);
				
				
				TextView reQty = new TextView(this);
				reQty.setId(size);
				reQty.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
				reQty.setText("Return Qty"); 
				reQty.setTextColor(Color.parseColor("#800080")); 
				reQty.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
				reQty.setPadding(5, 5, 5, 5); 
				reQty.setHeight(trHeight);
				reQty.setTextSize(size);
				reQty.setTypeface(null, Typeface.BOLD);
		        tr_head.addView(reQty);
		        
			
				TextView remQty = new TextView(this);
				remQty.setId(size);
				remQty.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
				remQty.setText("Rem Qty"); 
				remQty.setTextColor(Color.parseColor("#800080")); 
				remQty.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
				remQty.setPadding(5, 5, 5, 5); 
				remQty.setHeight(trHeight);
				remQty.setTextSize(size);
				remQty.setTypeface(null, Typeface.BOLD);
		        tr_head.addView(remQty);
		    
	        tl.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
	        
			int length= item_list.size();
			if(length<end_Limit)
				length= end_Limit;
		    
	        for(int r=1; r<length; r++){
	        	
	        	tr_head = new TableRow(Item_View.this);
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
	        //int i= r-1;
	        if(r==1){
	        			
	        	text.setTextColor(Color.parseColor("#800080"));
	        	text.setTag(""+r);
	        	if(col==KEY_COLUMN_ITEM_CODE){ 
	        		text.setText(""+itemcode_selected);
	        		text.setTag(""+r);
	        	}else if(col==KEY_COLUMN_ITEM_NAME){
	        		text.setText(""+itemname_selected);
	        		text.setTag(""+r);
	        		
	        	}else if(col==KEY_COLUMN_QTY){
	        		text.setText(""+total_qty);
	        		text.setTag(""+r);
	        	
	        	}else if(col==KEY_COLUMN_REM_QTY){
        			//System.out.println("item......."+item_list.get(i).get(KEY_ITEM_QTY)+"........."+sold);
        			//sold=""+( Integer.parseInt(sold)-Integer.parseInt(item_list.get(i).get(KEY_ITEM_QTY)));
        			text.setText(""+item_available);
        			text.setTag(""+r);
        			
        		}else if(col==KEY_COLUMN_RETURN_QTY ){
	        		text.setText(""+return_qty);
	        		text.setTag(""+r);
	        	}
	        	else if(col==KEY_COLUMN_SOLD_QTY ){
	        		text.setText(""+sold_qty);
	        		text.setTag(""+r);
	        		
	        	}else if(col==KEY_COLUMN_GROUP ){
	        		text.setText(""+item_group);
	        		text.setTag(""+r);
	        		
	        	}else if( col== KEY_COLUMN_SIZE  ){
	        		text.setText(""+item_size);
	        		text.setTag(""+r);
	        		
	        	}else if( col== KEY_COLUMN_COLOR ){
	        		text.setText(""+item_color);
	        		text.setTag(""+r);
	        		
	        	}else{
	        		text.setText(""+r);
	        		text.setTag(""+r);
	        	}
	        	
	        	text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        	if(col== KEY_COLUMN_COLOR ||col== KEY_COLUMN_GROUP||col==KEY_COLUMN_SR);
        			
    			//	text.setOnClickListener(Item_View.this);
	        	
	        	
	        	
	        	tr_head.addView(text);
	        	
	        }else{
	        	text.setTag(""+r);
	        	if(col==KEY_COLUMN_ITEM_CODE){ 
	        		text.setText("0987654321");
	        		text.setTextColor(Color.TRANSPARENT);
	        		text.setTag(""+r);
	        		
	        	}else if(col==KEY_COLUMN_REM_QTY||col==KEY_COLUMN_RETURN_QTY || 
	        			col==KEY_COLUMN_QTY|| col==KEY_COLUMN_SOLD_QTY){
	        		text.setText("198");
	        		text.setTextColor(Color.TRANSPARENT);
	        		text.setTag(""+r);
	        		
	        	}else if(col==KEY_COLUMN_GROUP|| col== KEY_COLUMN_SIZE ||  col== KEY_COLUMN_COLOR ){
	        		text.setText("1000,00");
	        		text.setTextColor(Color.TRANSPARENT);
	        		text.setTag(""+r);
	        		
	        	}else if(col==KEY_COLUMN_ITEM_NAME){
	        		text.setText("");
	        		text.setTextColor(Color.TRANSPARENT);
	        		text.setTag(""+r);
	            }else{
	            	text.setText("");
	            	text.setTag(""+r);
	            }

	        	if(col==KEY_COLUMN_SR);
	        	else
	        		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        	tr_head.addView(text);
	        }
	        	}
	        	tl.addView(tr_head);
	        }	
	}

	private void counter_reset() {
		if(!timerHasStarted) {
			countDownTimer.start();
			timerHasStarted = true;
		}else{
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
	//27th
	private void itemcall() {
		db_items= null;
		db_items = new Items_Database(getApplicationContext());
		item_list= null;
		item_list = new ArrayList<HashMap<String, String>>();
		List<Items> allItems = db_items.getAllItems(""+start_Limit, ""+end_Limit);
		
		for (final Items items : allItems) {
			
				/*	System.out.println(""+items.getBarcode()
							+"\n.........."+ items.getId()
					+"\n.........."+ items.getAvailable_quantity()
					+"\n.........."+ items.getGroup_id()
					+"\n.........."+ items.getSize_id()
					+"\n.........."+ items.getColor_id()
					+"\n.........."+ items.getSelling_price()
					+"\n.........."+ items.getReturn_qty()
					+"\n.........."+ items.getTotal_qty()
					+"\n.........."+ items.getLarge_pic()
					+"\n.........."+ items.getDecs1());*/
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_ITEM_CODE, items.getItem_id());
			map.put(KEY_ITEM_NAME, items.getDecs1());
			map.put(KEY_ITEM_GROUP, items.getGroup_id());
			map.put(KEY_ITEM_COLOR, items.getColor_id());
			map.put(KEY_ITEM_SIZE, items.getSize_id());
			map.put(KEY_ITEM_QTY, items.getAvailable_quantity());
			map.put(KEY_ITEM_QTY_TOTAL, items.getTotal_qty());
			
			int sold= 0;//(Integer.parseInt(items.getTotal_qty())- (Integer.parseInt(items.getAvailable_quantity())));
			System.out.println("sold list......"+sold);
			map.put(KEY_ITEM_SOLD_QTY, ""+sold);
			map.put(KEY_ITEM_RETURN_QTY,items.getReturn_qty());
			map.put(KEY_ITEM_REM_QTY,"0");
			
			item_list.add(map);
		}
		
		System.out.println("item list......"+item_list.size());
		db_items.closeDB();
		//System.out.println(""+ item_list.size());
		searchResults=new ArrayList<HashMap<String,String>>(item_list);
		
	}
	
	private void item_search(String item_code, String decs) {
		
		item_list= null;
		item_list = new ArrayList<HashMap<String, String>>();
		List<Items> allItems = db_items.getItems(item_code, decs);
		
		for (final Items items : allItems) {
			System.out.println("1."+"\n.........."+ items.getItem_id()
			
					+"\n.........."+ items.getId()
					+"\n.........."+items.getBarcode()
					+"\n.........."+ items.getAvailable_quantity()
					+"\n.........."+ items.getGroup_id()
					+"\n.........."+ items.getSize_id()
					+"\n.........."+ items.getColor_id()
					
					+"\n.........."+ items.getSelling_price()
					+"\n.........."+ items.getTotal_qty()
					+"\n.........."+ items.getDecs1());
			
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(KEY_ITEM_CODE, items.getItem_id());
			map.put(KEY_ITEM_NAME, items.getDecs1());
			map.put(KEY_ITEM_GROUP, items.getGroup_id());
			map.put(KEY_ITEM_COLOR, items.getColor_id());
			map.put(KEY_ITEM_SIZE, items.getSize_id());
			map.put(KEY_ITEM_QTY, items.getAvailable_quantity());
			map.put(KEY_ITEM_QTY_TOTAL, items.getTotal_qty());
			int sold= (Integer.parseInt(items.getTotal_qty())-(Integer.parseInt( items.getAvailable_quantity())));
			map.put(KEY_ITEM_SOLD_QTY, ""+sold);
			map.put(KEY_ITEM_RETURN_QTY,items.getReturn_qty());
			map.put(KEY_ITEM_REM_QTY,"0");
			
			item_list.add(map);
		}
		searchResults= null;
		adapter= null;
		pincode= (CustomAutoCompleteTextView) findViewById(R.id.itemcode);
		
		pincode.setHint("");
		pincode.setInputType(InputType.TYPE_NULL);
		
		searchResults=new ArrayList<HashMap<String,String>>(item_list);
		adapter = new SimpleAdapter(getBaseContext(), searchResults, R.layout.list_item_code, from, to);
		
		/** Setting the itemclick event listener */
        pincode.setOnItemClickListener(itemClickListener);
        pincode.setAdapter(adapter);
       // pincode.setText(item_code);
		
	}
	
	Dialog dialog;
	
	private void popupOPen() {
		
		dialog = new  Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
		dialog.getWindow().setContentView(R.layout.delivery_received);
		//dialog = new AlertDialog.Builder(this).create();
		//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.show();
		
		//dialog.setCancelable(false);
				//Dialog(_activity, android.R.style.Theme_Translucent_NoTitleBar);
		
		//dialog.setContentView(R.layout.delivery_received);
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		Button save = (Button) dialog.findViewById(R.id.save);
		Button cancel= (Button) dialog.findViewById(R.id.cancel);
		TextView branchNo, companyNo;
		final TextView note_id;
		TextView delivery_date;
		
		branchNo= (TextView) dialog.findViewById(R.id.branch_no);
		companyNo= (TextView) dialog.findViewById(R.id.company_no);
		
		note_id= (TextView) dialog.findViewById(R.id.note_id);
		delivery_date= (TextView) dialog.findViewById(R.id.delivery_date);
		
		tl_return= (TableLayout) dialog.findViewById(R.id.purchase_items);
		tl_return.removeAllViews();
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		/*
		View child = tl.getChildAt(Integer.parseInt(index));
		TableRow row = (TableRow) child;
		
		String itemcode=((TextView)row.getChildAt(KEY_COLUMN_NOTE_ID)).getText().toString();
		String status_id="1";

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
					+"\n.........."+ items.getStatus_id()
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
		 */
		 setPopUPTable();
		// setPopUPTable(item_list, status_id);
		 
		
		db_items.closeDB();
	}
	
	@SuppressWarnings("deprecation")
	private void setPopUPTable() {
		start_counter=0;
		tl_return= (TableLayout) dialog.findViewById(R.id.purchase_items);
		tl_return.removeAllViews();
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
	        item_code.setText("Note ID"); // set the text for the header 
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
	        item_name.setText("Decs1");  
	        item_name.setTextColor(Color.parseColor("#800080"));
	        item_name.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        item_name.setPadding(5, 5, 5, 5);
	        item_name.setTypeface(null, Typeface.BOLD);
	        item_name.setHeight(trHeight);
	        item_name.setTextSize(size);
	        
	        tr_head.addView(item_name);
	        
	        item_name = new TextView(this);
	        item_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        item_name.setId(size);
	        item_name.setText("Decs2");  
	        item_name.setTextColor(Color.parseColor("#800080"));
	        item_name.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        item_name.setPadding(5, 5, 5, 5);
	        item_name.setTypeface(null, Typeface.BOLD);
	        item_name.setHeight(trHeight);
	        item_name.setTextSize(size);
	        
	        tr_head.addView(item_name);
	        
	        item_name = new TextView(this);
	        item_name.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        item_name.setId(size);
	        item_name.setText("Decs3");  
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
	        rate.setText("Supp. No");  
	        rate.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        rate.setTextColor(Color.parseColor("#800080"));
	        rate.setPadding(5, 5, 5, 5);
	        rate.setHeight(trHeight);
	        rate.setTextSize(size);
	        rate.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(rate);
	        
	        rate = new TextView(this);
	        rate.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        rate.setId(size);
	        rate.setText("Supp. Item No");  
	        rate.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        rate.setTextColor(Color.parseColor("#800080"));
	        rate.setPadding(5, 5, 5, 5);
	        rate.setHeight(trHeight);
	        rate.setTextSize(size);
	        rate.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(rate);
	        
	        
	        rate = new TextView(this);
	        rate.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        rate.setId(size);
	        rate.setText("Buying Price");  
	        rate.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        rate.setTextColor(Color.parseColor("#800080"));
	        rate.setPadding(5, 5, 5, 5);
	        rate.setHeight(trHeight);
	        rate.setTextSize(size);
	        rate.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(rate);
	        
	        rate = new TextView(this);
	        rate.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        rate.setId(size);
	        rate.setText("Retail Price");  
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
	        discount_type.setText("Dist"); 
	        discount_type.setTextColor(Color.parseColor("#800080")); 
	        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount_type.setPadding(5, 5, 5, 5); 
	        discount_type.setHeight(trHeight);
	        discount_type.setTextSize(size);
	        discount_type.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount_type);
	        
	        
	        discount_type =  new TextView(this);
	        discount_type.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        discount_type.setId(size);
	        discount_type.setText("TAX %"); 
	        discount_type.setTextColor(Color.parseColor("#800080")); 
	        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount_type.setPadding(5, 5, 5, 5); 
	        discount_type.setHeight(trHeight);
	        discount_type.setTextSize(size);
	        discount_type.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount_type);
	        
	        discount_type =  new TextView(this);
	        discount_type.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        discount_type.setId(size);
	        discount_type.setText("Total Qty"); 
	        discount_type.setTextColor(Color.parseColor("#800080")); 
	        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount_type.setPadding(5, 5, 5, 5); 
	        discount_type.setHeight(trHeight);
	        discount_type.setTextSize(size);
	        discount_type.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount_type);
	        
	        discount_type =  new TextView(this);
	        discount_type.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        discount_type.setId(size);
	        discount_type.setText("Sold Qty"); 
	        discount_type.setTextColor(Color.parseColor("#800080")); 
	        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount_type.setPadding(5, 5, 5, 5); 
	        discount_type.setHeight(trHeight);
	        discount_type.setTextSize(size);
	        discount_type.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount_type);
	        
	        discount_type =  new TextView(this);
	        discount_type.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        discount_type.setId(size);
	        discount_type.setText("Return Qty"); 
	        discount_type.setTextColor(Color.parseColor("#800080")); 
	        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount_type.setPadding(5, 5, 5, 5); 
	        discount_type.setHeight(trHeight);
	        discount_type.setTextSize(size);
	        discount_type.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount_type);
	        
	        discount_type =  new TextView(this);
	        discount_type.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_table_head));
	        discount_type.setId(size);
	        discount_type.setText("Remain. Qty"); 
	        discount_type.setTextColor(Color.parseColor("#800080")); 
	        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
	        discount_type.setPadding(5, 5, 5, 5); 
	        discount_type.setHeight(trHeight);
	        discount_type.setTextSize(size);
	        discount_type.setTypeface(null, Typeface.BOLD);
	        tr_head.addView(discount_type);
	        
	        
	        tl_return.addView(tr_head);//, new TableLayout.LayoutParams(layoutpParams));
	        
		int length= item_list.size();
		if(length<11)
			length= 11;
	    
		
		
		/*
        for(int r=1; r<length; r++){
        	
        	tr_head = new TableRow(Item_View.this);
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
        		
        		int i= r-1;
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
        				System.out.println("status.................. "+status_id);
        				if(col==3){
            				qty= new EditText(this);
            				qty.setInputType(InputType.TYPE_CLASS_TEXT);
            				qty.setId(r);
            				
            				if(r==1){
            					qty.requestFocus();
            				}
            				
            				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                	        imm.hideSoftInputFromWindow(qty.getWindowToken(), 0);
                			qty.setOnFocusChangeListener(new OnFocusChangeListener() {
        						
        						@Override
        						public void onFocusChange(View v, boolean hasFocus) {
        							System.out.println("edite text focus");
        							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        							imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        							
        							System.out.println("value"+ qty.getText().toString());
        						}
        					});
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
        				System.out.println("status.................. "+status_id);
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
        	tl_return.addView(tr_head);
        	start_counter++;
        	System.out.println("start counter........."+start_counter);
  	 	}
	*/
	}
	
	public int TOTAL_LIST_ITEMS = 0;
	public int NUM_ITEMS_PAGE   = 15;
	private int noOfBtns=10;
	private Button[] btns;
	
	private void Btnfooter(){
		
		
	    ll.removeAllViews();
	    
	    btns    =new Button[noOfBtns];
	    for(int i=0;i<noOfBtns;i++){
	        btns[i] =   new Button(this);
	        btns[i].setBackgroundResource(R.drawable.disactive);//(getResources().getDrawable(R.drawable.disactive));
	        btns[i].setText(""+(i+1));
	        btns[i].setTextSize(17);

	        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        lp.setMargins(5, 2, 5, 2);
	        ll.addView(btns[i], lp);

	        final int j = i;
	        btns[j].setOnClickListener(new OnClickListener() {
	            public void onClick(View v){
	            	start_Limit= j* end_Limit;
	            	System.out.println(start_Limit+"...... satrt and edn"+ end_Limit);
	            	
	            	if(total_count_values> start_Limit){
	    				thread_load(v);
	    			}
	                //loadList(j);
	                CheckBtnBackGroud(j);
	            }
	        });
	    }
	}
	
	/**
	 * Method for Checking Button Backgrounds
	 */
	@SuppressWarnings("deprecation")
	private void CheckBtnBackGroud(int index){
	    //title.setText("Page "+(index+1)+" of "+noOfBtns);
	    for(int i=0;i<noOfBtns;i++){
	        if(i==index){
	        	btns[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.active));
	        	btns[i].setTextColor(getResources().getColor(android.R.color.white));
	        }
	        else{
	        	btns[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.disactive));
	            btns[i].setTextColor(getResources().getColor(android.R.color.black));
	        }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onPause() {
		System.out.println("counter pause...");
		super.onPause();
	}
	
	public void onResume() {
		
		super.onResume();
		counter_reset();
		db_items = new Items_Database(getApplicationContext());
		String count=db_items.countItem();
		total_count_values= Integer.parseInt(count);
		
		total_pages= 0;
		
		
		double last_found=total_count_values/end_Limit;
		
		TOTAL_LIST_ITEMS=(total_count_values % 2);
		if(TOTAL_LIST_ITEMS == 0){
			//result= result;
		
		}else if(TOTAL_LIST_ITEMS == 1){
			last_found= last_found+1;
			
		}
		total_pages= (int) last_found;
		this.count.setText(" "+count);
		
		System.out.println("counter resume..."+ total_count_values+"%"+end_Limit+"= "+ TOTAL_LIST_ITEMS
				+"  indlouble......"+last_found+"......."+total_pages);
		//end_Limit= 50;
		start_Limit= 0;
		//total_pages= 50;
		
		if(total_pages<10)
			noOfBtns= total_pages;
		else 
			noOfBtns= 10;
		
		
		Btnfooter();
		db_items.closeDB();
		
		//v.setEnabled(false);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                pd.show();
            }
            @Override
            protected Void doInBackground(Void... arg0) {
                try{
                	itemcall();
                	//Thread.sleep(100);
                } catch (Exception e) {
                   e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                if (pd!=null) {
                	adapter = new SimpleAdapter(getBaseContext(), searchResults, R.layout.list_item_code, from, to);
            		pincode.setOnItemClickListener(itemClickListener);
                    pincode.setAdapter(adapter);
                    createTable();
                    pd.dismiss();
                    //v.setEnabled(true);
                }
            }  
        };
        task.execute((Void[])null);
        CheckBtnBackGroud(start_Limit);
    }
	
	@Override
    protected void onDestroy() {
		System.out.println("counter stop when app exit");
        countDownTimer.cancel();
        System.gc();
        db_items.closeDB();
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
			System.out.println("callllll......");
			//Login_POPUP myObject = new Login_POPUP(MainActivity1.this, Global.active_user);
			//boolean a=myObject.showDialog();
			
		}
		@Override
		public void onTick(long millisUntilFinished) {
			//System.out.println("lock remaining time...");
			//System.out.println("" + millisUntilFinished / 1000);
		}
	}

	private void thread_load(final View v) {
		

		v.setEnabled(false);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                pd.show();
            }
            @Override
            protected Void doInBackground(Void... arg0) {
                try{
                	itemcall();
                	Thread.sleep(10);
                } catch (Exception e) {
                   e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                if (pd!=null) {
                	adapter = new SimpleAdapter(getBaseContext(), searchResults, R.layout.list_item_code, from, to);
            		pincode.setOnItemClickListener(itemClickListener);
                    pincode.setAdapter(adapter);
                    createTable();
                    pd.dismiss();
                    v.setEnabled(true);
                }
            }
                
        };
        task.execute((Void[])null);
	
        CheckBtnBackGroud(start_Limit/end_Limit);
	
	}
	
	
	@Override
	public void onClick(final View v) {
		
		if(v== back){
			
			counter_reset();
			pincode.setHint("");
			back_list.setVisibility(View.VISIBLE);
			next_list.setVisibility(View.VISIBLE);
			
			first.setVisibility(View.VISIBLE);
			last.setVisibility(View.VISIBLE);
			ll.setVisibility(View.VISIBLE);
			bottom.setVisibility(View.VISIBLE);
			//pincode.setAdapter(adapter);
			pincode.setFocusable(true);
			pincode.setFocusableInTouchMode(true);
			pincode.setText("");
			pincode.requestFocus();
			
			block_back_del_confirm();
			back.setText("");
			back.setBackgroundResource(R.drawable.bt_back_disable);
			back.setClickable(false);
			back.setEnabled(false);
			
			//System.out.println(start_Limit+"...... satrt and edn"+ end_Limit);
			if(start_Limit>=0){
				v.setEnabled(false);
	            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
	                @Override
	                protected void onPreExecute() {
	                    pd.show();
	                }
	                @Override
	                protected Void doInBackground(Void... arg0) {
	                    try{
	                    	itemcall();
	                    	Thread.sleep(100);
	                    } catch (Exception e) {
	                       e.printStackTrace();
	                    }
	                    return null;
	                }
	                @Override
	                protected void onPostExecute(Void result) {
	                    if (pd!=null) {
	                    	adapter = new SimpleAdapter(getBaseContext(), searchResults, R.layout.list_item_code, from, to);
	                		pincode.setOnItemClickListener(itemClickListener);
	                        pincode.setAdapter(adapter);
	                        createTable();
	                        
	                        pd.dismiss();
	                        v.setEnabled(true);
	                    }
	                }
	            };
	            task.execute((Void[])null);
			}
		}else if(v== confirm){
			counter_reset();
			
		}else if(v== del){
			counter_reset();
			item_code=pincode.getText().toString();
			if(item_code.length()>0)
				item_code= item_code.substring(0, item_code.length()-1);
			pincode.setText(item_code);
			if(item_code.equals("")){
				block_back_del_confirm();
				item_code_selected= false;
			}
			
		}
		else if(v== back_list){
			
			start_Limit= start_Limit-end_Limit;
			
			System.out.println(start_Limit+"...... satrt and edn"+ end_Limit);
			
			if(start_Limit>=0){
				thread_load(v);
			}else{
				start_Limit=0;
			}
			
		}else if(v== next_list){
			
			start_Limit= start_Limit+end_Limit;
			if(total_count_values<start_Limit){
				start_Limit= start_Limit-end_Limit;
			}
			if(total_count_values> start_Limit){
				thread_load(v);
			}
			
		}else if(v== first){
			start_Limit=0;
			CheckBtnBackGroud(start_Limit);
			System.out.println(start_Limit+"...... satrt and edn"+ end_Limit);
			
			thread_load(v);
		
		}else if(v== last){
			
			CheckBtnBackGroud(total_pages-1);
			start_Limit= (total_pages-1)* end_Limit;
			System.out.println(start_Limit+"...... satrt and edn"+ end_Limit);
			
			thread_load(v);
			
		}
		else if(v== park){
			
		}else if(v== unpark){
			
		}else if(v== discount){
			
		}else if(v== search){
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
			
			
			
			
		}else{
			
			counter_reset();
			del.setBackgroundResource(R.drawable.btn_delete);
			del.setText("Delete");
			del.setOnClickListener(this);
			del.setEnabled(true);
			del.setClickable(true);
			item_code_selected= true;
			
			String s = v.getTag().toString();
			System.out.println("pin......"+s);
			
			if(s.equals("pin") && pincode.getHint().toString().equals("")){
				Button b = (Button)v;
				String buttonText = b.getText().toString();
				pincode.setFocusable(true);
				pincode.setFocusableInTouchMode(true);
				item_code= item_code+""+buttonText;
			}
			
			item_search( item_code, item_code);
			pincode.setText(""+item_code);
			
		}
	}

	

	

}



