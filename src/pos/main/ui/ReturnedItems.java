package pos.main.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ReturnedItems {
	
	Activity activity;
	TableLayout table;
	TableRow t_row;
	TextView t_text;
	CheckBox t_checkbox;
	
	
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
	
	int trHeight = 0;
    int  size=15;
    
	public ReturnedItems(Activity activity, TableLayout table) {
		super();
		this.activity = activity;
		this.table = table;
	}
	
	
	
	
	@SuppressWarnings("deprecation") 
	void createTable() {
		
        table.removeAllViews();
        t_row = new TableRow(activity);
        
        TextView sr1 = new TextView(activity);
        sr1.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        sr1.setId(size);
        sr1.setTextSize(size);
        sr1.setText("Sr.");
        sr1.setTextColor(Color.parseColor("#800080"));
        sr1.setTypeface(null, Typeface.BOLD);
        sr1.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        sr1.setPadding(5, 5, 5, 5);
        sr1.setHeight(trHeight);
        //t_row.setPadding(1, 1, 1, 1);
        t_row.addView(sr1);// add the column to the table row here

        TextView item_code = new TextView(activity);
        item_code.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        item_code.setId(size);// define id that must be unique
        item_code.setText("Item Code"); // set the text for the header 
        item_code.setTextColor(Color.parseColor("#800080"));
        item_code.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        item_code.setPadding(5, 5, 5, 5);
        item_code.setTextSize(size);
        item_code.setHeight(trHeight);
        item_code.setTypeface(null, Typeface.BOLD);
        t_row.addView(item_code); // add the column to the table row here
        
        
        TextView item_name = new TextView(activity);
        item_name.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        item_name.setId(size);
        item_name.setText("Item Name ");  
        item_name.setTextColor(Color.parseColor("#800080"));
        item_name.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        item_name.setPadding(5, 5, 5, 5);
        item_name.setTypeface(null, Typeface.BOLD);
        item_name.setHeight(trHeight);
        item_name.setTextSize(size);
        
        t_row.addView(item_name);
         
        TextView rate = new TextView(activity);
        rate.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        rate.setId(size);
        rate.setText(" Rate € ");  
        rate.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        rate.setTextColor(Color.parseColor("#800080"));
        rate.setPadding(5, 5, 5, 5);
        rate.setHeight(trHeight);
        rate.setTextSize(size);
        rate.setTypeface(null, Typeface.BOLD);
        t_row.addView(rate);
        
        TextView discount_type =  new TextView(activity);
        discount_type.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        discount_type.setId(size);
        discount_type.setText(" Disc "); 
        discount_type.setTextColor(Color.parseColor("#800080")); 
        discount_type.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        discount_type.setPadding(5, 5, 5, 5); 
        discount_type.setHeight(trHeight);
        discount_type.setTextSize(size);
        discount_type.setTypeface(null, Typeface.BOLD);
        t_row.addView(discount_type);
        
        TextView discount =  new TextView(activity);
        discount.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        discount.setId(size);
        discount.setText("Disc-Amt"); 
        discount.setTextColor(Color.parseColor("#800080")); 
        discount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        discount.setPadding(0, 5, 0, 5); 
        discount.setHeight(trHeight);
        discount.setTextSize(size);
        discount.setTypeface(null, Typeface.BOLD);
        t_row.addView(discount);
        
        TextView qty = new TextView(activity);
        qty.setId(size);
        qty.setText(" Qty "); 
        qty.setTextColor(Color.parseColor("#800080")); 
        qty.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        qty.setPadding(5, 5, 5, 5);
        qty.setHeight(trHeight);
        qty.setTextSize(size);
        qty.setTypeface(null, Typeface.BOLD);
        qty.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        t_row.addView(qty);
        
        TextView amount = new TextView(activity);
        amount.setId(size);
        amount.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.gradient_table_head));
        amount.setText(" Amount € "); 
        amount.setTextColor(Color.parseColor("#800080")); 
        amount.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        amount.setPadding(5, 5, 5, 5); 
        amount.setHeight(trHeight);
        amount.setTextSize(size);
        amount.setTypeface(null, Typeface.BOLD);
        t_row.addView(amount);
        
        table.addView(t_row);//, new TableLayout.LayoutParams(layoutpParams));
        
        for(int r=1; r<4; r++){
        	
        	t_row = new TableRow(activity);
        	t_row.setId(r);
        	t_row.setTag(r);
	 		
        	for(int col=0; col<KEY_COLUMN_LENGHT; col++){
        		
        		TextView text = new TextView(activity);
        		text.setHeight(trHeight);
        		text.setTextSize(size);
        		text.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        		if(r%2==1){
        			text.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.row_gradient_pink));
            	}else{
            		text.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.row_gradient));
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
        		t_row.addView(text);
			}
        	table.addView(t_row);
  	 	}
	}

}
