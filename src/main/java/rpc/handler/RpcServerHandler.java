package rpc.handler;

import rpc.common.RpcRequest;
import rpc.common.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(RpcServerHandler.class);
    private Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setResponseId(msg.getRequestId());
        try {
            response.setResult(handleRequest(msg));
        } catch (Exception e) {
            Throwable cause = e.getCause();
            logger.warn("occur Exception : {}", cause.toString());
            response.setException((Exception) cause);

        }
        ctx.writeAndFlush(response);
    }

    //TODO 细化异常，写一个异常类来封装rpc中的异常
    private Object handleRequest(RpcRequest request) throws Exception {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        return method.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("server caught exception", cause);
        ctx.close();
    }
}
