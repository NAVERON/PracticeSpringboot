package com.eron.practice.utils;

public class ThreadUtils {
	
	// 根据threadid 获取thread对象 
	// 获取线程的id  Thread.currentThread().getId() 
	public static Thread getThreadByThreadId(long threadId) {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while(group != null) {
			Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];  // 获取group 所有活动线程
			int count = group.enumerate(threads, true);
			for(int i = 0; i < count; i++) {
				if(threadId == threads[i].getId()) {
					return threads[i];
				}
			}
		}
		
		return null;
	}
	
}









