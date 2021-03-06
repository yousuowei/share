/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BaseFragment.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.aci.fragment
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午3:13:45
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci.fragment;

import java.lang.ref.WeakReference;
import java.util.List;

import org.yousuowei.share.R;
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.ui.view.CommonDialog;
import org.yousuowei.share.ui.view.CommonToast;
import org.yousuowei.share.ui.view.CommonDialog.BtnInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: BaseFragment
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-3 下午3:13:45
 */

public abstract class BaseFragment extends Fragment {
    // view
    protected View mainV;
    protected TextView titleTv;
    protected CommonDialog m_dialog;
    protected Toast m_toast;
    // data
    private String[] titles;
    // control
    protected Mhandler m_handler = new Mhandler(this);

    protected String getLogTag() {
	return this.getClass().getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	m_toast = new CommonToast(getActivity());
	m_dialog = new CommonDialog(getActivity());
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
	m_dialog.dismiss();
	m_dialog.cancel();
	m_dialog = null;
    }

    public void showTitle(int position) {
	if (null == mainV) {
	    return;
	}
	titleTv = (TextView) mainV.findViewById(R.id.tv_title);
	if (null == titleTv) {
	    throw new NullPointerException("fragment titleTv can't be null!");
	}
	if (null == titles) {
	    titles = getResources().getStringArray(R.array.main_titles);
	}
	CommonLog.d(getLogTag(), "showTitle position:", position, " title:",
		titles[position]);
	String title = titles[position];
	titleTv.setText(title);
    }

    protected static class Mhandler extends Handler {
	private WeakReference<BaseFragment> frg;

	public Mhandler(BaseFragment frg) {
	    this.frg = new WeakReference<BaseFragment>(frg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Message msg) {
	    super.handleMessage(msg);
	    if (null != frg) {
		switch (msg.what) {
		case Constants.HW_SHOW_DIALOG:
		    String content = msg.getData().getString(
			    Constants.INTENT_EXTRA_KEY_COMMON_DIALOG_CONTENT);
		    List<BtnInfo> btnInfos = (List<BtnInfo>) msg.obj;
		    frg.get().m_dialog.addContent(content, btnInfos);
		    frg.get().m_dialog.show();
		    break;
		case Constants.HW_SHOW_TOAST:
		    frg.get().m_toast.show();
		    break;
		case Constants.HW_HIDE_DIALOG:
		    frg.get().m_dialog.dismiss();
		    break;
		}
		frg.get().handleMessage(msg);
	    }
	}
    }

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
	    throw new NullPointerException("m_toast instance is null!");
	}
	CommonLog.d(getLogTag(), "showDialog content:", content);
	Message msg = m_handler.obtainMessage(Constants.HW_SHOW_DIALOG);
	msg.obj = btnInfos;
	Bundle data = new Bundle();
	data.putSerializable(Constants.INTENT_EXTRA_KEY_COMMON_DIALOG_CONTENT,
		content);
	msg.setData(data);
	m_handler.sendMessage(msg);
    }

    protected void hideDialog() {
	m_handler.sendEmptyMessage(Constants.HW_HIDE_DIALOG);
    }

}
