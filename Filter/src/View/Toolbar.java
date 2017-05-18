package View;

import Entities.ToolbarButton;

import javax.swing.*;
import java.awt.*;

import static Entities.Const.*;
import static Entities.ToolbarButton.*;

/**
 * Created by kannabi on 24/03/2017.
 */
public class Toolbar extends JPanel{
    private JToolBar jtb = new JToolBar();

    private static OnButtonClickListener listener;

    public interface OnButtonClickListener{
        void onButtonClick(ToolbarButton button);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.listener = listener;
    }

    public Toolbar() {
        setLayout(new BorderLayout());

        add(jtb, BorderLayout.NORTH);
        jtb.setFloatable(false);

        JButton bt = new JButton(NEW_FILE_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(NEW_FILE));
        jtb.add(bt);

        bt = new JButton(SAVE_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(SAVE));
        jtb.add(bt);

        jtb.addSeparator();

        bt = new JButton(A_TO_B_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(A_TO_B));
        jtb.add(bt);

        bt = new JButton(R2BMP_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(R2BMP));
        jtb.add(bt);

        jtb.addSeparator();

        bt = new JButton(TO_GRAY_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(TO_GRAY));
        jtb.add(bt);

        bt = new JButton(NEGATIVE_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(NEGATIVE));
        jtb.add(bt);

        bt = new JButton(SHARPNESS_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(SHARPNESS));
        jtb.add(bt);

        bt = new JButton(EMBOSSING_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(EMBOSSING));
        jtb.add(bt);

        bt = new JButton(WATERCOLOR_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(WATERCOLOR));
        jtb.add(bt);

        bt = new JButton(GAMMA_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(GAMMA));
        jtb.add(bt);

        bt = new JButton(BLUR_BUTTON);
        bt.addActionListener((e) -> listener.onButtonClick(BLUR));
        jtb.add(bt);
    }
}
