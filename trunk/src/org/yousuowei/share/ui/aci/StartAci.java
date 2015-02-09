/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: StartAci.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:30:58
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci;

import org.yousuowei.share.R;
import org.yousuowei.share.common.Api;
import org.yousuowei.share.utils.Utils;

import com.squareup.picasso.Picasso;
import com.umeng.update.UmengUpdateAgent;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

/**
 * @ClassName: StartAci
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:30:58
 */

public class StartAci extends BaseAci {
    private ImageView startIv;

    private Runnable nextAciRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
	super.onPause();
	m_handler.removeCallbacks(nextAciRun);
    }

    @Override
    protected void initView() {
	setContentView(R.layout.start_layout);
	startIv = (ImageView) findViewById(R.id.iv_start);

	Picasso.with(m_ctx.get()).load(R.drawable.img_start).into(startIv);
    }

    @Override
    protected void initControl() {
	showDeviceInfo();
	goNextAci();
	checkUpdate();
    }

    private void checkUpdate() {
	UmengUpdateAgent.setUpdateAutoPopup(true);
	UmengUpdateAgent.silentUpdate(m_ctx.get());
    }

    private void showDeviceInfo() {
	int maxMemory = Utils.getAppMaxMemory(getApplicationContext());
	DisplayMetrics mDisplayMetrics = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
	int width = mDisplayMetrics.widthPixels;
	int heiht = mDisplayMetrics.heightPixels;
	int dpi = mDisplayMetrics.densityDpi;
	WebSettings settings = new WebView(m_ctx.get()).getSettings();
	String webUserAgent = settings.getUserAgentString();

	Log.d(getLogTag(), "device setting - maxMemory:" + maxMemory
		+ " width:" + width + " heiht:" + heiht + " dpi:" + dpi
		+ " webUserAgent:" + webUserAgent);
    }

    private void goNextAci() {
	nextAciRun = new Runnable() {
	    @Override
	    public void run() {
		Api.startMainAci(m_ctx.get());
	    }
	};
	m_handler.postDelayed(nextAciRun, 2000);
    }
}
