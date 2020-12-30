package lt.lb.fastid;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * Fast, counter-based, sequential, sortable, thread-safe id generator for
 * disposable object marking.
 *
 * @author laim0nas100
 */
public class FastIDGen {

    private static AtomicLong markerCounter = new AtomicLong(0L);
   
    private final long marker;

    private final ThreadLocal<LongMutable> counter = ThreadLocal.withInitial(() -> new LongMutable(0));

    private FastIDGen(long mark) {
        this.marker = mark;
    }

    public FastIDGen() {
        this(markerCounter.getAndIncrement());
    }

    public FastID get() {
        return new FastID(marker, Thread.currentThread().getId(), counter.get().get());
    }

    public FastID getAndIncrement() {
        return new FastID(marker, Thread.currentThread().getId(), counter.get().getInc());
    }

}
