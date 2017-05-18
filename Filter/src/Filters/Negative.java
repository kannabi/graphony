package Filters;

import Interfaces.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by kannabi on 26/03/2017.
 */
public class Negative implements Filter {

    @Override
    public BufferedImage transform(BufferedImage image){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int red, green, blue;
        Color color;
        int c;

        for (int i = 0; i < image.getWidth(); ++i)
            for (int j = 0; j < image.getHeight(); ++j){
                color = new Color(image.getRGB(i, j));
                red = 255 - color.getRed();
                green = 255 - color.getGreen();
                blue = 255 - color.getBlue();

                newImage.setRGB(i, j, new Color(red, green, blue).getRGB());
            }
        return newImage;
    }
}
