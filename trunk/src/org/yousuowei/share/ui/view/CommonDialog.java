package org.yousuowei.share.ui.view;

import java.util.List;

import org.yousuowei.share.R;
import org.yousuowei.share.common.CommonLog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommonDialog extends Dialog {

    // view
    private ViewGroup mainV;
    private TextView contentTv;
    private ViewGroup btnListV;
    private List<BtnInfo> btnInfos;

    private String content;

    public CommonDialog(Context context) {
	super(context, R.style.common_dialog);
	setContentView(R.layout.common_dialog_layout);
	mainV = (ViewGroup) findViewById(R.id.fl_main);
    }

    /**
     * 
     * @param content
     *            弹窗内容
     * @param btnInfos
     *            弹窗button
     * @author: jie
     * @date: 2014-12-12 下午4:05:54
     */
    public void addContent(String content, List<BtnInfo> btnInfos) {
	this.content = content;
	this.btnInfos = btnInfos;
	initView();
	initData();
    }

    private void initView() {
	btnListV = (ViewGroup) findViewById(R.id.ll_btn_list);
	contentTv = (TextView) findViewById(R.id.tv_content);

	if (null != btnInfos && btnInfos.size() != 0) {
	    String text;
	    TextView view;
	    btnListV.removeAllViews();
	    boolean isFirst = true;
	    int marginLeft;
	    for (BtnInfo info : btnInfos) {
		if (isFirst) {
		    view = (TextView) LayoutInflater.from(getContext())
			    .inflate(R.layout.common_dialog_init_btn_layout,
				    null);
		    marginLeft = 0;
		    isFirst = false;
		} else {
		    view = (TextView) LayoutInflater.from(getContext())
			    .inflate(R.layout.common_dialog_btn_layout, null);
		    marginLeft = getContext().getResources()
			    .getDimensionPixelSize(
				    R.dimen.bind_bracelet_btn_ok_margin_center);
		}
		text = getContext().getResources().getString(info.textResId);
		view.setText(text);
		view.setOnClickListener(info.clickListener);

		CommonLog.d("", "test left:", marginLeft);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(20, 0, 0, 0);
		btnListV.addView(view, layoutParams);
	    }
	}
    }

    private void initData() {
	contentTv.setText(content);
    }

    public static class BtnInfo {
	public BtnInfo() {
	    // TODO Auto-generated constructor stub
	}

	public BtnInfo(int textResId,
		android.view.View.OnClickListener clickListener) {
	    this.textResId = textResId;
	    this.clickListener = clickListener;
	}

	public int textResId;
	public android.view.View.OnClickListener clickListener;
    }

    @Override
    public void show() {
	mainV.setVisibility(View.VISIBLE);
	super.show();
    }
}
