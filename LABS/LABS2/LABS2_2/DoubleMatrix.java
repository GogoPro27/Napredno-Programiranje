package Napredno_Programiranje.LABS.LABS2.LABS2_2;

import java.util.Arrays;
import java.util.Objects;

public class DoubleMatrix {
    private double[][] matrix;
    private final int n,m;

    public DoubleMatrix(double[]a, int m, int n) throws InsufficientElementsException {
        this.n = n;
        this.m = m;


        if(a.length<(m*n))throw new InsufficientElementsException("Insufficient number of elements");
        else if(a.length==(m*n)){
            int counter=0;
            this.matrix = new double[m][n];
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    matrix[i][j] = a[counter++];
                }
            }
        }else{
            this.matrix = new double[m][n];
            int counter = a.length - m*n;
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    matrix[i][j] = a[counter++];
                }
            }
        }
    }

    public String getDimensions(){
        return "["+m+" x "+n+"]";
    }

    public int rows(){
        return m;
    }
    public int columns(){
        return n;
    }

    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if(row>m||row<1)throw new InvalidRowNumberException("Invalid row number");
        double max = matrix[row-1][0];
        for(int i=1;i<n;i++)
            if(matrix[row-1][i]>max)max = matrix[row-1][i];
        return max;
    }
    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if(column>n||column<1)throw new InvalidColumnNumberException("Invalid column number");
        double max = matrix[0][column-1];
        for(int i=1;i<m;i++)
            if(matrix[i][column-1]>max)max = matrix[i][column-1];
        return max;
    }

    public double sum(){
        double sum = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                sum+=matrix[i][j];
            }
        }
        return sum;
    }

//    public double[] toSortedArray(){
//        double[] array = new double[m*n];
//        for(int i=0;i<m;i++){
//            for(int j=0;j<n;j++){
//                array[i] = matrix[i][j];
//            }
//        }
//        for(int i=0;i<m;i++){
//            for(int j=i;j<n;j++){
//                if(array[i]<array[j]) {
//                    double temp = array[i];
//                    array[i] = array[j];
//                    array[j] = temp;
//                }
//            }
//        }
//        return array;
//    }
public double[] toSortedArray() {
    double[] array = new double[m * n];
    int c = 0;

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            array[c] = matrix[i][j];
            c++;
        }
    }

    Arrays.sort(array);

    for (int i = 0; i < array.length / 2; i++) {
        double temp = array[i];
        array[i] = array[array.length - 1 - i];
        array[array.length - 1 - i] = temp;
    }

    return array;
}

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                s.append(String.format( "%.2f",matrix[i][j]));
                if (j != n - 1) {
                    s.append("\t");
                }
            }
            if (i != m - 1) {
                s.append("\n");
            }
        }

    return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleMatrix that = (DoubleMatrix) o;
        return n == that.n && m == that.m && Arrays.deepEquals(matrix, that.matrix); //WHAT DA HELLLL
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(n, m);
        result = 31 * result + Arrays.deepHashCode(matrix);//WHAT DA HELLLL
        return result;
    }

}
