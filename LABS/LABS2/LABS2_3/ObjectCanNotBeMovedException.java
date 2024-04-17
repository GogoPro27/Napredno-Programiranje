package Napredno_Programiranje.LABS.LABS2.LABS2_3;

public class ObjectCanNotBeMovedException extends Exception{
    Movable m;
    public ObjectCanNotBeMovedException(Movable m) {
        this.m = m;
    }
    public void message(){
        if(m.getType() == TYPE.POINT){
            System.out.println(String.format("Point (%d,%d) is out of bounds",m.getCurrentXPosition(),m.getCurrentYPosition()));
        }else{
            System.out.println(String.format("Circle (%d,%d) is out of bounds",m.getCurrentXPosition(),m.getCurrentYPosition()));
        }
    }
}
