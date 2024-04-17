package Napredno_Programiranje.LABS.LABS2.LABS2_1;

public abstract class Contact {
//    private final String date;
    private final int year;
    private final int month;
    private final int days;

    public Contact(String date) {
        String[] parts = date.split("-");
        year = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]);
        days = Integer.parseInt(parts[2]);
    }

    public boolean isNewerThan(Contact contact){
       // if (contact==null)return false;
        return getDays() > contact.getDays();
    }
    public long getDays(){
        return year* 365L + month*30L + days;
    }
    public abstract String getType();
}
//ok
