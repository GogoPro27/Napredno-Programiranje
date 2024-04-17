package Napredno_Programiranje.SecondPartialExercises.kol_7_onceMore;

import java.util.*;
import java.util.stream.Collectors;

public class PayrollSystemTest2 {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 11 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5.5 + i * 2.5);
        }

        Scanner sc = new Scanner(System.in);

        int employeesCount = Integer.parseInt(sc.nextLine());

        PayrollSystem ps = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);
        Employee emp = null;
        for (int i = 0; i < employeesCount; i++) {
            try {
                emp = ps.createEmployee(sc.nextLine());
            } catch (BonusNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }

        int testCase = Integer.parseInt(sc.nextLine());

        switch (testCase) {
            case 1: //Testing createEmployee
                if (emp != null)
                    System.out.println(emp);
                break;
            case 2: //Testing getOvertimeSalaryForLevels()
                ps.getOvertimeSalaryForLevels().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Overtime salary: %.2f\n", level, overtimeSalary);
                });
                break;
            case 3: //Testing printStatisticsForOvertimeSalary()
                ps.printStatisticsForOvertimeSalary();
                break;
            case 4: //Testing ticketsDoneByLevel
                ps.ticketsDoneByLevel().forEach((level, overtimeSalary) -> {
                    System.out.printf("Level: %s Tickets by level: %d\n", level, overtimeSalary);
                });
                break;
            case 5: //Testing getFirstNEmployeesByBonus (int n)
                ps.getFirstNEmployeesByBonus(Integer.parseInt(sc.nextLine())).forEach(System.out::println);
                break;
        }

    }
}
class BonusNotAllowedException extends Exception{
    public BonusNotAllowedException(String message) {
        super(message);
    }
}
class PayrollSystem{
    List<Employee> employees;
    static Map<String,Double> hourlyRateByLevel;
    static Map<String,Double> ticketRateByLevel;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        PayrollSystem.hourlyRateByLevel = hourlyRateByLevel;
        PayrollSystem.ticketRateByLevel = ticketRateByLevel;
        employees = new ArrayList<>();
    }
    public Employee createEmployee (String line) throws BonusNotAllowedException {
        Employee employee = EmployeeFactory.createEmployee(line);
        employees.add(employee);
        return employee;
    }
    public Map<String, Double> getOvertimeSalaryForLevels (){
        return employees.stream()
                .filter(i->i.getType().equals("Hourly"))
                .collect(Collectors.groupingBy(
                        employee->employee.level,
                        Collectors.summingDouble(Employee::overtimeWage)
                ));
    }
    public void printStatisticsForOvertimeSalary (){
        DoubleSummaryStatistics dss = employees.stream()
                .filter(i->i.getType().equals("Hourly"))
                .mapToDouble(Employee::overtimeWage)
                .summaryStatistics();
        System.out.println(String.format("Statistics for overtime salary: Min: %.2f Average: %.2f Max: %.2f Sum: %.2f",
                                dss.getMin(),dss.getAverage(),dss.getMax(),dss.getSum()
        ));

    }
    public Map<String, Integer> ticketsDoneByLevel(){
         return employees.stream()
                 .filter(i->i.getType().equals("Freelance"))
                .collect(Collectors.groupingBy(
                        employee->employee.level,
                        Collectors.summingInt(Employee::ticketsDone)
                ));
    }
    public Collection<Employee> getFirstNEmployeesByBonus (int n){
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getBonus).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
}

class EmployeeFactory{
    public static Employee createEmployee(String line) throws BonusNotAllowedException {
        String [] partsBySpace = line.split("\\s+");
        String [] partsByComma= partsBySpace[0].split(";");

        String id = partsByComma[1];
        String level = partsByComma[2];
        Employee tobeAdded = null;
        switch (partsByComma[0]){
            case "H":{
                double hours = Double.parseDouble(partsByComma[3]);
                tobeAdded = new HourlyEmployee(id,level,hours,PayrollSystem.hourlyRateByLevel.get(level));
                break;
            }
            case "F":{
                List<Integer> points = Arrays.stream(partsByComma)
                        .skip(3)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                tobeAdded = new FreelanceEmployee(id,level,points,PayrollSystem.ticketRateByLevel.get(level));
                break;
            }
        }
        if (tobeAdded ==null)throw new RuntimeException();
        if (partsBySpace.length>1){
            if (partsBySpace[1].contains("%")){
                double percent = Double.parseDouble(partsBySpace[1].replace("%",""));
                if (percent > 20) throw new BonusNotAllowedException(String.format("Bonus of %.2f%% is not allowed",percent));
                tobeAdded.setPercentBonus(percent);
            }else {
                double fixedBonus = Double.parseDouble(partsBySpace[1]);
                if (fixedBonus > 1000)throw new BonusNotAllowedException(String.format("Bonus of %.0f$ is not allowed",fixedBonus));
                tobeAdded.setFixedBonus(fixedBonus);
            }
        }
        return tobeAdded;
    }
}
abstract class Employee{
    String id;
    double bonus;
    String level;

    public Employee(String id, String level) {
        this.id = id;
        this.level = level;
    }

    public double getBonus() {
        return bonus;
    }

    public void setPercentBonus(double bonus) {
        this.bonus = getWage() * ((bonus)/100);
    }
    public void setFixedBonus(double bonus) {
        this.bonus = bonus;
    }
    public abstract double overtimeWage();
    public abstract int ticketsDone();

    public abstract double getWage();
    public abstract String  getType();

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f",id,level,getWage());
    }
}
class HourlyEmployee extends Employee{
    double hours;
    double wageForHour;

    public HourlyEmployee(String id, String level, double hours, double wageForHour) {
        super(id, level);
        this.hours = hours;
        this.wageForHour = wageForHour;
    }

    @Override
    public double overtimeWage() {
      if (getOvertimeHours()>0)return (1.5 * getOvertimeHours() *wageForHour);
        else return 0;
    }

    @Override
    public int ticketsDone() {
        return 0;
    }
    public double getRegularHours(){
        if (hours<=40) return hours;
        else return 40;
    }
    public double getOvertimeHours(){
        if (hours<=40)return 0;
        else return hours-40;
    }
    @Override
    public double getWage() {
            return getRegularHours() * wageForHour + overtimeWage() +bonus;
    }

    @Override
    public String getType() {
        return "Hourly";
    }

    @Override
    public String toString() {
        String s =  super.toString()+String.format(" Regular hours: %.2f Overtime hours: %.2f",getRegularHours(),getOvertimeHours());
        if (getBonus()>0) s+=String.format(" Bonus: %.2f",getBonus());
        return s;
    }
}
class FreelanceEmployee extends Employee{
    List<Integer> ticketPoints ;
    double wageForTicket;

    public FreelanceEmployee(String id, String level, List<Integer> ticketPoints, double wageForTicket) {
        super(id, level);
        this.ticketPoints = ticketPoints;
        this.wageForTicket = wageForTicket;
    }

    @Override
    public double overtimeWage() {
        return 0;
    }

    @Override
    public int ticketsDone() {
        return ticketPoints.size();
    }

    @Override
    public double getWage() {
        return ticketPoints.stream().mapToInt(i->i).sum() * wageForTicket + bonus;
    }

    @Override
    public String getType() {
        return "Freelance";
    }

    @Override
    public String toString() {
        String s = super.toString()+String.format(" Tickets count: %d Tickets points: %d",ticketsDone(),ticketPoints.stream().mapToInt(i->i).sum());
        if (getBonus()>0) s+=String.format(" Bonus: %.2f",getBonus());
        return s;
    }
}


















