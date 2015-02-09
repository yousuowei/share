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
import org.yousuowei.share.common.Api;
import org.yousuowei.share.ui.adapter.SettingAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @ClassName: PostionFragment
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-2 下午4:14:47
 */

public class SettingFragment extends BaseFragment {

    // view
    private ListView settingLv;
    private SettingAdapter settingAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	mainV = inflater.inflate(R.layout.main_setting_layout, null);
	initView();
	return mainV;
    }

    protected void initView() {
	settingLv = (ListView) mainV.findViewById(R.id.lv_setting);
	settingLv.setOnItemClickListener(itemClickListener);
	settingAdapter = new SettingAdapter(getActivity());
	settingLv.setAdapter(settingAdapter);
    }

    private final static int UPGRADE_VERSION = 0;
    private final static int SHARE = UPGRADE_VERSION + 1;
    private final static int ABOUT_US = SHARE + 1;

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	    String title = (String) parent.getItemAtPosition(position);
	    ;
	    switch (position) {
	    case UPGRADE_VERSION:
		Api.startUpgradeVersionAci(getActivity(), title);
		break;
	    case SHARE:
		Api.startSettingShareAci(getActivity(), title);
		break;
	    case ABOUT_US:
		Api.startAboutUsAci(getActivity(), title);
		break;
	    }

	}
    };

}
