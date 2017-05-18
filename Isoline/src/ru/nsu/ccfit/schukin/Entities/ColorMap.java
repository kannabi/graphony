package ru.nsu.ccfit.schukin.Entities;

import sun.plugin.javascript.navig4.Link;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kannabi on 03.04.2017.
 */

public class ColorMap {
    LinkedList<Double> values = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();

    public ColorMap(Color color){
        colors.add(color);
    }

    public ColorMap(){}

    public void putAll(LinkedList<Double> values, LinkedList<Color> colors) throws ColorMapException{
        if(colors.size() - values.size() != 1)
            throw new ColorMapException("Wrong number of colors and values!");

        this.values.addAll(values);
        this.colors.addAll(colors);
    }

    public void putFirstColor(Color color){
        colors.add(color);
    }

    public void put(double value, Color color) throws ColorMapException{
        if(colors.size() - values.size() != 1)
            throw new ColorMapException("There are no first color in the map!");

        this.values.add(value);
        this.colors.add(color);
    }

    public ColorMap clone(){
        ColorMap colorMap = new ColorMap();

        return colorMap;
    }

    public Color get(double val){
        for (int i = 0; i < values.size(); ++i)
            if (val < values.get(i))
                return colors.get(i);
        return colors.getLast();
    }

    public int colorNumber(){
        return colors.size();
    }

    public double getCellKey(int i) throws  IOException{
        return values.get(i);
    }

    public Color getCellValue(int i) throws IOException{
        return colors.get(i);
    }
}