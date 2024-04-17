package Napredno_Programiranje.FirstPartialExercises.Canvas_6_pak;
//tuka imam edna mnogu jaka add funkcija sto go naogja prviot pomal od toj so treba da se vnese vo opagjacka
//so streamovi, razglej...
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}
public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}
interface Scalable{
    public void scale(float scaleFactor);
}
interface Stackable{
    public float weight();
}
 abstract class Shape implements Scalable, Stackable {
    private String id;
    private Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

     public String getId() {
         return id;
     }

     public Color getColor() {
         return color;
     }

     @Override
     public String toString() {
         return String.format("%-5s%-10s%10.2f",id,color,weight());
     }
 }
class Circle extends Shape{
    private float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius*=scaleFactor;
    }

    @Override
    public float weight() {
        return (float) (radius*radius*Math.PI);
    }

    @Override
    public String toString() {
        return "C: "+super.toString();
    }
}
class Rectangle extends Shape{
    private float width;
    private float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void scale(float scaleFactor) {
        width*=scaleFactor;
        height*=scaleFactor;
    }

    @Override
    public float weight() {
        return width*height;
    }

    @Override
    public String toString() {
        return "R: "+super.toString();
    }
}
class Canvas {
    private List<Shape> shapes;

    public Canvas() {
        shapes = new ArrayList<>();
    }
    public void add(String id, Color color, float radius){
        Circle add = new Circle(id,color,radius);
        addToCorrectPosition(add);
    }
    public void add(String id, Color color, float width, float height){
        Rectangle add = new Rectangle(id,color,width,height);
       addToCorrectPosition(add);
    }
    public void scale(String id, float scaleFactor){
        for (int i = 0; i < shapes.size(); i++) {
            if(shapes.get(i).getId().equals(id)){
                shapes.get(i).scale(scaleFactor);
                addToCorrectPosition(shapes.remove(i));
                return;
            }
        }
    }

    public void addToCorrectPosition(Shape shape) {
        Optional<Shape> opt = shapes.stream().filter(s -> s.weight() < shape.weight()).findFirst();
        if (opt.isPresent()) {
            shapes.add(shapes.indexOf(opt.get()), shape);
        }else {
            shapes.add(shapes.size(),shape);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        shapes.forEach(s->sb.append(s).append('\n'));
        return sb.toString();
    }
}