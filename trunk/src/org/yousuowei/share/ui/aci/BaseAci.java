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

import java.lang.ref.WeakReference;
import java.util.List;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.ui.view.CommonDialog;
import org.yousuowei.share.ui.view.CommonToast;
import org.yousuowei.share.ui.view.CommonDialog.BtnInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * @ClassName: BaseAci
 * @Description: Activity 基础类
 * @author: jie
 * @date: 2014-6-30 下午5:06:04
 */

public class BaseAci extends Activity {

    // view
    protected CommonDialog m_dialog;
    protected Toast m_toast;
    // data
    // control
    protected WeakReference<Context> m_ctx = new WeakReference<Context>(this);
    protected Mhandler m_handler = new Mhandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	m_toast = new CommonToast(m_ctx.get());
	m_dialog = new CommonDialog(m_ctx.get());
	initView();
	initData();
	initControl();
    }

    @Override
    protected void onPause() {
	super.onPause();
	cancleDialog();
    }

    private void cancleDialog() {
	if (null != m_dialog) {
	    m_dialog.dismiss();
	    m_dialog.cancel();
	    m_dialog = null;
	}
    }

    /**
     * init start
     */
    protected void initView() {

    }

    protected void initData() {

    }

    protected void initControl() {

    }

    /**
     * init end
     */

    /**
     * control start
     */
    protected static class Mhandler extends Handler {
	private WeakReference<BaseAci> aci;

	public Mhandler(BaseAci aci) {
	    this.aci = new WeakReference<BaseAci>(aci);
	}

	@Override
	public void handleMessage(Message msg) {
	    super.handleMessage(msg);
	    if (null != aci) {
		switch (msg.what) {
		case Constants.HW_SHOW_DIALOG:
		    aci.get().m_dialog.show();
		    break;
		case Constants.HW_SHOW_TOAST:
		    aci.get().m_toast.show();
		    break;
		case Constants.HW_HIDE_DIALOG:
		    aci.get().m_dialog.dismiss();
		    break;
		}
		aci.get().handleMessage(msg);
	    }
	}
    }

    /**
     * control end
     */

    /**
     * operation start
     */
    protected void handleMessage(Message msg) {

    }

    protected void showToast(String toastStr) {
	if (null == m_toast) {
	    throw new NullPointerException("m_toast instance is null!");
	}
	CommonLog.d(getLogTag(), "showToast toastStr:", toastStr);
	m_toast.setText(toastStr);
	m_handler.sendEmptyMessage(Constants.HW_SHOW_TOAST);
    }

    protected void showDialog(String content, List<BtnInfo> btnInfos) {
	if (null == m_dialog) {
	    throw new NullPointerException("m_dialog instance is null!");
	}
	CommonLog.d(getLogTag(), "showDialog content:", content);
	m_dialog.addContent(content, btnInfos);
	m_handler.sendEmptyMessage(Constants.HW_SHOW_DIALOG);
    }

    protected void hideDialog() {
	m_handler.sendEmptyMessage(Constants.HW_HIDE_DIALOG);
    }

    /**
     * operation end
     */

    protected String getLogTag() {
	return this.getClass().getSimpleName();
    }

}
