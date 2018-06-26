package com.symagic.service;

import com.symagic.asm.interceptor.Interceptor;
import com.symagic.asm.interceptor.RegisterInterceptor;

/**
 * @author magic
 * @date 2018/6/26 16:47
 * @version 1.0
 * Description GreetingServiceFrenchImpl
 */
public class GreetingServiceFrenchImpl implements GreetingService {

    @Override
    public String sayHello(String name) {
        Object[] args = new Object[1];
        args[0] = name;
        Interceptor interceptor = RegisterInterceptor.getInterceptor(1);
        interceptor.before(this,"sayHello",args);
        String result;
        try {
            result = "french Hello " + name;
        }catch (Throwable throwable){
            interceptor.after(this,"sayHello",args,throwable,null);
            throw throwable;
        }
        interceptor.after(this,"sayHello",args,null,result);
        return result;
    }

    @Override
    public String sayHello(String name,String address,int age) {
        return "english Hello " + name;
    }

    @Override
    public String sayHello(String name,String address,int age,Object... mg){
        return "english Hello " + name;
    }

    @Override
    public Integer sayHello(int age,Object... mg){
        return 199;
    }

}