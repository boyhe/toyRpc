package rpc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import rpc.common.RpcDecoder;
import rpc.common.RpcEncoder;
import rpc.common.RpcRequest;
import rpc.common.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);
    private String host;
    private int port;

    private RpcResponse response;
    private ChannelFuture cf;
    private volatile boolean connected = false;

    private static final Object object = new Object();

    public RpcClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        this.response = msg;
        synchronized (object) {
            object.notifyAll();
        }
    }

    public RpcResponse send(RpcRequest request) throws InterruptedException {
        if (!connected) initClient();
        cf.channel().writeAndFlush(request).sync();
        synchronized (object) {
            object.wait();
        }
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("client caught exception", cause);
        ctx.close();
    }

    private void initClient() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new RpcDecoder())
                                .addLast(new RpcEncoder())
                                .addLast(RpcClientHandler.this);
                    }
                });
        cf = bootstrap.connect(host, port).sync();
        logger.info("cf.isSuccess: {}", cf.isSuccess());
        if (cf.isSuccess()) connected = true;
    }
}
