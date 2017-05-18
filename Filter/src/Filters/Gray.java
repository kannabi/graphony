package Filters;

import Interfaces.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by kannabi on 25/03/2017.
 */
public class Gray implements Filter {
    @Override
    public BufferedImage transform(BufferedImage image){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int red, green, blue;
        Color color;
        int c;

        for (int i = 0; i < image.getWidth(); ++i)
            for (int j = 0; j < image.getHeight(); ++j){
                color = new Color(image.getRGB(i, j));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

//                System.out.println(image.getRGB(i, j));

                c = (int)(0.299 * red + 0.587 * green + 0.114 * blue);
                newImage.setRGB(i, j, new Color(c, c, c).getRGB());
            }
        return newImage;
    }
}
