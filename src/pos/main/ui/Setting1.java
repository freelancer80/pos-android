package pos.main.ui;


import org.json.JSONArray;
import org.json.JSONException;

import pos.main.adapters.SettingAdapter;
import pos.main.adapters.TransparentProgressDialog;
import pos.main.model.Global;
import pos.main.syncConnection.SyncItems;
import android.app.AlertDialog;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class Setting1 extends ListActivity {
	 
		static final String[] MOBILE_OS = 
	               new String[] { "Update Items", "Notes Sync", "Language", "Time Out"};
		Context context;
		Button add_user, view_all;
		String s="not_found";
		String [] ids_synch;
		String pause;
		int count_of_update ;
		boolean ids_up= false;
		
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.setting_sync1);
			setListAdapter(new SettingAdapter(this, MOBILE_OS));
			context= this;
			
			add_user= (Button) findViewById(R.id.add_user);
			view_all= (Button) findViewById(R.id.view_user);
			
			pd = new TransparentProgressDialog(this, R.drawable.spinner2);
			r =new Runnable() {
				@Override
				public void run() {
					if (pd.isShowing()) {
						pd.dismiss();
					}
				}
			};
			
		/*	add_user.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			view_all.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});*/
		}
	 
		JSONArray jsonArray =null;
		
		@Override
		protected void onListItemClick(ListView l, final View v, int position, long id) {
			//get selected items
			String selectedValue = (String) getListAdapter().getItem(position);
			if(selectedValue.equals("Update Items")){
				pause= "items";
				final SyncItems sync= new SyncItems(context);
				v.setEnabled(false);
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        pd.show();
                    }
                    @Override
                    protected Void doInBackground(Void... arg0) {
                        try {
                        	String r=sync.getItems_Count(Global.shop_id);
                        	if(r.equals("\"\"")){
                        		s= "not_found";
                        	}else{
                        		try{
                        			jsonArray=null;
                        			jsonArray = new JSONArray(r);
                        			ids_synch= null;
									ids_synch= new String[jsonArray.length()];
									counter=0;
									ids_up= false;
									System.out.println("lenght.........."+jsonArray.length());
									count_of_update=jsonArray.length();
									
									if(jsonArray.length()>0)
	                            		s= "item_found";
									
								} catch (JSONException e) {
									e.printStackTrace();
								}
                        		
                            }
                        	 //s=sync.send_Shop("1");
            				//Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            				Thread.sleep(10);
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                        return null;
                    }
                    
                    @Override
                    protected void onPostExecute(Void result) {
                        if (pd!=null) {
                            pd.dismiss();
                            
                            if(s.equals("item_found")){
                            	String s=progressBar(v);
                            	
                            }else if(s.equals("not_found")){
                            	invalidUserPopup();
                            }
                           // dialog.dismiss();
                            v.setEnabled(true);
                        }
                    }    
                };
                task.execute((Void[])null);
            }else if(selectedValue.equals("Notes Sync")){
				pause= "notes";
				final SyncItems sync= new SyncItems(context);
				v.setEnabled(false);
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        pd.show();
                    }
                    @Override
                    protected Void doInBackground(Void... arg0) {
                    	try{
                    		Thread.sleep(1000);
                        	String r=sync.getIDelvery_Count(Global.shop_id);
                        	if(r.equals("\"\"")){
                        		s= "not_found";
                        	}else{
                        		try{
                        			jsonArray=null;
                        			jsonArray = new JSONArray(r);
                        			ids_synch= null;
									ids_synch= new String[jsonArray.length()];
									counter=0;
									ids_up= false;
									System.out.println("lenght.........."+jsonArray.length());
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                        		if(jsonArray.length()>0)
                            		s= "item_found";
                            }
                        	Thread.sleep(10);
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        if (pd!=null) {
                            pd.dismiss();
                            if(s.equals("item_found")){
                            	progressBar(v);
                            	
                            }else if(s.equals("not_found")){
                            	invalidUserPopup();
                            }
                            v.setEnabled(true);
                        }
                    }
                };
                task.execute((Void[])null);
			}
			//Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
	 
		}
		
		private TransparentProgressDialog pd;
		Runnable r;
		ProgressDialog progressBar;
		private int progressBarStatus = 0;
		private Handler progressBarHandler = new Handler();
		private long fileSize = 0;
		int counter=0;
		
		
		private String progressBar(View v) {
			// prepare for a progress bar dialog
			progressBar = new ProgressDialog(v.getContext());
			
			progressBar.setCancelable(false);
			if(pause.equals("items")){
				progressBar.setMessage("Items Sync ...");
			}else if(pause.equals("notes")){
				progressBar.setMessage("Delivery Notes Sync ...");
			}
			
			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressBar.setProgress(0);
			progressBar.setMax(jsonArray.length());
			progressBar.show();

			//reset progress bar status
			progressBarStatus = 0;
			
			//reset filesize
			fileSize = jsonArray.length()/10;
			
			final SyncItems sync= new SyncItems(context);
			new Thread(new Runnable() {
				public void run() {
					System.out.println("counter............"+counter);
					while (jsonArray.length()>counter) {
						try {
							if(pause.equals("items")){
								String s=sync.send_shop1(counter, jsonArray);
								ids_synch[counter] = s.trim();
								System.out.println("id.............."+ids_synch[counter]+"....counter"+counter);
								ids_up= true;
								
							}else if(pause.equals("notes")){
								String s=sync.saveDeliveryItems(counter, jsonArray);
								ids_synch[counter] = s.trim();
								System.out.println("id"+ids_synch[counter]+"....counter"+counter);
								ids_up= true;
							}
							counter++;
							progressBarStatus =counter;
							Thread.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Update the progress bar
						progressBarHandler.post(new Runnable() {
							public void run() {
								progressBar.setProgress(progressBarStatus);
							}
						});
					}
					
				/*	while (progressBarStatus < 100) {
						
						progressBarStatus = doSomeTasks();
						try {
							//Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}

						// Update the progress bar
						progressBarHandler.post(new Runnable() {
							public void run() {
								progressBar.setProgress(progressBarStatus);
							}
						});
					}*/

					// ok, file is downloaded,
					if (progressBarStatus >= jsonArray.length()) {
						// sleep 2 seconds, so that you can see the 100%
						try {
							
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
						progressBar.dismiss();
						if(ids_synch.length>0){
							if(pause.equals("items") && ids_up){
								s=sync.sendItemUpdate(ids_synch, "1");
								if(s.equals("OK")){
									//successfullySync();
								}
							}else if(pause.equals("notes")  && ids_up){
								boolean s=sync.sendDeliveryReceivde(ids_synch);
								try {
									
									Thread.sleep(1000);
								} catch (Exception e) {
									e.printStackTrace();
								}
								if(s){
									//successfullySync();
								}
								//s=sync.sendItemUpdate(ids_synch);
							}
                    	}
					}
				}
			}).start();
		
		
		
		return s;
		
		}
		
		
		public int doSomeTasks() {
			while (fileSize <= 1000000) {

				fileSize++;

				if (fileSize == 100000) {
					return 10;
				} else if (fileSize == 200000) {
					return 20;
				} else if (fileSize == 300000) {
					return 30;
				}else if (fileSize == 400000) {
					return 40;
				}else if (fileSize == 500000) {
					return 50;
				}else if (fileSize == 600000) {
					return 60;
				}else if (fileSize == 700000) {
					return 70;
				}else if (fileSize == 800000) {
					return 80;
				}else if (fileSize == 900000) {
					return 90;
				}
			}
			return 100;

		}

		private void invalidUserPopup() {
			
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
			det.setText("No Update Found");
			confirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
			    }});
		}
		
		private void successfullySync() {
			
			final AlertDialog  dialog = new AlertDialog.Builder(this).create();
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.show();
					//Dialog(_activity, android.R.style.Theme_Translucent_NoTitleBar);
			
			dialog.setContentView(R.layout.invalid_user);
			dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			
			Button confirm = (Button)dialog.findViewById(R.id.cancel);
			TextView heading= (TextView) dialog.findViewById(R.id.heading);
			TextView det= (TextView) dialog.findViewById(R.id.det);
			
			heading.setText("Successfully Update");
			heading.setTextColor(Color.BLACK);
			
			det.setText("Total "+ jsonArray.length()+"Item Sync.");
			confirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
			    }});
		}
	
	/*
	private class TransparentProgressDialog extends Dialog {
		
		private ImageView iv;
		public TransparentProgressDialog(Context context, int resourceIdOfImage) {
			super(context, R.style.TransparentProgressDialog);
        	WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        	wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        	getWindow().setAttributes(wlmp);
			setTitle(null);
			setCancelable(false);
			setOnCancelListener(null);
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			iv = new ImageView(context);
			iv.setImageResource(resourceIdOfImage);
			layout.addView(iv, params);
			addContentView(layout, params);
		}
		
		@Override
		public void show() {
			super.show();
			RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
			anim.setInterpolator(new LinearInterpolator());
			anim.setRepeatCount(Animation.INFINITE);
			anim.setDuration(3000);
			iv.setAnimation(anim);
			iv.startAnimation(anim);
		}
	}*/
}
