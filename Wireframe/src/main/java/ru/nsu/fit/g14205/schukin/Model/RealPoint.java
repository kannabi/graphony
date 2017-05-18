package ru.nsu.fit.g14205.schukin.Model;

import lombok.Data;

/**
 * Created by kannabi on 18.04.2017.
 */

@Data
public class RealPoint {
    private double x, y;

    public RealPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return Double.toString(x) + " | " + Double.toString(y);
    }

    public static RealPoint to2D(RealPoint3D point3D){
        return new RealPoint(point3D.getX(), point3D.getY());
    }
}
