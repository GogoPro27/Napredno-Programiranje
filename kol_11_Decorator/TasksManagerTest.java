package SecondPartialExcercises.kol_11_Decorator;
//dobra za dekorator
//konsultacii
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}
class TaskManager{
    private List<TaskComponent>taskComponents;
    private Map<String,List<TaskComponent>> categoryToTasksMap;

    public TaskManager() {
        taskComponents = new ArrayList<>();
        categoryToTasksMap = new HashMap<>();
    }

    public void readTasks (InputStream inputStream){
        BufferedReader br= new BufferedReader(new InputStreamReader(inputStream));
        List<String> inputs = br.lines().collect(Collectors.toList());
        for (String input : inputs) {
            try {
                String[] parts = input.split(",");
                TaskComponent taskComponent = new BaseTaskComponent(parts[1],parts[2]);
                if (parts.length ==4 || parts.length==5) {
                    if (parts[3].length() > 10) {
                        taskComponent = new ExpiringTaskDecorator(taskComponent, LocalDateTime.parse(parts[3]));
                    } else {
                        taskComponent = new PriorityTaskDecorator(taskComponent, Integer.parseInt(parts[3]));
                    }
                }
                if (parts.length == 5) {
                    if (parts[4].length() > 10) {
                        taskComponent = new ExpiringTaskDecorator(taskComponent, LocalDateTime.parse(parts[4]));
                    } else {
                        taskComponent = new PriorityTaskDecorator(taskComponent, Integer.parseInt(parts[4]));
                    }
                }
                taskComponents.add(taskComponent);
                categoryToTasksMap.putIfAbsent(parts[0],new ArrayList<>());
                categoryToTasksMap.get(parts[0]).add(taskComponent);

            }catch (DeadlineNotValidException exception){
                System.out.println(exception.getMessage());
            }
        }
    }
    void printTasks(OutputStream os, boolean includePriority, boolean includeCategory){
        Comparator<TaskComponent> comparator;
        if (includePriority){
            comparator = Comparator.comparing(TaskComponent::getPriority).reversed()
                    .thenComparing(taskComponent -> Duration.between(taskComponent.getDeadline(),LocalDateTime.now())).reversed();
        }else comparator = Comparator.comparing(taskComponent -> -Duration.between(taskComponent.getDeadline(),LocalDateTime.now()).toSeconds());
        //                                                                             zasto ovde (^) ne moze reversed() samo? morav da se snaogjam..
        if (includeCategory){
            categoryToTasksMap.keySet().stream()
                    .forEach(key->{
                        System.out.println(key.toUpperCase());
                        categoryToTasksMap.get(key).stream()
                                .sorted(comparator)
                                .forEach(taskComponent -> System.out.println(taskComponent+"}"));
                    });
        }else {
            taskComponents.stream()
                    .sorted(comparator)
                    .forEach(taskComponent -> System.out.println(taskComponent+"}"));
        }
    }
}
interface TaskComponent{
    public int getPriority();
    public LocalDateTime getDeadline();
}
class BaseTaskComponent implements TaskComponent {
    private String name;
    private String description;

    public BaseTaskComponent(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public LocalDateTime getDeadline() {
        return LocalDateTime.MAX;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'';
    }
}
abstract class TaskDecorator implements TaskComponent{
    protected TaskComponent wrap;

    TaskDecorator(TaskComponent wrap) {
        this.wrap = wrap;
    }
}
class ExpiringTaskDecorator extends TaskDecorator{
    private LocalDateTime deadline;

    public ExpiringTaskDecorator(TaskComponent wrap, LocalDateTime deadline) throws DeadlineNotValidException {
        super(wrap);
        if (deadline.isBefore(LocalDateTime.of(2020,6,2,23,59,59)))
            throw new DeadlineNotValidException("The deadline 2020-06-01T23:59:59 has already passed");
        this.deadline = deadline;
    }

    @Override
    public int getPriority() {
        return wrap.getPriority();
    }

    @Override
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return wrap+", deadline=" + deadline;
    }
}
class PriorityTaskDecorator extends TaskDecorator{
    private int priority;

    public PriorityTaskDecorator(TaskComponent wrap, int priority) {
        super(wrap);
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public LocalDateTime getDeadline() {
        return wrap.getDeadline();
    }

    @Override
    public String toString() {
        return wrap+", priority=" + priority;

    }
}
class DeadlineNotValidException extends Exception{
    public DeadlineNotValidException(String message) {
        super(message);
    }
}

