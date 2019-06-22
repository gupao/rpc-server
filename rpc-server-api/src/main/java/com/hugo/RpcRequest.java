package com.hugo;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 实现序列化接口，这个类才可以支持远程传输
 * created by xuyahui on 2019/6/17
 */
public class RpcRequest implements Serializable {

    private String className;
    private String methodName;
    private Object[] parameters;
    private String version;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
