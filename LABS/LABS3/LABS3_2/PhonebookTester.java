package Napredno_Programiranje.LABS.LABS3.LABS3_2;//package Napredno_Programiranje.LABS.LABS3.LABS3_2;
//
//import java.util.Arrays;
//import java.util.Random;
//import java.util.Scanner;
//import java.io.*;
//
////
//class InvalidFormatException extends Exception{
//    public InvalidFormatException() {
//        System.out.println("Invalid format");
//    }
//}
////
//class InvalidNameException extends Exception{
//    public String name;
//    public InvalidNameException(String name) {
//        this.name = name;
//    }
//    public void message(){
//        System.out.println("The name "+name+" is inappropriate");
//    }
//}
////
//class InvalidNumberException extends Exception{
//    String num;
//    public InvalidNumberException(String phoneNum) {
//        this.num = phoneNum;
//    }
//    public void message(){
//        System.out.println("Invalid number: "+num);
//    }
//}
//
////
//class MaximumSizeExceddedException extends Exception {
//    int max;
//    public MaximumSizeExceddedException(int maxContacts) {
//        max = maxContacts;
//    }
//    public void message(){
//        System.out.println("Maximum capacity of :"+ max + "reached");
//    }
//}
//
////
//class Contact implements Comparable<Contact>{
//    private final String name;
//    private String[] phoneNumbers;
//    public static final int MAX_CONTACTS = 5;
//    int n;
//
//    public Contact(String name,String... phoneNumbers) throws InvalidNameException, InvalidNumberException, MaximumSizeExceddedException {
//        this.name = name;
//        n=0;
//        this.phoneNumbers = new String[0];
//        if(!checkName(name))throw new InvalidNameException(name);
//        for(String number : phoneNumbers){
//            if(this.phoneNumbers.length==5) throw new MaximumSizeExceddedException(MAX_CONTACTS);
//            if(!checkPhoneNumber(number)) throw new InvalidNumberException(number);
//            this.phoneNumbers = Arrays.copyOf(this.phoneNumbers,++n);
//            this.phoneNumbers[n-1] = number;
//        }
//    }
//
//    public static boolean checkName(String n){
//        if(n.length()<4||n.length()>10)return false;
//        for(int i = 0 ; i < n.length() ;i++){
//            if(!Character.isLetterOrDigit(n.charAt(i))){
//                return false;
//            }
//        }
//        return true;
//    }
//    public static boolean checkPhoneNumber(String num){
//        if (num.length()!=9)return false;
//        if(num.charAt(0)!='0' || num.charAt(1)!='7')return false;
//        if(num.charAt(2)=='3'||num.charAt(2)=='4'||num.charAt(2)=='9')return false;
//        for (int i=0;i<num.length();i++){
//            if(!Character.isDigit(num.charAt(i)))return false;
//        }
//        return true;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String[] getNumbers() {
//        String[] tmp = Arrays.copyOf(phoneNumbers, n); // Create a copy of the array
//        Arrays.sort(tmp);
//        return tmp;
//    }
//    public void addNumber(String phonenumber) throws MaximumSizeExceddedException, InvalidNumberException {
//        if(this.phoneNumbers.length==5) throw new MaximumSizeExceddedException(MAX_CONTACTS);
//        if(!checkPhoneNumber(phonenumber)) throw new InvalidNumberException(phonenumber);
//        this.phoneNumbers = Arrays.copyOf(this.phoneNumbers,++n);
//        this.phoneNumbers[n-1] = phonenumber;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(name).append('\n');
//        sb.append(n).append('\n');
//        for(String s : getNumbers()){
//            sb.append(s).append('\n');
//        }
//        return sb.toString();
//    }
//
//    //    public Contact valueOf(String s) {
////
////    }
//    public boolean hasPrefix(String s){
//        for(String num : phoneNumbers){
//            if(num.substring(0,s.length()-1).equals(s))return true;
//        }return false;
//    }
//    @Override
//    public int compareTo(Contact o) {
//        return this.name.compareTo(o.getName());
//    }
//}
//////
////
//class PhoneBook {
//    private Contact[] contacts;
//    private int n;
//    private static final int MAXIMUM_CONTACTS=250;
//
//    public PhoneBook() {
//        contacts = new Contact[0];
//        n=0;
//    }
//
//    public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
//        if(n==MAXIMUM_CONTACTS) throw new MaximumSizeExceddedException(MAXIMUM_CONTACTS);
//        for(Contact c : contacts){
//            if(c.getName().equals(contact.getName()))throw new InvalidNameException(contact.getName());
//        }
//        contacts = Arrays.copyOf(contacts,n+1);
//        contacts[n++] = contact;
//    }
//
//    public Contact getContactForName(String name){
//        for(Contact c :contacts){
//            if(c.getName().equals(name))return c;
//        }
//        return null;
//    }
//    public int numberOfContacts(){
//        return n;
//    }
//    public Contact[] getContacts(){
//        Contact[] tmp = Arrays.copyOf(contacts, n); // Create a copy of the array
//        Arrays.sort(tmp);
//        return tmp;
//    }
//
//    public boolean removeContact(String name){
//        int flag=0;
//        int idx=0;
//        for(Contact c : contacts){
//            if (c.getName().equals(name)) {
//                flag = 1;
//                break;
//            }
//            idx++;
//        }
//        if(flag==0)return false;
//        for(int i=idx;i<n-1;i++){
//            contacts[i] = contacts[i+1];
//        }
//        contacts = Arrays.copyOf(contacts,n-1);
//        n--;
//
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Contact c :contacts){
//            stringBuilder.append(c.toString());
//        }
//        return stringBuilder.toString();
//    }
//    public static boolean saveAsTextFile(PhoneBook pb, String path) {
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
//            oos.writeObject(pb);
//            oos.close();
//        } catch (IOException e) {
//            return false;
//        }
//
//        return true;
//    }
//
//    public static PhoneBook loadFromTextFile(String file) throws InvalidFormatException {
//        PhoneBook pb = null;
//
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
//            pb = (PhoneBook) ois.readObject();
//            ois.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new InvalidFormatException();
//        }
//
//        return pb;
//    }
//    public Contact[] getContactsForNumber(String number_prefix){
//        Contact[]tmp = new Contact[0];
//        for(Contact c:contacts){
//            if(c.hasPrefix(number_prefix)){
//                tmp = Arrays.copyOf(tmp,tmp.length+1);
//                tmp[tmp.length-1] = c;
//            }
//        }
//        return tmp;
//    }
//}
////
////
//public class PhonebookTester {
//
//    public static void main(String[] args) throws Exception {
//        Scanner jin = new Scanner(System.in);
//        String line = jin.nextLine();
//        switch( line ) {
//            case "test_contact":
//                testContact(jin);
//                break;
//            case "test_phonebook_exceptions":
//                testPhonebookExceptions(jin);
//                break;
//            case "test_usage":
//                testUsage(jin);
//                break;
//        }
//    }
//
//    private static void testFile(Scanner jin) throws Exception {
//        PhoneBook phonebook = new PhoneBook();
//        while ( jin.hasNextLine() )
//            phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
//        String text_file = "phonebook.txt";
//        PhoneBook.saveAsTextFile(phonebook,text_file);
//        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
//        if ( ! pb.equals(phonebook) ) System.out.println("Your file saving and loading doesn't seem to work right");
//        else System.out.println("Your file saving and loading works great. Good job!");
//    }
//
//    private static void testUsage(Scanner jin) throws Exception {
//        PhoneBook phonebook = new PhoneBook();
//        while ( jin.hasNextLine() ) {
//            String command = jin.nextLine();
//            switch ( command ) {
//                case "add":
//                    phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
//                    break;
//                case "remove":
//                    phonebook.removeContact(jin.nextLine());
//                    break;
//                case "print":
//                    System.out.println(phonebook.numberOfContacts());
//                    System.out.println(Arrays.toString(phonebook.getContacts()));
//                    System.out.println(phonebook.toString());
//                    break;
//                case "get_name":
//                    System.out.println(phonebook.getContactForName(jin.nextLine()));
//                    break;
//                case "get_number":
//                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
//                    break;
//            }
//        }
//    }
//
//    private static void testPhonebookExceptions(Scanner jin) {
//        PhoneBook phonebook = new PhoneBook();
//        boolean exception_thrown = false;
//        try {
//            while ( jin.hasNextLine() ) {
//                phonebook.addContact(new Contact(jin.nextLine()));
//            }
//        }
//        catch ( InvalidNameException e ) {
//            System.out.println(e.name);
//            exception_thrown = true;
//        }
//        catch ( Exception e ) {}
//        if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw InvalidNameException");
//        /*
//		exception_thrown = false;
//		try {
//		phonebook.addContact(new Contact(jin.nextLine()));
//		} catch ( MaximumSizeExceddedException e ) {
//			exception_thrown = true;
//		}
//		catch ( Exception e ) {}
//		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
//        */
//    }
//
//    private static void testContact(Scanner jin) throws Exception {
//        boolean exception_thrown = true;
//        String names_to_test[] = { "And\nrej","asd","AAAAAAAAAAAAAAAAAAAAAA","Ð�Ð½Ð´Ñ€ÐµÑ˜A123213","Andrej#","Andrej<3"};
//        for ( String name : names_to_test ) {
//            try {
//                new Contact(name);
//                exception_thrown = false;
//            } catch (InvalidNameException e) {
//                exception_thrown = true;
//            }
//            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
//        }
//        String numbers_to_test[] = { "+071718028","number","078asdasdasd","070asdqwe","070a56798","07045678a","123456789","074456798","073456798","079456798" };
//        for ( String number : numbers_to_test ) {
//            try {
//                new Contact("Andrej",number);
//                exception_thrown = false;
//            } catch (InvalidNumberException e) {
//                exception_thrown = true;
//            }
//            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
//        }
//        String nums[] = new String[10];
//        for ( int i = 0 ; i < nums.length ; ++i ) nums[i] = getRandomLegitNumber();
//        try {
//            new Contact("Andrej",nums);
//            exception_thrown = false;
//        } catch (MaximumSizeExceddedException e) {
//            exception_thrown = true;
//        }
//        if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
//        Random rnd = new Random(5);
//        Contact contact = new Contact("Andrej",getRandomLegitNumber(rnd),getRandomLegitNumber(rnd),getRandomLegitNumber(rnd));
//        System.out.println(contact.getName());
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//        contact.addNumber(getRandomLegitNumber(rnd));
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//        contact.addNumber(getRandomLegitNumber(rnd));
//        System.out.println(Arrays.toString(contact.getNumbers()));
//        System.out.println(contact.toString());
//    }
//
//    static String[] legit_prefixes = {"070","071","072","075","076","077","078"};
//    static Random rnd = new Random();
//
//    private static String getRandomLegitNumber() {
//        return getRandomLegitNumber(rnd);
//    }
//
//    private static String getRandomLegitNumber(Random rnd) {
//        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
//        for ( int i = 3 ; i < 9 ; ++i )
//            sb.append(rnd.nextInt(10));
//        return sb.toString();
//    }
//
//
//}
//
