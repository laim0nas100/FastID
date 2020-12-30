package lt.lb.fastid;

/**
 *
 * Fast, counter-based, sequential (in a thread context), sortable, thread-safe
 * id for disposable object marking.
 *
 * @author laim0nas100
 */
public class FastID implements Comparable<FastID> {

    private static final ThreadLocal<FastIDGen> threadGlobal = ThreadLocal.withInitial(() -> new FastIDGen());

    /**
     * Each thread can get a global generator and use it to make some id's.
     *
     * @return
     */
    public static FastID getAndIncrementGlobal() {
        return threadGlobal.get().getAndIncrement();
    }

    /**
     * Create new FastIDGen. 
     * @return 
     */
    public static FastIDGen getNewGenerator() {
        return new FastIDGen();
    }

    private final long mark;
    private final long threadId;
    private final long num;

    public FastID(long mark, long threadId, long num) {
        this.mark = mark;
        this.threadId = threadId;
        this.num = num;
    }

    public static final String MARKER = "M";
    public static final String THREAD_ID = "T";
    public static final String NUMBER = "N";

    public FastID(String str) {

        int N = str.indexOf(NUMBER);
        int T = str.indexOf(THREAD_ID);
        int M = str.indexOf(MARKER);

        this.mark = Long.parseLong(str.substring(M + 1));
        this.threadId = Long.parseLong(str.substring(T + 1, M));
        this.num = Long.parseLong(str.substring(N + 1, T));

    }

    @Override
    public String toString() {
        return NUMBER + num + THREAD_ID + threadId + MARKER + mark;
    }

    /**
     * The mark that the FastIDGen had while generating this id.
     * @return 
     */
    public long getMark() {
        return mark;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.mark ^ (this.mark >>> 32));
        hash = 37 * hash + (int) (this.threadId ^ (this.threadId >>> 32));
        hash = 37 * hash + (int) (this.num ^ (this.num >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FastID other = (FastID) obj;
        if (this.num != other.num) {
            return false;
        }
        if (this.threadId != other.threadId) {
            return false;
        }

        return this.mark == other.mark;
    }

    @Override
    public int compareTo(FastID o) {
        if (this.threadId == o.threadId && this.mark == o.mark) {
            return Long.compare(num, o.num);
        }
        return 0;

    }

    public static int compare(FastID a, FastID b) {
        if (a == null || b == null) {
            return 0;
        }
        return a.compareTo(b);
    }

}
