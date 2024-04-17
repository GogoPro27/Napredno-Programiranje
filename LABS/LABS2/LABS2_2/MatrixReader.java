package Napredno_Programiranje.LABS.LABS2.LABS2_2;
import java.io.InputStream;
import java.util.Scanner;


public class MatrixReader {
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException{
        Scanner scanner = new Scanner(input);
        int m,n;
        m = scanner.nextInt();
        n = scanner.nextInt();

        double []arr = new double[n*m];
        for(int i=0;i<m*n;i++){

                arr[i] = scanner.nextDouble();

        }
        return new DoubleMatrix(arr,m,n);
    }
}
