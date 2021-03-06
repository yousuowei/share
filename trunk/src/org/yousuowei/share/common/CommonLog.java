/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: CommonLog.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.common
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-29 下午2:12:10
 * @version: V1.0
 */

package org.yousuowei.share.common;


import org.yousuowei.share.BuildConfig;

import android.util.Log;

/**
 * @ClassName: CommonLog
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-29 下午2:12:10
 */

public class CommonLog {

    public static void d(String tag, Object... msgs) {
	if (BuildConfig.DEBUG) {
	    String str = appendStrs(msgs);
	    Log.d(tag, str);
	}
    }

    public static void i(String tag, Object... msgs) {
	if (BuildConfig.DEBUG) {
	    String str = appendStrs(msgs);
	    Log.i(tag, str);
	}
    }

    public static void e(String tag, Object... msgs) {
	if (BuildConfig.DEBUG) {
	    String str = appendStrs(msgs);
	    Log.e(tag, str);
	}
    }

    public static void e(String tag, Throwable e, Object... msgs) {
	if (BuildConfig.DEBUG) {
	    String str = appendStrs(msgs);
	    Log.e(tag, str, e);
	}
    }

    private static String appendStrs(Object... msgs) {
	if (null == msgs) {
	    return "";
	}
	StringBuilder str = new StringBuilder();
	for (Object msg : msgs) {
	    if (null != msg) {
		str.append(msg.toString());
	    }
	}
	return str.toString();
    }

}
