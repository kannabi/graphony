package ru.nsu.fit.g14205.schukin.Interfaces;

import ru.nsu.fit.g14205.schukin.Entities.ToolBarButton;
import ru.nsu.fit.g14205.schukin.Model.LifeModel;
import ru.nsu.fit.g14205.schukin.View.LifeView;

/**
 * Created by kannabi on 06.03.2017.
 */
public interface LifePresenterInterface {
    LifeModel getModel();

    void setModel(LifeModel model);

    LifeView getView();

    void setView(LifeView view);

    void mouseClicked(int x, int y);

    void clearMap();

    void getNextStep();

    void run();

    void setReplaceMode(ToolBarButton mode);

    void startIterationProcess ();

    void redrawMap();

    void saveConfiguration();

    void showHelpDialog();

    void showSettingsWindow();

    void chooseFile();

    void createNewGameDocument();

    void exit();
}
