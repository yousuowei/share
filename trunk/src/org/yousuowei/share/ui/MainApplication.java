/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: MainApplication.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:42:14
 * @version: V1.0
 */

package org.yousuowei.share.ui;

import org.yousuowei.share.BuildConfig;
import org.yousuowei.share.data.entity.NearInfo;
import org.yousuowei.share.data.sms.SMSConstant;
import org.yousuowei.share.data.sms.SMSObserver;

import android.app.Application;
import android.content.ContentResolver;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * @ClassName: MainApplication
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-26 下午5:42:14
 */

public class MainApplication extends Application {
    // private final static String TAG = MainApplication.class.getName();

    private NearInfo roomInfo;

    public BDLocation location;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener;

    @Override
    public void onCreate() {
	super.onCreate();
	initSmsReciver();
	initLoaction();
    }


    private void initSmsReciver() {
	ContentResolver resolver = getContentResolver();
	SMSObserver observer = new SMSObserver(null, getApplicationContext());
	// 注册观察者类时得到回调数据确定一个给定的内容URI变化。
	resolver.registerContentObserver(SMSConstant.CONTENT_URI, true,
		observer);
    }

    private void initLoaction() {
	myListener = new LocationListener();
	mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
	mLocationClient.registerLocationListener(myListener); // 注册监听函数
	initLocationClientOption();

	mLocationClient.start();
    }

    private void initLocationClientOption() {
	LocationClientOption option = new LocationClientOption();
	option.setOpenGps(true);
	option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
	option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值bd09ll
	option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
	mLocationClient.setLocOption(option);
    }

    @Override
    public void onTerminate() {
	super.onTerminate();
	mLocationClient.stop();
    }

    public NearInfo getRoomInfo() {
	return roomInfo;
    }

    public void setRoomInfo(NearInfo roomInfo) {
	this.roomInfo = roomInfo;
    }

    private class LocationListener implements BDLocationListener {
	@Override
	public void onReceiveLocation(BDLocation bdlocation) {
	    if (null != bdlocation) {
		mLocationClient.stop();
	    }
	    if (BuildConfig.DEBUG) {
		Log.d("MainApplication",
			"onReceiveLocation radius:" + bdlocation.getRadius()
				+ " latitude:" + bdlocation.getLatitude()
				+ " longitude:" + bdlocation.getLongitude());
	    }
	    location = bdlocation;
	}
    }

}
