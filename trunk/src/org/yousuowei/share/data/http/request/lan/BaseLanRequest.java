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

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.mipt.clientcommon.BaseRequest;
import com.mipt.clientcommon.BaseResult;

/**
 * @ClassName: BaseBusinessRequest
 * @Description: 局域网内访问
 * @author: jie
 * @date: 2014-8-29 下午2:01:34
 */

public abstract class BaseLanRequest extends BaseRequest {

    public BaseLanRequest(Context context, BaseResult result) {
	this(context, result, false);
    }

    public BaseLanRequest(Context context, BaseResult result,
	    Boolean needPassport) {
	super(context, result, needPassport);
    }

    @Override
    protected RequestType getMethod() {
	return RequestType.GET;
    }

    @Override
    protected String getUrl() {
	String url = new StringBuilder().append(Constants.HTTP_69_HOST)
		.append(getActionUrl()).toString();
	CommonLog.d(getLogTag(), "test===", url);
	return url;
    }

    protected abstract String getActionUrl();

    protected String getLogTag() {
	return this.getClass().getName();
    }

    @Override
    protected ArrayMap<String, String> appendUrlSegment() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected ArrayMap<String, String> getHeaders() {
	// TODO Auto-generated method stub
	return null;
    }

}
