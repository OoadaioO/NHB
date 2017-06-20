package com.nhb.app.custom.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 11:48
 * Version:1.0
 * Description:带圆角和边框的图片
 * ***********************************************************************
 */
public class RoundedTransformation implements com.squareup.picasso.Transformation {

	// 圆角的大小 in dp
	private final float radius;
	// 边框的大小 in dp
	private final float strokeWidth;
	private final int strokeColor;

	public RoundedTransformation(final float radius, final float strokeWidth, int strokeColor) {
		this.radius = radius;
		this.strokeWidth = strokeWidth;
		this.strokeColor = strokeColor;
	}

	@Override
	public Bitmap transform(final Bitmap source) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

		PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		canvas.setDrawFilter(pfd);
		canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint);

		if(strokeWidth > 0){
			// 绘制边框
			paint.setShader(null);
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(strokeColor);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStrokeWidth(strokeWidth);
			canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), radius, radius, paint);
		}

		if (source != output) {
			source.recycle();
		}

		return output;
	}

	@Override
	public String key() {
		return "rounded";
	}
}
