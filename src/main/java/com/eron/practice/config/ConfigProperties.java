package com.eron.practice.config;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration 
@ConfigurationProperties(prefix = "spring.auth0.jwt") 
public class ConfigProperties {
	
	public Long tokenValidity;  // com.eron.secret 
	public String tokenHead;  // com.eron.test 
	public String tokenPrefix;  // com.eron.user-name 
	public String tokenSecret;
	
	@PostConstruct 
	public void init () {
		System.out.println(this.toString());
	}
	
	public Long getTokenValidity() {
		return tokenValidity;
	}

	public void setTokenValidity(Long tokenValidity) {
		this.tokenValidity = tokenValidity;
	}

	public String getTokenHead() {
		return tokenHead;
	}

	public void setTokenHead(String tokenHead) {
		this.tokenHead = tokenHead;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	@Override
	public String toString() {
		return "ConfigProperties [tokenValidity=" + tokenValidity + ", tokenHead=" + tokenHead + ", tokenPrefix="
				+ tokenPrefix + ", tokenSecret=" + tokenSecret + "]";
	}
	
	
}










