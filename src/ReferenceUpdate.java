public class ReferenceUpdate extends Thread{

    private int virtualPageNumber;
    private TLB tlb;
    private RAM ram;
    private int translationCount = 0;
    private int loadCount = 0;

    //get from TLB
    //cases:
    //can get from TLB (best)
    //gets from PT, loads to TLB on FIFO
    //gets from disk (worst) -> have to take into account aging algorithm and choose the biggest counter to delete

    //this thread executes every two milliseconds

    public ReferenceUpdate(int virtualPageNumber, TLB tlb, RAM ram) {
        this.virtualPageNumber = virtualPageNumber;
        this.tlb = tlb;
        this.ram = ram;
    }

    public void pageFault(int virtualPageNumber){
        // read the page table twice
        // look for the references table on the page table
        // choose a reference to kick out to disk and place my page there
        loadCount += 60;
        translationCount += 60;
        int kick = ram.getOldestIndex();
        ram.updatePageTableListItem(virtualPageNumber, kick);
        tlb.removeFromTLB(virtualPageNumber);
        System.err.println("Page fault retrieving virtual page " + virtualPageNumber);
    }

    public int getLoadCount() {
        return loadCount;
    }

    public int getTranslationCount() {
        return translationCount;
    }

    @Override
    public void run() {
        System.out.println("--------------------------");
        System.out.println("Gets virtual page: " + virtualPageNumber);
        System.out.println("PageTable: " + ram.getPageTableList());
        int physPageNumber = tlb.lookForTagValue(virtualPageNumber);
        if (physPageNumber != -1 && physPageNumber != -2){
            // está en la TLB y en RAM
            translationCount += 2; // go to the tlb and translate from there.
            loadCount += 30; // get the page from RAM
        } else { // looks on the page table
            physPageNumber = ram.getFromPageTableList(virtualPageNumber);
            if (physPageNumber == -1){
                pageFault(virtualPageNumber);
                int realPageNumber = 0;
                realPageNumber = ram.getFromPageTableList(virtualPageNumber);
                tlb.fifo(virtualPageNumber, realPageNumber);
            }
            else {
                translationCount += 30;
                loadCount += 30;
                ram.updateReference(physPageNumber);
                tlb.fifo(virtualPageNumber,physPageNumber);
            }
        }
    }
}
