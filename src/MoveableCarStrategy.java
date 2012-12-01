/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public interface MoveableCarStrategy {
    /*
     * Setzt Interaktionen zum Ueberpruefen des naechsten Schritts und
     * fuehrt diesen ggf auch aus.
     */
    public void performMove(Car a, Racetrack track);
}
