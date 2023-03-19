import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyDialog extends JDialog implements ActionListener {

    JButton okBut;

    JButton confirmBut;
    JButton cancleBut;

    String tableName;
    OperationType operationType;
    int index;

    boolean[] tableBool = null;
    ArrayList<Object> jTextFields = new ArrayList<>();
    public  MyDialog(String content,JFrame owner)
    {
        super(owner);
        Font font = new Font("Ms Song",Font.BOLD,15);
        initDialog(content,font);

    }
    public  MyDialog(String content,JDialog owner)
    {
        super(owner);
        Font font = new Font("Ms Song",Font.BOLD,15);
        initDialog(content,font);

    }
    public  MyDialog(String content,JFrame owner,int size)
    {
        super(owner);
        Font font = new Font("Ms Song",Font.BOLD,size);
        initDialog(content,font);

    }

    public  MyDialog(String content,JDialog owner,int size)
    {
        super(owner);
        Font font = new Font("Ms Song",Font.BOLD,size);
        initDialog(content,font);

    }

    public MyDialog(JFrame owner,String[] tags,String tableName,OperationType operationType,boolean[] tableBool)
    {
        super(owner);
        this.operationType = operationType;
        this.tableBool = tableBool;
        initDialog2(tags,tableName,tableBool);
    }

    public MyDialog(JFrame owner,String[] tags,String tableName,OperationType operationType,int element,boolean[] tableBool)
    {
        super(owner);
        index = element;
        this.tableBool = tableBool;
        this.operationType = operationType;
        initDialog2(tags,tableName,tableBool);
    }

    private void initDialog(String content,Font font)
    {
        this.getContentPane().setLayout(null);
        this.setVisible(true);
        this.setSize(300,180);
        this.setLocationRelativeTo(null);
        JLabel jLabel = new JLabel(content);
        jLabel.setBounds(10,20,250,50);
        jLabel.setFont(font);
        this.getContentPane().add(jLabel);

        okBut = new JButton("ok");
        okBut.setFocusPainted(false);
        okBut.setFont(font);
        okBut.addActionListener(this);
        okBut.setBounds(100,95,100,40);
        this.getContentPane().add(okBut);
    }

    private void initDialog2(String[] tags,String tableName,boolean[] tableBool)
    {
        this.tableName = tableName;
        this.setLayout(null);
        this.setSize(200,80*tags.length + 100);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        Dimension dim = new Dimension(150,30);

        int i = 0;
        for( i = 0 ; i < tags.length; i++)
        {
            JLabel tag = new JLabel(tags[i]);
            tag.setBounds(25,80 * (i ),150,30);

            if(tableBool == null || tableBool[i] == false) {
                JTextField jTextField = new JTextField();
                jTextField.setPreferredSize(dim);
                jTextField.setBounds(25, 40 * (i + 1) + 40 * i, 150, 30);
                jTextFields.add(jTextField);
                this.getContentPane().add(jTextField);
            }
            else {
                if(ConnectMySQL.instance.operatorBool == tableBool || (ConnectMySQL.instance.famaccountBool == tableBool && i == 3) ||(ConnectMySQL.instance.fammenberBool == tableBool && i == 3 ))
                {
                    JComboBox<String> jcb = new JComboBox<>();
                    jcb.setPreferredSize(dim);
                    ArrayList<String> list = ConnectMySQL.instance.getTypeInfo("address");
                    for(int s = 0;s < list.size();s++)
                    {
                        jcb.addItem(list.get(s));
                    }
                    jcb.setBounds(25, 40 * (i + 1) + 40 * i, 150, 30);
                    jTextFields.add(jcb);
                    this.getContentPane().add(jcb);
                } else if (ConnectMySQL.instance.famaccountBool == tableBool && i == 7) {
                    JComboBox<String> jcb = new JComboBox<>();
                    jcb.setPreferredSize(dim);
                    ArrayList<String> list = ConnectMySQL.instance.getTypeInfo("poortype");
                    for(int s = 0;s < list.size();s++)
                    {
                        jcb.addItem(list.get(s));
                    }
                    jcb.setBounds(25, 40 * (i + 1) + 40 * i, 150, 30);
                    jTextFields.add(jcb);
                    this.getContentPane().add(jcb);
                }
            }
            this.getContentPane().add(tag);

        }

        if(operationType == OperationType.Alter2)
        {
            Object[] objects = ConnectMySQL.instance.getInfo(tableName,index);
            for(int x = 0;x < jTextFields.size();x++)
            {
                if(tableBool == null || tableBool[x] == false) {
                    ((JTextField)jTextFields.get(x)).setText(objects[x].toString());
                }
                else {
                    if(ConnectMySQL.instance.operatorBool == tableBool || (ConnectMySQL.instance.famaccountBool == tableBool && x == 3)) {
                        ArrayList<String> list = ConnectMySQL.instance.getTypeInfo("address");
                        for (int s = 0;s < list.size();s++)
                        {
                            if(list.get(s) .equals(objects[x].toString()))
                            {
                                ((JComboBox<String>)jTextFields.get(x)).setSelectedIndex(s);
                            }
                        }
                    } else if (ConnectMySQL.instance.famaccountBool == tableBool && x == 7) {
                        ArrayList<String> list = ConnectMySQL.instance.getTypeInfo("poortype");
                        for (int s = 0;s < list.size();s++)
                        {
                            if(list.get(s) .equals(objects[x].toString()))
                            {
                                ((JComboBox<String>)jTextFields.get(x)).setSelectedIndex(s);
                            }
                        }
                    }
                }
            }
        }

        confirmBut = new JButton("Confirm");
        confirmBut.setFocusPainted(false);
        confirmBut.addActionListener(this);
        confirmBut.setBounds(10,80 * i + 10,80,40);
        this.getContentPane().add(confirmBut);

        cancleBut = new JButton("Cancel");
        cancleBut.setFocusPainted(false);
        cancleBut.addActionListener(this);
        cancleBut.setBounds(100,80 * i + 10,80,40);
        this.getContentPane().add(cancleBut);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okBut) {
            this.dispose();
            if(this.getOwner().getOwner() != null)
            {
                this.getOwner().dispose();
            }
            //if(this.getOwner().getOwner().getOwner() != null)
            //{
              //  this.getOwner().getOwner().dispose();
            //}
        }
        else if (e.getSource() == confirmBut)
        {
            ArrayList<String> element = new ArrayList<>();
            for(int i = 0; i < jTextFields.size(); i++)
            {
                if(tableBool == null || tableBool[i] == false) {
                   element.add (((JTextField)jTextFields.get(i)).getText());
                }
                else {
                    if (ConnectMySQL.instance.operatorBool == tableBool || (ConnectMySQL.instance.famaccountBool == tableBool && i == 3)||(ConnectMySQL.instance.fammenberBool == tableBool && i == 3 )) {
                        ArrayList<Integer> ids = ConnectMySQL.instance.getIdInfo("address");
                        element.add(String.valueOf( ids.get( ( (JComboBox<String>) jTextFields.get(i)).getSelectedIndex() ) ) );
                    } else if (ConnectMySQL.instance.famaccountBool == tableBool && i == 7) {
                        ArrayList<Integer> ids = ConnectMySQL.instance.getIdInfo("poortype");
                        element.add(String.valueOf( ids.get( ( (JComboBox<String>) jTextFields.get(i)).getSelectedIndex() ) ) );
                    }
                }
            }
            if(operationType == OperationType.Alter1)
            {
                int index = Integer.parseInt(element.get(0));

                if(null == ConnectMySQL.instance.getInfo(tableName,index))
                {
                    MyDialog myDialog = new MyDialog("The id does not exist",(JFrame) this.getOwner());
                }else {
                    switch (tableName) {
                        case "poortype" -> {
                            MyDialog myDialog = new MyDialog((JFrame) this.getOwner(), new String[]{"Type", "Des"}, "poortype", OperationType.Alter2, index,tableBool);

                            break;
                        }
                        case "address" -> {
                            MyDialog myDialog = new MyDialog((JFrame) this.getOwner(), new String[]{"Province", "City", "County", "Town", "Village"}, "address", OperationType.Alter2, index,tableBool);
                            break;
                        }
                        case "operator" -> {
                            MyDialog myDialog = new MyDialog((JFrame) this.getOwner(), new String[]{"Account", "Password","Name","Identity Sard","Sex","Address","Phone num","Work Address"}, "operator", OperationType.Alter2, index,tableBool);
                            break;
                        }
                        case "famaccount"->{
                            new MyDialog((JFrame) this.getOwner(),new String[]{"name","identity card","sex","address","phone num","house area","member count","poor type"},"famaccount",OperationType.Alter2,index,tableBool);
                            break;
                        }
                        case "fammenber"->{
                            new MyDialog((JFrame) this.getOwner(),new String[]{"name","identity card","sex","address","phone num","work unit"},"fammenber",OperationType.Alter2,index,tableBool);
                            break;
                        }
                    }
                }
                this.dispose();
            } else if (operationType == OperationType.Alter2) {

                new YesNoDialog(element, tableName, this, operationType,index);
            } else {
                new YesNoDialog(element, tableName, this, operationType);
            }
        }else if(e.getSource() == cancleBut)
        {
            this.dispose();
        }
    }
}
