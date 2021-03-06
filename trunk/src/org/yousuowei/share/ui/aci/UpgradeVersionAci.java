/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: UpgradeVersionAci.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.aci
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-4 下午2:30:41
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.yousuowei.share.R;
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.data.entity.VersionInfo;
import org.yousuowei.share.data.http.HttpEngine;
import org.yousuowei.share.data.http.result.VersionCheckResult;
import org.yousuowei.share.ui.view.CommonDialog;
import org.yousuowei.share.ui.view.DownloadDialog;
import org.yousuowei.share.ui.view.CommonDialog.BtnInfo;
import org.yousuowei.share.utils.Utils;

import com.mipt.clientcommon.BaseResult;
import com.mipt.clientcommon.HttpCallback;
import com.mipt.clientcommon.download.DownloadCallback;
import com.mipt.clientcommon.download.Downloader;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @ClassName: UpgradeVersionAci
 * @Description: 版本升级界面
 * @author: jie
 * @date: 2014-9-4 下午2:30:41
 */

public class UpgradeVersionAci extends BaseTitleAci {

    private ViewGroup newVersionVg;
    private TextView currentTv;
    private TextView nextTv;
    private TextView descTv;

    private Button okBtn;
    private Button cancle;
    private DownloadDialog speedDialog;

    // data
    private VersionInfo versionInfo;

    @Override
    protected void initView() {
	setContentView(R.layout.setting_upgrade_layout);
	super.initView();

	newVersionVg = (ViewGroup) findViewById(R.id.ll_new_version);
	currentTv = (TextView) findViewById(R.id.tv_current_version);
	nextTv = (TextView) findViewById(R.id.tv_next_version);
	descTv = (TextView) findViewById(R.id.tv_desc);
	okBtn = (Button) findViewById(R.id.btn_ok);
	okBtn.setOnClickListener(clickListener);
	cancle = (Button) findViewById(R.id.btn_cancle);
	cancle.setOnClickListener(clickListener);
    }

    @Override
    protected void initData() {
	super.initData();

	String currentVersion = Utils.getPackageInfos(m_ctx.get()).versionName;
	currentTv.setText(currentVersion);
	checkVersion();
    }

    @Override
    protected void onPause() {
	super.onPause();
	stopDownload();
	clearDialog();
    }

    private void stopDownload() {
	if (null != versionInfo) {
	    Downloader.getInstance(m_ctx.get()).cancel(versionInfo.url);
	}
    }

    private void clearDialog() {
	if (null != speedDialog) {
	    speedDialog.dismiss();
	    speedDialog.cancel();
	    speedDialog = null;
	}
    }

    private OnClickListener clickListener = new OnClickListener() {
	@Override
	public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.btn_ok:
		startDownloadNewVersion(versionInfo);
		break;
	    case R.id.btn_cancle:
		finish();
		break;
	    }
	}
    };

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

	@Override
	public void onRequestFail(int id, String reason) {
	    hideNewVersion();
	    showNowVersionFoundDialog();
	}

	private void httpGetNewVersion(BaseResult result) {
	    versionInfo = (VersionInfo) ((VersionCheckResult) result).getData();
	    showNewVersion(versionInfo);
	}
    }

    private void showNewVersion(VersionInfo versionInfo) {
	nextTv.setText(String.valueOf(versionInfo.version));
	descTv.setText(versionInfo.desc);
	newVersionVg.setVisibility(View.VISIBLE);
    }

    private void hideNewVersion() {
	newVersionVg.setVisibility(View.GONE);
    }

    private void showNowVersionFoundDialog() {
	String content = getResources().getString(
		R.string.setting_upgrade_newest);
	List<BtnInfo> btnInfos = new ArrayList<CommonDialog.BtnInfo>(1);
	BtnInfo btn = new BtnInfo();
	btn.textResId = R.string.common_ok;
	btn.clickListener = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		hideDialog();
	    }
	};
	btnInfos.add(btn);
	showDialog(content, btnInfos);
    }

    private void startDownloadNewVersion(VersionInfo versionInfo) {
	Downloader.getInstance(m_ctx.get()).download(versionInfo.url,
		new DownloadCallback.SimpleCallback() {
		    @Override
		    public void onDownloadStart(String downloadUrl) {
			if (null == speedDialog) {
			    speedDialog = new DownloadDialog(m_ctx.get(),
				    new OnClickListener() {
					@Override
					public void onClick(View v) {
					    stopDownload();
					    clearDialog();
					}
				    });
			}
			speedDialog.show();
		    }

		    @Override
		    public void onDownloadProgress(String downloadUrl,
			    int downloaded, int total) {
			if (null != speedDialog) {
			    speedDialog.doChangeSpeed(downloaded, total);
			}
		    }

		    @Override
		    public void onDownloadStop(String downloadUrl) {
			CommonLog.d(getLogTag(), "onDownloadStop");
			hideDownloadDialog();
		    }

		    private void hideDownloadDialog() {
			if (null != speedDialog) {
			    speedDialog.hide();
			}
		    }

		    @Override
		    public void onDownloadFail(String downloadUrl) {
			CommonLog.d(getLogTag(), "onDownloadFail");
			hideDownloadDialog();
		    }

		    @Override
		    public void onDownloadSuccess(String downloadUrl,
			    File apkFile) {
			CommonLog.d(getLogTag(), "onDownloadSuccess");
			Utils.installApk(m_ctx.get(), apkFile.getAbsolutePath());
			hideDownloadDialog();
		    }
		});
    }

}
