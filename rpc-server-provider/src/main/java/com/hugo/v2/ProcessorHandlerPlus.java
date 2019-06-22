package com.hugo.v2;

import com.hugo.RpcRequest;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * created by xuyahui on 2019/6/17
 */
public class ProcessorHandlerPlus implements Runnable {

    private Socket socket;
    private Map<String,Object> serviceMap;

    public ProcessorHandlerPlus(Socket socket, Map<String,Object> serviceMap) {
        this.socket = socket;
        this.serviceMap = serviceMap;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest);// 反射调用本地服务

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if(objectOutputStream != null){
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Object invoke(RpcRequest rpcRequest) {

        String serviceName = rpcRequest.getClassName();
        String version = rpcRequest.getVersion();
        if(!StringUtils.isEmpty(version)){
            serviceName += "-" + version;
        }
        Object service =  serviceMap.get(serviceName);
        if(null == service){
            throw new RuntimeException("service not found " + serviceName);
        }

        Object[] args = rpcRequest.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }
        Class clazz = null;
        try {
            clazz = Class.forName(rpcRequest.getClassName());
            Method method = clazz.getMethod(rpcRequest.getMethodName(), types);
            Object result = method.invoke(service, args);
            return result;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
