import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Imag.*;

import static java.awt.Color.red;

public class LoginWindow extends JFrame  implements ActionListener {

    public JLabel lableofTitle;
    public JLabel backGroundImage;

    public MyJmenu jMenuBar;

    private JButton loginBut;

    private  JButton signupBut;
    private JComboBox<String> jcb;

    private  JTextField accountFeield;
    private  JPasswordField passwordField;
    public LoginWindow()
    {
        initJfram();

        initLableofTitle();

        jMenuBar = new MyJmenu(this);//初始化菜单



        initJButton();//初始化按钮
        initLoginpanel();//初始化账户密码框

        initBackGroundImage();//初始化背景
        this.setVisible(true);
    }

    private void initLoginpanel() {
        //设置下拉框
        jcb = new JComboBox<String>();
        Dimension dim = new Dimension(150,30);
        jcb.setPreferredSize(dim);

        jcb.addItem("Administrator");
        jcb.addItem("Operator");
        jcb.setBounds(350,115,150,30);
        jcb.addActionListener(this);

        //身份标签
        Font font = new Font("Ms Song",Font.PLAIN,15);
        JLabel statusText = new JLabel("Status");
        statusText.setBounds(300,115,50,30);
        statusText.setFont(font);

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

        accountFeield.setText("root");
        passwordField.setText("123456");

        this.getContentPane().add(accountFeield);
        this.getContentPane().add(passwordField);
        this.getContentPane().add(accountText);
        this.getContentPane().add(passwordText);
        this.getContentPane().add(statusText);
        this.getContentPane().add(jcb);
    }

    private void initJButton() {
         loginBut = new JButton("Log In");
         signupBut = new JButton("Sign Up");

        loginBut.setFocusPainted(false);
        signupBut.setFocusPainted(false);

        Font font = new Font("Ms Song",Font.BOLD,15);
        loginBut.setFont(font);
        signupBut.setFont(font);

        loginBut.addActionListener(this);
        signupBut.addActionListener(this);

        loginBut.setBounds(260,300,120,40);
        signupBut.setBounds(420,300,120,40);
        this.getContentPane().add(loginBut);
        this.getContentPane().add(signupBut);
    }



    public void initBackGroundImage() {
        backGroundImage = new JLabel(Img.instance.GetMainPanelImage());
        backGroundImage.setBounds(0,0,800,450);

        this.getContentPane().add(backGroundImage);
    }

    private void initLableofTitle() {
        lableofTitle = new JLabel("RURAL DEMOGRAPHIC SYSTEM");
        lableofTitle.setFont(new Font("Ms Song",Font.BOLD,30));//设置字体，样式，字体大小
        lableofTitle.setBounds(160,0,480,100);

        lableofTitle.setForeground(red);
        this.getContentPane().add(lableofTitle);
    }

    public void initJfram()
    {
        this.setTitle("RuralDemographicSystem");
        this.setSize(800,450);
        this.setLocationRelativeTo(null);//使窗口居中
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setIconImage(Img.instance.GetIconImage().getImage());
        this.setResizable(false);
        this.setLayout(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jcb)
        {
            if(jcb.getSelectedIndex() == 1)
            {
                accountFeield.setText("operator01");
                passwordField.setText("1234567");
            } else if (jcb.getSelectedIndex() == 0) {
                accountFeield.setText("root");
                passwordField.setText("123456");
            }
        }

        if(e.getSource() == loginBut)
        {
            if(jcb.getSelectedIndex() == 0)
            {
                String account = accountFeield.getText();
                String password = new String(passwordField.getPassword());
                if(ConnectMySQL.instance.seleteInfo("administrator",account,password))
                {
                    new administratorFrame();
                    this.dispose();
                }

            } else if (jcb.getSelectedIndex() == 1) {
                String account = accountFeield.getText();
                String password = new String(passwordField.getPassword());
                if(ConnectMySQL.instance.seleteInfo("operator",account,password))
                {
                    new OperatorFrame();
                    this.dispose();
                }
            }


        }
        else if( e.getSource() == signupBut)
        {
            new SignUpFram();
            dispose();

        }
    }
}
