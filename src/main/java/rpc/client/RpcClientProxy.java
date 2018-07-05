package rpc.client;

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


    @SuppressWarnings("unchecked")
    public <T> T create(Class<?> interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest(UUID.randomUUID().toString(),
                        interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);
                RpcClientHandler clientHandler = new RpcClientHandler("localhost", 9090);
                RpcResponse response = clientHandler.send(request);
                return response.getResult();
            }
        });
    }
}
