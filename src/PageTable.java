import java.util.ArrayList;
import java.util.BitSet;

public class PageTable {

    private static ArrayList<Integer> map = new ArrayList<>(64); // where to find each page in RAM. If its not present, returns -1
    private static ArrayList<Integer> references = new ArrayList<>(64);

    //aging algorithm should modify here, it's for solving page faults
    public PageTable(int pageFrames) {
        for (int i = 0; i < 64; i++) {
            references.add(0);
            if (i <= pageFrames){
                map.add(i);
            }
            else {
                map.add(-2);
            }
        }
    }

    public synchronized Integer getFromMap(int index){
        return map.get(index);
    }

    public synchronized void putOnMap(int index, int newReference){
        map.set(index, newReference);
    }

    public synchronized void updateReference(int index){
        Integer i = map.get(index);
        i += 0b10000000;
    }
}
