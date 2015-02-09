/**
 * Copyright © 2014 69. All rights reserved.
 * @Title: WifiUtil.java
 * @Prject: BeeVideo
 * @Package: cn.beevideo.utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-12 上午11:22:01
 * @version: V1.0
 */

package org.yousuowei.share.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.yousuowei.share.common.CommonLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

/**
 * @ClassName: WifiUtil
 * @Description: wifi工具类
 * @author: jie
 * @date: 2014-8-12 上午11:22:01
 */

public class WifiUtil {
    private final static String TAG = WifiUtil.class.getName();

    private static WifiUtil instance;
    private static WifiManager wifiManager;
    private Context context;

    private OnWifiChangeListener wifiChangeListener;

    private WifiReceiver wifiReceiver;
    private Mhandler handler;
    private boolean isRegistedReceiver = false;

    private WifiUtil(Context ctx) {
	context = ctx;
	wifiManager = (WifiManager) context
		.getSystemService(Context.WIFI_SERVICE);
	registBroadcast();
	handler = new Mhandler();
    }

    public static WifiUtil getInstance(Context context) {
	if (null == instance) {
	    instance = new WifiUtil(context);
	}
	return instance;
    }

    private void registBroadcast() {
	wifiReceiver = new WifiReceiver();
	final IntentFilter filters = new IntentFilter();
	filters.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
	filters.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
	filters.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
	context.registerReceiver(wifiReceiver, filters);
	isRegistedReceiver = true;
    }

    public boolean openWifiAp(String ssid, String psd) {
	CommonLog.d(TAG, "openWifiAp ssid:", ssid, " psd:", psd);
	WifiConfiguration apConfig = createWifiConfiguration(ssid, psd,
		WifiConnectType.NONE);
	// wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
	wifiManager.setWifiEnabled(false);
	Method method = null;
	try {
	    method = wifiManager.getClass().getMethod("setWifiApEnabled",
		    WifiConfiguration.class, Boolean.TYPE);
	    return (Boolean) method.invoke(wifiManager, apConfig, true);
	} catch (NoSuchMethodException e) {
	    CommonLog.e("", "closeWifiAp", e);
	} catch (IllegalArgumentException e) {
	    CommonLog.e("", "closeWifiAp", e);
	} catch (IllegalAccessException e) {
	    CommonLog.e("", "closeWifiAp", e);
	} catch (InvocationTargetException e) {
	    CommonLog.e("", "closeWifiAp", e);
	}
	return false;
    }

    public boolean closeWifiAp() {
	CommonLog.d(TAG, "closeWifiAp");
	Method method = null;
	try {
	    method = wifiManager.getClass().getMethod("setWifiApEnabled",
		    WifiConfiguration.class, Boolean.TYPE);
	    // 返回热点打开状态
	    return (Boolean) method.invoke(wifiManager, null, false);
	} catch (NoSuchMethodException e) {
	    CommonLog.e("", "closeWifiAp", e);
	} catch (IllegalArgumentException e) {
	    CommonLog.e("", "closeWifiAp", e);
	} catch (IllegalAccessException e) {
	    CommonLog.e("", "closeWifiAp", e);
	} catch (InvocationTargetException e) {
	    CommonLog.e("", "closeWifiAp", e);
	}
	return false;
    }

    public boolean searchWifi() {
	CommonLog.d(TAG, "searchWifi");
	if (wifiManager.isWifiEnabled()) {
	    wifiManager.setWifiEnabled(true);
	}
	return wifiManager.startScan();
    }

    public boolean connectWifi(String ssid, String psd) {
	CommonLog.d(TAG, "connectWifi ssid:", ssid, " psd:", psd);
	WifiConfiguration config = createWifiConfiguration(ssid, psd,
		WifiConnectType.NONE);
	int netId = wifiManager.addNetwork(config);
	return wifiManager.enableNetwork(netId, false);
    }

    public boolean disconnectWifi() {
	CommonLog.d(TAG, "disconnectWifi ");
	return wifiManager.disconnect();
    }

    public void release() {
	if (isRegistedReceiver) {
	    context.unregisterReceiver(wifiReceiver);
	    isRegistedReceiver = false;
	}
    }

    public static interface OnWifiChangeListener {
	public void onNetWorkChange(boolean isConnected);

	public void onStateChange(boolean isEnable);

	public void onScanResult(List<ScanResult> wifiList);
    }

