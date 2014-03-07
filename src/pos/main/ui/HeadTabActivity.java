package pos.main.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pos.main.model.Global;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class HeadTabActivity extends TabActivity implements OnTabChangeListener {

	
	private static final String TAB_LOGOUT = "0";
	private static final String TAB_ITEMS = "1";
	private static final String TAB_SALEVOUCHER = "2";
	private static final String TAB_MENU = "3";
	//private static final String TAB_SETTING = "4";
	private static final String TAB_LOGO = "5";
	
	String months[] = {
		      "Jan", "Feb", "Mar", "Apr",
		      "May", "Jun", "Jul", "Aug",
		      "Sep", "Oct", "Nov", "Dec"};

	private TabHost tabHost;
	
	
	
	private static HeadTabActivity theInstance;

    public static HeadTabActivity getInstance() {
    	
        return HeadTabActivity.theInstance;
    }

    public HeadTabActivity() {
    	HeadTabActivity.theInstance = this;
    }

	@SuppressLint({ "NewApi", "SimpleDateFormat" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.top_tab_host);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			System.out.println("tab activity refresh...");
			
			String login=" James";
			GregorianCalendar gcalendar = new GregorianCalendar();
			Date dNow = new Date( );
		      SimpleDateFormat ft =  new SimpleDateFormat ("hh:mm a");
			 String m=months[gcalendar.get(Calendar.MONTH)]+" "+gcalendar.get(Calendar.DATE)+" "+ft.format(dNow);
			  
		//	String styledText = "<big>" +login  + "</big>"+ "<br/>"+"<small> "
		//		            + "<b>"+ m+ "</b></small>" + "<br />" ;
			
			Display display = getWindowManager().getDefaultDisplay();
	        int width = display.getWidth();
	        int hight= display.getHeight();
			
			tabHost = getTabHost();
			tabHost.setHorizontalScrollBarEnabled(true);
			
			TabSpec tab = tabHost.newTabSpec("Tabs");
			getTabWidget().setStripEnabled(false);
			
			tab = tabHost.newTabSpec(TAB_LOGOUT);
			Global.active_user= "";
			
			
			/*
			 * 
			 */
			tab.setContent(new Intent(this, MainActivity1.class));
			tab.setIndicator("");
			//tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(0).setBackgroundResource(R.drawable.tab_logout);
			
			tab = tabHost.newTabSpec(TAB_ITEMS);
			tab.setContent(new Intent(this, Item_View.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(1).setBackgroundResource(R.drawable.tab_items);
			
			
			tab = tabHost.newTabSpec(TAB_SALEVOUCHER);
			tab.setContent(new Intent(this, MainActivity1.class));
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(2).setBackgroundResource(R.drawable.tab_sale_voucher);
			
			tab = tabHost.newTabSpec(TAB_MENU);
			tab.setContent(new Intent(this, MainActivity1.class));
			//tab.setIndicator("Menu", null);
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(3).setBackgroundResource(R.drawable.tab_menu);
			
			/*tab = tabHost.newTabSpec(TAB_SETTING);
			tab.setContent(new Intent(this, Setting1.class));
			//tab.setIndicator("Menu", null);
			tab.setIndicator("");
			tabHost.addTab(tab);
			getTabWidget().getChildTabViewAt(4).setBackgroundResource(R.drawable.tab_setting);
			*/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			///
			TextView txtTab = new TextView(this);
			txtTab.setText("Order: 0000  ");
			txtTab.setPadding(8, 9, 8, 9);
			txtTab.setTextColor(Color.WHITE);
			txtTab.setTextSize(20);
			txtTab.setId(4);
			txtTab.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.logo, 0);
			
			txtTab.setGravity( Gravity.CENTER_VERTICAL| Gravity.RIGHT);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER_VERTICAL;
			
			LinearLayout li= new LinearLayout(this);
			li.setLayoutParams(params);
			li.setOrientation(LinearLayout.VERTICAL);
			li.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
			li.setPadding(120, 10, 0, 10);
			
			
			LinearLayout li1= new LinearLayout(this);
			li1.setLayoutParams(params);
			li1.setOrientation(LinearLayout.HORIZONTAL);
			li1.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
			
			
			TextView txtTab1 = new TextView(this);
			txtTab1.setText(login);
			//txtTab1.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.notif_bell, 0);
			txtTab1.setPadding(30, 0, 10, 0);
			txtTab1.setTextColor(Color.WHITE);
			txtTab1.setTextSize(20);
			txtTab1.setGravity(  Gravity.CENTER_VERTICAL|Gravity.LEFT);
			
			ImageView img= new ImageView(this);
			img.setBackgroundResource(R.drawable.notif_bell);
			
			TextView txtTab11 = new TextView(this);
			txtTab11.setText(m);
			//txtTab11.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.notif_bell, 0);
			txtTab11.setPadding(30, 0, 0, 0);
			txtTab11.setTextColor(Color.WHITE);
			txtTab11.setTextSize(20);
			txtTab11.setGravity(  Gravity.CENTER_VERTICAL|Gravity.LEFT);
			
			
			//txtTab1.setLayoutParams(params);
			
			li1.addView(txtTab1);
			li1.addView(img);
			
			li.addView(li1);
			li.addView(txtTab11);
			tab = tabHost.newTabSpec("");
			
			
			tab.setContent(new Intent(this, MainActivity.class));
			//tab.setIndicator("Menu", null);
			tab.setIndicator(li);
			tabHost.addTab(tab);
			//getTabWidget().getChildTabViewAt(4).setBackgroundResource(R.drawable.top_bar);
			
			tab = tabHost.newTabSpec(TAB_LOGO);
			tab.setContent(new Intent(this, MainActivity1.class));
			tab.setIndicator(txtTab);
			tabHost.addTab(tab);
			//getTabWidget().getChildTabViewAt(5).setBackgroundResource(R.drawable.top_bar);
			
			
			tabHost.setCurrentTab(5);
			System.out.println(width+"....."+hight+"...."+((width/2)-2)/2);
            //tabHost.getTabWidget().getChildAt(5).setLayoutParams(new LinearLayout.LayoutParams(300,100));
            tabHost.getTabWidget().getChildAt(4).setLayoutParams(new LinearLayout.LayoutParams(300,100));
            tabHost.getTabWidget().getChildAt(3).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,100));
            tabHost.getTabWidget().getChildAt(5).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,100));
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
