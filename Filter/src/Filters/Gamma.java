package Filters;

import Interfaces.Filter;


import java.awt.*;
import java.awt.image.BufferedImage;
import static Filters.TransformationUtils.*;
import static java.lang.Math.pow;

/**
 * Created by kannabi on 27/03/2017.
 */
public class Gamma implements Filter {

    private double coef;

    public Gamma(double coef){
        this.coef = coef;
    }

    @Override
    public BufferedImage transform(BufferedImage image){
        BufferedImage resImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int newRed, newGreen, newBlue;

        for (int i = 0; i < image.getWidth(); ++i)
            for (int j = 0; j < image.getHeight(); ++j){
                newRed = getSafeVal((int)(pow(getRed(image.getRGB(i, j)), coef)));
                newGreen = getSafeVal((int)(pow(getGreen(image.getRGB(i, j)), coef)));
                newBlue = getSafeVal((int)(pow(getBlue(image.getRGB(i, j)), coef)));

//                int correction = -20;
//                newRed = getSafeVal((int)(getRed(image.getRGB(i, j)) + correction));
//                newGreen = getSafeVal((int)(getGreen(image.getRGB(i, j)) + correction));
//                newBlue = getSafeVal((int)(getBlue(image.getRGB(i, j)) + correction));

                resImage.setRGB(i, j, new Color(newRed, newGreen, newBlue).getRGB());
            }

        return resImage;
    }
}
