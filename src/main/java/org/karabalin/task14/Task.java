package org.karabalin.task14;

public class Task implements Executable {

    private final int num;

    public Task(int num) {
        this.num = num;
    }

    @Override
    public void execute() {
        System.out.println("Task execute " + num);
    }
}
