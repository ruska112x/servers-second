package org.karabalin.task13;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class DataReader implements Runnable {
    private final BlockingQueue<Data> queue;

    public DataReader(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Data data = queue.take();
                System.out.println("R: " + Arrays.toString(data.get()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
