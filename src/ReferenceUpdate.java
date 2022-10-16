public class ReferenceUpdate extends Thread{

    private final int virtualPageNumber;
    private final TLB tlb;
    private final RAM ram;
    private int translationCount = 0;
    private long loadCount = 0L;

    public ReferenceUpdate(int virtualPageNumber, TLB tlb, RAM ram) {
        this.virtualPageNumber = virtualPageNumber;
        this.tlb = tlb;
        this.ram = ram;
    }

    public void pageFault(int virtualPageNumber){
        translationCount += 60; // read the page table twice
        loadCount += 10000000;
        int kick = ram.getOldestIndex();
        ram.updatePageTableListItem(virtualPageNumber, kick);
        tlb.removeFromTLB(virtualPageNumber);
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
            if (physPageNumber == -1){
                pageFault(virtualPageNumber);
                physPageNumber = ram.getFromPageTableList(virtualPageNumber);
                tlb.fifo(virtualPageNumber, physPageNumber);
            }
            else {
                translationCount += 30;
                tlb.fifo(virtualPageNumber,physPageNumber);
            }
        }
    }
}
