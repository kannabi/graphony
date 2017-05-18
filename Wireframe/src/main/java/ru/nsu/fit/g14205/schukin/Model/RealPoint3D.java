package ru.nsu.fit.g14205.schukin.Model;

import lombok.Data;

/**
 * Created by kannabi on 26.04.2017.
 */

@Data
public class RealPoint3D {
    private double x, y, z;

    @Override
    public String toString() {
        return "RealPoint3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public RealPoint3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
