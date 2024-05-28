package org.karabalin.task13;

import java.util.concurrent.BlockingQueue;

public class Writer implements Runnable {
    private final BlockingQueue<Data> queue;

    public Writer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Write");
                queue.put(new Data());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
