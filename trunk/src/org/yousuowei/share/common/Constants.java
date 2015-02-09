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

import org.yousuowei.share.utils.StringUtil;

/**
 * @ClassName: Constants
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 上午10:15:25
 */

public class Constants {
    public final static boolean CHECK_VERSION = true;
    public final static boolean TEST = true;

    public final static String DEFAULT_VERIFY_SSID = "「^_^」";
    public final static String DEFAULT_VERIFY_PASSWORD = "jie201314";
    public final static String DEFAULT_SERVER_IP = "192.168.43.1";
    public final static int DEFAULT_SERVER_PORT = 2020;
    public final static String HTTP_69_HOST = StringUtil.appendStr("http://",
	    DEFAULT_SERVER_IP, "/", DEFAULT_SERVER_PORT);

    public final static int HW_SHOW_TOAST = 0;
    public final static int HW_SHOW_DIALOG = HW_SHOW_TOAST + 1;
    public final static int HW_HIDE_DIALOG = HW_SHOW_DIALOG + 1;
    public final static int HW_CHANGE_NEAR_LIST = HW_HIDE_DIALOG + 1;

    public final static String INTENT_EXTRA_KEY_ACI_TITLE = "intent_extra_key_aci_title";
    public final static String INTENT_EXTRA_KEY_COMMON_DIALOG_CONTENT = "intent_extra_key_common_dialog_content";

    public final static String TAG_SPLIT_SMS_MSG = ":";
    public final static String TAG_SPLIT_WRAP = "\n";
    public final static String ENCODING_FORMAT = "UTF-8";
    public final static String SCHEMA_HTTP = "http://%s";
}
