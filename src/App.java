import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

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

        int translationCount = 0;
        int loadCount = 0;

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


        //TODO How can I implement the whole "executes every __ milliseconds"?
        //ReferenceUpdate r = new ReferenceUpdate(tag, tlb, pageTable, translationCount, loadCount);
        //r.start();

    }
}
