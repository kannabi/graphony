package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static Entities.Const.DEFAULT_PANEL_HEIGHT;
import static Entities.Const.DEFAULT_PANEL_WIDTH;


/**
 * Created by kannabi on 25/03/2017.
 */
public class Window extends JPanel{
    private BufferedImage buffer;

    public Window(){
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black)));
        this.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));
    }

    public void setBuffer(BufferedImage image){
        buffer = image;
    }

    public BufferedImage getBuffer(){
        return buffer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }
}
