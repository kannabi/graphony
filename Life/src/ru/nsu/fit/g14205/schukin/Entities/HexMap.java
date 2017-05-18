package ru.nsu.fit.g14205.schukin.Entities;
import static ru.nsu.fit.g14205.schukin.Entities.Const.*;

/**
 * Created by kannabi on 05.03.2017.
 */
public class HexMap {
    private int m, n;
    private Hex [][] map;
    private int len;
    private int thickness;

    private double live_begin;
    private double live_end;
    private double birth_begin;
    private double birth_end;

    public HexMap(int m, int n){
        this.m = m + PAD * 2 + 1;
        this.n = n + PAD * 2 + 1;
        map = new Hex[this.m][this.n];
        initMap();
    }

    private void initMap(){
        for(int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                map[i][j] = new Hex(0, 0, 0, 0);
    }

    public void setHex(int i, int j, int x, int y){
        map[i + PAD][j + PAD] = new Hex(i, j, x, y);
    }

    public Hex getHex(int i, int j){
        return map[i + PAD][j + PAD];
    }

    public int hexAliveState(int i, int j){
        return map[i + PAD][j + PAD].isAlive();
    }

    public void setHexAlive(int i, int j){
        map[i + PAD][j + PAD].setAlive();
    }

    public void killHex(int i, int j){
        map[i + PAD][j + PAD].die();
    }

    public int getVerticalSize(){
        return m - PAD * 2 - 1;
    }

    public int getHorizontalSize(){
        return n - PAD * 2 - 1;
    }

    public void refreshAliveState(){
        double impact;
        for (int i = PAD; i < m - PAD; ++i)
            for (int j = PAD; j < n - PAD; ++j) {
                impact = map[i][j].getIMPACT();
                if (impact < live_begin || impact > live_end) {
                    map[i][j].die();
                    continue;
                }
                if (birth_begin <= impact && birth_end >= impact)
                    map[i][j].setAlive();
            }
    }

    public void setLive_begin(double live_begin){
        this.live_begin = live_begin;
    }

    public void setLive_end(double live_end){
        this.live_end = live_end;
    }

    public void setBirth_begin(double birth_begin){
        this.birth_begin = birth_begin;
    }

    public void setBirth_end(double birth_end){
        this.birth_end = birth_end;
    }

    public void setHexCoords(int i, int j, int x, int y){
        map[i + PAD][j + PAD].setCoords(x, y);
    }

    public void setLength(int len){
        this.len = len;
    }

    public int getLength(){
        return len;
    }

    public void setThickness(int thickness){
        this.thickness = thickness;
    }

    public int getThickness(){
        return thickness;
    }
}