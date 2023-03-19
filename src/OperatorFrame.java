import Imag.Img;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorFrame extends JFrame implements ActionListener {
    private JButton[] jButtons;
    private TableType tableType = TableType.PovertyType;
    private JScrollPane jScrollPane = null;
    public static OperatorFrame instance;

    JButton confirmBut;
    JButton cancleBut;
    JTextField jTextField;
    JDialog jd;
    public int familyAccountId;
    public OperatorFrame()
    {
        instance = this;
        initFrame();
        new MyJmenu(this);
        this.setVisible(true);
        tableType = TableType.Famaccount;
        showTableOr("famaccount",new String[]{"id","name","identity card","sex","address","phone num","house area","member count","poor type"},new int[]{25,60,90,50,190,120,75,90,80});

    }
    private void initFrame()
    {
        this.setTitle("RuralDemographicSystem");
        this.setSize(800,450);
        this.setLocationRelativeTo(null);//使窗口居中
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setIconImage(Img.instance.GetIconImage().getImage());
        this.setResizable(false);
        JPanel butPanel = new JPanel();
        butPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        butPanel.setBounds(0,0,800,50);
        initBut(butPanel,new String[]{"Family Account","Family Members","Add","Delete","Alter","Refresh"});
        this.getContentPane().add(butPanel);
        this.setLayout(null);
    }

    private void initBut(JPanel butPanel,String[] strings) {
        jButtons = new JButton[strings.length];
        for(int i = 0; i < strings.length;i++)
        {
            JButton jButton = new JButton(strings[i]);
            jButton.setSize(120,40);
            jButton.setFocusPainted(false);
            jButton.addActionListener(this);
            butPanel.add(jButton);
            jButtons[i] =jButton;
        }

    }

    private void initFammenberDialog()
    {
        jd = new JDialog(this);
        jd.setLayout(null);
        jd.setSize(200,80 + 100);
        jd.setVisible(true);
        jd.setLocationRelativeTo(null);

        JLabel tag = new JLabel("Family Account Id");
        tag.setBounds(25,0,150,30);
        tag.setVisible(true);
        jd.add(tag);

        jTextField = new JTextField();
        jTextField.setBounds(25, 40 , 150, 30);
        jd.getContentPane().add(jTextField);

        confirmBut = new JButton("Confirm");
        confirmBut.setFocusPainted(false);
        confirmBut.addActionListener(this);
        confirmBut.setBounds(10,80  + 10,80,40);
        jd.getContentPane().add(confirmBut);

        cancleBut = new JButton("Cancel");
        cancleBut.setFocusPainted(false);
        cancleBut.addActionListener(this);
        cancleBut.setBounds(100,80 + 10,80,40);
        jd.getContentPane().add(cancleBut);
    }

    private void butFunc( int i) {
        switch (i)
        {
            case 0:
                showFamaccount();
                break;
            case 1:
                initFammenberDialog();
                break;
            case 2:
                myadd();
                break;
            case 3:
                mydelete();
                break;
            case 4:
                myalter();
                break;
            case 5:
                refresh();
                break;
        }

    }

    private void refresh() {
        if(tableType == TableType.Famaccount)
        {
            showFamaccount();
        }else if(tableType == TableType.FamMember)
        {
            showFamMembers();
        }
    }

    private void myalter() {
        switch (tableType)
        {
            case Famaccount -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"famaccount",OperationType.Alter1,ConnectMySQL.instance.famaccountBool);
                break;
            }
            case FamMember -> {
                MyDialog myDialog = new MyDialog(this, new String[]{"id"}, "fammenber", OperationType.Alter1, ConnectMySQL.instance.fammenberBool);
                break;
            }
        }
    }

    private void mydelete() {
        switch (tableType)
        {
            case Famaccount -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"famaccount",OperationType.Delete,null);

                break;
            }
            case FamMember -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"fammenber",OperationType.Delete,null);
                break;
            }
        }
    }

    private void myadd() {
        switch (tableType)
        {
            case Famaccount -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"name","identity card","sex","address","phone num","house area","member count","poor type"},"famaccount",OperationType.Add,ConnectMySQL.instance.famaccountBool);

                break;
            }
            case FamMember -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"name","identity card","sex","address","phone num","work unit"},"fammenber",OperationType.Add,ConnectMySQL.instance.fammenberBool);
                break;
            }
        }
    }

    private void showFamaccount() {
        tableType = TableType.Famaccount;
        showTable("famaccount",new String[]{"id","name","identity card","sex","address","phone num","house area","member count","poor type"},new int[]{25,60,90,50,190,120,75,90,80});
    }

    private void showFamMembers()
    {
        tableType = TableType.FamMember;
        showTable("fammenber",new String[]{"id","name","identity card","sex","address","phone num","work unit"},new int[]{50,70,150,50,160,150,150},familyAccountId);
    }


    private void showTable(String tableName,String[] tableHead,int[] width)
    {
        this.getContentPane().remove(jScrollPane);
        Object[][] objects = ConnectMySQL.instance.getInfo(tableName,tableType);
        MyJTable jTable = new MyJTable(tableHead,objects,width);
        jScrollPane = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        jScrollPane.setBounds(4,50,780,335);

        this.getContentPane().add(jScrollPane);
    }

    private void showTable(String tableName,String[] tableHead,int[] width,int familyAccountId)
    {
        this.getContentPane().remove(jScrollPane);
        Object[][] objects = ConnectMySQL.instance.getInfo(tableName,tableType,familyAccountId);
        MyJTable jTable = new MyJTable(tableHead,objects,width);
        jScrollPane = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        jScrollPane.setBounds(4,50,780,335);
        jd.dispose();
        this.getContentPane().add(jScrollPane);
    }

    private void showTableOr(String tableName,String[] tableHead,int[] width)
    {
        Object[][] objects = ConnectMySQL.instance.getInfo(tableName,tableType);
        MyJTable jTable = new MyJTable(tableHead,objects,width);
        jScrollPane = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        jScrollPane.setBounds(4,50,780,335);

        this.getContentPane().add(jScrollPane);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < jButtons.length; i++)
        {
            if(e.getSource() == jButtons[i])
            {

                butFunc(i);
            } else if (e.getSource() == confirmBut) {
               familyAccountId = Integer.parseInt(jTextField.getText());
               showFamMembers();
            } else if (e.getSource() ==cancleBut) {
                jd.dispose();
            }
        }
    }
}
