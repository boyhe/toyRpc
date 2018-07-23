package rpc.proxy;

import rpc.common.RpcRequest;
import rpc.common.RpcResponse;
import rpc.handler.RpcClientHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

@Component
public class RpcClientProxy {
    private Class<?> serviceInterface;
    private String remoteServerIP;
    private int remoteServerPort;

    public RpcClientProxy() {
    }


    @SuppressWarnings("unchecked")
    public <T> T createObject(){

        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest(UUID.randomUUID().toString(),
                    serviceInterface.getName(), method.getName(), method.getParameterTypes(), args);
                RpcClientHandler clientHandler = new RpcClientHandler(remoteServerIP, remoteServerPort);
                RpcResponse response = clientHandler.send(request);
                return response.getResult();
            }
        });
    }

    public RpcClientProxy setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
        return this;
    }

    public RpcClientProxy setRemoteServerIP(String remoteServerIP) {
        this.remoteServerIP = remoteServerIP;
        return this;
    }

    public RpcClientProxy setRemoteServerPort(int remoteServerPort) {
        this.remoteServerPort = remoteServerPort;
        return this;
    }

}
