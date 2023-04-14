package com.wddmg.concurrent.test;

import com.sun.corba.se.impl.activation.ProcessMonitorThread;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duym
 * @version $ Id: Test13, v 0.1 2023/04/04 11:18 duym Exp $
 */
@Slf4j
public class Test13 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();

        Thread.sleep(3500);
        log.debug("停止监控");
        tpt.stop();
    }
}

@Slf4j
class TwoPhaseTermination{
    private Thread monitor;

    private volatile boolean stop;

    // 判断是否执行过start方法
    private boolean starting = false;
    // 启动监控线程
    public  void start(){
        synchronized (this){
            if(starting){
                return;
            }
            starting = true;
        }
        monitor = new Thread(()->{
            while(true){
                Thread current = Thread.currentThread();
                if(stop){
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置打断标记
                    current.interrupt();
                }
            }
        });
        monitor.start();

    }

    // 停止监控线程
    public void stop(){
        stop = true;
        monitor.interrupt();
    }
}