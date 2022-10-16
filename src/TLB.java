import java.util.Hashtable;
import java.util.LinkedList;

public class TLB {
    /**
     * Size of the TLB
     */
    private int n;

    /**
     * Queue for implementing FIFO algorithm
     */
    private final LinkedList<Integer> queue = new LinkedList<>();

    /**
     * Key, value pair that maps the TLB
     * key -> virtual page number
     * value -> real page number
     */
    private final Hashtable<Integer, Integer> map = new Hashtable<>(n);

    /**
     * Creates the cache used for the imaginary process
     * @param n Size of the TLB
     */
    public TLB(int n) {
        this.n = n;
    }

    /**
     * First in first out algorithm. Adds a key value pair to the map
     * @param tag virtual page number
     * @param realPageNumber
     */
    public void fifo(int tag, int realPageNumber) {
        queue.add(tag);
        map.put(tag, realPageNumber);
        if (map.size() == n+1) {
            int toRemove = queue.remove();
            map.remove(toRemove);
        }
    }

    /**
     * Looks for a given key (tag) in the map
     * @param tag virtual page number
     * @return real page number. Returns -2 if it's a TLB miss.
     */
    public int lookForTagValue(int tag){
        for (int t:
             map.keySet()) {
            if (t == tag) return map.get(t);
        }
        return -2; //it's not in the TLB
    }

    /**
     * Removes a key value pair from given key
     * @param tag virtual page number to remove
     */
    public void removeFromTLB(int tag) {
        if (map.containsKey(tag)) {
            map.remove(tag);
            queue.remove(Integer.valueOf(tag));
        }
    }
}
