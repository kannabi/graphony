package ru.nsu.ccfit.schukin.Entities;

import ru.nsu.ccfit.schukin.Interfaces.IsolineModelInterface;

import java.text.DecimalFormat;

import static java.lang.Math.pow;
import static ru.nsu.ccfit.schukin.Entities.Const.ACCURACY;

/**
 * Created by kannabi on 03.04.2017.
 */
public class FunctionData {
    private double [][] coords;
    private IsolineModelInterface model;
    private double xBorder;
    private double yBorder;


    public FunctionData(double xBorder, double yBorder, IsolineModelInterface model){
        this.model = model;
        this.xBorder = xBorder;
        this.yBorder = yBorder;
        coords = new double[(int)(xBorder * ACCURACY + 1)][(int)(yBorder * ACCURACY + 1)];
        initValues();
    }

    public void initValues(){
        int highBorderX = coords.length;
        int highBorderY = coords[0].length;

        for (int i = 0; i < highBorderX; ++i)
            for (int j = 0; j < highBorderY; ++j) {
                coords[i][j] = model.getFunctionValue((double)(i) / ACCURACY, (double)(j) / ACCURACY);
            }
    }

    public void printData(){
        int highBorderX = coords.length;
        int highBorderY = coords[0].length;
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < highBorderX; ++i) {
            for (int j = 0; j < highBorderY; ++j)
                str.append(new DecimalFormat("#.#").format(get(i, j)) + " | ");
            System.out.println(str.toString());
            str = new StringBuilder();
        }
    }

    public int getHighBorderX(){
        return coords.length - 1;
    }

    public int getHighBorderY(){
        return coords[0].length - 1;
    }

    public double get(int x, int y) throws ArrayIndexOutOfBoundsException{
            return coords[x][y];
    }

    public double getValueX(int i){
        return (double) i / ACCURACY;
    }

    public double getValueY(int j){
        return (double) j / ACCURACY;
    }

    public double getPixelFunctionValue(int x, int y, int height, int width){
        int cellX = x / (width / (int)pow(ACCURACY, 2));
        int cellY = y / (height / (int)pow(ACCURACY, 2));

        return model.getFunctionValue((double) cellX / pow(ACCURACY, 2), (double) cellY / pow(ACCURACY, 2));
    }
}