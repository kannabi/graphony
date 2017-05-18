package Filters;

import Interfaces.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Objects;

import static Filters.TransformationUtils.*;

/**
 * Created by kannabi on 27/03/2017.
 */
public class Watercolorization implements Filter {
    private BufferedImage workingImage;

    @Override
    public BufferedImage transform(BufferedImage image){
        int radius = 1;
        BufferedImage workingImage  = new BufferedImage(image.getWidth() + radius * 2,
                image.getHeight() + radius * 2, BufferedImage.TYPE_INT_RGB);
        BufferedImage resImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        LinkedList<Integer> area = new LinkedList<>();
        int newRed, newGreen, newBlue;

        for (int  l = 0; l < image.getWidth(); ++l)
            for (int k = 0; k < image.getHeight(); ++k) {
                workingImage.setRGB(l + radius, k + radius, image.getRGB(l, k));
                resImage.setRGB(l, k, image.getRGB(l, k));
            }


        for (int i = radius; i < image.getWidth(); ++i)
            for (int j = radius; j < image.getHeight(); ++j){

                for (int l = -radius; l < radius + 1; ++l)
                    for (int k = -radius; k < radius + 1; ++k)
                        area.add(getRed(workingImage.getRGB(i + l, j + k)));

                area.sort(new Comparator());
                newRed = area.get(area.size() / 2);
                area.clear();

                for (int l = -radius; l < radius + 1; ++l)
                    for (int k = -radius; k < radius + 1; ++k)
                        area.add(getGreen(workingImage.getRGB(i + l, j + k)));

                area.sort(new Comparator());
                newGreen = area.get(area.size() / 2);
                area.clear();

                for (int l = -radius; l < radius + 1; ++l)
                    for (int k = -radius; k <radius + 1; ++k)
                        area.add(getBlue(workingImage.getRGB(i + l, j + k)));

                area.sort(new Comparator());
                newBlue = area.get(area.size() / 2);
                area.clear();

                resImage.setRGB(i, j, new Color(newRed, newGreen, newBlue).getRGB());
            }


        return new Sharpness().transform(resImage);
    }

    private class Comparator implements java.util.Comparator<Integer>{
        @Override
        public int compare(Integer v0, Integer v1){
            if (v0 > v1)
                return 1;
            if (Objects.equals(v0, v1))
                return 0;
            if (v0 < v1)
                return -1;
            return 0;
        }
    }
}
