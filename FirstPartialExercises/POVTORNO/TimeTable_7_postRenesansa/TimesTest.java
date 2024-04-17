package Napredno_Programiranje.FirstPartialExercises.POVTORNO.TimeTable_7_postRenesansa;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
class InvalidTimeException extends Exception{
    public InvalidTimeException(String message) {
        super(message);
    }
}
class UnsupportedFormatException extends Exception{
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}
class TimeTable{
    private List<LocalTime> times;

    public TimeTable() {
        times = new ArrayList<>();
    }
    public void readTimes(InputStream inputStream) throws InvalidTimeException, UnsupportedFormatException {
        String currTime= "";
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()){
            currTime = scanner.next();
            long hasDot = currTime.chars().filter(c->c=='.').count();

            if(hasDot!=0){
                currTime = currTime.replace('.',':');
            }

            long hasIllegalChar = currTime.chars().filter(c-> c!=':'&& (c<'0'|| c>'9')).count();
            if (hasIllegalChar!=0)throw new UnsupportedFormatException(currTime);

            String []parts = currTime.split(":");
            if(outOfBounds(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])))throw new InvalidTimeException(currTime);

            times.add(LocalTime.parse(currTime,DateTimeFormatter.ofPattern("H:mm")));
        }
    }
    public void writeTimes(OutputStream outputStream, TimeFormat format){
        PrintWriter pw = new PrintWriter(outputStream);
        if(format == TimeFormat.FORMAT_24) {
            times.stream().sorted().forEach(time -> pw.println(format_24(time)));
        }else{
            times.stream().sorted().forEach(time -> pw.println(format_AMPM(time)));
        }

        pw.flush();
    }
    public String format_24(LocalTime time){
        return String.format("%5s",time.format(DateTimeFormatter.ofPattern("H:mm")));
    }
    public String format_AMPM(LocalTime time){
        return String.format("%8s",time.format(DateTimeFormatter.ofPattern("h:mm a")));
    }
    public static boolean outOfBounds(int h,int m){
        return !(h >= 0 && h <= 23) && !(m >= 0 && m <= 59);
    }

}