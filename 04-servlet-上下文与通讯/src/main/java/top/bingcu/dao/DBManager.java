package top.bingcu.dao;

import java.sql.*;

public class DBManager {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    private String url = null;

    public DBManager(String dbUrl, String  jdbcDriverPath){
        this.url = dbUrl;

        try {
            Class.forName(jdbcDriverPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getData(String sql, Object[] params, String dbName, String dbPassword) throws SQLException {
        this.connection = DriverManager.getConnection(url, dbName, dbPassword);
        this.statement = this.connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            this.statement.setObject(i + 1, params[i]);
        }

        rs = this.statement.executeQuery();

        return rs;
    }

    public boolean setData(String sql, Object[] params, String dbName, String dbPassword) {
        try {
            this.connection = DriverManager.getConnection(url, dbName, dbPassword);
            this.statement = this.connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                this.statement.setObject(i + 1, params[i]);
            }

            return this.statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return false;
    }

    public void close(){
        try {

            if (this.rs != null)                    this.rs.close();
            if (this.statement != null)         this.statement.close();
            if (this.connection != null)        this.connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
