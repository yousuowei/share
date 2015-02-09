/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: Constants.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.common
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 上午10:15:25
 * @version: V1.0
 */

package org.yousuowei.share.common;

import android.content.Context;
import android.text.TextUtils;

import com.mipt.clientcommon.Prefs;

/**
 * @ClassName: Constants
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 上午10:15:25
 */

public class HttpConstants {

    private final static String HTTP_HOST_LOCAL = "http://192.168.52.17:9999/";
    private final static String HTTP_HOST = "http://yousuowei.com/";

    public static String fixUrl(Context context, String url) {
	if (TextUtils.isEmpty(url)) {
	    return url;
	}
	if (url.contains("http")) {
	    return url;
	} else {
	    String host = getRequestHost(context);
	    String ret = new StringBuilder()
		    .append(String.format(Constants.SCHEMA_HTTP, host))
		    .append(url).toString();
	    return ret;
	}
    }

    public static String getRequestHost(Context context) {
	String host = null;
	if (Constants.TEST) {
	    host = HTTP_HOST_LOCAL;
	} else {
	    host = (String) Prefs.getInstance(context).get(Prefs.TYPE_STRING,
		    PrefConstants.PREF_HOST, HttpConstants.HTTP_HOST);
	}
	return host;
    }

}
