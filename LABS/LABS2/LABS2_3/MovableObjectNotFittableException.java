package Napredno_Programiranje.LABS.LABS2.LABS2_3;

public class MovableObjectNotFittableException extends Exception{
    Movable m;
    public MovableObjectNotFittableException(Movable m) {
        this.m = m;
    }
    public void message(){
        if(m.getType() == TYPE.POINT){
            System.out.println(String.format("Point (%d,%d) is out of bounds\n",m.getCurrentXPosition(),m.getCurrentYPosition()));
        }else{
            System.out.println(String.format("Movable circle with center (%d,%d) and radius %d can not be fitted into the collection",m.getCurrentXPosition(),m.getCurrentYPosition(),((MovableCircle)m).getRadius()));
        }
    }
}
