/**
 *
 * Copyright borqs. All Rights Reserved.
 *
 */
package org.yousuowei.share.task;

import java.io.Serializable;

import android.content.Context;
import android.os.Bundle;

/**
 * 
 * 
 * @author Jack
 * @date 2009-8-20
 * @since 1.6
 */
public abstract class AbstractTask implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    public final static int T_RESULT_OK = 200;
    public final static int T_RESULT_FAILED = -1;

    protected final static String LOG = AbstractTask.class.getName();

    public static final String S_status = "status";

    protected Context mContext = null;
    protected TaskCallbackIfc mCallback = null;

    private boolean mIsCanceled = false;

    protected int nErrorCode = T_RESULT_OK;
    // protected String sErrorMsg = "";
    public int result = T_RESULT_OK;

    int m_nTaskGroupId = TaskEngine.DEFAULT_TASKGROUPID;
    int m_nPriority = TaskEngine.PRIORITY_HIGHEST;

    public AbstractTask(Context mContext, TaskCallbackIfc mCallback) {
	this.mContext = mContext;
	this.mCallback = mCallback;
    }

    public abstract void execute();

    protected Bundle[] doGetResultDataArray() {
	return null;
    };

    protected int relateId = -1;

    public Bundle getResultAsBundle() {
	// Bundle ret = new Bundle();
	//
	// ret.putInt(SN.RET_ERRCODE, nErrorCode);
	// ret.putString(SN.RET_ERRMSG, sErrorMsg);
	//
	// Bundle da[] = doGetResultDataArray();
	// if(null != da) {
	// ret.putParcelableArray(SN.RET_DATA_ARRAY, da);
	// }
	//
	// return ret;
	return null;
    }

    protected synchronized void doCallbackFinish() {
	if (null != mCallback && !isCanceled()) {
	    mCallback.call(this);
	}
    }

    public void callbackFinish() {
	doCallbackFinish();
    }

    public synchronized void cancel() {
	mIsCanceled = true;
    }

    public synchronized boolean isCanceled() {
	return mIsCanceled;
    }

    // protected void setError(int c, String m) {
    // nErrorCode = c;
    // sErrorMsg = m;
    // }

    protected void setError(int c) {
	nErrorCode = c;
    }

    public int getErrorCode() {
	return nErrorCode;
    }

    // public String getErrorMsg() {
    // return sErrorMsg;
    // }

    public void setTaskGroupId(int id) {
	this.m_nTaskGroupId = id;
    }

    public int getTaskGroupId() {
	return m_nTaskGroupId;
    }

    public void setPriority(int priority) {
	this.m_nPriority = priority;
    }

    public int getPriority() {
	return this.m_nPriority;
    }

    public void setResult(int result) {
        this.result = result;
    }
    
}
