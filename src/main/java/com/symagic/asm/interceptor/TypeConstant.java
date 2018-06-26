package com.symagic.asm.interceptor;

import org.objectweb.asm.Type;

import java.lang.reflect.Method;

/**
 * @author magic
 * @date 2018/6/26 17:44
 * @version 1.0
 * Description TypeConstant
 *
 *   Type.getType(RegisterInterceptor.class).getInternalName() ==> "com/symagic/asm/interceptor/RegisterInterceptor"
 *   Type.getType(RegisterInterceptor.class).getDescriptor()   ==> "Lcom/symagic/asm/interceptor/RegisterInterceptor;"
 *   Type.getType(Object[].class).getDescriptor()   ==> "[Ljava/lang/Object;"
 *
 */
public enum TypeConstant {

    REGISTER_INTERCEPTOR(RegisterInterceptor.class),
    REGISTER_INTERCEPTOR_GET_INTERCEPTOR_METHOD(RegisterInterceptor.class,"getInterceptor",new Class[]{Integer.class}),
    INTERCEPTOR(Interceptor.class),
    INTERCEPTOR_BEFORE_METHOD(Interceptor.class,"before",new Class[]{Object.class,String.class,Object[].class}),
    INTERCEPTOR_AFTER_METHOD(Interceptor.class,"after",new Class[]{Object.class,String.class,Object[].class,Throwable.class,Object.class}),
    OBJECT(Object.class),
    OBJECT_ARRAY_ONE(Object[].class),
    INTEGER(Integer.class),
    INTEGER_VALUE_OF_BOXING_METHOD(Integer.class,"valueOf",new Class[]{int.class}),
    INTEGER_VALUE_OF_UNBOXING_METHOD(Integer.class,"intValue",new Class[]{}),
    THROWABLE(Throwable.class);

    private Type type;
    private String descriptor;
    private String internalName;
    private String methodName;

    TypeConstant(Class clz){
        this.type = Type.getType(clz);
        this.descriptor = type.getDescriptor();
        this.internalName = type.getInternalName();
        this.methodName = "";
    }

    TypeConstant(Class clz,String methodName,Class... parameter){
        Method method;
        try {
            method = clz.getMethod(methodName,parameter);
            this.type = Type.getType(method);
            this.descriptor = type.getDescriptor();
            this.internalName = type.getInternalName();
            this.methodName = methodName;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e.toString());
        }

    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getInternalName() {
        return internalName;
    }

    public Type getType() {
        return type;
    }

    public String getMethodName() {
        return methodName;
    }
}
