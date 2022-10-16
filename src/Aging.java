import java.util.ArrayList;

public class Aging extends Thread{

    private final RAM ram;

    public Aging(RAM ram) {
        this.ram = ram;
    }
    
    public void updateRBit(){
        ArrayList<Integer> references = ram.getReferences();
        for (int i = 0; i < references.size(); i++) {
            if (references.get(i) == 1) {
                ram.updateRBit(i);
            }
        }
    }
    
    public void agingAlgorithm (){
        ram.shiftRBits();
    }

    @Override
    public void run() {
        updateRBit();
        agingAlgorithm();
        ram.clearReferences();
    }
}
