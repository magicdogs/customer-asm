package com.symagic.asm.interceptor;

import com.symagic.asm.attachment.AttachmentAccess;
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
    REGISTER_INTERCEPTOR_GET_INTERCEPTOR_METHOD(RegisterInterceptor.class,"getInterceptor",ClassConstant.GET_INTERCEPTOR_METHOD_PARAMETER_CLASS),
    INTERCEPTOR(Interceptor.class),
    INTERCEPTOR_BEFORE_METHOD(Interceptor.class,"before",ClassConstant.BEFORE_METHOD_PARAMETER_CLASS),
    INTERCEPTOR_AFTER_METHOD(Interceptor.class,"after",ClassConstant.AFTER_METHOD_PARAMETER_CLASS),
    OBJECT(Object.class),
    OBJECT_ARRAY_ONE(Object[].class),
    INTEGER(Integer.class),
    INTEGER_VALUE_OF_BOXING_METHOD(Integer.class,"valueOf",ClassConstant.VALUE_OF_METHOD_PARAMETER_CLASS),
    INTEGER_VALUE_OF_UNBOXING_METHOD(Integer.class,"intValue",ClassConstant.EMPTY_PARAMETER_CLASS),
    THROWABLE(Throwable.class),
    ATTACHMENT_ACCESS(AttachmentAccess.class),
    ATTACHMENT_ACCESS_SET_ATTACHMENT_METHOD(AttachmentAccess.class,"setAttachment",ClassConstant.SET_ATTACHMENT_METHOD_PARAMETER_CLASS),
    ATTACHMENT_ACCESS_GET_ATTACHMENT_METHOD(AttachmentAccess.class,"getAttachment",ClassConstant.EMPTY_PARAMETER_CLASS);

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
