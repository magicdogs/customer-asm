package com.symagic.asm.interceptor;

/**
 * @author magic
 * @version 1.0
 * Description ClassConstant
 * @date 2018/6/27 8:44
 */
public class ClassConstant {
    static Class[] BEFORE_METHOD_PARAMETER_CLASS = new Class[]{Object.class, String.class, Object[].class};
    static Class[] AFTER_METHOD_PARAMETER_CLASS = new Class[]{Object.class, String.class, Object[].class, Throwable.class, Object.class};
    static Class[] GET_INTERCEPTOR_METHOD_PARAMETER_CLASS = new Class[]{Integer.class};
    static Class[] VALUE_OF_METHOD_PARAMETER_CLASS = new Class[]{int.class};
    static Class[] EMPTY_PARAMETER_CLASS = new Class[]{};
    static Class[] SET_ATTACHMENT_METHOD_PARAMETER_CLASS = new Class[]{Object.class};

}
