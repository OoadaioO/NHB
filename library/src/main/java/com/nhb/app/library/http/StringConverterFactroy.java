package com.nhb.app.library.http;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 说明：自定义Converter实现RequestBody到String的转换
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/6/12 10:47
 * <p/>
 * 版本：verson 1.0
 */
public class StringConverterFactroy extends Converter.Factory{

    private static final StringConverterFactroy INSTANCE = new StringConverterFactroy();

    public static StringConverterFactroy create(){
        return INSTANCE;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return StringConverter.INSTANCE;
        }
        return null;
    }

    public static class StringConverter implements Converter<ResponseBody,String>{

        public static final StringConverter INSTANCE = new StringConverter();

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }
}
