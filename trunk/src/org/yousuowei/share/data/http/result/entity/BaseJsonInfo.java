/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BaseHttpInfo.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.entity
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:35:54
 * @version: V1.0
 */

package org.yousuowei.share.data.http.result.entity;

import org.yousuowei.share.data.entity.BaseInfo;

/**
 * @ClassName: BaseHttpInfo
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:35:54
 */

public class BaseJsonInfo<T> extends BaseInfo {

    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */

    private static final long serialVersionUID = 1L;
    public int status;
    public String msg;
    private T data;

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public String getMsg() {
	return msg;
    }

    public void setMsg(String msg) {
	this.msg = msg;
    }

    public T getData() {
	return data;
    }

    public void setData(T data) {
	this.data = data;
    }

}
