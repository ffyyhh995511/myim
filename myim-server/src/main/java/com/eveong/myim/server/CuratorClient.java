package com.eveong.myim.server;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

public class CuratorClient {
    public static void main(String[] args)throws Exception{
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
                new RetryNTimes(3,1000));
        client.start();

        /**
         * PERSISTENT：持久化
         * PERSISTENT_SEQUENTIAL：持久化并且带序列号
         * EPHEMERAL：临时
         * EPHEMERAL_SEQUENTIAL：临时并且带序列号
         */
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/name","7".getBytes());

        NodeCache nodeCache = new NodeCache(client,"/name");
        // 参数：false默认的入参，创建节点时就出发；true创建节点时不触发
        nodeCache.start(true);
        // 监听，可以解决zookeeper原生的监听器只触发一次的问题
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged()  throws Exception {
                System.out.println("数据发生了改变");
            }
        });

        /*client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                // 只会触发一次
                System.out.println("zookeeper原生的Watcher");
            }
        }).forPath("/name");*/

        System.in.read();
    }
}