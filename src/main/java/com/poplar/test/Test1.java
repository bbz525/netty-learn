package com.poplar.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * Created By poplar on 2019/11/1
 */
public class Test1 {

    public static void main(String[] args) {
        int count= Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(count);
    }
}
