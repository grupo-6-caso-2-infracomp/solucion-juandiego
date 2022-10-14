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
        map.put(tag, physPageNumber);
        if (map.size() >= n+1) {
            int toRemove = queue.remove();
            map.remove(toRemove);
        }
    }

    public int lookForTagValue(int tag){
        System.out.println("TLB: " + map);
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

    public void removeFromTLB (int tag) {
        if (map.contains(tag)) {
            map.remove(tag);
            queue.remove(Integer.valueOf(tag));
        }
    }
}
