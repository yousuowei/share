/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: ActionFactory.java
 * @Prject: BeeVideo
 * @Package: cn.beevideo.weixin.http
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-12 下午4:48:53
 * @version: V1.0
 */

package org.yousuowei.share.data.sms.action;

import org.yousuowei.share.common.CommonLog;

import android.content.Context;
import android.text.TextUtils;

/**
 * @ClassName: ActionFactory
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-12 下午4:48:53
 */

public class SmsActionFactory {
    private final static String TAG = SmsActionFactory.class.getName();

    private static SmsActionFactory factory;
    private static byte[] loocker = new byte[0];

    private final static String SPLIT_ACTION_TAG = ":";
    private final static String SPLIT_PARAMS_TAG = ",";

    private SmsActionFactory() {
    }

    public static synchronized SmsActionFactory getInstance() {
	synchronized (loocker) {
	    if (null == factory) {
		factory = new SmsActionFactory();
	    }
	}
	return factory;
    }

    public synchronized void excuteAction(Context ctx, String smsContent,
	    String fromPhone) {
	if (TextUtils.isEmpty(smsContent)) {
	    return;
	}
	String actionName = null;
	String[] params = null;
	try {
	    String[] contents = smsContent.split(SPLIT_ACTION_TAG);
	    actionName = contents[0];
	    String paramStr = contents[1];
	    params = paramStr.split(SPLIT_PARAMS_TAG);
	} catch (Exception e) {
	    CommonLog.e(TAG, e);
	}
	if (TextUtils.isEmpty(actionName)) {
	    return;
	}
	CommonLog.d(TAG, "excuteAction actionName:", actionName);
	BaseAction action = createAction(ctx, actionName, params, fromPhone);
	if (null == action) {
	    return;
	}
	action.executeAction();
    }

    private BaseAction createAction(Context ctx, String actionName,
	    String[] params, String fromPhone) {
	BaseAction action = null;
	return action;
    }

}
