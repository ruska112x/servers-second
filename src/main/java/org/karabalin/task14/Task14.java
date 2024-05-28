package org.karabalin.task14;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task14 {
    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("task13").build();
        parser.addArgument("-w")
                .metavar("w")
                .type(Integer.class)
                .setDefault(4);
        parser.addArgument("-r")
                .metavar("r")
                .type(Integer.class)
                .setDefault(8);
        parser.addArgument("-c")
                .metavar("c")
                .type(Integer.class)
                .setDefault(8);

        try {
            Namespace res = parser.parseArgs(args);
            int numOfWriters = res.get("w");
            int numOfReaders = res.get("r");
            int capacity = res.get("c");
            BlockingQueue<Executable> queue = new ArrayBlockingQueue<>(capacity);
            Thread[] writers = new Thread[numOfWriters];
            Thread[] readers = new Thread[numOfReaders];
            for (int i = 0; i < numOfWriters; i++) {
                writers[i] = new Thread(new ExecutableWriter(queue));
                writers[i].start();
            }
            for (int i = 0; i < numOfReaders; i++) {
                readers[i] = new Thread(new ExecutableReader(queue));
                readers[i].start();
            }
            for (int i = 0; i < numOfWriters; i++) {
                writers[i].join();
            }
            for (int i = 0; i < numOfReaders; i++) {
                readers[i].join();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
