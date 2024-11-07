package it.DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    public static DataBase instance;

    private Connection conn;
    public DataBase(String file) throws Exception {
        instance=this;
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:"+file);
    }
    public DataBase(String host, String dbname, String username, String password) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+dbname,username,password);
    }
}
