/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: Utils.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-28 下午3:45:11
 * @version: V1.0
 */

package org.yousuowei.share.utils;

import java.lang.reflect.Field;

import org.yousuowei.share.common.CommonLog;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.telephony.TelephonyManager;

/**
 * @ClassName: Utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-28 下午3:45:11
 */

public class Utils {
    private final static String TAG = Utils.class.getName();

    public static void installApk(Context context, String apkPath) {
	Intent intent = new Intent(Intent.ACTION_VIEW);
	intent.setDataAndType(Uri.parse("file://" + apkPath),
		"application/vnd.android.package-archive");
	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	context.startActivity(intent);
    }

    public static String getImei(Context context) {
	String imei = ((TelephonyManager) context
		.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	return imei;
    }

    public static PackageInfo getPackageInfos(Context context) {
	PackageManager pm = context.getPackageManager();
	PackageInfo pi = null;
	try {
	    pi = pm.getPackageInfo(context.getPackageName(), 0);
	} catch (NameNotFoundException e) {
	    CommonLog.e(TAG, e);
	}
	return pi;
    }

    public static int getAppMaxMemory(Context ctx) {
	ActivityManager activityManager = (ActivityManager) ctx
		.getSystemService(Context.ACTIVITY_SERVICE);
	int maxMemory = activityManager.getMemoryClass();
	return maxMemory;
    }

    public static byte[] byteMerger(byte[] startBytes, byte[] endBytes) {
	if (null == startBytes && null == endBytes) {
	    return null;
	}
	if (null == startBytes) {
	    return endBytes;
	}
	if (null == endBytes) {
	    return startBytes;
	}
	byte[] mergeBytes = new byte[startBytes.length + endBytes.length];
	System.arraycopy(startBytes, 0, mergeBytes, 0, startBytes.length);
	System.arraycopy(endBytes, 0, mergeBytes, startBytes.length,
		endBytes.length);
	return mergeBytes;
    }

    public static Object copyProperties(Object source, Class<?> destClass) {
	if (null == source || null == destClass) {
	    throw new NullPointerException(
		    "copyProperties source or destClass can't be null!");
	}
	Object dest = null;
	Field[] fields = source.getClass().getDeclaredFields();
	Field field;
	try {
	    dest = destClass.newInstance();
	    for (Field f : fields) {
		f.setAccessible(true);
		try {
		    field = destClass.getDeclaredField(f.getName());
		    field.setAccessible(true);
		    field.set(dest, f.get(source));
		} catch (NoSuchFieldException e) {
		    CommonLog.e(TAG, e, "copyProperties");
		}
	    }
	} catch (InstantiationException e) {
	    CommonLog.e(TAG, e, "copyProperties");
	} catch (IllegalAccessException e) {
	    CommonLog.e(TAG, e, "copyProperties");
	}
	return dest;
    }
}
