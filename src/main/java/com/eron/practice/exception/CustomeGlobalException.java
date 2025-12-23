package com.eron.practice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class CustomeGlobalException extends Exception {
	
	@Serial
	private static final long serialVersionUID = 699507205888446901L;
	
	private static final Logger log = LoggerFactory.getLogger(CustomeGlobalException.class);
	
	public CustomeGlobalException(String message) {
		super(message);
		log.error("CustomeGlobalException message, ERROR: {}", message);
	}
}
