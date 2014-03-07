package pos.main.ui;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import pos.main.database.Create_Tables;
import pos.main.database.Items_Database;
import pos.main.database.LoginDatabase;
import pos.main.database.Order_Database;
import pos.main.model.Global;
import pos.main.model.Users;
import pos.main.syncConnection.MySqlConnection;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginPin extends Activity implements OnClickListener{
	
	
	EditText pincode, passcode;
	Button one, two, three, four, five, six, seven, eight, nine, zero;
	Button enter, clear;
	boolean focus_user_id= false, focus_pin= false;
	String code="";
	String pin="";
	String active_user;
	
	String pass1="123", pass2="123", pin1= "0123", pin2="1234";
	
	LoginDatabase db_users;
	Create_Tables db_tables;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_pin);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		//Global.active_user= "";
		Global.shop_id= "1";
		
		/*
		 * add user
		 */
		db_tables = new Create_Tables(getApplicationContext());
		db_tables.closeDB();
		
		//db_users = new LoginDatabase(getApplicationContext());
		
	/*	Users item= new Users("0123", "123", "fn", "fl", "email", "mob", "1");
		
		long item_id = db_users.createUser(item);
		System.out.println("item inserted id is...:"+ item_id);
		
		item= new Users("1234", "123", "James", "fl", "email", "mob", "2");
		
		item_id = db_users.createUser(item);
		System.out.println("item inserted id is...:"+ item_id);
		
		item= new Users("2345", "123", "James2", "fl", "email", "mob", "2");
		
		item_id = db_users.createUser(item);
		System.out.println("item inserted id is...:"+ item_id);
		
		*/
		
		//db_users.closeDB();
		pincode= (EditText) findViewById(R.id.input);
		pincode.setText("");
		pincode.setInputType(InputType.TYPE_NULL);
		pincode.setCursorVisible(true);
		
		passcode= (EditText) findViewById(R.id.input_pass);
		passcode.setText("");
		passcode.setInputType(InputType.TYPE_NULL);
		passcode.setCursorVisible(true);
		
		enter = (Button) findViewById(R.id.equal);
		clear = (Button) findViewById(R.id.cancel);
		one = (Button) findViewById(R.id.one);
		two = (Button) findViewById(R.id.two);
		three = (Button) findViewById(R.id.three);
		four = (Button) findViewById(R.id.four);
		five = (Button) findViewById(R.id.five);
		six = (Button) findViewById(R.id.six);
		seven = (Button) findViewById(R.id.seven);
		eight = (Button) findViewById(R.id.eight);
		nine = (Button) findViewById(R.id.nine);
		zero = (Button) findViewById(R.id.zero);
		
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
		clear.setOnClickListener(this);
		enter.setOnClickListener(this);
		
		focus_user_id= true;
		focus_pin= false;
		//pincode.requestFocus();
		int position = pincode.length();
		Selection.setSelection(pincode.getText(), position);
		code= pincode.getText().toString();
		System.out.println(code);
		
		pincode.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				focus_user_id= true;
				focus_pin= false;
				//pincode.requestFocus();
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
				code= pincode.getText().toString();
				System.out.println(code);
				return false;
			}
		});

		
		passcode.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				focus_pin= true;
				focus_user_id= false;
				//passcode.requestFocus();
				int position = passcode.length();
				Selection.setSelection(passcode.getText(), position);
				code= passcode.getText().toString();
				System.out.println(code);
				
				return false;
			}
		});
		
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void invalidUserPopup() {
		final AlertDialog  dialog = new AlertDialog.Builder(this).create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
				//Dialog(_activity, android.R.style.Theme_Translucent_NoTitleBar);
		
		dialog.setContentView(R.layout.invalid_user);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		Button confirm = (Button)dialog.findViewById(R.id.cancel);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(pincode.getText().toString().equals("")){
					
					focus_user_id= true;
					focus_pin= false;
					pincode.requestFocus();
					int position = pincode.length();
					Selection.setSelection(pincode.getText(), position);
					code= pincode.getText().toString();
					System.out.println(code);
					
				}else if(passcode.getText().toString().equals("")){
		    			
					focus_pin= true;
					focus_user_id= false;
					passcode.requestFocus();
					int position = passcode.length();
					Selection.setSelection(passcode.getText(), position);
					code= passcode.getText().toString();
					System.out.println(code);
		    		
				}else{

					focus_user_id= true;
					focus_pin= false;
					pincode.requestFocus();
					int position = pincode.length();
					Selection.setSelection(pincode.getText(), position);
					code= pincode.getText().toString();
					System.out.println(code);
					
				}
		    	dialog.dismiss();
		    }});
	}
	
	
	
	
	
	@Override
	public void onClick(View v) {
		
		if(v== enter){
			if(pincode.getText().toString().length()>0 && !focus_pin && focus_user_id){
				focus_pin= true;
				focus_user_id= false;
				passcode.requestFocus();
				int position = passcode.length();
				Selection.setSelection(passcode.getText(), position);
				code= passcode.getText().toString();
				
			}else if (pincode.getText().toString().length()>0 && passcode.getText().toString().length()>0) {
				
				String a =pincode.getText().toString();
				if(a.trim().equals(pin1) && pin.trim().equals(pass1)){
					active_user ="1";
					Global.active_user= "1";
					
					Intent i= new Intent(LoginPin.this, HeadTabActivity.class);
					startActivity(i);
					
				}else if(a.trim().equals(pin2) && pin.trim().equals(pass2) ){
					active_user= "2";
					Global.active_user= "2";
					System.out.println("active user id is:..........."+Global.active_user);
					Intent i= new Intent(LoginPin.this, HeadTabActivityAdmin.class);
					startActivity(i);
				}
				else{
					invalidUserPopup();
				}
				
			}else
				invalidUserPopup();
		}else if(v== clear){
			
			if(code.length()>0)
				code= code.substring(0, code.length()-1);
			int position = code.length();
			
			if(focus_pin){
				String s= passcode.getText().toString();
				if(pin.length()>0){
					s= s.substring(0, s.length()-1);
					pin= pin.substring(0, pin.length()-1);
				}
				
				passcode.setText(s);
				position = s.length();
				Selection.setSelection(passcode.getText(), position);
			}else if(focus_user_id){
				pincode.setText(code);
				Selection.setSelection(pincode.getText(), position);
			}
		}else if(v== pincode){
			focus_user_id= true;
			focus_pin= false;
			pincode.requestFocus();
			pincode.setCursorVisible(true);
			int position = pincode.length();
			Selection.setSelection(pincode.getText(), position);
			code= pincode.getText().toString();
			
		}else if(v== passcode){
			focus_pin= true;
			focus_user_id= false;
			passcode.requestFocus();
			int position = passcode.length();
			Selection.setSelection(passcode.getText(), position);
			code= passcode.getText().toString();
			
		}else{
			Button b = (Button)v;
		    String buttonText = b.getText().toString();
			
			if(focus_pin){
				pin= pin+""+buttonText;
				passcode.setText(passcode.getText().toString()+"*");
				int position = passcode.length();
				Selection.setSelection(passcode.getText(), position);
			
			}else if(focus_user_id){
				pincode.setCursorVisible(true);
				code= code+""+buttonText;
				pincode.setText(""+code);
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
			}
		}
	}


	@Override
	public void onPause() {
		super.onPause();
		
		System.out.print("on pause");
		
	}
	public void onResume() {
		super.onResume();
		System.out.print("on resume");
		
		
	}
	@Override
    protected void onDestroy() {
		System.out.println("counter stop when app exit");
        System.gc();
        super.onDestroy();
	}
	
	




}