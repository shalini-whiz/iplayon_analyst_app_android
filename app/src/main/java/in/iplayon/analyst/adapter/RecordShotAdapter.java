package in.iplayon.analyst.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.iplayon.analyst.R;
import in.iplayon.analyst.modal.Shot;


public class RecordShotAdapter extends BaseAdapter {

		private Context mContext;
		private Activity mActivity;
		private List<Shot> mShotList;
		
		OnDataChangeListener mOnDataChangeListener;

		public interface OnDataChangeListener {
			public void onDataChanged(int lSize);
		}
		public RecordShotAdapter(Context lContext, Activity lActivity, List<Shot> lShotList) {
			this.mContext = lContext;
			this.mActivity = lActivity;
			this.mShotList = lShotList;			
		}
		
		public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
			mOnDataChangeListener = onDataChangeListener;
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
				convertView = lInflater.inflate(R.layout.record_shot,null);
			}
			
			LinearLayout customShotLayout = (LinearLayout) convertView.findViewById(R.id.customRecordShotLayout);
			//if(position%2 == 0)		
				//customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighterevenblue));		
			//else
				//customShotLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.lighteroddblue));
			
			TextView shotHand = (TextView) convertView.findViewById(R.id.recordShotHand);
			TextView shotDestination = (TextView) convertView.findViewById(R.id.recordShotDestination);
			ImageView recordShotSettings = (ImageView) convertView.findViewById(R.id.recordShortSettings);
			final TextView shotHandName = (TextView) convertView.findViewById(R.id.viewShotHandName);
			final TextView shotDestinationName = (TextView) convertView.findViewById(R.id.viewShotDestinationName);
			
			final Shot lMessageObj = mShotList.get(position);
			
			
			
			if(lMessageObj.getStatus().equalsIgnoreCase("current"))
				recordShotSettings.setVisibility(View.VISIBLE);
			else
				recordShotSettings.setVisibility(View.INVISIBLE);
			if(lMessageObj.getPlayerName().equalsIgnoreCase(lMessageObj.getPlayer1Name()))
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
			
			if(lMessageObj.getStrokeHandName() != null)			
				shotHandName.setText(lMessageObj.getStrokeHandName());	
			if(lMessageObj.getStrokeDestinationName() != null)
				shotDestinationName.setText(lMessageObj.getStrokeDestinationName());
			
			
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
		
			recordShotSettings.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(final View view) {

					AlertDialog.Builder shotDialog = new AlertDialog.Builder(mActivity,R.style.AlertDialogTheme);

					LayoutInflater inflater = mActivity.getLayoutInflater();
					View deleteSeqLayout = inflater.inflate(R.layout.confirm_dialog, null);
					TextView dialogTitle = (TextView) deleteSeqLayout.findViewById(R.id.dialogTitle);
					TextView dialogMessage = (TextView) deleteSeqLayout.findViewById(R.id.dialogMessage);
					dialogTitle.setText("Confirm");
					dialogMessage.setText("Delete Shot");
					shotDialog.setView(deleteSeqLayout);

					shotDialog.setCancelable(false);
					shotDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							 dialog.cancel();
						}
					});

					shotDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mShotList.remove(position);
							if (mOnDataChangeListener != null) {
								mOnDataChangeListener.onDataChanged(mShotList.size());
							}
							if(mShotList.size() >= 1)
							{
								if(mShotList.get(position-1) != null)
									mShotList.get(position-1).setStatus("current");;
									
							}
							
							notifyDataSetChanged();
						}
					});
					shotDialog.show();	
				}
			});

			return convertView;
		}
		
		
	}