/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SettingAdapter.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.adapter
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午4:17:47
 * @version: V1.0
 */

package org.yousuowei.share.ui.adapter;

import org.yousuowei.share.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @ClassName: SettingAdapter
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午4:17:47
 */

public class SettingAdapter extends BaseAdapter {
    // private final static String TAG = SettingAdapter.class.getName();

    private Context context;
    private String[] settings;

    public SettingAdapter(Context context) {
	this.context = context;
	settings = context.getResources().getStringArray(R.array.settings);
    }

    @Override
    public int getCount() {
	return settings.length;
    }

    @Override
    public Object getItem(int position) {
	return settings[position];
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	if (null == convertView) {
	    convertView = LayoutInflater.from(context).inflate(
		    R.layout.main_setting_item_layout, null);
	    holder = new ViewHolder();
	    holder.settingTv = (TextView) convertView
		    .findViewById(R.id.tv_setting);
	    convertView.setTag(holder);
	} else {
	    holder = (ViewHolder) convertView.getTag();
	}
	holder.settingTv.setText(settings[position]);
	return convertView;
    }

    public class ViewHolder {
	TextView settingTv;

    }
}
