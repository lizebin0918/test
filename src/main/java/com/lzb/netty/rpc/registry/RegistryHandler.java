package com.lzb.netty.rpc.registry;

import com.lzb.netty.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <br/>
 * Created on : 2021-06-06 17:36
 * @author lizebin
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //1.一个包名将所有符合条件的class全部扫描出来，放到一个容器中
    //2.给每一个对应的class起一个唯一的名字，作为服务名称，保存到一个容器中

    public RegistryHandler() {
        scannerClass("com.lzb.netty.rpc.provider");

        if (classNames.isEmpty()) return;

        for (String className : classNames) {
            try {
                Class<?> klass = Class.forName(className);
                Class<?> itf = klass.getInterfaces()[0];
                String serviceName = itf.getName();
                registryMap.put(serviceName, klass.newInstance());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private static final List<String> classNames = new ArrayList<>();
    private Map<String, Object> registryMap = new ConcurrentHashMap<>();

    private static void scannerClass(String packgetName) {
        URL url =
            RegistryHandler.class.getClassLoader().getResource(packgetName.replaceAll(
            "\\.", File.separator));
        if (url == null) return;
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                scannerClass(packgetName + "." + file.getName());
            } else {
                classNames.add(packgetName + "." + file.getName().replace(
                    ".class", ""));
            }
        }

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println(" client registered ");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println(" client read complete ");
    }

    /**
     * 3.当有客户端连接过来之后，获取 InvokerProtocol 的对象
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol request = (InvokerProtocol) msg;

        //4.容器中找到符合条件的服务
        String className = request.getClassName();
        Object service = registryMap.get(className);
        if (service != null) {
            Method m = service.getClass().getMethod(request.getMethodName(),
                                                    request.getParams());
            //5.远程过程调用Provider返回结果给客户端
            result = m.invoke(service, request.getValues());
        }

        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
