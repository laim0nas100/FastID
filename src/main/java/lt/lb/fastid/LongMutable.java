package lt.lb.fastid;

/**
 * Minimal long mutable without overkill that is AtomicLong.
 *
 * @author laim0nas100
 */
class LongMutable {

    private long num;

    public LongMutable() {
        this(0L);
    }

    public LongMutable(long num) {
        this.num = num;
    }

    public long get() {
        return num;
    }

    public long incGet() {
        return ++num;
    }

    public long getInc() {
        return num++;
    }
}
