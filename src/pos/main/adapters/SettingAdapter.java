package pos.main.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import pos.main.ui.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class SettingAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final String[] valuesDet={"Sycn item list with CMS", "Sycn Delivery Note with CMS", "", ""};
	private final int[] valuesimages={R.drawable.setting_image_change,R.drawable.setting_image_change, R.drawable.language, R.drawable.timeout};
 
	public SettingAdapter(Context context, String[] values) {
		super(context, R.layout.item_sync, values);
		this.context = context;
		this.values = values;
	}
 
	
	 private class ViewHolder{
		 TextView heading;
			TextView det;
			ImageView imageView;// = (ImageView) rowView.findViewById(R.id.update2);
	}
	 
	 ViewHolder viewHolder;
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView==null){
    		convertView=inflater.inflate(R.layout.item_sync, null);
    		viewHolder=new ViewHolder();
    		
    		viewHolder.imageView=(ImageView) convertView.findViewById(R.id.listimage);
    		viewHolder.heading=(TextView) convertView.findViewById(R.id.update1);
    		viewHolder.det=(TextView) convertView.findViewById(R.id.update2);
    		//viewHolder.merc_frnds=(TextView) convertView.findViewById(R.id.merc_friends_list);
    		
    		convertView.setTag(viewHolder);
   
    	}else
    		viewHolder=(ViewHolder) convertView.getTag();
		
		
    	viewHolder.heading.setText(values[position]);
    	viewHolder.det.setText(valuesDet[position]);
    	viewHolder.imageView.setImageResource(valuesimages[position]);
    	
 
		return convertView;
	}
}

/*extends ArrayAdapter<HashMap<String, String>>{
	
	private static String KEY_BUSINESS_NAME = "business_name";
	private static String KEY_BUSINESS_DISTANCE = "distance";
	private static String KEY_BUSINESS_FRIENDS = "friends_were_here";

	private static String KEY_BUSINESS_SMALLIMAGE = "small_image";
	
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader;
	Context context;
	
	ArrayList<HashMap<String, String>> merc_list;
	
    public SettingAdapter(Context context, int textViewResourceId,ArrayList<HashMap<String, String>> merc_list) {
    	
    	super(context, textViewResourceId, merc_list);
    	this.context= context;
    	
    	inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	imageLoader=new ImageLoader(context.getApplicationContext());
    	this.merc_list= merc_list;
    	
    }
      
    private class ViewHolder{
    	ImageView photo;
    	TextView merc_name,merc_distence, merc_frnds;
  
    }
 
    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
    	if(convertView==null){
    		convertView=inflater.inflate(R.layout.list_style, null);
    		viewHolder=new ViewHolder();
    		viewHolder.photo=(ImageView) convertView.findViewById(R.id.listimage);
    		viewHolder.merc_name=(TextView) convertView.findViewById(R.id.merch_name_list);
    		viewHolder.merc_distence=(TextView) convertView.findViewById(R.id.merc_distence_list);
    		viewHolder.merc_frnds=(TextView) convertView.findViewById(R.id.merc_friends_list);
    		
    		if(position%2==0)
    			convertView.setBackgroundResource(R.drawable.merc_dark_panel);
    		else 
    			convertView.setBackgroundResource(R.drawable.merc_white_panel);
    		convertView.setTag(viewHolder);
   
    	}else
    		viewHolder=(ViewHolder) convertView.getTag();
    	
    	String photoId= merc_list.get(position).get(KEY_BUSINESS_SMALLIMAGE);
    	
    	viewHolder.photo.setTag(merc_list.get(position).get(KEY_BUSINESS_NAME).toString());
        imageLoader.DisplayImage(photoId, (Activity) context, viewHolder.photo);
        
    	viewHolder.merc_name.setText(merc_list.get(position).get(KEY_BUSINESS_NAME).toString());
    	viewHolder.merc_distence.setText(merc_list.get(position).get(KEY_BUSINESS_DISTANCE).toString());
    	viewHolder.merc_frnds.setText(merc_list.get(position).get(KEY_BUSINESS_FRIENDS).toString());
 
    	return convertView;
    }
}*/
