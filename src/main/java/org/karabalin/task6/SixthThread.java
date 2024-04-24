package org.karabalin.task6;

public class SixthThread extends Thread {
    private final SixthTask sixthTask;

    private boolean add;

    public SixthThread(SixthTask sixthTask, boolean add) {
        this.sixthTask = sixthTask;
        this.add = add;
    }

    @Override
    public void run() {
        if (add) {
            for (int i = 0; i < 10000; ++i) {
                sixthTask.adding();
                System.out.println("A" + i);
                synchronized (sixthTask) {
                    sixthTask.notify();
                }
            }
        } else {
            for (int i = 0; i < 10000; ++i) {
                if (sixthTask.isEmpty()) {
                    synchronized (sixthTask) {
                        try {
                            sixthTask.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                sixthTask.subtracting();
                System.out.println("D" + i);
            }
        }
    }
}
