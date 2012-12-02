/*
 * Fahre solange gerade aus bis das Auto gerade noch wenden kann. 
 */
public class DumbStrategy implements FastCarStrategy, MoveableCarStrategy{
    
    @Override
    public void performMove(Car a, Racetrack track) throws InterruptedException{
        //Perform some moves.
        Directions drivingDirection = Directions.FORWARD;
        int oldX, oldY;
        int x = oldX = a.getX();
        int y = oldY = a.getY();

        if(a.getOrientation() == Orientations.NORTH){
            y = y - 1;
        }else if(a.getOrientation() == Orientations.EAST){
            x = x + 1;
        }else if(a.getOrientation() == Orientations.SOUTH){
            y = y + 1;
        }else if(a.getOrientation() == Orientations.WEST){
            x = x - 1;
        }
        
        try{
            track.moveTo(a, drivingDirection);
        }catch(OutOfRacetrackException e){
            System.out.println("OutOfRacetrackException");
        }
        /*
        try{
            if(track.isEmpty(x, y)){
                
            }
        }catch(NullPointerException e){
            
        }
        * *
        */


    }

}
