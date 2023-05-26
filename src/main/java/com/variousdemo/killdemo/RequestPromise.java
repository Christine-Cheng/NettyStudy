package com.variousdemo.killdemo;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/22-17:26
 **/
public class RequestPromise {
    private UserRequest userRequest;
    private Result result;
    
    public RequestPromise(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
    
    public RequestPromise(UserRequest userRequest, Result result) {
        this.userRequest = userRequest;
        this.result = result;
    }
    
    public UserRequest getUserRequest() {
        return userRequest;
    }
    
    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
    
    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }
    
    @Override
    public String toString() {
        return "RequestPromise{" +
                "userRequest=" + userRequest +
                ", result=" + result +
                '}';
    }
}
