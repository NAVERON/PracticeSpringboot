package com.eron.practice.api;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eron.practice.model.BusinessResponseEntity;
import com.eron.practice.utils.ResponseUtils;
import com.eron.practice.utils.ZookeeperUtils;

@RestController 
@RequestMapping(value = "api/v1")
public class CommonCloudConfigApi {
	
	private static final Logger log = LoggerFactory.getLogger(CommonCloudConfigApi.class);
	
	@Resource 
	private ZookeeperUtils zkUtils;

	private final String configPath = "/common/config";  // 设置功能前缀 需要提前创建好路径
	
	@PostConstruct 
	public void init() {
		// 对于配置节点 需要提前创建好 父节点 
		this.zkUtils.creatMutiZNode(this.configPath);
	}
	
	@GetMapping(value = "config/{configName}") 
	public BusinessResponseEntity<Object> getCurrentConfig(@PathVariable(value = "configName") String configName){
		log.info("request CommonCloudConfigApi -> getCurrentConfig");
		
		try {
			zkUtils.getDataOfZNode(this.configPath + "/" + configName);
		} catch (UnsupportedEncodingException | KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return ResponseUtils.success();
	}
	
	@PostMapping(value = "config/{configName}") 
	public BusinessResponseEntity<Object> createNewConfig(@PathVariable(value = "configName") String configName, 
			@RequestBody String config) {
		log.info("request CommonCloudConfigApi -> createNewConfig, configName -> {}, config data -> {}", configName, config);
		
		byte[] configData = config.getBytes(Charset.forName("UTF-8"));
		log.info("转换成其他数据格式 => {}", configData);
		
		// 判断是否存在 节点
		zkUtils.createZNodeWithData(this.configPath + "/" + configName, configData);
		
		return ResponseUtils.success();
	}
	
	@DeleteMapping(value = "config/{configName}") 
	public BusinessResponseEntity<Object> deleteConfig(@PathVariable(value = "configName") String configName) {
		
		log.info("request CommonCloudConfigApi -> deleteConfig, configName -> {}", configName);
		
		try {
			zkUtils.deleteZNode(this.configPath + "/" + configName);
		} catch (InterruptedException | KeeperException e) {
			e.printStackTrace();
		}
		
		return ResponseUtils.success();
	}
	
	
}


















