package ru.nsu.ccfit.schukin.IsolineView;

import ru.nsu.ccfit.schukin.Entities.FunctionData;
import ru.nsu.ccfit.schukin.Entities.ToolbarButton;
import ru.nsu.ccfit.schukin.Interfaces.IsolineModelInterface;
import ru.nsu.ccfit.schukin.Interfaces.IsolinePresenterInterface;
import ru.nsu.ccfit.schukin.Interfaces.IsolineViewInterface;
import ru.nsu.ccfit.schukin.Interfaces.RequiresEDT;

import javax.swing.*;
import java.awt.*;

import static ru.nsu.ccfit.schukin.Entities.Const.HELP_MESSAGE;

/**
 * Created by kannabi on 02.04.2017.
 */
public class IsolineView extends JFrame implements IsolineViewInterface {
    private IsolinePresenterInterface presenter;
    private Toolbar toolbar;
    private GraphWindow graphWindow;
    private LegendPanel legendPanel;
    private CoordinatesBar coordinatesBar;

    @Override
    public void setPresenter(IsolinePresenterInterface presenter){
        this.presenter = presenter;
    }

    @Override
    public IsolinePresenterInterface getPresenter(){
        return presenter;
    }

    @Override
    public void initUI(){
        setTitle("Filter");
        setResizable(false);

        JPanel global = new JPanel(new BorderLayout());

        JPanel cp = new JPanel(new BorderLayout());

        toolbar = new Toolbar();
        toolbar.setOnButtonClickListener(this::toolbarButtonProcessor);
        global.add(toolbar, BorderLayout.NORTH);

        graphWindow = new GraphWindow();
        graphWindow.setOnMouseMoveListener(this::mouseMoveProcessor);
        cp.add(graphWindow, BorderLayout.CENTER);

        legendPanel = new LegendPanel();
        cp.add(legendPanel, BorderLayout.SOUTH);

        coordinatesBar = new CoordinatesBar();
        cp.add(coordinatesBar, BorderLayout.NORTH);

        global.add(cp, BorderLayout.CENTER);

        setContentPane(global);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void paintGraph(IsolineModelInterface model, FunctionData data){
        graphWindow.prepareGraph(data, model);
        graphWindow.repaint();
        legendPanel.setParams(model.getColorMap());
        legendPanel.repaint();
    }

    private void toolbarButtonProcessor(ToolbarButton btn){
        switch (btn){
            case HELP:
                Thread dialog = new Thread(() -> JOptionPane.showMessageDialog(null, HELP_MESSAGE));
                dialog.start();
                break;
            case GRID:
                graphWindow.switchDrawGridState();
                paintGraph(presenter.getModel(), presenter.getFunctionalData());
                break;
            case ENTRY_POINTS:
                graphWindow.switchDrawEntryPoints();
                paintGraph(presenter.getModel(), presenter.getFunctionalData());
                break;
        }
    }

    @RequiresEDT
    private void mouseMoveProcessor(double x, double y, double functionValue){
        coordinatesBar.drawValues(x, y, functionValue);
        coordinatesBar.repaint();
    }
}
