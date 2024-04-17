package Napredno_Programiranje.LABS.LABS2.LABS2_3;

public class MovablePoint implements Movable{

    private int x , y;
    private final int xSpeed;
    private final int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }


    public void tryMoving(DIRECTION direction) throws ObjectCanNotBeMovedException {
        if(direction==DIRECTION.RIGHT){
            if((x+xSpeed)>MovablesCollection.maxX)throw new ObjectCanNotBeMovedException(new MovablePoint(x+xSpeed,y,1,1));
        }else if(direction==DIRECTION.LEFT){
            if((x-xSpeed)<0)throw new ObjectCanNotBeMovedException(new MovablePoint(x-xSpeed,y,1,1));
        }
        if(direction==DIRECTION.UP){
            if((y+ySpeed)>MovablesCollection.maxY)throw new ObjectCanNotBeMovedException(new MovablePoint(x,y+ySpeed,1,1));
        }else if(direction==DIRECTION.DOWN){
            if((y-ySpeed)<0)throw new ObjectCanNotBeMovedException(new MovablePoint(x,y-ySpeed,1,1));
        }
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        tryMoving(DIRECTION.UP);
        y += ySpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        tryMoving(DIRECTION.LEFT);
        x-=xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        tryMoving(DIRECTION.RIGHT);
        x+=xSpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        tryMoving(DIRECTION.DOWN);
        y-=ySpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)\n",x,y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    @Override
    public TYPE getType() {
        return TYPE.POINT;
    }
}
