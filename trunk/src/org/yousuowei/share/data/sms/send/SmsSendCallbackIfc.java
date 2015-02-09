package org.yousuowei.share.data.sms.send;

import org.yousuowei.share.task.TaskCallbackIfc;

public interface SmsSendCallbackIfc extends TaskCallbackIfc {

    public void sendSuccess(int taskId);

    public void sendFailed(int taskId, int errorCode);

}
