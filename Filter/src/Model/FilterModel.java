package Model;

import Interfaces.FilterModelInterface;
import Presenter.FilterPresenter;

/**
 * Created by kannabi on 24/03/2017.
 */
public class FilterModel implements FilterModelInterface {
    private FilterPresenter presenter;

    @Override
    public void setPresenter(FilterPresenter presenter){
        this.presenter = presenter;
    }
}
