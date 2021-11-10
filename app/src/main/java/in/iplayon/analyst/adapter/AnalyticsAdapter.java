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
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import in.iplayon.analyst.R;
import in.iplayon.analyst.modal.ShotInfo;


public class AnalyticsAdapter extends BaseAdapter {

		private Context mContext;
		private Activity mActivity;
		private List<ShotInfo> mShotList;
		private Map<String,String> mDestinationKeyMap;
		private Map<String,String> mStrokeKeyMap;

		public AnalyticsAdapter(Context lContext, Activity lActivity, List<ShotInfo> lShotList, Map<String, String> lServiceKeyMap, Map<String, String> lDestinationKeyMap, Map<String, String> lStrokeKeyMap) {
			this.mContext = lContext;
			this.mActivity = lActivity;
			this.mShotList = lShotList;	
			this.mDestinationKeyMap = lDestinationKeyMap;
			this.mStrokeKeyMap = lStrokeKeyMap;
			
		}
		@Override
		public int getCount() {
			return mShotList.size();
		}

		@Override
		public Object getItem(int position) {
			return mShotList.get(position);

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
				convertView = lInflater.inflate(R.layout.shotinfo_view,null);
			}
			
			ShotInfo lMessageObj = mShotList.get(position);

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
			try
			{
				float shot1 = Float.parseFloat(lMessageObj.getShotPlayed().toString());
				float win1 = Float.parseFloat(lMessageObj.getShotWin().toString());
				float loss1 = Float.parseFloat(lMessageObj.getShotLoss().toString());

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
			

			String shotsPlayed = "<b>"+lMessageObj.getShotPlayed()+"</b> times";
			String shotsWin = "<b>"+lMessageObj.getShotWin()+"</b> times,<b>"+ winPercentage+"</b>%";
			String shotsLoss = "<b>"+lMessageObj.getShotLoss()+"</b> times,<b>"+lossPercentage+"</b>%";
			
			playedCount.setText(lMessageObj.getShotPlayed());
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
			
				
			
			
			if(lMessageObj.getCombinedShotKey().toString().contains("-"))
			{
				String strokeKey = lMessageObj.getCombinedShotKey().toString();
				int destinationIndex = strokeKey.lastIndexOf("-");
		        String serviceHand = strokeKey.substring(0, destinationIndex);
		   		String serviceDestination = strokeKey.substring(destinationIndex+1, strokeKey.length());
		   		shotHand.setText(serviceHand);
				shotDestination.setText(serviceDestination);
		   		
			}
			
			String strokeName = mStrokeKeyMap.get(lMessageObj.getShotHand());
			String destinationName = mDestinationKeyMap.get(lMessageObj.getShotDestination());
			if(strokeName != null)
				shotHandInfoName.setText(strokeName);
			else
				shotHandInfoName.setText("");
			if(destinationName != null)
				shotDestinationInfoName.setText(destinationName);
			else
				shotDestinationInfoName.setText("");
			
			
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