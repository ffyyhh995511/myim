package com.eveong.myim.server.service;

import com.eveong.myim.common.zk.CuratorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * @Author fangyunhe
 * @Date 2019/10/30 11:23
 * Version 1.0
 **/
@Service
public class ZKService {

    @Value("${server.port}")
    private int serverPort;

    @Value("${netty.server.port}")
    private int nettyServerPort;

    @Resource
    CuratorClient curatorClient;

    /**
     * @Author fangyunhe
     * @Date 2019-10-30 11:27:22
     * @Param
     * @return void
     **/
    public void register() throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        curatorClient.registerService(ip,nettyServerPort, serverPort);
    }
}
