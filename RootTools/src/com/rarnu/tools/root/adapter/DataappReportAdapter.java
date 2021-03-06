package com.rarnu.tools.root.adapter;

import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rarnu.tools.root.GlobalInstance;
import com.rarnu.tools.root.R;
import com.rarnu.tools.root.common.DataappInfo;
import com.rarnu.tools.root.holder.DataappReportAdapterHolder;
import com.rarnu.tools.root.utils.ApkUtils;

public class DataappReportAdapter extends BaseAdapter {

	// [region] field define
	private LayoutInflater inflater;
	private List<DataappInfo> list;

	// [/region]

	// [region] constructor
	public DataappReportAdapter(LayoutInflater inflater, List<DataappInfo> list) {
		this.inflater = inflater;
		this.list = list;
	}

	// [/region]

	// [region] adapter
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final DataappInfo item = list.get(position);
		View v;
		if (convertView == null) {
			v = inflater.inflate(R.layout.dataapp_report_item, parent, false);
		} else {
			v = convertView;
		}
		DataappReportAdapterHolder holder = (DataappReportAdapterHolder) v.getTag();
		if (holder == null) {
			holder = new DataappReportAdapterHolder();
			holder.icon = (ImageView) v.findViewById(R.id.report_icon);
			holder.name = (TextView) v.findViewById(R.id.report_name);
			holder.state = (TextView) v.findViewById(R.id.report_state);
			v.setTag(holder);
		}

		if (item != null) {

			if (item.type == 1) {
				holder.icon.setBackgroundDrawable(GlobalInstance.pm.getApplicationIcon(item.info));
				holder.name.setText(GlobalInstance.pm.getApplicationLabel(item.info));
				switch (item.logId) {
				case 0:
					holder.state.setText(R.string.rep_bak_succ);
					holder.state.setTextColor(Color.GRAY);
					break;
				case 1:
					holder.state.setText(R.string.rep_bak_na);
					holder.state.setTextColor(Color.GRAY);
					break;
				case 2:
					holder.state.setText(R.string.rep_bak_fail);
					holder.state.setTextColor(Color.RED);
					break;
				}

			} else {
				holder.icon.setBackgroundDrawable(ApkUtils.getIconFromPackage(v.getContext(), item.info));
				holder.name.setText(ApkUtils.getLabelFromPackage(v.getContext(), item.info));
				switch (item.logId) {
				case 0:
					holder.state.setText(R.string.rep_res_succ);
					holder.state.setTextColor(Color.GRAY);
					break;
				case 2:
					holder.state.setText(R.string.rep_res_fail);
					holder.state.setTextColor(Color.RED);
					break;
				}
			}

		}

		return v;
	}

	// [/region]
}
