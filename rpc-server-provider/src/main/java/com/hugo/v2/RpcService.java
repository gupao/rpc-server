package com.hugo.v2;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by xuyahui on 2019/6/18
 */

@Target(ElementType.TYPE)// 此注解可以修饰的对象（类和接口）
@Retention(RetentionPolicy.RUNTIME)// 运行时处理
@Component // 被spring 进行扫描，注册到容器中
public @interface RpcService {

    Class<?> value();// 拿到服务的接口

    String version() default "";

}
