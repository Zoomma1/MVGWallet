package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockRepository {

    private String connectionString; // Your database connection string
    private String username; // Your database username
    private String password; // Your database password

    public StockRepository(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }
}
