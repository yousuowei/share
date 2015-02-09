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

package org.yousuowei.share.data.http.request;

import org.yousuowei.share.common.HttpConstants;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.mipt.clientcommon.BaseRequest;
import com.mipt.clientcommon.BaseResult;

/**
 * @ClassName: BaseBusinessRequest
 * @Description: 符合网络请求公共业务父类
 * @author: jie
 * @date: 2014-8-29 下午2:01:34
 */

public abstract class BaseBusinessRequest extends BaseRequest {

    public BaseBusinessRequest(Context context, BaseResult result) {
	this(context, result, false);
    }

    public BaseBusinessRequest(Context context, BaseResult result,
	    Boolean needPassport) {
	super(context, result, needPassport);
    }

    @Override
    protected RequestType getMethod() {
	return RequestType.GET;
    }

    @Override
    protected String getUrl() {
	String url = HttpConstants.fixUrl(context, getActionUrl());
	return url;
    }

    protected abstract String getActionUrl();

    @Override
    protected ArrayMap<String, String> appendUrlSegment() {
	return null;
    }

    @Override
    protected ArrayMap<String, String> getHeaders() {
	String UseAgent = getUserAgent();
	ArrayMap<String, String> map = new ArrayMap<String, String>();
	map.put("UseAgent", UseAgent);
	return map;
    }

    private String getUserAgent() {
	// android.os.Build.VERSION.RELEASE获取版本号
	// android.os.Build.MODEL 获取手机型号
	return android.os.Build.MODEL;
    }

    protected String getLogTag() {
	return this.getClass().getName();
    }

}
