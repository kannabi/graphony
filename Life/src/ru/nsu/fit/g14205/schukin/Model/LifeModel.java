package ru.nsu.fit.g14205.schukin.Model;

import ru.nsu.fit.g14205.schukin.Entities.Hex;
import ru.nsu.fit.g14205.schukin.Entities.HexMap;
import ru.nsu.fit.g14205.schukin.Entities.Point;
import ru.nsu.fit.g14205.schukin.Entities.ToolBarButton;
import ru.nsu.fit.g14205.schukin.Interfaces.LifeModelInterface;

import java.util.LinkedList;

import static java.lang.Math.*;
import static ru.nsu.fit.g14205.schukin.Entities.ToolBarButton.RPLC;
import static ru.nsu.fit.g14205.schukin.Entities.ToolBarButton.XOR;

/**
 * Created by kannabi on 05.03.2017.
 */

public class LifeModel implements LifeModelInterface{
    private int m;
    private int n;
    private int len;
    private int thickness;
    private int pad;
    private int gridSection;
    private ToolBarButton replaceMode = RPLC;

    private double live_begin;
    private double live_end;
    private double birth_begin;
    private double birth_end;
    private double fst_impact;
    private double snd_impact;

    private HexMap hexMap;

    private void happyNewYear(){
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                calcImpact(hexMap.getHex(i, j));
    }


    private void calcImpact(Hex hex){
        double newImpact = 0;
        int i = hex.getMapX();
        int j = hex.getMapY();

        newImpact = (hexMap.hexAliveState(i - 1, j + i % 2) + hexMap.hexAliveState(i + 1, j + i % 2) +
                    hexMap.hexAliveState(i, j + 1) + hexMap.hexAliveState(i, j - 1) +
                    hexMap.hexAliveState(i - 1, j - 1 + i % 2) + hexMap.hexAliveState(i + 1, j - 1 + i % 2))
                    * fst_impact;

        newImpact += (hexMap.hexAliveState(i - 1, j - 2 + i % 2) + hexMap.hexAliveState(i + 1, j - 2 + i % 2) +
                        hexMap.hexAliveState(i + 2, j) + hexMap.hexAliveState(i - 2, j) +
                        hexMap.hexAliveState(i - 1, j + 1 + i % 2) + hexMap.hexAliveState(i + 1, j + 1 + i % 2))
                        * snd_impact;

        hex.setIMPACT(newImpact);
    }


    @Override
    public HexMap buildMap(int len, int m, int n, int thickness){
        this.len = len;
        this.pad = len;
        this.m = m;
        this.n = n;
        this.thickness = thickness;
        hexMap = new HexMap(m, n);

        hexMap.setLive_begin(live_begin);
        hexMap.setLive_end(live_end);
        hexMap.setBirth_begin(birth_begin);
        hexMap.setBirth_end(birth_end);
        hexMap.setLength(len);
        hexMap.setThickness(thickness);

        int toLowLevel = (int)(3 * len / 2);
        int step = (int)(len * sqrt(3));
        gridSection = step;

        for(int j = pad, l = 0;l < m; j += 2 * toLowLevel, l += 2){
            for (int i = pad, k = 0;k < n; i += step, ++k){
                hexMap.setHex(l, k, i, j);
                hexMap.setHex(l + 1, k,i + step / 2, j + toLowLevel);
            }
        }

        refreshMap();

        return hexMap;
    }


    @Override
    public HexMap refreshMap(){
        happyNewYear();
        hexMap.refreshAliveState();
        return hexMap;
    }


