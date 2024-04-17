package SecondPartialExcercises.kol_30_State;

//package mk.ukim.finki.midterm;


import java.util.*;
import java.util.stream.Collectors;

class DurationConverter {
    public static String convert(long duration) {
        long minutes = duration / 60;
        duration %= 60;
        return String.format("%02d:%02d", minutes, duration);
    }
}


public class TelcoTest2 {
    public static void main(String[] args) {
        TelcoApp app = new TelcoApp();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String command = parts[0];

            if (command.equals("addCall")) {
                String uuid = parts[1];
                String dialer = parts[2];
                String receiver = parts[3];
                long timestamp = Long.parseLong(parts[4]);
                app.addCall(uuid, dialer, receiver, timestamp);
            } else if (command.equals("updateCall")) {
                String uuid = parts[1];
                long timestamp = Long.parseLong(parts[2]);
                String action = parts[3];
                app.updateCall(uuid, timestamp, action);
            } else if (command.equals("printChronologicalReport")) {
                String phoneNumber = parts[1];
                app.printChronologicalReport(phoneNumber);
            } else if (command.equals("printReportByDuration")) {
                String phoneNumber = parts[1];
                app.printReportByDuration(phoneNumber);
            } else {
                app.printCallsDuration();
            }
        }

    }
}
class TelcoApp{
    private Map<String,List<Call>> numberToCallsMap;
    private Map<String , Call> uuidToCallMap;

    public TelcoApp() {
        numberToCallsMap = new HashMap<>();
        uuidToCallMap = new HashMap<>();
    }

    void addCall (String uuid, String dialer, String receiver, long timestamp){
        Call call= new Call(uuid,dialer,receiver,timestamp);

        numberToCallsMap.putIfAbsent(dialer,new ArrayList<>());
        numberToCallsMap.putIfAbsent(receiver,new ArrayList<>());

        numberToCallsMap.get(dialer).add(call);
        numberToCallsMap.get(receiver).add(call);

        uuidToCallMap.put(uuid,call);
    }
    public void updateCall (String uuid, long timestamp, String action){
        Call reference = uuidToCallMap.get(uuid);
        switch (action){
            case "ANSWER":{
                reference.answer(timestamp);
                break;
            }
            case "END":{
                reference.end(timestamp);
                break;
            }
            case "HOLD":{
                reference.hold(timestamp);
                break;
            }
            case "RESUME":{
                reference.resume(timestamp);
                break;
            }
            default:{
                throw new RuntimeException();
            }
        }
    }
    public void printChronologicalReport(String phoneNumber){
        numberToCallsMap.get(phoneNumber).stream()
                .sorted(Call.COMPARATOR1)
                .forEach(call -> {
                    String RorD = phoneNumber.equals(call.getDialer())?"D":"R";
                    String otherNum = RorD.equals("D")?call.getReceiver(): call.getDialer();
                    System.out.println(String.format("%s %s %s",RorD,otherNum,call));
                });
    }
    void printReportByDuration(String phoneNumber){
        numberToCallsMap.get(phoneNumber).stream()
                .sorted(Call.COMPARATOR2)
                .forEach(call -> {
                    String RorD = phoneNumber.equals(call.getDialer())?"D":"R";
                    String otherNum = RorD.equals("D")?call.getReceiver(): call.getDialer();
                    System.out.println(String.format("%s %s %s",RorD,otherNum,call));
                });
    }
    void printCallsDuration(){
        Map<String,Long> map= uuidToCallMap.values().stream()
                    .collect(Collectors.groupingBy(
                            call -> String.format("%s <-> %s",call.getDialer(),call.getReceiver()),
                            Collectors.summingLong(Call::getDuration)
                    ));
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(stringLongEntry -> {
                    System.out.println(String.format("%s : %s",stringLongEntry.getKey(),DurationConverter.convert(stringLongEntry.getValue())));
                });
    }

}
interface State{
    void answer(long timestamp);
    void end(long timestamp);
    void hold(long timestamp);
    void resume(long timestamp);
}
abstract class CallState implements State{
    Call call;

    public CallState(Call call) {
        this.call = call;
    }
}
class CallingInProgressState extends CallState {
    public CallingInProgressState(Call call) {
        super(call);
    }

