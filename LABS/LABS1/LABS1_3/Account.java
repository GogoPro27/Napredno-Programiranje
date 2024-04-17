package Napredno_Programiranje.LABS.LABS1.LABS1_3;
import java.util.Random;
public class Account {
    private String name;
    private long id;
    private String balance;

    public Account(String name, String balance) {
        this.name = new String(name);
        this.balance = new String(balance);
        id = new Random().nextLong();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }
    public Double getDoubleBalance(){
        return Double.parseDouble(balance.substring(0,balance.length()-1));
    }

    public void setBalance(String balance){
        this.balance = String.format("%.2f$", Double.parseDouble(balance));
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +  "Balance:" + balance + "\n";
    }
}
