package com.nhb.app.custom.recyclerview;

import android.support.annotation.LayoutRes;

import com.nhb.app.custom.base.NHBViewModel;

import java.util.List;

/**
 * <p>***********************************************************************
 * <p> Author:pengxiaofang
 * <p> CreateData:2016-03-10 11:48
 * <p> Version:1.0
 * <p> Description:RecyclerView的item对应的ViewModel
 * <p>    ==================================================================
 * <p>    	需要注意:RecyclerItemVM中成员变量只能在构造方法中赋值且不能是静态变量
 * <p>    ==================================================================
 * <p>***********************************************************************
 */
public abstract class RecyclerItemVM<T> extends NHBViewModel {

	public T bean;
	public int position;
	public List<T> beans;

	public void setData(List<T> beans, T bean, int position){
		this.beans = beans;
		this.bean = bean;
		this.position = position;
	}

	@LayoutRes
	public abstract int loadItemView();

	public T item() {
		return bean;
	}
}
