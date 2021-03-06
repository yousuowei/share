/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: BaseBusinessRequest.java
 * @Prject: OmBracelet
 * @Package: org.om.bracelet.data.http.request
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-29 下午2:01:34
 * @version: V1.0
 */

package org.yousuowei.share.data.http.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.yousuowei.share.common.CommonLog;
import org.yousuowei.share.common.Constants;
import org.yousuowei.share.utils.StringUtil;
import org.yousuowei.share.utils.Utils;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.mipt.clientcommon.BaseResult;

/**
 * @ClassName: BaseBusinessRequest
 * @Description: 符合网络请求公共业务父类
 * @author: jie
 * @date: 2014-8-29 下午2:01:34
 */

public abstract class BaseFileUploadRequest extends BaseBusinessRequest {

    private final static String FILE_BODER = "----------";

    public BaseFileUploadRequest(Context context, BaseResult result) {
	this(context, result, false);
    }

    public BaseFileUploadRequest(Context context, BaseResult result,
	    Boolean needPassport) {
	super(context, result, needPassport);
    }

    @Override
    protected RequestType getMethod() {
	return RequestType.POST;
    }

    @Override
    protected ArrayMap<String, String> getHeaders() {
	String UseAgent = getUserAgent();
	ArrayMap<String, String> map = new ArrayMap<String, String>();
	map.put("UseAgent", UseAgent);
	map.put("Connection", "Keep-Alive");
	map.put("Charset", Constants.ENCODING_FORMAT);
	map.put("Content-Type", "multipart/form-data; boundary=" + FILE_BODER);
	return map;
    }

    private String getUserAgent() {
	return android.os.Build.MODEL;
    }

    @Override
    protected byte[] composePostData() {
	byte[] postData = null;
	postData = appendPostParams(postData);

	CommonLog.d(getLogTag(), "appendPostParams postData:", postData.length);
	Map<String, String> uploadFiles = getPostFileMap();
	if (null != uploadFiles) {
	    for (String uploadName : uploadFiles.keySet()) {
		postData = appendFile(postData, uploadName,
			uploadFiles.get(uploadName));
	    }
	}
	CommonLog.d(getLogTag(), "composePostData postData:", postData.length);
	return postData;
    }

    public byte[] appendPostParams(byte[] postData) {
	Map<String, String> params = appendUrlSegment();
	if (null == params) {
	    return postData;
	}
	StringBuffer paramStr = new StringBuffer(); // 存储封装好的请求体信息
	try {
	    for (Map.Entry<String, String> entry : params.entrySet()) {
		paramStr.append(entry.getKey())
			.append("=")
			.append(URLEncoder.encode(entry.getValue(),
				Constants.ENCODING_FORMAT)).append("&");
	    }
	    paramStr.deleteCharAt(paramStr.length() - 1); // 删除最后的一个"&"
	    CommonLog.d(getLogTag(), "appendPostParams paramStr:",
		    paramStr.toString());
	    byte[] paramsBytes = paramStr.toString().getBytes(
		    Constants.ENCODING_FORMAT);
	    return Utils.byteMerger(postData, paramsBytes);
	} catch (Exception e) {
	    CommonLog.e(getLogTag(), e);
	}
	return postData;
    }

    private byte[] appendFile(byte[] postData, String uploadName,
	    String filePath) {
	if (TextUtils.isEmpty(filePath)) {
	    return postData;
	}
	File file = new File(filePath);
	if (!file.exists()) {
	    return postData;
	}

	StringBuilder postHeadStr = new StringBuilder();
	postHeadStr.append("--"); // 必须多两道线
	postHeadStr.append(FILE_BODER);
	postHeadStr.append("\r\n");
	postHeadStr.append("Content-Disposition: form-data;name=\"");
	postHeadStr.append(uploadName);
	postHeadStr.append("\";filename=\"");
	postHeadStr.append(file.getName());
	postHeadStr.append("\"\r\n");
	postHeadStr.append("Content-Type:application/octet-stream\r\n\r\n");
	try {
	    byte[] head = postHeadStr.toString().getBytes(
		    Constants.ENCODING_FORMAT);
	    CommonLog
		    .d(getLogTag(), "appendFile head:", postHeadStr.toString());
	    postData = Utils.byteMerger(postData, head);
	    byte[] files = readVoiceFileBytes(filePath);
	    postData = Utils.byteMerger(postData, files);
	    byte[] foot = StringUtil.appendStr("\r\n--", FILE_BODER, "--\r\n")
		    .getBytes(Constants.ENCODING_FORMAT);// 定义最后数据分隔线
	    postData = Utils.byteMerger(postData, foot);
	} catch (UnsupportedEncodingException e) {
	    CommonLog.e(getLogTag(), e);
	}
	return postData;
    }

    protected Map<String, String> getPostFileMap() {
	return null;
    }

    protected byte[] readVoiceFileBytes(String path) {
	byte[] voiceBytes = null;
	File voiceFile = new File(path);
	InputStream in = null;
	try {
	    voiceBytes = new byte[Long.valueOf(voiceFile.length()).intValue()];
	    in = new FileInputStream(voiceFile);
	    in.read(voiceBytes);
	} catch (FileNotFoundException e) {
	    CommonLog.e(getLogTag(), e);
	} catch (IOException e) {
	    CommonLog.e(getLogTag(), e);
	} finally {
	    try {
		in.close();
	    } catch (IOException e) {
		CommonLog.e(getLogTag(), e);
	    }
	}
	return voiceBytes;
    }
}
