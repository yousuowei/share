/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BindBraceletResult.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.http.result
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-29 下午2:06:50
 * @version: V1.0
 */

package org.yousuowei.share.data.http.result;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.data.entity.BaseInfo;
import org.yousuowei.share.data.entity.JsonInfo;

import android.content.Context;

import com.google.gson.Gson;
import com.mipt.clientcommon.BaseResult;

/**
 * @ClassName: BindBraceletResult
 * @Description: 简单的status xml数据返回
 * @author: jie
 * @date: 2014-8-29 下午2:06:50
 */

public abstract class SimpleJsonResult extends BaseResult {

    private final static int STATUS_SUCESS = 0;

    // data
    private BaseInfo data;

    public SimpleJsonResult(Context context) {
	super(context);
    }

    @Override
    protected boolean parseResponse(InputStream is) throws Exception {
	Gson gson = new Gson();
	Reader reader = new InputStreamReader(is);
	JsonInfo jsonInfo = gson.fromJson(reader, JsonInfo.class);
	statusCode = jsonInfo.getStatus();
	msg = jsonInfo.getMsg();
	CommonLog.d(getLogTag(), "result:", jsonInfo.toString(), " data:",
		jsonInfo.getData().toString());
	if (statusCode == STATUS_SUCESS) {
	    data = (BaseInfo) gson.fromJson(jsonInfo.getData().toString(),
		    getInfoClass());
	    CommonLog.d(getLogTag(), "data:", data.toString());
	    return true;
	} else {
	    return false;
	}
    }

    protected abstract Class<?> getInfoClass();

    protected String getLogTag() {
	return this.getClass().getSimpleName();
    }

    public BaseInfo getData() {
	return data;
    }

    public void setData(BaseInfo data) {
	this.data = data;
    }

}
