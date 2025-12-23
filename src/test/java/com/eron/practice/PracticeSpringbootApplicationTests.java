package com.eron.practice;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PracticeSpringbootApplicationTests { 
	
	private static final Logger log = LoggerFactory.getLogger(PracticeSpringbootApplicationTests.class);

	@Test
	void contextLoads() {
		log.info("hello test unit");
	}

}
