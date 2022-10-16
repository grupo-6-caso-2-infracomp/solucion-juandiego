import java.util.ArrayList;

public class RAM {

    private ArrayList<Integer> pageTableList = new ArrayList<>(); // where to find each page in RAM. If not present, returns -1
    private ArrayList<Long> rBits = new ArrayList<>(); //is a part of the RAM
    private ArrayList<Integer> references = new ArrayList<>(); //is a part of the RAM

    private int pageFrames;


    public RAM(int pageFrames, int numberOfPages) {
        this.pageFrames = pageFrames;
        for (int i = 0; i < numberOfPages ; i++) {
            pageTableList.add(-1);
            if (i < pageFrames){
                rBits.add(0L);
                references.add(0);
            }
        }
    }

    public synchronized Integer getFromPageTableList(int virtualPageNumber){
        int realPageNumber =  pageTableList.get(virtualPageNumber);
        if (realPageNumber >= 0){
            updateReference(realPageNumber);
        }
        return realPageNumber;
    }

    public synchronized int updatePageTableListItem(int newVirtualPageNumber, int realPageNumber){
        //le pongo el numero de pagina real al otro indice
        int countDifferent = 0;
        for (int i:
             pageTableList) {
            if (i != -1) countDifferent ++;
        }

        if (countDifferent == pageFrames) {
            //System.err.println("Case complete RAM");
            int oldVirtualPageNumber = -1000;
            for (int virtualPageNumber = 0; virtualPageNumber < pageTableList.size(); virtualPageNumber++) {
                if (pageTableList.get(virtualPageNumber) == realPageNumber){
                    oldVirtualPageNumber = virtualPageNumber;
                }
            }
            //System.err.println("Sets " + newVirtualPageNumber + " to " + realPageNumber);
            pageTableList.set(newVirtualPageNumber, realPageNumber);
            //System.err.println("Sets " + oldVirtualPageNumber + " to " + "-1");
            pageTableList.set(oldVirtualPageNumber, -1);
            return oldVirtualPageNumber;
        }
        else {
            //System.err.println("Case incomplete RAM");
            //System.err.println("Sets " + newVirtualPageNumber + " to " + countDifferent);
            pageTableList.set(newVirtualPageNumber, countDifferent);
        }

        return -2;
    }

    public synchronized void shiftRBits(){
        rBits.replaceAll(integer -> integer >> 1);
    }

    public synchronized void updateRBit(int index){
        rBits.set(index, rBits.get(index)+ (long) Integer.MAX_VALUE + 1);
    }


    public synchronized int getOldestIndex(){
        //System.out.println("RBits: " + rBits );
        long min = Long.MAX_VALUE;
        int index = -100;
        for (int i = 0; i < rBits.size(); i++) {
            if (rBits.get(i) < min){
                min = rBits.get(i);
                index = i;
            }
        }
        //System.out.println("Minimum: " + min);
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

    public synchronized void clearReferences(){
        references.replaceAll(i -> 0);
    }
}
