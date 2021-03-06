/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: UiUtil.java
 * @Prject: BeeVideo
 * @Package: cn.beevideo.utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-7 下午6:00:06
 * @version: V1.0
 */

package org.yousuowei.share.utils;

import java.util.List;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * @ClassName: UiUtil
 * @Description: UI工具类
 * @author: jie
 * @date: 2014-8-7 下午6:00:06
 */

public class UiUtil {

    /**
     * textview 不同字段显示不同颜色
     * 
     * @param v
     * @param color
     * @param start
     * @param end
     * @author: jie
     * @date: 2014-8-11 上午9:36:29
     */
    public static void setTextViewForegroundColor(TextView v, int color,
	    int start, int end) {
	SpannableStringBuilder builder = new SpannableStringBuilder(v.getText()
		.toString());
	ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
	builder.setSpan(colorSpan, start, end,
		Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	v.setText(builder);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 
     * @param context
     * @param dpValue
     * @return
     * @author: jie
     * @date: 2014-9-10 上午9:14:49
     */
    public static int convertDpToPx(Context context, float dpValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * 
     * @param context
     * @param pxValue
     * @return
     * @author: jie
     * @date: 2014-9-10 上午9:14:56
     */
    public static int convertPxToDp(Context context, float pxValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (pxValue / scale + 0.5f);
    }

    public static CharSequence convertClickableText(String text,
	    List<ClickPartText> clickParts) {
	SpannableStringBuilder style = new SpannableStringBuilder(text);
	for (ClickPartText clickPart : clickParts) {
	    style.setSpan(clickPart.clickableSpan, clickPart.start,
		    clickPart.end, 0);
	}
	return style;
    }

    public static class ClickPartText {
	public ClickPartText(int start, int end, ClickableSpan clickableSpan) {
	    this.start = start;
	    this.end = end;
	    this.clickableSpan = clickableSpan;
	}

	public int start;
	public int end;
	public ClickableSpan clickableSpan;
    }

}
