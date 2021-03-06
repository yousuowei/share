/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: NetworkUtil.java
 * @Prject: BeeVideo
 * @Package: cn.beevideo.utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-12 上午11:22:01
 * @version: V1.0
 */

package org.yousuowei.share.utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import org.yousuowei.share.common.CommonLog;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

/**
 * @ClassName: NetworkUtil
 * @Description: 网络工具类
 * @author: jie
 * @date: 2014-8-12 上午11:22:01
 */

public class NetworkUtil {
    private final static String TAG = NetworkUtil.class.getName();

    private final static String HOST_LOCAL = "127.0.0.1";

    /**
     * 检查当前网络是否通畅
     * 
     * @param ctx
     * @return
     * @author: jie
     * @date: 2014-8-12 上午11:23:38
     */
    public static boolean isNetWorkConnected(Context ctx) {
	ConnectivityManager connManager = (ConnectivityManager) ctx
		.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo info = connManager.getActiveNetworkInfo();

	boolean isConnnected = false;
	if (null != info && (info.isAvailable() || info.isConnected())) {
	    isConnnected = true;
	}
	return isConnnected;
    }

    /**
     * 获取本机网络ip
     * 
     * @return
     * @author: jie
     * @date: 2014-8-12 下午1:54:03
     */
    public static String getLocalRemoteIp() {
	Enumeration<NetworkInterface> allNetInterfaces = null;
	try {
	    allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	} catch (SocketException e) {
	    Log.e(TAG, "getLocalRemoteIp", e);
	}
	if (null == allNetInterfaces) {
	    return null;
	}

	InetAddress ip;
	NetworkInterface netInterface;
	Enumeration<InetAddress> addresses;
	while (allNetInterfaces.hasMoreElements()) {
	    netInterface = (NetworkInterface) allNetInterfaces.nextElement();

	    addresses = netInterface.getInetAddresses();
	    while (addresses.hasMoreElements()) {
		ip = (InetAddress) addresses.nextElement();
		if (!ip.isLoopbackAddress() && !ip.isLinkLocalAddress()) {
		    return ip.getHostAddress().toString();
		}
	    }
	}
	return null;
    }

    /**
     * 检查网络端口是否闲置
     * 
     * @param port
     * @return
     * @author: jie
     * @date: 2014-8-12 上午11:39:34
     */
    public static boolean isNetworkPortIdle(int port) {
	boolean isIdle = false;
	try {
	    InetAddress theAddress = InetAddress.getByName(HOST_LOCAL);
	    try {
		Socket theSocket = new Socket(theAddress, port);
		theSocket.close();
		theSocket = null;
		theAddress = null;
		isIdle = true;
	    } catch (IOException e) {
		Log.e(TAG, "isNetworkPortIdle", e);
	    } catch (Exception e) {
		Log.e(TAG, "isNetworkPortIdle", e);
	    }
	} catch (UnknownHostException e) {
	    Log.e(TAG, "isNetworkPortIdle", e);
	}
	return isIdle;
    }