    private class WifiReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
	    String action = intent.getAction();
	    if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
		handler.sendEmptyMessage(HW_SCAN_RESULTS);
	    } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
		handler.sendEmptyMessage(HW_NETWORK_STATE_CHANGED);
	    } else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
		handler.sendEmptyMessage(HW_WIFI_STATE_CHANGED);
	    }
	}
    }

    private final static int HW_SCAN_RESULTS = 0;
    private final static int HW_NETWORK_STATE_CHANGED = 0;
    private final static int HW_WIFI_STATE_CHANGED = 0;

    private class Mhandler extends Handler {
	@Override
	public void handleMessage(Message msg) {
	    if (msg.what == HW_SCAN_RESULTS) {
		List<ScanResult> wifiList = wifiManager.getScanResults();
		wifiChangeListener.onScanResult(wifiList);
	    } else if (msg.what == HW_NETWORK_STATE_CHANGED) {
		wifiChangeListener.onNetWorkChange(NetworkUtil
			.isNetWorkConnected(context));
	    } else if (msg.what == HW_WIFI_STATE_CHANGED) {
		wifiChangeListener.onStateChange(wifiManager.isWifiEnabled());
	    }
	    super.handleMessage(msg);
	}
    }

    public enum WifiConnectType {
	NONE, IEEE8021XEAP, WEP, WPA, WPA2
    }

    public static WifiConfiguration createWifiConfiguration(String ssid,
	    String password, WifiConnectType type) {
	WifiConfiguration newWifiConfiguration = new WifiConfiguration();
	newWifiConfiguration.allowedAuthAlgorithms.clear();
	newWifiConfiguration.allowedGroupCiphers.clear();
	newWifiConfiguration.allowedKeyManagement.clear();
	newWifiConfiguration.allowedPairwiseCiphers.clear();
	newWifiConfiguration.allowedProtocols.clear();
	newWifiConfiguration.SSID = "\"" + ssid + "\"";
	switch (type) {
	case NONE:
	    newWifiConfiguration.allowedKeyManagement
		    .set(WifiConfiguration.KeyMgmt.NONE);
	    break;
	case IEEE8021XEAP:
	    break;
	case WEP:
	    break;
	case WPA:
	    newWifiConfiguration.preSharedKey = "\"" + password + "\"";
	    newWifiConfiguration.hiddenSSID = true;
	    newWifiConfiguration.allowedAuthAlgorithms
		    .set(WifiConfiguration.AuthAlgorithm.OPEN);
	    newWifiConfiguration.allowedGroupCiphers
		    .set(WifiConfiguration.GroupCipher.TKIP);
	    newWifiConfiguration.allowedKeyManagement
		    .set(WifiConfiguration.KeyMgmt.WPA_PSK);
	    newWifiConfiguration.allowedPairwiseCiphers
		    .set(WifiConfiguration.PairwiseCipher.TKIP);
	    newWifiConfiguration.allowedProtocols
		    .set(WifiConfiguration.Protocol.WPA);
	    newWifiConfiguration.status = WifiConfiguration.Status.ENABLED;
	    break;
	case WPA2:
	    newWifiConfiguration.preSharedKey = "\"" + password + "\"";
	    newWifiConfiguration.allowedAuthAlgorithms
		    .set(WifiConfiguration.AuthAlgorithm.OPEN);
	    newWifiConfiguration.allowedGroupCiphers
		    .set(WifiConfiguration.GroupCipher.TKIP);
	    newWifiConfiguration.allowedGroupCiphers
		    .set(WifiConfiguration.GroupCipher.CCMP);
	    newWifiConfiguration.allowedKeyManagement
		    .set(WifiConfiguration.KeyMgmt.WPA_PSK);
	    newWifiConfiguration.allowedPairwiseCiphers
		    .set(WifiConfiguration.PairwiseCipher.TKIP);
	    newWifiConfiguration.allowedPairwiseCiphers
		    .set(WifiConfiguration.PairwiseCipher.CCMP);
	    newWifiConfiguration.allowedProtocols
		    .set(WifiConfiguration.Protocol.RSN);
	    newWifiConfiguration.status = WifiConfiguration.Status.ENABLED;
	    break;
	default:
	    return null;
	}
	return newWifiConfiguration;
    }

    public void setOnWifiChangeListener(OnWifiChangeListener wifiChangeListener) {
	instance.wifiChangeListener = wifiChangeListener;
    }
}
