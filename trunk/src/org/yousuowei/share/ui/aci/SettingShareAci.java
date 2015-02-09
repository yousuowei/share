package org.yousuowei.share.ui.aci;

import org.yousuowei.share.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class SettingShareAci extends BaseAci {

    private ImageView codeIv;

    private UMSocialService mController;

    String appID = "wx9fab014e56ef026e";
    String appSecret = "df22016f2352e4fc2c4407b3b98ded71";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	setContentView(R.layout.setting_share);
	super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
		requestCode);
	if (ssoHandler != null) {
	    ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	}
    }

    @Override
    protected void initView() {
	codeIv = (ImageView) findViewById(R.id.code_img);

	// 首先在您的Activity中添加如下成员变量
	mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	mController.getConfig().setSsoHandler(new SinaSsoHandler());

	// 添加微信平台
	UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
	wxHandler.addToSocialSDK();
	// 添加微信朋友圈
	UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
	wxCircleHandler.setToCircle(true);
	wxCircleHandler.addToSocialSDK();

	// mController.getConfig().removePlatform(SHARE_MEDIA.RENREN,
	// SHARE_MEDIA.DOUBAN);
	codeIv.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// 是否只有已登录用户才能打开分享选择页
		mController.openShare(SettingShareAci.this, false);
	    }
	});
    }

    @Override
    protected void initControl() {
	// 设置分享内容
	mController
		.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
	// 设置分享图片, 参数2为图片的url地址
	mController.setShareMedia(new UMImage(m_ctx.get(),
		"http://www.umeng.com/images/pic/banner_module_social.png"));
	// 设置分享图片，参数2为本地图片的资源引用
	// mController.setShareMedia(new UMImage(getActivity(),
	// R.drawable.icon));
	// 设置分享图片，参数2为本地图片的路径(绝对路径)
	// mController.setShareMedia(new UMImage(getActivity(),
	// BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

	// 设置分享音乐
	// UMusic uMusic = new
	// UMusic("http://sns.whalecloud.com/test_music.mp3");
	// uMusic.setAuthor("GuGu");
	// uMusic.setTitle("天籁之音");
	// 设置音乐缩略图
	// uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	// mController.setShareMedia(uMusic);

	// 设置分享视频
	// UMVideo umVideo = new UMVideo(
	// "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
	// 设置视频缩略图
	// umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	// umVideo.setTitle("友盟社会化分享!");
	// mController.setShareMedia(umVideo);
    }
}
