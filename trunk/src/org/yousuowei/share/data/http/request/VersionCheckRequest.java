/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: VersionCheckRequest.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.http.request
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 下午4:45:57
 * @version: V1.0
 */

package org.yousuowei.share.data.http.request;

import org.yousuowei.share.data.http.result.VersionCheckResult;
import org.yousuowei.share.utils.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.v4.util.ArrayMap;

/**
 * @ClassName: VersionCheckRequest
 * @Description: 检查新版本
 * @author: jie
 * @date: 2014-8-27 下午4:45:57
 */

public class VersionCheckRequest extends BaseBusinessRequest {
    private final static String ACTION_URL = "/ota/api/apkversion/checkApkVersion";

    public VersionCheckRequest(Context context) {
	super(context, new VersionCheckResult(context), false);
    }

    @Override
    protected String getActionUrl() {
	return ACTION_URL;
    }

    @Override
    protected ArrayMap<String, String> appendUrlSegment() {
	PackageInfo info = Utils.getPackageInfos(context);
	ArrayMap<String, String> params = new ArrayMap<String, String>();
	params.put("packageName", info.packageName);
	params.put("version", String.valueOf(info.versionCode));
	return params;
    }

}
