package com.symagic.asm.interceptor;

/**
 * @author magic
 * @date 2018/6/25 17:55
 * @version 1.0
 * Description Interceptor
 */
public interface Interceptor {

    /**
     *  before
     * @param target this
     * @param methodName method
     * @param args args
     */
    void before(Object target,String methodName,Object[] args);

    /**
     * after
     * @param target target
     * @param methodName methodName
     * @param args args
     * @param throwable throwable
     * @param result result
     */
    void after(Object target,String methodName,Object[] args,Throwable throwable,Object result);
}
