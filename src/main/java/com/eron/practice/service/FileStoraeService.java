package com.eron.practice.service;

/**
 * 文件存储服务 
 * @author eron
 *
 */
public interface FileStoraeService {
	
	// 初始化存储环境 
	public void init();
	// 保存文件 
	public String store();
	// 获取单个文件 
	public String load();
	// 删除文件
	public String delete();
	
}









