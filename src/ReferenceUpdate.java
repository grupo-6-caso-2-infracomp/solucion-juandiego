public class ReferenceUpdate extends Thread{

    private int tag;
    private TLB tlb;

    private PageTable pageTable;

    public int translationCount;
    public int loadCount;


    //get from TLB
    //cases:
    //can get from TLB (best)
    //gets from PT, loads to TLB on FIFO
    //gets from disk (worst) -> have to take into account aging algorithm and choose the biggest counter to delete

    //this thread executes every two milliseconds

    public void pageFault(int reference){
        // read the page table twice
        // look for the references table on the page table
        // choose a reference to kick out to disk and place my page there

    }


    @Override
    public void run() {
        // looks on the tlb
        int physPageNumber = tlb.lookForTagValue(tag);
        if (physPageNumber != -1 && physPageNumber != -2){
            translationCount += 2; // go to the tlb and translate from there.
            loadCount += 30; // get the page from RAM
        } else if (physPageNumber == -1) {
            //page fault
        } else {
            // I have to go get it from the page table, load it into the TLB by FIFO.
            physPageNumber = pageTable.getFromPageTableList(tag);
            tlb.fifo(tag,physPageNumber);
            if (physPageNumber == -1){
                // page fault
            }
            else {
                translationCount += 30;
                loadCount += 30;
            }
        }
    }
}
