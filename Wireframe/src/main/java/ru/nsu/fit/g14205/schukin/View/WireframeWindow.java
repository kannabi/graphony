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
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.*;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import static ru.nsu.fit.g14205.schukin.Entities.Const.*;

/**
 * Created by kannabi on 18.04.2017.
 */
public class WireframeWindow extends JPanel {
    private BufferedImage buffer;

    private int cellSize;
    private Point centerCoord;

    private static OnMouseClickListener clickListener;
    private static OnMouseMoveListener moveListener;
    private int padding = 50;

    public interface OnMouseClickListener{
        void onMouseClick(int x, int y);
    }
    public interface OnMouseMoveListener{
        void onMouseDragged(double x, double y, double z);
    }

    public void setOnMouseClickListener(OnMouseClickListener listener){
        clickListener = listener;
    }

    public void setOnMouseMoveListener(OnMouseMoveListener listener){
        moveListener = listener;
    }

    public WireframeWindow(){
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        buffer = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        setBordersValue();
        setMouseMotionListener();
        setMouseListener();
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
                double z;
                if(isLeftMouseButton(e)) {
                    moveListener.onMouseDragged(startPoint.getX() - toRealX(e.getX()),
                                                startPoint.getY() - toRealY(e.getY()), DONT_CHANGE);
//                    startPoint.setX(toRealX(e.getX()));
//                    startPoint.setY(toRealY(e.getY()));
                } else if (isRightMouseButton(e)) {
                    z = toRealX(e.getX()) / toRealY(e.getY());
//                    z = ((startPoint.getX()) - (toRealX(e.getX()))) / ((startPoint.getY()) - (toRealY(e.getY())));
                    moveListener.onMouseDragged(DONT_CHANGE, DONT_CHANGE,
                                                startPoint.getZ() - z);
                    startPoint.setZ(z);
                }
                startPoint.setX(toRealX(e.getX()));
                startPoint.setY(toRealY(e.getY()));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    private void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e){
                startPoint.setX(toRealX(e.getX()));
                startPoint.setY(toRealY(e.getY()));
                startPoint.setZ(toRealX(e.getX()) / toRealY(e.getY()));
            }

            @Override
            public void mouseReleased(MouseEvent e){
            }
        });
    }

    public void drawFigure(LinkedList<GraphLine> lines){
        buffer = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffer.createGraphics();
        drawBorder(g);
        ArrayList<ArrayList<RealPoint>> figure;

        for (GraphLine graphLine : lines) {
            figure = graphLine.getWireframeFigure();
            g.setColor(graphLine.getGraphColor());
            ArrayList<RealPoint> line;
//            for (ArrayList<RealPoint> line : figure) {
            int k = 0;
            for (; k < figure.size() - 3; ++k) {
                line = figure.get(k);
                for (int i = 0; i < line.size() - 1; ++i)
                    g.drawLine(toPixelX(line.get(i).getX()), toPixelY(line.get(i).getY()),
                            toPixelX(line.get(i + 1).getX()), toPixelY(line.get(i + 1).getY()));
            }

            if (!figure.isEmpty()) {
                Color[] axisColor = {Color.blue, Color.red, Color.green};
                for (Color c : axisColor) {
                    g.setColor(c);
                    line = figure.get(k++);
                    for (int i = 0; i < line.size() - 1; ++i)
                        g.drawLine(toPixelX(line.get(i).getX()), toPixelY(line.get(i).getY()),
                                toPixelX(line.get(i + 1).getX()), toPixelY(line.get(i + 1).getY()));
                }
            }
        }
    }

    private void drawBorder(Graphics g){
        g.drawLine(padding, padding, DEFAULT_WIDTH - padding, padding);
        g.drawLine(padding, padding, padding, DEFAULT_HEIGHT - padding);
        g.drawLine(padding, DEFAULT_HEIGHT - padding, DEFAULT_WIDTH - padding, DEFAULT_HEIGHT - padding);
        g.drawLine(DEFAULT_WIDTH - padding, padding, DEFAULT_WIDTH - padding, DEFAULT_HEIGHT - padding);
    }

    private int toPixelX(double val){
        int pixel = (int)((double)cellSize / 1000 * ((abs(val)) * 1000));
        return val >= 0 ? pixel + centerCoord.x: centerCoord.x - pixel;
    }

    private int toPixelY(double val){
        int pixel = (int)((double)cellSize / 1000 * ((abs(val)) * 1000));
        return val <= 0 ? pixel + centerCoord.y: centerCoord.y - pixel;
    }

    private double toRealX(int val){
        int fromCenter = val - centerCoord.x;
        return (double) fromCenter / cellSize;
    }

    private double toRealY(int val){
        int fromCenter = centerCoord.y - val;
        return (double) fromCenter / cellSize;
    }

    private void setBordersValue(){
        cellSize = min(DEFAULT_WIDTH - padding, DEFAULT_HEIGHT - padding) / 2;

//        centerCoord = new Point(abs((int)minX) * cellSize, (int)maxY * cellSize);
        centerCoord = new Point(cellSize + padding / 2, cellSize + padding / 2);
    }
}
