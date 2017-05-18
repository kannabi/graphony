package ru.nsu.ccfit.schukin.IsolineView;

import ru.nsu.ccfit.schukin.Entities.ColorMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import static ru.nsu.ccfit.schukin.Entities.Const.DEFAULT_HEIGHT;
import static ru.nsu.ccfit.schukin.Entities.Const.DEFAULT_WIDTH;
import static ru.nsu.ccfit.schukin.Entities.Const.LEGEND_BACKGROUND_COLOR;

/**
 * Created by kannabi on 03.04.2017.
 */
public class LegendPanel extends JPanel{
    private BufferedImage buffer;
    private int part = 12;
    private int height, width;

    public LegendPanel(){
        height = DEFAULT_HEIGHT / part;
        width = DEFAULT_WIDTH;
        setPreferredSize(new Dimension(width, height));
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        spanBackground();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }

    public void setParams(ColorMap colorMap){
        int cellSize = width / colorMap.colorNumber();

        drawValues(colorMap, cellSize);

        try {
            for (int i = 0, pos = 0; i < colorMap.colorNumber() && pos < width; ++i, pos += cellSize)
                spanCell(pos, height - height / 2, cellSize, colorMap.getCellValue(i));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void spanCell(int x, int y, int cellSize, Color color){
        int i, j;
        for (i = x; i < x + cellSize - 2; ++i)
            for (j = y; j < height; ++j)
                buffer.setRGB(i, j, color.getRGB());

        for (j = y; j < height; ++j) {
            buffer.setRGB(i, j, 0x000000);
            buffer.setRGB(i + 1, j, 0x000000);
        }
    }

    private void spanBackground(){
        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height / 2; ++j)
                buffer.setRGB(i, j, LEGEND_BACKGROUND_COLOR);
    }

    private void drawValues(ColorMap colorMap, int cellSize){
        Graphics g = buffer.createGraphics();
        Font font = new Font("Helvetica", Font.PLAIN, 18);

        g.setFont(font);
        g.setColor(Color.black);

        int y = height / 4 + 5;
        try {
            for (int i = 0, pos = cellSize - 10; i < colorMap.colorNumber() - 1; ++i, pos += cellSize)
                g.drawString(new DecimalFormat("#.##").format(colorMap.getCellKey(i)), pos, y);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
