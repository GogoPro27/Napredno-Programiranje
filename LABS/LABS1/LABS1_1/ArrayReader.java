package Napredno_Programiranje.LABS.LABS1.LABS1_1;
import java.util.Scanner;//kako raboti ova???
import java.io.InputStream;

public class ArrayReader {
    public static IntegerArray readIntegerArray(InputStream input){
        Scanner in = new Scanner(input);
        int n = in.nextInt();
        int tmp[] = new int[n];
        for(int i=0;i<n;i++){
            tmp[i] = in.nextInt();
        }
        in.close();
        return new IntegerArray(tmp);
    }
}
