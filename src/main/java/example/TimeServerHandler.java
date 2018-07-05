package example;
import io.netty.channel.*;
import io.netty.buffer.*;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        long time = System.currentTimeMillis();
        ByteBuf response = ctx.alloc().buffer(8);
        response.writeLong(time);
        ChannelFuture f = ctx.writeAndFlush(response);
        f.addListener(future -> {
            assert f == future;
            ctx.close();
        });
    }
}