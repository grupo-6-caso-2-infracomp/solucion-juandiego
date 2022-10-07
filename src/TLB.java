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

    public synchronized void fifo(int tag, int physPageNumber) {
        queue.add(tag);
        add(tag, physPageNumber);
        int toRemove = queue.remove();
        remove(toRemove);
    }

    public void add(int tag, int RAMPageNumber){
        while (map.size() == n){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        map.put(tag, RAMPageNumber);
    }

    public void remove(int tag){
        while (map.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        map.remove(tag);
        notify();
    }

    public synchronized int lookForTagValue(int tag){
        // looks for the index of the reference searched.
        for (int t:
             map.keySet()) {
            if (t == tag) return map.get(t);
        }
        return -2; //it's not in the TLB
    }
}
