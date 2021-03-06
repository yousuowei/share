package org.yousuowei.share.ui.view;

import org.yousuowei.share.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CommonToast extends Toast {
    private Context mContext;
    private TextView mToastTxt;

    public CommonToast(Context context) {
	this(context, 0, 0);
    }

    public CommonToast(Context context, int resId, int textViewId) {
	super(context);
	mContext = context;
	setView(resId, textViewId);
    }

    private void setView(int resId, int textViewId) {
	if (0 == resId) {
	    resId = R.layout.common_toast_layout;
	    textViewId = R.id.tv_toast;
	}
	View v = LayoutInflater.from(mContext).inflate(resId, null);
	mToastTxt = (TextView) v.findViewById(textViewId);
	if (mToastTxt == null) {
	    throw new NullPointerException("can't found mToastTxt!");
	}
	setView(v);
    }

    @Override
    public void setText(CharSequence s) {
	setDuration(LENGTH_LONG);
	mToastTxt.setText(s);
    }

}
