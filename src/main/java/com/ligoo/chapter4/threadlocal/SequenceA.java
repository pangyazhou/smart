package com.ligoo.chapter4.threadlocal;

/**
 * @Author: Administrator
 * @Date: 2018/12/18 15:32:43
 * @Description:
 */
public class SequenceA implements Sequence {
    private static MyThreadLocal<Integer> numberContainer = new MyThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    @Override
    public int getNumber() {
        numberContainer.set(numberContainer.get() + 1);
        return numberContainer.get();
    }
}
