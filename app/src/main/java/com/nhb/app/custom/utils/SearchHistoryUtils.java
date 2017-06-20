package com.nhb.app.custom.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.nhb.app.custom.constant.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史工具类
 *
 * @author jie zhangmao
 */
public class SearchHistoryUtils {

    private static final int MAX_COUNT = 10;

    public static boolean addSearchContent(Context context, String content) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim())) {
            return false;
        }
        try {
            List<String> list = getSearchHistory(context);
            // 避免重复，先删除之前的
            if (list.contains(content)) {
                list.remove(content);
            }
            int size = list.size();
            if (size >= MAX_COUNT) {
                list.remove(size - 1);
            }
            list.add(0, content);
            SpCustom.get().write(Constants.SEARCH_HISTORY, JSON.toJSONString(list));
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    public static boolean removeContent(Context context, String content) {
        List<String> list = getSearchHistory(context);
        if (list.contains(content)) {
            list.remove(content);
        }
        return true;
    }

    public static void clearAll(Context context) {
        SpCustom.get().remove(Constants.SEARCH_HISTORY);
    }

    public static List<String> getSearchHistory(Context context) {
        List<String> list;
        String content = SpCustom.get().readString(Constants.SEARCH_HISTORY, "");
        if (TextUtils.isEmpty(content)) {
            list = new ArrayList<>();
            SpCustom.get().write(Constants.SEARCH_HISTORY, JSON.toJSONString(list));
        } else {
            list = JSON.parseArray(content, String.class);
        }
        return list;
    }
}
