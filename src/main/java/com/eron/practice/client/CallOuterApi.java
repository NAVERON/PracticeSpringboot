package com.eron.practice.client;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// 对外请求服务实现 

@Service 
public class CallOuterApi {

    private static final Logger log = LoggerFactory.getLogger(CallOuterApi.class);
    
    private RestTemplate client;
    
    /**
     * 定制化restTemplate 比如日志拦截
     * @author ERON
     *
     */
    private static class OuterRestTemplateCustomizer implements RestTemplateCustomizer {

        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
                
                @Override
                public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                        throws IOException {
                    log.info("请求日志");
                    log.warn("请求头 -> {}", request.getHeaders().toSingleValueMap());
                    log.warn("请求方法 -> {}", request.getMethod().name());
                    log.warn("请求url -> {}", request.getURI());
                    
                    return execution.execute(request, body);
                }
            });
        }
        
    }
    
    @Autowired  
    public CallOuterApi() {
        RestTemplateBuilder builder = new RestTemplateBuilder(new OuterRestTemplateCustomizer());
        client = builder.build();
    }
    
    @PostConstruct 
    public void test() { // 自动执行测试一下 
        // 测试请求外部url 
        String result = client.getForObject("http://jsonplaceholder.typicode.com/posts", String.class);
        
        // log.info("请求结果 ==> \n{}", result);
    }
    
}











