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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

import in.iplayon.analyst.R;


public class ServiceFaultAdapter extends BaseAdapter implements ListAdapter {
	
	private Context mContext;
	private Activity mActivity;
	private Map<String,String> mServiceKeyMap;
	private Map<String,String> mDestinationKeyMap;
    private JSONArray mJsonArray;

	public ServiceFaultAdapter(Context lContext, Activity lActivity, JSONArray jsonArray, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap) {
		this.mContext = lContext;
		this.mActivity = lActivity;
		this.mJsonArray = jsonArray;
		this.mServiceKeyMap = lServiceKeyMap;
		this.mDestinationKeyMap = lDestinationKeyMap;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
		{
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = lInflater.inflate(R.layout.service_fault_analytics,null);
		}
		
		//ShotInfo lMessageObj = mShotList.get(position);
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customServiceAnalyticsLayout);
		TextView serviceHand = (TextView) convertView.findViewById(R.id.serviceHand);
		TextView serviceDestination = (TextView) convertView.findViewById(R.id.serviceDestination);
		TextView serviceWin = (TextView) convertView.findViewById(R.id.serviceCount);
		final TextView viewShotHandName = (TextView) convertView.findViewById(R.id.viewShotHandName);
		final TextView viewShotDestinationName = (TextView) convertView.findViewById(R.id.viewShotDestinationName);
		final LinearLayout displayInfo = (LinearLayout) convertView.findViewById(R.id.displayInfo);
		final TableLayout thirdBallDataTable = (TableLayout) convertView.findViewById(R.id.faultDataTable);
		final TableLayout strokeHeaderTable = (TableLayout) convertView.findViewById(R.id.faultHeaderTable);
		final ScrollView scrollView2 = (ScrollView) convertView.findViewById(R.id.faultScrollView);

		if(position%2 == 0)				
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		else	
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
		
		final JSONObject json_data = getItem(position);
		
	    if(null!=json_data )
	    {        	
	        if(json_data.containsKey("serviceHand")) 
	        {
	        	serviceHand.setText(json_data.get("serviceHand").toString());
				String serviceName = mServiceKeyMap.get(json_data.get("serviceHand").toString());
				if(serviceName != null)
					viewShotHandName.setText(serviceName);
				else
					viewShotHandName.setText("");
	        }
	        
	        if(json_data.containsKey("serviceDestination"))
	        {
	        	serviceDestination.setText(json_data.get("serviceDestination").toString());
				String destinationName = mDestinationKeyMap.get(json_data.get("serviceDestination").toString());
				if(destinationName != null)
					viewShotDestinationName.setText(destinationName);
				else
					viewShotDestinationName.setText("");
	        }
	        if(json_data.containsKey("totalPlayed")) 
				serviceWin.setText(json_data.get("totalPlayed").toString());
	    }
	        	
	        	

	    customShotLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if (viewShotHandName.getVisibility() == View.VISIBLE)
				{
					displayInfo.setVisibility(View.GONE);
					viewShotHandName.setVisibility(View.GONE);
					viewShotDestinationName.setVisibility(View.GONE);
					strokeHeaderTable.setVisibility(View.GONE);
					thirdBallDataTable.setVisibility(View.GONE);
				}
				else
				{
					if(json_data.containsKey("dataSet"))	
		        	{
						thirdBallDataTable.removeAllViews();

		        		JSONArray jsonArr = (JSONArray) json_data.get("dataSet");

						for (int i = 0; i < jsonArr.size(); i++) 
						{		
				     		 JSONObject jsonObj = (JSONObject) jsonArr.get(i);
						     View rowView = mActivity.getLayoutInflater().inflate(R.layout.servicefaulttable, null);
						     TextView setValue = (TextView) rowView.findViewById(R.id.setValue);
						     TextView pointValue = (TextView) rowView.findViewById(R.id.pointValue);
						     TextView faultCount = (TextView) rowView.findViewById(R.id.faultCount);

						     if(jsonObj.containsKey("p1Set") && jsonObj.containsKey("p2Set"))
						     {
						    	 setValue.setText(jsonObj.get("p1Set").toString()+"-"+jsonObj.get("p2Set").toString());
						     }	
						     if(jsonObj.containsKey("p1Points") && jsonObj.containsKey("p2Points"))
						     {
						    	 pointValue.setText(jsonObj.get("p1Points").toString()+"-"+jsonObj.get("p2Points").toString());
						     }	
						     if(jsonObj.containsKey("count"))
						    	 faultCount.setText(jsonObj.get("count").toString());
						    						 					     				             
				             thirdBallDataTable.addView(rowView);
	
						}
		        	}
					displayInfo.setVisibility(View.VISIBLE);
					viewShotHandName.setVisibility(View.VISIBLE);
					viewShotDestinationName.setVisibility(View.VISIBLE);
					scrollView2.setVisibility(View.VISIBLE);
					strokeHeaderTable.setVisibility(View.VISIBLE);
					thirdBallDataTable.setVisibility(View.VISIBLE);
				}
			}
		});
		
		
	
		return convertView;
	}

}
