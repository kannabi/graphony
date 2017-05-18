package ru.nsu.fit.g14205.schukin.Model;

import ru.nsu.fit.g14205.schukin.Interfaces.WireframeModelInterface;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframePresenterInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by kannabi on 16.04.2017.
 */
public class WireframeModel implements WireframeModelInterface {
    private WireframePresenterInterface presenter;
    private LinkedList<GraphLine> graphLines = new LinkedList<>();

    private double minX = -5, maxX = 5;
    private double minY = -5, maxY = 5;

    private ModelState prevState = new ModelState();

    public WireframeModel(){
        GraphLine line = new GraphLine();
        line.addB_point(new RealPoint(-2.0, 0.0));
        line.addB_point(new RealPoint(-1.0, 2.0));
        line.addB_point(new RealPoint(0.0, 0.0));
        line.addB_point(new RealPoint(1.0, 2.0));
        line.addB_point(new RealPoint(2.0, 0.0));
        graphLines.add(line);
    }

    public void setPresenter(WireframePresenterInterface presenter){
        this.presenter = presenter;
    }

    public WireframePresenterInterface getPresenter(){
        return  presenter;
    }

    public ArrayList<RealPoint> getGeneratixPoints(int graphNumber){
//        graphLines.get(graphNumber).refreshGraphPoints(calcGeneratixPoints(graphLines.get(graphNumber).getB_points()));
        return graphLines.get(graphNumber).getGraphPoints();
    }

    public GraphLine getGraphLine(int graphNum){
        getGeneratixPoints(graphNum);
        return graphLines.get(graphNum);
    }

    public LinkedList<GraphLine> getGraphLines(){
        return (LinkedList<GraphLine>) graphLines.clone();
    }

    public ArrayList<RealPoint> getB_points(int graphNum){
        return graphLines.get(graphNum).getB_points();
    }

    public int getPrecision(int graphNum){
        return graphLines.get(graphNum).getPrecision();
    }

    public void setPresicion(int graphNum, int presicion){
        graphLines.get(0).setPrecision(presicion);
    }

    public void addB_point(int graphNum, RealPoint bPoint){
        graphLines.get(graphNum).addB_point(bPoint);
    }

    public void deleteB_point(int graphNum, double x, double y){
        graphLines.get(graphNum).deleteB_point(x, y);
    }

    public void moveB_point(int graphNum, int i, double x, double y){
        graphLines.get(graphNum).moveB_point(i, x, y);
    }

    public int findB_point(int i, double x, double y){
        return graphLines.get(i).findB_point(x, y);
    }

    public void setGraphColor(int graphNum, Color color){
        graphLines.get(graphNum).setGraphColor(color);
    }

    public Color getGraphColor(int graphNum){
        return graphLines.get(graphNum).getGraphColor();
    }

    public void setSplineColor(int graphNum, Color color){
        graphLines.get(graphNum).setSplineColor(color);
    }

    public Color getSplineColor(int graphNum){
        return graphLines.get(graphNum).getSplineColor();
    }

    public double getMinX(){
        return minX;
    }

    public double getMaxX(){
        return maxX;
    }

    public double getMinY(){
        return minY;
    }

    public double getMaxY(){
        return maxY;
    }

    public void setMinX(double val){
        minX = val;
    }

    public void setMaxX(double val){
        maxX = val;
    }

    public void setMinY(double val){
        minY = val;
    }

    public void setMaxY(double val){
        maxY = val;
    }

    public void initAngles(){
        graphLines.forEach(GraphLine::initAngles);
    }

    public void initAngles(int graphNum){
        graphLines.get(graphNum).initAngles();
    }

    public void addNewGraph(){
        GraphLine newLine = new GraphLine();
        int lineColorRGB = (int)(Math.random() * 0xFFFFFF);
        newLine.setGraphColor(new Color(lineColorRGB));
        newLine.setSplineColor(new Color(0xFFFFFF - lineColorRGB));

        graphLines.add(newLine);
    }

    public int getGraphNumber(){
        return graphLines.size();
    }

    public void deleteGraph(int graphNumber){
        graphLines.remove(graphNumber);
    }

    public void setShowSegmentBorderState(int graphNum, boolean state){
        graphLines.get(graphNum).setShowSegmentBorderState(state);
    }

    public void setWireframeNumber(int graphNumber, int number){
        graphLines.get(graphNumber).setWireframeNumber(number);
    }

    public int getWireframeNumber(int graphNumber){
        return graphLines.get(graphNumber).getWireframeNumber();
    }

    public int getVerticalNumber(int graphNum){
        return graphLines.get(graphNum).getVerticalNumber();
    }

    public void setVerticalNumber(int graphNum, int verticalNumber){
        graphLines.get(graphNum).setVerticalNumber(verticalNumber);
    }

    public double getZb(int graphNum){
        return graphLines.get(graphNum).getZb();
    }

    public void setZb(int graphNum, double zb){
        graphLines.get(graphNum).setZb(zb);
    }

    public double getZf(int graphNum){
        return graphLines.get(graphNum).getZf();
    }

    public void setZf(int graphNum, double zf){
        graphLines.get(graphNum).setZf(zf);
    }

    public ArrayList<ArrayList<RealPoint>> getFigure(int graphNum){
        return graphLines.get(graphNum).getWireframeFigure();
    }

    public void rotateFigure(int graphNum, double x, double y, double z){
        graphLines.get(graphNum).rotate(x, y, z);
    }

    public void rotateFigures(double x, double y, double z){
        graphLines.forEach(o -> o.rotate(x, y, z));
    }

    public void moveStartPoint(int graphNum, double x, double y, double z){
        graphLines.get(graphNum).getStartPoint().setX(x);
        graphLines.get(graphNum).getStartPoint().setY(y);
        graphLines.get(graphNum).getStartPoint().setZ(z);
    }

    public RealPoint3D getStartPoint(int graphNum){
        return graphLines.get(graphNum).getStartPoint();
    }

    public void saveState(){
        LinkedList<GraphLine> cash = new LinkedList<>();

        ArrayList<RealPoint> b_p;
        GraphLine savingLine;
        for (GraphLine line : graphLines) {
            b_p = new ArrayList<>();
            for (RealPoint b_point : line.getB_points())
                b_p.add(new RealPoint(b_point.getX(), b_point.getY()));
            savingLine = new GraphLine(b_p);
            savingLine.setColors(line.getGraphColor(), line.getSplineColor());
            savingLine.setPrecision(line.getPrecision());
            savingLine.setVerticalNumber(line.getVerticalNumber());
            savingLine.setWireframeNumber(savingLine.getWireframeNumber());
            savingLine.setAngleX(line.getAngleX());
            savingLine.setAngleY(line.getAngleY());
            savingLine.setAngleZ(line.getAngleZ());
            cash.add(savingLine);
        }

        prevState.setGraphLines(cash);
        prevState.setMaxX(maxX);
        prevState.setMinX(minX);
        prevState.setMinY(minY);
        prevState.setMaxY(maxY);
    }

    public void restoreState(){
        graphLines.clear();
        graphLines.addAll(prevState.getGraphLines());
        maxX = prevState.getMaxX();
        minX = prevState.getMinX();
        maxY = prevState.getMaxY();
        minY = prevState.getMinY();
    }
}
