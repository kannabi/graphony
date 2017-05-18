package ru.nsu.ccfit.schukin.IsolineView;

import ru.nsu.ccfit.schukin.Entities.ColorMap;
import ru.nsu.ccfit.schukin.Entities.FunctionData;
import ru.nsu.ccfit.schukin.Interfaces.IsolineModelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Math.min;
import static ru.nsu.ccfit.schukin.Entities.Const.DEFAULT_HEIGHT;
import static ru.nsu.ccfit.schukin.Entities.Const.DEFAULT_WIDTH;
import static ru.nsu.ccfit.schukin.Entities.Const.EPS;

/**
 * Created by kannabi on 03.04.2017.
 */
public class GraphWindow extends JPanel {
    private BufferedImage buffer;
    private int cellSize;
    private FunctionData functionData;
    ColorMap colorMap;
    private LinkedList<Double> isolines = new LinkedList<>();
    private boolean isDrawGrid = false;
    private boolean isDrawEntryPoints = false;

    private static OnMouseClickListener clickListener;
    private static OnMouseMoveListener moveListener;

    public interface OnMouseClickListener{
        void onMouseClick(int x, int y);
    }
    public interface OnMouseMoveListener{
        void onMouseMove(double x, double y, double functionValue);
    }

    public void setOnMouseClickListener(OnMouseClickListener listener){
        clickListener = listener;
    }

    public void setOnMouseMoveListener(OnMouseMoveListener listener){
        moveListener = listener;
    }

