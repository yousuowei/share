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

package org.yousuowei.share.service.action;

import android.content.Context;

/**
 * @ClassName: BaseAction
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-12 下午4:46:05
 */

public abstract class BaseAction {

    private String[] params;
    private String fromPhone;
    protected Context context;

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
	return isActionExecute;
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
