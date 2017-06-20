package com.nhb.app.custom.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * <p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-06-12 19:16
 * <p> Version: 1.0
 * <p> Description: RecyclerView点击事件
 * <p>
 * <p>***********************************************************************
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

	private OnItemClickListener mListener;
	private GestureDetector mGestureDetector;

	public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}

	public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
		mListener = listener;
		mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return true;
			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
		View childView = view.findChildViewUnder(e.getX(), e.getY());
		if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
			mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
		}
		return false;
	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

	}

	@Override
	public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
	}
}
