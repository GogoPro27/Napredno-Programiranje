package SecondPartialExcercises.kol_21;
//malku zaebanka kaj vtorata funkcija razgledaj!!
import java.util.*;

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }

    }

}

// Вашиот код овде
class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String message) {
        super(message);
    }
}
class PhoneBook{
    private Map<String ,String> contacts;
    private Map<String , TreeSet<String>> nameAndNums;
    private Map<String , Set<Contact>> partialNumToContacts;

    PhoneBook(){
        contacts = new HashMap<>();
        nameAndNums = new HashMap<>();
        partialNumToContacts = new HashMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if (contacts.containsKey(name))throw new DuplicateNumberException("Duplicate number: "+number);
        contacts.put(number,name);
        nameAndNums.putIfAbsent(name,new TreeSet<>());
        nameAndNums.get(name).add(number);

        List<String> subNums = getPartials(number);
        for (String subNum : subNums) {
            partialNumToContacts.putIfAbsent(subNum,new TreeSet<>(Contact.COMPARATOR));
            partialNumToContacts.get(subNum).add(new Contact(name,number));
        }
    }

    public void contactsByNumber(String number){
        if(!partialNumToContacts.containsKey(number)){
            System.out.println("NOT FOUND");
            return;
        }
        partialNumToContacts.get(number).forEach(System.out::println);
    }
    public static List<String> getPartials(String num){
        List<String> list  = new ArrayList<>();

        for (int i = 3; i < num.length(); i++) {
            for (int j = 0; j <= num.length()-i; j++) {
                list.add(num.substring(j,j+i));
            }
        }list.add(num);
       // System.out.println(list);
        return list;
    }
    public void contactsByName(String name){
        if(!nameAndNums.containsKey(name)){
            System.out.println("NOT FOUND");
            return;
        }
        nameAndNums.get(name).forEach(i-> System.out.println(name+" "+i));
    }
}
class Contact{
    private String name;
    private String number;
    static final Comparator<Contact> COMPARATOR = Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber);

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return name+" "+number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}

