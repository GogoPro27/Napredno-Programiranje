package Napredno_Programiranje.LABS.LABS1.LABS1_2;

public class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */
    public static String toRoman(int n) {
        StringBuilder string = new StringBuilder();
        int[] silniBrojki = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String [] romanSoodvetno = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        for(int i=0;i<13;i++) {
            while (n >= silniBrojki[i]) {
                string.append(romanSoodvetno[i]);
                n -= silniBrojki[i];
            }
        }
        return string.toString();
    }

}