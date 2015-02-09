/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SmsBroadcastReceiver.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.sms
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:30:20
 * @version: V1.0
 */

package org.yousuowei.share.data.sms;

import org.yousuowei.share.common.CommonLog;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.text.TextUtils;

/**
 * @ClassName: SmsBroadcastReceiver
 * @Description: ContentObserver——内容观察者，目的是观察(捕捉)特定Uri引起的数据库的变化，继而做一些相应的处理，它类似于
 *               数据库技术中的触发器(Trigger)，当ContentObserver所观察的Uri发生变化时，便会触发它。
 *               触发器分为表触发器、行触发器，
 *               相应地ContentObserver也分为“表“ContentObserver、“行”ContentObserver，
 *               当然这是与它所监听的Uri MIME Type有关的。
 * @author: jie
 * @date: 2014-8-27 上午11:30:20
 */

public class SMSObserver extends ContentObserver {
    private static final String TAG = SMSObserver.class.getName();
    private Context context;
    // private Handler mHandler;
    // 内容解析器，和ContentProvider刚好相反,一个提供，一个解析
    private ContentResolver mResolver;
    // private static final int MAX_NUMS = 10;// 需要取得的短信条数
    private static final int MAX_ID = 0;// 用于保存记录中最大的ID
    // 需要获得的字段列
    private static final String[] PROJECTION = { SMSConstant.ID,
	    SMSConstant.TYPE, SMSConstant.ADDRESS, SMSConstant.BODY,
	    SMSConstant.DATE, SMSConstant.THREAD_ID, SMSConstant.READ,
	    SMSConstant.PROTOCOL };
    /*
     * 查询语句 用于查询ID大于 MAX_ID的记录，初始为0，后面用于保存记录的最大ID。短信的起始ID为1
     */
    private static final String SELECTION = SMSConstant.ID + " > %s" + " and ("
	    + SMSConstant.TYPE + "=" + SMSConstant.MESSAGE_TYPE_INBOX + " or "
	    + SMSConstant.TYPE + "=" + SMSConstant.MESSAGE_TYPE_SENT + ")";
    // 取值对应的结果就是PROJECTION 里对应的字段
    // private static final int COLUMN_INDEX_ID = 0;
    // private static final int COLUMN_INDEX_TYPE = 1;
    private static final int COLUMN_INDEX_PHONE = 2;
    private static final int COLUMN_INDEX_BODY = 3;
    // private static final int COLUMN_INDEX_DATE = 4;
    private static final int COLUMN_INDEX_PROTOCOL = 7;

    public SMSObserver(Handler handler, Context context) {
	super(handler);
	this.context = context;
	mResolver = context.getContentResolver();
    }

    @Override
    public void onChange(boolean selfChange) {
	super.onChange(selfChange);
	Cursor cursor = mResolver.query(SMSConstant.CONTENT_URI, // 查询的URI
		PROJECTION, // 需要取得的列
		String.format(SELECTION, MAX_ID), // 查询语句
		null, // 可能包括您的选择，将被替换selectionArgs的值，在选择它们出现的顺序。该值将被绑定为字符串。
		null); // 排序
	if (cursor != null) {
	    while (cursor.moveToNext()) {
		// int id = cursor.getInt(COLUMN_INDEX_ID);
		// int type = cursor.getInt(COLUMN_INDEX_TYPE);
		int protocol = cursor.getInt(COLUMN_INDEX_PROTOCOL);
		// long date = cursor.getLong(COLUMN_INDEX_DATE);
		String fromPhone = cursor.getString(COLUMN_INDEX_PHONE);
		String content = cursor.getString(COLUMN_INDEX_BODY);
		if (protocol == SMSConstant.PROTOCOL_SMS) {
		    boolean isBindBracelet = checkIsBindWear(context, fromPhone);
		    CommonLog.d(TAG, "onReceive fromPhone:", fromPhone,
			    " isBindBracelet:", isBindBracelet);
		    if (isBindBracelet) {
			SmsActionFactory.getInstance().excuteAction(context,
				content, fromPhone);
		    }
		    break;
		}
	    }
	    cursor.close();
	}
    }

    private boolean checkIsBindWear(Context context, String fromPhone) {
	if (TextUtils.isEmpty(fromPhone)) {
	    return false;
	}
	String bindWearPhone = "";
	CommonLog.d(TAG, "checkIsBindWear fromPhone:", fromPhone,
		" bindWearPhone:", bindWearPhone);
	if (null != bindWearPhone && phoneCompare(fromPhone, bindWearPhone)) {
	    return true;
	}
	return false;
    }

    private boolean phoneCompare(String phoneA, String phoneB) {
	String dempA = null;
	String dempB = null;
	if (phoneA.length() >= phoneB.length()) {
	    dempA = phoneA;
	    dempB = phoneB;
	} else {
	    dempA = phoneB;
	    dempB = phoneA;
	}

	int index = dempA.indexOf(dempB);
	return index != -1;
    }

}
