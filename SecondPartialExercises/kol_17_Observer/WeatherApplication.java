package Napredno_Programiranje.SecondPartialExercises.kol_17_Observer;

import java.util.*;

public class WeatherApplication {

    public static void main(String[] args) {
        WeatherDispatcher weatherDispatcher = new WeatherDispatcher();

        CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherDispatcher);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherDispatcher);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            weatherDispatcher.setMeasurements(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
            if(parts.length > 3) {
                int operation = Integer.parseInt(parts[3]);
                if(operation==1) {
                    weatherDispatcher.remove(forecastDisplay);
                }
                if(operation==2) {
                    weatherDispatcher.remove(currentConditions);
                }
                if(operation==3) {
                    weatherDispatcher.register(forecastDisplay);
                }
                if(operation==4) {
                    weatherDispatcher.register(currentConditions);
                }

            }
        }
    }
}



class WeatherDispatcher{
    private Set<IObserver> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherDispatcher() {
        observers = new TreeSet<>(Comparator.comparing(IObserver::priority));
        pressure =0;
        temperature = 0;
        humidity = 0;
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;

        notifyObservers();
        System.out.println();
    }

    public void register(IObserver observer) {
        observers.add(observer);
    }

    public void remove(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.stream()
                .forEach(IObserver::update);
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
interface IObserver{
    void update();
    int priority();

}
abstract class Observer implements IObserver{
    protected WeatherDispatcher observed;


    public Observer(WeatherDispatcher Observed) {
        this.observed = Observed;
        observed.register(this);
    }

}
class CurrentConditionsDisplay extends Observer{
    public CurrentConditionsDisplay(WeatherDispatcher observing) {
        super(observing);
    }

    @Override
    public void update() {
        System.out.println(String.format("Temperature: %.1fF",observed.getTemperature()));
        System.out.println(String.format("Humidity: %.1f%%",observed.getHumidity()));
    }

    @Override
    public int priority() {
        return 0 ;
    }
}
class ForecastDisplay extends Observer{
    private float lastPressure;

    public ForecastDisplay(WeatherDispatcher observing) {
        super(observing);
        lastPressure = observed.getPressure();
    }

    @Override
    public void update() {
        if (observed.getPressure() > lastPressure){
            System.out.println("Forecast: Improving");
        }else if (observed.getPressure() < lastPressure){
            System.out.println("Forecast: Cooler");
        }else {
            System.out.println("Forecast: Same");
        }
        lastPressure = observed.getPressure();
    }

    @Override
    public int priority() {
        return 1;
    }

}
