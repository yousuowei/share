/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: Api.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.common
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:40:52
 * @version: V1.0
 */

package org.yousuowei.share.common;

import org.yousuowei.share.R;
import org.yousuowei.share.ui.aci.AboutUsAci;
import org.yousuowei.share.ui.aci.MainAci;
import org.yousuowei.share.ui.aci.RoomBuildAci;
import org.yousuowei.share.ui.aci.RoomClientAci;
import org.yousuowei.share.ui.aci.RoomServerAci;
import org.yousuowei.share.ui.aci.SettingShareAci;
import org.yousuowei.share.ui.aci.UpgradeVersionAci;

import android.content.Context;
import android.content.Intent;

/**
 * @ClassName: Api
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:40:52
 */

public class Api {
    // private final static String TAG = Api.class.getName();

    public static void startMainAci(Context context) {
	Intent intent = new Intent(context, MainAci.class);
	context.startActivity(intent);
    }

    public static void startUpgradeVersionAci(Context context, String title) {
	Intent intent = new Intent(context, UpgradeVersionAci.class);
	intent.putExtra(Constants.INTENT_EXTRA_KEY_ACI_TITLE, title);
	context.startActivity(intent);
    }

    public static void startSettingShareAci(Context context, String title) {
	Intent intent = new Intent(context, SettingShareAci.class);
	intent.putExtra(Constants.INTENT_EXTRA_KEY_ACI_TITLE, title);
	context.startActivity(intent);
    }

    public static void startAboutUsAci(Context context, String title) {
	Intent intent = new Intent(context, AboutUsAci.class);
	intent.putExtra(Constants.INTENT_EXTRA_KEY_ACI_TITLE, title);
	context.startActivity(intent);
    }

    public static void startRoomBuildAci(Context context) {
	Intent intent = new Intent(context, RoomBuildAci.class);
	String title = context.getResources().getString(
		R.string.title_room_build);
	intent.putExtra(Constants.INTENT_EXTRA_KEY_ACI_TITLE, title);
	context.startActivity(intent);
    }

    public static void startRoomClientAci(Context context) {
	Intent intent = new Intent(context, RoomClientAci.class);
	String title = context.getResources().getString(
		R.string.title_room_info);
	intent.putExtra(Constants.INTENT_EXTRA_KEY_ACI_TITLE, title);
	context.startActivity(intent);
    }

    public static void startRoomServerAci(Context context) {
	Intent intent = new Intent(context, RoomServerAci.class);
	String title = context.getResources().getString(
		R.string.title_room_server);
	intent.putExtra(Constants.INTENT_EXTRA_KEY_ACI_TITLE, title);
	context.startActivity(intent);
    }

}
