package com.eron.practice;

import java.util.Arrays;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication // = AutoConfiguration(EnableConfiguration)  ComponentScan  
@EnableScheduling  // 启动可定时注解 Scheduled 
// mybatis 注解配置  Mappercan 或者 Mapper 
public class PracticeSpringbootApplication implements CommandLineRunner { 
	
	private static final Logger log = LoggerFactory.getLogger(PracticeSpringbootApplication.class);
	
	@Resource 
	private Environment env; 
	
	public static void main(String[] args) { 
		SpringApplication application = new SpringApplication(PracticeSpringbootApplication.class);
		application.run(args);
		//SpringApplication.run(PracticeSpringbootApplication.class, args); 
	} 
	
	@Override 
	public void run(String... args) throws Exception {
		log.info("spring boot running..."); 
		
		log.warn("active profiles : {}", Arrays.toString(env.getActiveProfiles())); 
	}

}









