package org.karabalin.task9;


import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PingThread extends Thread {
    private final Lock lock;

    private final Condition ping;

    private final Condition pong;
    private final AtomicBoolean flag;

    public PingThread(Lock lock, Condition ping, Condition pong, AtomicBoolean flag) {
        this.lock = lock;
        this.ping = ping;
        this.pong = pong;
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            while (true) {
                lock.lock();
                try {
                    while (!flag.get()) {
                        ping.await();
                    }
                    System.out.println("ping");
                    flag.set(false);
                    pong.signal();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PongThread extends Thread {
    private final Lock lock;

    private final Condition ping;

    private final Condition pong;
    private final AtomicBoolean flag;

    public PongThread(Lock lock, Condition ping, Condition pong, AtomicBoolean flag) {
        this.lock = lock;
        this.ping = ping;
        this.pong = pong;
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            while (true) {
                lock.lock();
                try {
                    while (flag.get()) {
                        pong.await();
                    }
                    System.out.println("pong");
                    flag.set(true);
                    ping.signal();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Task9Test {
    @Test
    public void ninthTask() {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition ping = reentrantLock.newCondition();
        Condition pong = reentrantLock.newCondition();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        PingThread pingThread = new PingThread(reentrantLock, ping, pong, atomicBoolean);
        PongThread pongThread = new PongThread(reentrantLock, ping, pong, atomicBoolean);
        pingThread.start();
        pongThread.start();
        try {
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
