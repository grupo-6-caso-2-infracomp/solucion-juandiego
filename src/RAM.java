import java.util.ArrayList;

public class RAM {

    private ArrayList<Integer> pageTableList = new ArrayList<>(); // where to find each page in RAM. If not present, returns -1
    private ArrayList<Long> rBits = new ArrayList<>(); //is a part of the RAM
    private ArrayList<Integer> references = new ArrayList<>(); //is a part of the RAM



    public RAM(int pageFrames, int numberOfPages) {
        for (int i = 0; i < numberOfPages - 1; i++) { 
            if (i < pageFrames){
                pageTableList.add(i);
                rBits.add(0L);
                references.add(0);
            }
            else {
                pageTableList.add(-1); // page fault
            }
            //System.out.println("Page Table: " + pageTableList.toString());
        }
    }

    public synchronized Integer getFromPageTableList(int virtualPageNumber){
        return pageTableList.get(virtualPageNumber);
    }

    public synchronized void updatePageTableListItem(int newVirtualPageNumber, int oldVirtualPageNumber){
        //le pongo el numero de pagina real al otro indice
        int realPageNumber = pageTableList.get(oldVirtualPageNumber);
        pageTableList.set(newVirtualPageNumber, realPageNumber);
        pageTableList.set(oldVirtualPageNumber, -1);
    }

    public synchronized void shiftRBits(){
        rBits.replaceAll(integer -> integer >> 1);
        references.replaceAll(i -> i*0);
    }

    public synchronized void updateRBit(int index){
        rBits.set(index, rBits.get(index)+ (long) Integer.MAX_VALUE + 1);
    }


    public synchronized int getOldestIndex(){
        long min = Long.MAX_VALUE;
        int index = -100;
        for (int i = 0; i < rBits.size(); i++) {
            if (i < min){
                min = rBits.get(i);
                index = i;
            }
        }
        System.err.println("Page fault!");
        System.err.println("Index to kick: " + index);
        return index;
    }

    public synchronized ArrayList<Integer> getReferences() {
        return references;
    }
    public synchronized void updateReference(int index){
        references.set(index, 1);
    }

    public synchronized ArrayList<Long> getrBits() {
        return rBits;
    }

    public synchronized ArrayList<Integer> getPageTableList() {
        return pageTableList;
    }
}
