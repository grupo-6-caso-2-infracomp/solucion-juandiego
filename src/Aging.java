import java.util.ArrayList;

public class Aging extends Thread{

    private PageTable pageTable;

    public Aging(PageTable pageTable) {
        this.pageTable = pageTable;
    }
    
    public void updateRBit(PageTable pageTable){
        ArrayList<Integer> references = pageTable.getReferences();
        for (int i = 0; i < references.size(); i++) {
            if (references.get(i) == 1) {
                pageTable.updateRBit(i);
            }
        }
    }
    
    public void agingAlgorithm (PageTable pageTable){
        pageTable.shiftRBits();
    }

    @Override
    public void run() {
        updateRBit(pageTable);
        agingAlgorithm(pageTable);
    }
}
