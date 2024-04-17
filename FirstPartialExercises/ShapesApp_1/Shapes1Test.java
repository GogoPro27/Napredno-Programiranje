package Napredno_Programiranje.FirstPartialExercises.ShapesApp_1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
class Square{
    private int side;

    public Square(int side) {
        this.side = side;
    }
    public int perimeter(){
        return 4*side;
    }
}
class Canvas{
    private String id;
    private List<Square> squares;

    public Canvas(String id, List<Square> squares) {
        this.id = id;
        this.squares = squares;
    }
    public static Canvas generateCanvas(String line){
        String [] parts = line.split("\\s+" );
        String id = parts[0];
//        364fbe94 24 30 22 33 32 30 37 18 29 27 33 21 27 26
        List<Square> list = Arrays.stream(parts).skip(1)
                .map(Integer::parseInt)
                .map(Square::new)
                .collect(Collectors.toList());

        return new Canvas(id,list);
    }

    public List<Square> getSquares() {
        return squares;
    }
    public int totalPerimeter(){
        return squares.stream()
                .mapToInt(Square::perimeter)
                .sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d",id,squares.size(),totalPerimeter());
    }
}
class ShapesApplication{
    private List<Canvas> canvases;

    public ShapesApplication() {
        canvases = new ArrayList<>();
    }
    public int readCanvases(InputStream inputStream){

        BufferedReader br  = new BufferedReader(new InputStreamReader(inputStream));
        List<String> inputs = br.lines().collect(Collectors.toList());

        canvases = inputs.stream()
                .map(Canvas::generateCanvas)
                .collect(Collectors.toList());
        int numS = canvases.stream()
                .mapToInt(c->c.getSquares().size())
                .sum();


        return numS;
    }
    public void printLargestCanvasTo(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        Canvas max = canvases.stream().max(Comparator.comparingInt(Canvas::totalPerimeter)).get();
        pw.println(max);
        pw.flush();
    }
}