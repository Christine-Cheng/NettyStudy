package com.utils;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @Describe: 声明式事务工具类
 * @Author Happy
 * @Create 2023/3/3-14:34
 **/
public class TransactionUtils {
    
    @Transactional
    public void doTx(){
        //start Tx
        TransactionUtils.doAfterTransaction(new DoTransactionCompletion(()->{
            /**
             * 达到的效果:事务成功执行之后,才会去执行此处的回调函数.诸如MQ RPC
             */
            //send MQ...   RPC...
        }));
        
        //end Tx
    
    }
    
    
    public static void doAfterTransaction(DoTransactionCompletion doTransactionCompletion){
        if (TransactionSynchronizationManager.isActualTransactionActive()) {//判断当前上下文有没有事务激活
            TransactionSynchronizationManager.registerSynchronization(doTransactionCompletion);//注册回调接口
            //此处是把当前的api实现注册到当前的事务的上下文同步器里面
        }
    }
}


/**
 * 定义一个扩展节点的实现,在事务完成之后做什么事情
 */
class DoTransactionCompletion implements TransactionSynchronization {
    
    private Runnable runnable;
    
    public DoTransactionCompletion(Runnable runnable) {
        this.runnable = runnable;
    }
    
    @Override
    public void afterCompletion(int status) {
        TransactionSynchronization.super.afterCompletion(status);
        if (status == TransactionSynchronization.STATUS_COMMITTED) {//事务成功提交的时候
            //TODO 定义回调函数,本地事务执行了之后要去做什么事情
            this.runnable.run();
        }
    }
}
