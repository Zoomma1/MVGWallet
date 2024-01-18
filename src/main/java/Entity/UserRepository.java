package Entity;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.*;

public class UserRepository {
    private Connection conn;
    private Statement stmt;
    public void closeConnection() throws SQLException {
        try {
            this.conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public UserRepository() {
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://176.147.224.139:3306/MVGWallet_DBB", "MVGWallet", "wGtv[Db&Wymu*ht!YmKTxwFz5T;?vQ");
            this.stmt = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String checkRegex(String text, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
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

    public void insertToUser(String first_name, String password, String salt, String email, Timestamp lastConnection, boolean stayConnected) throws SQLException {
        String sql = "INSERT INTO users (first_name, password, salt, email, last_connection, stay_connected) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, password);
            pstmt.setString(3, salt);
            pstmt.setString(4, email);
            pstmt.setTimestamp(5, lastConnection);
            pstmt.setBoolean(6, stayConnected);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Si quantity === 0 alors l'utilisateur suit simplement le stocks */
    public UserRepository insertToStocks(double quantity, String name, int id_wallet) throws SQLException {
        String sql = "INSERT INTO stocks (quantity,name,id_wallet) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, quantity);
            pstmt.setString(2, name);
            pstmt.setInt(3, id_wallet);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            // Il est préférable de relancer l'exception plutôt que d'imprimer la stack trace.
            throw e;
        }
        return this;
    }

    public UserRepository insertToWallet(int id_user, boolean selected) throws SQLException {
        String sql = "INSERT INTO wallet (user_id, selected) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, id_user);
            pstmt.setBoolean(2, selected);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating crypto_utilisee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating crypto_utilisee failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this;
    }

    public UserRepository insertToCrypto(double quantity, String name, int id_wallet) throws SQLException
    {
        String sql = "INSERT INTO crypto (quantity,name,id_wallet) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setDouble(1, quantity);
            pstmt.setString(2, name);
            pstmt.setInt(3,id_wallet);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            // Il est préférable de relancer l'exception plutôt que d'imprimer la stack trace.
            throw e;
        }
        return this;
    }

    public static String hashingWord(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashByte = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        // Convertissez les octets du hachage en une représentation hexadécimale
        StringBuilder hexString = new StringBuilder(2 * hashByte.length);
        for (int i = 0; i < hashByte.length; i++) {
            String hex = Integer.toHexString(0xff & hashByte[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**@return true if the user is knowed */
    public boolean checkKnowUser(String user, String password){
        ArrayList<String> id = new ArrayList<>();
        id.add("id");
        try{
            String idPassword = this.selectWhere(id,"users","password",password);
            String idUser = this.selectWhere(id,"users","first_name",user);
            if (idPassword.equals(idUser)) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public int findIdByPassword(String password) throws NoSuchAlgorithmException, SQLException {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("id");
        String listId = selectWhere(columns,"users","password",Singleton.getInstance().getCurrentUser().password);
        String x = checkRegex(listId,"\\d{1,9}");
        return Integer.parseInt(x);
    }
}
