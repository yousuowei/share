/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: Utils.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-28 下午3:45:11
 * @version: V1.0
 */

package org.yousuowei.share.utils;

import java.util.List;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;

import android.content.Context;
import android.telephony.SmsManager;
import android.text.TextUtils;

/**
 * 发送短信
 * 
 * @ClassName: SmsUtils
 * @Description: TODO
 * @author: jie
 * @date: 2015-2-4 下午2:39:43
 */
public class SmsUtils {
    private final static String TAG = SmsUtils.class.getName();

    public static void sendSms(Context context, SmsMsg msg) {
	new SendSmsThread(msg).start();
    }

    private static class SendSmsThread extends Thread {
	private SmsMsg msg;

	public SendSmsThread(SmsMsg msg) {
	    this.msg = msg;
	}

	@Override
	public void run() {
	    String content = msg.getContentStr();
	    CommonLog.d(TAG, "send sms msg:", msg.toString());
	    String phone = msg.phoneNum;// 电话号码
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

    public static class SmsMsg {
	public String phoneNum;
	public String head;
	public String paramStr;

	public SmsMsg(String head, String paramStr) {
	    this(null, head, paramStr);
	}

	public SmsMsg(String phoneNum, String head, String paramStr) {
	    super();
	    this.phoneNum = phoneNum;
	    this.head = head;
	    this.paramStr = paramStr;
	}

	public String getContentStr() {
	    if (TextUtils.isEmpty(head)) {
		throw new NullPointerException("SmsMsg's head can't be null!");
	    }
	    if (TextUtils.isEmpty(paramStr)) {
		throw new NullPointerException(
			"SmsMsg's paramStr can't be null!");
	    }
	    return StringUtil.appendStr(head, Constants.TAG_SPLIT_SMS_MSG,
		    paramStr);
	}

    }

}
