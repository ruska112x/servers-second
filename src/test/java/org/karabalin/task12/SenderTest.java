package org.karabalin.task12;

import org.junit.jupiter.api.Test;

public class SenderTest {
    @Test
    public void testSender() {
        final long startTime = System.currentTimeMillis();
        int size = 1000000;
        String msg = "1".repeat(size);
        Sender.spam(msg, "Ruslan", "emails.txt");
        final long endTime = System.currentTimeMillis();
        System.out.println("Start: " + startTime + ", End: " + endTime + ", Diff: " + (endTime - startTime));
    }
}
