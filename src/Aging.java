import java.util.ArrayList;

public class Aging extends Thread{

    /**
     * Shared RAM used in the imaginary process
     */
    private final RAM ram;

    /**
     * Constructor that instantiates a new aging algorithm thread
     * @param ram Shared RAM used in the imaginary process
     */
    public Aging(RAM ram) {
        this.ram = ram;
    }

    /**
     * Updates rBit list given from the references
     */
    public void updateRBit(){
        ArrayList<Integer> references = ram.getReferences();
        for (int i = 0; i < references.size(); i++) {
            if (references.get(i) == 1) {
                ram.updateRBit(i);
            }
        }
    }

    @Override
    public void run() {
        updateRBit();
        ram.shiftRBits();
        ram.clearReferences();
    }
}
