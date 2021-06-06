package com.lzb.netty.rpc.consumer.proxy;

import com.lzb.netty.rpc.protocol.InvokerProtocol;
import com.lzb.netty.rpc.registry.RegistryHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.*;

import java.lang.reflect.*;

/**
 * <br/>
 * Created on : 2021-06-06 21:00
 * @author lizebin
 */
public class RpcProxy {

    public static <T> T create(Class<T> klass) {
        MethodProxy proxy = new MethodProxy(klass);
        return (T) Proxy.newProxyInstance(klass.getClassLoader(),
                                          new Class[]{klass},
                                          proxy);
    }

    private static class MethodProxy implements InvocationHandler {

        private Class<?> klass;

        RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();

        private MethodProxy(Class<?> klass) {
            this.klass = klass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            } else {
                return rpcInvoker(proxy, method, args);
            }
        }

        private Object rpcInvoker(Object proxy, Method method, Object[] args) {
            InvokerProtocol msg = new InvokerProtocol();
            msg.setClassName(klass.getName());
            msg.setMethodName(method.getName());
            msg.setParams(method.getParameterTypes());
            msg.setValues(args);

            //发起网络请求
            EventLoopGroup worker = new NioEventLoopGroup();
            Bootstrap client = new Bootstrap();

            client.group(worker).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
            client.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();

                    //还原 InvokerProtocol
                    //对于自定义协议，需要对内容编解码
                    p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                                               0, 4, 0, 4));
                    //自定义编码
                    p.addLast(new LengthFieldPrepender(4));

                    //还原 InvokerProtocol 参数
                    //实参处理
                    p.addLast("encoder", new ObjectEncoder());
                    p.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE,
                                                           ClassResolvers.cacheDisabled(null)));
                    //执行业务逻辑
                    //1.注册：给每一个对象起一个名字
                    p.addLast(rpcProxyHandler);
                }
            });

            try {
                ChannelFuture future = client.connect("localhost", 8080).sync();
                future.channel().writeAndFlush(msg).sync();
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                worker.shutdownGracefully();
            }

            return rpcProxyHandler.getResult();
        }
    }

}
