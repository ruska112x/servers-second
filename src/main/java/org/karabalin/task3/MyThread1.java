package org.karabalin.task3;

public class MyThread1 extends Thread {

    private final String name;

    public MyThread1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Run method from " + name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("After sleep");
    }
}
