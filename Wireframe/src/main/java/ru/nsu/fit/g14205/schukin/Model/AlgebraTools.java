package ru.nsu.fit.g14205.schukin.Model;

import java.util.Vector;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

/**
 * Created by kannabi on 17.04.2017.
 */

//Здесь будут вспомогательные матрицы для работы с матрицами и вот этим всем
public class AlgebraTools {
    public static double [][] multiplicateMatrix(double[][] matrixA, double[][] matrixB) throws AlgebraToolsException{
        double [][] res = new double[matrixA.length][matrixB[0].length];
        double [][] matrixBT = new double[matrixB[0].length][matrixB.length];

        if (matrixA[0].length != matrixB.length)
            throw new AlgebraToolsException("Incorrect matrix size");

        //В этих ваших енторнетах говорят, что если умножать не на исходную, а на транспонированную матрицу,
        //это дает ощутимый прирост в скорости за счет устройства кеша. Не проверял.
        for(int i = 0; i < matrixB.length; ++i)
            for(int j = 0; j < matrixB[0].length; ++j)
                matrixBT[j][i] = matrixB[i][j];

        for(int i = 0; i < matrixA.length; ++i)
            for(int j = 0; j < matrixB[0].length; ++j) {
                res[i][j] = 0;
                for (int k = 0; k < matrixA[0].length; ++k)
                    res[i][j] += matrixA[i][k] * matrixBT[j][k];
            }

        return res;
    }

    public static double getVectorNorm(Vector<Double> vector){
        return vector.stream().reduce(0.0, (sum, el) -> sum + pow(el, 2));
    }

    public static Vector<Double> multiplicateVector(Vector<Double> v0, Vector<Double> v1){
        Vector<Double> res = new Vector<>();

        res.add(v0.get(1) * v1.get(2) - v0.get(2) * v1.get(1));
        res.add(v0.get(2) * v1.get(0) - v0.get(0) * v1.get(2));
        res.add(v0.get(0) * v1.get(1) - v0.get(1) * v1.get(0));

        return res;
    }

    public static RealPoint3D toPoint(Vector<Double> v){
        return new RealPoint3D(v.get(0), v.get(1), v.get(2));
    }

    public static Vector<Double> toVector(RealPoint3D p){
        Vector<Double> res = new Vector<>();

        res.add(p.getX());
        res.add(p.getY());
        res.add(p.getZ());

        return res;
    }
}
