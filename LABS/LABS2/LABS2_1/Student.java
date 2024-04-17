package Napredno_Programiranje.LABS.LABS2.LABS2_1;

import java.util.Arrays;
//import java.util.Objects;

public class Student {
    private Contact[] contacts;
    //private int capacity;
    private int numContacts;

    private final String firstName;
    private final String lastName;
    private final String city;
    private final int age;
    private final long index;


    public Student( String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;

//        capacity = 50;
//        contacts = new Contact[capacity];
//        numContacts=0;
        this.contacts = new Contact[0];
        this.numContacts = 0;

    }

    public void addEmailContact(String date, String email){
////        if(capacity==numContacts){
////            capacity*=2;
////            contacts = Arrays.copyOf(contacts,2*capacity);
////        }
//        contacts[numContacts++] = new EmailContact(date, email);
        contacts = Arrays.copyOf(contacts,numContacts+1);
        contacts[numContacts] = new EmailContact(date,email);
        numContacts++;
    }

    public void addPhoneContact(String date, String phone){
//        if(capacity==numContacts){
//            capacity*=2;
//            contacts = Arrays.copyOf(contacts,2*capacity);
//        }
//        contacts[numContacts++] = new PhoneContact(date, phone);
        contacts = Arrays.copyOf(contacts,numContacts+1);
        contacts[numContacts] = new PhoneContact(date,phone);
        numContacts++;
    }
//    public int countByType(String s){
//        int counter = 0;
//        for(int i=0;i<numContacts;i++){
//            if(contacts[i].getType().equals(s)){
//                counter++;
//            }
//        }
//        return counter;
//    }
//    public Contact[] getEmailContacts() {
////        Contact [] emailC = new Contact[capacity];
//        int counter = 0;
//        int numEmailC = countByType("Email");
//
//        Contact [] emailC = new Contact[numEmailC] ;
//
//        for(int i=0;i<numEmailC;i++){
//            if(contacts[i].getType().equals("Email")){
//                emailC[counter++] = contacts[i];
//            }
//        }
//
//        return emailC;
//    }
//
//    public Contact[] getPhoneContacts() {
//        int counter = 0;
//        int numPhoneC = countByType("Phone");
//
//        Contact [] phoneC = new Contact[numPhoneC] ;
//
//        for(int i=0;i<numPhoneC;i++){
//            if(contacts[i].getType().equals("Phone")){
//                phoneC[counter++] = contacts[i];
//            }
//        }
//        return phoneC;
//    }ZASHO NE RABOTI
public Contact[] getEmailContacts() {
    Contact[] array;
    int i = 0;

    for (Contact c : contacts) {
        if (c.getType().equals("Email")) {
            i++;
        }
    }

    array = new Contact[i];
    i = 0;

    for (Contact c : contacts) {
        if (c.getType().equals("Email")) {
            array[i] = c;
            i++;
        }
    }

    return array;
}

    public Contact[] getPhoneContacts() {
        Contact[] array;
        int i = 0;

        for (Contact c : contacts) {
            if (c.getType().equals("Phone")) {
                i++;
            }
        }

        array = new Contact[i];
        i = 0;

        for (Contact c : contacts) {
            if (c.getType().equals("Phone")) {
                array[i] = c;
                i++;
            }
        }

        return array;
    }


    public String getCity() {
        return city;
    }

    public String getFullName(){
        return firstName + " " +lastName;
    }

    public long getIndex() {
        return index;
    }

    public int getNumContacts() {
        return numContacts;
    }

    public Contact getLatestContact(){
        Contact latest = contacts[0];
        for(Contact c : contacts){
            if(c.isNewerThan(latest))latest = c;
        }
        return latest;
    }

    @Override
    public String toString() {
        return "{\"ime\":\"" +
                firstName +
                "\", \"prezime\":\"" +
                lastName +
                "\", \"vozrast\":" +
                age +
                ", \"grad\":\"" +
                city +
                "\", \"indeks\":" +
                index +
                ", \"telefonskiKontakti\":" +
                Arrays.toString(getPhoneContacts()) +
                ", \"emailKontakti\":" +
                Arrays.toString(getEmailContacts()) +
                "}";
    }
}
