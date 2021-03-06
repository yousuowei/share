/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BaseBusinessRequest.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.http.request
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-29 下午2:01:34
 * @version: V1.0
 */

package org.yousuowei.share.data.http.request.lan;

import org.yousuowei.share.data.http.result.SendMsgResult;

import android.content.Context;

/**
 * @ClassName: TestLanRequest
 * @Description: 测试局域网发送请求
 * @author: jie
 * @date: 2014-8-29 下午2:01:34
 */

public class SendMsgRequest extends BaseLanRequest {

    public SendMsgRequest(Context context) {
	super(context, new SendMsgResult(context));
    }

    @Override
    protected String getActionUrl() {
	return "你好啊！测试下额！";
    }

}
