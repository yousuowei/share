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
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.data.entity.VersionInfo;
import org.yousuowei.share.data.http.HttpEngine;
import org.yousuowei.share.data.http.result.VersionCheckResult;
import org.yousuowei.share.utils.Utils;

import com.mipt.clientcommon.BaseResult;
import com.mipt.clientcommon.HttpCallback;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	checkUpdate();
	showDeviceInfo();
    }

    private void checkUpdate() {
	// 自升级检测
	UmengUpdateAgent.update(m_ctx.get());
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

    @Override
    protected void initView() {
	super.initView();
	setContentView(R.layout.start_layout);
	startIv = (ImageView) findViewById(R.id.iv_start);

	Picasso.with(m_ctx.get()).load(R.drawable.img_start).into(startIv);
	goNextAci();
    }

    private void goNextAci() {
	m_handler.postDelayed(new Runnable() {
	    @Override
	    public void run() {
		Api.startMainAci(m_ctx.get());
	    }
	}, 2000);
    }

    private void checkVersion() {
	if (Constants.CHECK_VERSION) {
	    HttpCallback callback = new CheckVersionHttpCallback();
	    HttpEngine.requestCheckVersion(m_ctx.get(), callback);
	}
    }

    private class CheckVersionHttpCallback extends HttpCallback.SimpleCallback {
	@Override
	public void onRequestSuccess(int id, BaseResult result) {
	    httpGetNewVersion(result);
	}

	private void httpGetNewVersion(BaseResult result) {
	    VersionInfo versionInfo = (VersionInfo) ((VersionCheckResult) result)
		    .getData();
	    showNewVersion(versionInfo);
	}
    }

    private void showNewVersion(VersionInfo versionInfo) {
	// List<BtnInfo> btnInfos = new ArrayList<CommonDialog.BtnInfo>();
	// BtnInfo downLoadBtn = new
	// BtnInfo(R.string.version_dialog_btn_download,
	// new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	//
	// m_dialog.addContent(content, btnInfos);
    }
}
