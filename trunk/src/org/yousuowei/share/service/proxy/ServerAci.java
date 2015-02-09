package org.yousuowei.share.service.proxy;

import org.yousuowei.share.service.proxy.Server.ServerBinder;
import org.yousuowei.share.service.proxy.Server.ServerReciverListener;
import org.yousuowei.share.ui.aci.BaseAci;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ServerAci extends BaseAci {

    private final static String TAG = ServerAci.class.getName();
    private TextView msgTv;

    private ServerConnect connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	msgTv = new TextView(getApplicationContext());

	LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		LayoutParams.MATCH_PARENT);
	addContentView(msgTv, params);

	Log.d(TAG, "onCreate ");
	connect = new ServerConnect();
	Intent intent = new Intent(getApplicationContext(), Server.class);
	bindService(intent, connect, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	unbindService(connect);
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
	msg = obj.toString() + " \n\r";
	Log.d(TAG, "showMsg:" + msg);
	msgTv.setText(msg);
    }

    private class ServerConnect implements ServiceConnection {
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
	    Log.d(TAG, "onServiceConnected ComponentName:" + name);
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
