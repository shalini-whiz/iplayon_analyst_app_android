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


public class ThirdBallAttackAdapter extends BaseAdapter implements ListAdapter {

	private final Activity mActivity;
    private final JSONArray mJsonArray;
    private final Context mContext;
	private Map<String,String> mStrokeKeyMap;
	private Map<String,String> mServiceKeyMap;
	private Map<String,String> mDestinationKeyMap;

    
 
    public ThirdBallAttackAdapter(Context lContext, Activity lActivity, JSONArray jsonArray, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap, Map<String, String> lStrokeKeyMap) {

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
    public View getView(int position, View convertView, ViewGroup parent) {
       

		if(convertView == null)
		{
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = lInflater.inflate(R.layout.thirdball_analysis,null);
		}
		
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customThirdBallInfoLayout);
		final LinearLayout displayInfo = (LinearLayout) convertView.findViewById(R.id.displayInfo);
		final TableLayout thirdBallDataTable = (TableLayout) convertView.findViewById(R.id.thirdBallDataTable);
		final TableLayout strokeHeaderTable = (TableLayout) convertView.findViewById(R.id.strokeHeaderTable);
		final ScrollView scrollView2 = (ScrollView) convertView.findViewById(R.id.scrollView2);
		if(position%2 == 0)		
		{
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
			//displayInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		}
		else
		{
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
			//displayInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
		}
		
		TextView shotHand = (TextView) convertView.findViewById(R.id.shotHand);
		TextView shotDestination = (TextView) convertView.findViewById(R.id.shotDestination);
		TextView totalPlayed = (TextView) convertView.findViewById(R.id.totalPlayed);
		TextView shotHandInfoName = (TextView) convertView.findViewById(R.id.shotHandInfoName);
		TextView shotDestinationInfoName = (TextView) convertView.findViewById(R.id.shotDestinationInfoName);
		
        final JSONObject json_data = getItem(position);
        String strokeName = "";
        String destinationName = "";
        if(null!=json_data )
        {
        	if(json_data.containsKey("serviceHand"))
        	{
        		shotHand.setText(json_data.get("serviceHand").toString()); 
            	strokeName = mServiceKeyMap.get(json_data.get("serviceHand").toString());
        		shotHandInfoName.setText(strokeName);
        		

        	}
        	if(json_data.containsKey("serviceDestination"))	
        	{
        		shotDestination.setText(json_data.get("serviceDestination").toString()); 			        	
        		destinationName = mDestinationKeyMap.get(json_data.get("serviceDestination").toString());    		
        		shotDestinationInfoName.setText(destinationName);
        		
        	}
        	if(json_data.containsKey("totalPlayed") && json_data.containsKey("totalWinCount"))	
        		totalPlayed.setText(json_data.get("totalWinCount").toString()+"/"+json_data.get("totalPlayed").toString()); 
        	
        }
        
        customShotLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if (displayInfo.getVisibility() == View.VISIBLE)
				{
					displayInfo.setVisibility(View.GONE);
					scrollView2.setVisibility(View.GONE);
					strokeHeaderTable.setVisibility(View.GONE);
					thirdBallDataTable.setVisibility(View.GONE);
				}
				else
				{
					thirdBallDataTable.removeAllViews();
					if(json_data.containsKey("dataSet"))	
		        	{
		        		JSONArray jsonArr = (JSONArray) json_data.get("dataSet");
		        	
						for (int i = 0; i < jsonArr.size(); i++) 
						{		
				     		 JSONObject jsonObj = (JSONObject) jsonArr.get(i);
						     View rowView = mActivity.getLayoutInflater().inflate(R.layout.thirdballtable, null);
						     TextView recvShot = (TextView) rowView.findViewById(R.id.recvShot);
						     TextView recvShotDestination = (TextView) rowView.findViewById(R.id.recvShotDestination);
						     TextView thirdBall = (TextView) rowView.findViewById(R.id.thirdBall);
						     TextView thirdBallCount = (TextView) rowView.findViewById(R.id.thirdBallCount);
						   						    
						     if(jsonObj.containsKey("recvShot"))
						     {
						    	 if(mStrokeKeyMap.get(jsonObj.get("recvShot").toString()) != null)
						    		 recvShot.setText(mStrokeKeyMap.get(jsonObj.get("recvShot").toString()));
						     }
						     if(jsonObj.containsKey("recvDestination"))
						     {
						    	 if(mDestinationKeyMap.get(jsonObj.get("recvDestination").toString()) != null)
						    		 recvShotDestination.setText(mDestinationKeyMap.get(jsonObj.get("recvDestination").toString()));
						     }
						     if(jsonObj.containsKey("strokeHand"))
						     {
						    	 if(mStrokeKeyMap.get(jsonObj.get("strokeHand").toString()) != null)
						    		 thirdBall.setText(mStrokeKeyMap.get(jsonObj.get("strokeHand").toString()));
						     }	
						     if(jsonObj.containsKey("count") && jsonObj.containsKey("winCount"))
						    	 thirdBallCount.setText(jsonObj.get("winCount").toString()+"/"+jsonObj.get("count").toString());
						    						 					     				             
				             thirdBallDataTable.addView(rowView);
	
						}
		        	}
					displayInfo.setVisibility(View.VISIBLE);
					scrollView2.setVisibility(View.VISIBLE);
					strokeHeaderTable.setVisibility(View.VISIBLE);
					thirdBallDataTable.setVisibility(View.VISIBLE);
					
	        		
				}
			}
		});
         return convertView;
    }

}
