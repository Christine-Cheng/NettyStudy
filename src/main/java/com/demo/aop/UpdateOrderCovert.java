package com.demo.aop;

/**
 * @Describe: 通过实现Covert接口,把UpdateOrder的id字段值赋值给日志标准模型OperateLogDo
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
