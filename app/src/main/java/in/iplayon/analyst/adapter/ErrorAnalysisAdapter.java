package in.iplayon.analyst.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
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

public class ErrorAnalysisAdapter extends BaseAdapter implements ListAdapter {
	
	private final Activity mActivity;
    private final JSONArray mJsonArray;
    private final Context mContext;
	private Map<String,String> mStrokeKeyMap;
	private Map<String,String> mServiceKeyMap;
	private Map<String,String> mDestinationKeyMap;

    
 
    public ErrorAnalysisAdapter(Context lContext, Activity lActivity, JSONArray jsonArray, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap, Map<String, String> lStrokeKeyMap) {

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
	public int getViewTypeCount() {
		int count;
		if (mJsonArray.size() > 0) {
			count = getCount();
		} else {
			count = 1;
		}
		return count;

	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

		final JSONObject json_data = getItem(position);
		TextView netShotPlayed = null;
		TextView missShotPlayed = null;
		TextView outShotPlayed = null;
		TextView batEdgeShotPlayed = null;




		if(convertView == null)
		{
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = lInflater.inflate(R.layout.error_analytics,null);
			netShotPlayed = (TextView) convertView.findViewById(R.id.netShotPlayed);
			missShotPlayed = (TextView) convertView.findViewById(R.id.missShotPlayed);
			outShotPlayed = (TextView) convertView.findViewById(R.id.outShotPlayed);
			batEdgeShotPlayed = (TextView) convertView.findViewById(R.id.batEdgeShotPlayed);
			String calcText = "<b>0</b> times,<b>0</b>%";
			netShotPlayed.setText(Html.fromHtml(calcText));
			missShotPlayed.setText(Html.fromHtml(calcText));
			outShotPlayed.setText(Html.fromHtml(calcText));
			batEdgeShotPlayed.setText(Html.fromHtml(calcText));


		}
		else
		{
			netShotPlayed = (TextView) convertView.findViewById(R.id.netShotPlayed);
			missShotPlayed = (TextView) convertView.findViewById(R.id.missShotPlayed);
			outShotPlayed = (TextView) convertView.findViewById(R.id.outShotPlayed);
			batEdgeShotPlayed = (TextView) convertView.findViewById(R.id.batEdgeShotPlayed);
		}
		
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customErrorAnalysisLayout);
		final LinearLayout displayInfo = (LinearLayout) convertView.findViewById(R.id.displayInfo);
		final LinearLayout netInfoLayout = (LinearLayout) convertView.findViewById(R.id.netInfoLayout);
		final LinearLayout missInfoLayout = (LinearLayout) convertView.findViewById(R.id.missInfoLayout);
		final LinearLayout outInfoLayout = (LinearLayout) convertView.findViewById(R.id.outInfoLayout);
		final LinearLayout batEdgeInfoLayout = (LinearLayout) convertView.findViewById(R.id.batEdgeInfoLayout);
		
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
		TextView countTitle = (TextView) convertView.findViewById(R.id.countTitle);
		TextView shotHandInfoName = (TextView) convertView.findViewById(R.id.shotHandInfoName);
		TextView shotDestinationInfoName = (TextView) convertView.findViewById(R.id.shotDestinationInfoName);

		
		
        String strokeName = "";
        String destinationName = "";
        if(null!=json_data )
        {
        	if(json_data.containsKey("strokeHand"))
        	{
        		shotHand.setText(json_data.get("strokeHand").toString()); 
            	strokeName = mStrokeKeyMap.get(json_data.get("strokeHand").toString());
        		shotHandInfoName.setText(strokeName);
        		

        	}
        	if(json_data.containsKey("fromDestination"))	
        	{
        		shotDestination.setText(json_data.get("fromDestination").toString()); 			        	
        		destinationName = mDestinationKeyMap.get(json_data.get("fromDestination").toString());    		
        		shotDestinationInfoName.setText(destinationName);
        		
        	}
        	if(json_data.containsKey("totalPlayed"))	
        		countTitle.setText(json_data.get("totalPlayed").toString());


			if(json_data.containsKey("dataSet"))
			{
				JSONArray dataSet = (JSONArray) json_data.get("dataSet");
				if(dataSet.size() > 0)
				{
					for(int i=0; i< dataSet.size();i++)
					{
						JSONObject dataObj = (JSONObject) dataSet.get(i);
						if(dataObj.containsKey("strokeDestination"))
						{

							if(dataObj.containsKey("played"))
							{
								float calcperc = (Float.parseFloat(dataObj.get("played").toString())/ Float.parseFloat(json_data.get("totalPlayed").toString())) * 100;
								String.format("%.2f", calcperc);
								String calcText = "<b>"+dataObj.get("played").toString()+"</b> times,<b>"+ String.format("%.2f", calcperc)+"</b>%";

								if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("NET"))
									netShotPlayed.setText(Html.fromHtml(calcText));
								if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("MISSED"))
									missShotPlayed.setText(Html.fromHtml(calcText));
								if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("OUT"))
									outShotPlayed.setText(Html.fromHtml(calcText));
								if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("BE"))
									batEdgeShotPlayed.setText(Html.fromHtml(calcText));
							}
						}
					}
				}

			}

        }
        
        customShotLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if (displayInfo.getVisibility() == View.VISIBLE)
				{
					displayInfo.setVisibility(View.GONE);
					netInfoLayout.setVisibility(View.GONE);
					missInfoLayout.setVisibility(View.GONE);
					outInfoLayout.setVisibility(View.GONE);
					batEdgeInfoLayout.setVisibility(View.GONE);
				}
				else
				{
					/*if(json_data.containsKey("dataSet"))
		        	{
		        		JSONArray dataSet = (JSONArray) json_data.get("dataSet"); 			        	
		        		if(dataSet.size() > 0)
						{
							for(int i=0; i< dataSet.size();i++)
							{
								JSONObject dataObj = (JSONObject) dataSet.get(i);
								if(dataObj.containsKey("strokeDestination"))
								{
									
									if(dataObj.containsKey("played"))
									{
										float calcperc = (Float.parseFloat(dataObj.get("played").toString())/Float.parseFloat(json_data.get("totalPlayed").toString())) * 100;
										String.format("%.2f", calcperc);
										String calcText = "<b>"+dataObj.get("played").toString()+"</b> times,<b>"+ String.format("%.2f", calcperc)+"</b>%";

										if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("NET"))
											netShotPlayed.setText(Html.fromHtml(calcText));
										if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("MISSED"))
											missShotPlayed.setText(Html.fromHtml(calcText));
										if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("OUT"))
											outShotPlayed.setText(Html.fromHtml(calcText));
										if(dataObj.get("strokeDestination").toString().equalsIgnoreCase("BE"))
											batEdgeShotPlayed.setText(Html.fromHtml(calcText));					
									}						
								}							
							}
						}
						
		        	}*/
					displayInfo.setVisibility(View.VISIBLE);
					netInfoLayout.setVisibility(View.VISIBLE);
					missInfoLayout.setVisibility(View.VISIBLE);
					outInfoLayout.setVisibility(View.VISIBLE);
					batEdgeInfoLayout.setVisibility(View.VISIBLE);
					
	        		
				}
			}
		});
         return convertView;
    }

}
