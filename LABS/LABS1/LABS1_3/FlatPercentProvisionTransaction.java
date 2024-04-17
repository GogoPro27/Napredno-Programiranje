package Napredno_Programiranje.LABS.LABS1.LABS1_3;

import java.util.Objects;

public class FlatPercentProvisionTransaction extends Transaction{
    int percent;
    FlatPercentProvisionTransaction (long fromId, long toId, String amount, int centsPerDolar){
        super(fromId,toId,"FlatPercent",amount);
        percent = centsPerDolar;
    }
    public int getPercent(){
        return percent;
    }
    public Double getTotalAmount(){
        double d = (int)getDoubleAmount() * ((double) percent / 100);
        return getDoubleAmount()+d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return percent == that.percent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percent);
    }
}
