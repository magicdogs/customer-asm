package com.symagic.asm.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.symagic.asm.attachment.AttachmentAccess;

/**
 * @author magic
 * @date 2018/6/25 17:59
 * @version 1.0
 * Description TimeCountInterceptor
 */
public class TimeCountInterceptor implements Interceptor {

    private Long start;

    @Override
    public void before(Object target,String methodName,Object[] args){
        ObjectMapper objectMapper = new ObjectMapper();
        if(target instanceof AttachmentAccess){
            ((AttachmentAccess) target).setAttachment(methodName);
        }
        try {
            System.out.println("before args: " + objectMapper.writeValueAsString(args));
            start = System.currentTimeMillis();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void after(Object target,String methodName,Object[] args, Throwable throwable, Object result) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println("afters args: "
                    + objectMapper.writeValueAsString(args)
                    + ", throw: " + throwable
                    + ",result: " + result
                    + ",spends: " + (System.currentTimeMillis() - start) + " ms."
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
