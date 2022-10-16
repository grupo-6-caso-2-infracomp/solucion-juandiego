import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    /**
     * This number indicates how many pages does our imaginary process have assigned.
     * It's not an input, because the activity states these are the number of pages; but for
     * smaller testing, you can set this value to something else. Like with the references that
     * start with "test_", that only have 8 pages.
     */
    public static final int NUM_PAGES = 64;

    public static void main(String[] args) throws InterruptedException {
        /*
         *               Inputs
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Number of TLB entries");
        int nTLB = scanner.nextInt();
        System.out.println("Number of page frames in RAM");
        int nPF = scanner.nextInt();
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Name of the file (must be inside the references folder)");
        String fileName = scanner2.nextLine();

        /*
         *               Execution
         */
        TLB tlb = new TLB(nTLB);
        File file = new File("references/" + fileName);
        RAM ram = new RAM(nPF,NUM_PAGES);
        ArrayList<Integer> tags = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s=br.readLine())!= null){
                int tag = Integer.parseInt(s);
                tags.add(tag);
            }
        } catch (IOException e) {
            System.err.println("Couldn't read given file name.");
        }

        int translationCount = 0;
        long loadCount = 0L;

        ArrayList<ReferenceUpdate> referenceUpdates = new ArrayList<>();
        for (int tag: tags) { // thread pool
            referenceUpdates.add(new ReferenceUpdate(tag, tlb, ram));
        }
        
        ArrayList<Aging> agings = new ArrayList<>();
        for (int i = 0; i < tags.size() * 2; i++) { //thread pool
            agings.add(new Aging(ram));
        }

        for (ReferenceUpdate r:
             referenceUpdates) {
            agings.remove(0).start();
            Thread.sleep(1);
            agings.remove(0).start();
            r.start();
            r.join();
            loadCount += r.getLoadCount();
            translationCount += r.getTranslationCount();
            Thread.sleep(1);
        }

        System.out.println("Load time: " + loadCount);
        System.out.println("Translation time: " + translationCount);
    }
}
