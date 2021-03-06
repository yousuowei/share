/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BaseAci.java
 * @Prject: yousuowei-android-clearance
 * @Package: org.yousuowei.clearance.ui
 * @Description: TODO
 * @author: jie
 * @date: 2014-6-30 下午5:06:04
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci;

import org.yousuowei.share.R;
import org.yousuowei.share.common.Constants;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: BaseAci
 * @Description: Activity 基础类
 * @author: jie
 * @date: 2014-6-30 下午5:06:04
 */

public class BaseTitleAci extends BaseAci {

    protected ImageView backIv;
    protected TextView titleTv;

    @Override
    protected void initView() {
	super.initView();

	backIv = (ImageView) findViewById(R.id.iv_back);
	backIv.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		finish();
	    }
	});
	titleTv = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected void initData() {
	super.initData();

	String title = getIntent().getStringExtra(
		Constants.INTENT_EXTRA_KEY_ACI_TITLE);
	titleTv.setText(title);
    }

}
