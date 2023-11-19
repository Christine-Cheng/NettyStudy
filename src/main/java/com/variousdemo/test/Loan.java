package com.variousdemo.test;

public class Loan{
    public static void main(String[] args) {
    
        double A = 50 * 10000.0;
        double P = 0.05;
        int m = 360;
    
        Loan dk = new Loan(A, m, P);
        System.out.println(String.format("%.2f", dk.getX()));
    
        for (int i = 1; i < 4; i++) {
            System.out.println(String.format("%.2f, %.2f", dk.getAi(i), dk.getBi(i)));
        }
        
    }
    
    /**
     * 等额本息还款金额计算类
     */
    private double A; // 贷款总额
    private double P; // 年利率
    private double p; // 月利率
    private int m; // 贷款期限（月）
    
    private double a1; // 第1个月所还本金
    private double b1; // 第1个月所还利息
    private double x; // 每月所还金额
    
    /**
     * @param A
     *          贷款总额
     * @param m
     *          贷款期限（月）
     * @param P
     *          年利率
     */
    public Loan(double A, int m, double P) {
        this.A = A;
        this.m = m;
        this.P = P;
        this.p = this.P / 12;
        
        this.b1 = A * p;
        this.a1 = A * p / (Math.pow(1 + p, m) - 1);
        this.x = a1 + b1;
    }
    
   
    
    /**
     * @return 每月所还金额
     */
    public double getX() {
        return this.x;
    }
    
    /**
     * @param i
     *          月份
     * @return 返回第i个月所还本金
     */
    public double getAi(int i) {
        return this.a1 * Math.pow(1 + p, i - 1);
    }
    
    /**
     * @param i
     *          月份
     * @return 返回第i个月所还利息
     */
    public double getBi(int i) {
        return this.x - getAi(i);
    }
    
    /**
     * @param i
     *          月份
     * @return 返回到第i月所还本金总和
     */
    public double getASum(int i) {
        double sum = 0.0;
        for (int j = 1; j <= i; j++) {
            sum += getAi(j);
        }
        return sum;
    }
    
    /**
     * @param i
     *          月份
     * @return 返回到第i月所还利息总和
     */
    public double getBSum(int i) {
        double sum = 0.0;
        for (int j = 1; j <= i; j++) {
            sum += getBi(j);
        }
        return sum;
    }
}

