package Napredno_Programiranje.VezbanjeStreams;

import java.time.LocalTime;

public class LocalD {
    public static void main(String[] args) {
        LocalTime time = LocalTime.of(23,50,30);
//        String format_24 = time.format(DateTimeFormatter.ofPattern("HH:mm:ss a"));
//        String format_AmPm = time.format(DateTimeFormatter.ofPattern("h:mm:ss"));
//        System.out.println(format_24);
//        System.out.println(format_AmPm);
//        System.out.println();
        System.out.println(time);
        System.out.println(time.plusSeconds(30));
        System.out.println(time.plusHours(10).plusMinutes(10).plusSeconds(59));
    }
}
