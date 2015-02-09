/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: PostionFragment.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.ui.aci
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-2 下午4:14:47
 * @version: V1.0
 */

package org.yousuowei.share.ui.aci.fragment;

import org.yousuowei.share.R;
import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.ui.MainApplication;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: PostionFragment
 * @Description: TODO
 * @author: jie
 * @date: 2014-9-2 下午4:14:47
 */

public class PostionFragment extends BaseFragment {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private GeoCoder mSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	CommonLog.d(getLogTag(), "onCreateView");
	mainV = inflater.inflate(R.layout.main_position_layout, null);
	initView();
	showLocalPosition();
	return mainV;
    }

    protected void initView() {
	// 获取地图控件引用
	mMapView = (MapView) mainV.findViewById(R.id.bmv_location);
	mBaiduMap = mMapView.getMap();

	mSearch = GeoCoder.newInstance();
	mSearch.setOnGetGeoCodeResultListener(getGeoCoderResultListener);
    }

    private void showLocalPosition() {
	initLocationMap();
	BDLocation location = ((MainApplication) getActivity()
		.getApplicationContext()).location;
	if (null != location) {
	    showLocation(location);
	}
    }

    // private void showPosition(float lat, float lon) {
    // CommonLog.d(getLogTag(), "showPosition lon:", lon, " lat:", lat);
    // LatLng ptCenter = new LatLng(lat, lon);
    // // 反Geo搜索
    // mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
    // }

    @Override
    public void onDestroy() {
	super.onDestroy();
	mBaiduMap.setMyLocationEnabled(false);
	mBaiduMap.clear();
	mBaiduMap = null;

	// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
	mMapView.onDestroy();
	mMapView = null;
    }

    @Override
    public void onResume() {
	super.onResume();
	// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
	mMapView.onResume();
    }

    @Override
    public void onPause() {
	super.onPause();
	// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
	mMapView.onPause();
    }

    public void showLocation(BDLocation location) {
	// 开启定位图层
	mBaiduMap.setMyLocationEnabled(true);
	// 构造定位数据
	MyLocationData locData = new MyLocationData.Builder()
		.accuracy(location.getRadius())
		// 此处设置开发者获取到的方向信息，顺时针0-360
		.direction(100).latitude(location.getLatitude())
		.longitude(location.getLongitude()).build();
	// 设置定位数据
	mBaiduMap.setMyLocationData(locData);
    }

    private void initLocationMap() {
	// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
	MyLocationConfiguration config = new MyLocationConfiguration(
		MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
	mBaiduMap.setMyLocationConfigeration(config);
    }

    private OnGetGeoCoderResultListener getGeoCoderResultListener = new OnGetGeoCoderResultListener() {
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
	    mBaiduMap.clear();
	    mBaiduMap.addOverlay(new MarkerOptions().position(
		    result.getLocation()).icon(
		    BitmapDescriptorFactory
			    .fromResource(R.drawable.img_location_position)));
	    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
		    .getLocation()));
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {

	}
    };

}
