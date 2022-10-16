import java.util.ArrayList;
import java.util.Collections;


/**
 * Class that emulates a RAM and all of its processes.
 * It also contains the pageTable, but in reality it's not located here.
 */
public class RAM {
    /**
     * This list represents the page table. Each index of the list is the virtual
     * page number and the value is the real page number. If said virtual page number
     * is not present in RAM, the value is -1
     */
    private final ArrayList<Integer> pageTableList = new ArrayList<>();

    /**
     * This list has the rBit of each real page number in RAM. Works with the aging
     * algorithm.
     */
    private final ArrayList<Long> rBits = new ArrayList<>();

    /**
     * This list has the reference of each real page in a certain instant. Works with
     * the aging algorithm.
     */
    private final ArrayList<Integer> references = new ArrayList<>();

    /**
     * Number of page frames in RAM determined by the user.
     */
    private final int pageFrames;


    /**
     * @param pageFrames Number of page frames in RAM determined by the user.
     * @param numberOfPages Amount of pages designated for the imaginary
     *                      program
     */
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

    /**
     * Returns the real page number from the virtual page number
     * and updates the reference of said real page number
     * @param virtualPageNumber
     * @return Real page number
     */
    public synchronized Integer getFromPageTableList(int virtualPageNumber){
        int realPageNumber =  pageTableList.get(virtualPageNumber);
        if (realPageNumber >= 0){
            updateReference(realPageNumber);
        }
        return realPageNumber;
    }

    /**
     * Updates a virtual page number.
     * It has two possible cases. A case in which the RAM is not full and where it is full.
     * If it's not full, it adds the next integer available of space
     * If it is full, it has to kick another.
     * @param newVirtualPageNumber The virtual page number who is about to get a space in RAM
     * @param realPageNumber The real page number to be given
     * @return Old virtual page number from where it got the
     * space. If this wasn't the case, it returns -2.
     */
    public synchronized int updatePageTableListItem(int newVirtualPageNumber, int realPageNumber){
        int countDifferent = 0;
        for (int i:
             pageTableList) {
            if (i != -1) countDifferent ++;
        }

        if (countDifferent == pageFrames) {
            int oldVirtualPageNumber = -1000;
            for (int virtualPageNumber = 0; virtualPageNumber < pageTableList.size(); virtualPageNumber++) {
                if (pageTableList.get(virtualPageNumber) == realPageNumber){
                    oldVirtualPageNumber = virtualPageNumber;
                }
            }
            pageTableList.set(newVirtualPageNumber, realPageNumber);
            pageTableList.set(oldVirtualPageNumber, -1);
            return oldVirtualPageNumber;
        }
        else {
            pageTableList.set(newVirtualPageNumber, countDifferent);
        }
        return -2;
    }

    /**
     * Adds a 0 the left most bit of each real page number.
     */
    public synchronized void shiftRBits(){
        rBits.replaceAll(integer -> integer >> 1);
    }

    /**
     * Adds a 1 to the left most bit of given real page number.
     * @param index Real page number referenced
     */
    public synchronized void updateRBit(int index){
        rBits.set(index, rBits.get(index)+ (long) Integer.MAX_VALUE + 1);
    }


    /**
     * Returns the smallest index on the rBits array list
     * @return Said smallest index
     */
    public synchronized int getSmallestIndex(){
        long min = Long.MAX_VALUE;
        int index = -100;
        for (int i = 0; i < rBits.size(); i++) {
            if (rBits.get(i) < min){
                min = rBits.get(i);
                index = i;
            }
        }
        return index;
    }

    /**
     * @return The references array list
     */
    public synchronized ArrayList<Integer> getReferences() {
        return references;
    }

    /**
     * Updates the reference for a given real page number.
     * @param index The real page number referenced
     */
    public synchronized void updateReference(int index){
        references.set(index, 1);
    }

    /**
     * Sets every reference to zero.
     */
    public synchronized void clearReferences(){
        Collections.fill(references, 0);
    }
}
