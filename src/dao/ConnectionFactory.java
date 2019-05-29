package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection getConnection(){

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/db_livraria", "evaldo", "evaldo123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
