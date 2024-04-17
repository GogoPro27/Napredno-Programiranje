package Napredno_Programiranje.LABS.LABS1.LABS1_3;

public abstract class Transaction {
    private long fromId;
    private long toId;
    private String description;
    private String amount;

    Transaction(){}
    Transaction(long fromId, long toId, String description, String amount){
        this.fromId = fromId;
        this.toId = toId;
        this.description = new String(description);
        this.amount = new String(amount);
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    public String getAmount() {
        return amount;
    }
    public double getDoubleAmount(){
        return Double.parseDouble(amount.substring(0,amount.length()-1));
    }

    public String getDescription() {
        return description;
    }

    public abstract Double getTotalAmount();

}
