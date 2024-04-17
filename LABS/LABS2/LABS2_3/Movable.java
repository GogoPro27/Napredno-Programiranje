package Napredno_Programiranje.LABS.LABS2.LABS2_3;

public interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;

    void moveDown() throws ObjectCanNotBeMovedException;

    void moveRight() throws ObjectCanNotBeMovedException;

    void moveLeft() throws ObjectCanNotBeMovedException;
    public int getCurrentXPosition();
    public int getCurrentYPosition();
    TYPE getType();
}
