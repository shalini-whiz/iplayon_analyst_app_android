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

import java.util.List;
import java.util.Map;

import in.iplayon.analyst.R;
import in.iplayon.analyst.modal.SequenceDetail;
import in.iplayon.analyst.modal.StrokesPlayed;


public class ViewStrokeShotAdapter extends BaseAdapter {

	private Context mContext;
	private List<StrokesPlayed> mShotList;
	private Map<String,String> mServiceMap;
	private Map<String,String> mStrokeMap;
	private Map<String,String> mDestinationMap;
	private SequenceDetail mSequenceDetail;

	public ViewStrokeShotAdapter(Context lContext, List<StrokesPlayed> lMessageList, SequenceDetail sequenceDetail, Map<String, String> serviceMap, Map<String, String> strokeMap, Map<String, String> destinationMap) {
		this.mContext = lContext;
		this.mShotList = lMessageList;
		this.mServiceMap = serviceMap;
		this.mStrokeMap = strokeMap;
		this.mDestinationMap = destinationMap;
		this.mSequenceDetail = sequenceDetail;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
		{
			LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = lInflater.inflate(R.layout.view_shot,null);
		}
		
		TextView shotHand = (TextView) convertView.findViewById(R.id.viewShotHand);
		TextView shotDestination = (TextView) convertView.findViewById(R.id.viewShotDestination);
		LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customShotLayout);
		//if(position%2 == 0)
			//customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		//else
			//customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));

		final TextView shotHandName = (TextView) convertView.findViewById(R.id.viewShotHandName);
		final TextView shotDestinationName = (TextView) convertView.findViewById(R.id.viewShotDestinationName);

		StrokesPlayed lMessageObj = mShotList.get(position);

		if(mSequenceDetail.getPlayer1Id().equalsIgnoreCase(lMessageObj.getStrokePlayed()))
		{
			shotHand.setTextColor(ContextCompat.getColor(mContext, R.color.playerColor));
			shotDestination.setTextColor(ContextCompat.getColor(mContext, R.color.playerColor));
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));
		}

		else
		{
			shotHand.setTextColor(ContextCompat.getColor(mContext, R.color.opponentColor));
			shotDestination.setTextColor(ContextCompat.getColor(mContext, R.color.opponentColor));
			customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));

		}
		shotHand.setText(lMessageObj.getStrokeHand());
		shotDestination.setText(lMessageObj.getStrokeDestination());
		
		if(position == 0)
			shotHandName.setText(mServiceMap.get(lMessageObj.getStrokeHand()));
		else
			shotHandName.setText(mStrokeMap.get(lMessageObj.getStrokeHand()));
		
		
		shotDestinationName.setText(mDestinationMap.get(lMessageObj.getStrokeDestination()));

		shotHand.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if (shotHandName.getVisibility() == View.VISIBLE)
				{
					shotHandName.setVisibility(View.GONE);
					shotDestinationName.setVisibility(View.GONE);
					
				}
				else
				{
					shotHandName.setVisibility(View.VISIBLE);
					shotDestinationName.setVisibility(View.VISIBLE);
					shotHandName.setFocusable(true);
					shotDestinationName.setFocusable(true);

				}
			}
		});
		
		shotDestination.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(shotDestinationName.getVisibility() == View.VISIBLE)
				{
					shotHandName.setVisibility(View.GONE);
					shotDestinationName.setVisibility(View.GONE);
				}
				else
				{
					shotHandName.setVisibility(View.VISIBLE);
					shotDestinationName.setVisibility(View.VISIBLE);
				}
			}
		});
		
		return convertView;
	}
}