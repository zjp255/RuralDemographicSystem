package Imag;
import javax.swing.*;
import java.io.IOException;

public  class Img {
    public static Img instance;
    private ImageIcon iconImage;
    public ImageIcon GetIconImage()
    {
        return iconImage;
    }

    private ImageIcon mainPanelImage;
    public ImageIcon GetMainPanelImage()
    {
        return mainPanelImage;
    }
    public Img() throws IOException {
        instance = this;

        iconImage = new ImageIcon("src/Imag/TitleIcon.jpeg");
        mainPanelImage = new ImageIcon("src/Imag/MainPanel.jpeg");
    }

}
