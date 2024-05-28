package org.karabalin.task14;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ExecutableWriter implements Runnable {
    private BlockingQueue<Executable> queue;

    public ExecutableWriter(BlockingQueue<Executable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.put(new Task(new Random().nextInt(0, 9)));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
