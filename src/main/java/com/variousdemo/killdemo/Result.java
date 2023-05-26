package com.variousdemo.killdemo;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/22-17:27
 **/
public class Result {
    private Boolean success;
    private String msg;
    
    public boolean isSuccess() {
        return success;
    }
    
    
    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
    
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
