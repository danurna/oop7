
public class Tile {
    private Car car;

    public Car getCar() {
        return car;
    }

    // VB kein Auto vorhanden
    // NB Auto vorhanden
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
