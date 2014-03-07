package pos.main.ui;

import pos.main.model.Global;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.InputType;
import android.text.Selection;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class Login_POPUP implements OnClickListener{
	
	private  Activity activity;
	AlertDialog  dialog;
	
	EditText pincode, passcode;
	Button one, two, three, four, five, six, seven, eight, nine, zero;
	Button enter, clear;
	boolean focus_user_id= false, focus_pin= false;
	String code="";
	String pin="";
	
	String active_user="1";
	boolean login_finish= false;
	
	String pass1="123", pass2="123", pin1= "0123", pin2="1234";
	
	
	Login_POPUP(Activity activity, String active_user) {
		super();
		this.activity = activity;
		this.active_user= active_user;
	}
	
	public boolean showDialog() {
		
		boolean login_finish= false;
		dialog = new AlertDialog.Builder(activity).create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		dialog.show();
		dialog.setCancelable(false);
				//Dialog(_activity, android.R.style.Theme_Translucent_NoTitleBar);
		
		dialog.setContentView(R.layout.popup_login);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		pincode= (EditText) dialog.findViewById(R.id.input);
		pincode.setText("");
		pincode.setInputType(InputType.TYPE_NULL);
		pincode.setCursorVisible(true);
		
		passcode= (EditText) dialog.findViewById(R.id.input_pass);
		passcode.setText("");
		passcode.setInputType(InputType.TYPE_NULL);
		passcode.setCursorVisible(true);
		
		enter = (Button) dialog.findViewById(R.id.equal);
		clear = (Button) dialog.findViewById(R.id.cancel);
		one = (Button) dialog.findViewById(R.id.one);
		two = (Button) dialog.findViewById(R.id.two);
		three = (Button) dialog.findViewById(R.id.three);
		four = (Button) dialog.findViewById(R.id.four);
		five = (Button) dialog.findViewById(R.id.five);
		six = (Button) dialog.findViewById(R.id.six);
		seven = (Button) dialog.findViewById(R.id.seven);
		eight = (Button) dialog.findViewById(R.id.eight);
		nine = (Button) dialog.findViewById(R.id.nine);
		zero = (Button) dialog.findViewById(R.id.zero);
		
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
		//int position = pincode.length();
		//Selection.setSelection(pincode.getText(), position);
		//code= pincode.getText().toString();
		//System.out.println(code);
		
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
	  //  AlertDialog.Builder alert = new AlertDialog.Builder(mContext);//we use the context
		return login_finish;
	  }

	
	private void invalidUserPopup() {
		
		final AlertDialog  dialog = new AlertDialog.Builder(activity).create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
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
				System.out.println(active_user);
				
				if(active_user.equals("1")){
					if(a.trim().equals(pin1) && pin.trim().equals(pass1) ){
						if(dialog.isShowing())
							dialog.dismiss();
						login_finish= true;
						code="";
						pin="";
						
					}else if(a.trim().equals(pin2) && pin.trim().equals(pass2) ){
						active_user="2";
						System.out.println("active"+ active_user+"............"+pin2+".........."+a);
						Global.active_user= active_user;
						dialog.dismiss();
						Intent i= new Intent(activity, HeadTabActivity.class);
						activity.finish();
						activity.startActivity(i);
						
					}
					else{
						invalidUserPopup();
					}
				}else if(active_user.equals("2")){
					if(a.trim().equals(pin2) && pin.trim().equals(pass2) ){
						if(dialog.isShowing())
							dialog.dismiss();
						login_finish= true;
						code="";
						pin="";
						
					}else if(a.trim().equals(pin1) && pin.trim().equals(pass1) ){
						active_user="1";
						Global.active_user= active_user;
						dialog.dismiss();
						System.out.println("active"+ active_user+"............"+pin1+".........."+a);
						Intent i= new Intent(activity, HeadTabActivity.class);
						activity.finish();
						activity.startActivity(i);
						
					}
					else{
						invalidUserPopup();
					}
				}
				
			}//else
				//invalidUserPopup();
		}else if(v== clear){
			
			if(focus_pin){
				String s= passcode.getText().toString();
				System.out.println(pin.length()+"..........."+ s.length());
				if(s.length()>0){
					System.out.println(pin);
					s= s.substring(0, s.length()-1);
					pin= pin.substring(0, pin.length()-1);
				}
				passcode.setText(s);
				
			}else if(focus_user_id){
				code= pincode.getText().toString();
				System.out.println(code.length()+"........."+code);
				if(code.length()>0)
					code= code.substring(0, code.length()-1);
				int position = code.length();
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
				System.out.println(pin);
				int position = passcode.length();
				Selection.setSelection(passcode.getText(), position);
			
			}else if(focus_user_id){
				pincode.setCursorVisible(true);
				code= code+""+buttonText;
				pincode.setText(""+code);
				System.out.println(code);
				int position = pincode.length();
				Selection.setSelection(pincode.getText(), position);
			}
		}
	
		
	}

	
}
