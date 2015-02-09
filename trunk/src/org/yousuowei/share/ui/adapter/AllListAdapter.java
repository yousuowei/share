/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: TaskAdapter.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.adapter
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午4:17:47
 * @version: V1.0
 */

package org.yousuowei.share.ui.adapter;

import org.yousuowei.share.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: ListAdapter
 * @Description: 功能集合
 * @author: jie
 * @date: 2014-9-3 下午4:17:47
 */

public class AllListAdapter extends BaseExpandableListAdapter {
    // control
    private Context context;

    // data
    private String[] titles;

    private TypedArray childArray;

    @SuppressLint("Recycle")
    public AllListAdapter(Context context) {
	this.titles = context.getResources()
		.getStringArray(R.array.list_titles);

	childArray = context.getResources().obtainTypedArray(
		R.array.list_child_titles);
	this.context = context;
    }

    private class ViewHolder {
	TextView titleTv;
	ImageView rightIv;
    }

    @Override
    public int getGroupCount() {
	return titles.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
	return childArray.getTextArray(groupPosition).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
	return titles[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
	return childArray.getTextArray(groupPosition)[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
	return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
	return 0;
    }

    @Override
    public boolean hasStableIds() {
	return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
	    View convertView, ViewGroup parent) {
	ViewHolder view;
	if (null == convertView) {
	    view = new ViewHolder();
	    convertView = LayoutInflater.from(context).inflate(
		    R.layout.main_list_item_layout, null);
	    view.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
	    view.rightIv = (ImageView) convertView
		    .findViewById(R.id.iv_right_arrow);
	    convertView.setTag(view);
	} else {
	    view = (ViewHolder) convertView.getTag();
	}
	view.titleTv.setText(titles[groupPosition]);
	if (getChildrenCount(groupPosition) == 0) {
	    view.rightIv.setVisibility(View.VISIBLE);
	} else {
	    view.rightIv.setVisibility(View.GONE);
	}
	return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
	    boolean isLastChild, View convertView, ViewGroup parent) {
	ViewHolder view;
	if (null == convertView) {
	    view = new ViewHolder();
	    convertView = LayoutInflater.from(context).inflate(
		    R.layout.main_list_child_item_layout, null);
	    view.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
	    convertView.setTag(view);
	} else {
	    view = (ViewHolder) convertView.getTag();
	}
	view.titleTv
		.setText(childArray.getTextArray(groupPosition)[childPosition]);
	return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
	return true;
    }

}
