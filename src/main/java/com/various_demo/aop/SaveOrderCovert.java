package com.various_demo.aop;

/**
 * @Describe: 通过实现Covert接口,把SaveOrder的orderId字段值赋值给日志标准模型OperateLogDo
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
