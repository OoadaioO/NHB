package com.nhb.app.custom.recyclerview;

import android.support.annotation.NonNull;

/**
 *<p>***********************************************************************
 * <p> Author: pengxiaofang
 * <p> CreateData: 2016-07-06 17:16
 * <p> Version: 1.0
 * <p> Description: ItemViewModel
 * <p>
 * <p>***********************************************************************
 */

public abstract class ItemVMFactory {

	@NonNull
	public abstract RecyclerItemVM getItemVM(int viewType);

	public int getItemViewType(int position){
		return 0;
	}
}
