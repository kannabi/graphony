package ru.nsu.fit.g14205.schukin.Model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static ru.nsu.fit.g14205.schukin.Entities.Const.DONT_CHANGE;
import static ru.nsu.fit.g14205.schukin.Model.AlgebraTools.*;

/**
 * Created by kannabi on 23.04.2017.
 */

public class GraphLine {
    private ArrayList<RealPoint> b_points = new ArrayList<>();

    @Getter
    private RealPoint3D startPoint = new RealPoint3D(0, 0, 0);

    @Getter @Setter
    private Color graphColor = new Color(0xFFFFFF);
    @Getter @Setter
    private Color splineColor = new Color(0xFF00FF);
    @Getter @Setter
    private int precision = 100;
//    private int precision = 2;
    private boolean showSegmentBorder = false;
    @Setter @Getter
    private int wireframeNumber = 10;
    private int verticalNumber = 7;

    private RealPoint3D pointView = new RealPoint3D(0.0, 0.0, 5.0);
    private RealPoint3D pointEye = new RealPoint3D(0.0, 0.0, 0.5);
//    private RealPoint3D pointEye = new RealPoint3D(-0.5, 0.0, 0.0);
    private RealPoint3D pointRef = new RealPoint3D(0.0, 0.0, -0.5);
//    private RealPoint3D pointRef = new RealPoint3D(0.5, 0.0, 0.0);
    private RealPoint3D vUp = new RealPoint3D(0.0, 1.0, 0.0);

    @Getter @Setter
    private double sw = 5.0, sh = 5.0;
    @Getter @Setter
    private double zb = 20, zf = 2;
    private double z_center = 0;
    @Setter @Getter
    private double angleX = 0, angleY = 0, angleZ = 0;

    public GraphLine(ArrayList<RealPoint> b_points){
        this.b_points.addAll(b_points);
    }

    public GraphLine(){}

    public void initAngles(){
        angleX = angleY = angleZ = 0;
    }

    public ArrayList<RealPoint> getGraphPoints(){
        return calcGeneratixPoints(b_points);
    }

    public ArrayList<RealPoint> getB_points(){
        return (ArrayList<RealPoint>) b_points.clone();
    }

    public void addB_point(RealPoint point){
        b_points.add(point);
    }

    public void deleteB_point(double x, double y){
        b_points.remove(findB_point(x, y));
    }

    public int findB_point(double x, double y){
        int k = 0;
        double rangeX = abs(b_points.get(k).getX() - x);
        double rangeY = abs(b_points.get(k).getY() - y);
        for (int i = 1; i < b_points.size(); ++i){
            if (abs(b_points.get(i).getX() - x) <= rangeX && abs(b_points.get(i).getY() - y) <= rangeY){
                k = i;
                rangeX = abs(b_points.get(i).getX() - x);
                rangeY = abs(b_points.get(i).getY() - y);
            }
        }
        return k;
    }

    public void moveB_point(int i, double x, double y){
        b_points.get(i).setX(x);
        b_points.get(i).setY(y);
    }

    public void setColors(Color graphColor, Color splineColor){
        this.graphColor = graphColor;
        this.splineColor = splineColor;
    }

    public void setShowSegmentBorderState(boolean state){
        showSegmentBorder = state;
    }

    public boolean isShowSegmentBorder(){
        return showSegmentBorder;
    }

