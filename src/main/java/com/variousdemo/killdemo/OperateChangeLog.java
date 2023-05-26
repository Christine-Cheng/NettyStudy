package com.variousdemo.killdemo;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/22-17:25
 **/
public class OperateChangeLog {
    private Long orderId;
    private Integer count;
    // 1-扣减，2-回滚
    private Integer operateType;
    
    public OperateChangeLog(Long orderId, Integer count, Integer operateType) {
        this.orderId = orderId;
        this.count = count;
        this.operateType = operateType;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public Integer getOperateType() {
        return operateType;
    }
    
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
    
    @Override
    public String toString() {
        return "OperateChangeLog{" +
                "orderId=" + orderId +
                ", count=" + count +
                ", operateType=" + operateType +
                '}';
    }
}
