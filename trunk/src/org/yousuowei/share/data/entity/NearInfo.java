/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: SmsMsg.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.entity
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:36:22
 * @version: V1.0
 */

package org.yousuowei.share.data.entity;

import android.net.wifi.WifiInfo;

/**
 * @ClassName: SmsMsg
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 上午11:36:22
 */

public class NearInfo extends BaseInfo {

    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */

    private static final long serialVersionUID = 1L;

    public NearInfo(String ssid) {
	this.ssid = ssid;
    }

    public String ssid;
    public String name;
    public String ip;
    public String port;

    public WifiInfo wifiInfo;

}