    //Высчитываем точки, по которым будем рисовать образующую
    private ArrayList<RealPoint> calcGeneratixPoints(List<RealPoint> b_points){
        double [][] M = {{-1.0 / 6, 3.0 / 6, -3.0 / 6, 1.0 / 6},
                            {3.0 / 6, -6.0 / 6, 3.0 / 6, 0},
                            {-3.0 / 6, 0, 3.0 / 6, 0},
                            {1.0 / 6, 4.0 / 6, 1.0 / 6, 0}};
        double[][] T = new double[1][4];
        double[][] BX = new double[4][1];
        double[][] BY = new double[4][1];
        double X, Y;
        double[] t = new double[precision + 1];
        double[][] buf;
        ArrayList<RealPoint> nodes = new ArrayList<>();
        t[0] = 0;
        for (int i = 1; i <= precision; ++i)
            t[i] += t[i - 1] + (1.0 / precision);

        for (int k = 1; k < b_points.size() - 2; ++k) {
            //Инициализиурем матрицу G из уравнения xi = T*M*G
            BX[0][0] = b_points.get(k - 1).getX(); BY[0][0] = b_points.get(k - 1).getY();
            BX[1][0] = b_points.get(k).getX(); BY[1][0] = b_points.get(k).getY();
            BX[2][0] = b_points.get(k + 1).getX(); BY[2][0] = b_points.get(k + 1).getY();
            BX[3][0] = b_points.get(k + 2).getX(); BY[3][0] = b_points.get(k + 2).getY();

            for (int i = 0; i <= precision; ++i) {
                //Инициализиурем матрицу T из уравнения xi = T*M*G
                T[0][0] = pow(t[i], 3);
                T[0][1] = pow(t[i], 2);
                T[0][2] = t[i];
                T[0][3] = 1;
                try {
                    buf = multiplicateMatrix(T, M);
                    X = multiplicateMatrix(buf, BX)[0][0];
                    Y = multiplicateMatrix(buf, BY)[0][0];
                    nodes.add(new RealPoint(X, Y));
                } catch (AlgebraToolsException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }

    private void rotateX(double degree, ArrayList<RealPoint3D> line){
        degree = Math.toRadians(degree);

        double [][] mx = {{1, 0, 0, 0},
                        {0, cos(degree), (-1)* sin(degree), 0},
                        {0, sin(degree), cos(degree), 0},
                        {0, 0, 0, 1}};

        processLine(line, mx);
    }

    private void rotateY(double degree, ArrayList<RealPoint3D> line){
        degree = Math.toRadians(degree);

        double [][] my = {{cos(degree), 0, sin(degree), 0},
                            {0, 1, 0, 0},
                            {(-1)* sin(degree), 0, cos(degree), 0},
                            {0, 0, 0, 1}};

        processLine(line, my);
    }

    private void rotateZ(double degree, ArrayList<RealPoint3D> line){
//        degree = Math.toRadians(degree);

        double [][] mz = {{cos(degree), (-1)* sin(degree), 0, 0},
                            {sin(degree), cos(degree), 0, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 1}};

        processLine(line, mz);
    }

    private double[][] getMoveMatrix(double x, double y, double z){
        return new double[][]{{1.0, 0.0, 0.0, x},
                        {0.0, 1.0, 0.0, y},
                        {0.0, 0.0, 1.0, z},
                        {0.0, 0.0, 0.0, 1.0}};
    }

    private double[][] getCameraCoordMatrix(){
        Vector<Double> w = new Vector<>();
        Vector<Double> buf = new Vector<>();
        buf.add(pointEye.getX() - pointRef.getX());
        buf.add(pointEye.getY() - pointRef.getY());
        buf.add(pointEye.getZ() - pointRef.getZ());
        double norm = getVectorNorm(buf);
        w.add(buf.get(0) / norm);
        w.add(buf.get(1) / norm);
        w.add(buf.get(2) / norm);


        Vector<Double> rr = multiplicateVector(toVector(vUp), w);


        Vector<Double> u = new Vector<>();
        norm = getVectorNorm(rr);
        u.add(rr.get(0) / norm);
        u.add(rr.get(1) / norm);
        u.add(rr.get(2) / norm);

        Vector<Double> v = multiplicateVector(w, u);

        return new double[][]{{u.get(0), u.get(1), u.get(2), 0.0},
                                {v.get(0), v.get(1), v.get(2), 0.0},
                                {w.get(0), w.get(1), w.get(2), 0.0},
                                {0.0, 0.0, 0.0, 1.0}};
    }

    private void processLine(ArrayList<RealPoint3D> line, double[][] m){
        double[][] res= new double[4][1];
        double[][] p = new double[4][1];
        for (RealPoint3D curPoint : line) {
            p[0][0] = curPoint.getX(); p[1][0] = curPoint.getY(); p[2][0] = curPoint.getZ(); p[3][0] = 1;
            try {
                res = multiplicateMatrix(m, p);
            } catch (AlgebraToolsException e) {
                e.printStackTrace();
            }
            curPoint.setX(res[0][0]);
            curPoint.setY(res[1][0]);
            curPoint.setZ(res[2][0]);
        }
    }

    private RealPoint rotate2D(double angle, double x, double y){
        angle= Math.toRadians(angle);
        double[][] m = {{cos(angle), (-1)* sin(angle)},
                        {sin(angle), cos(angle)}};

        double[][] point = {{x}, {y}};
        double [][] res = {{0}, {0}};

        try {
            res = multiplicateMatrix(m, point);
        } catch (AlgebraToolsException e){
            e.printStackTrace();
        }

        return new RealPoint(res[0][0], res[1][0]);
    }

    private void toCube(RealPoint3D point){
        point.setX(point.getX() * 2 * zf / (sw * point.getZ()));
        point.setY(point.getY() * 2 * zf / (sh * point.getZ()));
        point.setZ(((point.getZ() * zb / (zb - zf)) - (zb * zf / (zb - zf))) / point.getZ());
    }

    public ArrayList<ArrayList<RealPoint>> getWireframeFigure(){
        ArrayList<ArrayList<RealPoint3D>> wireframes = getWireframes();
        ArrayList<ArrayList<RealPoint>> figure = new ArrayList<>();
        double[][] moveViewMatrix = getMoveMatrix(pointView.getX(), pointView.getY(), pointView.getZ());
        double[][] moveMatrix = getMoveMatrix(startPoint.getX(), startPoint.getY(), startPoint.getZ());
//        double[][] moveBackMatrix = getMoveMatrix(startPoint.getX(), startPoint.getY(), startPoint.getZ());

        ArrayList<RealPoint> line2D;
        for(ArrayList<RealPoint3D> line : wireframes) {
            line2D = new ArrayList<>();
            processLine(line, moveMatrix);
            rotateY(angleX, line);
            rotateX(angleY, line);
            rotateZ(angleZ, line);
            processLine(line, getCameraCoordMatrix());
            processLine(line, moveViewMatrix);
            line.parallelStream().forEach(this::toCube);
            cutLine(line);
            line2D.addAll(line.parallelStream().map(RealPoint::to2D).collect(Collectors.toList()));
            figure.add(line2D);
        }
        return figure;
    }

    private void cutLine(ArrayList<RealPoint3D> line){
        List<RealPoint3D> new_line = line.parallelStream().
                                        filter(o -> abs(o.getX()) < (0.95) && abs(o.getY()) < 0.95 &&
                                                o.getZ() < 0.95 && o.getZ() > 0.0).
                                        collect(Collectors.toList());
        line.clear();
        line.addAll(new_line);
    }

    private ArrayList<ArrayList<RealPoint3D>> getWireframes(){
        ArrayList<ArrayList<RealPoint3D>> wireframes = new ArrayList<>();
        if (b_points.size() < 4)
            return wireframes;
        double angle = 360.0 / wireframeNumber;

        ArrayList<RealPoint> line2D = calcGeneratixPoints(b_points);

        List<RealPoint3D> firstLine = line2D.stream().map(o -> new RealPoint3D(o.getX(), o.getY(), z_center)).collect(Collectors.toList());
        wireframes.add((ArrayList<RealPoint3D>) firstLine);

        ArrayList<RealPoint3D> bufLine;
        for (int i = 1; i < wireframeNumber; ++i){
            bufLine = copy(wireframes.get(i - 1));
            rotateX(angle, bufLine);
            wireframes.add(copy(bufLine));
        }

        addVerticalLines(wireframes);

        addAxis(wireframes);

        return wireframes;
    }

    private void addAxis(ArrayList<ArrayList<RealPoint3D>> wireframes){
        ArrayList <RealPoint3D> lineX = new ArrayList<>();
        ArrayList <RealPoint3D> lineY = new ArrayList<>();
        ArrayList <RealPoint3D> lineZ = new ArrayList<>();
        double len = 2.0;

        double start = -len;
        double step = len * 2 / precision;
        for (int i = 0; i < precision; ++i, start += step){
            lineX.add(new RealPoint3D(start, 0.0, 0.0));
            lineY.add(new RealPoint3D(0.0, start, 0.0));
            lineZ.add(new RealPoint3D(0.0, 0.0, start));
        }
        wireframes.add(lineX);
        wireframes.add(lineY);
        wireframes.add(lineZ);
    }

    private void addVerticalLines(ArrayList<ArrayList<RealPoint3D>> wireframes){
        int size = wireframes.get(0).size();
        double step = (wireframes.get(0).get(size - 1).getX() - wireframes.get(0).get(0).getX()) / verticalNumber;
        double x = wireframes.get(0).get(0).getX();
        double angleStep = 360 / (verticalNumber * (precision / sqrt(precision)));
        RealPoint buf;
        int pos;
        int linePos;
        for (int i = 0; i < verticalNumber + 1; ++i){
            linePos = wireframeNumber + i;
            wireframes.add(new ArrayList<>());
            pos = findPointOnLine(x, wireframes.get(0));
            wireframes.get(linePos).add(new RealPoint3D(x,
                    wireframes.get(0).get(pos).getY(),
                    wireframes.get(0).get(pos).getZ()));
            for (int k = 1; k < (verticalNumber + 1) * sqrt(precision); ++k){
                buf = rotate2D(angleStep, wireframes.get(linePos).get(k - 1).getY(), wireframes.get(linePos).get(k - 1).getZ());
                wireframes.get(linePos).add(new RealPoint3D(x, buf.getX(), buf.getY()));
            }
            x += step;
        }
    }

    public void rotate(double x, double y, double z){
        if (!(x == DONT_CHANGE && y == DONT_CHANGE)) {
            this.angleX = this.angleX - 3.6 * (x) / (sw / zf / 100);
            this.angleY = this.angleY + 3.6 * (y) / (sh / zf / 100);
        } else
            this.angleZ = this.angleZ + atan(z);

    }

    private ArrayList<RealPoint3D> copy(ArrayList<RealPoint3D> l){
        ArrayList<RealPoint3D> cl = new ArrayList<>();
        for (RealPoint3D i : l)
            cl.add(new RealPoint3D(i.getX(), i.getY(), i.getZ()));
        return cl;
    }

    public int getVerticalNumber() {
        return verticalNumber;
    }

    public void setVerticalNumber(int verticalNumber) {
        this.verticalNumber = verticalNumber;
    }

    public int findPointOnLine(double x, ArrayList<RealPoint3D> line){
        double min = abs(x - line.get(0).getX());
        int res_index = 0;

        for (int i = 1; i < line.size(); ++i){
            if (abs(x - line.get(i).getX()) <= min){
                min = x - line.get(i).getX();
                res_index = i;
            }
        }

        return res_index;
    }
}