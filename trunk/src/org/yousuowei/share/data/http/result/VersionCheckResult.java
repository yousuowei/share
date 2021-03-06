/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: VersionCheckResult.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.http.result
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-27 下午4:54:45
 * @version: V1.0
 */

package org.yousuowei.share.data.http.result;

import org.yousuowei.share.data.http.result.entity.JsonVersionInfo;

import android.content.Context;

/**
 * @ClassName: VersionCheckResult
 * @Description: 检查新版本结果解析
 * @author: jie
 * @date: 2014-8-27 下午4:54:45
 */

public class VersionCheckResult extends SimpleJsonResult {

    public VersionCheckResult(Context context) {
	super(context);
    }

    @Override
    protected Class<?> getInfoClass() {
	return JsonVersionInfo.class;
    }

}
