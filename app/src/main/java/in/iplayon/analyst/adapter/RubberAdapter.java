package in.iplayon.analyst.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.iplayon.analyst.R;
import in.iplayon.analyst.modal.Rubber;


public class RubberAdapter extends BaseAdapter {

		private Context mContext;
		private Activity mActivity;
		ArrayList<Rubber> rubberArr;

		public RubberAdapter(Context lContext, Activity lActivity, ArrayList<Rubber> rubberArr) {
			this.mContext = lContext;
			this.mActivity = lActivity;
			this.rubberArr = rubberArr;	
			
		}
		
		@Override
		public int getCount() {
			return rubberArr.size();
		}

		@Override
		public Object getItem(int position) {
			return rubberArr.get(position);

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
				convertView = lInflater.inflate(R.layout.rubber_type,null);
			}
			
			Rubber lMessageObj = rubberArr.get(position);
			TextView rubberTypeData = (TextView) convertView.findViewById(R.id.rubberTypeData);
			TextView rubberDateTypeData = (TextView) convertView.findViewById(R.id.rubberDateTypeData);
			ImageView deleteRubberData = (ImageView) convertView.findViewById(R.id.deleteRubberData);

			rubberTypeData.setText(lMessageObj.getRubberType());
			rubberDateTypeData.setText(lMessageObj.getRubberDate());
			deleteRubberData.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					rubberArr.remove(position);
					notifyDataSetChanged();
				}
			});

			
		
			return convertView;
		}
		
		
	}