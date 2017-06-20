package com.nhb.app.custom.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.fast.library.utils.DeviceUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 列表工具类
 *
 * @author ZhangMao
 * @version 4.4.0
 * @since 2015-04-23
 */
public class ListViewUtil {

    /**
     * 添加通用的底部边距
     */
    public static void addCommonFooter(Context context, PullToRefreshListView lv_content) {
        if (context == null || lv_content == null) {
            return;
        }
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(30));
        View footer = new View(context);
        footer.setLayoutParams(lp);
        footer.setBackgroundColor(Color.TRANSPARENT);
        if (lv_content.getRefreshableView().getFooterViewsCount() < 2) {
            lv_content.getRefreshableView().addFooterView(footer);
        }
    }

    /**
     * @param exitMap 数据记录器，不传递null，此参数的生命周期跟随页面的生命周期--全局私有
     * @param data    数据源
     * @param clazz   数据item的类型
     * @param key     数据的字段名，按照此字段来去重
     * @param <T>     把数据源中重复对数据删除然后返回
     * @return
     */
    public static final <T> List<T> removeDataDuplicate(Map<String, Integer> exitMap, List<T> data, Class<T> clazz, String key) {
        if (data == null || data.size() == 0) {
            return data;
        }
        if (exitMap == null) {
            throw new NullPointerException();
        }
        List<T> result = new ArrayList<>();
        for (T item : data) {
            try {
                String keyValue = clazz.getDeclaredField(key).get(item).toString();
                if (!exitMap.containsKey(keyValue)) {
                    exitMap.put(keyValue, 0);
                    result.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取Listview的高度，然后设置ViewPager的高度
     *
     * @param listView
     * @return
     */
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }

    /**
     * View是否可以竖直滚动
     *
     * @param v
     * @param checkV
     * @param dy
     * @param x
     * @param y
     * @return
     */
    public static boolean canScroll(View v, boolean checkV, int dy, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft()
                        && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop()
                        && y + scrollY < child.getBottom()
                        && canScroll(child, true, dy,
                        x + scrollX - child.getLeft(), y + scrollY
                                - child.getTop())) {
                    return true;
                }
            }
        }
        return checkV && ViewCompat.canScrollVertically(v, -dy);
    }

}
