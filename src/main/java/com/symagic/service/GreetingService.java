package com.symagic.service;

/**
 * @author magic
 * @date 2018/6/26 16:47
 * @version 1.0
 * Description GreetingService
 */
public interface GreetingService {

    /**
     * sayHello
     * @param name  name
     * @return return
     */
    String sayHello(String name);

    /**
     * sayHello
     * @param name name
     * @param address address
     * @param age age
     * @return return
     */
    String sayHello(String name,String address,int age) ;

    /**
     *  sayHello
     * @param name name
     * @param address address
     * @param age age
     * @param mg mg
     * @return return
     */
    String sayHello(String name,String address,int age,Object... mg);

    /**
     * sayHello
     * @param age age
     * @param mg mg
     * @return return
     */
    Integer sayHello(int age,Object... mg);

}
