package com.eveong.myim.server.zk;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eveong.myim.server.zk.instance.MyimServiceInstance;
import com.eveong.myim.server.zk.instance.Payload;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author:fangyunhe
 * @time:2019年10月23日 上午11:59:35
 * @version 1.0
 */
@Slf4j
@Component
public class CuratorClient {

	@Value("${server.port}")
	private int serverPort;

	@Value("${netty.server.port}")
	private int nettyServerPort;

	@Value("${zk.address}")
	private String zkAddress;

	private int sessionTimeoutMs = 1000;
	private int maxRetries = 5;
	private int connectionTimeoutMs = 1000;

	private CuratorFramework curatorClient;

	@PostConstruct
	public void init() throws InterruptedException {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(sessionTimeoutMs, maxRetries);
		// 实例化Curator客户端，Curator的编程风格可以让我们使用方法链的形式完成客户端的实例化
		// 使用工厂类来建造客户端的实例对象
		curatorClient = CuratorFrameworkFactory.builder()
				// 放入zookeeper服务器ip
				.connectString(zkAddress)
				// 设定会话时间以及重连策略
				.sessionTimeoutMs(connectionTimeoutMs).retryPolicy(retryPolicy)
				// 建立连接通道
				.build();
		// 启动Curator客户端
		curatorClient.start();
		curatorClient.blockUntilConnected();
	}

	/**
	 * 新建节点
	 * 
	 * @param rootPath
	 * @param subPath
	 * @throws Exception
	 * @author:fangyunhe
	 * @time:2019年10月24日 下午4:30:25
	 */
	public void createNode(String rootPath, String subPath) throws Exception {
		curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
				.forPath(rootPath + "/" + subPath);
	}

	/**
	 * 服务注册
	 * 
	 * @throws Exception
	 * @author:fangyunhe
	 * @time:2019年10月24日 下午4:31:02
	 */
	public void registerService() throws Exception {
		// 服务构造器
		ServiceInstanceBuilder<Payload> sib = ServiceInstance.builder();

		// 添加payload
		Payload payload = new Payload();
		String ip = InetAddress.getLocalHost().getHostAddress();
		payload.setIp(ip);
		payload.setNettyPort(nettyServerPort);
		payload.setHttpPort(serverPort);

		// 实例服务
		ServiceInstance<Payload> serviceInstance = sib.name(MyimServiceInstance.INSTANCE_NAME).payload(payload).build();

		// 构建 ServiceDiscovery 用来注册服务
		ServiceDiscovery<Payload> serviceDiscovery = ServiceDiscoveryBuilder.builder(Payload.class)
				.client(curatorClient).serializer(new JsonInstanceSerializer<Payload>(Payload.class))
				.basePath(MyimServiceInstance.ROOT_PATH).build();
		// 服务注册
		serviceDiscovery.registerService(serviceInstance);
		serviceDiscovery.start();

		log.info("服务注册成功");

	}

	/**
	 * 获取注册实例信息
	 * 
	 * @throws Exception
	 * @author:fangyunhe
	 * @time:2019年10月24日 下午4:35:29
	 */
	public Payload getRegisterInstance() throws Exception {
		ServiceDiscovery<Payload> serviceDiscovery = ServiceDiscoveryBuilder.builder(Payload.class)
				.client(curatorClient).basePath(MyimServiceInstance.ROOT_PATH)
				.serializer(new JsonInstanceSerializer<Payload>(Payload.class)).build();
		serviceDiscovery.start();

		// 根据名称获取服务
		Collection<ServiceInstance<Payload>> services = serviceDiscovery.queryForInstances(MyimServiceInstance.INSTANCE_NAME);
		if (CollectionUtils.isEmpty(services)) {
			log.info("当前没有发现服务");
			return null;
		}
		List<ServiceInstance<Payload>> list = (List<ServiceInstance<Payload>>) services;
		//随机获取
		Collections.shuffle(list);
		ServiceInstance<Payload> serviceInstance = list.get(0);
		Payload payload = serviceInstance.getPayload();
		log.info(payload.toString());
		return payload;
	}
}
