/*
 * Fahre solange gerade aus bis das Auto gerade noch wenden kann. 
 */
public class DumpStrategy implements FastCarStrategy, MoveableCarStrategy{
    
    @Override
    public void performMove(Car a, Racetrack track){
        //Perform some moves.
        Directions drivingDirection = Directions.FORWARD;
        int oldX, oldY;
        int x = oldX = a.getX();
        int y = oldY = a.getY();

        if(a.getOrientation() == Orientations.NORTH){
            y = y - 2;
        }else if(a.getOrientation() == Orientations.EAST){
            x = x + 2;
        }else if(a.getOrientation() == Orientations.SOUTH){
            y = y + 2;
        }else if(a.getOrientation() == Orientations.WEST){
            x = x - 2;
        }
        

        try{
            if(track.isEmpty(x, y)){
                
            }
        }catch(NullPointerException e){
            
        }


    }

}
