package in.iplayon.analyst.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import in.iplayon.analyst.R;


public class RallyAdapter extends BaseAdapter implements ListAdapter {

	private final Activity mActivity;
    private final JSONArray mJsonArray;
    private final Context mContext;

    public RallyAdapter(Context context, Activity activity, JSONArray jsonArray) {

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
			convertView = lInflater.inflate(R.layout.rally_analytics,null);
		}
		
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.rallyHeaderLayout);
		TextView sequenceLen = (TextView) convertView.findViewById(R.id.sequenceLen);
		TextView shotsPlayed = (TextView) convertView.findViewById(R.id.shotsPlayed);
		TextView winCount = (TextView) convertView.findViewById(R.id.winCount);
		TextView lossCount = (TextView) convertView.findViewById(R.id.lossCount);
		TextView efficiency = (TextView) convertView.findViewById(R.id.efficiency);

		if(position%2 == 0)		
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		else
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
		
        JSONObject json_data = getItem(position);
	
        if(null!=json_data )
        {
        	if(json_data.containsKey("sequenceLen"))      	
        		sequenceLen.setText(json_data.get("sequenceLen").toString()+""); 
        			       	
        	if(json_data.containsKey("played"))	
    			shotsPlayed.setText(json_data.get("played").toString()+"");

            if(json_data.containsKey("winCount"))	
    			winCount.setText(json_data.get("winCount").toString()+"");

            if(json_data.containsKey("lossCount"))	
    			lossCount.setText(json_data.get("lossCount").toString()+"");

        	if(json_data.containsKey("efficiency"))
        	{
        		if(json_data.get("efficiency").toString().equalsIgnoreCase("0"))
    			{
    				efficiency.setText(json_data.get("efficiency").toString()+"%");
    			}
    			else
    			{
    				String temp = Math.round(Float.parseFloat(json_data.get("efficiency").toString()))+"%";
    				efficiency.setText(temp);
    			}
        	}



	
        }
        
        
         return convertView;
    }

}