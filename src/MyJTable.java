import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class MyJTable extends JTable {

    public MyJTable(String[] tableHead,Object[][] data,int[] width)
    {
        super(data,tableHead);

        //设置每个格子大小
        TableColumn column = null;
        for (int i = 0; i < tableHead.length; i++) {
            column = this.getColumnModel().getColumn(i);
            column.setPreferredWidth(width[i]);
            }

        //使表格中字体居中
        DefaultTableCellRenderer dc=new DefaultTableCellRenderer();
        dc.setHorizontalAlignment(JLabel.CENTER);
        this.setDefaultRenderer(Object.class, dc);
    }
}
