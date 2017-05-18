package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Entities.ToolbarButton;
import ru.nsu.fit.g14205.schukin.Interfaces.RequiresEDT;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframeModelInterface;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframePresenterInterface;
import ru.nsu.fit.g14205.schukin.Interfaces.WireframeViewInterface;
import ru.nsu.fit.g14205.schukin.Model.RealPoint;

import javax.swing.*;
import java.awt.*;

/**
 * Created by kannabi on 16.04.2017.
 */
public class WireframeView extends JFrame implements WireframeViewInterface{
    private WireframePresenterInterface presenter;
    private WireframeModelInterface model;
    private WireframeWindow window;
    private Toolbar toolbar;


    public void setPresenter(WireframePresenterInterface presenter){
        this.presenter = presenter;
    }

    public WireframePresenterInterface getPresenter(){
        return presenter;
    }

    public void initUI(){
        setTitle("Wireframe");
        setResizable(false);

        JPanel cp = new JPanel(new BorderLayout());

        toolbar = new Toolbar();
        toolbar.setOnButtonClickListener(this::toolbarButtonProcessor);
        cp.add(toolbar, BorderLayout.NORTH);
        window = new WireframeWindow();
        window.setOnMouseMoveListener((x, y, z) -> {
            model.rotateFigures(x, y, z);
            repaintView();
        });

        updateParameters();
        cp.add(window);

        setContentPane(cp);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void toolbarButtonProcessor(ToolbarButton btn){
        switch (btn){
            case SETTINGS:
                model.saveState();
                new Thread(() -> new SettingsWindow(presenter.getModel(), this)).start();
//                new SettingsWindow(presenter.getModel(), this);
                break;
            case INIT:
                model.initAngles();
                repaintView();
                break;
        }
    }

    @RequiresEDT
    public void repaintView(){
        window.drawFigure(model.getGraphLines());
        window.repaint();
    }

    public void setModel(WireframeModelInterface model){
        this.model = model;
    }

    public WireframeModelInterface getModel(){
        return model;
    }

    public void updateParameters(){
        repaintView();
    }
}
