package org.karabalin.task7;

import java.util.concurrent.atomic.AtomicBoolean;

public class PongThread extends Thread {

    private final Object lock;
    private AtomicBoolean flag;

    public PongThread(Object lock, AtomicBoolean flag) {
        this.lock = lock;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (flag.get()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("pong");
                flag.set(true);
                lock.notify();
            }
        }
    }
}
