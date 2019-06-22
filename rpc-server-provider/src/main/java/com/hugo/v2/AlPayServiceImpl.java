package com.hugo.v2;

import com.hugo.IPayService;

/**
 * created by xuyahui on 2019/6/22
 */
@RpcService(value = IPayService.class,version = "v1.0")
public class AlPayServiceImpl implements IPayService {
    @Override
    public String pay(String money) {
        System.out.println("【v1.0】支付金额 ：" + money);
        return "【v1.0】支付金额 ：" + money;
    }
}
