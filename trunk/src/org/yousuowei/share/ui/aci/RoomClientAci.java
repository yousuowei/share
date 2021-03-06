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

import java.util.Date;

import org.yousuowei.share.R;
import org.yousuowei.share.common.CommonData;
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.data.entity.NearInfo;
import org.yousuowei.share.data.entity.VersionInfo;
import org.yousuowei.share.data.http.HttpEngine;
import org.yousuowei.share.data.http.result.SimpleJsonResult;
import org.yousuowei.share.utils.StringUtil;

import com.mipt.clientcommon.BaseResult;
import com.mipt.clientcommon.HttpCallback;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @ClassName: RoomInfoAci
 * @Description: 客户端展示房间界面
 * @author: jie
 * @date: 2014-9-4 下午2:30:41
 */

public class RoomClientAci extends BaseTitleAci {

    private TextView ssidTv;
    private TextView nameTv;
    private TextView msgTv;
    private EditText inputEt;
    private Button sendBtn;

    private String msg;

    @Override
    protected void initView() {
	setContentView(R.layout.room_client_layout);
	super.initView();

	ssidTv = (TextView) findViewById(R.id.tv_room_ssid);
	nameTv = (TextView) findViewById(R.id.tv_room_name);
	msgTv = (TextView) findViewById(R.id.tv_msg);
	inputEt = (EditText) findViewById(R.id.et_input);
	sendBtn = (Button) findViewById(R.id.btn_send);
	sendBtn.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		String input = inputEt.getText().toString();
		sendMsg(input);
	    }
	});
    }

    @Override
    protected void initData() {
	super.initData();

	NearInfo roomInfo = CommonData.getRoomInfo(m_ctx.get());
	ssidTv.setText(roomInfo.ssid);
	nameTv.setText(roomInfo.name);
	msg = StringUtil.appendStr(new Date().toString(),
		Constants.TAG_SPLIT_WRAP);
    }

    @Override
    protected void initControl() {
	super.initControl();
	msgTv.setText(msg);
    }

    private void sendMsg(String input) {
	if (checkInputStr(input)) {
	    msg = StringUtil.appendStr(msg, input, Constants.TAG_SPLIT_WRAP);
	    msgTv.setText(msg);
	}
	HttpEngine.requestMsgLan(m_ctx.get(),
		new HttpCallback.SimpleCallback() {
		    @Override
		    public void onRequestSuccess(int id, BaseResult result) {
			VersionInfo info = (VersionInfo) ((SimpleJsonResult) result)
				.getData();
			msg = StringUtil.appendStr(msg, info.getDesc(),
				Constants.TAG_SPLIT_WRAP);
		    }
		});
    }

    private boolean checkInputStr(String input) {
	if (TextUtils.isEmpty(input)) {
	    return false;
	}
	return true;
    }
}
