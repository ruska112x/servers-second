package org.karabalin.task5;

public class FifthThread extends Thread {

    private final FifthTask fifthTask;

    private boolean add;

    public FifthThread(FifthTask fifthTask, boolean add) {
        this.fifthTask = fifthTask;
        this.add = add;
    }

    @Override
    public void run() {
        if (add) {
            for (int i = 0; i < 10000; ++i) {
                fifthTask.adding();
                System.out.println("A" + i);
                synchronized (fifthTask) {
                    fifthTask.notify();
                }
            }
        } else {
            for (int i = 0; i < 10000; ++i) {
                synchronized (fifthTask) {
                    if (fifthTask.isEmpty()) {
                        try {
                            fifthTask.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                fifthTask.subtracting();
                System.out.println("D" + i);
            }
        }
    }
}
