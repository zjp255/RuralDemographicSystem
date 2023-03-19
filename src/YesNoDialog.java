import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class YesNoDialog extends JDialog implements ActionListener {

    JButton yesBut;
    JButton noBut;

    String sql;

    ArrayList<String> strings;
    String tableName;
    OperationType operationType;
    int id;
    public YesNoDialog(ArrayList<String> strings, String tableName, MyDialog owner,OperationType operationType)
    {
        super(owner);
        this.strings = strings;
        this.operationType =operationType;
        this.tableName = tableName;
        Font font = new Font("Ms Song",Font.BOLD,15);
        initDialog(font);
        dealWithSql(strings,tableName,operationType);
    }

    public YesNoDialog(ArrayList<String> strings, String tableName, MyDialog owner,OperationType operationType,int id)
    {
        super(owner);
        this.id = id;
        this.operationType =operationType;
        this.strings = strings;
        this.tableName = tableName;
        Font font = new Font("Ms Song",Font.BOLD,15);
        initDialog(font);
        dealWithSql(strings,tableName,operationType);
    }

    private void dealWithSql(ArrayList<String> strings, String tableName,OperationType operationType) {
        switch (operationType)
        {
            case Add -> {
                sql = ConnectMySQL.instance.insertSql(tableName,strings);
                break;
            }
            case Delete -> {
               sql = ConnectMySQL.instance.deleteSql(tableName,strings);
                break;
            }
            case Alter2 -> {
                sql = ConnectMySQL.instance.alterSql(tableName,strings,id);
            }
        }

    }

    private void initDialog(Font font)
    {
        this.getContentPane().setLayout(null);
        this.setVisible(true);
        this.setSize(300,180);
        this.setLocationRelativeTo(null);
        JLabel jLabel = new JLabel("Are you sure?");
        jLabel.setBounds(25,20,250,50);
        jLabel.setFont(font);
        this.getContentPane().add(jLabel);

        yesBut = new JButton("Yes");
        yesBut.setFocusPainted(false);
        yesBut.setFont(font);
        yesBut.addActionListener(this);
        yesBut.setBounds(25,95,100,40);
        this.getContentPane().add(yesBut);

        noBut = new JButton("No");
        noBut.setFocusPainted(false);
        noBut.setFont(font);
        noBut.addActionListener(this);
        noBut.setBounds(175,95,100,40);
        this.getContentPane().add(noBut);
    }
    private boolean dealWithStrings()
    {
        int x = strings.size();
        if(tableName.equals("address"))
            x -= 2;
        if(tableName.equals("fammenber"))
            x -= 1;
        for (int i = 0; i < x;i++)
        {
            if(strings.get(i).equals(""))
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource() == yesBut)
            {
                if(dealWithStrings())
                {
                    MyDialog dialog = new MyDialog("Blank cannot be empty",this,13);
                }
                else {
                    if(operationType == OperationType.Delete)
                    {

                        MyDialog dialog = new MyDialog("The operation was successful", this);
                        ConnectMySQL.instance.connect(sql);
                    }else {
                        if (ConnectMySQL.instance.seleteInfo(tableName, strings) && operationType != OperationType.Alter2) {
                            MyDialog dialog = new MyDialog("The info already exists", this);
                        } else {
                            MyDialog dialog = new MyDialog("The operation was successful", this);
                            ConnectMySQL.instance.connect(sql);
                        }
                    }
                }
            } else if (e.getSource() == noBut) {
                this.dispose();
            }
    }
}
