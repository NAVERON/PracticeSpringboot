package com.eron.practice.service.serviceImpl;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.eron.practice.service.FileStoraeService;

/**
 * 目的 ： 提供单纯对文件存储的基础服务 
 * 文件存储目录设置, 文件id生成, 文件id查询, 文件结构设计 
 * @author eron
 *
 */

@Service 
public class FileStorageServiceImpl implements FileStoraeService {

	@PostConstruct 
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String store() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String load() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

}







