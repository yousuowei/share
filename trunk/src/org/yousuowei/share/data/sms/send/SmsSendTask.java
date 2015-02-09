/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SmsSendTask.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.sms
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 下午1:57:24
 * @version: V1.0
 */

package org.yousuowei.share.data.sms.send;

import java.util.List;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.data.entity.SmsMsgInfo;
import org.yousuowei.share.task.AbstractTask;
import org.yousuowei.share.task.TaskCallbackIfc;

import android.content.Context;
import android.telephony.SmsManager;
import android.text.TextUtils;

/**
 * @ClassName: SmsSendTask
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 下午1:57:24
 */

public class SmsSendTask extends AbstractTask {
    private final static String TAG = SmsSendTask.class.getName();

    private static final long serialVersionUID = 1L;
    private SmsMsgInfo msg;

    public SmsSendTask(Context mContext, TaskCallbackIfc mCallback,
	    SmsMsgInfo msg) {
	super(mContext, mCallback);
	this.msg = msg;
    }

    @Override
    public void execute() {
	String content = msg.getContentStr();
	CommonLog.d(TAG, "send sms msg:", msg.toString());
	if (TextUtils.isEmpty(content)) {
	    setError(T_RESULT_FAILED);
	}
	String phone = msg.phoneNum;// 电话号码
	if (TextUtils.isEmpty(phone)) {
	    setError(T_RESULT_FAILED);
	    return;
	}

	try {
	    SmsManager smsManager = SmsManager.getDefault();
	    /** 切分短信，每七十个汉字切一个，不足七十就只有一个：返回的是字符串的List集合 */
	    List<String> texts = smsManager.divideMessage(content);
	    String text;
	    for (int i = 0; i < texts.size(); i++) {
		text = texts.get(i);
		smsManager.sendTextMessage(phone, null, text, null, null);
	    }
	} catch (Exception e) {
	    CommonLog.e(TAG, e);
	    setError(T_RESULT_FAILED);
	}
	CommonLog.d(TAG, "send sms success! phone:", phone);
    }

}
