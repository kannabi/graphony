package ru.nsu.fit.g14205.schukin.View;

import ru.nsu.fit.g14205.schukin.Entities.ToolBarButton;

import static ru.nsu.fit.g14205.schukin.Entities.Const.*;
import static ru.nsu.fit.g14205.schukin.Entities.ToolBarButton.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by kannabi on 06.03.17.
 */

public class Toolbar extends JPanel{
    private JToolBar jtb = new JToolBar();
    private JToggleButton replaceButton, xorButton;
    private final JButton ply_bt;
    private final JButton impBt;

    private static OnButtonClickListener listener;

    public interface OnButtonClickListener{
        void onButtonClick(ToolBarButton button);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.listener = listener;
    }

    public Toolbar() {
        setLayout(new BorderLayout());

        add(jtb, BorderLayout.NORTH);
        jtb.setFloatable(false);

        JButton bt = null;
        bt = new JButton("CLR");
        bt.addActionListener((ActionEvent e) -> listener.onButtonClick(CLR));
        bt.setToolTipText(CLEAR_BUTTON_HINT);
        jtb.add(bt);

        bt = new JButton("STP");
        bt.addActionListener((ActionEvent e) -> listener.onButtonClick(NXT_STP));
        bt.setToolTipText(STEP_BUTTON_HINT);
        jtb.add(bt);

        ply_bt = new JButton(PLAY_BTN_NAME);
        ply_bt.addActionListener((ActionEvent e) ->{
            listener.onButtonClick(PLY);
            if(ply_bt.getText().equals(PLAY_BTN_NAME))
                ply_bt.setText(STOP_BTN_NAME);
            else
                ply_bt.setText(PLAY_BTN_NAME);
        });
        ply_bt.setToolTipText(RUN_BUTTON_HINT);
        jtb.add(ply_bt);

        jtb.addSeparator();

        replaceButton = new JToggleButton(REPLACE_BTN_NAME);

        xorButton = new JToggleButton(XOR_BTN_NAME);
        xorButton.addActionListener((ActionEvent e) -> {
            listener.onButtonClick(XOR);
            replaceButton.setSelected(false);
            xorButton.setSelected(true);
        });
        xorButton.setToolTipText(REPLACE_BUTTON_HINT);
        jtb.add(xorButton);

        replaceButton.addActionListener((ActionEvent e) -> {
            listener.onButtonClick(RPLC);
            xorButton.setSelected(false);
            replaceButton.setSelected(true);
        });
        replaceButton.setToolTipText(REPLACE_BUTTON_HINT);
        jtb.add(replaceButton);

        impBt = new JButton(SHOW_IMPACT);
        impBt.addActionListener((ActionEvent e) ->{
            listener.onButtonClick(SH_IMP);
            if(impBt.getText().equals(SHOW_IMPACT))
                impBt.setText(HIDE_IMPACT);
            else
                impBt.setText(SHOW_IMPACT);
        });
        impBt.setToolTipText(SHOW_IMPACT_BUTTON_HINT);
        jtb.add(impBt);

        jtb.addSeparator();

        final JButton stnButton = new JButton(SETTINGS_BTN_NAME);
        stnButton.addActionListener((ActionEvent e) -> listener.onButtonClick(SETTINGS));
        stnButton.setToolTipText(SETTINGS_BUTTON_HINT);
        jtb.add(stnButton);

        final JButton helpButton = new JButton(HELP_BUTTON);
        helpButton.addActionListener((ActionEvent e) -> listener.onButtonClick(HELP));
        helpButton.setToolTipText(HELP_BUTTON_HINT);
        jtb.add(helpButton);
    }

    public void setReplaceMode(ToolBarButton mode){
        if (mode == XOR){
            replaceButton.setSelected(false);
            xorButton.setSelected(true);
        } else {
            replaceButton.setSelected(true);
            xorButton.setSelected(false);
        }
    }

    public void changeRunButton(){
        if(ply_bt.getText().equals(PLAY_BTN_NAME))
            ply_bt.setText(STOP_BTN_NAME);
        else
            ply_bt.setText(PLAY_BTN_NAME);
    }

    public void changeShowImpactButton(){
        if(impBt.getText().equals(SHOW_IMPACT))
            impBt.setText(HIDE_IMPACT);
        else
            impBt.setText(SHOW_IMPACT);
    }
}
