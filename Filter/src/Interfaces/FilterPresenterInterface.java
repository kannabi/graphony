package Interfaces;

import Model.FilterModel;
import Presenter.FilterPresenter;
import View.FilterView;

/**
 * Created by kannabi on 24/03/2017.
 */
public interface FilterPresenterInterface {

    void setView(FilterViewInterface view);

    void setModel(FilterModel model);

    FilterViewInterface getView();

    FilterModel getModel();

    void run();
}
