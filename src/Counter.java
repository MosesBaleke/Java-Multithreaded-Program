// A Java program that demonstrates multithreading.
// The program uses a counter to increment and decrement.
import java.util.concurrent.Semaphore;

// Extending counter class with Thread
// to enable threads creation
public class Counter extends Thread {
    // Declaring a Semaphore
    private final Semaphore sem;
    // counter to be shared by threads
    static int counter = 0;

    // constructor that takes in a Semaphore and a name
    public Counter(Semaphore sem, String threadName) {
        super(threadName);
        this.sem = sem;
    }

    // Main program method
    public static void main(String[] args) throws InterruptedException {
        // creating a Semaphore object
        // with number of permits 1
        Semaphore sem = new Semaphore(1);

        // creating thread one with name and start it
        Counter md1 = new Counter(sem, "Thread One");
        md1.start();
        md1.join();

        // creating thread Two with name and start it
        Counter md2 = new Counter(sem, "Thread Two");
        md2.start();
        md2.join();
    }

    @Override
    public void run() {
        // Thread method with logic to handle the
        // the order of thread execution
        if (counter == 0) {
            increaseCounter();
        } else {
            decreaseCounter();
        }
    }

    // method for incrementing counter
    private void increaseCounter() {
        // surrounding code block with try catch for possible errors
        try {
            // Beginning the lock to avoid share resource interruption in loop
            sem.acquire();
            System.out.println("...................Starting " + Thread.currentThread().getName() + "...................");
            System.out.println(Thread.currentThread().getName() + " : " + counter);
            for (int i = 0; i < 20; i++) {
                //incrementing and displaying counter value in loop
                counter++;
                System.out.println(Thread.currentThread().getName() + " : " + counter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " Finished");
            // unlocking the resource
            sem.release();
        }

    }

    // method for decrementing counter
    private void decreaseCounter() {
        // surrounding code block with try catch for possible errors
        try {
            // Beginning the lock to avoid share resource interruption in loop
            sem.acquire();
            System.out.println("...................Starting " + Thread.currentThread().getName() + "...................");
            for (int i = 0; i < 20; i++) {
                //incrementing and displaying counter value in loop
                counter--;
                System.out.println(Thread.currentThread().getName() + " : " + counter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " Finished");
            // unlocking the resource
            sem.release();
        }
    }
}