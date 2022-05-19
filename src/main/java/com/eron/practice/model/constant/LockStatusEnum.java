package com.eron.practice.model.constant;

public enum LockStatusEnum {

    // boolean + message --> boolean = final status, message = business things
    LOCK_SUCCESS(Boolean.TRUE, "获取锁成功", 0), 
    LOCK_FAIL(Boolean.FALSE, "加锁失败", -1),
    RELEASE_SUCESS(Boolean.TRUE, "释放锁成功", 0), 
    RELEASE_FAIL(Boolean.FALSE, "解锁失败", -1)
    ;

    private Boolean status;  // 布尔值示意
    private String message;  // 中文解释
    private Integer type;  // 多状态功能

    LockStatusEnum(Boolean status, String message, Integer type) {
        this.status = status;
        this.message = message;
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
