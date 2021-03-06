/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: StringUtil.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-5 下午3:51:46
 * @version: V1.0
 */

package org.yousuowei.share.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.yousuowei.share.common.CommonLog;

/**
 * @ClassName: StringUtil
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-5 下午3:51:46
 */

@SuppressLint("SimpleDateFormat")
public class StringUtil {
    private final static String TAG = StringUtil.class.getName();

    public static String appendStr(Object... strs) {
	if (null == strs) {
	    return "";
	}
	StringBuilder str = new StringBuilder();
	for (Object demp : strs) {
	    str.append(demp);
	}
	return str.toString();
    }

    public static String appendStr(char splitTag, Object... strs) {
	if (null == strs) {
	    return "";
	}
	StringBuilder str = new StringBuilder();
	for (Object demp : strs) {
	    str.append(demp);
	    str.append(splitTag);
	}
	return str.toString();
    }

    public static String intConvertTime(int hour, int minut) {
	StringBuilder time = new StringBuilder();
	time.append(hour / 10 % 10);
	time.append(hour % 10);
	time.append(":");
	time.append(minut / 10 % 10);
	time.append(minut % 10);
	return time.toString();
    }

    @SuppressWarnings("deprecation")
    public static int timeGetHour(String timeStr) {
	if (TextUtils.isEmpty(timeStr)) {
	    return 0;
	}
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	try {
	    Date time = format.parse(timeStr);
	    return time.getHours();
	} catch (ParseException e) {
	    CommonLog.e(TAG, e);
	}
	return 0;
    }

    @SuppressWarnings("deprecation")
    public static int timeGetMinut(String timeStr) {
	if (TextUtils.isEmpty(timeStr)) {
	    return 0;
	}
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	try {
	    Date time = format.parse(timeStr);
	    return time.getMinutes();
	} catch (ParseException e) {
	    CommonLog.e(TAG, e);
	}
	return 0;
    }

}
