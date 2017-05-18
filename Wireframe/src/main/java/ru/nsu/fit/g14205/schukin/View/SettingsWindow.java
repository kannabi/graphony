package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Interfaces.RequiresEDT;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframeModelInterface;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframeViewInterface;
import ru.nsu.fit.g14205.schukin.Model.RealPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ru.nsu.fit.g14205.schukin.Entities.Const.MAX_WIREFRAME_NUMBER;

public class SettingsWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner minXSpinner;
    private JSpinner maxXSpinner;
    private JSpinner minYSpinner;
    private JSpinner maxYSpinner;
    private JSpinner precisionSpinner;
    private JSpinner nSpinner;
    private JSpinner kSpinner;
    private JSpinner numberGraphSpinner;
    private JSpinner zbSpinner;
    private JSpinner zfSpinner;
    private JSpinner spinner11;
    private JSpinner spinner12;
    private JButton addNewGraphButton;
    private JButton chooseGraphColorButton;
    private JButton deleteGraphButton;
    private JToggleButton deleteSplinePointButton;
    private JButton chooseSplineColorButton;
    private JPanel graphPane;
    private JToggleButton addSplinePoint;
    private JLabel numGraphLabel;
    private JToggleButton showSegmentBordersToggleButton;
    private JToggleButton hideSplineToggleButton;
    private JToggleButton showPresentationButton;
    private JButton dropAnglesButton;
    private JSpinner startXSpinner;
    private JSpinner startYSpinner;
    private JSpinner startZSpinner;

    private WireframeModelInterface model;
    WireframeViewInterface parent;
    private int curGraph = 0;

    public SettingsWindow(WireframeModelInterface model, WireframeViewInterface parent) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.parent = parent;
        this.model = model;
        repaintGraph();
        initSpinners();
        initButtons();

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void onOK() {
        model.saveState();
        parent.updateParameters();
        dispose();
    }

    private void onCancel() {
        model.restoreState();
        parent.updateParameters();
        dispose();
    }

    private RealPoint startPoint = new RealPoint(0.0, 0.0);

    private void createUIComponents() {
        // TODO: place custom component creation code here
        graphPane = new GeneratixGraphWindow(this);
        ((GeneratixGraphWindow) graphPane).setOnMouseClickListener((x, y) -> {
            if (!showPresentationButton.isSelected()) {
                if (addSplinePoint.isSelected()) {
                    model.addB_point(curGraph, new RealPoint(x, y));
                } else if (deleteSplinePointButton.isSelected()) {
                    model.deleteB_point(curGraph, x, y);
                }
                repaintGraph();
            }
        });

        ((GeneratixGraphWindow) graphPane).setOnMouseMoveListener((i, x, y) ->{
            if (!(addSplinePoint.isSelected() ||
                deleteSplinePointButton.isSelected() ||
                showPresentationButton.isSelected())) {
                model.moveB_point(curGraph, i, x, y);
                repaintGraph();
            }
            if (showPresentationButton.isSelected()){

//                model.rotateFigure(curGraph, x, y, z);
                repaint3D();
            }
        });
    }

    @RequiresEDT
    private void repaintGraph() {
        ((GeneratixGraphWindow) graphPane).setBordersValue(model.getMinX(), model.getMaxX(), model.getMinY(), model.getMaxY());
        ((GeneratixGraphWindow) graphPane).prepareData(model.getGraphLine(curGraph));
        parent.updateParameters();
        graphPane.repaint();
    }

    @RequiresEDT
    private void repaint3D(){
        ((GeneratixGraphWindow) graphPane).setBordersValue(model.getMinX(), model.getMaxX(), model.getMinY(), model.getMaxY());
        ((GeneratixGraphWindow) graphPane).draw3D(model.getFigure(curGraph));
        parent.updateParameters();
        graphPane.repaint();
    }

    private void initSpinners() {
        precisionSpinner.setModel(new SpinnerNumberModel(model.getPrecision(curGraph), 1, 100, 1));
        precisionSpinner.addChangeListener((listener) -> {
            model.setPresicion(curGraph, (int) precisionSpinner.getValue());
            repaintGraph();
            parent.repaintView();
        });

        numberGraphSpinner.setModel(new SpinnerNumberModel(0, 0, model.getGraphNumber() - 1, 1));
        numberGraphSpinner.addChangeListener((listener) ->{
            curGraph = (int)numberGraphSpinner.getValue();
            showSegmentBordersToggleButton.setSelected(model.getGraphLine(curGraph).isShowSegmentBorder());
            refreshSpinners();
            repaintGraph();
        });

        zfSpinner.setModel(new SpinnerNumberModel(model.getZf(curGraph), 1.0, 100.0, 0.5));
        zfSpinner.addChangeListener((listener) ->{
            model.setZf(curGraph, (double)zfSpinner.getValue());
            parent.repaintView();
        });

        zbSpinner.setModel(new SpinnerNumberModel(model.getZb(curGraph), 1.0, 100.0, 0.5));
        zbSpinner.addChangeListener((listener) ->{
            model.setZb(curGraph, (double)zbSpinner.getValue());
            parent.repaintView();
        });

        nSpinner.setModel(new SpinnerNumberModel(model.getWireframeNumber(curGraph), 1, MAX_WIREFRAME_NUMBER, 1));
        nSpinner.addChangeListener((listener) -> {
            model.setWireframeNumber(curGraph, (int)nSpinner.getValue());
            parent.repaintView();
        });

        kSpinner.setModel(new SpinnerNumberModel(model.getVerticalNumber(curGraph), 1, MAX_WIREFRAME_NUMBER, 1));
        kSpinner.addChangeListener((listener) -> {
            model.setVerticalNumber(curGraph, (int)kSpinner.getValue());
            parent.repaintView();
        });

        minXSpinner.setModel(new SpinnerNumberModel(model.getMinX(), -50, -1, 1));
        minYSpinner.setModel(new SpinnerNumberModel(model.getMinY(), -50, -1, 1));
        maxXSpinner.setModel(new SpinnerNumberModel(model.getMaxX(), 1, 50, 1));
        maxYSpinner.setModel(new SpinnerNumberModel(model.getMaxY(), 1, 50, 1));
        maxXSpinner.addChangeListener((l)->{
            model.setMaxX((double)maxXSpinner.getValue());
            repaintGraph();
        });
        maxYSpinner.addChangeListener((l)->{
            model.setMaxY((double)maxYSpinner.getValue());
            repaintGraph();
        });
        minYSpinner.addChangeListener((l)->{
            model.setMinY((double)minYSpinner.getValue());
            repaintGraph();
        });
        minXSpinner.addChangeListener((l)->{
            model.setMinX((double)minXSpinner.getValue());
            repaintGraph();
        });

        startXSpinner.setModel(new SpinnerNumberModel(model.getStartPoint(curGraph).getX(), -100.0, 100.0, 0.2));
        startXSpinner.addChangeListener((listener) ->{
            model.moveStartPoint(curGraph, (double)startXSpinner.getValue(),
                                (double)startYSpinner.getValue(), (double)startZSpinner.getValue());
            parent.repaintView();
        });

        startYSpinner.setModel(new SpinnerNumberModel(model.getStartPoint(curGraph).getY(), -100.0, 100.0, 0.2));
        startYSpinner.addChangeListener((listener) ->{
            model.moveStartPoint(curGraph, (double)startXSpinner.getValue(),
                    (double)startYSpinner.getValue(), (double)startZSpinner.getValue());
            parent.repaintView();
        });

        startZSpinner.setModel(new SpinnerNumberModel(model.getStartPoint(curGraph).getZ(), -100.0, 100.0, 0.2));
        startZSpinner.addChangeListener((listener) ->{
            model.moveStartPoint(curGraph, (double)startXSpinner.getValue(),
                    (double)startYSpinner.getValue(), (double)startZSpinner.getValue());
            parent.repaintView();
        });
    }

    private void initButtons() {
        addSplinePoint.addActionListener((e)->{
            if (deleteSplinePointButton.isSelected())
                deleteSplinePointButton.setSelected(false);
        });

        deleteSplinePointButton.addActionListener((e)->{
            if(addSplinePoint.isSelected())
                addSplinePoint.setSelected(false);
        });

        chooseGraphColorButton.addActionListener((e) -> {
            Color curColor = model.getGraphColor(curGraph);
            Color newColor = JColorChooser.showDialog(this, "Choose graph color", curColor);
            model.setGraphColor(curGraph, newColor);
            repaintGraph();
        });

        chooseSplineColorButton.addActionListener((e) -> {
            Color curColor = model.getSplineColor(curGraph);
            Color newColor = JColorChooser.showDialog(this, "Choose graph color", curColor);
            model.setSplineColor(curGraph, newColor);
            repaintGraph();
        });

        addNewGraphButton.addActionListener((e) ->{
            model.addNewGraph();
            int lastGraph = model.getGraphNumber() - 1;
            numberGraphSpinner.setModel(new SpinnerNumberModel(lastGraph, 0, lastGraph, 1));
            curGraph = lastGraph;
            refreshSpinners();
            repaintGraph();
        });

        deleteGraphButton.addActionListener((e) ->{
            model.deleteGraph(curGraph);
            int lastGraph = model.getGraphNumber() - 1;
            curGraph = curGraph - 1 < 0 ? 0 : curGraph - 1;
            numberGraphSpinner.setModel(new SpinnerNumberModel(curGraph, 0, lastGraph, 1));
            refreshSpinners();
            repaintGraph();
        });

        showSegmentBordersToggleButton.addActionListener((e) ->{
            model.setShowSegmentBorderState(curGraph, showSegmentBordersToggleButton.isSelected());
            repaintGraph();
        });

        hideSplineToggleButton.addChangeListener((e) -> {
            ((GeneratixGraphWindow) graphPane).setShowSplineState(!hideSplineToggleButton.isSelected());
            repaintGraph();
        });

        showPresentationButton.addChangeListener((e) ->{
            if (showPresentationButton.isSelected())
                repaint3D();
            else
                repaintGraph();
        });

        dropAnglesButton.addActionListener((e) -> {
            model.initAngles(curGraph);
            parent.repaintView();
            if (showPresentationButton.isSelected())
                repaint3D();
        });
    }

    int getB_pointfromModel(double x, double y){
        return model.findB_point(curGraph, x, y);
    }

    private void refreshSpinners(){
        startXSpinner.setValue(model.getStartPoint(curGraph).getX());
        startYSpinner.setValue(model.getStartPoint(curGraph).getY());
        startZSpinner.setValue(model.getStartPoint(curGraph).getZ());

        kSpinner.setValue(model.getVerticalNumber(curGraph));
        nSpinner.setValue(model.getWireframeNumber(curGraph));
    }
}