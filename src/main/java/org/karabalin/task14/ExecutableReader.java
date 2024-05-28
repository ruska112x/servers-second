package org.karabalin.task14;

import java.util.concurrent.BlockingQueue;

public class ExecutableReader implements Runnable {
    private BlockingQueue<Executable> queue;

    public ExecutableReader(BlockingQueue<Executable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Executable task = queue.take();
                task.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
