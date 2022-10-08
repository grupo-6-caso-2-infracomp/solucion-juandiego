import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {

        // Inputs

        Scanner scanner = new Scanner(System.in);

        System.out.println("Number of TLB entries");
        int nTLB = scanner.nextInt();

        System.out.println("Number of page frames in RAM");
        int nPF = scanner.nextInt();

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Name of the references file");
        String fileName = scanner2.nextLine(); // the reference file asks for a determined page

        // Execution

        TLB tlb = new TLB(nTLB);
        PageTable pageTable = new PageTable(nPF);
        File file = new File(fileName);
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

        boolean running = true;

        long time = System.currentTimeMillis();

        int translationCount = 0;
        int loadCount = 0;

        while (running){
            long elapsed = System.currentTimeMillis();
            if (elapsed-time == 2){
                try {
                    int virtualPageNumber = tags.remove(0);
                    ReferenceUpdate r = new ReferenceUpdate(virtualPageNumber, tlb, pageTable);
                    r.start();
                    r.join();
                    loadCount += r.getLoadCount();
                    translationCount += r.getTranslationCount();
                }
                catch (IndexOutOfBoundsException e) {
                    running = false;
                }

            }
            if (elapsed-time == 1){
                Aging aging = new Aging(pageTable);
                aging.start();
                aging.join();
            }
        }

        System.out.println(loadCount);
        System.out.println(translationCount);

    }
}
