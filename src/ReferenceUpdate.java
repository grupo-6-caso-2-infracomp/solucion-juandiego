public class ReferenceUpdate extends Thread {

    /**
     * Virtual page number that is going to be referenced in the single execution
     */
    private final int virtualPageNumber;

    /**
     * Cache to use
     */
    private final TLB tlb;

    /**
     * RAM to use
     */
    private final RAM ram;

    /**
     * This field will count the time to translate given virtual page
     */
    private int translationCount = 0;

    /**
     * This field will count the time to load given virtual page
     */
    private long loadCount = 0L;

    /**
     * Constructor that sets every field to given thread instance.
     */
    public ReferenceUpdate(int virtualPageNumber, TLB tlb, RAM ram) {
        this.virtualPageNumber = virtualPageNumber;
        this.tlb = tlb;
        this.ram = ram;
    }

    /**
     * Solves a page fault
     * @param virtualPageNumber Where the page fault occured
     */
    private void pageFault(int virtualPageNumber){
        translationCount += 60; // read the page table twice
        loadCount += 10000000; // solves a page fault
        int kick = ram.getSmallestIndex(); // takes smallest rBit (from aging algorithm)
        int old = ram.updatePageTableListItem(virtualPageNumber, kick);
        if (old != -2){ // if there's a dependency on the page table and TLB this removes it
            tlb.removeFromTLB(old);
        }
    }

    public long getLoadCount() {
        return loadCount;
    }

    public int getTranslationCount() {
        return translationCount;
    }

    @Override
    public void run() {
        int physPageNumber = tlb.lookForTagValue(virtualPageNumber);
        if (physPageNumber != -1 && physPageNumber != -2){ //tlb hit
            translationCount += 2;
            loadCount += 30;
        } else { // looks on the page table
            physPageNumber = ram.getFromPageTableList(virtualPageNumber);
            if (physPageNumber == -1){ // page fault
                pageFault(virtualPageNumber);
                physPageNumber = ram.getFromPageTableList(virtualPageNumber);
                tlb.fifo(virtualPageNumber, physPageNumber);
            }
            else { // its on RAM
                translationCount += 30;
                tlb.fifo(virtualPageNumber,physPageNumber);
            }
        }
    }
}
