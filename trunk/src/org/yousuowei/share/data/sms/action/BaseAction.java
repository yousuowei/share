/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BaseAction.java
 * @Prject: BeeVideo
 * @Package: cn.beevideo.weixin.http.actions
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-12 下午4:46:05
 * @version: V1.0
 */

package org.yousuowei.share.data.sms.action;

import org.yousuowei.share.data.sms.SMSConstant;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * @ClassName: BaseAction
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-12 下午4:46:05
 */

public abstract class BaseAction {

    protected Context context;
    private String[] params;
    private String fromPhone;

    public BaseAction(Context ctx, String[] params, String fromPhone) {
	this.setParams(params);
	this.context = ctx;
	this.setFromPhone(fromPhone);
    }

    protected abstract void execute();

    protected abstract boolean checkParams(String[] params);

    public boolean executeAction() {
	boolean isParamLicit = checkParams(params);
	boolean isActionExecute = false;
	if (isParamLicit) {
	    execute();
	    isActionExecute = true;
	}
	sendBroadcast();
	return isActionExecute;
    }

    private void sendBroadcast() {
	String action = getAction();
	if (TextUtils.isEmpty(action)) {
	    throw new NullPointerException("getAction is null!");
	}
	Intent intent = new Intent();
	intent.setAction(action);
	intent.putExtra(SMSConstant.SMS_EXTRA_FROM_PHONE, fromPhone);
	intent.putExtra(SMSConstant.SMS_EXTRA_PARAMS, params);
	context.sendBroadcast(intent);
    }

    protected String getAction() {
	return SMSConstant.SMS_ACTION_RECEIVER;
    }

    public String[] getParams() {
	return params;
    }

    public void setParams(String[] params) {
	this.params = params;
    }

    public String getFromPhone() {
	return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
	this.fromPhone = fromPhone;
    }

    protected String getLogTag() {
	return this.getClass().getName();
    }

}
