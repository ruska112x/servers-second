package org.karabalin.task9;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PingPongThread extends Thread {
    private final Lock lock;
    private final Condition ping;
    private final Condition pong;

    private boolean flag = true;

    public PingPongThread(Lock lock) {
        this.lock = lock;
        ping = lock.newCondition();
        pong = lock.newCondition();
    }

    public void ping() {
        lock.lock();
        try {
            while (!flag) {
                ping.await();
            }
            System.out.println("ping");
            flag = false;
            pong.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void pong() {
        lock.lock();
        try {
            while (flag) {
                pong.await();
            }
            System.out.println("pong");
            flag = true;
            ping.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; ++i) {
            ping();
            pong();
        }
    }
}