    @Override
    public HexMap addAliveHex(int x, int y){
        Hex curHex = determinateHex(x, y);

        try {
            if (replaceMode == XOR)
                if (is(hexMap.getHex(curHex.getMapX(), curHex.getMapY()).isAlive()))
                    hexMap.killHex(curHex.getMapX(), curHex.getMapY());
                else
                    hexMap.setHexAlive(curHex.getMapX(), curHex.getMapY());
            else
                hexMap.setHexAlive(curHex.getMapX(), curHex.getMapY());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return hexMap;
    }

    @Override
    public void addInitsHexes(LinkedList<Point> points){
        for (Point it : points)
            hexMap.setHexAlive(it.getX(), it.getY());
    }


    /*We divide up map by grid. Nodes of the grid are centres of hexes.
    * After that we found area of our point and determinate hex by distance*/
    private Hex determinateHex(int x, int y){
        int iStep = (int)(len * 3 / 2);
        int jStep = gridSection;

        //Bottom approximation
        int iA = (int)(y/iStep);
        int jA = (int)(x/jStep) - 1;
        if (iA > m || jA > n)
            return null;

        double dif = distance(x, y, hexMap.getHex(iA, jA).getCoorX(), hexMap.getHex(iA, jA).getCoorY()) -
                distance(x, y, hexMap.getHex(iA, jA + 1).getCoorX(), hexMap.getHex(iA, jA + 1).getCoorY());

        if (dif == 0.0)
            return null;

        return dif < 0 ? hexMap.getHex(iA, jA) : hexMap.getHex(iA, jA + 1);
    }


    private double distance(int x0, int y0, int x1, int y1){
        return sqrt(pow(abs(x1 - x0), 2) + pow(abs(y1 - y0), 2));
    }


    private void printMap(){
        System.out.println("PRINT MAP");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j)
                builder.append("(" + hexMap.getHex(i, j).isAlive() + " " + hexMap.getHex(i, j).getIMPACT() + ") | ");
            System.out.println(builder.toString());
            builder = new StringBuilder();
        }
    }

    @Override
    public HexMap getMap(){
        return hexMap;
    }

    @Override
    public HexMap armageddon(){
        for (int i = 0; i < hexMap.getHorizontalSize(); ++i)
            for(int j = 0; j < hexMap.getVerticalSize(); ++j)
                hexMap.killHex(i, j);

        return hexMap;
    }

    @Override
    public void setReplaceMode(ToolBarButton mode){
        replaceMode = mode;
    }

    private boolean is(int i){
        return i == 1;
    }

    @Override
    public double getLive_begin(){
        return live_begin;
    }

    @Override
    public double getLive_end(){
        return live_end;
    }

    @Override
    public double getBirth_begin(){
        return birth_begin;
    }

    @Override
    public double getBirth_end(){
        return birth_end;
    }

    @Override
    public double getFst_impact(){
        return fst_impact;
    }

    @Override
    public double getSnd_impact(){
        return snd_impact;
    }

    @Override
    public void setLive_begin(double live_begin){
        if(hexMap != null)
            hexMap.setLive_begin(live_begin);
        this.live_begin = live_begin;
    }

    @Override
    public void setLive_end(double live_end){
        if (hexMap != null)
            hexMap.setLive_end(live_end);
        this.live_end = live_end;
    }

    @Override
    public void setBirth_begin(double birth_begin){
        if (hexMap != null)
            hexMap.setBirth_begin(birth_begin);
        this.birth_begin = birth_begin;
    }

    @Override
    public void setBirth_end(double birth_end){
        if (hexMap != null)
            hexMap.setBirth_end(birth_end);
        this.birth_end = birth_end;
    }

    @Override
    public void setFst_impact(double fst_impact){
        this.fst_impact = fst_impact;
    }

    @Override
    public void setSnd_impact(double snd_impact){
        this.snd_impact = snd_impact;
    }

    @Override
    public void setLength(int len){
        this.len = len;
        recalcCoords();
    }

    @Override
    public int getM (){
        return m;
    }

    @Override
    public int getN(){
        return n;
    }

    @Override
    public int getLength(){
        return len;
    }

    @Override
    public int getThickness(){
        return thickness;
    }

    @Override
    public ToolBarButton getReplaceMode(){
        return replaceMode;
    }

    @Override
    public void setThickness(int thickness){
        this.thickness = thickness;
        hexMap.setThickness(thickness);
    }
    @Override
    public void setM(int m){
        this.m = m;
    }

    @Override
    public void setN(int n){
        this.n = n;
    }

    private void recalcCoords(){
        int toLowLevel = (int)(3 * len / 2);
        int step = (int)(len * sqrt(3));
        gridSection = step;
        this.pad = len;

        hexMap.setLength(len);

        for(int j = pad, l = 0;l < m; j += 2 * toLowLevel, l += 2){
            for (int i = pad, k = 0;k < n; i += step, ++k){
                hexMap.setHexCoords(l, k, i, j);
                hexMap.setHexCoords(l + 1, k,i + step / 2, j + toLowLevel);
            }
        }
    }

    @Override
    public void resizeMap(int m, int n){

        if(m == this.m && n == this.n)
            return;

        LinkedList<Hex> savedStates = new LinkedList<>();

        for (int i = 0; i < hexMap.getHorizontalSize(); ++i)
            for (int j = 0; j < hexMap.getVerticalSize(); ++j)
                if (is(hexMap.getHex(i, j).isAlive()))
                    savedStates.push(hexMap.getHex(i, j));

        buildMap(len, m, n, thickness);

        Hex hex;
        while (!savedStates.isEmpty()) {
            hex = savedStates.pop();
            hexMap.getHex(hex.getMapX(), hex.getMapY()).setAlive();
        }
    }

    public LinkedList <Point> getAliveHexList(){
        LinkedList<Point> points = new LinkedList<>();
        for (int i = 0; i < hexMap.getHorizontalSize(); ++i)
            for (int j = 0; j < hexMap.getVerticalSize(); ++j)
                if(is(hexMap.getHex(i, j).isAlive()))
                    points.add(new Point(i, j));

        return points;
    }
}