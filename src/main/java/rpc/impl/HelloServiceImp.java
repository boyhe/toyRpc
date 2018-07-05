package rpc.impl;

import rpc.annotation.RpcService;
import rpc.service.HelloService;

@RpcService(HelloService.class)
public class HelloServiceImp implements HelloService {
    @Override
    public String hello(String name) {
        return String.format("Hello %s", name);
    }
}
