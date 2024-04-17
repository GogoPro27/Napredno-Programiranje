package Napredno_Programiranje.FirstPartialExercises.Timetable_7;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}
class UnsupportedFormatException extends Exception{

    public UnsupportedFormatException(String message) {
        super(message);
    }
}
class InvalidTimeException extends Exception{
    public InvalidTimeException(String message) {
        super(message);
    }
}
enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}
class Time {
    private int hour;
    private int minutes;

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public String format_24(){
        return String.format("%2d:%02d",hour,minutes);
    }
    public String format_AMPM(){
        int h;
        String s = "";
        if(hour==0){
            h=12;
            s="AM";
        }else if(hour>=1&&hour<=11){
            h=hour;
            s="AM";
        }else if(hour==12){
            h=hour;
            s="PM";
        }else {
            h=hour-12;
            s="PM";
        }
        return String.format("%2d:%02d %s",h,minutes,s);
    }
    public String pecati(TimeFormat t){
        if(t == TimeFormat.FORMAT_24){
            return format_24();
        }else {
            return format_AMPM();
        }
    }
    public int getTotalMinutes() {
        return minutes+hour*60;
    }
}
class TimeTable{
    private static List<Time>times;

    public TimeTable() {
        times = new ArrayList<>();
    }
    public static void readTimes(InputStream inputStream) throws UnsupportedFormatException, InvalidTimeException {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()){
            String currTime = scanner.next();
            if(containsDot(currTime)){
                currTime = currTime.replace('.',':');
            }

            if (isUnsupported(currTime))throw new UnsupportedFormatException(currTime);

            int hour,minutes;
            if (currTime.length()==4)currTime='0'+currTime;
            LocalTime t = LocalTime.parse(currTime);
            hour = t.getHour();
            minutes = t.getMinute();
            if (outOfBounds(hour,minutes))throw new InvalidTimeException("Invalid Time: "+currTime);

            times.add(new Time(hour,minutes));
        }
    }
    public void writeTimes(OutputStream outputStream, TimeFormat format){
        PrintWriter pw = new PrintWriter(outputStream);
        times.stream().sorted((t1,t2)->t1.getTotalMinutes()-t2.getTotalMinutes()).forEach(t-> pw.println(t.pecati(format)));
        pw.flush();
    }
    public static boolean isUnsupported(String s){
        int c=0;
        for (int i = 0; i < s.length(); i++) {
            if(!(s.charAt(i)>='0'&&s.charAt(i)<='9')&&!(s.charAt(i)=='.'||s.charAt(i)==':'))return true;
        }return false;
    }
    public static boolean outOfBounds(int h,int m){
        return !(h >= 0 && h <= 23) && !(m >= 0 && m <= 59);
    }
    public static boolean containsDot(String s){
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i)=='.')return true;
        }return false;
    }


}