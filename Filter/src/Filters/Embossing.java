package Filters;

import Interfaces.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Filters.TransformationUtils.*;
import static Filters.TransformationUtils.getBlue;

/**
 * Created by kannabi on 26/03/2017.
 */
public class Embossing implements Filter {
    private int n = 3;
    private int [][] cMatrix = new int[n][n];
    private BufferedImage newImage;

    @Override
    public BufferedImage transform(BufferedImage image){
        newImage = new BufferedImage(image.getWidth() + n / 2, image.getHeight() + n / 2, BufferedImage.TYPE_INT_RGB);
        BufferedImage resImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);;
        int newRed, newGreen, newBlue;

        initMatrix();

        int l = 0, k = 0;
        try {
            for (l = 0; l < image.getWidth(); ++l)
                for (k = 0; k < image.getHeight(); ++k) {
                    newImage.setRGB(l + 1, k + 1, image.getRGB(l, k));
                    resImage.setRGB(l, k, image.getRGB(l, k));
                }
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            System.out.println(l + " " + k);
        }

        for (int i = n / 2; i < image.getWidth(); ++i)
            for (int j = n / 2; j < image.getHeight(); ++j){

//            for (l = -1; l < 2; ++l)
//                for (k = -1; k < 2; ++k)
//                    newGreen +=

                newGreen = getSafeVal((
                        cMatrix[0][0] * getGreen(newImage.getRGB(i - 1,j - 1)) +
                                cMatrix[0][1] * getGreen(newImage.getRGB(i, j - 1)) +
                                cMatrix[0][2] * getGreen(newImage.getRGB(i + 1, j - 1)) +
                                cMatrix[1][0] * getGreen(newImage.getRGB(i - 1, j)) +
                                cMatrix[1][1] * getGreen(newImage.getRGB(i, j)) +
                                cMatrix[1][2] * getGreen(newImage.getRGB(i + 1, j)) +
                                cMatrix[2][0] * getGreen(newImage.getRGB(i - 1, j + 1)) +
                                cMatrix[2][1] * getGreen(newImage.getRGB(i, j + 1))
                                + cMatrix[2][2] * getGreen(newImage.getRGB(i + 1, j + 1))
                ) + 128);

                newRed = getSafeVal((
                        cMatrix[0][0] * getRed(newImage.getRGB(i - 1,j - 1)) +
                                cMatrix[0][1] * getRed(newImage.getRGB(i, j - 1)) +
                                cMatrix[0][2] * getRed(newImage.getRGB(i + 1, j - 1)) +
                                cMatrix[1][0] * getRed(newImage.getRGB(i - 1, j)) +
                                cMatrix[1][1] * getRed(newImage.getRGB(i, j)) +
                                cMatrix[1][2] * getRed(newImage.getRGB(i + 1, j)) +
                                cMatrix[2][0] * getRed(newImage.getRGB(i - 1, j + 1)) +
                                cMatrix[2][1] * getRed(newImage.getRGB(i, j + 1))
                                + cMatrix[2][2] * getRed(newImage.getRGB(i + 1, j + 1))
                ) + 128);

                newBlue = getSafeVal((
                        cMatrix[0][0] * getBlue(newImage.getRGB(i - 1,j - 1)) +
                                cMatrix[0][1] * getBlue(newImage.getRGB(i, j - 1)) +
                                cMatrix[0][2] * getBlue(newImage.getRGB(i + 1, j - 1)) +
                                cMatrix[1][0] * getBlue(newImage.getRGB(i - 1, j)) +
                                cMatrix[1][1] * getBlue(newImage.getRGB(i, j)) +
                                cMatrix[1][2] * getBlue(newImage.getRGB(i + 1, j)) +
                                cMatrix[2][0] * getBlue(newImage.getRGB(i - 1, j + 1)) +
                                cMatrix[2][1] * getBlue(newImage.getRGB(i, j + 1))
                                + cMatrix[2][2] * getBlue(newImage.getRGB(i + 1, j + 1))
                ) + 128);

                resImage.setRGB(i, j, new Color(newRed, newGreen, newBlue).getRGB());
            }

        return resImage;
    }

    private void initMatrix() {
        cMatrix[1][0] = 1;
        cMatrix[2][1] = 1;
        cMatrix[0][1] = -1;
        cMatrix[1][2] = -1;
    }
}
