import java.util.ArrayList;

public class PageTable {

    private static final ArrayList<Integer> pageTableList = new ArrayList<>(64); // where to find each page in RAM. If not present, returns -1
    private static final ArrayList<Integer> rBits = new ArrayList<>(64);

    //aging algorithm should modify here, it's for solving page faults
    public PageTable(int pageFrames) {
        for (int i = 0; i < 64; i++) {
            rBits.add(0);
            if (i <= pageFrames){
                pageTableList.add(i);
            }
            else {
                pageTableList.add(-1); // page fault
            }
        }
    }

    public synchronized Integer getFromPageTableList(int index){
        return pageTableList.get(index);
    }

    public synchronized void updatePageTableListItem(int index, int newReference){
        pageTableList.set(index, newReference);
    }

    public synchronized void shiftRBits(){
        rBits.replaceAll(integer -> integer >> 1);
    }

    public synchronized void updateRBit(int index){
        rBits.set(index, rBits.get(index)+0b10000000);
    }

}