    public static boolean openWifiAp(Context context, WifiConfiguration apConfig) {
	WifiManager wifiManager = (WifiManager) context
		.getSystemService(Context.WIFI_SERVICE);
	// wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
	wifiManager.setWifiEnabled(false);
	Method method = null;
	try {
	    method = wifiManager.getClass().getMethod("setWifiApEnabled",
		    WifiConfiguration.class, Boolean.TYPE);
	    // 返回热点打开状态
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

    public static boolean closeWifiAp(Context context) {
	WifiManager wifiManager = (WifiManager) context
		.getSystemService(Context.WIFI_SERVICE);
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

    private final static String WIFI_STATIC_IP = "192.168.0.101";
    private final static String WIFI_STATIC_NETMASK = "255.255.255.0";
    private final static String WIFI_STATIC_GATEWAY = "192.168.0.1";
    private final static String WIFI_STATIC_DNS1 = "192.168.0.1";
    private final static String WIFI_STATIC_DNS2 = "192.168.0.2";

    public static void setWifiStaticIp(Context context) {
	// WifiConfiguration wifiConfig = null;
	// WifiManager wifiManager = (WifiManager) context
	// .getSystemService(Context.WIFI_SERVICE);
	// WifiInfo connectionInfo = wifiManager.getConnectionInfo();
	// List<WifiConfiguration> configuredNetworks = wifiManager
	// .getConfiguredNetworks();
	// for (WifiConfiguration conf : configuredNetworks) {
	// if (conf.networkId == connectionInfo.getNetworkId()) {
	// wifiConfig = conf;
	// break;
	// }
	// }
	WifiConfiguration wifiConfig = new WifiConfiguration();

	if (android.os.Build.VERSION.SDK_INT < 11) { // 如果是android2.x版本的话
	    setWifistaticIpWithAndroidVersion2(context);
	} else { // 如果是android3.x版本及以上的话
	    setWifistaticIpWithAndroidUpVersion3(context, wifiConfig);
	}
    }

    private static void setWifistaticIpWithAndroidUpVersion3(Context context,
	    WifiConfiguration wifiConfig) {
	try {
	    setIpAssignment("STATIC", wifiConfig);
	    setIpAddress(InetAddress.getByName(WIFI_STATIC_IP), 24, wifiConfig);
	    setGateway(InetAddress.getByName(WIFI_STATIC_GATEWAY), wifiConfig);
	    setDNS(InetAddress.getByName(WIFI_STATIC_DNS1), wifiConfig);
	    WifiManager wifiManager = (WifiManager) context
		    .getSystemService(Context.WIFI_SERVICE);
	    wifiManager.updateNetwork(wifiConfig);
	} catch (Exception e) {
	    CommonLog.e(TAG, "setIpWithTfiStaticIp failed!", e);
	}
    }

    @SuppressWarnings("deprecation")
    private static void setWifistaticIpWithAndroidVersion2(Context context) {
	ContentResolver ctRes = context.getContentResolver();
	Settings.System.putInt(ctRes, Settings.System.WIFI_USE_STATIC_IP, 1);
	Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_IP,
		WIFI_STATIC_IP);
	Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_NETMASK,
		WIFI_STATIC_NETMASK);
	Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_GATEWAY,
		WIFI_STATIC_GATEWAY);
	Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_DNS1,
		WIFI_STATIC_DNS1);
	Settings.System.putString(ctRes, Settings.System.WIFI_STATIC_DNS2,
		WIFI_STATIC_DNS2);
    }

    private static void setIpAssignment(String assign,
	    WifiConfiguration wifiConf) throws SecurityException,
	    IllegalArgumentException, NoSuchFieldException,
	    IllegalAccessException {
	setEnumField(wifiConf, assign, "ipAssignment");
    }

    @SuppressWarnings("unchecked")
    private static void setIpAddress(InetAddress addr, int prefixLength,
	    WifiConfiguration wifiConf) throws SecurityException,
	    IllegalArgumentException, NoSuchFieldException,
	    IllegalAccessException, NoSuchMethodException,
	    ClassNotFoundException, InstantiationException,
	    InvocationTargetException {
	Object linkProperties = getField(wifiConf, "linkProperties");
	if (linkProperties == null)
	    return;
	Class<?> laClass = Class.forName("android.net.LinkAddress");
	Constructor<?> laConstructor = laClass.getConstructor(new Class[] {
		InetAddress.class, int.class });
	Object linkAddress = laConstructor.newInstance(addr, prefixLength);

	ArrayList<Object> mLinkAddresses = (ArrayList<Object>) getDeclaredField(
		linkProperties, "mLinkAddresses");
	mLinkAddresses.clear();
	mLinkAddresses.add(linkAddress);
    }

    @SuppressWarnings("unchecked")
    private static void setGateway(InetAddress gateway,
	    WifiConfiguration wifiConf) throws SecurityException,
	    IllegalArgumentException, NoSuchFieldException,
	    IllegalAccessException, ClassNotFoundException,
	    NoSuchMethodException, InstantiationException,
	    InvocationTargetException {
	Object linkProperties = getField(wifiConf, "linkProperties");
	if (linkProperties == null)
	    return;

	if (android.os.Build.VERSION.SDK_INT >= 14) { // android4.x版本
	    Class<?> routeInfoClass = Class.forName("android.net.RouteInfo");
	    Constructor<?> routeInfoConstructor = routeInfoClass
		    .getConstructor(new Class[] { InetAddress.class });
	    Object routeInfo = routeInfoConstructor.newInstance(gateway);

	    ArrayList<Object> mRoutes = (ArrayList<Object>) getDeclaredField(
		    linkProperties, "mRoutes");
	    mRoutes.clear();
	    mRoutes.add(routeInfo);
	} else { // android3.x版本
	    ArrayList<InetAddress> mGateways = (ArrayList<InetAddress>) getDeclaredField(
		    linkProperties, "mGateways");
	    mGateways.clear();
	    mGateways.add(gateway);
	}

    }

    @SuppressWarnings("unchecked")
    private static void setDNS(InetAddress dns, WifiConfiguration wifiConf)
	    throws SecurityException, IllegalArgumentException,
	    NoSuchFieldException, IllegalAccessException {
	Object linkProperties = getField(wifiConf, "linkProperties");
	if (linkProperties == null)
	    return;
	ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(
		linkProperties, "mDnses");
	mDnses.clear(); // 清除原有DNS设置（如果只想增加，不想清除，词句可省略）
	mDnses.add(dns);// 增加新的DNS

    }

    private static Object getField(Object obj, String name)
	    throws SecurityException, NoSuchFieldException,
	    IllegalArgumentException, IllegalAccessException {
	Field f = obj.getClass().getField(name);
	Object out = f.get(obj);
	return out;
    }

    private static Object getDeclaredField(Object obj, String name)
	    throws SecurityException, NoSuchFieldException,
	    IllegalArgumentException, IllegalAccessException {
	Field f = obj.getClass().getDeclaredField(name);
	f.setAccessible(true);
	Object out = f.get(obj);
	return out;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void setEnumField(Object obj, String value, String name)
	    throws SecurityException, NoSuchFieldException,
	    IllegalArgumentException, IllegalAccessException {
	Field f = obj.getClass().getField(name);
	f.set(obj, Enum.valueOf((Class<Enum>) f.getType(), value));
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
}
