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
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.service.proxy.Server;
import org.yousuowei.share.service.proxy.Server.ServerBinder;
import org.yousuowei.share.service.proxy.Server.ServerReciverListener;
import org.yousuowei.share.utils.NetworkUtil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;

/**
 * @ClassName: RoomInfoAci
 * @Description: 房间详情界面
 * @author: jie
 * @date: 2014-9-4 下午2:30:41
 */

public class RoomServerAci extends BaseTitleAci {

    private TextView msgTv;

    private ServerConnect connect;

    @Override
    protected void initView() {
	setContentView(R.layout.room_server_layout);
	super.initView();

	msgTv = (TextView) findViewById(R.id.tv_msg);
    }

    @Override
    protected void initControl() {
	super.initControl();

	if (null == connect) {
	    connect = new ServerConnect();
	    Intent intent = new Intent(getApplicationContext(), Server.class);
	    bindService(intent, connect, Context.BIND_AUTO_CREATE);
	}
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	unbindService(connect);
	NetworkUtil.closeWifiAp(m_ctx.get());
    }

    private final static int HW_SHOW_MSG = 0;

    @Override
    protected void handleMessage(Message msg) {
	super.handleMessage(msg);
	switch (msg.what) {
	case HW_SHOW_MSG:
	    showMsg(msg.obj);
	    break;
	}
    }

    private String msg = "";

    private void showMsg(Object obj) {
	msg = obj.toString() + " \n";
	CommonLog.d(getLogTag(), "showMsg:", msg);
	msgTv.setText(msg);
    }

    private class ServerConnect implements ServiceConnection {
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
	    CommonLog.d(getLogTag(), "onServiceConnected ComponentName:", name);
	    ServerBinder binder = (ServerBinder) service;
	    binder.setListener(new ServerReciverListener() {
		@Override
		public void reciver(String msg) {
		    Message hwMsg = m_handler.obtainMessage(HW_SHOW_MSG);
		    hwMsg.obj = msg;
		    m_handler.sendMessage(hwMsg);
		}
	    });
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {

	}
    }

}
