package FlowSkeleton;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeCounter{
    final private AtomicInteger threadCount;

    ThreadSafeCounter(){
        threadCount = new AtomicInteger(0);
    }

    public int get(){
        return threadCount.get();
    }
//
//    public void CheckAndInc(int i){
//        threadCount.compareAndSet(i,threadCount);
//    }

        //can use to reset
    public void set(int value){
        threadCount.set(value);
    }

    public void inc(){
        threadCount.getAndIncrement();
    }

}