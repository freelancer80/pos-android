package pos.main.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pos.main.database.Order_Database;
import pos.main.model.Global;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class HeadTabActivityAdmin extends TabActivity implements OnTabChangeListener { 

	
	private static final String TAB_LOGOUT = "0";
	private static final String TAB_SALES = "1";
	private static final String TAB_SALEVOUCHER = "2";
	private static final String TAB_ITEMS_INVENTORY = "3";
	private static final String TAB_NOTES = "4";
	private static final String TAB_SALES_REPORT = "5";
	private static final String TAB_SETTING = "6";
	private static final String TAB_MENU = "7";
	private static final String TAB_LOGO = "8";
	
	String months[] = {
		      "Jan", "Feb", "Mar", "Apr",
		      "May", "Jun", "Jul", "Aug",
		      "Sep", "Oct", "Nov", "Dec"};

	private TabHost tabHost;
	Order_Database db_order;
	
	
	private static HeadTabActivityAdmin theInstance;

    public static HeadTabActivityAdmin getInstance() {
    	
        return HeadTabActivityAdmin.theInstance;
    }

    public HeadTabActivityAdmin() {
    	HeadTabActivityAdmin.theInstance = this;
    }

	@SuppressLint({ "NewApi", "SimpleDateFormat" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.top_tab_host);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			
			db_order = new Order_Database(getApplicationContext());
			long order_id=db_order.create_order_id();
			System.out.println("order id............"+ order_id);
			
			String login=" James";
			GregorianCalendar gcalendar = new GregorianCalendar();
			Date dNow = new Date( );
			SimpleDateFormat ft =  new SimpleDateFormat ("hh:mm a");
			String m=months[gcalendar.get(Calendar.MONTH)]+" "+gcalendar.get(Calendar.DATE)+" "+ft.format(dNow);
			 
			System.out.println("active user............"+ Global.active_user);
			  
		//	String styledText = "<big>" +login  + "</big>"+ "<br/>"+"<small> "
		//		            + "<b>"+ m+ "</b></small>" + "<br />" ;
			
			// display = getWindowManager().getDefaultDisplay();
	      // width = display.getWidth();
	      //  int hight= display.getHeight();
			
			tabHost = getTabHost();
			tabHost.setHorizontalScrollBarEnabled(true);
			
			TabSpec tab = tabHost.newTabSpec("Tabs");
			getTabWidget().setStripEnabled(false);
			tab = tabHost.newTabSpec(TAB_LOGOUT);
			//Intent i= new Intent(this, LoginPin.class);
			
			Intent intent = new Intent().setClass(this, Log_out.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//this.getParent().finish();
			
			//spec = tabhost.newTabSpec("Product").setIndicator("Product").setContent(intent);

			//tabhost.addTab(spec);
			
			
			tab.setIndicator("");
			tab.setContent(intent);
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(0).setBackgroundResource(R.drawable.tab_logout);
			
			tab = tabHost.newTabSpec(TAB_SALES);
			Intent intent_sale= new Intent(HeadTabActivityAdmin.this,MainActivity.class);
			intent_sale.putExtra("order_id", ""+order_id);
			
			tab.setContent(intent_sale);
			
			
			//tab.setContent(new Intent(this, MainActivity.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(1).setBackgroundResource(R.drawable.tab_sales);
			
			tab = tabHost.newTabSpec(TAB_SALEVOUCHER);
			tab.setContent(new Intent(this, MainActivity1.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(2).setBackgroundResource(R.drawable.tab_sale_voucher);
			
			
			tab = tabHost.newTabSpec(TAB_ITEMS_INVENTORY);
			tab.setContent(new Intent(this, Item_View.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(3).setBackgroundResource(R.drawable.tab_items);
			
			tab = tabHost.newTabSpec(TAB_NOTES);
			tab.setContent(new Intent(this, Items_Notes.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(4).setBackgroundResource(R.drawable.tab_notes);
			
			tab = tabHost.newTabSpec(TAB_SALES_REPORT);
			tab.setContent(new Intent(this, MainActivity1.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(5).setBackgroundResource(R.drawable.tab_sale_report);
			
			tab = tabHost.newTabSpec(TAB_SETTING);
			tab.setContent(new Intent(this, Setting1.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(6).setBackgroundResource(R.drawable.tab_setting);
			
			tab = tabHost.newTabSpec(TAB_MENU);
			tab.setContent(new Intent(this, Items_Menu.class));
			//tab.setIndicator("Menu", null);
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(7).setBackgroundResource(R.drawable.tab_menu);
			
			
			
			////////////////////////////////////////////////////////////////////////////////////////
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER_VERTICAL;
			
			
			TextView txtTab = new TextView(this);
			txtTab.setText("Order: 0000  ");
			txtTab.setPadding(25, 0, 0, 0);
			txtTab.setTextColor(Color.WHITE);
			txtTab.setTextSize(20);
			txtTab.setId(4);
			//txtTab.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.logo, 0);
			txtTab.setGravity( Gravity.CENTER_VERTICAL| Gravity.LEFT);
			
			
			TextView txtTab1 = new TextView(this);
			txtTab1.setText(login);
			txtTab1.setPadding(25, 0, 0, 0);
			txtTab1.setTextColor(Color.WHITE);
			txtTab1.setTextSize(20);
			txtTab1.setGravity(  Gravity.CENTER_VERTICAL|Gravity.LEFT);
			
			ImageView img= new ImageView(this);
			img.setBackgroundResource(R.drawable.notif_bell);
			
			ImageView img1= new ImageView(this);
			img1.setBackgroundResource(R.drawable.logo);
			
			
			TextView txtTab11 = new TextView(this);
			txtTab11.setText(m);
			txtTab11.setPadding(25, 0, 0, 0);
			txtTab11.setTextColor(Color.WHITE);
			txtTab11.setTextSize(20);
			txtTab11.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
			
			
			
			LinearLayout li1= new LinearLayout(this);
			li1.setLayoutParams(params);
			li1.setOrientation(LinearLayout.HORIZONTAL);
			li1.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
			
			li1.addView(txtTab1);
			li1.addView(img);
			
			
			LinearLayout li= new LinearLayout(this);
			li.setLayoutParams(params);
			li.setOrientation(LinearLayout.VERTICAL);
			li.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
			//li.setPadding(120, 10, 0, 10);
			
			li.addView(li1);
			li.addView(txtTab11);
			li.addView(txtTab);
			
			LinearLayout l_merge= new LinearLayout(this);
			l_merge.setLayoutParams(params);
			l_merge.setOrientation(LinearLayout.HORIZONTAL);
			l_merge.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
			l_merge.setPadding(0, 10, 10, 0);
			
			//l_merge.addView(li);
			l_merge.addView(img1);
			

			tab = tabHost.newTabSpec("");
			Intent intent1= new Intent(HeadTabActivityAdmin.this,MainActivity.class);
			//intent1.putExtra("order_id", ""+order_id);
			
			tab.setContent(intent1);
			tab.setIndicator(l_merge);
			
			tabHost.addTab(tab);
			tabHost.getTabWidget().setTag(TAB_LOGO);
			tabHost.setCurrentTab(1);
			
            
            tabHost.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(3).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(4).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(5).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(6).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(7).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
           
            tabHost.getTabWidget().getChildAt(8).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,100));
            
            tabHost.getTabWidget().getChildAt(8).setOnTouchListener(new OnTouchListener(){

	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	                int action = event.getAction();

	                if(action == MotionEvent.ACTION_UP)
	                {                       
	                    String currentTabTag = (String)tabHost.getTabWidget().getTag();//CurrentTabTag();
	                    String clickedTabTag = (String)v.getTag();
	                    System.out.println("clickd.............."+clickedTabTag);
	                    System.out.println("clickd.............."+currentTabTag);
	                    if(currentTabTag.equals(TAB_LOGO))
	                    {
	                        return true; // Prevents from clicking
	                    }
	                }
	                return false;
	            }               
	        });
			tabHost.setOnTabChangedListener(this);
	}

	@Override
	public void onTabChanged(String tabId) {
		//int id = Integer.parseInt(tabId);
		for (int i = 0; i < getTabWidget().getChildCount(); i++) {
			/*if (id == i && i!= 4) {
				System.out.println("id of the tab seection.........."+i);
				getTabWidget().getChildTabViewAt(i).setBackgroundResource(R.color.un_sel_tab_color);
			}else if(i==4){
				getTabWidget().getChildTabViewAt(i).setBackgroundResource(R.color.sel_tab_color);
			} else {
				getTabWidget().getChildTabViewAt(i).setBackgroundResource(R.color.sel_tab_color);
				
			}*/
		}
		
	}

}
