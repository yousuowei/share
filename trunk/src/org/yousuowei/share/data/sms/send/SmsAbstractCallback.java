/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SmsAbstractCallback.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.sms
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 下午5:57:21
 * @version: V1.0
 */

package org.yousuowei.share.data.sms.send;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.task.AbstractTask;

/**
 * @ClassName: SmsAbstractCallback
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 下午5:57:21
 */

public abstract class SmsAbstractCallback implements SmsSendCallbackIfc {
    private final static String TAG = SmsAbstractCallback.class.getName();

    @Override
    public void call(AbstractTask task) {
	CommonLog.d(TAG, "call result:", task.result, " result code:",
		task.getErrorCode());
	if (task instanceof SmsSendTask) {
	    if (task.getErrorCode() == AbstractTask.T_RESULT_OK) {
		sendSuccess(task.getTaskGroupId());
	    } else {
		sendFailed(task.getTaskGroupId(), task.getErrorCode());
	    }
	}
    }

    public abstract void sendSuccess(int taskId);

    public abstract void sendFailed(int taskId, int errorCode);
}
