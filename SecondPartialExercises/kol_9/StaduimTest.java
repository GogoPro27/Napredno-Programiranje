package Napredno_Programiranje.SecondPartialExercises.kol_9;
//interesna zadaca ne e tolku teska samo kaj tipovite moze da se zbunis
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

class Sector{
    private String code;
    private int numSeats;
    private int type;
    private Map<Integer,Boolean>isSeatTaken;
    private int takenSeats;

    public Sector(String code, int numSeats) {
        this.code = code;
        this.numSeats = numSeats;
        type =0;
        isSeatTaken = new HashMap<>();
        for (int i = 1; i <= numSeats; i++) {
            isSeatTaken.put(i,false);
        }
        takenSeats = 0;
    }
    public void buySeat(int seat,int type) throws SeatNotAllowedException, SeatTakenException {
        if (isSeatTaken.get(seat))throw new SeatTakenException("SeatTakenException");
        if (this.type == 0)this.type = type;
        else if (type==1 && this.type==2) throw new SeatNotAllowedException("SeatNotAllowedException");
        else if (type==2 && this.type==1) throw new SeatNotAllowedException("SeatNotAllowedException");
        isSeatTaken.put(seat,true);
        takenSeats++;
    }

    public String getCode() {
        return code;
    }
    public int getNumberOfAvailableSeats(){
        return numSeats - takenSeats;
    }
    public double full(){
        return (double) takenSeats/numSeats *100.00;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%",code,getNumberOfAvailableSeats(),numSeats,full());
    }
}
class Stadium{
    private String name;
    private Map<String,Sector> nameToSector;

    public Stadium(String name) {
        this.name = name;
        nameToSector = new HashMap<>();
    }
    void createSectors(String[] sectorNames, int[] sizes){
        for (int i = 0; i < sectorNames.length; i++) {
            Sector newSector = new Sector(sectorNames[i],sizes[i]);
            nameToSector.put(sectorNames[i],newSector);
        }
    }
    void buyTicket(String sectorName, int seat, int type) throws SeatNotAllowedException, SeatTakenException {
        nameToSector.get(sectorName).buySeat(seat,type);
    }
    public void showSectors(){
        Comparator<Sector> comparator =
                Comparator.comparing(Sector::getNumberOfAvailableSeats).reversed()
                                .thenComparing(Sector::getCode);
        nameToSector.values().stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }
}
class SeatNotAllowedException extends Exception{
    public SeatNotAllowedException(String message) {
        super(message);
    }
}
class SeatTakenException extends Exception{
    public SeatTakenException(String message) {
        super(message);
    }
}