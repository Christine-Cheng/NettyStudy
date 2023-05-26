package com.variousdemo;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @Describe: 声明式事务工具类
 * 【【Java高级】如何写出优雅可靠事务代码？基于Spring事务扩展】
 * https://www.bilibili.com/video/BV168411h7PU/?share_source=copy_web&vd_source=5f66f394e277af98190ae0ed6c364b6b
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
    
    
    /**
     * doAfterTransaction: 事务执行成功后,进行处理其他
     * 1.先判断上下文是否有事务激活
     * 2.注册接口
     * 3.把当前的api实现注册到当前的事务的上下文同步器里面
     * @param doTransactionCompletion
     */
    public static void doAfterTransaction(DoTransactionCompletion doTransactionCompletion){
        if (TransactionSynchronizationManager.isActualTransactionActive()) {//判断当前上下文有没有事务激活
            TransactionSynchronizationManager.registerSynchronization(doTransactionCompletion);//注册回调接口
            //此处是把当前的api实现注册到当前的事务的上下文同步器里面
        }
    }
}


/**
 * 定义一个扩展节点的实现,在事务完成之后做什么事情
 * 实现Spring的一个SPI
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
    
    
    //@Autowired
    //private TransactionTemplate transactionTemplate;
    //public void testTransaction2() {
    //    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
    //        @Override
    //        protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
    //            try {
    //                // ....  业务代码
    //            } catch (Exception e) {
    //                //回滚
    //                transactionStatus.setRollbackOnly();
    //            }
    //        }
    //    });
    //}
    //
    //@Autowired
    //private PlatformTransactionManager transactionManager;
    //public void testTransaction() {
    //
    //    TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    //    try {
    //        // ....  业务代码
    //        transactionManager.commit(status);
    //    } catch (Exception e) {
    //        transactionManager.rollback(status);
    //    }
    //}
}
