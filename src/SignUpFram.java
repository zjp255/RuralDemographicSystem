import Imag.Img;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.red;

public class SignUpFram extends JFrame implements ActionListener {
    private MyJmenu myJmenu;
    public JLabel backGroundImage;
    public JLabel lableofTitle;

    public JButton signupBut;
    public JButton cancleBut;
    private  JTextField accountFeield;
    private  JPasswordField passwordField;

    public SignUpFram() {
        initJfram();

        initjMenubar();
        initLableofTitle();

        initJButton();
        initSignUppanel();
        initBackGroundImage();
        this.setVisible(true);
    }

    private void initjMenubar() {
        myJmenu = new MyJmenu(this);
    }

    private void initBackGroundImage() {
        backGroundImage = new JLabel(Img.instance.GetMainPanelImage());
        backGroundImage.setBounds(0,0,800,450);

        this.getContentPane().add(backGroundImage);
    }

    private void initJfram() {
        this.setTitle("RuralDemographicSystem");
        this.setSize(800,450);
        this.setLocationRelativeTo(null);//使窗口居中
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setIconImage(Img.instance.GetIconImage().getImage());
        this.setResizable(false);
        this.setLayout(null);
    }

    private void initLableofTitle() {
        lableofTitle = new JLabel("Registered Administrator");
        lableofTitle.setFont(new Font("Ms Song",Font.BOLD,30));//设置字体，样式，字体大小
        lableofTitle.setBounds(225,0,480,100);

        lableofTitle.setForeground(red);
        this.getContentPane().add(lableofTitle);
    }

    private void initJButton() {
        signupBut = new JButton("Sign Up");
        cancleBut = new JButton("Cancel");

        signupBut.setFocusPainted(false);
        cancleBut.setFocusPainted(false);

        Font font = new Font("Ms Song",Font.BOLD,15);
        signupBut.setFont(font);
        cancleBut.setFont(font);

        signupBut.addActionListener(this);
        cancleBut.addActionListener(this);

        signupBut.setBounds(260,300,120,40);
        cancleBut.setBounds(420,300,120,40);
        this.getContentPane().add(cancleBut);
        this.getContentPane().add(signupBut);
    }
    private void initSignUppanel() {

        Dimension dim = new Dimension(150,30);


        //身份标签
        Font font = new Font("Ms Song",Font.PLAIN,15);

        JLabel accountText =  new JLabel("Account");
        accountText.setBounds(290,160,60,30);
        accountText.setFont(font);

        JLabel passwordText = new JLabel("Password");
        passwordText.setBounds(290,205,60,30);
        accountText.setFont(font);

        accountFeield = new JTextField();
        accountFeield.setPreferredSize(dim);
        accountFeield.setBounds(350,160,150,30);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(dim);
        passwordField.setBounds(350,205,150,30);
        passwordField.setEchoChar('*');

        this.getContentPane().add(accountFeield);
        this.getContentPane().add(passwordField);
        this.getContentPane().add(accountText);
        this.getContentPane().add(passwordText);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancleBut)
        {
            new LoginWindow();
            this.dispose();
        }
        else if(e.getSource() == signupBut)
        {
            String account = accountFeield.getText();
            String password = new String(passwordField.getPassword());
            if(account.equals("") || password.equals(""))
            {
                MyDialog dialog = new MyDialog("Account or password cannot be empty",this,13);
            }
            else {
                if (ConnectMySQL.instance.seleteInfo("administrator", account, password)) {
                    MyDialog dialog = new MyDialog("The account already exists", this);
                } else {
                    MyDialog dialog = new MyDialog("Signup was successful", this);
                    String sql = "insert into administrator values(null,\"" + account + "\",\"" + password + "\")";
                    ConnectMySQL.instance.connect(sql);


                }
            }
        }
    }
}
