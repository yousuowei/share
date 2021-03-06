/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: VersionEntity.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.entity
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-28 上午10:10:37
 * @version: V1.0
 */

package org.yousuowei.share.data.entity;

/**
 * @ClassName: VersionEntity
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-28 上午10:10:37
 */

public class VersionInfo extends BaseInfo {
    private static final long serialVersionUID = 1L;

    public String name;// 应用包名
    public String url;// apk文件地址
    public int version;// apk版本
    public String fileMd5;// 文件md5值
    public String desc;// 版本描述

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public int getVersion() {
	return version;
    }

    public void setVersion(int version) {
	this.version = version;
    }

    public String getFileMd5() {
	return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
	this.fileMd5 = fileMd5;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

}
