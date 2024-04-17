package SecondPartialExcercises.kol_8;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CanvasTest {

    public static void main(String[] args) {
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        try {
            canvas.readShapes(System.in);
        } catch (InvalidDimensionException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}

class Canvas{
    private Map<String,Set<Form>> idToForms;
    private Map<String,User> idToUser;
    private Set<Form> forms;

    public Canvas() {

        idToForms = new HashMap<>();
        idToUser = new HashMap<>();
        forms = new TreeSet<>(Comparator.comparing(Form::area));
    }

    public void readShapes(InputStream is) throws InvalidDimensionException {
        //Factory za ova treba realno
       BufferedReader br = new BufferedReader(new InputStreamReader(is));
       List<String> inputs = br.lines().collect(Collectors.toList());
        for (String input : inputs) {
            String[] parts = input.split("\\s+");
            String id = parts[1];
            try {
                if (!checkUserId(id)) throw new InvalidIDException("ID " + id + " is not valid");
                switch (parts[0]) {
                    case "1": {
                        double radius = Double.parseDouble(parts[2]);
                        if (radius == 0) throw new InvalidDimensionException("Dimension 0 is not allowed!");
                        Circle circle = new Circle(radius);
                        idToUser.putIfAbsent(id, new User(id));
                        idToUser.get(id).addNumForms();
                        idToForms.putIfAbsent(id, new TreeSet<>());
                        idToForms.get(id).add(circle);
                        idToUser.get(id).addArea(circle.area());
                        forms.add(circle);

                        break;
                    }
                    case "2": {
                        double side = Double.parseDouble(parts[2]);
                        if (side == 0) throw new InvalidDimensionException("Dimension 0 is not allowed!");
                        Square square = new Square(side);
                        idToUser.putIfAbsent(id, new User(id));
                        idToUser.get(id).addNumForms();
                        idToForms.putIfAbsent(id, new TreeSet<>());
                        idToForms.get(id).add(square);
                        idToUser.get(id).addArea(square.area());
                        forms.add(square);

                        break;
                    }
                    case "3": {
                        double side1 = Double.parseDouble(parts[2]);
                        double side2 = Double.parseDouble(parts[3]);
                        if (side1 == 0 || side2 == 0) throw new InvalidDimensionException("Dimension 0 is not allowed!");
                        Rectangle rectangle = new Rectangle(side1, side2);
                        idToUser.putIfAbsent(id, new User(id));
                        idToUser.get(id).addNumForms();
                        idToForms.putIfAbsent(id, new TreeSet<>());
                        idToForms.get(id).add(rectangle);
                        idToUser.get(id).addArea(rectangle.area());
                        forms.add(rectangle);

                    }
                }
            }catch (InvalidIDException i){
                System.out.println(i.getMessage());
            }
        }

   }
   public void scaleShapes (String userID, double coef){
        if (!idToForms.containsKey(userID))return;
        idToForms.get(userID).forEach(i->i.scale(coef));
   }
   public void printAllShapes (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        forms.forEach(pw::println);
        pw.flush();
   }
   public void printByUserId (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        idToUser.values().stream()
                        .sorted(Comparator.comparing(User::getNumForms).reversed().thenComparing(User::getSumOfFormsAreas))
                                .forEach(user -> {
                                    pw.println("Shapes of user: "+user.getId());
                                    idToForms.get(user.getId()).forEach(pw::println);
                                });
        pw.flush();
   }
   public void statistics (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        DoubleSummaryStatistics dss= forms.stream().mapToDouble(Form::area).summaryStatistics();
       pw.println(String.format("count: %d\n" +
                       "sum: %.2f\n" +
                       "min: %.2f\n" +
                       "average: %.2f\n" +
                        "max: %.2f",dss.getCount(),dss.getSum(),dss.getMin(),dss.getAverage(),dss.getMax()));
       pw.flush();
    }

    public static boolean checkUserId(String id){
        if (id.length() != 6)return false;
       for (int i = 0; i < id.length(); i++) {
           if (!Character.isLetterOrDigit(id.charAt(i)))return false;
       }
       return true;
   }
}
class InvalidIDException extends Exception{
    public InvalidIDException(String message) {
        super(message);
    }
}
class InvalidDimensionException extends Exception{
    public InvalidDimensionException(String message) {
        super(message);
    }
}
class User{
    private String id;
    private double numForms;
    private double sumOfFormsAreas;

    public User(String id) {
        this.id = id;
        numForms = 0;
        sumOfFormsAreas = 0;
    }
    public void addNumForms(){numForms++;}
    public void addArea(double area){sumOfFormsAreas+=area;}

    public String getId() {
        return id;
    }

    public double getNumForms() {
        return numForms;
    }

    public double getSumOfFormsAreas() {
        return sumOfFormsAreas;
    }
}
interface Form extends Comparable<Form>{
    double perimeter();
    double area();
    void scale(double c);

    @Override
    default int compareTo(Form o){
        return Double.compare(perimeter(),o.perimeter());
    }
}
class Circle implements Form{
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double perimeter() {
        return 2*radius*Math.PI;
    }

    @Override
    public double area() {
        return Math.pow(radius,2)*Math.PI;
    }

    @Override
    public void scale(double c) {
        radius*=c;
    }

    @Override
    public String toString() {
        return String.format("Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f",radius,area(),perimeter());
    }
}
class Square implements Form{
    private double side;

    public Square(double side){
        this.side = side;
    }

    @Override
    public double perimeter() {
        return 4*side;
    }

    @Override
    public double area() {
        return Math.pow(side,2);
    }

    @Override
    public void scale(double c) {
        side*=c;
    }

    @Override
    public String toString() {
        return String.format("Square: -> Side: %.2f Area: %.2f Perimeter: %.2f",side,area(),perimeter());
    }
}
class Rectangle implements Form{
    private double side1;
    private double side2;

    public Rectangle(double side1, double side2) {
        this.side1 = side1;
        this.side2 = side2;
    }

    @Override
    public double perimeter() {
        return (side1 + side2)*2;
    }

    @Override
    public double area() {
        return side1 * side2;
    }

    @Override
    public void scale(double c) {
        side1*=c;
        side2*=c;
    }

    @Override
    public String toString() {
        return String.format("Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f",side1,side2,area(),perimeter());
    }
}
