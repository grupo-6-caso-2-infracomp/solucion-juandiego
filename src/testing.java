import java.util.ArrayList;
import java.util.BitSet;

public class testing {
    public static void main(String[] args) {

        Integer num = 0b00000000;
        num += 0b10000000; // sumarle el bit a la izq si se hace una referencia
        num = num >> 1; // hacer corrimiento a la derecha
        System.out.println(num);

        ArrayList<Integer> references = new ArrayList<>(64);
        System.out.println(references.toString());

    }
}
