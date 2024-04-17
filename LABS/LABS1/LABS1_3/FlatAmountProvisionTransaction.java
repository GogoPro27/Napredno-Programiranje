package Napredno_Programiranje.LABS.LABS1.LABS1_3;

import java.util.Objects;

public class FlatAmountProvisionTransaction extends Transaction{
    private String flatAmount;
    FlatAmountProvisionTransaction(long fromId, long toId,String amount, String flatProvision){
        super(fromId,toId,"FlatAmount",amount);
        this.flatAmount = flatProvision;
    }
    public String getFlatAmount(){
        return flatAmount;
    }
    public Double getDoubleFlatAmount(){
        return Double.parseDouble(flatAmount.substring(0,flatAmount.length()-1));
    }
    public Double getTotalAmount(){
        return getDoubleFlatAmount()+getDoubleAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Objects.equals(flatAmount, that.flatAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flatAmount);
    }
}
