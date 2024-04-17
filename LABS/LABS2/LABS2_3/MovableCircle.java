package Napredno_Programiranje.LABS.LABS2.LABS2_3;

public class MovableCircle implements Movable{
    private final int radius;
    private final MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        center.moveUp();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
      center.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        center.moveRight();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        center.moveDown();
    }

    @Override
    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    @Override
    public TYPE getType() {
        return TYPE.CIRCLE;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d\n",center.getCurrentXPosition(),center.getCurrentYPosition(),radius);
    }

    public int getRadius() {
        return radius;
    }

    public MovablePoint getCenter() {
        return center;
    }
}
