package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Entities.HexMap;
import ru.nsu.fit.g14205.schukin.Entities.MenuButtons;
import ru.nsu.fit.g14205.schukin.Entities.ToolBarButton;
import ru.nsu.fit.g14205.schukin.Interfaces.LifeViewInterface;
import ru.nsu.fit.g14205.schukin.Presenter.LifePresenter;
import sun.management.ExtendedPlatformComponent;

import javax.swing.*;
import java.awt.*;

import static ru.nsu.fit.g14205.schukin.Entities.ToolBarButton.RPLC;
import static ru.nsu.fit.g14205.schukin.Entities.ToolBarButton.XOR;

/**
 * Created by kannabi on 06.03.2017.
 */

//TODO: сделать все-таки две разные залипающие кнопки для XOR и REPLACE.

public class LifeView extends JFrame implements LifeViewInterface {
    private HexMapComponent mapComponent;
    private LifePresenter presenter;
    private Toolbar toolbar;

    private static LifeView.OnChangeButtonStatekListener toolbarListener;

    public interface OnChangeButtonStatekListener{
        void onChangeButtonState(ToolBarButton button);
    }

    public void setOnChangeButtonStateListener(LifeView.OnChangeButtonStatekListener listener){
        this.toolbarListener = listener;
    }

    @Override
    public void paintMap(HexMap map){
        if (SwingUtilities.isEventDispatchThread()){
            mapComponent.drawMap(map);
            mapComponent.repaint();
        } else {
            SwingUtilities.invokeLater(() -> {
                mapComponent.drawMap(map);
                mapComponent.repaint();
            });
        }
    }

    @Override
    public void spanHex(int i, int j){}

    @Override
    public void createInterface(HexMap map, int len){

        Menu menu = new Menu();
        menu.setOnItemClickListener(this::menuItemProcessor);
        setJMenuBar(menu.getMenuBar());

        setTitle("Life");
        JPanel cp = new JPanel(new BorderLayout());

        toolbar = new Toolbar();
        toolbar.setOnButtonClickListener(this::toolbarButtonProcessor);
        setReplaceModeButtons(RPLC);
        cp.add(toolbar, BorderLayout.NORTH);

        cp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(Color.black)));
        setContentPane(cp);

        mapComponent = new HexMapComponent();
        mapComponent.drawMap(map);
        mapComponent.setOnMouseClickListener((x, y) -> presenter.mouseClicked(x, y));

        JScrollPane scrollPane = new JScrollPane(mapComponent);

        cp.add(scrollPane, BorderLayout.CENTER);

        JButton btn = new JButton("Close");
        btn.addActionListener(e -> System.exit(0));
        cp.add(btn, BorderLayout.SOUTH);

        cp.setBackground(Color.white);
//        cp.setPreferredSize(new Dimension(500, 500));
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        pack();
        setVisible(true);
    }

    @Override
    public void setPresenter(LifePresenter presenter){
        this.presenter = presenter;
    }

    private void toolbarButtonProcessor(ToolBarButton button){
        switch (button){
            case NXT_STP:
                presenter.getNextStep();
                break;
            case PLY:
                presenter.startIterationProcess();
                break;
            case CLR:
                presenter.clearMap();
                break;
            case XOR:
                presenter.setReplaceMode(XOR);
                break;
            case RPLC:
                presenter.setReplaceMode(RPLC);
                break;
            case SH_IMP:
                mapComponent.changeShowingImpact();
                presenter.redrawMap();
                break;
            case SETTINGS:
                presenter.showSettingsWindow();
                break;
            case HELP:
                presenter.showHelpDialog();
                break;
        }
    }

    private void menuItemProcessor(MenuButtons item){
        switch (item){
            case LOAD_CONF:
                presenter.chooseFile();
                break;
            case SAVE_CONF:
                presenter.saveConfiguration();
                break;
            case NEW_FILE:
                presenter.createNewGameDocument();
                break;
            case EXIT:
                presenter.exit();
                break;
            case CLEAR:
                presenter.clearMap();
                break;
            case PARAMS:
                presenter.showSettingsWindow();
                break;
            case ABOUT:
                presenter.showHelpDialog();
                break;
            case STEP:
                presenter.getNextStep();
            case PLAY:
                presenter.startIterationProcess();
                toolbar.changeRunButton();
                break;
            case XOR:
                toolbar.setReplaceMode(XOR);
                presenter.setReplaceMode(XOR);
                break;
            case RPLC:
                toolbar.setReplaceMode(RPLC);
                presenter.setReplaceMode(RPLC);
                break;
            case IMP:
                toolbar.changeShowImpactButton();
                mapComponent.changeShowingImpact();
                presenter.redrawMap();
                break;
        }
    }

    @Override
    public void setReplaceModeButtons(final ToolBarButton mode){
        if (SwingUtilities.isEventDispatchThread()){
            toolbar.setReplaceMode(mode);
        } else {
            SwingUtilities.invokeLater(() -> {
                toolbar.setReplaceMode(mode);
            });
        }
    }
}
