package com.eron.practice.api;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

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

import com.eron.practice.model.ResponseEntity;
import com.eron.practice.utils.ResponseUtils;
import com.eron.practice.utils.ZookeeperUtils;

@RestController 
@RequestMapping(value = "api/v1")
public class CommonCloudConfigApi {
	
	private static final Logger log = LoggerFactory.getLogger(CommonCloudConfigApi.class);
	
	@Resource 
	private ZookeeperUtils zkUtils;

	private static final String nodePath = "/config";
	
	@GetMapping(value = "config/{configName}") 
	public ResponseEntity<Object> getCurrentConfig(@PathVariable(value = "configName") String configName){
		log.info("request CommonCloudConfigApi -> getCurrentConfig");
		
		try {
			zkUtils.getDataOfZNode(nodePath + "/" + configName);
		} catch (UnsupportedEncodingException | KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseUtils.success();
	}
	
	@PostMapping(value = "config/{configName}") 
	public ResponseEntity<Object> createNewConfig(@PathVariable(value = "configName") String configName, 
			@RequestBody String config) {
		log.info("request CommonCloudConfigApi -> createNewConfig, configName -> {}, config data -> {}", configName, config);
		
		byte[] configData = config.getBytes(Charset.forName("UTF-8"));
		log.info("转换成其他数据格式 => {}", configData);
		
		try {
			zkUtils.createZNode(nodePath + "/" + configName, configData);
		} catch (KeeperException | InterruptedException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseUtils.success();
	}
	
	@DeleteMapping(value = "config/{configName}") 
	public ResponseEntity<Object> deleteConfig(@PathVariable(value = "configName") String configName) {
		
		log.info("request CommonCloudConfigApi -> deleteConfig, configName -> {}", configName);
		
		try {
			zkUtils.deleteZNode(nodePath + "/" + configName);
		} catch (InterruptedException | KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseUtils.success();
	}
	
	
}


















