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

import org.yousuowei.share.data.entity.NearInfo;
import org.yousuowei.share.ui.MainApplication;

import android.content.Context;

/**
 * @ClassName: Constants
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 上午10:15:25
 */

public class CommonData {
    public static void changeRoomInfo(Context context, NearInfo roomInfo) {
	((MainApplication) context.getApplicationContext())
		.setRoomInfo(roomInfo);
    }

    public static NearInfo getRoomInfo(Context context) {
	return ((MainApplication) context.getApplicationContext())
		.getRoomInfo();
    }
}
