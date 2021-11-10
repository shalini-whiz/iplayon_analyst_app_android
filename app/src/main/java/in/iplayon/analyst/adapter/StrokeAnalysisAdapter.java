package in.iplayon.analyst.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

import in.iplayon.analyst.R;


public class StrokeAnalysisAdapter extends BaseAdapter implements ListAdapter {
	
	private final Activity mActivity;
    private final JSONArray mJsonArray;
    private final Context mContext;
	private Map<String,String> mStrokeKeyMap;
	private Map<String,String> mServiceKeyMap;
	private Map<String,String> mDestinationKeyMap;

    
 
    public StrokeAnalysisAdapter(Context lContext, Activity lActivity, JSONArray jsonArray, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap, Map<String, String> lStrokeKeyMap) {

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
			convertView = lInflater.inflate(R.layout.stroke_analytics,null);
		}
		
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customStrokeAnalysisLayout);
		final LinearLayout displayInfo = (LinearLayout) convertView.findViewById(R.id.displayInfo);
		final TableLayout strokeDataTable = (TableLayout) convertView.findViewById(R.id.strokeDataTable);
		final TableLayout strokeHeaderTable = (TableLayout) convertView.findViewById(R.id.strokeHeaderTable);
		final ScrollView scrollView2 = (ScrollView) convertView.findViewById(R.id.scrollView2);
		
		
		
		TextView shotHand = (TextView) convertView.findViewById(R.id.shotHand);
		TextView shotDestination = (TextView) convertView.findViewById(R.id.shotDestination);
		TextView totalPlayed = (TextView) convertView.findViewById(R.id.totalPlayed);
		TextView shotHandInfoName = (TextView) convertView.findViewById(R.id.shotHandInfoName);
		TextView shotDestinationInfoName = (TextView) convertView.findViewById(R.id.shotDestinationInfoName);
		
		
		if(position%2 == 0)		
		{
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
			//displayInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
			//strokeHeaderTable.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		}
		else
		{
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
			//displayInfo.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
			//strokeHeaderTable.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
		}
						
        final JSONObject json_data = getItem(position);
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
        		totalPlayed.setText(json_data.get("totalPlayed").toString()); 	
        }
        
        customShotLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if (displayInfo.getVisibility() == View.VISIBLE)
				{
					displayInfo.setVisibility(View.GONE);
					scrollView2.setVisibility(View.GONE);
					strokeHeaderTable.setVisibility(View.GONE);
					strokeDataTable.setVisibility(View.GONE);
					
				}
				else
				{
					if(json_data.containsKey("dataSet"))	
		        	{
						strokeDataTable.removeAllViews();
		        		JSONArray dataSet = (JSONArray) json_data.get("dataSet");
		        		if(dataSet.size() > 0)
						{
							for(int i=0; i< dataSet.size();i++)
							{
								JSONObject dataObj = (JSONObject) dataSet.get(i);
										
						     		 int k=i+1;
						     		 TableRow tbrow = new TableRow(mActivity);
								     tbrow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
								     tbrow.setGravity(Gravity.CENTER);
						             float estimatedWeightF = 3;
								     tbrow.setWeightSum(estimatedWeightF);
								     LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					                          LayoutParams.MATCH_PARENT,
					                          LayoutParams.WRAP_CONTENT,3f);
								     tbrow.setLayoutParams(param);
								     
						 			 TableRow.LayoutParams lp;
						 			 String p1SetVal = "";
						 			 String p2SetVal = "";
						 			 String p1PointsVal = "";
						 			 String p2PointsVal = "";
						 			 String totalShotsVal = "";
						 			 String shotsVal = "";
						 			 
								     TextView t1v = new TextView(mActivity);
						             t1v.setTextColor(ContextCompat.getColor(mContext, R.color.playerColor));
						             t1v.setGravity(Gravity.CENTER);
								     lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
								     t1v.setLayoutParams(lp);
								     if(dataObj.containsKey("p1Set"))
											p1SetVal = dataObj.get("p1Set").toString();
								     if(dataObj.containsKey("p2Set"))
											p2SetVal = dataObj.get("p2Set").toString();
								     t1v.setText(p1SetVal+" - "+p2SetVal);
						             tbrow.addView(t1v);
						                
						             TextView t2v = new TextView(mActivity);
							         t2v.setTextColor(ContextCompat.getColor(mContext, R.color.playerColor));
						             t2v.setGravity(Gravity.CENTER);
						             lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
								     t2v.setLayoutParams(lp);
								     
								     if(dataObj.containsKey("p1Points"))
								    	 p1PointsVal = dataObj.get("p1Points").toString();
									 if(dataObj.containsKey("p2Points"))
										 p2PointsVal = dataObj.get("p2Points").toString();
								     
						             t2v.setText(p1PointsVal+" - "+p2PointsVal);
						             tbrow.addView(t2v);
						                
						                
						             TextView t3v = new TextView(mActivity);
						             t3v.setTextColor(ContextCompat.getColor(mContext, R.color.playerColor));
						             t3v.setGravity(Gravity.CENTER);
						             lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
								     t3v.setLayoutParams(lp);
								     if(dataObj.containsKey("played"))
											shotsVal = dataObj.get("played").toString();
								     if(json_data.containsKey("totalPlayed"))	
							        		totalShotsVal = json_data.get("totalPlayed").toString(); 	
								     
						             t3v.setText(shotsVal+"/"+totalShotsVal);
						             tbrow.addView(t3v);
						             
						             strokeDataTable.addView(tbrow);


								
							
															
							}
						}
						
		        	}
					displayInfo.setVisibility(View.VISIBLE);
					scrollView2.setVisibility(View.VISIBLE);
					strokeHeaderTable.setVisibility(View.VISIBLE);
					strokeDataTable.setVisibility(View.VISIBLE);
					
	        		
				}
			}
		});
         return convertView;
    }

}
