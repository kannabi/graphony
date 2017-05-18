package ru.nsu.fit.g14205.schukin.Entities;

import static ru.nsu.fit.g14205.schukin.Entities.Const.*;

/**
 * Created by kannabi on 05/03/2017.
 */
public class Hex {
    private int mapX, mapY;
    private int coorX, coorY;
    private double IMPACT;
    private int alive;

    public Hex(int mapX, int mapY, int coorX, int coorY){
        this.mapX = mapX;
        this.mapY = mapY;
        this.coorX = coorX;
        this.coorY = coorY;
        alive = 0;
    }

    public void die(){
        alive = 0;
    }

    public int getMapX(){
        return mapX;
    }

    public int getMapY(){
        return mapY;
    }

    public int getCoorX(){
        return coorX;
    }

    public int getCoorY() {
        return coorY;
    }

    public double getIMPACT(){
        return IMPACT;
    }

    public int isAlive(){
        return alive;
    }

    public void setIMPACT(double impact){
        IMPACT = impact;
    }

    public void setAlive(){
        alive = 1;
    }

    public void setCoords(int x, int y){
        coorX = x;
        coorY = y;
    }
}


