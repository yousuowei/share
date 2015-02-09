/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SmsMsg.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.entity
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:36:22
 * @version: V1.0
 */

package org.yousuowei.share.data.entity;

import org.yousuowei.share.common.Constants;
import org.yousuowei.share.utils.StringUtil;

import android.text.TextUtils;

/**
 * @ClassName: SmsMsg
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:36:22
 */

public class SmsMsgInfo extends BaseInfo {

    private static final long serialVersionUID = 1L;
    public String phoneNum;
    public String head;
    public String paramStr;

    public SmsMsgInfo(String head, String paramStr) {
	this(null, head, paramStr);
    }

    public SmsMsgInfo(String phoneNum, String head, String paramStr) {
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
	    throw new NullPointerException("SmsMsg's paramStr can't be null!");
	}
	return StringUtil
		.appendStr(head, Constants.TAG_SPLIT_SMS_MSG, paramStr);
    }

}
