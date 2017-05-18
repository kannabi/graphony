package ru.nsu.fit.g14205.schukin.Interfaces;

import ru.nsu.fit.g14205.schukin.Entities.HexMap;
import ru.nsu.fit.g14205.schukin.Entities.Point;
import ru.nsu.fit.g14205.schukin.Entities.ToolBarButton;

import java.util.LinkedList;

/**
 * Created by kannabi on 06.03.2017.
 */

public interface LifeModelInterface {
    HexMap buildMap(int len, int m, int n, int thickness);

    HexMap refreshMap();

    HexMap addAliveHex(int x, int y);

    void addInitsHexes(LinkedList<Point> points);

    HexMap armageddon();

    void setReplaceMode(ToolBarButton mode);

    HexMap getMap();

    double getLive_begin();

    double getLive_end();

    double getBirth_begin();

    double getBirth_end();

    double getFst_impact();

    double getSnd_impact();

    int getM ();

    int getN();

    int getLength();

    int getThickness();

    ToolBarButton getReplaceMode();

    void setLive_begin(double live_begin);

    void setLive_end(double live_end);

    void setBirth_begin(double birth_begin);

    void setBirth_end(double birth_end);

    void setFst_impact(double fst_impact);

    void setSnd_impact(double snd_impact);

    void setLength(int len);

    void setThickness(int thickness);

    void setM(int m);

    void setN(int n);

    void resizeMap(int m, int n);
}