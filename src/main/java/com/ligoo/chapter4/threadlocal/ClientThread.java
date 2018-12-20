package com.ligoo.chapter4.threadlocal;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 15:31:12
 * @Description:
 */
public class ClientThread extends Thread {
    private Sequence sequence;

    public ClientThread(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++){
            System.out.println(Thread.currentThread().getName() + " => " + sequence.getNumber());
        }
    }
}
