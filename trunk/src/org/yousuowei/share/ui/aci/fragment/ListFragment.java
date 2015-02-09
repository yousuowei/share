/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: PostionFragment.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.aci
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-2 下午4:14:47
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci.fragment;

import org.yousuowei.share.R;
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.ui.adapter.AllListAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;

/**
 * @ClassName: ListFragment
 * @Description: 功能列表界面
 * @author: jie
 * @date: 2014-9-2 下午4:14:47
 */

public class ListFragment extends BaseFragment {

    private ExpandableListView listLv;
    private AllListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	CommonLog.d(getLogTag(), "onCreateView");
	mainV = inflater.inflate(R.layout.main_list_layout, null);
	initView();
	initData();
	return mainV;
    }

    private void initData() {

    }

    private void initView() {
	listAdapter = new AllListAdapter(getActivity());
	listLv = (ExpandableListView) mainV.findViewById(R.id.lv_list);
	listLv.setAdapter(listAdapter);
	listLv.setOnItemClickListener(itemClickListener);
    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	    switch (parent.getId()) {
	    case R.id.lv_near_list:

		break;
	    }
	}
    };
}
