package ru.nsu.fit.g14205.schukin.Entities;

/**
 * Created by kannabi on 04/03/2017.
 */
public class Span {
    int left, right;
    int y;

    public Span(int left, int right, int y){
        this.left = left;
        this.right = right;
        this.y = y;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getY() {
        return y;
    }

    public void expandLeft(){
        --left;
    }

    public void expandRight(){
        ++left;
    }
}
