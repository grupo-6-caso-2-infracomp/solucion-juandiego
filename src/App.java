import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        // Inputs

        Scanner scanner = new Scanner(System.in);

        System.out.println("Number of TLB entries");
        Integer nTLB = scanner.nextInt();

        System.out.println("Number of pages in RAM"); //marco de pagina
        Integer nMP = scanner.nextInt();

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Name of the references file");
        String fileName = scanner2.nextLine(); // the reference file asks for the determined page


        // Execution

        TLB tlb = new TLB(nTLB);

        RAM ram = new RAM();

        PageTable pageTable = new PageTable();

        File file = new File(fileName);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s=br.readLine())!= null){
                // the program executes
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
