/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BindBraceletResult.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.http.result
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-29 下午2:06:50
 * @version: V1.0
 */

package org.yousuowei.share.data.http.result;

import java.io.InputStream;

import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.yousuowei.share.data.entity.BaseInfo;

import android.content.Context;

import com.mipt.clientcommon.BaseResult;

/**
 * @ClassName: BindBraceletResult
 * @Description: 简单的status xml数据返回
 * @author: jie
 * @date: 2014-8-29 下午2:06:50
 */

public class SimpleXmlResult extends BaseResult {
    protected static final String RESP_STATUS = "status";
    protected static final String RESP_MSG = "msg";
    protected static final String RESP_VERSION = "version";
    protected static final String RESP_URL = "url";
    protected static final String RESP_LEVEL = "level";
    protected static final String RESP_SIZE = "size";
    protected static final String RESP_MD5 = "md5";
    protected static final String RESP_TIME = "time";
    protected static final String RESP_DESC = "desc";
    protected static final String RESP_VERSION_NAME = "versionName";
    protected static final String RESP_WEAR_LIST = "wearlist";
    protected static final String RESP_WEAR = "wear";
    protected static final String RESP_WEAR_PHONE = "wearPhone";
    protected static final String RESP_IMEI = "imei";
    protected static final String RESP_TYPE = "type";
    protected static final String RESP_APP_PHONE = "appPhone";
    protected static final String RESP_TASK_LIST = "tasklist";
    protected static final String RESP_TASK = "task";
    protected static final String RESP_NAME = "name";
    protected static final String RESP_ID = "id";
    protected static final String RESP_COME_TYPE = "comeType";
    protected static final String RESP_VOICE_FILE = "voiceFile";
    protected static final String RESP_ENABLE = "enable";
    protected static final String RESP_WEEK_INFO = "weekinfo";
    protected static final String RESP_LOCATION_LIST = "locationlist";
    protected static final String RESP_LOCATION = "location";
    protected static final String RESP_LONGITUDE = "jingdu";
    protected static final String RESP_LATITUDE = "weidu";
    protected static final String RESP_INFO = "info";

    // data
    private BaseInfo xmlInfo;

    public SimpleXmlResult(Context context) {
	super(context);
    }

    // <?xml version="1.0" encoding="UTF-8"?>
    // <result>
    // <status></status>状态，0表示处理成功，1代表处理失败
    // <msg></msg>
    // </result>

    @Override
    protected boolean parseResponse(InputStream is) throws Exception {
	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	factory.setNamespaceAware(true);
	XmlPullParser xpp = factory.newPullParser();
	xpp.setInput(is, HTTP.UTF_8);
	int eventType = xpp.getEventType();
	String tag = "";
	while (eventType != XmlPullParser.END_DOCUMENT) {
	    if (eventType == XmlPullParser.START_TAG) {
		tag = xpp.getName();
		if (tag.equals(RESP_STATUS)) {
		    String status = xpp.nextText();
		    if (status == null || Integer.valueOf(status) != 0) {
			extractMsg(xpp);
			return false;
		    }
		}
	    }
	    eventType = xpp.next();
	}
	return true;
    }

    @Override
    protected void extractMsg(XmlPullParser parser) throws Exception {
	int eventType = parser.getEventType();
	String tag = "";
	while (eventType != XmlPullParser.END_DOCUMENT) {
	    if (eventType == XmlPullParser.START_TAG) {
		tag = parser.getName();
		if (tag.equals(RESP_MSG)) {
		    msg = parser.nextText();
		}
	    }
	    eventType = parser.next();
	}
    }

    protected String getLogTag() {
	return this.getClass().getSimpleName();
    }

    public BaseInfo getXmlInfo() {
	return xmlInfo;
    }

    public void setXmlInfo(BaseInfo xmlInfo) {
	this.xmlInfo = xmlInfo;
    }
}
