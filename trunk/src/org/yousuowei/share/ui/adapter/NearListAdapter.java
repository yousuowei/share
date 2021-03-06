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

import java.util.ArrayList;
import java.util.List;

import org.yousuowei.share.R;
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.data.entity.NearInfo;
import org.yousuowei.share.ui.view.CheckSwitchButton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @ClassName: TaskAdapter
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午4:17:47
 */

public class NearListAdapter extends BaseAdapter {
    private final static String TAG = NearListAdapter.class.getName();

    // control
    private Context context;
    private DeleteTaskListener deleteTaskListener;
    private OnNearActiveChangeListener onNearActiveChangeListener;

    // view
    private View movedView;

    // data
    private List<NearInfo> nearList;
    private boolean isEdit;
    private int activePosition = -1;

    public NearListAdapter(Context context, List<NearInfo> nearList) {
	if (null == nearList) {
	    nearList = new ArrayList<NearInfo>(0);
	}
	this.setNearList(nearList);
	this.context = context;
    }

    private class ViewHolder {
	public TextView descTv;
	public TextView timeTv;
	public CheckSwitchButton selectIbtn;
	public ImageButton deleteIbtn;
	public ImageButton editIbtn;
	public TextView confirmDelTv;
    }

    @Override
    public int getCount() {
	return getNearList().size();
    }

    @Override
    public Object getItem(int position) {
	return getNearList().get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder view;
	if (null == convertView) {
	    view = new ViewHolder();
	    convertView = LayoutInflater.from(context).inflate(
		    R.layout.main_near_item_layout, null);
	    view.descTv = (TextView) convertView
		    .findViewById(R.id.tv_task_desc);
	    view.timeTv = (TextView) convertView
		    .findViewById(R.id.tv_task_time);
	    view.selectIbtn = (CheckSwitchButton) convertView
		    .findViewById(R.id.csb_isActive);
	    view.selectIbtn.setTag(position);
	    view.deleteIbtn = (ImageButton) convertView
		    .findViewById(R.id.iv_delete);
	    view.editIbtn = (ImageButton) convertView
		    .findViewById(R.id.iv_edit);
	    view.confirmDelTv = (TextView) convertView
		    .findViewById(R.id.tv_confirm_delete);
	    view.confirmDelTv.setTag(position);
	    view.confirmDelTv.setOnClickListener(clickListener);
	    final View parentV = convertView;
	    initDelClick(view, parentV);
	    convertView.setTag(view);
	} else {
	    view = (ViewHolder) convertView.getTag();
	}
	view.descTv.setText(getNearList().get(position).ssid);
	view.timeTv.setText(getNearList().get(position).ip);
	view.selectIbtn.setChecked(checkIsActive(position));
	view.selectIbtn.setVisibility(isEdit ? View.GONE : View.VISIBLE);
	view.selectIbtn.setOnCheckedChangeListener(checkedChangeListener);
	view.deleteIbtn.setVisibility(isEdit ? View.VISIBLE : View.GONE);
	view.editIbtn.setVisibility(isEdit ? View.VISIBLE : View.GONE);
	return convertView;
    }

    private boolean checkIsActive(int position) {
	return activePosition == position;
    }

    private void initDelClick(ViewHolder view, final View parentV) {
	view.deleteIbtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		if (null != movedView) {
		    scrollView(false, movedView);
		}
		scrollView(true, parentV);
		movedView = parentV;
	    }
	});
    }

    public void cancleDelete() {
	if (null != movedView) {
	    scrollView(false, movedView);
	    movedView = null;
	}
    }

    private void scrollView(boolean isLeft, View view) {
	float moveX = view.getResources().getDimensionPixelSize(
		R.dimen.main_task_lv_item_del_confirm_width);
	int moveInt = (int) (isLeft ? moveX : 0 - moveX);
	CommonLog.d(TAG, "scrollView isLeft:", isLeft, " moveInt:", moveInt);
	view.scrollBy(moveInt, 0);
    }

    public void changeListUiState(boolean isEdit) {
	this.isEdit = isEdit;
	notifyDataSetChanged();
    }

    public void changeEditState() {
	isEdit = !isEdit;
	cancleDelete();
	notifyDataSetChanged();
    }

    private OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
	@Override
	public void onCheckedChanged(CompoundButton buttonView,
		boolean isChecked) {
	    int position = (Integer) buttonView.getTag();
	    activePosition = isChecked ? position : -1;
	    if (null != onNearActiveChangeListener) {
		onNearActiveChangeListener.onTaskActiveChange(
			nearList.get(position), isChecked);
	    }
	    notifyDataSetChanged();
	}
    };

    public static interface OnNearActiveChangeListener {
	public void onTaskActiveChange(NearInfo nearInfo, boolean isChecked);
    }

    private OnClickListener clickListener = new OnClickListener() {
	@Override
	public void onClick(View v) {
	    int postion = Integer.parseInt(v.getTag().toString());
	    if (null != deleteTaskListener) {
		deleteTaskListener.deleteTask(nearList.get(postion));
	    }
	}
    };

    public static interface DeleteTaskListener {
	public void deleteTask(NearInfo NearInfo);
    }

    public List<NearInfo> getNearList() {
	return nearList;
    }

    public void setNearList(List<NearInfo> nearList) {
	if (null != nearList) {
	    this.nearList = nearList;
	    cancleDelete();
	    notifyDataSetChanged();
	}
    }

    public void setDeleteTaskListener(DeleteTaskListener deleteTaskListener) {
	this.deleteTaskListener = deleteTaskListener;
    }

    public void setOnNearActiveChangeListener(
	    OnNearActiveChangeListener onNearActiveChangeListener) {
	this.onNearActiveChangeListener = onNearActiveChangeListener;
    }

}
