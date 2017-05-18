package ru.nsu.fit.g14205.schukin.View.NewDocument;

import ru.nsu.fit.g14205.schukin.Model.LifeModel;
import ru.nsu.fit.g14205.schukin.Presenter.LifePresenter;

import javax.swing.*;
import java.awt.event.*;

import static ru.nsu.fit.g14205.schukin.Entities.Const.*;

public class NewDocument extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner lengthSpinner;
    private JSpinner thinknessSpinner;
    private JSpinner mSpinner;
    private JSpinner nSpinner;

    LifeModel model;
    LifePresenter presenter;

    public NewDocument(LifeModel model, LifePresenter presenter) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("New Document");

        this.model = model;
        this.presenter = presenter;

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
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setUpSpinners();

        pack();
        setVisible(true);
    }

    private void onOK() {
        model.resizeMap((int)mSpinner.getValue(), (int)nSpinner.getValue());
        model.setLength((int)lengthSpinner.getValue());

        model.setFst_impact(FST_IMPACT);
        model.setSnd_impact(SND_IMPACT);
        model.setBirth_begin(BIRTH_BEGIN);
        model.setBirth_end(BIRTH_END);
        model.setLive_begin(LIVE_BEGIN);
        model.setLive_end(LIVE_END);

        presenter.redrawMap();
//        model.setThinkness((int)thinknessSpinner.getValue());
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void setUpSpinners(){
        thinknessSpinner.setModel(new SpinnerNumberModel(1, 0, 5, 1));
        lengthSpinner.setModel(new SpinnerNumberModel(30, 5, 100, 1));
        mSpinner.setModel(new SpinnerNumberModel(10, 1, 100, 1));
        nSpinner.setModel(new SpinnerNumberModel(10, 1, 100, 1));
    }
}
