public class Aging extends Thread{
    // this thread executes every millisecond
    // what the algorithm does is it updates the reference of each page by adding a 1 in the R bit of the counter

    private TLB tlb;

    public Aging(TLB tlb) {
        this.tlb = tlb;
    }

    public void agingAlgorithm (){
        int pagesCounter[] = new int[tlb.n];
        // update each reference based on the reference table
    }
}
