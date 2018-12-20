package com.ligoo.chapter4.threadlocal;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 15:33:28
 * @Description:
 */
public class Test {
    public static void main(String[] args){
        Sequence sequence = new SequenceA();
        Thread thread1 = new ClientThread(sequence);
        Thread thread2 = new ClientThread(sequence);
        Thread thread3 = new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
