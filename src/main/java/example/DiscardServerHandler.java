package example;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(DiscardServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBufMsg = (ByteBuf) msg;
        logger.info("byteBufMsg.readerIndex(): {}", byteBufMsg.readerIndex());
        logger.info("byteBufMsg.writerIndex(): {}", byteBufMsg.writerIndex());
        logger.info("byteBufMsg.capacity(): {}", byteBufMsg.capacity());
        byte[] in = new byte[byteBufMsg.writerIndex()];
        byteBufMsg.readBytes(in);
        logger.info("in : {}", new String(in));
        byteBufMsg.release();
    }
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        long time = System.currentTimeMillis();
        logger.info("time : {}", time);
        ByteBuf response = ctx.alloc().buffer(8);
        response.writeLong(time);
        final ChannelFuture f = ctx.writeAndFlush(response);
        f.addListener(future -> {
            assert f == future;
            logger.info("close");
            ctx.close();
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        logger.error("exception : {}", cause.getCause());
        ctx.close();
    }
}
