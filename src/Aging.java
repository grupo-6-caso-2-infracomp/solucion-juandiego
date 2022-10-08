public class Aging extends Thread{

    private PageTable pageTable;

    public Aging(PageTable pageTable) {
        this.pageTable = pageTable;
    }

    public void agingAlgorithm (PageTable pageTable){
        pageTable.shiftRBits();
    }
}
