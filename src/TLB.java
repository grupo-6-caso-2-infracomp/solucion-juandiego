import java.util.Hashtable;
import java.util.LinkedList;

public class TLB {
    // cache to use. Algorithm: FIFO
    private int n;
    private final LinkedList<Integer> queue = new LinkedList<>();
    private final Hashtable<Integer, Integer> map = new Hashtable<>(n);

    public TLB(int n) {
        this.n = n;
    }

    public void fifo(int tag, int physPageNumber) {
        queue.add(tag);
        add(tag, physPageNumber);
        if (map.size() >= n+1) {
            int toRemove = queue.remove();
            remove(toRemove);
        }
    }

    public synchronized void add(int tag, int RAMPageNumber){
        map.put(tag, RAMPageNumber);
    }

    public synchronized void remove(int tag){
        map.remove(tag);
    }

    public synchronized int lookForTagValue(int tag){
        // looks for the index of the reference searched.
        for (int t:
             map.keySet()) {
            if (t == tag) return map.get(t);
        }
        return -2; //it's not in the TLB
    }

    public Hashtable<Integer, Integer> getMap() {
        return map;
    }

    public void updateTLBItem(int kick, int virtualPageNumber) {
        map.put(virtualPageNumber, kick);
    }
}
