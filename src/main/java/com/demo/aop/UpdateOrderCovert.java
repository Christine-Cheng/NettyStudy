package com.demo.aop;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/3-17:24
 **/
public class UpdateOrderCovert implements Covert<UpdateOrder>{
    @Override
    public OperateLogDo covert(UpdateOrder updateOrder) {
        OperateLogDo operateLogDo = new OperateLogDo();
        operateLogDo.setOrderId(updateOrder.getOrderId());
        return operateLogDo;
    }
}
