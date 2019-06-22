package com.hugo.v1;

import com.hugo.IHelloService;
import com.hugo.v2.RpcService;
import com.hugo.User;

/**
 * created by xuyahui on 2019/6/17
 */
public class HelloServiceImpl implements IHelloService{

    @Override
    public String sayHello(String content) {
        System.out.println("request in sayHello " + content);
        return "say hello : " + content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("request in sayHello " + user);
        return "success";
    }
}
