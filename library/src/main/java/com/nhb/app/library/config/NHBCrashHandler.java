package com.nhb.app.library.config;

import com.fast.library.utils.CrashHandler;
import com.fast.library.utils.DateUtils;

import java.io.File;

/**
 * 说明：崩溃日志处理
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/17 17:23
 * <p/>
 * 版本：verson 1.0
 */
public class NHBCrashHandler extends CrashHandler {

    private static NHBCrashHandler crashHandler = new NHBCrashHandler();

    public static NHBCrashHandler getInstance(){
        return crashHandler;
    }

    @Override
    public void upCrashLog(File file, String error) {

    }

    @Override
    public String setFileName() {
        return "crash_" + DateUtils.getNowTime(DateUtils.FORMAT_YYYY_MM_DD_HH_MM_SS_4)+".txt";
    }

    @Override
    public String setCrashFilePath() {
        return Constant.CRASH;
    }

    @Override
    public boolean isCleanHistory() {
        return true;
    }
}
