package org.yousuowei.share.ui.view;

import org.yousuowei.share.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.ProgressBar;

public class DownloadDialog extends Dialog {

    // view
    private ProgressBar speedPb;
    private Button cancleButton;

    private android.view.View.OnClickListener clickListener;

    public DownloadDialog(Context context,
	    android.view.View.OnClickListener clickListener) {
	super(context, R.style.common_dialog);
	this.clickListener = clickListener;
	setContentView(R.layout.upgrading_dialog_layout);
	initView();
    }

    private void initView() {
	speedPb = (ProgressBar) findViewById(R.id.pb_speed);
	speedPb.setProgress(0);
	cancleButton = (Button) findViewById(R.id.btn_cancle);
	cancleButton.setOnClickListener(clickListener);
    }

    public void doChangeSpeed(int progress, int total) {
	speedPb.setMax(total);
	speedPb.setProgress(progress);
    }

}
