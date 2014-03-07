package pos.main.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pos.main.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;



public class Search_Items_Adapter extends ArrayAdapter<HashMap<String, String>> {
	
	private static String KEY_ITEM_NAME = "item_name";
	private static String KEY_ITEM_CODE = "item_code";
	//private static String KEY_ITEM_IMAGE = "item_image";
	
	private static LayoutInflater inflater=null;
	
	Context context;
	ArrayList<HashMap<String, String>> arrayList; 
	
	ArrayList<HashMap<String, String>> items_list;
	
	public Search_Items_Adapter(Context context, int textViewResourceId,ArrayList<HashMap<String, String>> items_list) {
		//let android do the initializing :)
    	super(context, textViewResourceId, items_list);
    	this.context= context;
    	inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	this.items_list= items_list;
    	arrayList= items_list;
	}
    
	//class for caching the views in a row  
	private class ViewHolder{
    	TextView item_name;
    	TextView item_code;
    	ImageView image;
	}
 
    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
    	if(convertView==null){
    		                        
    		convertView=inflater.inflate(R.layout.list_item_code,parent,  false);
    		viewHolder=new ViewHolder();
    		//cache the views
    		viewHolder.item_name=(TextView) convertView.findViewById(R.id.item_name_list);
    		viewHolder.item_code=(TextView) convertView.findViewById(R.id.item_code_list);
    		viewHolder.image=(ImageView) convertView.findViewById(R.id.item_image_list);
    		//link the cached views to the convertview
    		convertView.setTag(viewHolder);
   
    	}else
    		viewHolder=(ViewHolder) convertView.getTag();
    	
    	
    	viewHolder.item_name.setText(items_list.get(position).get(KEY_ITEM_NAME).toString());
    	viewHolder.item_code.setText(items_list.get(position).get(KEY_ITEM_CODE).toString());
    	System.out.println(items_list.get(position).get(KEY_ITEM_NAME).toString());
    	//viewHolder.image.setTag(items_list.get(position).get(KEY_ITEM_IMAGE).toString());
    	
    	return convertView;
    }
    

    @Override
	public Filter getFilter() {
	   Filter filter = new Filter() {
		   @SuppressWarnings("unchecked")
	       @Override
	       protected void publishResults(CharSequence constraint,FilterResults results) {
			   arrayList = (ArrayList<HashMap<String, String>>) results.values; // has the filtered values
			   notifyDataSetChanged();  // notifies the data with new filtered values
		   }
		   @SuppressLint("DefaultLocale")
		@Override
		   protected FilterResults performFiltering(CharSequence constraint) {
			   FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
			   List<String> FilteredArrList = new ArrayList<String>();
			   if (items_list == null) {
				   items_list = (arrayList); // saves the original data in mOriginalValues
			   }

	 
			   if (constraint == null || constraint.length() == 0) {
				   // set the Original result to return  
				   results.count = items_list.size();
				   results.values = items_list;
			   } else {
				   constraint = constraint.toString().toLowerCase();
				   for (int i = 0; i < items_list.size(); i++) {
					   String data = items_list.get(i).get(KEY_ITEM_CODE).toString();
					   
					   if (data.toLowerCase().startsWith(constraint.toString())) {
						   FilteredArrList.add(data);
						   System.out.println("data........."+data);
					   }
				   }
				   // set the Filtered result to return
				   results.count = FilteredArrList.size();
				   results.values = FilteredArrList;
				   
				   System.out.println("count........."+results.count+"........."+ results.values);
			   }
			   return results;
		   }
	   };
	   return filter;
	}
}
