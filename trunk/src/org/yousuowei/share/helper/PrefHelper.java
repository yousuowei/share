/**
 * @author Brin.Liu
 * @email liubilin@gmail.com
 * @create 2012-6-1
 *          <p>
 */
package org.yousuowei.share.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefHelper {
    private SharedPreferences sp;
    private Editor edit;
    private Context context;
    private static PrefHelper instance;

    private PrefHelper() {
    }

    public static PrefHelper getInstance(Context ctx) {
	synchronized (PrefHelper.class) {
	    if (instance == null) {
		instance = new PrefHelper();
		instance.context = ctx;
		instance.init();
	    }
	}
	return instance;
    }

    private void init() {
	sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void putString(String key, String value) {
	synchronized (sp) {
	    edit = sp.edit();
	    edit.putString(key, value);
	    edit.commit();
	}
    }

    private final static String PREF_KEY_ROOM_NAME = "pre_key_room_name";// 房间名称

    public void saveRoomName(String roomName) {
	putString(PREF_KEY_ROOM_NAME, roomName);
    }

    public String getRoomName() {
	synchronized (sp) {
	    return sp.getString(PREF_KEY_ROOM_NAME, null);
	}
    }
}