    public GraphWindow(){
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        buffer = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        setMouseMotionListener();
        setMouseListener();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Double i : isolines)
            drawIsoline(i);
        g.drawImage(buffer, 0, 0, null);
    }

    public void prepareGraph(FunctionData data, IsolineModelInterface model){
        this.functionData = data;
        int minEdge = min(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        int minAxis = min(data.getHighBorderX(), data.getHighBorderY());
        cellSize = minEdge / minAxis;

        this.colorMap = model.getColorMap();

        spanGraph(colorMap);

        try {
            for (int k = 0; k < colorMap.colorNumber() - 1; ++k)
                drawIsoline(colorMap.getCellKey(k));
        } catch (IOException e){
            e.printStackTrace();
        }

        if(isDrawGrid)
            drawGrid();
    }

    private void prepareGraph(){
        int minEdge = min(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        int minAxis = min(functionData.getHighBorderX(), functionData.getHighBorderY());
        cellSize = minEdge / minAxis;

        spanGraph(colorMap);

        try {
            for (int k = 0; k < colorMap.colorNumber() - 1; ++k)
                drawIsoline(colorMap.getCellKey(k));
        } catch (IOException e){
            e.printStackTrace();
        }

        if(isDrawGrid)
            drawGrid();
    }

    private void spanCell(Color color, int x, int y){
        for (int i = x; i < x + cellSize; ++i)
            for (int j = y; j < y + cellSize; ++j)
                buffer.setRGB(i, j, color.getRGB());
    }

    private void setMouseMotionListener(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                isolines.pop();
                prepareGraph();
                isolines.push(functionData.getPixelFunctionValue(e.getX(), DEFAULT_HEIGHT - e.getY(), DEFAULT_HEIGHT, DEFAULT_WIDTH));
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int cellX = e.getX() / cellSize;
                int cellY = (DEFAULT_HEIGHT - e.getY()) / cellSize;
                double x = functionData.getValueX(cellX);
                double y = functionData.getValueY(cellY);
                double funcVal = functionData.get(cellX, cellY);
                moveListener.onMouseMove(x, y, funcVal);
            }
        });
    }

    private void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                isolines.push(functionData.getPixelFunctionValue(e.getX(), DEFAULT_HEIGHT - e.getY(), DEFAULT_HEIGHT, DEFAULT_WIDTH));
                System.out.println(functionData.getPixelFunctionValue(e.getX(), DEFAULT_HEIGHT - e.getY(), DEFAULT_HEIGHT, DEFAULT_WIDTH));
                System.out.println("-----------" + isolines.size());
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e){
                isolines.push(functionData.getPixelFunctionValue(e.getX(), DEFAULT_HEIGHT - e.getY(), DEFAULT_HEIGHT, DEFAULT_WIDTH));
            }

            @Override
            public void mouseReleased(MouseEvent e){
                isolines.push(functionData.getPixelFunctionValue(e.getX(), DEFAULT_HEIGHT - e.getY(), DEFAULT_HEIGHT, DEFAULT_WIDTH));
            }
        });
    }

    private void drawIsoline(double z){
        for (int i = 0; i < DEFAULT_WIDTH; i += cellSize)
            for (int j = 0; j < DEFAULT_HEIGHT; j += cellSize)
                procCell(i, j, i + cellSize, j + cellSize, z);
    }

    /*(x1, y1) -- левый нижний
    * (х2, у2) -- правый верхний
    * */
    private void procCell(int x1, int y1, int x2, int y2, double z) {
        LinkedList<Point> isoPoints = new LinkedList<>();

        isoPoints.addAll(procCellEdge(x1, y1, x2, y1, z));
        isoPoints.addAll(procCellEdge(x1, y1, x1, y2, z));
        isoPoints.addAll(procCellEdge(x2, y1, x2, y2, z));
        isoPoints.addAll(procCellEdge(x1, y2, x2, y2, z));

        Graphics g = buffer.createGraphics();
        g.setColor(Color.white);
        int size = isoPoints.size();
        if (size == 2) {
            g.drawLine(
                    isoPoints.get(0).x,
                    isoPoints.get(0).y,
                    isoPoints.get(1).x,
                    isoPoints.get(1).y
            );
//            return;
        }
        if (size == 3) {
            procCell(x1, y1, x2, y2, z - EPS);
            procCell(x1, y1, x2, y2, z + EPS);
//            return;
        }

        if (size == 4) {
            if ((functionData.get(x1, y2) >= z && functionData.get(x1 + x2 / 2, y1 + y2 / 2) >= z) ||
                    (functionData.get(x1, y2) <= z && (functionData.get(x1 + x2 / 2, y1 + y2 / 2) <= z))) {
                g.drawLine(
                        isoPoints.get(0).x,
                        isoPoints.get(0).y,
                        isoPoints.get(1).x,
                        isoPoints.get(1).y
                );
                g.drawLine(
                        isoPoints.get(2).x,
                        isoPoints.get(2).y,
                        isoPoints.get(3).x,
                        isoPoints.get(3).y
                );
            } else {
                g.drawLine(
                        isoPoints.get(0).x,
                        isoPoints.get(0).y,
                        isoPoints.get(3).x,
                        isoPoints.get(3).y
                );
                g.drawLine(
                        isoPoints.get(1).x,
                        isoPoints.get(1).y,
                        isoPoints.get(2).x,
                        isoPoints.get(2).y
                );
            }
        }

        if (isDrawEntryPoints)
            for (Point point : isoPoints)
                g.drawOval((int)point.getX(), (int)point.getY(), 4, 4);
    }

    private LinkedList<Point> procCellEdge(int x1, int y1, int x2, int y2, double z){
        LinkedList <Point> points = new LinkedList<>();

        double funcVal1 = functionData.get(x1 / cellSize, y1 / cellSize);
        double funcVal2 = functionData.get(x2 / cellSize, y2 / cellSize);

        if ((z < funcVal1 && z < funcVal2) || (z > funcVal1 && z > funcVal2)) {
            return points;
        }

        points.add(getIsoPoint(x1, y1, x2, y2, funcVal1, funcVal2, z));
        return points;
    }

    private Point getIsoPoint(int x1, int y1, int x2, int y2, double val1, double val2, double z) {
        int x;
        int y;

        if (val2 > val1) {
            x = (int) (x1 + Math.abs((double)x1 - x2) * (z - val1) / (val2 - val1));
            y = (int) (y1 + Math.abs((double)y1 - y2) * (z - val1) / (val2 - val1));
        } else {
            x = (int) (x2 - Math.abs((double)x1 - x2) * (z - val2) / (val1 - val2));
            y = (int) (y2 - Math.abs((double)y1 - y2) * (z - val2) / (val1 - val2));
        }

        return new Point(x, DEFAULT_HEIGHT - y);
    }

    private void drawGrid(){
        for (int i = 0; i < DEFAULT_WIDTH; i +=cellSize)
            for (int j = 0; j < DEFAULT_HEIGHT; ++j) {
                buffer.setRGB(i, j, 0xFF00FF);
                buffer.setRGB(j, i, 0xFF00FF);
            }
    }

    private void spanGraph(ColorMap colorMap){
        for (int i = 0; i < DEFAULT_WIDTH; ++i)
            for (int j = 1; j < DEFAULT_HEIGHT; ++j) {
                buffer.setRGB(i, DEFAULT_HEIGHT - j,
                        colorMap.get(
                                functionData.getPixelFunctionValue(i,
                                        j,
                                        DEFAULT_HEIGHT,
                                        DEFAULT_WIDTH)).getRGB());
            }

    }

    public void switchDrawGridState(){
        isDrawGrid = !isDrawGrid;
    }

    public void switchDrawEntryPoints(){
        isDrawEntryPoints = !isDrawEntryPoints;
    }
}
