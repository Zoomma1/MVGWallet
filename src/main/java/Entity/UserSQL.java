package Entity;
import java.sql.*;
import java.util.ArrayList;

public class UserSQL {
    private Connection conn;
    private Statement stmt;

    public void closeConnection() throws SQLException {
        try {
            this.conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public UserSQL() {
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.1.65:3306/MVGWallet_DBB", "MVGWallet", "wGtv[Db&Wymu*ht!YmKTxwFz5T;?vQ");
            this.stmt = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /** @return a String of the builder */
    public String selectWhere(ArrayList<String> columns, String tables, String columnCondition, String columnResult) throws SQLException {
        String queryTable = String.join(",", columns);
        String sql = String.format("SELECT %s FROM %s WHERE %s = '%s';", queryTable, tables, columnCondition, columnResult);
        ResultSet res = stmt.executeQuery(sql);
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuilder resultBuilder = new StringBuilder();
        while (res.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                int type = metaData.getColumnType(i);
                switch (type) {
                    case Types.INTEGER:
                        int intValue = res.getInt(i);
                        resultBuilder.append(columnName).append(": ").append(intValue);
                        break;
                    case Types.VARCHAR:
                        String stringValue = res.getString(i);
                        resultBuilder.append(columnName).append(": ").append(stringValue);
                        break;
                    // Vous pouvez ajouter plus de types ici
                    default:
                        Object objValue = res.getObject(i);
                        System.out.print(columnName + ": " + objValue);
                        resultBuilder.append(columnName).append(": ").append(objValue);
                        break;
                }
                if (i < columnCount) {
                    resultBuilder.append(", ");
                }
                resultBuilder.append("\n");
            }
        }
        return resultBuilder.toString();
    }
    public String select(ArrayList<String> columns, String tables) throws SQLException {
        String queryTable = String.join(",", columns);
        String sql = String.format("SELECT %s FROM %s;", queryTable, tables);
        ResultSet res = stmt.executeQuery(sql);
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuilder resultBuilder = new StringBuilder();
        while (res.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                int type = metaData.getColumnType(i);
                switch (type) {
                    case Types.INTEGER:
                        int intValue = res.getInt(i);
                        resultBuilder.append(columnName).append(": ").append(intValue);
                        break;
                    case Types.VARCHAR:
                        String stringValue = res.getString(i);
                        resultBuilder.append(columnName).append(": ").append(stringValue);
                        break;
                    default:
                        Object objValue = res.getObject(i);
                        System.out.print(columnName + ": " + objValue);
                        resultBuilder.append(columnName).append(": ").append(objValue);
                        break;
                }
                if (i < columnCount) {
                    resultBuilder.append(", ");
                }
                resultBuilder.append("\n");
            }
        }
        return resultBuilder.toString();
    }

    public UserSQL insert(int idWallet, String identifiant, String password, String salt, String email, Timestamp lastConnection, boolean stayConnected) throws SQLException {
        String sql = "INSERT INTO User (id_wallet, identifiant, password, salt, email, last_connection, stay_connected) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idWallet);
            pstmt.setString(2, identifiant);
            pstmt.setString(3, password);
            pstmt.setString(4, salt);
            pstmt.setString(5, email);
            pstmt.setTimestamp(6, lastConnection);
            pstmt.setBoolean(7, stayConnected);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }
}
