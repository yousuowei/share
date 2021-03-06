/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: VoicePlayerControl.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.view
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-5 下午4:21:39
 * @version: V1.0
 */

package org.yousuowei.share.ui.view;

import java.io.IOException;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Timer;
import org.yousuowei.share.common.Timer.VoiceTimeListener;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;

/**
 * @ClassName: VoicePlayerControl
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-5 下午4:21:39
 */

public class VoicePlayerControl {
    private final static String TAG = VoicePlayerControl.class.getName();

    private MediaPlayer mediaPlayer;
    private static Timer timer;
    private static RecordTimeListener timeListener;
    private VoiceListener voiceListener;

    private static VoicePlayerControl control;

    private VoicePlayerControl() {
    };

    public static VoicePlayerControl getInstance() {
	synchronized (RecordControl.class) {
	    if (null == control) {
		control = new VoicePlayerControl();
		timeListener = new RecordTimeListener();
		timer = new Timer(timeListener);
	    }
	    return control;
	}
    }

    public void startPlayer(String path) {
	if (null == mediaPlayer) {
	    mediaPlayer = new MediaPlayer();
	} else {
	    mediaPlayer.reset();
	}
	try {
	    mediaPlayer.setOnInfoListener(infoListener);
	    mediaPlayer.setOnCompletionListener(playCompletionListener);
	    mediaPlayer.setOnPreparedListener(preparedListener);
	    mediaPlayer.setDataSource(path);
	    mediaPlayer.prepareAsync();
	} catch (IllegalArgumentException e) {
	    CommonLog.e(TAG, "startPlayer", e);
	} catch (SecurityException e) {
	    CommonLog.e(TAG, "startPlayer", e);
	} catch (IllegalStateException e) {
	    CommonLog.e(TAG, "startPlayer", e);
	} catch (IOException e) {
	    CommonLog.e(TAG, "startPlayer", e);
	}
	CommonLog.d(TAG, "startPlayer path:", path);
    }

    private OnPreparedListener preparedListener = new OnPreparedListener() {
	@Override
	public void onPrepared(MediaPlayer mp) {
	    mediaPlayer.start();
	    timer.start();
	}
    };

    public void stopPlayer() {
	if (null != mediaPlayer) {
	    mediaPlayer.stop();
	    mediaPlayer.release();
	    mediaPlayer = null;
	    timer.stop();
	}
    }

    public void canclePlayer() {
	if (null != mediaPlayer) {
	    mediaPlayer.stop();
	    mediaPlayer.release();
	    mediaPlayer = null;
	    timer.stop();
	}
    }

    public boolean isPlaying() {
	if (null != mediaPlayer) {
	    CommonLog.d(TAG, "isPlaying:", mediaPlayer.isPlaying(),
		    " islooping:", mediaPlayer.isLooping());
	}
	if (null != mediaPlayer && mediaPlayer.isPlaying()) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean startOrStopPlaying(String path, VoiceListener voiceListener) {
	CommonLog.d(TAG, "startOrStopPlaying path:", path, " isPlaying():",
		isPlaying());
	if (!isPlaying()) {
	    startPlayer(path);
	    control.voiceListener = voiceListener;
	    return true;
	} else {
	    stopPlayer();
	    return false;
	}
    }

    private OnCompletionListener playCompletionListener = new OnCompletionListener() {
	@Override
	public void onCompletion(MediaPlayer mp) {
	    timer.stop();
	}
    };

    private OnInfoListener infoListener = new OnInfoListener() {
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
	    CommonLog.d(TAG, "infoListener what:", what, " extra:", extra);
	    return false;
	}
    };

    public static interface VoiceListener {
	public void playing(int secondes);

	public void finished(int secondes);
    }

    private static class RecordTimeListener implements VoiceTimeListener {
	@Override
	public void timeRefresh(int secondes) {
	    if (null != control.voiceListener) {
		control.voiceListener.playing(secondes);
	    }
	}

	@Override
	public void timeStop(int secondes) {
	    if (null != control.voiceListener) {
		control.voiceListener.finished(secondes);
	    }
	}
    };

}
