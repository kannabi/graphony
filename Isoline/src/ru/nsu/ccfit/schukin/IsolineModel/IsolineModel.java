package ru.nsu.ccfit.schukin.IsolineModel;

import ru.nsu.ccfit.schukin.Entities.ColorMap;
import ru.nsu.ccfit.schukin.Entities.ColorMapException;
import ru.nsu.ccfit.schukin.Interfaces.IsolineModelInterface;
import ru.nsu.ccfit.schukin.Interfaces.IsolinePresenterInterface;

import java.awt.*;
import java.util.LinkedList;

import static java.lang.Math.*;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static ru.nsu.ccfit.schukin.Entities.Const.X_MAX;
import static ru.nsu.ccfit.schukin.Entities.Const.Y_MAX;

/**
 * Created by kannabi on 02.04.2017.
 */
public class IsolineModel implements IsolineModelInterface {
    private IsolinePresenterInterface presenter;
    private int k, m;
    private int n;
    private double xMin = 0, xMax = X_MAX;
    private double yMin = 0, yMax = Y_MAX;
    private ColorMap colorMap = new ColorMap();
    private Color isolineColor;

//    public IsolineModel(LinkedList<Color> colors){
    public IsolineModel(){
        LinkedList<Color> colors = new LinkedList<>();
        colors.add(new Color(0x000000));
//        colors.add(new Color(0x960000));
        colors.add(new Color(0xAF0000));
        colors.add(new Color(0xC80000));
        colors.add(new Color(0xE10000));
        colors.add(new Color(0xFF0000));
        initColorMap(colors);
    }

    @Override
    public void setPresenter(IsolinePresenterInterface presenter){
        this.presenter = presenter;
    }

    @Override
    public IsolinePresenterInterface getPresenter(){
        return presenter;
    }

    @Override
    public double getFunctionValue(double x, double y){
        return pow(x, 2) + pow(y, 2);
    }

    private void initColorMap(LinkedList<Color> colors){
        double fValue = getFunctionValue(xMin, yMin);
        double sValue = getFunctionValue(xMax, yMax);

        double min = min(fValue, sValue);
        double max = max(fValue, sValue);

        double step = (max - min) / colors.size();

        LinkedList<Double> values = new LinkedList<>();
        fValue = min;
        for (int i = 0; i < colors.size() - 1; ++i, fValue += step)
            values.add(fValue);


        try {
            colorMap.putAll(values, colors);
        } catch (ColorMapException e){
            e.printStackTrace();
        }
    }

    @Override
    public ColorMap getColorMap(){
        return colorMap;
    }
}
