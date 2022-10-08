public class Aging extends Thread{
    public void agingAlgorithm (PageTable pageTable){
        pageTable.shiftRBits();
    }
}
