package Napredno_Programiranje.AUD.aud1;

public class prvaZad {
    public static boolean  isPrefix(String first, String second ){

        if(first.length()>second.length())return false;
        for(int i=0;i<first.length();i++){
            if(first.charAt(i)!=second.charAt(i))return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPrefix("test","testing"));
    }
}
