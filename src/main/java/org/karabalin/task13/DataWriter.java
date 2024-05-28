package org.karabalin.task13;

import java.util.concurrent.BlockingQueue;

public class DataWriter implements Runnable {
    private final BlockingQueue<Data> queue;

    public DataWriter(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("W");
                queue.put(new Data());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
