package com.symagic.service;

import com.symagic.asm.attachment.AttachmentAccess;
import org.springframework.beans.factory.Aware;

/**
 * @author magic
 * @date 2018/6/26 16:45
 * @version 1.0
 * Description GreetingServiceEnglishImpl
 */
public class GreetingServiceEnglishImpl implements GreetingService,Aware {

    @Override
    public String sayHello(String name) {
        //throw new RuntimeException(getClass().toGenericString());
        if(this instanceof AttachmentAccess){
            System.out.println("AttachmentAccess: " + ((AttachmentAccess)this).getAttachment());
        }
        return "english Hello " + name;
    }

    @Override
    public String sayHello(String name,String address,int age) {
        return "english Hello " + name;
    }

    @Override
    public String sayHello(String name,String address,int age,Object... mg) {
        return "english Hello " + name;
    }

    @Override
    public Integer sayHello(int age,Object... mg){
        return 199;
    }

}