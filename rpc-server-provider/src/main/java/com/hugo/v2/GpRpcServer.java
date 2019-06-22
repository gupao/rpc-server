package com.hugo.v2;

import com.hugo.v1.ProcessorHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by xuyahui on 2019/6/18
 */
@Component
public class GpRpcServer implements ApplicationContextAware,InitializingBean {

    ExecutorService executorService = Executors.newCachedThreadPool();

    private int port;

    private Map<String,Object> serviceMap = new HashMap<>();

    public GpRpcServer(int port) {
        this.port = port;
    }

    /**
     * 属性设置完成之后调用
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandlerPlus(socket,serviceMap));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * dubbo 的思路
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(!beanMap.isEmpty()){
            for (Object serviceBean : beanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String beanName = rpcService.value().getName();
                String version = rpcService.version();
                if(!StringUtils.isEmpty(version)){
                    beanName += "-" + version;
                }
                serviceMap.put(beanName,serviceBean);
            }
        }
    }
}
