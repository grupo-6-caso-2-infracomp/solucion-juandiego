import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static final int NUM_PAGES = 64;
    public static void main(String[] args) throws InterruptedException {

        // Inputs

        Scanner scanner = new Scanner(System.in);

        System.out.println("Number of TLB entries");
        int nTLB = scanner.nextInt();

        System.out.println("Number of page frames in RAM");
        int nPF = scanner.nextInt();

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Name of the references file");
        String fileName = scanner2.nextLine(); // the reference file asks for a determined virtual page

        // Execution

        TLB tlb = new TLB(nTLB);
        File file = new File(fileName);
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
            e.printStackTrace();
        }

        int translationCount = 0;
        long loadCount = 0L;

        ArrayList<ReferenceUpdate> referenceUpdates = new ArrayList<>();

        for (int tag: tags) {
            referenceUpdates.add(new ReferenceUpdate(tag, tlb, ram));
        }
        
        ArrayList<Aging> agings = new ArrayList<>();

        for (int i = 0; i < tags.size() * 2; i++) {
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
