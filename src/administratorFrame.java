import Imag.Img;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

enum TableType{
    PovertyType,
    Address,
    Operator,
    Famaccount,
    FamMember
}

enum OperationType{
    Add,
    Delete,
    Alter1,
    Alter2
}

public class administratorFrame extends JFrame implements ActionListener {
    private JButton[] jButtons;
    private TableType tableType = TableType.PovertyType;
    private JScrollPane jScrollPane = null;
    public administratorFrame()
    {
        initFrame();
        new MyJmenu(this);
        this.setVisible(true);
        initTable();

    }
    private void initTable()
    {
        Object[][] objects = ConnectMySQL.instance.getInfo("poortype",tableType);
        MyJTable jTable = new MyJTable(new String[]{"Id","Type","Des"},objects,new int[]{50,150,580});
        jScrollPane = new JScrollPane(jTable);
        jTable.setFillsViewportHeight(true);
        jScrollPane.setBounds(4,50,780,335);

        this.getContentPane().add(jScrollPane);
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
        initBut(butPanel,new String[]{"Poverty Type","Address","Operator","Add","Delete","Alter","Refresh"});
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

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < jButtons.length; i++)
        {
            if(e.getSource() == jButtons[i])
            {

                butFunc(i);
            }
        }
    }

    private void butFunc( int i) {
        switch (i)
        {
            case 0:
                showPovertyType();
                break;
            case 1:
                showAdress();
                break;
            case 2:
                showOperator();
                break;
            case 3:
                myadd();
                break;
            case 4:
                mydelete();
                break;
            case 5:
                myalter();
                break;
            case 6:
                refresh();
                break;
        }

    }

    private void refresh() {
        if(tableType == TableType.PovertyType)
        {
            showPovertyType();
        }else if(tableType == TableType.Address)
        {
            showAdress();
        } else if (tableType == TableType.Operator) {
            showOperator();
        }
    }

    private void myalter() {
        switch (tableType)
        {
            case PovertyType -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"poortype",OperationType.Alter1,null);
                break;
            }
            case Address -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"address",OperationType.Alter1,null);
                break;
            }
            case Operator -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"operator",OperationType.Alter1,ConnectMySQL.instance.operatorBool);
                break;
            }
        }
    }

    private void mydelete() {
        switch (tableType)
        {
            case PovertyType -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"poortype",OperationType.Delete,null);

                break;
            }
            case Address -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"address",OperationType.Delete,null);
                break;
            }
            case Operator -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"id"},"operator",OperationType.Delete,ConnectMySQL.instance.operatorBool);
                break;
            }
        }
    }

    private void myadd() {
        switch (tableType)
        {
            case PovertyType -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"Type","Des"},"poortype",OperationType.Add,null);

                break;
            }
            case Address -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"Province","City","County","Town","Village"},"address",OperationType.Add,null);
                break;
            }
            case Operator -> {
                MyDialog myDialog = new MyDialog(this,new String[]{"Account","Password","Name","Identity Sard","Sex","Address","Phone num","Work Address"},"operator",OperationType.Add,ConnectMySQL.instance.operatorBool);
                break;
            }
        }
    }

    private void showOperator() {
        tableType = TableType.Operator;
        showTable("operator",new String[]{"id","account","password","name","identity card","sex","address","phone num","work address"},new int[]{50,90,90,70,110,40,120,90,120});
    }

    private void showAdress() {
        tableType = TableType.Address;
        showTable("address",new String[]{"Id","province","city","county","town","village"},new int[]{50,120,120,120,120,120,120});
    }

    private void showPovertyType() {
        tableType = TableType.PovertyType;
        showTable("poortype",new String[]{"Id","Type","Des"},new int[]{50,150,580});
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
}
