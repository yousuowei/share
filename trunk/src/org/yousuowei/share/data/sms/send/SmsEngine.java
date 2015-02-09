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

package org.yousuowei.share.data.sms.send;

import org.yousuowei.share.data.entity.SmsMsgInfo;
import org.yousuowei.share.task.TaskEngine;

import android.content.Context;

/**
 * @ClassName: SmsEngine
 * @Description: 短信操作类
 * @author: jie
 * @date: 2014-8-27 上午11:18:51
 */

public class SmsEngine {

    public static int sendSms(Context context, SmsSendCallbackIfc callback,
	    SmsMsgInfo msg) {
	SmsSendTask task = new SmsSendTask(context, callback, msg);
	return TaskEngine.getInstance().addTaskTail(task);
    }

    public static void cancleSendSms(int id) {
	TaskEngine.getInstance().cancelTasks(id);
    }

}
