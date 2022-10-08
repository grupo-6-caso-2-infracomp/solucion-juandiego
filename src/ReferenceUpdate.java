public class ReferenceUpdate extends Thread{

    private int virtualPageNumber;
    private TLB tlb;
    private PageTable pageTable;
    private int translationCount = 0;
    private int loadCount = 0;

    //get from TLB
    //cases:
    //can get from TLB (best)
    //gets from PT, loads to TLB on FIFO
    //gets from disk (worst) -> have to take into account aging algorithm and choose the biggest counter to delete

    //this thread executes every two milliseconds

    public ReferenceUpdate(int virtualPageNumber, TLB tlb, PageTable pageTable) {
        this.virtualPageNumber = virtualPageNumber;
        this.tlb = tlb;
        this.pageTable = pageTable;
    }

    public void pageFault(int virtualPageNumber){
        // read the page table twice
        // look for the references table on the page table
        // choose a reference to kick out to disk and place my page there, BASED ON REFERENCES
        loadCount += 60;
        translationCount += 60;
        int kick = pageTable.getOldestIndex();
        pageTable.updatePageTableListItem(kick, virtualPageNumber);

    }

    public int getLoadCount() {
        return loadCount;
    }

    public int getTranslationCount() {
        return translationCount;
    }

    @Override
    public void run() {
        pageTable.updateRBit(virtualPageNumber);
        // looks on the tlb
        int physPageNumber = tlb.lookForTagValue(virtualPageNumber);
        if (physPageNumber != -1 && physPageNumber != -2){
            translationCount += 2; // go to the tlb and translate from there.
            loadCount += 30; // get the page from RAM
        } else if (physPageNumber == -1) {
            pageFault(virtualPageNumber);
        } else { // looks on the page table
            physPageNumber = pageTable.getFromPageTableList(virtualPageNumber);
            tlb.fifo(virtualPageNumber,physPageNumber);
            if (physPageNumber == -1){
                pageFault(virtualPageNumber);
            }
            else {
                translationCount += 30;
                loadCount += 30;
            }
        }
    }
}
