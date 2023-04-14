package com.duym.concurrent;

/**
 *
 * @author duym
 * @version $ Id: MyArrayQueue, v 0.1 2023/03/10 16:22 duym Exp $
 */
public class MyArrayQueue {


    private String[] data = new String[10];

    private int putIndex = 0;

    private int getIndex = 0;

    private int maxSize;

    public synchronized void put(String element) throws InterruptedException {
        //满了阻塞
        if(maxSize == data.length){
            wait();
        }
        data[putIndex] = element;
        notifyAll();
        maxSize++;
        putIndex++;
        if(putIndex == data.length){
            putIndex = 0;
        }
    }

    public synchronized String take() throws InterruptedException {
        if(maxSize == 0){
            wait();
        }
        String result = data[getIndex];
        notifyAll();
        --maxSize;
        ++getIndex;
        if(getIndex == data.length){
            getIndex = 0;
        }
        return result;
    }
}
