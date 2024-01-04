package SecondPartialExcercises.kol_17_Observer_nedovrsena;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private List<IObserver> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherDispatcher() {
        observers = new ArrayList<>();
        pressure =0;
        temperature = -100;
        humidity = -100;
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    public void register(IObserver observer) {
        observers.add(observer);
    }

    public void remove(IObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(IObserver::update);
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
}
abstract class Observer implements IObserver{
    protected WeatherDispatcher observed;

    public Observer(WeatherDispatcher Observed) {
        this.observed = Observed;
    }
}
class CurrentConditionsDisplay extends Observer{
    public CurrentConditionsDisplay(WeatherDispatcher observing) {
        super(observing);
    }

    @Override
    public void update() {
//        System.out.println("Updating CurrentConditionsDisplay");
        System.out.println(String.format("Temperature: %.1fF",observed.getTemperature()));
        System.out.println(String.format("Humidity: %.1f%%",observed.getHumidity()));
        System.out.println();
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
//        System.out.println("Updating ForecastDisplay");
        if (observed.getPressure() > lastPressure){
            System.out.println("Forecast: Improving");
        }else if (observed.getPressure() < lastPressure){
            System.out.println("Forecast: Cooler");
        }else {
            System.out.println("Forecast: Same");
        }
        lastPressure = observed.getPressure();
        System.out.println();
    }
}