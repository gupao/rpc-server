package com.hugo.v2;

import com.hugo.IHelloService;
import com.hugo.User;

/**
 * created by xuyahui on 2019/6/17
 */
@RpcService(value = IHelloService.class,version = "v1.0")
public class HelloServiceImplPlusV1 implements IHelloService{

    @Override
    public String sayHello(String content) {
        System.out.println("【v1.0】request in sayHello " + content);
        return "【v1.0】say hello : " + content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("【v1.0】request in sayHello " + user);
        return "【v1.0】success";
    }
}
