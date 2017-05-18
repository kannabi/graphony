package ru.nsu.fit.g14205.schukin.View.SettingWindow;

import ru.nsu.fit.g14205.schukin.Model.LifeModel;
import ru.nsu.fit.g14205.schukin.Presenter.LifePresenter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.*;

import static ru.nsu.fit.g14205.schukin.Entities.Const.INCORRECT_LIFE_PARAMS;
import static ru.nsu.fit.g14205.schukin.Entities.ToolBarButton.RPLC;
import static ru.nsu.fit.g14205.schukin.Entities.ToolBarButton.XOR;

public class SettingsWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel mLabel;
    private JLabel nLabel;
    private JSlider lengthSlider;
    private JSlider thinknessSlider;
    private JSpinner thinknessSpinner;
    private JRadioButton xorButton;
    private JRadioButton replaceButton;
    private JLabel modeLabel;
    private JSpinner lengthSpinner;
    private JSpinner mSpinner;
    private JSpinner nSpinner;
    private JSpinner birthBeginSpinner;
    private JSpinner birthEndSpinner;
    private JSpinner liveBeginSpinner;
    private JSpinner liveEndSpinner;
    private JSpinner firstImpactSpinner;
    private JSpinner secondImpactSpinner;

    private LifeModel model;
    private LifePresenter presenter;

    private int m, n;

    public SettingsWindow(LifeModel model, LifePresenter presenter) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("SettingsWindow");
//        setSize(600, 350);

        this.model = model;
        this.presenter = presenter;

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        initFieldsValue();

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

        pack();
        setVisible(true);
    }

    private void onOK() {
        if (xorButton.isSelected() && model.getReplaceMode() == RPLC)
            model.setReplaceMode(XOR);
        else
            model.setReplaceMode(RPLC);

        model.setLength(lengthSlider.getValue());
        model.setThickness(thinknessSlider.getValue());
        model.resizeMap((int)mSpinner.getValue(), (int)nSpinner.getValue());

        model.setFst_impact((double)firstImpactSpinner.getValue());
        model.setSnd_impact((double)secondImpactSpinner.getValue());
        model.setBirth_begin((double)birthBeginSpinner.getValue());
        model.setBirth_end((double)birthEndSpinner.getValue());
        model.setLive_begin((double)liveBeginSpinner.getValue());
        model.setLive_end((double)liveEndSpinner.getValue());

        presenter.redrawMap();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void initFieldsValue(){
        setUpSizeSpinners();
        setUpLengthSlider();
        setUpThinknessSlider();
        setUpRadioButtons();
        setUpLifeParamsSpinners();
    }

    private void setUpSizeSpinners(){
        mSpinner.setModel(new SpinnerNumberModel());
        mSpinner.setValue(model.getM());

        nSpinner.setModel(new SpinnerNumberModel());
        nSpinner.setValue(model.getN());
    }

    private void setUpLengthSlider(){
        lengthSlider.setMinimum(5);
        lengthSlider.setMaximum(50);
        lengthSlider.setPaintTicks(true);
        lengthSlider.setMajorTickSpacing(5);
        lengthSlider.setPaintLabels(true);
        lengthSlider.setValue(model.getLength());

        lengthSpinner.setModel(new SpinnerNumberModel());
        lengthSpinner.setValue(model.getLength());

        lengthSpinner.addChangeListener((ChangeEvent e) ->
                lengthSlider.setValue((int)(((JSpinner)e.getSource()).getValue())));

        lengthSlider.addChangeListener((ChangeEvent e) ->
                lengthSpinner.setValue((int)((JSlider)e.getSource()).getValue()));
    }

    private void setUpThinknessSlider(){
        thinknessSlider.setMinimum(1);
        thinknessSlider.setMaximum(5);
        thinknessSlider.setPaintTicks(true);
        thinknessSlider.setMajorTickSpacing(1);
        thinknessSlider.setPaintLabels(true);
        thinknessSlider.setValue(model.getThickness());

        thinknessSpinner.setModel(new SpinnerNumberModel());
        thinknessSpinner.setValue(model.getThickness());

        thinknessSpinner.addChangeListener((ChangeEvent e) ->
                thinknessSlider.setValue((int)(((JSpinner)e.getSource()).getValue())));

        thinknessSlider.addChangeListener((ChangeEvent e) ->
                thinknessSpinner.setValue((int)((JSlider)e.getSource()).getValue()));
    }

    private void setUpRadioButtons(){
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(xorButton);
        buttonGroup.add(replaceButton);
        if (model.getReplaceMode() == XOR)
            xorButton.setSelected(true);
        else
            replaceButton.setSelected(true);

    }

    private void setUpLifeParamsSpinners(){
        firstImpactSpinner.setModel(new SpinnerNumberModel(model.getFst_impact(), 0.0, 100.0, 0.1));
        firstImpactSpinner.addChangeListener((listener) -> {
            if(!checkCorrectnessLifeParams())
                firstImpactSpinner.setValue(model.getFst_impact());
        });

        secondImpactSpinner.setModel(new SpinnerNumberModel(model.getSnd_impact(), 0.0, 100.0, 0.1));
        secondImpactSpinner.addChangeListener((listener) ->{
                if(!checkCorrectnessLifeParams())
                    secondImpactSpinner.setValue(model.getSnd_impact());
        });

        liveBeginSpinner.setModel(new SpinnerNumberModel(model.getLive_begin(), 0.0, 100.0, 0.1));
        liveBeginSpinner.addChangeListener((listener) -> {
            if(!checkCorrectnessLifeParams())
                liveBeginSpinner.setValue(model.getLive_begin());
        });

        liveEndSpinner.setModel(new SpinnerNumberModel(model.getLive_end(), 0.0, 100.0, 0.1));
        liveEndSpinner.addChangeListener((listener) -> {
            if(!checkCorrectnessLifeParams())
                liveEndSpinner.setValue(model.getLive_end());
        });

        birthBeginSpinner.setModel(new SpinnerNumberModel(model.getBirth_begin(), 0.0, 100.0, 0.1));
        birthBeginSpinner.addChangeListener((listener) -> {
            if(!checkCorrectnessLifeParams())
                birthBeginSpinner.setValue(model.getBirth_begin());
        });

        birthEndSpinner.setModel(new SpinnerNumberModel(model.getBirth_end(), 0.0, 100.0, 0.1));
        birthEndSpinner.addChangeListener((listener) -> {
            if(!checkCorrectnessLifeParams())
                birthEndSpinner.setValue(model.getBirth_end());
        });
    }

    private boolean checkCorrectnessLifeParams(){
        if(((double)liveBeginSpinner.getValue()) <= ((double)birthBeginSpinner.getValue()) &&
                (double)birthBeginSpinner.getValue() <= (double)birthEndSpinner.getValue() &&
                (double)birthEndSpinner.getValue() <= (double)liveEndSpinner.getValue())
            return true;

        Thread dialog = new Thread(() -> JOptionPane.showMessageDialog(null, INCORRECT_LIFE_PARAMS));
        dialog.start();

        return false;
    }
}
