package Napredno_Programiranje.FirstPartialExercises.Component_13;
//zaebanka zadaca so rekurzii
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}
class InvalidPositionException extends Exception{
    public InvalidPositionException(String message) {
        super(message);
    }
}
// вашиот код овде
class Component implements Comparable<Component> {
    private String color;
    private int weight;
    private int pos;
    List<Component> components;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        pos = -1;
        components = new ArrayList<>();
    }
    public void addComponent(Component component){
        components.add(component);
        sredi();
    }
    public void sredi(){
        components = components.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    public int getPos() {
        return pos;
    }

    public Component setPos(int pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public int compareTo(Component o) {
        if(weight>o.weight){
            return 1;
        }else if(weight<o.weight){
            return -1;
        }else {
            return color.compareTo(o.color);
        }
    }
    public String getComponentString(int intend){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intend; i++) {
            sb.append("---");
        }
        sb.append(String.format("%d:%s\n",weight,color));
        components.stream().forEach(c->sb.append(c.getComponentString(intend+1)));
        return sb.toString();
    }
    public void changeColor(int weight,String color){
        if(this.weight<weight)this.color = color;
        if(!components.isEmpty()){
            components.forEach(c->c.changeColor(weight,color));
        }
        sredi();
    }
}
class Window{
    private String ime;
    private List<Component> components;

    public Window(String ime) {
        this.ime = ime;
        components = new ArrayList<>();
    }
    public void addComponent(int position, Component component) throws InvalidPositionException {
        if(components.isEmpty()){
            components.add(component.setPos(position));
        }else {
            for (int i = 0; i < components.size(); i++) {
                if(components.get(i).getPos()==position)throw new InvalidPositionException("Invalid position "+position+", alredy taken!");
            }
            components.add(component.setPos(position));
            sredi();
        }
    }
    public void sredi(){
        components = components.stream()
                .sorted(Comparator.comparingInt(Component::getPos))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("WINDOW ").append(ime).append('\n');
        components.stream().forEach(c->sb.append(c.getPos()).append(":").append(c.getComponentString(0)));
        return sb.toString();
    }
    public void changeColor(int weight, String color){
        for (int i = 0; i < components.size(); i++) {
            components.get(i).changeColor(weight,color);
        }
        sredi();
    }
    public void swichComponents(int pos1, int pos2){
        Component tmp1=null,tmp2=null;
        for (int i = 0; i < components.size(); i++) {
            if(components.get(i).getPos()==pos1){
                tmp1 = components.get(i);
            }else if(components.get(i).getPos()==pos2){
                tmp2 = components.get(i);
            }
        }
        if(tmp1!=null&&tmp2!=null) {
            int tmp = tmp1.getPos();
            tmp1.setPos(tmp2.getPos());
            tmp2.setPos(tmp);
            sredi();
        }

    }
}