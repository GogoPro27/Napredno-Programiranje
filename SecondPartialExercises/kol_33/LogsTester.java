package Napredno_Programiranje.SecondPartialExercises.kol_33;
// package SecondPartialExcercises.kol_33;

import java.util.*;
import java.util.stream.Collectors;

public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k,v)-> System.out.printf("%d -> %d%n", k,v));
            } else if (line.startsWith("displayLogs")){
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                System.out.println(line);

                collector.displayLogs(service, microservice, order);
            }
        }
    }
}
class LogCollector{
    private final Map<String, List<Log>> serviceLogsMap;
    //    private Map<String, List<Log>> microServiceLogsMap;
    private final Map<String,Map<String,List<Log>>> microServicesInServices;

    public LogCollector() {
        serviceLogsMap = new HashMap<>();
        microServicesInServices = new HashMap<>();
    }

    public void addLog (String log){
        Log newLog = LogFactory.createLog(log);
        String service = newLog.getServiceName();
        String microService = newLog.getMicroServiceName();

        serviceLogsMap.putIfAbsent(service,new ArrayList<>());
        microServicesInServices.putIfAbsent(service,new HashMap<>());
        microServicesInServices.get(service).putIfAbsent(microService,new ArrayList<>());

        serviceLogsMap.get(service).add(newLog);
        microServicesInServices.get(service).get(microService).add(newLog);

    }
    void printServicesBySeverity(){
        serviceLogsMap.entrySet().stream()//probaj i so comparingByValue , zasto ne dava reversed?
                .sorted(Comparator.comparing(serviceListEntry -> -averageSeverity(serviceListEntry.getValue())))
                .forEach(serviceListEntry->{
                    printService(serviceListEntry.getKey());
                });
    }
    public void printService(String serviceName){
        int micros = microServicesInServices.get(serviceName).keySet().size();
        int logs = serviceLogsMap.get(serviceName).size();
        double avgLogsInMicros = microServicesInServices.get(serviceName)
                .values().stream()
                .flatMap(Collection::stream)
                .count()/(double)micros;

        System.out.println(String.format("Service name: %s Count of microservices: %d Total logs in service: %d Average severity for all logs: %.2f Average number of logs per microservice: %.2f",
                serviceName,micros,logs,averageSeverity(serviceLogsMap.get(serviceName)),avgLogsInMicros));
    }
    public double averageSeverity(List<Log> list){
        return list.stream()
                .mapToDouble(Log::severity)
                .average().orElse(0);
    }
    Map<Integer, Integer> getSeverityDistribution(String service, String microservice){
        Map<Integer, Integer> getSeverityDistribuition;
        if (microservice!=null) {
            getSeverityDistribuition = microServicesInServices.get(service).get(microservice).stream()
                    .collect(Collectors.groupingBy(
                            Log::severity,
                            Collectors.summingInt(i -> 1)
                    ));
        }else {
            getSeverityDistribuition = serviceLogsMap.get(service).stream()
                    .collect(Collectors.groupingBy(
                            Log::severity,
                            Collectors.summingInt(i -> 1)
                    ));
        }
        return getSeverityDistribuition;
    }
    void displayLogs(String service, String microservice, String order){
        if (microServicesInServices.get(service).containsKey(microservice)) {
//            System.out.println("KONECNO VLEGOV!");
            microServicesInServices.get(service).get(microservice).stream()
                    .sorted(ComparatorFactory.createComparator(order))
                    .forEach(System.out::println);
        }
        else
            microServicesInServices.get(service).keySet().stream()
                    .map(key->microServicesInServices.get(service).get(key))
                    .flatMap(Collection::stream)
                    .sorted(ComparatorFactory.createComparator(order))
                            .forEach(System.out::println);

    }

}
class ComparatorFactory{
    public static Comparator<Log> createComparator(String type){
        switch (type){
            case "NEWEST_FIRST":
                return Comparator.comparing(Log::getTimestamp).reversed();
            case "OLDEST_FIRST":
                return Comparator.comparing(Log::getTimestamp);
            case "MOST_SEVERE_FIRST":
                return Comparator.comparing(Log::severity).thenComparing(Log::getTimestamp).thenComparing(Log::getMicroServiceName).reversed();
            case "LEAST_SEVERE_FIRST":
                return Comparator.comparing(Log::severity).thenComparing(Log::getTimestamp).thenComparing(Log::getMicroServiceName);
            default:return Comparator.comparing(Log::getServiceName);
        }
    }
}
class LogFactory{
    public static Log createLog(String line){
        String[]parts = line.split("\\s+");
        switch (parts[2]){
            case "INFO":{
                return new InfoLog(parts[0],parts[1], Arrays.stream(parts).skip(3).limit(parts.length-4).collect(Collectors.joining(" ")), Long.parseLong(parts[parts.length-1]));
            }
            case  "WARN":{
                return new WarnLog(parts[0],parts[1], Arrays.stream(parts).skip(3).limit(parts.length-4).collect(Collectors.joining(" ")), Long.parseLong(parts[parts.length-1]));
            }
            case "ERROR":{
                return new ErrorLog(parts[0],parts[1], Arrays.stream(parts).skip(3).limit(parts.length-4).collect(Collectors.joining(" ")), Long.parseLong(parts[parts.length-1]));
            }
            default:return null;
        }
    }
}
abstract class Log{
    private String serviceName;
    private String microServiceName;
    private String message;
    private long timestamp;
    public static final Comparator<Log> COMPARATOR =
            Comparator.comparing(Log::severity).reversed();

    public Log(String serviceName, String microServiceName, String message, long timestamp) {
        this.serviceName = serviceName;
        this.microServiceName = microServiceName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMicroServiceName() {
        return microServiceName;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public abstract int severity();

    @Override
    public String toString() {
        return "Log{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
class InfoLog extends Log{
    public InfoLog(String serviceName, String microServiceName, String message, long timestamp) {
        super(serviceName, microServiceName, message, timestamp);
    }

    @Override
    public int severity() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s|%s [INFO] %s %d T:%d",getServiceName(),getMicroServiceName(),getMessage(),getTimestamp(),getTimestamp());
    }
}
class WarnLog extends Log{
    public WarnLog(String serviceName, String microServiceName, String message, long timestamp) {
        super(serviceName, microServiceName, message, timestamp);
    }

    @Override
    public int severity() {
        return 1 + (getMessage().contains("might cause error")?1:0);
    }
    @Override
    public String toString() {
        return String.format("%s|%s [WARN] %s %d T:%d",getServiceName(),getMicroServiceName(),getMessage(),getTimestamp(),getTimestamp());
    }
}
class ErrorLog extends Log {
    public ErrorLog(String serviceName, String microServiceName, String message, long timestamp) {
        super(serviceName, microServiceName, message, timestamp);
    }

    @Override
    public int severity() {
        int severity =3;
        severity+=getMessage().contains("fatal") ? 2 : 0;
        severity+=getMessage().contains("exception") ? 3 : 0;
        return severity;
    }
    @Override
    public String toString() {
        return String.format("%s|%s [ERROR] %s %d T:%d",getServiceName(),getMicroServiceName(),getMessage(),getTimestamp(),getTimestamp());
    }
}
