/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BuildRoomAci.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.aci
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-4 下午2:30:41
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci;

import org.yousuowei.share.R;
import org.yousuowei.share.common.Api;
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.helper.PrefHelper;
import org.yousuowei.share.utils.NetworkUtil;
import org.yousuowei.share.utils.StringUtil;
import org.yousuowei.share.utils.NetworkUtil.WifiConnectType;

import android.net.wifi.WifiConfiguration;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

/**
 * @ClassName: BuildRoomAci
 * @Description: 关于我们界面
 * @author: jie
 * @date: 2014-9-4 下午2:30:41
 */

public class RoomBuildAci extends BaseTitleAci {

    private EditText nameEt;
    private TextView descTv;
    private Button openBtn;
    private Button cancleBtn;

    @Override
    protected void initView() {
	setContentView(R.layout.room_build_layout);
	super.initView();

	nameEt = (EditText) findViewById(R.id.et_room_name);
	descTv = (TextView) findViewById(R.id.tv_room_desc);
	openBtn = (Button) findViewById(R.id.btn_ok);
	openBtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		openRoom();
	    }
	});
	cancleBtn = (Button) findViewById(R.id.btn_cancle);
	cancleBtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		finish();
	    }
	});
    }

    @Override
    protected void initData() {
	super.initData();
	String roomName = PrefHelper.getInstance(m_ctx.get()).getRoomName();
	nameEt.setText(roomName);
    }

    private void openRoom() {
	boolean dataValidate = dataValidate();
	boolean isWifiApEnable = false;
	if (dataValidate) {
	    String ssid = nameEt.getText().toString();
	    isWifiApEnable = setWifiApEnabled(true, ssid);
	    if (isWifiApEnable) {
		String ip = NetworkUtil.getLocalRemoteIp();
		descTv.setText("server ip:" + ip);
		PrefHelper.getInstance(m_ctx.get()).saveRoomName(
			nameEt.getText().toString());
		Api.startRoomServerAci(m_ctx.get());
	    }
	}
    }

    private boolean dataValidate() {
	if (TextUtils.isEmpty(nameEt.getText().toString())) {
	    showToast("请输入房间名！");
	    nameEt.requestFocus();
	    return false;
	}
	return true;
    }

    public boolean setWifiApEnabled(boolean enabled, String ssid) {
	CommonLog.d(getLogTag(), "setWifiApEnabled enabled:", enabled);
	String ssidStr = StringUtil.appendStr(ssid,
		Constants.DEFAULT_VERIFY_SSID);
	String password = Constants.DEFAULT_VERIFY_PASSWORD;
	WifiConfiguration apConfig = NetworkUtil.createWifiConfiguration(
		ssidStr, password, WifiConnectType.NONE);
	return NetworkUtil.openWifiAp(m_ctx.get(), apConfig);
    }
}
