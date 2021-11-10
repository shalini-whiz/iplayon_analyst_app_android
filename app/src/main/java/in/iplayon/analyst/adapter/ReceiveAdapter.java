package in.iplayon.analyst.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

import in.iplayon.analyst.R;


public class ReceiveAdapter extends BaseAdapter {

		private Context mContext;
		private Activity mActivity;
		private Map<String,String> mServiceKeyMap;
		private Map<String,String> mDestinationKeyMap;
		private Map<String,String> mStrokeKeyMap;
	    private JSONArray mJsonArray;

		public ReceiveAdapter(Context lContext, Activity lActivity, JSONArray jsonArray, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap, Map<String, String> lStrokeKeyMap) {
			this.mContext = lContext;
			this.mActivity = lActivity;
			this.mJsonArray = jsonArray;
			this.mServiceKeyMap = lServiceKeyMap;
			this.mDestinationKeyMap = lDestinationKeyMap;
			this.mStrokeKeyMap = lStrokeKeyMap;
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
				convertView = lInflater.inflate(R.layout.receive_analytics,null);
			}
			
			LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customReceiveAnalyticsLayout);
			TextView serviceHand = (TextView) convertView.findViewById(R.id.serviceHand);
			TextView serviceDestination = (TextView) convertView.findViewById(R.id.serviceDestination);
			TextView winShot = (TextView) convertView.findViewById(R.id.winShot);
			TextView serviceWin = (TextView) convertView.findViewById(R.id.serviceCount);
			final TextView viewShotHandName = (TextView) convertView.findViewById(R.id.viewShotHandName);
			final TextView viewShotDestinationName = (TextView) convertView.findViewById(R.id.viewShotDestinationName);
			final TextView viewWinShot = (TextView) convertView.findViewById(R.id.viewWinShot);
			final LinearLayout displayInfo = (LinearLayout) convertView.findViewById(R.id.displayInfo);
			
			if(position%2 == 0)				
				customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
			else	
				customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
			
			String winStroke = null;
			String winDestination = null;
			
			
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
		        if(json_data.containsKey("strokeHand"))
		        {
					winStroke = mStrokeKeyMap.get(json_data.get("strokeHand").toString());
		        }
		        if(json_data.containsKey("strokeDestination"))
		        {
		        	winDestination = mDestinationKeyMap.get(json_data.get("strokeDestination").toString());
      	
		        }
		        if(json_data.containsKey("win"))
		        {
		        	serviceWin.setText(json_data.get("win").toString());
		        }
		        if(json_data.containsKey("strokeHand") && json_data.containsKey("strokeDestination"))
		        {
					winShot.setText(json_data.get("strokeHand").toString()+" : "+json_data.get("strokeDestination").toString());
		        }
		        
		    }
			
			
			if(winStroke != null && winDestination != null)
				viewWinShot.setText(winStroke+" : "+winDestination);
			else
				viewWinShot.setText("");
			
			
			serviceHand.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					if (viewShotHandName.getVisibility() == View.VISIBLE)
					{
						displayInfo.setVisibility(View.GONE);
						viewShotHandName.setVisibility(View.GONE);
						viewShotDestinationName.setVisibility(View.GONE);
						viewWinShot.setVisibility(View.GONE);
					}
					else
					{
						displayInfo.setVisibility(View.VISIBLE);

						viewShotHandName.setVisibility(View.VISIBLE);
						viewShotDestinationName.setVisibility(View.VISIBLE);
						viewWinShot.setVisibility(View.VISIBLE);

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
						viewWinShot.setVisibility(View.GONE);

					}
					else
					{
						displayInfo.setVisibility(View.VISIBLE);
						viewShotHandName.setVisibility(View.VISIBLE);
						viewShotDestinationName.setVisibility(View.VISIBLE);
						viewWinShot.setVisibility(View.VISIBLE);

					}
				}
			});
			
			winShot.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					if (viewShotHandName.getVisibility() == View.VISIBLE)
					{
						displayInfo.setVisibility(View.GONE);
						viewShotHandName.setVisibility(View.GONE);
						viewShotDestinationName.setVisibility(View.GONE);
						viewWinShot.setVisibility(View.GONE);
					}
					else
					{
						displayInfo.setVisibility(View.VISIBLE);
						viewShotHandName.setVisibility(View.VISIBLE);
						viewShotDestinationName.setVisibility(View.VISIBLE);
						viewWinShot.setVisibility(View.VISIBLE);

					}
				}
			});
			
			
	       

			
		
			return convertView;
		}
		
		
	}