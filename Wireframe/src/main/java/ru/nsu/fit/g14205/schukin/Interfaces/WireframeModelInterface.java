package ru.nsu.fit.g14205.schukin.Interfaces;

import ru.nsu.fit.g14205.schukin.Model.GraphLine;
import ru.nsu.fit.g14205.schukin.Model.RealPoint;
import ru.nsu.fit.g14205.schukin.Model.RealPoint3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by kannabi on 16.04.2017.
 */

public interface WireframeModelInterface {
    void setPresenter(WireframePresenterInterface presenter);

    WireframePresenterInterface getPresenter();

    GraphLine getGraphLine(int graphNum);

    LinkedList<GraphLine> getGraphLines();

    ArrayList<RealPoint> getGeneratixPoints(int graphNumber);

    ArrayList<RealPoint> getB_points(int graphNum);

    int getPrecision(int graphNum);

    void setPresicion(int graphNum, int presicion);

    int getVerticalNumber(int graphNum);

    void setVerticalNumber(int graphNum, int verticalNumber);

    void addB_point(int graphNum, RealPoint bPoint);

    void deleteB_point(int graphNum, double x, double y);

    void moveB_point(int graphNum, int i, double x, double y);

    int findB_point(int i, double x, double y);

    void setGraphColor(int graphNum, Color color);

    Color getGraphColor(int graphNum);

    void setSplineColor(int graphNum, Color color);

    Color getSplineColor(int graphNum);

    double getMinX();

    double getMaxX();

    double getMinY();

    double getMaxY();

    void setMinX(double val);

    void setMaxX(double val);

    void setMinY(double val);

    void setMaxY(double val);

    void initAngles();

    void initAngles(int graphNum);

    void addNewGraph();

    int getGraphNumber();

    void setWireframeNumber(int graphNumber, int number);

    int getWireframeNumber(int graphNumber);

    void deleteGraph(int graphNumber);

    void setShowSegmentBorderState(int graphNum, boolean state);

    ArrayList<ArrayList<RealPoint>> getFigure(int graphNum);

    void rotateFigure(int graphNum, double x, double y, double z);

    void rotateFigures(double x, double y, double z);

    void saveState();

    void restoreState();

    void moveStartPoint(int graphNum, double x, double y, double z);

    RealPoint3D getStartPoint(int graphNum);

    double getZb(int graphNum);

    void setZb(int graphNum, double zb);

    double getZf(int graphNum);

    void setZf(int graphNum, double zf);
}
