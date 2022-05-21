package com.eron.practice.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eron.practice.config.zookeeper.ZookeeperWatcher;
import com.eron.practice.model.constant.LockStatusEnum;

@Component
public class ZookeeperUtils {

    private static final Logger log = LoggerFactory.getLogger(ZookeeperUtils.class);

//    @Value(value = "${spring.zookeeper.server.url}")
//    private String serverUrl;
//    @Value(value = "${spring.zookeeper.connection.timeout}")
//    private Integer connectionTimeout;

    private ZooKeeper zkClient = null; // 初始化后可以直接使用
    
    // 初始化对象直接生成zkclient连接
    public ZookeeperUtils(@Value(value = "${spring.zookeeper.server.url}") String serverUrl, 
    		@Value(value = "${spring.zookeeper.connection.timeout}") Integer connectionTimeout) throws IOException {
    	log.info("初始化zookeeper客户端");
        // 这里需要修正, 注册watcher应当使用注册dispatcher 应对不同的业务 
    	this.zkClient = new ZooKeeper(serverUrl, connectionTimeout, new ZookeeperWatcher());
    	log.info("zookeeper client stats : {}", this.zkClient.getState().toString());
    }
    
    public ZooKeeper getClientInstance() {  // 获取zkclient实例 
        return this.zkClient;
    }

    @PostConstruct 
    public void init() {  // 锁的实现需要提前部署好 
        // 判断锁节点路径是否存在  如果不存在则创建
		try {
			Stat stat = this.zkClient.exists(this.exclusiveLockPath, false);
	        if(stat == null) {
	        	log.info("当前锁节点不存在， 需要手动创建"); 
	        	// 实现多级节点的创建
	            //String node = this.zkClient.create(this.exclusiveLockPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	        	String node = this.creatMutiZNode(this.exclusiveLockPath);
	        	log.info("创建锁根节点 => {}", node);
	        }
	        
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void initPath() {
    	// 对外提供创建多功能节点 
    }

    public List<String> getChildrenOfZNode(String nodePath) throws KeeperException, InterruptedException {
        List<String> all = this.zkClient.getChildren(nodePath, true);
        log.info("get all nodes of nodePath, {} => {}", nodePath, all.toString());
        
        return all;
    }

    public String createZNodeWithData(String nodePath, byte[] nodeData) {
        String createresult = "";  // 初始化

		try {
			String x = new String(nodeData, "UTF-8");
			log.info("检查是否没有变化 {}", x);
			Stat stat = this.zkClient.exists(nodePath, false);
			
	        if(stat == null) {
	        	// 肯呢个上一级节点不存在  这里简化处理， 不做多余判断 
		        createresult = this.zkClient.create(nodePath, nodeData, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		        log.info("create node result : {}", createresult);
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        return createresult;
    }
    
    /**
     * 一次性创建多级路径 不插入数据， 数据后续在插入
     * 比如创建 /root/common/test/hello 一次性循环创建多级
     * @param fullPath
     * @return 创建节点的结果 
     */
    public String creatMutiZNode(String fullPath) {
    	fullPath = fullPath.substring(1);  // 去除最前面的 ‘/’
    	List<String> fullPathSplitList = Arrays.asList(fullPath.split("/"));  // 只需要读取 不修改 无影响
    	
    	StringBuilder partPath = new StringBuilder();  // 辅助路径层级迭代
    	fullPathSplitList.forEach(s -> {  // 对于每个路径节点, 检查路径节点是否存在, 不存在则创建, 存在则进入下一个循环
    		partPath.append("/").append(s);
    		log.info("当前节点路径 检查 -> {}", partPath.toString());
    		try {
				Stat stat = this.zkClient.exists(partPath.toString(), false);
				if(stat == null) {
					String tmpPath = this.zkClient.create(partPath.toString(), new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.info("创建新的节点 -> {}", tmpPath);
				}
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
    	});
    	
    	return partPath.toString();
    }
    public String getDataOfZNode(String nodePath)
            throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] result = this.zkClient.getData(nodePath, true, null);
        String resultString = new String(result, "UTF-8");

        log.info("get data result : {}, toString {}", result, resultString);
        
        return resultString;
    }

    public void deleteZNode(String nodePath) throws InterruptedException, KeeperException {
        this.zkClient.delete(nodePath, -1);

        log.info("delete znode : {}", nodePath);
    }

    public void updateZNode(String nodePath, byte[] nodeData) throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        // 第三个参数表示当前节点的数据版本，一般先获取数据stat，然后指定数据版本

        int version = this.zkClient.exists(nodePath, true).getVersion();
        stat = this.zkClient.setData(nodePath, nodeData, version);
        
        log.info("stat changed => {}", stat.toString());
    }
    
    public void registZNodeWatcher(String path, Watcher watcher) {
        // 实现注册监听功能 对外提供, 可以通过exists getchildren getdata方法调用时注册 watcher
        try {
            this.zkClient.exists(path, watcher);  // 节点注册监听只有1次 
        } catch (KeeperException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * 获取锁 排他锁 
     * key 表示对不同的业务获取不同的锁, 不同业务获取锁不影响 
     * @throws InterruptedException 
     * @throws KeeperException 
     */
    // zookeeper 实现分布式锁  在分布式系统中, 提供全局 唯一的协议锁 
    // 基于cruator实现和使用zookeeper基础实现
    private String exclusiveLockPath = "/common/exclusivelocker";  // 锁前缀
    private String commonLockPrefix = "LOCK_";
    private ThreadLocal<String> lockedPath = new ThreadLocal<String>();
    private Watcher lockWatcher = new Watcher() {
        @Override 
        public void process(WatchedEvent event) {
            // 实现锁节点监听 
            if(event.getType() == EventType.NodeDeleted) {
                log.info("节点被删除 -> {}", event.getPath());
            }
        }
    };
    
    public LockStatusEnum tryexclusiveLock(String key) throws KeeperException, InterruptedException {
        // 加锁 创建临时有序节点  排他锁可以使用单个临时节点直接逻辑, 但是为了避免惊群效应, 使用临时有序子节点的方式
        String curLock = this.zkClient.create(this.exclusiveLockPath + "/" + commonLockPrefix, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL); 
        log.warn("创建的临时节点 => {}", curLock);
        this.lockedPath.set(curLock);
        
        List<String> lockerKeys = this.zkClient.getChildren(this.exclusiveLockPath, false);  // 获取锁节点下的所有节点名称 不是全路径名 
        Collections.sort(lockerKeys);
        // 获取节点名称 末尾值
        Integer curIndex = lockerKeys.indexOf(curLock.substring(this.exclusiveLockPath.length()+1));
        //判断当前枷锁是否是最小值 最小值枷锁成功 否则失败 
        if (curIndex == 0) {
            log.info("加锁成功");
            return LockStatusEnum.LOCK_SUCCESS;
        } else {
            // 判断上一个节点的状态 
            String preNode = lockerKeys.get(curIndex - 1);
            Stat stat = this.zkClient.exists(this.exclusiveLockPath + "/" + preNode, this.lockWatcher);  // 检查是否存在节点 
            
            if(stat == null) {
                return LockStatusEnum.LOCK_FAIL;
            }
        }
        
        return LockStatusEnum.LOCK_FAIL;
    }

    public LockStatusEnum releaseexclusiveLock(String key) throws InterruptedException, KeeperException {
        // 释放锁
        this.zkClient.delete(this.lockedPath.get(), -1);
        this.lockedPath.remove();
        
        return LockStatusEnum.RELEASE_SUCESS;
    }
    
    
}














