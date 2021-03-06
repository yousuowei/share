/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: Timer.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.common
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:40:52
 * @version: V1.0
 */

package org.yousuowei.share.common;

import android.os.Handler;
import android.os.Message;

/**
 * @ClassName: Timer
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:40:52
 */

public class Timer {
    // private final static String TAG = Timer.class.getName();

    private long delayMilliSecondes;
    private int secondes;
    private int timeNum;
    // control
    private VoiceTimeListener voiceTimeListener;

    public Timer(VoiceTimeListener voiceTimeListener) {
	this(1000, voiceTimeListener);
    }

    public Timer(long delayMilliSecondes, VoiceTimeListener voiceTimeListener) {
	if (null == voiceTimeListener) {
	    throw new NullPointerException("voiceTimeListener can't be null!");
	}
	if (delayMilliSecondes == 0l) {
	    delayMilliSecondes = 1 * 1000;
	}
	timeNum = (int) (delayMilliSecondes / 1000);
	this.delayMilliSecondes = delayMilliSecondes;
	this.voiceTimeListener = voiceTimeListener;

    }

    public void start() {
	secondes = 0;
	Message sendMsg = mHandler.obtainMessage(HW_TIME_REFRESH);
	mHandler.sendMessageDelayed(sendMsg, delayMilliSecondes);
    }

    public void stop() {
	if (null != voiceTimeListener) {
	    voiceTimeListener.timeStop(secondes);
	}
	mHandler.removeMessages(HW_TIME_REFRESH);
    }

    public static interface VoiceTimeListener {
	public void timeRefresh(int secondes);

	public void timeStop(int secondes);
    }

    private final static int HW_TIME_REFRESH = 0;
    private Handler mHandler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
	    if (msg.what == HW_TIME_REFRESH) {
		secondes += timeNum;
		voiceTimeListener.timeRefresh(secondes);
		Message sendMsg = mHandler.obtainMessage(HW_TIME_REFRESH);
		mHandler.sendMessageDelayed(sendMsg, delayMilliSecondes);
	    }
	};
    };

}
