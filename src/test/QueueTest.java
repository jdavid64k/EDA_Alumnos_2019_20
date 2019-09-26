package test;

import material.linear.ArrayQueue;
import material.linear.LinkedQueue;
import material.linear.Queue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {
    Queue<Integer> queue;
    final int MAX=25;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
//        queue = new ArrayQueue<>();
        queue = new LinkedQueue<>();
        for (int i = 0; i < MAX; i++) {
            queue.enqueue(i);
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        queue = null;
    }

    @org.junit.jupiter.api.Test
    void testSize() {
        assertEquals(queue.size(), MAX);
    }

    @org.junit.jupiter.api.Test
    void testIsEmpty() {
        assertFalse(queue.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void testFront() {
        assertEquals((int) queue.front(), 0);
    }

    @org.junit.jupiter.api.Test
    void testEnqueue() {
        int size = queue.size();
        for (int i=1;i<=10;i++) {
            queue.enqueue(i);
            size++;
            assertEquals(size, queue.size());
        }
    }

    @org.junit.jupiter.api.Test
    void testDequeue() {
        int actual = 0;
        while(!queue.isEmpty()){
            int element = queue.dequeue();
            assertEquals(element,actual);
            actual++;
        }
        assertTrue(queue.isEmpty());
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> queue.dequeue());
        assertEquals("Queue is empty", runtimeException.getMessage());

    }

    @org.junit.jupiter.api.Test
    void testResize() {
        //assumeTrue reports if the condition is not met and the test is stopped, a stacktrace is reported,
        //and the test marked as ignored.
        Assumptions.assumeTrue(queue instanceof ArrayQueue, "Not an instance of ArrayQueue");

        //assumingThat if a test tha only executes in certain conditions
        //if the condition is not met the test will pass.
        Assumptions.assumingThat(queue instanceof ArrayQueue,
                ()->{
                    int start = 100;
                    int maxElements = 500;

                    //Empty Queue
                    while(!queue.isEmpty()) {
                        queue.dequeue();
                    }

                    //fill and exceed default capacity
                    for (int i = 0; i <maxElements ; i++) {
                        queue.enqueue(i+start);
                    }

                    //retrieve all new elements
                    int actual = start;
                    while (!queue.isEmpty()) {
                        int element = queue.dequeue();
                        assertEquals(element, actual);
                        actual++;
                    }
                    RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> queue.dequeue());
                    assertEquals("Queue is empty", runtimeException.getMessage());

                });

    }
}