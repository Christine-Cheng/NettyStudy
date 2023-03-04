package com.demo.aop;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/3-17:22
 **/
public class SaveOrderCovert implements Covert<SaveOrder>{
    @Override
    public OperateLogDo covert(SaveOrder saveOrder) {
        OperateLogDo operateLogDo = new OperateLogDo();
        operateLogDo.setOrderId(saveOrder.getId());
        return operateLogDo;
    }
}
