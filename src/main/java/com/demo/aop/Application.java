package com.demo.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Describe:
 * @Author Happy
 * @Create 2023/3/3-15:11
 **/
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Application implements CommandLineRunner{
    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
    
    @Autowired
    private OrderService orderService;
    
    @Override
    public void run(String... args) throws Exception {
        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(1L);
        orderService.saveOrder(saveOrder);
        
        
        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(2L);
        orderService.updateOrder(updateOrder);
    }
}
