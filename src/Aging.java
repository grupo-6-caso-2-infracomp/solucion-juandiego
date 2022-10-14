import java.util.ArrayList;

public class Aging extends Thread{

    private RAM RAM;

    public Aging(RAM RAM) {
        this.RAM = RAM;
    }
    
    public void updateRBit(RAM RAM){
        ArrayList<Integer> references = RAM.getReferences();
        for (int i = 0; i < references.size(); i++) {
            if (references.get(i) == 1) {
                RAM.updateRBit(i);
            }
        }
    }
    
    public void agingAlgorithm (RAM RAM){
        RAM.shiftRBits();
    }

    @Override
    public void run() {
        updateRBit(RAM);
        agingAlgorithm(RAM);
        RAM.clearReferences();
    }
}
