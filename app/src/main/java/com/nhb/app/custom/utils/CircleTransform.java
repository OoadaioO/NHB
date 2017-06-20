package com.nhb.app.custom.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Shader;
import android.support.annotation.ColorInt;

import com.squareup.picasso.Transformation;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 12:02
 * Version:1.0
 * Description:圆形图片
 * ***********************************************************************
 */
public class CircleTransform implements Transformation {

	private float strokeWidth;
	private int strokeColor;

	public CircleTransform(float strokeWidth, @ColorInt int strokeColor){
		this.strokeWidth = strokeWidth;
		this.strokeColor = strokeColor;
	}

	@Override
	public Bitmap transform(Bitmap source) {
		if (source == null || source.isRecycled()) {
			return null;
		}

		final int width = source.getWidth();
		final int height = source.getHeight();

		Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);

		PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		Canvas canvas = new Canvas(canvasBitmap);
		canvas.setDrawFilter(pfd);
		float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
		canvas.drawCircle(width / 2, height / 2, radius, paint);

		if(strokeWidth > 0){
			// 绘制边框
			paint.setShader(null);
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(strokeColor);
			paint.setStrokeWidth(strokeWidth);
			canvas.drawCircle(width / 2, height / 2, radius - strokeWidth / 2, paint);
		}

		if (canvasBitmap != source) {
			source.recycle();
		}

		return canvasBitmap;
	}

	@Override
	public String key() {
		return "circle";
	}
}
