package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Model.GraphLine;
import ru.nsu.fit.g14205.schukin.Model.RealPoint;
import ru.nsu.fit.g14205.schukin.Model.RealPoint3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import static java.lang.Math.*;
import static java.lang.Math.pow;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import static ru.nsu.fit.g14205.schukin.Entities.Const.*;

/**
 * Created by kannabi on 18.04.2017.
 */
public class GeneratixGraphWindow extends JPanel {
    private BufferedImage buffer;
    private int cellSize;
    private Point centerCoord;
    private int numXCells, numYCells;
    private boolean isShowSpline = true;
    private int curMoving;
    SettingsWindow parent;

    private static OnMouseClickListener clickListener;
    private static OnMousePressListener pressListener;
    private static OnMouseMoveListener moveListener;

    public interface OnMouseClickListener{
        void onMouseClick(double x, double y);
    }

    public interface OnMousePressListener{
        void onMousePress(double x, double y);
    }

    public interface OnMouseMoveListener{
//        void onMouseDragged(int i, double x, double y, double z);
        void onMouseDragged(int i, double x, double y);
    }

    public void setOnMouseClickListener(OnMouseClickListener listener){
        clickListener = listener;
    }

    public void setOnMousePressListener(OnMousePressListener listener){
        pressListener = listener;
    }

    public void setOnMouseMoveListener(OnMouseMoveListener listener){
        moveListener = listener;
    }

