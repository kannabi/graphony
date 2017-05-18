package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Entities.Hex;
import ru.nsu.fit.g14205.schukin.Entities.Span;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static java.lang.Math.sqrt;
import static ru.nsu.fit.g14205.schukin.Entities.Const.SPAN_COLOR;

/**
 * Created by kannabi on 04/03/2017.
 */

//TODO: упростить алгоритм спан заливки так, чтобы отдельно пробегал всю линию

public final class DrawingTools {

    DrawingTools(){}

    private static int getSign(int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }

    public static void drawBresenhamLine (int x0, int y0, int x1, int y1, BufferedImage buffer, int color) {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = x1 - x0;
        dy = y1 - y0;

        incx = getSign(dx);
        incy = getSign(dy);

        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;

        //определяем наклон отрезка:
        if (dx > dy) {
            pdx = incx;	pdy = 0;
            es = dy;	el = dx;
        } else {
            pdx = 0;	pdy = incy;
            es = dx;	el = dy;
        }

        x = x0;
        y = y0;
        err = el/2;

        buffer.setRGB (x, y, color);

        for (int t = 0; t < el; t++) {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            } else {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            buffer.setRGB(x, y, color);
        }
    }

/*
* Гексагон (внезапно!) имеет шесть вершин.
* Высшую точку обозначим через приставку n (north), нижную -- через s (south);
* Точка справа-сверху -- en(east-north), справа-снизу -- es (east-south)
* Для левой грани аналогично.
* Координаты центра запишем в переменные хс и ус (xcenter и ycenter аналогично)
* */
    public static void drawHex(Hex hex, int len, BufferedImage buffer, int color, int thickness, int i, int j) {
        int xc = hex.getCoorX();
        int yc = hex.getCoorY();

        int nx = xc;
        int ny = yc - len;

        int enx = (int)((double)xc + sqrt(3) * (double) (len) / 2 + 0.5);
        int eny = yc - (len / 2);

        int esx = enx;
        int esy = eny + len;

        int sx = xc;
        int sy = yc + len;

        int wnx = (int)((double)xc - sqrt(3) * (double) (len) / 2 + 0.5);
        int wny = eny;

        int wsx = wnx;
        int wsy = esy;


        drawMyLine(enx, eny, esx, esy, buffer, color, thickness);
        drawMyLine(esx, esy, sx, sy, buffer, color, thickness);

        if (j == 0)
            drawMyLine(wnx, wny, wsx, wsy, buffer, color, thickness);
        drawMyLine(wsx, wsy, sx, sy, buffer, color, thickness);

        if (i % 2 == 0)
            drawMyLine(nx, ny, wnx, wny, buffer, color, thickness);

        if(i % 2 == 1 || i == 0)
            drawMyLine(nx, ny, enx, eny, buffer, color, thickness);
    }

    private static void drawMyLine(int x0, int y0, int x1, int y1, BufferedImage buffer, int color, int thickness){
        if(thickness == 1) {
            drawBresenhamLine(x0, y0, x1, y1, buffer, color);
        } else {
            Graphics2D g = buffer.createGraphics();
            g.setStroke(new BasicStroke(thickness));
            g.setColor(new Color(color));
            g.drawLine(x0, y0, x1, y1);
        }
    }

    public static void fillSpan(int seedX, int seedY, BufferedImage buffer, int oldColor){

        final boolean TAKED = false;
        final boolean NOT_TAKED = true;

        LinkedList<Span> stack = new LinkedList<>();
        Span currentSpan;
        int currentY;
        boolean flagUpSpan = NOT_TAKED;
        boolean flagDownSpan = NOT_TAKED;

        int i;

        stack.add(createSpan(seedX, seedY, buffer, oldColor));

        while (!stack.isEmpty()){
            currentSpan = stack.pop();
            currentY = currentSpan.getY();

            for (i = currentSpan.getLeft(); i <= currentSpan.getRight(); ++i){
                buffer.setRGB(i, currentY, SPAN_COLOR);

                if (buffer.getRGB(i, currentY - 1) == oldColor && flagUpSpan) {
                    stack.push(createSpan(i, currentY - 1, buffer, oldColor));
                    flagUpSpan = TAKED;
                } else
                    flagUpSpan = NOT_TAKED;

                if (buffer.getRGB(i, currentY + 1) == oldColor && flagDownSpan) {
                    stack.push(createSpan(i, currentY + 1, buffer, oldColor));
                    flagDownSpan = TAKED;
                } else
                    flagDownSpan = NOT_TAKED;
            }
        }
    }

    private static Span createSpan(int seedX, int seedY, BufferedImage buffer, int oldColor){
        int left, right;
        int y = seedY;

        left = right = seedX;

        while (buffer.getRGB(left, y) == oldColor && left > 0){
            --left;
        }
        ++left;

        int width = buffer.getWidth();
        while (buffer.getRGB(right,y) == oldColor && right < width - 1){
            ++right;
        }
        --right;

        return new Span(left, right, y);
    }
}