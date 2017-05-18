package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Entities.Hex;
import ru.nsu.fit.g14205.schukin.Entities.HexMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import static java.lang.Math.sqrt;

import static ru.nsu.fit.g14205.schukin.Entities.Const.*;
import static ru.nsu.fit.g14205.schukin.View.DrawingTools.*;

/**
 * Created by kannabi on 06/03/2017.
 */

public class HexMapComponent extends JPanel{
    private int width;
    private int height;
    private int color = BORDER_COLOR;
    private int len;
    private int pad = len;

    private boolean showImpact = false;

    HexMap map;

    private boolean pressed = false;

    private BufferedImage buffer;

    private static OnMouseClickListener listener;

    public interface OnMouseClickListener{
        void onButtonClick(int x, int y);
    }

    public void setOnMouseClickListener(OnMouseClickListener listener){
        this.listener = listener;
    }

    public HexMapComponent() {
        setOpaque(true);

        setMouseMotionListener();
        setMouseListener();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
        if (showImpact)
            drawImpactValues(g, map);
    }

    public void drawMap(HexMap hexMap){
        int x, y;
        int m = hexMap.getHorizontalSize();
        int n = hexMap.getVerticalSize();
        map = hexMap;
        int thickness = hexMap.getThickness();
        len = hexMap.getLength();
        width = (int)((map.getHorizontalSize() + 1) * len * (sqrt(3)) + PAD);
        height = (int)((map.getVerticalSize()) * len * 2 + PAD);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));

        for (int i = 0; i < buffer.getWidth(); ++i)
            for (int j = 0; j < buffer.getHeight(); ++j)
                buffer.setRGB(i, j, BACKGROUND_MAP_COLOR);

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                drawHex(hexMap.getHex(i, j), len, buffer, color, thickness, i, j);
                if (is(hexMap.getHex(i, j).isAlive())) {
                    x = hexMap.getHex(i, j).getCoorX();
                    y = hexMap.getHex(i, j).getCoorY();
                    fillSpan(x, y, buffer, buffer.getRGB(x, y));
                }
            }
        }
    }

    private boolean is(int i){
        return i == 1;
    }

    private void setMouseMotionListener(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (pressed)
                    listener.onButtonClick(e.getX(), e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });
    }

    private void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!compareColors(buffer.getRGB(e.getX(), e.getY()), BORDER_COLOR))
//                if (new Color(buffer.getRGB(e.getX(), e.getY())) != new Color(BORDER_COLOR))
                    listener.onButtonClick(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e){
                pressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e){
                pressed = false;
            }
        });
    }

    private void drawImpactValues(Graphics g, HexMap map){
        Font font = new Font("Helvetica", Font.PLAIN, 18);

        g.setFont(font);
        g.setColor(Color.green);

        for(int i = 0; i < map.getVerticalSize(); ++i)
            for(int j = 0; j < map.getHorizontalSize(); ++j) {
                Hex hex = map.getHex(i, j);
                String imp = new DecimalFormat("#.#").format(hex.getIMPACT());
                g.drawString(imp, hex.getCoorX() - 10, hex.getCoorY() + 5);
            }
    }

    public void changeShowingImpact(){
        showImpact = !showImpact;
    }

    private boolean compareColors(int color0, int color1){
        return ((color0 & 0xff) == (color1 & 0xff) &&
                ((color0 & 0xff00) >> 8) == ((color1 & 0xff00) >> 8) &&
                ((color0 & 0xff0000) >> 16) == ((color0 & 0xff0000) >> 16));
    }
}
