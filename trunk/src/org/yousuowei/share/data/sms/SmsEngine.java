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

package org.yousuowei.share.data.sms;

import java.util.List;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.data.entity.SmsMsgInfo;

import android.content.Context;
import android.telephony.SmsManager;
import android.text.TextUtils;

/**
 * @ClassName: SmsEngine
 * @Description: 短信操作类
 * @author: jie
 * @date: 2014-8-27 上午11:18:51
 */

public class SmsEngine {

    private final static String TAG = SmsEngine.class.getName();

    public static void sendSms(Context context, SmsMsgInfo msg) {
	new SendThread(msg).start();
    }

    public static class SendThread extends Thread {
	private SmsMsgInfo msg;

	public SendThread(SmsMsgInfo msg) {
	    this.msg = msg;
	}

	@Override
	public void run() {
	    String content = msg.getContentStr();
	    String phone = msg.phoneNum;// 电话号码
	    if (TextUtils.isEmpty(phone)) {
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
	    }
	    CommonLog.d(TAG, "send sms success! phone:", phone);
	}
    }

}
