package com.nhb.app.custom.base;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.nhb.app.custom.R;
import com.nhb.app.custom.domain.PicassoManager;
import com.nhb.app.custom.recyclerview.GMRecyclerSimpleAdapter;
import com.nhb.app.custom.recyclerview.ItemVMFactory;
import com.nhb.app.custom.utils.CircleTransform;
import com.nhb.app.custom.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-02 11:48
 * Version:1.0
 * Description:Binding适配器
 * ***********************************************************************
 */
public class BindingAdapters {

    /**
     * 在xml中设置ImageView的url，其中bind:error设置图片加载失败后的默认图。
     *
     * @param view
     * @param url          ImageView要显示的图片的url，eg:
     *                     bind:imageUrl="@{viewModel.image}"
     * @param error        ImageView加载图片失败后的默认图，eg:
     *                     bind:error="R.drawable.error"
     * @param round        是否是圆形的,此属性和radius属性互斥,当round为true时radius失效
     * @param radius       圆角大小，单位是dp
     * @param stroke_width 边框大小，单位是dp
     * @param stroke_color 边框颜色
     */
    @BindingAdapter(value = {"bind:imageUrl", "bind:placeHolder", "bind:error", "bind:round", "bind:radius", "bind:stroke_width", "bind:stroke_color"}, requireAll = false)
    public static void loadImageWithPicasso(ImageView view, String url, Drawable placeHolder, Drawable error, boolean round, int radius, int stroke_width, @ColorInt int stroke_color) {

        Picasso picasso = PicassoManager.with(view.getContext());
        //开启debug模式,可以显示出图片是从本地、内存还是网络获取
//		picasso.setDebugging(BuildConfig.isDebug);
        RequestCreator requestCreator = TextUtils.isEmpty(url) ? picasso.load(R.drawable.icon_gray_logo) : picasso.load(url);
        // 设置默认图
        requestCreator.placeholder(null != placeHolder ? placeHolder : view.getContext().getResources().getDrawable(R.drawable.icon_gray_logo));
        // 设置加载失败后的图
        requestCreator.error(null != error ? error : view.getContext().getResources().getDrawable(R.drawable.icon_gray_logo));
        // 设置图片为圆形
        if (round) {
            requestCreator.transform(new CircleTransform(stroke_width, stroke_color));
        } else if (0 != radius || 0 != stroke_width) {
            // 设置图片为圆角带边框
            requestCreator.transform(new RoundedTransformation(radius, stroke_width, stroke_color));
        }
        requestCreator.into(view);
    }

    @BindingAdapter(value = {"beans", "itemVMFactory", "itemClickListener", "layoutManager", "itemDecoration"}, requireAll = false)
    public static void setUpRecyclerView(RecyclerView recyclerView,
                                         ObservableArrayList beans,
                                         ItemVMFactory itemVMFactory,
                                         OnItemClickListener itemClickListener,
                                         RecyclerView.LayoutManager layoutManager,
                                         RecyclerView.ItemDecoration itemDecoration) {
        if (null == layoutManager) {
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
        }
        recyclerView.setLayoutManager(layoutManager);
        if (null != itemDecoration) {
            recyclerView.addItemDecoration(itemDecoration);
        }
        GMRecyclerSimpleAdapter simpleAdapter = new GMRecyclerSimpleAdapter(beans, itemVMFactory, itemClickListener);
        recyclerView.setAdapter(simpleAdapter);
    }

    @BindingAdapter(value = {"beans", "itemVMFactory", "itemClickListener"}, requireAll = false)
    public static void setUpAdapterView(AdapterView adapterView,
                                        ObservableArrayList beans,
                                        ItemVMFactory itemVMFactory,
                                        OnItemClickListener itemClickListener) {
        if (null == itemVMFactory) {
            throw new RuntimeException("please set ItemVMFactory");
        }
        if (null == beans) {
            throw new RuntimeException("please set beans");
        }
        adapterView.setAdapter(new NHBBaseAdapter(beans, itemVMFactory, itemClickListener));

    }

    /**
     * 设置view的背景图片
     * <p> Note that conversions only happen at the setter level, so it is not allowed to mix types like this:
     * <p>
     * <p>	<View
     * <p>		android:background="@{isError ? @drawable/error : @color/white}"
     * <p>		android:layout_width="wrap_content"
     * <p>		android:layout_height="wrap_content"/>
     *
     * @param view
     * @param res  idRes
     */
    @BindingAdapter({"bind:backgroundRes"})
    public static void setBackgroundRes(View view, int res) {
        view.setBackgroundResource(res);
    }

    /**
     * 设置view的背景图片
     * <p> Note that conversions only happen at the setter level, so it is not allowed to mix types like this:
     * <p>
     * <p>	<View
     * <p>		android:background="@{isError ? @drawable/error : @color/white}"
     * <p>		android:layout_width="wrap_content"
     * <p>		android:layout_height="wrap_content"/>
     *
     * @param view
     * @param drawable drawable
     */
    @BindingAdapter({"bind:backgroundDrawable"})
    public static void setBackgroundDrawable(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
    }

    /**
     * 设置ImageView的图片
     *
     * @param view
     * @param res  idRes
     */
    @BindingAdapter({"bind:srcRes"})
    public static void setSrcRes(ImageView view, int res) {
        view.setImageResource(res);
    }

    /**
     * 设置View的高度
     *
     * @param view
     * @param height View的高度
     */
    @BindingAdapter(value = {"bind:resizeHeight"})
    public static void setViewHeight(final View view, int height) {
        view.getLayoutParams().height = height;
    }

    /**
     * 设置View的宽度
     *
     * @param view
     * @param width View的宽度
     */
    @BindingAdapter(value = {"bind:resizeWidth"})
    public static void setViewWidth(final View view, int width) {
        view.getLayoutParams().width = width;
    }
}
