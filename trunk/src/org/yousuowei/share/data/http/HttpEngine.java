/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SmsEngine.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.sms
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:18:51
 * @version: V1.0
 */

package org.yousuowei.share.data.http;

import org.yousuowei.share.data.http.request.VersionCheckRequest;
import org.yousuowei.share.data.http.request.lan.SendMsgRequest;

import com.mipt.clientcommon.BaseRequest;
import com.mipt.clientcommon.HttpCallback;
import com.mipt.clientcommon.HttpTask;
import com.mipt.clientcommon.RequestIdGenFactory;
import com.mipt.clientcommon.TaskDispatcher;

import android.content.Context;

/**
 * @ClassName: SmsEngine
 * @Description: 短信操作类
 * @author: jie
 * @date: 2014-8-27 上午11:18:51
 */

public class HttpEngine {

    public static int requestCheckVersion(Context context, HttpCallback callback) {
	VersionCheckRequest request = new VersionCheckRequest(context);
	int taskId = executeHttpAsync(request, context, callback);
	return taskId;
    }

    public static int requestMsgLan(Context context, HttpCallback callback) {
	SendMsgRequest request = new SendMsgRequest(context);
	int taskId = executeHttpAsync(request, context, callback);
	return taskId;
    }

    private static int executeHttpAsync(BaseRequest request, Context context,
	    HttpCallback callback) {
	int taskId = RequestIdGenFactory.gen();
	HttpTask task = new HttpTask(context, request, callback, taskId);
	TaskDispatcher.getInstance().execute(task);
	return taskId;
    }

}
