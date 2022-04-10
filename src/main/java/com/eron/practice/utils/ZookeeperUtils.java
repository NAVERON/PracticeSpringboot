package com.eron.practice.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eron.practice.config.ZookeeperWatcher;


@Component 
public class ZookeeperUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ZookeeperUtils.class);
	
	@Value(value = "${spring.zookeeper.server.url}") 
	private String serverUrl;
	@Value(value = "${spring.zookeeper.connection.timeout}") 
	private Integer connectionTimeout;
	@Resource 
	private ZookeeperWatcher zkWatcher;
	
	private static ZooKeeper zkClient = null;
	
	@PostConstruct 
	public void initialZkClient () throws IOException, InterruptedException {
		zkClient = new ZooKeeper(serverUrl, connectionTimeout, zkWatcher);
		
		Thread.sleep(1000);
		log.info("zookeeper client stats : {}", zkClient.getState().toString());
	}
	
	public void getChildrenOfZNode(String nodePath) throws KeeperException, InterruptedException {
		List<String> all = zkClient.getChildren(nodePath, true);
		log.info("get all nodes of nodePath, {} => {}", nodePath, all.toString());
	}
	
	public void createZNode(String nodePath, byte[] nodeData) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		String x = new String(nodeData, "UTF-8");
		log.info("检查是否没有变化 {}", x);
		
		String createresult = zkClient.create(nodePath, nodeData, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		log.info("create node result : {}", createresult);
	}
	
	public void getDataOfZNode(String nodePath) throws KeeperException, InterruptedException, UnsupportedEncodingException {
		byte[] result = zkClient.getData(nodePath, true, null);
		String resultString = new String(result, "UTF-8");
		
		log.info("get data result : {}, toString {}", result, resultString);
	}
	
	public void deleteZNode(String nodePath) throws InterruptedException, KeeperException {
		zkClient.delete(nodePath, -1);
		
		log.info("delete znode : {}", nodePath);
	}
	
	public void updateZNode(String nodePath, byte[] nodeData) throws KeeperException, InterruptedException {
		Stat stat = new Stat();
        //第三个参数表示当前节点的数据版本，一般先获取数据stat，然后指定数据版本
		
		int version = zkClient.exists(nodePath, true).getVersion();
        zkClient.setData(nodePath, nodeData, version);
	}
	
}








