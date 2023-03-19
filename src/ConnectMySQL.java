import java.sql.*;
import java.util.*;

public class ConnectMySQL {
    public static ConnectMySQL instance;
    private final String DATABASE ="rdsdata";
    private final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=GMT";
    private final String USERNAME = "root";
    private final String PASSWORD = "123456";
    Connection connection;
    Statement statement;
    public boolean[] operatorBool = new boolean[]{false,false,false,false,false,false,false,true};
    public boolean[] famaccountBool = new boolean[]{false,false,false,true,false,false,false,true};
    public boolean[] fammenberBool = new boolean[]{false,false,false,true,false,false};
    public  ConnectMySQL()
    {
        instance = this;
    }
    public void connect(String sql)
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            statement = connection.createStatement();//获取statement的值
            System.out.println(sql);
            boolean flag = statement.execute(sql);//执行sql语句，执行成功返回true，失败返回false
            if(flag)
            {
                System.out.println("执行成功");
            }else{
                System.out.println("执行失败");
            }


        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean seleteInfo(String tableName, String account, String password)
    {
        String dql = "select * from " + tableName;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            while (rs.next())
            {
                if(  rs.getString("account").equals(account) & rs.getString("password").equals(password))
                {
                    return  true;
                }

            }


        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean seleteInfo(String tableName, ArrayList<String> strings)
    {
        String dql = "select * from " + tableName;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            while (rs.next())
            {
                if(tableName.equals("poortype")) {
                    if (rs.getString("type").equals(strings.get(0))) {
                        return true;
                    }
                } else if (tableName.equals("address")) {
                    if(rs.getString("province").equals(strings.get(0)) &&
                            rs.getString("city").equals(strings.get(1)) &&
                            rs.getString("county").equals(strings.get(2))&&
                            rs.getString("town").equals(strings.get(3))&&
                            rs.getString("village").equals(strings.get(4)))
                    {
                        return true;
                    }
                } else if (tableName.equals("operator")) {
                    if(
                            rs.getString("account").equals(strings.get(0)) ||
                            rs.getString("idnum").equals(strings.get(3)) ||
                            rs.getString("phonenum").equals(strings.get(6)))
                    {
                        return true;
                    }

                } else if (tableName.equals("famaccount")) {
                    if(

                            rs.getString("idnum").equals(strings.get(1)) ||
                            rs.getString("phonenum").equals(strings.get(4)))
                    {
                        return true;
                    }
                }

            }


        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object[][] getInfo(String tableName,TableType tableType) {
        String dql = "select * from " + tableName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            ArrayList<Object[]> list = new ArrayList<>();
            while (rs.next()) {
                switch (tableType)
                {
                    case PovertyType -> {
                        list.add( new Object[]{rs.getInt("id"),rs.getString("type"),rs.getString("des")});
                        break;
                    }
                    case Address -> {
                        list.add( new Object[]{rs.getInt("id"),rs.getString("province"),rs.getString("city"),rs.getString("county"),rs.getString("town"),rs.getString("village")});
                        break;
                    }
                    case Operator -> {
                        list.add( new Object[]{rs.getInt("id"),rs.getString("account"),rs.getString("password"),rs.getString("name"),rs.getString("idnum"),rs.getString("sex"),rs.getString("address"),rs.getString("phonenum"),getInfoForIndex("address",rs.getInt("address_id"))});
                        break;
                    }
                    case Famaccount -> {
                        list.add( new Object[]{rs.getInt("id"),rs.getString("name"),rs.getString("idnum"),rs.getString("sex"),getInfoForIndex("address",rs.getInt("address_id")),rs.getString("phonenum"),rs.getInt("housearea"),rs.getInt("menbercount"),getInfoForIndex("poortype",rs.getInt("poortype_id"))});
                        break;
                    }

                }


            }
            Object[][] objects = new Object[list.size()][];
            for(int j = 0; j < list.size();j++)
            {
                objects[j] =  list.get(j);
            }
            return  objects;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object[][] getInfo(String tableName,TableType tableType,int familyAccountId) {
        String dql = "select * from " + tableName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            ArrayList<Object[]> list = new ArrayList<>();
            while (rs.next()) {
                if(rs.getInt("famaccount_id") == familyAccountId) {
                    switch (tableType) {
                        case FamMember -> {
                            list.add(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("idnum"),rs.getString("sex"),getInfoForIndex("address",rs.getInt("address_id")),rs.getString("phonenum"),rs.getString("workunit")});
                            break;
                        }

                    }
                }


            }
            Object[][] objects = new Object[list.size()][];
            for(int j = 0; j < list.size();j++)
            {
                objects[j] =  list.get(j);
            }
            return  objects;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object[] getInfo(String tableName,int i) {
        String dql = "select * from " + tableName;
            Object[] objects = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            ArrayList<Object[]> list = new ArrayList<>();
            while (rs.next()) {
                if(rs.getInt("id") == i) {
                    switch (tableName) {
                        case "poortype" -> {
                            objects = new Object[]{rs.getString("type"), rs.getString("des")};
                            break;
                        }
                        case "address" -> {
                            objects = new Object[]{rs.getString("province"), rs.getString("city"), rs.getString("county"), (rs.getString("town") == null?"":rs.getString("town")), (rs.getString("village")==null?"":rs.getString("village"))};
                            break;
                        }
                        case "operator" -> {
                            objects = new Object[]{rs.getString("account"), rs.getString("password"),rs.getString("name"),rs.getString("idnum"),rs.getString("sex"),rs.getString("address"),rs.getString("phonenum"),getInfoForIndex("address",rs.getInt("address_id"))};
                            break;
                        }
                        case "famaccount" -> {
                            objects = new Object[]{rs.getString("name"),rs.getString("idnum"),rs.getString("sex"),getInfoForIndex("address",rs.getInt("address_id")),rs.getString("phonenum"),rs.getInt("housearea"),rs.getInt("menbercount"),getInfoForIndex("poortype",rs.getInt("poortype_id"))};
                            break;
                        }
                        case "fammenber" -> {
                            objects = new Object[]{rs.getString("name"), rs.getString("idnum"), rs.getString("sex"), getInfoForIndex("address", rs.getInt("address_id")), rs.getString("phonenum"),  (rs.getString("workunit") == null?"":rs.getString("workunit"))};
                            break;
                        }

                    }
                }


            }

            return  objects;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInfoForIndex(String targetTableName,int i) {
        String dql = "select * from " + targetTableName;
        String objects = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            ArrayList<Object[]> list = new ArrayList<>();
            while (rs.next()) {
                if(rs.getInt("id") == i) {
                    switch (targetTableName) {
                        case "poortype" -> {
                            objects = rs.getString("type");
                            break;
                        }
                        case "address" -> {
                            objects = rs.getString("province")+ rs.getString("city")+rs.getString("county")+ (rs.getString("town") == null?"":rs.getString("town") )+ (rs.getString("village") == null?"": rs.getString("village"));
                            break;
                        }

                    }
                }


            }

            return  objects;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getTypeInfo(String targetTableName) {
        String dql = "select * from " + targetTableName;
        ArrayList<String> objects = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            ArrayList<Object[]> list = new ArrayList<>();
            while (rs.next()) {

                    switch (targetTableName) {
                        case "poortype" -> {
                            objects.add(rs.getString("type"));
                            break;
                        }
                        case "address" -> {
                            objects.add(rs.getString("province")+ rs.getString("city")+rs.getString("county")+ (rs.getString("town") == null?"":rs.getString("town") )+ (rs.getString("village") == null?"": rs.getString("village")));
                            break;
                        }


                }


            }

            return  objects;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> getIdInfo(String targetTableName) {
        String dql = "select * from " + targetTableName;
        ArrayList<Integer> objects = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//通过反射加载MySQL驱动类
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);//获取数据库链接
            PreparedStatement pstatement = connection.prepareStatement(dql);//获取statement的值
            ResultSet rs = pstatement.executeQuery();//执行sql语句，执行成功返回true，失败返回false
            ArrayList<Object[]> list = new ArrayList<>();
            while (rs.next()) {



                objects.add(rs.getInt("id"));


            }

            return  objects;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   public String insertSql(String tableName,ArrayList<String> strings) {
       String sql = "";
        if(tableName.equals("fammenber"))
        {
            sql = "insert into " + tableName + " values(null,"+OperatorFrame.instance.familyAccountId;
        }
        else {
             sql = "insert into " + tableName + " values(null";
        }
       for (int i = 0; i < strings.size(); i++)
       {
           sql += ",\"" + strings.get(i) +"\"";
       }
       return sql += ")";
   }

   public String deleteSql(String tableName,ArrayList<String> strings)
   {
       String sql = "delete from " + tableName + " where id = " + strings.get(0);
       return sql;
   }
    public String alterSql(String tableName,ArrayList<String> strings,int id)
    {
        String sql = "update " + tableName + " set ";
        switch (tableName)
        {
            case "poortype" -> {
               sql += "type = \"" + strings.get(0) + "\" ," + "des = \"" + strings.get(1) +"\"";
                break;
            }
            case "address" -> {
                sql += "province = \"" + strings.get(0) + "\" ," + "city = \"" + strings.get(1) + "\" ," + "county = \"" + strings.get(2) + "\" ," + "town = \"" + strings.get(3) + "\" ," + "village = \"" + strings.get(4) + "\"";
                break;
            }
            case  "operator"-> {
                sql += "account = \"" + strings.get(0) + "\" ," + "password = \"" + strings.get(1) + "\","+"name = \"" + strings.get(2) + "\" ,"+"idnum = \"" + strings.get(3) + "\" ,"+"sex = \"" + strings.get(4) + "\" ,"+"address = \"" + strings.get(5) + "\" ,"+"phonenum = \"" + strings.get(6) + "\" ," + "address_id = " + strings.get(7) + " ";
                break;
            }
            case  "famaccount"->{
                sql += "name = \"" + strings.get(0) + "\" ," + "idnum = \"" + strings.get(1) + "\","+"sex= \"" + strings.get(2) + "\" ,"+"address_id = " + strings.get(3) + " ,"+"phonenum = \"" + strings.get(4) + "\" ,"+"housearea = " + strings.get(5) + " ,"+"menbercount = " + strings.get(6) + " ," + "poortype_id = " + strings.get(7) + " ";
                break;
            }
            case "fammenber"->{
                sql += "famaccount_id = " + OperatorFrame.instance.familyAccountId + ",name = \"" + strings.get(0) + "\" ," + "idnum = \"" + strings.get(1) + "\","+"sex= \"" + strings.get(2) + "\" ,"+"address_id = " + strings.get(3) + " ,"+"phonenum = \"" + strings.get(4) + "\" ,"+"workunit = \"" + strings.get(5) + "\"";
                break;
            }
        }
        sql += " where id = " + id + ";";
        return sql;
    }


}
