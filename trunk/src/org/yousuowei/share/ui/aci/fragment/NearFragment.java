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

import java.util.ArrayList;
import java.util.List;

import org.yousuowei.share.R;
import org.yousuowei.share.common.Api;
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.data.entity.NearInfo;
import org.yousuowei.share.ui.adapter.NearListAdapter;
import org.yousuowei.share.utils.WifiUtil;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @ClassName: PostionFragment
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-2 下午4:14:47
 */

public class NearFragment extends BaseFragment {

    private ListView nearLv;
    private NearListAdapter nearListAdapter;
    private TextView refreshTv;
    private TextView openTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	CommonLog.d(getLogTag(), "onCreateView");
	mainV = inflater.inflate(R.layout.main_near_layout, null);
	initView();
	initControl();
	return mainV;
    }

    private void initView() {
	nearLv = (ListView) mainV.findViewById(R.id.lv_near_list);
	nearLv.setOnItemClickListener(itemClickListener);
	refreshTv = (TextView) mainV.findViewById(R.id.tv_refresh);
	openTv = (TextView) mainV.findViewById(R.id.tv_open_room);
    }

    private void initControl() {
	WifiUtil.getInstance(getActivity()).setOnWifiChangeListener(
		onWifiChangeListener);
	WifiUtil.getInstance(getActivity()).searchWifi();
	// 通过按钮事件搜索热点
	refreshTv.setOnClickListener(new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
		WifiUtil.getInstance(getActivity()).searchWifi();
	    }
	});
	openTv.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Api.startRoomBuildAci(getActivity());
	    }
	});

    }

    @Override
    public void onDestroyView() {
	super.onDestroyView();
	WifiUtil.getInstance(getActivity()).release();
    }

    private WifiUtil.OnWifiChangeListener onWifiChangeListener = new WifiUtil.OnWifiChangeListener() {
	@Override
	public void onStateChange(boolean isEnable) {

	}

	@Override
	public void onScanResult(List<ScanResult> wifiList) {
	    List<NearInfo> nearList = checkNearList(wifiList);
	    refreshNearList(nearList);
	}

	@Override
	public void onNetWorkChange(boolean isConnected) {
	    if (isConnected) {
		Api.startRoomClientAci(getActivity());
	    } else {
		showToast("wifi 连接热点失败！");
	    }
	}
    };

    public List<NearInfo> checkNearList(List<ScanResult> wifiList) {
	List<NearInfo> dempNearList;
	if (null == wifiList) {
	    dempNearList = new ArrayList<NearInfo>(0);
	} else {
	    dempNearList = new ArrayList<NearInfo>(wifiList.size());
	    for (ScanResult result : wifiList) {
		if ((result.SSID).contains(Constants.DEFAULT_VERIFY_SSID)) {
		    dempNearList.add(new NearInfo(result.SSID));
		}
	    }
	}
	return dempNearList;
    }

    private void refreshNearList(List<NearInfo> nearList) {
	if (null == nearListAdapter) {
	    nearListAdapter = new NearListAdapter(getActivity(), nearList);
	    nearLv.setAdapter(nearListAdapter);
	} else {
	    nearListAdapter.setNearList(nearList);
	}
    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	    switch (parent.getId()) {
	    case R.id.lv_near_list:
		NearInfo nearInfo = (NearInfo) parent
			.getItemAtPosition(position);
		WifiUtil.getInstance(getActivity()).connectWifi(nearInfo.ssid,
			null);
		break;
	    }
	}
    };
}
