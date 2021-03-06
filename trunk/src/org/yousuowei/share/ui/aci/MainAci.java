/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: MainAci.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 上午10:02:58
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci;

import org.yousuowei.share.R;
import org.yousuowei.share.ui.aci.fragment.BaseFragment;
import org.yousuowei.share.ui.adapter.TaskFragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.baidu.mapapi.SDKInitializer;
import com.viewpagerindicator.TabPageIndicator;

/**
 * @ClassName: MainAci
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 上午10:02:58
 */

public class MainAci extends FragmentActivity {

    // view
    private ViewPager pager;
    private FragmentPagerAdapter adapter;
    private TabPageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_layout);
	// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
	// 注意该方法要再setContentView方法之前实现
	SDKInitializer.initialize(getApplicationContext());
	initView();
    }

    protected void initView() {
	pager = (ViewPager) findViewById(R.id.pager);
	adapter = new TaskFragmentAdapter(getApplicationContext(),
		getSupportFragmentManager());
	pager.setAdapter(adapter);

	indicator = (TabPageIndicator) findViewById(R.id.indicator);
	indicator.setViewPager(pager);
	indicator.setOnPageChangeListener(pageChangeListener);
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

	@Override
	public void onPageSelected(int position) {
	    ((BaseFragment) adapter.getItem(position)).showTitle(position);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
    };

}
