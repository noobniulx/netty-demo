package com.niulx.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


public class NettyServer {

    private final static String IP = "127.0.0.1";

    private final static int PORT = 1111;

    private final static int GROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;

    private final static int THREADSIZE = 100;

    private final static EventLoopGroup bossGroup = new NioEventLoopGroup(GROUPSIZE);

    private final static EventLoopGroup workGroup = new NioEventLoopGroup(THREADSIZE);


    public static void start() throws InterruptedException {
        ServerBootstrap serverBootstrap = init();
        ChannelFuture sync = serverBootstrap.bind(IP, PORT).sync();
        sync.channel().closeFuture().sync();
        System.out.println("server start");
    }

    public static ServerBootstrap init() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new TcpServerHandler());
            }
        });
        return bootstrap;
    }


    public static void shutdown() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("启动server ->");
        NettyServer.start();
    }


}
