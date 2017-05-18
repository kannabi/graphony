package ru.nsu.fit.g14205.schukin.Model;

import lombok.Data;

import java.util.LinkedList;

/**
 * Created by kannabi on 03.05.2017.
 */

@Data
public class ModelState {
    private double minX, maxX;
    private double minY, maxY;

    private LinkedList<GraphLine> graphLines = new LinkedList<>();
}
