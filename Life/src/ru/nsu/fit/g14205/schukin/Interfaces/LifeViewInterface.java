package ru.nsu.fit.g14205.schukin.Interfaces;

import ru.nsu.fit.g14205.schukin.Entities.HexMap;
import ru.nsu.fit.g14205.schukin.Entities.ToolBarButton;
import ru.nsu.fit.g14205.schukin.Presenter.LifePresenter;

/**
 * Created by kannabi on 06.03.2017.
 */
public interface LifeViewInterface {
    void paintMap(HexMap map);

    void spanHex(int i, int j);

    void createInterface(HexMap map, int len);

    void setPresenter(LifePresenter presenter);

    void setReplaceModeButtons(ToolBarButton mode);
}