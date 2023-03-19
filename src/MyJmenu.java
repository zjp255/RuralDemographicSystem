import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJmenu implements ActionListener {
    private JMenuBar jMenuBar;
    JMenuItem backSignUpFrame;
    JFrame jFrame;
    JMenuItem openItem ;
    JMenuItem closeItem ;
    public MyJmenu(JFrame jFrame)
    {
        this.jFrame = jFrame;
        initjMenubar(jFrame);

    }
    public void initjMenubar(JFrame jFrame) {
        jMenuBar = new JMenuBar();
        JMenu settingMenu = new JMenu("Setting");
        JMenu aboutMenu = new JMenu("About");

        JMenuItem aboutItem = new JMenuItem("About");

        JMenuItem englishItem = new JMenuItem("English");
        JMenuItem chineseItem = new JMenuItem("中文");
        openItem = new JMenuItem("Open");
        closeItem = new JMenuItem("Close");
        openItem.addActionListener(this);
        closeItem.addActionListener(this);


        JMenu languageMenu = new JMenu("Language");
        JMenu musicMenu = new JMenu("Music");

        languageMenu.add(englishItem);
        languageMenu.add(chineseItem);
        musicMenu.add(openItem);
        musicMenu.add(closeItem);

        backSignUpFrame =new JMenuItem("back");
        backSignUpFrame.addActionListener(this);

        aboutMenu.add(aboutItem);
        //settingMenu.add(languageMenu);
        settingMenu.add(musicMenu);
        settingMenu.add(backSignUpFrame);

        jMenuBar.add(settingMenu);
        jMenuBar.add(aboutMenu);
        jFrame.setJMenuBar(jMenuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backSignUpFrame)
        {
            jFrame.dispose();
            new LoginWindow();
        } else if (e.getSource() == openItem) {
            if(!Music.instance.clip.isRunning())
                Music.instance.playSound();
        } else if (e.getSource() == closeItem) {
            Music.instance.stop();
        }
    }
}
