package pos.main.ui;

import pos.main.model.Global;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class Log_out extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_pin);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		
	}

	@Override
	public void onPause() {
		super.onPause();
		
		System.out.print("on pause");
		
	}
	public void onResume() {
		super.onResume();
		System.out.print("on resume");
		
		Intent i= new Intent(Log_out.this, LoginPin.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		
		
	}
	@Override
    protected void onDestroy() {
		System.out.println("counter stop when app exit");
        System.gc();
        super.onDestroy();
	}
	
	
}
