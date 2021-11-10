package in.iplayon.analyst.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import in.iplayon.analyst.R;


public class FourthBallAdapter extends BaseAdapter implements ListAdapter {
	
	private final Activity mActivity;
    private final JSONArray mJsonArray;
    private final Context mContext;

    public FourthBallAdapter(Context context, Activity activity, JSONArray jsonArray) {

        this.mContext = context;
        this.mJsonArray = jsonArray;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
    	
        if(null==mJsonArray) 
         return 0;
        else
        	return mJsonArray.size();
    }

    @Override
    public JSONObject getItem(int position) {
         if(null==mJsonArray) return null;
         else
           return (JSONObject) mJsonArray.get(position);
    }

    @Override
    public long getItemId(int position) {
		return position;
     
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       

		if(convertView == null)
		{
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = lInflater.inflate(R.layout.fourthball_analysis,null);
		}
		
		TextView offensive_defensive_count = (TextView) convertView.findViewById(R.id.offensive_defensive_count);
		TextView offensive_offensive_count = (TextView) convertView.findViewById(R.id.offensive_offensive_count);
		TextView defensive_offensive_count = (TextView) convertView.findViewById(R.id.defensive_offensive_count);
		TextView defensive_defensive_count = (TextView) convertView.findViewById(R.id.defensive_defensive_count);
		TextView offensive_lost_count = (TextView) convertView.findViewById(R.id.offensive_lost_count);
		TextView defensive_lost_count = (TextView) convertView.findViewById(R.id.defensive_lost_count);
		TextView offensive_win_count = (TextView) convertView.findViewById(R.id.offensive_win_count);
		TextView defensive_win_count = (TextView) convertView.findViewById(R.id.defensive_win_count);

		
        JSONObject json_data = getItem(position);
        if(null!=json_data )
        {
        	if(json_data.containsKey("strokeHand1") && json_data.containsKey("strokeHand2"))
        	{
        		String strokeHand1 = json_data.get("strokeHand1").toString();
        		String strokeHand2 = json_data.get("strokeHand2").toString();
        		
        		if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("defensive"))
        		{
        			if(json_data.containsKey("count"))
        				offensive_defensive_count.setText(json_data.get("count").toString());   				
        		}      			
        		else if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("offensive"))
        		{
        			if(json_data.containsKey("count"))
        				offensive_offensive_count.setText(json_data.get("count").toString());
        		}   			
        		else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("offensive"))
            	{
            		if(json_data.containsKey("count"))
            			defensive_offensive_count.setText(json_data.get("count").toString());
            	}
        		else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("defensive"))
                {
                	if(json_data.containsKey("count"))
                		defensive_defensive_count.setText(json_data.get("count").toString());
                }
        		else if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("lost"))
                {
                	if(json_data.containsKey("count"))
                		offensive_lost_count.setText(json_data.get("count").toString());
                }
        		else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("lost"))
                {
                	if(json_data.containsKey("count"))
                		defensive_lost_count.setText(json_data.get("count").toString());
                }
        		else if(strokeHand1.equalsIgnoreCase("offensive") && strokeHand2.equalsIgnoreCase("win"))
                {
                	if(json_data.containsKey("count"))
                		offensive_win_count.setText(json_data.get("count").toString());
                }
        		else if(strokeHand1.equalsIgnoreCase("defensive") && strokeHand2.equalsIgnoreCase("win"))
                {
                	if(json_data.containsKey("count"))
                		defensive_win_count.setText(json_data.get("count").toString());
                }     		
        	}
        }          
        return convertView;
    }
}