    public GeneratixGraphWindow(SettingsWindow parent){
        this.parent = parent;
        setPreferredSize(new Dimension(DEFAULT_SETTING_PANE_WIDTH, DEFAULT_SETTING_PANE_HEIGHT));
        buffer = new BufferedImage(DEFAULT_SETTING_PANE_WIDTH, DEFAULT_SETTING_PANE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        setMouseMotionListener();
        setMouseListener();
    }

    public void prepareData(GraphLine graphLine){
        buffer = new BufferedImage(DEFAULT_SETTING_PANE_WIDTH, DEFAULT_SETTING_PANE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        drawAxis();

        drawGraph(graphLine);
    }

    private void drawGraph(GraphLine graph){
        Graphics g = buffer.createGraphics();
        ArrayList<RealPoint> graphPoints = graph.getGraphPoints();
        g.setColor(graph.getSplineColor());

        if (isShowSpline) {
            ArrayList<RealPoint> B_points = graph.getB_points();
            if (B_points.size() > 0)
                g.drawOval(toPixelX(B_points.get(0).getX()) - 4, toPixelY(B_points.get(0).getY()) - 4,
                        8, 8);
            for (int i = 1; i < B_points.size(); ++i) {
                g.drawLine(toPixelX(B_points.get(i - 1).getX()), toPixelY(B_points.get(i - 1).getY()),
                        toPixelX(B_points.get(i).getX()), toPixelY(B_points.get(i).getY()));
                g.drawOval(toPixelX(B_points.get(i).getX()) - 4, toPixelY(B_points.get(i).getY()) - 4,
                        8, 8);
            }
        }

        g.setColor(graph.getGraphColor());

        BigDecimal x0;
        BigDecimal x1;
        BigDecimal y0;
        BigDecimal y1;
        for (int i = 0; i < graphPoints.size() - 1; ++i){
            g.drawLine(toPixelX(graphPoints.get(i).getX()), toPixelY(graphPoints.get(i).getY()),
                    toPixelX(graphPoints.get(i + 1).getX()), toPixelY(graphPoints.get(i + 1).getY()));

            if (graph.isShowSegmentBorder()) {
                int prec = 4;
                x0 = new BigDecimal(Double.toString(graphPoints.get(i).getX())).setScale(prec, BigDecimal.ROUND_HALF_UP);
                x1 = new BigDecimal(Double.toString(graphPoints.get(i + 1).getX())).setScale(prec, BigDecimal.ROUND_HALF_UP);
                y0 = new BigDecimal(Double.toString(graphPoints.get(i).getY())).setScale(prec, BigDecimal.ROUND_HALF_UP);
                y1 = new BigDecimal(Double.toString(graphPoints.get(i + 1).getY())).setScale(prec, BigDecimal.ROUND_HALF_UP);
                if (Objects.equals(x0, x1) && Objects.equals(y0, y1)) {
                    g.drawOval(toPixelX(graphPoints.get(i).getX()) - 2, toPixelY(graphPoints.get(i).getY()) - 2, 4, 4);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }

    private RealPoint3D startPoint = new RealPoint3D(0.0, 0.0, 0.0);

    private void setMouseMotionListener(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                moveListener.onMouseDragged(curMoving, toRealX(e.getX()), toRealY(e.getY()));
//                double z;
//                if(isLeftMouseButton(e)) {
//                    moveListener.onMouseDragged(curMoving, startPoint.getX() - toRealX(e.getX()),
//                            startPoint.getY() - toRealY(e.getY()), DONT_CHANGE);
//                    startPoint.setX(toRealX(e.getX()));
//                    startPoint.setY(toRealY(e.getY()));
//                } else if (isRightMouseButton(e)) {
//                    z = toRealX(e.getX()) / toRealY(e.getY());
//                    moveListener.onMouseDragged(curMoving, DONT_CHANGE, DONT_CHANGE,
//                            startPoint.getZ() - z);
//                    startPoint.setZ(z);
//                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });
    }

    private void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickListener.onMouseClick(toRealX(e.getX()), toRealY(e.getY()));
//                clickListener.onMouseClick(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e){
                try {
                    curMoving = parent.getB_pointfromModel(toRealX(e.getX()), toRealY(e.getY()));
                }catch (IndexOutOfBoundsException exception){
                    curMoving = 0;
                }
                startPoint.setX(toRealX(e.getX()));
                startPoint.setY(toRealY(e.getY()));
                startPoint.setZ(toRealX(e.getX()) / toRealY(e.getY()));
            }

            @Override
            public void mouseReleased(MouseEvent e){}
        });
    }

    private int toPixelX(double val){
        int pixel = (int)((double)cellSize / 1000 * ((abs(val)) * 1000));
        return val >= 0 ? pixel + centerCoord.x : centerCoord.x - pixel;
    }

    private int toPixelY(double val){
        int pixel = (int)((double)cellSize / 1000 * ((abs(val)) * 1000));
        return val <= 0 ? pixel + centerCoord.y : centerCoord.y - pixel;
    }

    private double toRealX(int val){
        int fromCenter = val - centerCoord.x;
        return (double) fromCenter / cellSize;
    }

    private double toRealY(int val){
        int fromCenter = centerCoord.y - val;
        return (double) fromCenter / cellSize;
    }

    public void setBordersValue(double minX, double maxX, double minY, double maxY){
        numXCells = (int)(maxX - minX);
        numYCells = (int)(maxY - minY);

        cellSize = min(DEFAULT_SETTING_PANE_WIDTH, DEFAULT_SETTING_PANE_HEIGHT) / min(numXCells, numYCells);

        centerCoord = new Point(abs((int)minX) * cellSize, (int)maxY * cellSize);
        drawAxis();
    }

    void setShowSplineState(boolean state){
        this.isShowSpline = state;
    }

    private void drawAxis(){
        Graphics g = buffer.createGraphics();

        g.drawLine(0, centerCoord.y,
                DEFAULT_SETTING_PANE_WIDTH - 1, centerCoord.y);

        g.drawLine(centerCoord.x, 0,
                centerCoord.x,DEFAULT_SETTING_PANE_HEIGHT - 1);

        for (int i = 0, x = cellSize, y = cellSize; i < 19; ++i, x += cellSize, y += cellSize) {
            g.drawLine(centerCoord.x - 3, y, centerCoord.x + 3, y);
            g.drawLine(x, centerCoord.y - 3, x, centerCoord.y + 3);
        }
    }

    public void draw3D(ArrayList<ArrayList<RealPoint>> figure){
        buffer = new BufferedImage(DEFAULT_SETTING_PANE_WIDTH, DEFAULT_SETTING_PANE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics g = buffer.createGraphics();
        for (ArrayList<RealPoint> line : figure){
            for (int i = 0; i < line.size() - 1; ++i)
                g.drawLine(toPixelX(line.get(i).getX()), toPixelY(line.get(i).getY()),
                        toPixelX(line.get(i + 1).getX()), toPixelY(line.get(i + 1).getY()));
        }
    }
}
