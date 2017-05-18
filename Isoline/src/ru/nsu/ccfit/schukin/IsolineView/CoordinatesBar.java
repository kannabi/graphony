package ru.nsu.ccfit.schukin.IsolineView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import static ru.nsu.ccfit.schukin.Entities.Const.DEFAULT_HEIGHT;
import static ru.nsu.ccfit.schukin.Entities.Const.DEFAULT_WIDTH;
import static ru.nsu.ccfit.schukin.Entities.Const.LEGEND_BACKGROUND_COLOR;

/**
 * Created by kannabi on 04.04.2017.
 */

public class CoordinatesBar extends JPanel{
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT / 18;
    private BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public CoordinatesBar (){
        setPreferredSize(new Dimension(width, height));
        spanWhole();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }

    public void drawValues(double x, double y, double functionValue){
        spanHalf();

        Graphics g = buffer.createGraphics();
        Font font = new Font("Helvetica", Font.PLAIN, 22);

        g.setFont(font);
        g.setColor(Color.black);
        String values = "x: " +
                        new DecimalFormat("#.###").format(x) + " | " +
                        "y: " +
                        new DecimalFormat("#.###").format(y) + " | " +
                        "f: " +
                        new DecimalFormat("#.###").format(functionValue);
        g.drawString(values, 5, height - height / 2 + 10);
        repaint();
    }

    private void spanWhole(){
        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height; ++j)
                buffer.setRGB(i, j, LEGEND_BACKGROUND_COLOR);
    }

    private void spanHalf(){
        for (int i = 0; i < width / 2; ++i)
            for (int j = 0; j < height; ++j)
                buffer.setRGB(i, j, LEGEND_BACKGROUND_COLOR);
    }
}
