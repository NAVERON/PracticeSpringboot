package com.eron.practice.model;

import java.time.LocalDateTime;

public class KafkaMessage {

    private Long messageId;
    private String businessName;  // topic 业务名称
    private String jsonData;
    private LocalDateTime logTime;
    
    public KafkaMessage(Long messageId, String businessName, String jsonData) {
        this.messageId = messageId;
        this.businessName = businessName;
        this.jsonData = jsonData;
        this.logTime = LocalDateTime.now();
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getJsonData() {
        return jsonData;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }
    
    
}






