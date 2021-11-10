package in.iplayon.analyst.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

import in.iplayon.analyst.R;


public class AnalyticsAdapter1 extends BaseAdapter implements ListAdapter {

	private final Activity mActivity;
    private final JSONArray mJsonArray;
    private final Context mContext;
	private Map<String,String> mStrokeKeyMap;
	private Map<String,String> mServiceKeyMap;
	private Map<String,String> mDestinationKeyMap;

    
 
    public AnalyticsAdapter1 (Context lContext, Activity lActivity, JSONArray jsonArray, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap, Map<String, String> lStrokeKeyMap) {
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
			convertView = lInflater.inflate(R.layout.shotinfo_view,null);
		}
		TextView shotHand = (TextView) convertView.findViewById(R.id.shotHandInfo);
		TextView shotDestination = (TextView) convertView.findViewById(R.id.shotDestinationInfo);
		final ImageView shotInfoClick = (ImageView) convertView.findViewById(R.id.shotInfoClick);
		TextView shotPlayed = (TextView) convertView.findViewById(R.id.shotPlayed);
		TextView shotWin = (TextView) convertView.findViewById(R.id.shotWin);
		TextView shotLoss = (TextView) convertView.findViewById(R.id.shotLoss);
		TextView playedCount = (TextView) convertView.findViewById(R.id.playedCount);

		TextView shotHandInfoName = (TextView) convertView.findViewById(R.id.shotHandInfoName);
		TextView shotDestinationInfoName = (TextView) convertView.findViewById(R.id.shotDestinationInfoName);
	
		
		float winPer = 0;
		float lossPer = 0;
        final JSONObject json_data = getItem(position);
        
        float shot1 = 0;
		float win1 = 0;
		float loss1 = 0;
		

		try
		{
		
			
			if(json_data.containsKey("strokesPlayed"))
				shot1 = Float.parseFloat(json_data.get("strokesPlayed").toString());
			
			if(json_data.containsKey("win"))
				win1 = Float.parseFloat(json_data.get("win").toString());
			
			if(json_data.containsKey("loss"))
				loss1 = Float.parseFloat(json_data.get("loss").toString());
			
			if(win1 != 0 || shot1 != 0)
				winPer = (win1/shot1) * 100;
			if(loss1 != 0 || shot1 != 0)
				lossPer = (loss1/shot1) * 100;
			
		}catch(Exception e)
		{

		}
		int winPercentage = 0;
		int lossPercentage = 0;
		
		if(winPer != 0.0)
		{
			 winPercentage = Math.round(winPer);
		}
		if(lossPer != 0.0)
		{
			 lossPercentage = Math.round(lossPer);
		}
		

		String shotsPlayed = "<b>"+json_data.get("strokesPlayed").toString()+"</b> times";
		String shotsWin = "<b>"+json_data.get("win").toString()+"</b> times,<b>"+ winPercentage+"</b>%";
		String shotsLoss = "<b>"+json_data.get("loss").toString()+"</b> times,<b>"+lossPercentage+"</b>%";
		
		playedCount.setText(String.valueOf(json_data.get("strokesPlayed").toString()));
		shotPlayed.setText(Html.fromHtml(shotsPlayed));
		shotWin.setText(Html.fromHtml(shotsWin));
		shotLoss.setText(Html.fromHtml(shotsLoss));
		

		
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customShotInfoLayout);
		final LinearLayout shotInfoLayout = (LinearLayout) convertView.findViewById(R.id.shotInfoLayout);
		final LinearLayout shotWinLayout = (LinearLayout) convertView.findViewById(R.id.shotWinLayout);
		final LinearLayout shotLossLayout = (LinearLayout) convertView.findViewById(R.id.shotLossLayout);
		final LinearLayout displayInfo = (LinearLayout) convertView.findViewById(R.id.displayInfo);


		if(position%2 == 0)		
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		else
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
		
			
		if(json_data.containsKey("strokeKey"))
		{

			String strokeKey = json_data.get("strokeKey").toString();
			if(strokeKey.contains("-"))
			{
				int destinationIndex = strokeKey.lastIndexOf("-");
		        String serviceHand = strokeKey.substring(0, destinationIndex);
		   		String serviceDestination = strokeKey.substring(destinationIndex+1, strokeKey.length());
		   		shotHand.setText(serviceHand);
				shotDestination.setText(serviceDestination);
		   		
			}
	   		int destinationIndex = strokeKey.lastIndexOf("-");
	   		String shortHand = strokeKey.substring(0, destinationIndex);
	   		String shortDestination = strokeKey.substring(destinationIndex+1, strokeKey.length());
	   		String strokeName = mStrokeKeyMap.get(shortHand);
			String destinationName = mDestinationKeyMap.get(shortDestination);
	   		
			if(strokeName != null)
				shotHandInfoName.setText(strokeName);
			else
				shotHandInfoName.setText("");
			if(destinationName != null)
				shotDestinationInfoName.setText(destinationName);
			else
				shotDestinationInfoName.setText("");
			
	   		
		}
		
		
		
		shotInfoClick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
					if (shotInfoLayout.getVisibility() == View.VISIBLE)
					{
						displayInfo.setVisibility(View.GONE);
						shotInfoLayout.setVisibility(View.GONE);
						shotWinLayout.setVisibility(View.GONE);
						shotLossLayout.setVisibility(View.GONE);
						shotInfoClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.plus_20));
					}
					else
					{
						displayInfo.setVisibility(View.VISIBLE);
						shotInfoLayout.setVisibility(View.VISIBLE);
						shotWinLayout.setVisibility(View.VISIBLE);
						shotLossLayout.setVisibility(View.VISIBLE);
						shotInfoClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.minus_20));


					}
				
			}
		});
		customShotLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (shotInfoLayout.getVisibility() == View.VISIBLE)
				{
					displayInfo.setVisibility(View.GONE);

					shotInfoLayout.setVisibility(View.GONE);
					shotWinLayout.setVisibility(View.GONE);
					shotLossLayout.setVisibility(View.GONE);
					shotInfoClick.setImageResource(R.drawable.plus_20);
				}
				else
				{
					displayInfo.setVisibility(View.VISIBLE);

					shotInfoLayout.setVisibility(View.VISIBLE);
					shotWinLayout.setVisibility(View.VISIBLE);
					shotLossLayout.setVisibility(View.VISIBLE);
					shotInfoClick.setImageResource(R.drawable.minus_20);
				}
			}
		});
		return convertView;
		
		
		
        
        
    }
}
