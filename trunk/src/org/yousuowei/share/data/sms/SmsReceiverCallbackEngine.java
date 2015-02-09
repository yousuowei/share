/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SmsReceiverCallbackEngine.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.sms.action
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-10 下午4:16:59
 * @version: V1.0
 */

package org.yousuowei.share.data.sms;

import java.util.HashMap;
import java.util.Map;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.data.sms.action.BaseAction;

/**
 * @ClassName: SmsReceiverCallbackEngine
 * @Description: 注册监听，立即（一定时间内）手环进行答复
 * @author: jie
 * @date: 2014-9-10 下午4:16:59
 */

public class SmsReceiverCallbackEngine {
    private final static String TAG = SmsReceiverCallbackEngine.class.getName();

    private static SmsReceiverCallbackEngine engine;
    private Map<Class<?>, SmsReceiverCallback> callbacks;
    private Map<Class<?>, Long> timeCallbackMap;

    private boolean isTimeCheck = true;
    private final static long TIME_SLEEP = 2 * 1000;
    private final static long TIME_ACTION_TIME_OUT = 60 * 1000;

    private SmsReceiverCallbackEngine() {

    }

    public static SmsReceiverCallbackEngine getInstance() {
	synchronized (SmsReceiverCallbackEngine.class) {
	    if (null == engine) {
		engine = new SmsReceiverCallbackEngine();
		engine.callbacks = new HashMap<Class<?>, SmsReceiverCallback>();
		engine.timeCallbackMap = new HashMap<Class<?>, Long>();
		engine.timeCheckThread.start();
	    }
	}
	return engine;
    }

    public void registSmsReceiverCallback(Class<?> action,
	    SmsReceiverCallback callback) {
	registSmsReceiverCallback(action, callback, TIME_ACTION_TIME_OUT);
    }

    public void registSmsReceiverCallback(Class<?> action,
	    SmsReceiverCallback callback, long delayTime) {
	if (!action.getSuperclass().equals(BaseAction.class)) {
	    throw new ClassCastException(
		    "action must be children for org.om.bracelet.data.sms.action.BaseAction");

	}
	synchronized (callbacks) {
	    callbacks.put(action, callback);
	    CommonLog.d(TAG, "registSmsReceiverCallback action:",
		    action.getName(), " delayTime:", delayTime, " callbacks:",
		    callbacks.toString());
	}
	addTimeOutCheck(action, delayTime);
    }

    private void addTimeOutCheck(Class<?> action, long delayTime) {
	long timeOut = System.currentTimeMillis() + delayTime;
	synchronized (timeCallbackMap) {
	    timeCallbackMap.put(action, timeOut);
	    CommonLog.d(TAG, "addTimeOutCheck action:", action.getName(),
		    " timeOut:", timeOut, " timeCallbackMap:",
		    timeCallbackMap.toString());
	}
    }

    private void removeTimeOutCheck(Class<?> action) {
	synchronized (timeCallbackMap) {
	    timeCallbackMap.remove(action);
	    CommonLog.d(TAG, "removeTimeOutCheck action:", action.getName(),
		    " timeCallbackMap:", timeCallbackMap.toString());
	}
    }

    public void callback(BaseAction action) {
	if (null == action) {
	    throw new NullPointerException("callback action can't be null!");
	}
	synchronized (callbacks) {
	    for (Class<?> c : callbacks.keySet()) {
		if (action.getClass().equals(c)) {
		    callbacks.get(c).smsReceiverCallback(action);
		}
	    }
	}
	removeTimeOutCheck(action.getClass());
	CommonLog.d(TAG, "callback action:", action.getClass().getName());
    }

    public interface SmsReceiverCallback {
	public void smsReceiverCallback(BaseAction action);

	public void smsReceiverTimeoutCallback(Class<BaseAction> acionClass);
    }

    private Thread timeCheckThread = new Thread() {
	@Override
	public void run() {
	    while (isTimeCheck) {
		try {
		    sleep(TIME_SLEEP);
		} catch (InterruptedException e) {
		    CommonLog.e(TAG, e);
		}
		long nowTime = System.currentTimeMillis();
		checkCallbackTime(nowTime);
	    }
	}

	private void checkCallbackTime(long nowTime) {
	    if (null != timeCallbackMap) {
		long callbackTime;
		boolean isTimeOut;
		for (Class<?> c : timeCallbackMap.keySet()) {
		    callbackTime = timeCallbackMap.get(c);
		    isTimeOut = nowTime >= callbackTime;
		    CommonLog.d(TAG, "checkCallbackTime class:", c.getName(),
			    " nowTime:", nowTime, " callbackTime:",
			    callbackTime);
		    if (isTimeOut) {
			callbackTimeOut(c);
		    }
		}
	    }
	}

	@SuppressWarnings("unchecked")
	private void callbackTimeOut(Class<?> c) {
	    SmsReceiverCallback callback;
	    synchronized (callbacks) {
		callback = callbacks.get(c);
		if (null != callback) {
		    callback.smsReceiverTimeoutCallback((Class<BaseAction>) c);
		}
	    }
	    removeTimeOutCheck(c);
	    CommonLog.d(TAG, "callbackTimeOut action:", c.getName());
	};
    };

}
