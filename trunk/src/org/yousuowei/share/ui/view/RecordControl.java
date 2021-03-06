/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: RecordControl.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.view
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-5 上午11:27:28
 * @version: V1.0
 */

package org.yousuowei.share.ui.view;

import java.io.File;
import java.io.IOException;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Timer;
import org.yousuowei.share.common.Timer.VoiceTimeListener;
import org.yousuowei.share.helper.FileHelper;
import org.yousuowei.share.utils.StringUtil;

import android.content.Context;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;

/**
 * @ClassName: RecordControl
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-5 上午11:27:28
 */

public class RecordControl {
    private final static String TAG = RecordControl.class.getName();

    private static RecordControl control;
    private MediaRecorder mRecorder;
    private static Context mContext;
    private RecordListener recordListener;
    private static Timer timer;
    private static RecordTimeListener timeListener;

    // data
    private String recordFilePath;
    private boolean isRecording;

    private RecordControl() {
    };

    public static RecordControl getInstance(Context context) {
	synchronized (RecordControl.class) {
	    if (null == control) {
		control = new RecordControl();
		timeListener = new RecordTimeListener();
		timer = new Timer(timeListener);
	    }
	    mContext = context;
	    return control;
	}
    }

    public void startRecord(OnInfoListener infoListener,
	    OnErrorListener errorListener) {
	CommonLog.d(TAG, "startRecord");
	if (null == mRecorder) {
	    mRecorder = new MediaRecorder();
	    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	    mRecorder.setOnInfoListener(infoListener);
	    mRecorder.setOnErrorListener(errorListener);
	} else {
	    mRecorder.reset();
	}
	setRecordFilePath(createRecordFilePath());
	mRecorder.setOutputFile(getRecordFilePath());
	isRecording = true;
	try {
	    mRecorder.prepare();
	} catch (IllegalStateException e) {
	    CommonLog.e(TAG, e);
	} catch (IOException e) {
	    CommonLog.e(TAG, e);
	}
	mRecorder.start();
	timer.start();
    }

    private String createRecordFilePath() {
	String dir = FileHelper.getTaskRecordDirPath(mContext);
	String filePath = StringUtil.appendStr(dir, System.currentTimeMillis(),
		".3gp");
	try {
	    new File(filePath).createNewFile();
	} catch (IOException e) {
	    CommonLog.e(TAG, e);
	}
	CommonLog.d(TAG, "createRecordFilePath filePath:", filePath);
	return filePath;
    }

    public void stopRecord() {
	CommonLog.d(TAG, "stopRecord");
	if (null != mRecorder) {
	    mRecorder.stop();
	    mRecorder.release();
	    mRecorder = null;
	}
	timer.stop();
	isRecording = false;
    }

    public void cancleRecord() {
	if (null != mRecorder) {
	    mRecorder.stop();
	    mRecorder.release();
	    mRecorder = null;
	}
	timer.stop();
	isRecording = false;
    }

    public boolean isRecording() {
	return isRecording;
    }

    public boolean startOrStopRecord(RecordListener recordListener) {
	CommonLog.d(TAG, "startOrStopRecord isRecording:", isRecording);
	if (!isRecording) {
	    startRecord(infoListener, errorListener);
	    this.recordListener = recordListener;
	    return true;
	} else {
	    stopRecord();
	    return false;
	}
    }

    public String getRecordFilePath() {
	return recordFilePath;
    }

    public void setRecordFilePath(String recordFilePath) {
	this.recordFilePath = recordFilePath;
    }

    private OnInfoListener infoListener = new OnInfoListener() {
	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
	    CommonLog.d(TAG, "infoListener what:", what, " extra:", extra);
	}
    };

    private OnErrorListener errorListener = new OnErrorListener() {
	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
	    CommonLog.d(TAG, "errorListener what:", what, " extra:", extra);
	}
    };

    public static interface RecordListener {
	public void recording(int secondes);

	public void finished(String filePath, int secondes);
    }

    private static class RecordTimeListener implements VoiceTimeListener {
	@Override
	public void timeRefresh(int secondes) {
	    if (null != control.recordListener) {
		control.recordListener.recording(secondes);
	    }
	}

	@Override
	public void timeStop(int secondes) {
	    if (null != control.recordListener) {
		control.recordListener.finished(control.recordFilePath,
			secondes);
	    }
	}
    };
}
