package Napredno_Programiranje.AUD.aud1;

import java.math.BigDecimal;

public class BigComplex {

    private BigDecimal realPart;
    private BigDecimal imaginaryPart;

    public BigComplex() {
    }

    public BigComplex(BigDecimal realPart, BigDecimal imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    @Override
    public String toString() {
        return "BigComplex{" +
                "realPart=" + realPart +
                ", imaginaryPart=" + imaginaryPart +
                '}';
    }
    public BigComplex add(BigComplex complex){
        return new BigComplex(this.realPart.add(complex.realPart),this.imaginaryPart.add(complex.imaginaryPart));
    }
//    public static BigComplex add(BigComplex complex1, BigComplex complex2){}
}
