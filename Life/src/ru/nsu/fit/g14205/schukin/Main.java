package ru.nsu.fit.g14205.schukin;

import ru.nsu.fit.g14205.schukin.Model.LifeModel;
import ru.nsu.fit.g14205.schukin.Presenter.LifePresenter;
import ru.nsu.fit.g14205.schukin.View.LifeView;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LifePresenter presenter = new LifePresenter();
                LifeView view = new LifeView();
                presenter.setModel(new LifeModel());
                presenter.setView(new LifeView());
                presenter.run();
            }
        });
    }
}
