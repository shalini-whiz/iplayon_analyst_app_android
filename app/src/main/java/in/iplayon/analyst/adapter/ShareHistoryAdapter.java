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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.iplayon.analyst.R;
import in.iplayon.analyst.modal.ShareHistoryPlayerJson;
import in.iplayon.analyst.modal.SharedHistoryJson;


public class ShareHistoryAdapter extends BaseAdapter implements ListAdapter {

	private final Activity mActivity;
    private final JSONArray mJsonArray;
    private final Context mContext;
    private final ArrayList<ShareHistoryPlayerJson> mRegisteredPlayers;
    private ArrayList<SharedHistoryJson> mSharedHistoryList;

    public ShareHistoryAdapter (Context context, Activity activity, JSONArray jsonArray, List<ShareHistoryPlayerJson> registeredPlayers, ArrayList<SharedHistoryJson> lSharedHistoryList) {
    	//assert this.mActivity != null;
        //assert jsonArray != null;
        this.mContext = context;
        this.mJsonArray = jsonArray;
        this.mActivity = activity;
        this.mRegisteredPlayers = (ArrayList<ShareHistoryPlayerJson>) registeredPlayers;
		this.mSharedHistoryList = lSharedHistoryList;
    }

    @Override
    public int getCount() {
    	
        /*if(null==mJsonArray)
         return 0;
        else
        	return mJsonArray.size();*/



		if(null==mSharedHistoryList)
			return 0;
		else
			return mSharedHistoryList.size();


    }

    @Override
    public SharedHistoryJson getItem(int position) {
    	/*
         if(null==mJsonArray) return null;
         else
           return (JSONObject) mJsonArray.get(position);*/


		if(null==mSharedHistoryList) return null;
		else
			return (SharedHistoryJson) mSharedHistoryList.get(position);


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
			convertView = lInflater.inflate(R.layout.sharehistory_view,null);
		}
		
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.shareHistoryHeaderLayout);
		TextView sharedTo = (TextView) convertView.findViewById(R.id.sharedTo);
		TextView player1Name = (TextView) convertView.findViewById(R.id.player1Name);
		TextView player2Name = (TextView) convertView.findViewById(R.id.player2Name);
		TextView sharedCount = (TextView) convertView.findViewById(R.id.sharedCount);
		TextView sharedDate = (TextView) convertView.findViewById(R.id.sharedDate);

		if(position%2 == 0)		
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		else
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));




        /*JSONObject json_data = getItem(position);
	
        if(null!=json_data )
        {
        	if(json_data.containsKey("sharedId"))
        	{
        		sharedTo.setText(json_data.get("sharedId").toString()+"");
            	int pos = mRegisteredPlayers.indexOf(new ShareHistoryPlayerJson(json_data.get("sharedId").toString()));
            	if(pos > -1)
            		sharedTo.setText(mRegisteredPlayers.get(pos).getPlayerName());
            	else
            		sharedTo.setText("");

        	}

        	if(json_data.containsKey("player1Name"))
    			player1Name.setText(json_data.get("player1Name").toString());

            if(json_data.containsKey("player2Name"))
    			player2Name.setText(json_data.get("player2Name").toString()+"");

            if(json_data.containsKey("sequenceCount"))
    			sharedCount.setText(json_data.get("sequenceCount").toString()+"");

        	if(json_data.containsKey("sequenceSharedDate"))
        	{
        		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH);

				try {
					Date result1 = formatter.parse(json_data.get("sequenceSharedDate").toString());
					sharedDate.setText(new SimpleDateFormat("ddMMMyy").format(result1));

				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
        	}
        }*/



		SharedHistoryJson json_data = getItem(position);

		if(null!=json_data )
		{
			if(json_data.getSharedId() != null)
			{
				if(json_data.getSharedUser() != null)
					sharedTo.setText(json_data.getSharedUser());
				int pos = mRegisteredPlayers.indexOf(new ShareHistoryPlayerJson(json_data.getSharedId()));
				if(pos > -1)
					sharedTo.setText(mRegisteredPlayers.get(pos).getPlayerName());
				else
					sharedTo.setText("");

			}

				player1Name.setText(json_data.getPlayer1Name());
				player2Name.setText(json_data.getPlayer2Name()+"");
				sharedCount.setText(json_data.getSequenceCount()+"");

			if(json_data.getSequenceSharedDate() != null)
			{
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

				try {
					Date result1 = formatter.parse(json_data.getSequenceSharedDate());
					sharedDate.setText(new SimpleDateFormat("ddMMMyy").format(result1));

				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
		}
        
        
         return convertView;
    }

}