import java.util.ArrayList;

public class PageTable {

    private ArrayList<Integer> pageTableList = new ArrayList<>(64); // where to find each page in RAM. If not present, returns -1
    private ArrayList<Integer> rBits = new ArrayList<>(64);
    private ArrayList<Integer> references = new ArrayList<>(64);

    //aging algorithm should modify here, it's for solving page faults
    public PageTable(int pageFrames) {
        for (int i = 0; i < 64; i++) {
            rBits.add(0);
            references.add(0);
            if (i <= pageFrames){
                pageTableList.add(i);
            }
            else {
                pageTableList.add(-1); // page fault
            }
            System.out.println("Page Table: " + pageTableList.toString());
        }
    }

    public synchronized Integer getFromPageTableList(int virtualPageNumber){
        return pageTableList.get(virtualPageNumber);
    }

    public synchronized void updatePageTableListItem(int index, int newReference){
        pageTableList.set(index, newReference);
        int old = references.get(index);
        references.set(index, old + 1);
    }

    public synchronized void shiftRBits(){
        rBits.replaceAll(integer -> integer >> 1);
        references.replaceAll(i -> i*0);
    }

    public synchronized void updateRBit(int index){
        rBits.set(index, rBits.get(index)+0b10000000);
    }

    public synchronized int getOldestIndex(){
        int min = Integer.MAX_VALUE;
        int index = -100;
        for (int i:
             rBits) {
            if (i < min){
                min = i;
                index = i;
            }
        }
        return index;
    }

    public synchronized ArrayList<Integer> getReferences() {
        return references;
    }
    public synchronized void updateReference(int index){
        references.set(index, 1);
    }
}
