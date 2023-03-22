package com.various_demo.aop;

import org.springframework.stereotype.Service;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/3-15:04
 **/
@Service
public class OrderService {
    
    @RecordOperate(desc = "保存订单", covert=SaveOrderCovert.class)
    public Boolean saveOrder(SaveOrder saveOrder) {
        System.out.println("save order, orderId= " + saveOrder.getId());
        return true;
    }
    
    @RecordOperate(desc = "更新订单",covert = UpdateOrderCovert.class)
    public Boolean updateOrder(UpdateOrder updateOrder) {
        System.out.println("update order, orderId= " + updateOrder.getOrderId());
        return true;
    }
    
}
