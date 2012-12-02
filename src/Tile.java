
public class Tile {
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    
    public boolean isEmpty() {
        return car == null;
    }
    
    // VB Auto vorhanden
    // NB kein auto mehr vorhanden
    public void removeCar() {
        car = null;
    }
    
    
}
