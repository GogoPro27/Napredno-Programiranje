 package Napredno_Programiranje.FirstPartialExercises.ShapesApplication_2;
//vidi za Exceptionot, ova e epten peski ne morav da go nosam maxArea se do circle i square,
//tuku mozev samo da dozvolam vnesuvanje na circle ili square so area pogolema i da dozvolam kreiranje na prozorecot
//samooo koga kje treba da se vmetne vo listata kje proveri dali maksimalnata area vo canvasot e pogolema od maxArea...
import java.io.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

class IrregularCanvasException extends Exception{
    public IrregularCanvasException(String message) {
        super(message);
    }
}
class IrregularShape extends Exception{
    public IrregularShape() {}
}
abstract class Shape{
    private int size;
    private double maxArea;
    String type;
    public Shape(int size,double maxArea) throws  IrregularShape {
        this.maxArea = maxArea;
        this.size = size;
        if(getArea()>this.maxArea)throw new IrregularShape();
    }

    public int getSize() {
        return size;
    }

    public abstract double getArea();
}
class Circle extends Shape{
    public Circle(int size, double maxArea) throws IrregularShape {
        super(size, maxArea);
        type = "C";
    }

    @Override
    public double getArea() {
        return getSize()*getSize()*3.14159265359;
    }
}
class Square extends Shape{

    public Square(int size, double maxArea) throws IrregularShape {
        super(size, maxArea);
        type = "S";
    }

    @Override
    public double getArea() {
        return getSize()*getSize();
    }
}
class Prozorec{
    private String id;
    private List<Shape> shapes;
    double maxArea;

    public Prozorec(String line,double maxArea) throws IrregularCanvasException {
        String[] parts = line.split("\\s+");
        this.maxArea = maxArea;
        id = parts[0];
        shapes = new ArrayList<>();
        for (int i = 1; i < parts.length; i+=2) {
            if(parts[i].equals("C")){
                try {
                    shapes.add(new Circle(Integer.parseInt(parts[i+1]),maxArea));
                } catch (IrregularShape e) {
                    throw new IrregularCanvasException(String.format("Canvas %s has a shape with area larger than %.2f",id,maxArea));
                }
            }else {
                try {
                    shapes.add(new Square(Integer.parseInt(parts[i+1]),maxArea));
                } catch (IrregularShape e) {
                    throw new IrregularCanvasException(String.format("Canvas %s has a shape with area larger than %.2f",id,maxArea));
                }
            }
        }
    }
    public double getSumArea(){
        return shapes.stream().mapToDouble(Shape::getArea).sum();
    }

    @Override
    public String toString() {
        int numC,numS;
        numC = (int) shapes.stream().filter(i->i.type.equals("C")).count();
        numS = (int)shapes.stream().filter(i->i.type.equals("S")).count();
        DoubleSummaryStatistics ds = shapes.stream().mapToDouble(Shape::getArea).summaryStatistics();
        return String.format("%s %d %d %d %.2f %.2f %.2f",id,shapes.size(),numC,numS,ds.getMin(),ds.getMax(),ds.getAverage());
    }
}

class ShapesApplication{
    private List<Prozorec> prozorci;
    private double maxArea;
    public ShapesApplication(double maxArea) {
        this.maxArea = maxArea;
    }
    public void readCanvases(InputStream inputStream)  {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        List<String> inputs = br.lines().collect(Collectors.toList());
        prozorci = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            try {
                prozorci.add(new Prozorec(inputs.get(i),maxArea));
            } catch (IrregularCanvasException ie) {
                System.out.println(ie.getMessage());
            }
        }
    }
    public void printCanvases(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        prozorci.stream().sorted((p1,p2)->(int)(p2.getSumArea()-p1.getSumArea())).forEach(pw::println);
        pw.flush();
        pw.close();
    }
}

public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}