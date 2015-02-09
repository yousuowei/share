package org.yousuowei.share.service.proxy;

import java.io.IOException;
import java.util.Date;

import org.yousuowei.share.common.Constants;
import org.yousuowei.share.service.proxy.NanoHTTPD.Response.Status;
import org.yousuowei.share.utils.StringUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class Server extends Service {

    private final static String TAG = Server.class.getName();

    private ServerBinder binder;
    private ServerHTTPD server;

    private ServerReciverListener listener;

    @Override
    public void onCreate() {
	Log.d(TAG, "onCreate");
	binder = new ServerBinder();
	server = new ServerHTTPD(Constants.DEFAULT_SERVER_PORT,
		getApplicationContext());
	try {
	    server.start();
	} catch (IOException e) {
	    Log.e(TAG, "onBind", e);
	}
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
	server.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
	return binder;
    }

    public class ServerBinder extends Binder {
	public void setListener(ServerReciverListener reciverListener) {
	    listener = reciverListener;
	    String str = "ip:" + Constants.DEFAULT_SERVER_IP + " port:"
		    + Constants.DEFAULT_SERVER_PORT;
	    reciverMsg(str);
	}
    }

    private void reciverMsg(String str) {
	if (null != listener) {
	    listener.reciver(str);
	}
    }

    public interface ServerReciverListener {
	public void reciver(String msg);
    }

    public class ServerHTTPD extends NanoHTTPD {

	@SuppressWarnings("unused")
	private Context ctx;

	public ServerHTTPD(int port, Context mctx) {
	    super(port);
	    ctx = mctx;
	}

	@Override
	public Response serve(IHTTPSession session) {
	    Log.d(TAG, "serve url:" + session.getUri());
	    String respStr = StringUtil.appendStr(new Date(),
		    "- hello server!", Constants.TAG_SPLIT_WRAP, " url:",
		    session.getUri());
	    reciverMsg(respStr);
	    return new NanoHTTPD.Response(Status.OK, NanoHTTPD.MIME_HTML,
		    respStr);
	}
    }

}
