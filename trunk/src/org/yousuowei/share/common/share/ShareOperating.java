package org.yousuowei.share.common.share;


import org.yousuowei.share.R;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareOperating {
	public UMSocialService mController = UMServiceFactory
			.getUMSocialService(ShareUtils.umeng_share);
	private Context mContext;
	private UMImage umimage;

	public ShareOperating(Context mContext) {
		super();
		this.mContext = mContext;
		addWXPlatform(mContext);
		addQQQZonePlatform(mContext);
		// 添加新浪SSO授权
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.openShare((Activity) mContext, false);
//		umimage = new UMImage(mContext, R.drawable.sky_code);
		umimage = new UMImage(mContext, R.drawable.img_start);
	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform(Context context) {
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(context,
				ShareUtils.WETCHAT_APPID, ShareUtils.WETCHAT_APPSECRET);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(context,
				ShareUtils.WETCHAT_APPID, ShareUtils.WETCHAT_APPSECRET);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * @功能描述 : 添添加QQ平台支持
	 * @return
	 */
	public void addQQQZonePlatform(Context context) {
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context,
				ShareUtils.QQ_APPID, ShareUtils.QQ_KEY);
		// qqSsoHandler.setTargetUrl("http://www.umeng.com");
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				(Activity) context, ShareUtils.QQ_APPID, ShareUtils.QQ_KEY);
		qZoneSsoHandler.addToSocialSDK();
	}

	public void shareToQQ(String content, String title) {
		QQShareContent shareContent = new QQShareContent();
		if (!TextUtils.isEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (!TextUtils.isEmpty(title)) {
			shareContent.setTitle(title);
		}
		shareContent.setTargetUrl(ShareUtils.umeng_share);
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.QQ);
	}

	public void shareToQQ() {
		shareToQQ(null, null);
	}

	public void shareToWeixin(String content, String title) {
		WeiXinShareContent shareContent = new WeiXinShareContent();
		if (!TextUtils.isEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (!TextUtils.isEmpty(title)) {
			shareContent.setTitle(title);
		}
		shareContent.setTargetUrl(ShareUtils.umeng_share);
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.WEIXIN);
	}

	public void shareToWeixin() {
		shareToWeixin(null, null);
	}

	public void shareToCircle(String content, String title) {
		CircleShareContent shareContent = new CircleShareContent();
		if (!TextUtils.isEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (!TextUtils.isEmpty(title)) {
			shareContent.setTitle(title);
		}
		shareContent.setTargetUrl(ShareUtils.umeng_share);
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
	}

	public void shareToCircle() {
		shareToCircle(null, null);
	}

	public void shareToQZone(String content, String title) {
		QZoneShareContent shareContent = new QZoneShareContent();
		if (!TextUtils.isEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (!TextUtils.isEmpty(title)) {
			shareContent.setTitle(title);
		}
		shareContent.setTargetUrl(ShareUtils.umeng_share);
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.QZONE);
	}

	public void shareToQZone() {
		shareToQZone(null, null);
	}

	public void shareToSina(String content, String title) {
		SinaShareContent shareContent = new SinaShareContent();
		if (!TextUtils.isEmpty(content)) {
			shareContent.setShareContent(content);
		}
		if (!TextUtils.isEmpty(title)) {
			shareContent.setTitle(title);
		}
		shareContent.setTargetUrl(ShareUtils.umeng_share);
		shareContent.setShareImage(umimage);
		mController.setShareMedia(shareContent);
		performShare(SHARE_MEDIA.SINA);
	}

	public void shareToSina() {
		shareToSina(null, null);
	}

	public void performShare(SHARE_MEDIA platform) {
		mController.postShare(mContext, platform, new SnsPostListener() {
			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				String showText = platform.toString();
				if (eCode == StatusCode.ST_CODE_SUCCESSED) {
					showText += "平台分享成功";
				} else {
					showText += "平台分享失败";
				}
				Toast.makeText(mContext, showText, Toast.LENGTH_SHORT).show();
				Log.e("showText", showText);
			}
		});
	}

}