    @Override
    public void answer(long timestamp) {
        call.setTimestampStarted(timestamp);
        call.setState(new CallInProgressState(call));
    }

    @Override
    public void end(long timestamp) {
        call.setTimestampEnded(timestamp);
        call.setTimestampStarted(timestamp);
        call.MISSEDCALL = true;
        call.setState(new CallEndedState(call));
    }

    @Override
    public void hold(long timestamp) {
        //TODO nothing
    }

    @Override
    public void resume(long timestamp) {
        //TODO nothing
    }
}
class CallInProgressState extends CallState{
    public CallInProgressState(Call call) {
        super(call);
    }

    @Override
    public void answer(long timestamp) {
        //TODO nothing
    }

    @Override
    public void end(long timestamp) {
        call.setTimestampEnded(timestamp);
        call.setState(new CallEndedState(call));
    }

    @Override
    public void hold(long timestamp) {
        call.setHoldTimeStart(timestamp);
        call.setState(new CallOnHoldState(call));
    }

    @Override
    public void resume(long timestamp) {
        //TODO nothing
    }
}
class CallEndedState extends CallState{

    public CallEndedState(Call call) {
        super(call);
    }

    @Override
    public void answer(long timestamp) {
        //TODO nothing
    }

    @Override
    public void end(long timestamp) {
        //TODO nothing
    }

    @Override
    public void hold(long timestamp) {
        //TODO nothing
    }

    @Override
    public void resume(long timestamp) {
        //TODO nothing
    }
}
class CallOnHoldState extends CallState{

    public CallOnHoldState(Call call) {
        super(call);
    }

    @Override
    public void answer(long timestamp) {
        //TODO nothing
    }

    @Override
    public void end(long timestamp) {
        call.setTimestampEnded(timestamp);
        call.addHoldTime(timestamp-call.getHoldTimeStart());
        call.setState(new CallEndedState(call));
    }

    @Override
    public void hold(long timestamp) {
        //TODO nothing
    }

    @Override
    public void resume(long timestamp) {
        call.addHoldTime(timestamp-call.getHoldTimeStart());
        call.setState(new CallInProgressState(call));
    }
}
class Call implements State{
    private String uuid;
    private String dialer;
    private String receiver;
    private long timestampStarted;
    private long timestampEnded;
    private long holdTime;
    private long holdTimeStart;
    protected boolean MISSEDCALL;
    public static final Comparator<Call> COMPARATOR1 =
            Comparator.comparing(Call::getTimestampStarted);
    public static final Comparator<Call> COMPARATOR2 =
            Comparator.comparing(Call::getDuration).thenComparing(Call::getTimestampStarted).reversed();

    private State state;

    public Call(String uuid, String dialer, String receiver, long timestampCalled) {
        this.uuid = uuid;
        this.dialer = dialer;
        this.receiver = receiver;
        state = new CallingInProgressState(this);
        holdTime = 0;
        MISSEDCALL = false;
    }

    @Override
    public void answer(long timestamp) {
        state.answer(timestamp);
    }

    @Override
    public void end(long timestamp) {
        state.end(timestamp);
    }

    @Override
    public void hold(long timestamp) {
        state.hold(timestamp);
    }

    @Override
    public void resume(long timestamp) {
        state.resume(timestamp);
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setTimestampStarted(long timestampStarted) {
        this.timestampStarted = timestampStarted;
    }

    public void setTimestampEnded(long timestampEnded) {
        this.timestampEnded = timestampEnded;
    }

    public void addHoldTime(long holdTime) {
        this.holdTime += holdTime;
    }

    public void setHoldTimeStart(long holdTimeStart) {
        this.holdTimeStart = holdTimeStart;
    }

    public long getHoldTimeStart() {
        return holdTimeStart;
    }

    public long getTimestampStarted() {
        return timestampStarted;
    }

    public long getDuration(){
        if (!MISSEDCALL)
            return timestampEnded-timestampStarted-holdTime;
        else return 0;
    }
    @Override
    public String toString() {
        if (!MISSEDCALL)
            return String.format("%d %d %s",timestampStarted,timestampEnded,DurationConverter.convert(getDuration()));
        else return String.format("%d MISSED CALL 00:00",timestampStarted);
    }

    public String getDialer() {
        return dialer;
    }

    public String getReceiver() {
        return receiver;
    }


}



















