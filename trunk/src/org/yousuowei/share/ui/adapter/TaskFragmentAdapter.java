/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: TaskFragmentAdapter.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.adapter
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午5:32:25
 * @version: V1.0
 */

package org.yousuowei.share.ui.adapter;

import org.yousuowei.share.R;
import org.yousuowei.share.ui.aci.fragment.ListFragment;
import org.yousuowei.share.ui.aci.fragment.NearFragment;
import org.yousuowei.share.ui.aci.fragment.PostionFragment;
import org.yousuowei.share.ui.aci.fragment.SettingFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

/**
 * @ClassName: TaskFragmentAdapter
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午5:32:25
 */

public class TaskFragmentAdapter extends FragmentPagerAdapter implements
	IconPagerAdapter {
    // view
    private Fragment[] fragments;
    // data
    private String[] tabNames;
    private int[] icons;

    public TaskFragmentAdapter(Context context, FragmentManager fm) {
	super(fm);
	tabNames = context.getResources()
		.getStringArray(R.array.main_tab_names);
	icons = new int[] { R.drawable.img_main_tab_postion,
		R.drawable.img_main_tab_task, R.drawable.img_main_tab_setting,
		R.drawable.img_main_tab_setting };
	fragments = new Fragment[] { new PostionFragment(), new NearFragment(),
		new ListFragment(), new SettingFragment() };
    }

    @Override
    public Fragment getItem(int position) {
	return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
	return tabNames[position % tabNames.length];
    }

    @Override
    public int getIconResId(int index) {
	return icons[index];
    }

    @Override
    public int getCount() {
	return tabNames.length;
    }

}
