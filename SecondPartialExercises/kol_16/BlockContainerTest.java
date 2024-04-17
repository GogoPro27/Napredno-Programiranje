package Napredno_Programiranje.SecondPartialExercises.kol_16;
//zaso 73ti red raboti???toa so flatmap i stream
//kul zadaca bi sakal da vidam drugo resenie


import java.util.*;
import java.util.stream.Collectors;

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for(int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for(int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}

// Вашиот код овде
class BlockContainer<T extends Comparable<T>>{
    private int maxElInBlock;
    private Map<Integer,Block<T>> blocks;
    private int numBlocks;

    public BlockContainer(int n) {
        this.maxElInBlock = n;
        blocks = new HashMap<>();
        numBlocks = 0;
    }
    public void add(T a){
        if (numBlocks==0){
            blocks.put(++numBlocks,new Block<>(maxElInBlock));
        }
        if (blocks.get(numBlocks).getSize()==maxElInBlock){
            blocks.put(++numBlocks,new Block<>(maxElInBlock));
        }
        blocks.get(numBlocks).addElement(a);

    }
    public boolean remove(T a){
        boolean b =blocks.get(numBlocks).removeElement(a);
        if (blocks.get(numBlocks).getSize()==0){
            blocks.remove(numBlocks--);
        }
        return b;
    }
    public void sort() {
        List<T> sortedElements = blocks.values().stream()
                .flatMap(tBlock -> tBlock.getElements().stream())
                .sorted()
                .collect(Collectors.toList());

        clearBlocks();

        for (T element : sortedElements) {
            add(element);
        }
    }

    private void clearBlocks() {
        blocks.clear();
        numBlocks = 0;
    }

    @Override
    public String toString() {
        return blocks.values().stream()
                .map(Block::toString)
                .collect(Collectors.joining(","));
    }
}
class Block<T extends Comparable<T>>{
    private int size;
    private TreeSet<T> elements;

    public Block(int size) {
        this.size = size;
        elements = new TreeSet<>();
    }
    public void addElement(T element){
        elements.add(element);
    }
    public boolean removeElement(T a){
        return elements.remove(a);
    }

    @Override
    public String toString() {
        return "["+elements.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "))+"]";
    }

    public int getSize() {
        return elements.size();
    }

    public TreeSet<T> getElements() {
        return elements;
    }
}



