package com.symagic.asm.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magic
 * @date 2018/6/25 8:54
 * @version 1.0
 * Description RegisterInterceptor
 */
public class RegisterInterceptor {

    private static Map<String,Interceptor> interceptorMap = new ConcurrentHashMap<>(32);

    static {
        interceptorMap.put("1",new TimeCountInterceptor());
    }

    public static Interceptor getInterceptor(Integer index){
        Interceptor c = interceptorMap.get(String.valueOf(index));
        return c;
    }

}
