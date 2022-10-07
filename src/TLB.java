import java.util.ArrayList;

public class TLB {
    // cache to use. the aging algorithm will be applied here. Algorithm: FIFO
    private int n;
    private ArrayList<Integer> pages = new ArrayList<Integer>(n);

    public TLB(int n) {
        this.n = n;
    }

    public synchronized void add(int element){
        while (pages.size() == n){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        pages.add(element);
    }

    public synchronized void remove(int element){
        while (pages.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        pages.remove(element);
        notify();
    }

    public ArrayList<Integer> getPages() {
        return pages;
    }
}
