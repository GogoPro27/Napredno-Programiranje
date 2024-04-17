package Napredno_Programiranje.LABS.LABS2.LABS2_3;

import java.util.Arrays;

public class MovablesCollection {
    private Movable[] movable;
    public static int maxX;
    public static int maxY;
    int n;

    MovablesCollection(int x_MAX, int y_MAX){
        maxX = x_MAX;
        maxY = y_MAX;
        n=0;
        movable = new Movable[0];
    }

    public static void setxMax(int maxX) {
        MovablesCollection.maxX = maxX;
    }

    public static void setyMax(int maxY) {
        MovablesCollection.maxY = maxY;
    }
    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if(checkIfOutOfBounds(m))throw new MovableObjectNotFittableException(m);
        movable = Arrays.copyOf(movable,n+1);
        movable[n++] = m;
    }
    public boolean checkIfOutOfBounds(Movable m){
        if(m.getCurrentXPosition()<0 || m.getCurrentXPosition()>maxX){
            if(m.getCurrentYPosition()<0||m.getCurrentYPosition()>maxY){
                return true;
            }
        }
        if(m.getType()==TYPE.CIRCLE){
            int radius = ((MovableCircle)m).getRadius();
            int rightX = m.getCurrentXPosition() + radius;
            int leftX = m.getCurrentXPosition() - radius;
            int upY = m.getCurrentYPosition() + radius;
            int downY = m.getCurrentYPosition() - radius;
            if(rightX>maxX || leftX<0 || upY >maxY || downY <0)return true;
        }
        return false;
    }
    public void moveObjectsFromTypeWithDirection (TYPE type, DIRECTION direction) throws ObjectCanNotBeMovedException {
            for(Movable m:movable){
//                if(m.getType()==type){
//                    if(direction==DIRECTION.UP){
//                        m.moveUp();
//                    }else if(direction==DIRECTION.DOWN){
//                        m.moveDown();
//                    }else if(direction==DIRECTION.LEFT){
//                        m.moveLeft();
//                    } else if(direction==DIRECTION.RIGHT){
//                        m.moveRight();
//                    }
//                }
                if (m.getType() == type) {
                    try {
                        if (direction == DIRECTION.UP) {
                            m.moveUp();
                        } else if (direction == DIRECTION.DOWN) {
                            m.moveDown();
                        } else if (direction == DIRECTION.LEFT) {
                            m.moveLeft();
                        } else if (direction == DIRECTION.RIGHT) {
                            m.moveRight();
                        }
                    } catch (ObjectCanNotBeMovedException e) {
                        e.message();
                        // You can choose to handle the exception here if needed.
                    }
                }
            }
//        List<Movable> objectsToMove = new ArrayList<>();
//
//        for (Movable m : movable) {
//            if (m.getType() == type) {
//                objectsToMove.add(m);
//            }
//        }
//
//        for (Movable m : objectsToMove) {
//            if (direction == DIRECTION.UP) {
//                m.moveUp();
//            } else if (direction == DIRECTION.DOWN) {
//                m.moveDown();
//            } else if (direction == DIRECTION.LEFT) {
//                m.moveLeft();
//            } else if (direction == DIRECTION.RIGHT) {
//                m.moveRight();
//            }
//        }
//
//        for (Movable m : objectsToMove) {
//            if (checkIfOutOfBounds(m)) {
//                throw new ObjectCanNotBeMovedException(m);
//            }
//        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Collection of movable objects with size "+n+":\n");
        for(Movable m :movable) sb.append(m);
        return sb.toString();
    }
}
