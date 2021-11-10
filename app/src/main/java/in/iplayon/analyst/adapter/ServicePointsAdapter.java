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

import java.util.Map;

import in.iplayon.analyst.R;


public class ServicePointsAdapter extends BaseAdapter implements ListAdapter {

		private Context mContext;
		private Activity mActivity;
		private Map<String,String> mServiceKeyMap;
		private Map<String,String> mDestinationKeyMap;
	    private JSONArray mJsonArray;

		public ServicePointsAdapter(Context lContext, Activity lActivity, JSONArray jsonArray, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap) {
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
				convertView = lInflater.inflate(R.layout.service_analytics,null);
			}
			
			//ShotInfo lMessageObj = mShotList.get(position);
			LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customServiceAnalyticsLayout);
			TextView serviceHand = (TextView) convertView.findViewById(R.id.serviceHand);
			TextView serviceDestination = (TextView) convertView.findViewById(R.id.serviceDestination);
			TextView serviceWin = (TextView) convertView.findViewById(R.id.serviceCount);
			final TextView viewShotHandName = (TextView) convertView.findViewById(R.id.viewShotHandName);
			final TextView viewShotDestinationName = (TextView) convertView.findViewById(R.id.viewShotDestinationName);
			final LinearLayout displayInfo = (LinearLayout) convertView.findViewById(R.id.displayInfo);
			
			
			if(position%2 == 0)				
				customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
			else	
				customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
			
			JSONObject json_data = getItem(position);
			
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
		        if(json_data.containsKey("win")) 
					serviceWin.setText(json_data.get("win").toString());
		    }
		        	
		        


			serviceHand.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					if (viewShotHandName.getVisibility() == View.VISIBLE)
					{
						displayInfo.setVisibility(View.GONE);
						viewShotHandName.setVisibility(View.GONE);
						viewShotDestinationName.setVisibility(View.GONE);
					}
					else
					{
						displayInfo.setVisibility(View.VISIBLE);

						viewShotHandName.setVisibility(View.VISIBLE);
						viewShotDestinationName.setVisibility(View.VISIBLE);
					}
				}
			});
			
			serviceDestination.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					if (viewShotHandName.getVisibility() == View.VISIBLE)
					{
						displayInfo.setVisibility(View.GONE);

						viewShotHandName.setVisibility(View.GONE);
						viewShotDestinationName.setVisibility(View.GONE);
					}
					else
					{
						displayInfo.setVisibility(View.VISIBLE);

						viewShotHandName.setVisibility(View.VISIBLE);
						viewShotDestinationName.setVisibility(View.VISIBLE);
					}
				}
			});
		
			return convertView;
		}
		
		
	}