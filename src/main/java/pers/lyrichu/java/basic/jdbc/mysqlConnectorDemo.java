package pers.lyrichu.java.basic.jdbc;

import java.sql.*;

/*
 * jdbc 连接 mysql 数据库
 */
public class mysqlConnectorDemo {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/test"; // 数据库连接地址
    private static String userName = "root";
    private static String passwd = "hcc199521";

    public static void main(String[] args) {
        Connection connection = getConnection(driver,url,userName,passwd);
        String selectSql = "select * from my_test";
        readMysql(connection,selectSql);
        String insertSql = "insert into my_test (id,name,age,sex,degree) values(?,?,?,?,?)";
        insertMysql(connection,insertSql,3,"YuanPing",18,1,99.9f);
        updateMysql(connection);
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    private static Connection getConnection(String driver,String url,String userName,String passwd) {
        Connection connection = null;
        try {
            // 加载驱动程序
            Class.forName(driver);
            connection = DriverManager.getConnection(url,userName,passwd);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } catch (SQLException se) {
            System.err.println(se);
        }
        return connection;
    }

    /*
     * 从mysql database table 读取数据
     */
    private static void readMysql(Connection connection,String sql) {
        try {
            if(connection != null && !connection.isClosed()) {
                System.out.println("Connectted to database successfully!");
            }
            // 创建statement 类对象,执行sql语句
            Statement statement = connection.createStatement();
            // ResultSet 类,用于获取结果数据集
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("执行结果如下:");
            System.out.printf("id\tname\tage\tsex\tdegree\n");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int sex = rs.getInt("sex");
                float degree = rs.getFloat("degree"); // 分数
                System.out.printf("%s\t%s\t%d\t%d\t%.2f\n",id,name,age,sex,degree);
            }
            rs.close();
            System.out.println("read data from database successfully!");
        }catch (Exception e) {
            System.err.println(e);
        }
    }

    /*
     * 向mysql databse table 插入数据
     * @param psql:preparedStatement
     * @param id:插入id
     * @param name:插入name
     * @param age:插入age
     * @param sex:插入sex
     * @param degree:插入degree
     */
    private static void insertMysql(Connection connection,String psql,int id,String name,
                                    int age,int sex,float degree) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(psql);
            preparedStatement.setInt(1,3);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3,age);
            preparedStatement.setInt(4,sex);
            preparedStatement.setFloat(5,degree);
            // 执行更新
            preparedStatement.executeUpdate();
            System.out.println("Insert data successfully!");
        } catch (SQLException se) {
            System.err.println(se);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateMysql(Connection connection) {
        try {
            PreparedStatement psql = connection.prepareStatement(
                    "update my_test set age = ? where name = ?"
            );
            psql.setInt(1,33);
            psql.setString(2,"lyrichu");
            psql.executeUpdate();
            System.out.println("Update data successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
