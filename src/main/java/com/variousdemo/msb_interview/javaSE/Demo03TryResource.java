package com.variousdemo.msb_interview.javaSE;

/**
 * @Describe: try resource 关闭资源
 * @Author Happy
 * @Create 2023/3/19-9:11
 **/
public class Demo03TryResource {
    public static void main(String[] args) {
        try (Resource res = new Resource()) {
            res.doSome();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Resource implements AutoCloseable {
    void doSome() {
        System.out.println("do something");
    }
    
    @Override
    public void close() throws Exception {
        System.out.println("resource is closed");
    }
}
