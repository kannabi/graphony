package View;

import Entities.ToolbarButton;
import Filters.*;;
import Interfaces.FilterViewInterface;
import Interfaces.RequiresEDT;
import Presenter.FilterPresenter;
import View.Dialogs.GammaDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import static Entities.Const.DEFAULT_PANEL_HEIGHT;
import static Entities.Const.DEFAULT_PANEL_WIDTH;

/**
 * Created by kannabi on 24/03/2017.
 */


public class FilterView  extends JFrame implements FilterViewInterface {
    private FilterPresenter presenter;
    private Toolbar toolbar;
//    private volatile FilterView view = this;

    LinkedList<Window> windows = new LinkedList<>();

    @Override
    public void setPresenter(FilterPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void initUI(){
        setTitle("Filter");
        this.setResizable(false);
        BorderLayout layout = new BorderLayout();
        layout.setHgap(15);

        JPanel cp = new JPanel(layout);

        toolbar = new Toolbar();
        toolbar.setOnButtonClickListener(this::toolbarButtonProcessor);
        cp.add(new Toolbar(), BorderLayout.NORTH);
        setContentPane(cp);

        Window aWindow = new Window();
        Window bWindow = new Window();
        Window cWindow = new Window();

        cp.add(aWindow, BorderLayout.LINE_START);
        cp.add(bWindow, BorderLayout.CENTER);
        cp.add(cWindow, BorderLayout.LINE_END);

        windows.add(aWindow);
        windows.add(bWindow);
        windows.add(cWindow);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();
        this.setVisible(true);
    }

    private void toolbarButtonProcessor(ToolbarButton btn){
        switch (btn){
            case NEW_FILE:
                loadNewFile();
                break;
            case A_TO_B:
                moveAtoB();
                break;
            case R2BMP:
                r2bmp();
                break;
            case TO_GRAY:
                setResult(new Gray().transform(getWorkingBuffer()));
                break;
            case NEGATIVE:
                setResult(new Negative().transform(getWorkingBuffer()));
                break;
            case SHARPNESS:
                setResult(new Sharpness().transform(getWorkingBuffer()));
                break;
            case EMBOSSING:
                setResult(new Embossing().transform(getWorkingBuffer()));
                break;
            case WATERCOLOR:
                setResult(new Watercolorization().transform(getWorkingBuffer()));
                break;
            case GAMMA:
                GammaDialog dialog = new GammaDialog();
                if(dialog.getAnswer())
                    setResult(new Gamma(dialog.getResult()).transform(getWorkingBuffer()));
                break;
            case BLUR:
                setResult(new Blur().transform(getWorkingBuffer()));
                break;
        }
    }

    @RequiresEDT
    private void setResult(BufferedImage image){
        windows.get(2).setBuffer(image);
        windows.get(2).repaint();
    }

    @RequiresEDT
    private void r2bmp(){
        windows.get(1).setBuffer(windows.get(2).getBuffer());
        windows.get(1).repaint();
    }

    @RequiresEDT
    private void setNewFile(final BufferedImage image){
            windows.get(0).setBuffer(image);
            windows.get(0).repaint();
    }

    @RequiresEDT
    private void moveAtoB(){
        windows.get(1).setBuffer(windows.get(0).getBuffer());
        windows.get(1).repaint();
    }

    private void loadNewFile(){
        JFileChooser fileChooser = new JFileChooser("Data");
        int answer = fileChooser.showOpenDialog(this);

        if (answer == JFileChooser.APPROVE_OPTION)
            new Thread(() -> {
                try {
                    BufferedImage image = ImageIO.read(fileChooser.getSelectedFile());
                    int height;
                    int width;

                    if (image.getHeight() > image.getWidth()) {
                        height = DEFAULT_PANEL_HEIGHT;
                        width = -1;
                    } else {
                        height = -1;
                        width = DEFAULT_PANEL_WIDTH;
                    }

                    BufferedImage scaledImage = convertToBufferedImage(image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING));

                    setNewFile(scaledImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
    }

    private BufferedImage convertToBufferedImage(Image image) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    private BufferedImage getWorkingBuffer(){
        return windows.get(1).getBuffer();
    }
}