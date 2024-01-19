package Entity;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.*;

public class WalletRepository {
    private Connection conn;
    private Statement stmt;
    public void closeConnection() throws SQLException {
        try {
            this.conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public WalletRepository() {
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://176.147.224.139:3306/MVGWallet_DBB", "MVGWallet", "wGtv[Db&Wymu*ht!YmKTxwFz5T;?vQ");
            this.stmt = this.conn.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String checkRegex(String text, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    private ArrayList<String> selectWhere(ArrayList<String> columns, String tables, String columnCondition, String columnResult) throws SQLException {
        String queryTable = String.join(",", columns);
        String sql = String.format("SELECT %s FROM %s WHERE (%s) = (%s);", queryTable, tables, columnCondition, columnResult);
        ResultSet res = stmt.executeQuery(sql);
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();
        ArrayList<String> results = new ArrayList<>();
        while (res.next()) {
            StringBuilder resultBuilder = new StringBuilder();
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
                        resultBuilder.append(columnName).append(": ").append(objValue);
                        break;
                }
                if (i < columnCount) {
                    resultBuilder.append(", ");
                }
            }
            results.add(resultBuilder.toString());
        }
        return results;
    }


    private String select(ArrayList<String> columns, String tables) throws SQLException {
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

    private void insertToUser(String first_name, String password, String salt, String email, Timestamp lastConnection, boolean stayConnected) throws SQLException {
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
    private WalletRepository insertToStocks(double quantity, String name, int id_wallet) throws SQLException {
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

    private WalletRepository insertToWallet(int id_user, boolean selected) throws SQLException {
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

    private WalletRepository insertToCrypto(double quantity, String name, int id_wallet) throws SQLException
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
    private boolean checkKnowUser(String user, String password){
        ArrayList<String> id = new ArrayList<>();
        id.add("id");
        try{
            String idPassword = String.valueOf(this.selectWhere(id,"users","password",password));
            String idUser = String.valueOf(this.selectWhere(id,"users","first_name",user));
            if (idPassword.equals(idUser)) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public ArrayList<String> findById(int id) throws SQLException {
        ArrayList<String> columns = new ArrayList<>();
        columns.add(String.format("%d",id));
        return selectWhere(columns,"wallet","id",String.format("%d",id));
    }
    public ArrayList<String> findByUserId(int userId) throws SQLException {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        return selectWhere(columns,"wallet","user_id",String.format("%d",userId));
    }
    public void createWallet(int user_id, boolean selected) throws SQLException {
        insertToWallet(user_id,selected);
    }
    public ArrayList<String> findBySelectedAndId(int id) throws SQLException {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        return selectWhere(columns,"wallet","user_id, selected",String.format("'%d','1'",id));
    }
    public static ArrayList<String> selectWhereStatic(ArrayList<String> columns, String tables, String columnCondition, String columnResult) throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://176.147.224.139:3306/MVGWallet_DBB", "MVGWallet", "wGtv[Db&Wymu*ht!YmKTxwFz5T;?vQ");
        Statement stmt = conn.createStatement();
        String queryTable = String.join(",", columns);
        String sql = String.format("SELECT %s FROM %s WHERE (%s) = (%s);", queryTable, tables, columnCondition, columnResult);
        ResultSet res = stmt.executeQuery(sql);
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();
        ArrayList<String> results = new ArrayList<>();
        while (res.next()) {
            StringBuilder resultBuilder = new StringBuilder();
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
                        resultBuilder.append(columnName).append(": ").append(objValue);
                        break;
                }
                if (i < columnCount) {
                    resultBuilder.append(", ");
                }
            }
            results.add(resultBuilder.toString());
        }
        return results;
    }
    public static void insertIntoTable(String tableName, ArrayList<String> columns, ArrayList<String> values) throws SQLException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://176.147.224.139:3306²/MVGWallet_DBB", "MVGWallet", "wGtv[Db&Wymu*ht!YmKTxwFz5T;?vQ");
            StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
            for (int i = 0; i < columns.size(); i++) {
                sql.append(columns.get(i));
                if (i < columns.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") VALUES (");
            for (int i = 0; i < values.size(); i++) {
                sql.append("?");
                if (i < values.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(");");

            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < values.size(); i++) {
                pstmt.setString(i + 1, values.get(i));
            }

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }
    public static ArrayList<String> getCryptoTradeFromListWallet(ArrayList<String> wallets) throws SQLException {
        ArrayList<String> walletArray = User.checkManyRegex(wallets.toString(),"id: (\\d{1,9}), user_id: \\d+, selected: true");
        int idWallet = Integer.parseInt(User.checkRegex(walletArray.get(0),"\\d{1,9}"));
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        return WalletRepository.selectWhereStatic(columns,"crypto","id_wallet",String.format("%d",idWallet));
    }
    public static ArrayList<String> getCStocksTradeFromListWallet(ArrayList<String> wallets) throws SQLException {
        ArrayList<String> walletArray = User.checkManyRegex(wallets.toString(),"id: (\\d{1,9}), user_id: \\d+, selected: true");
        int idWallet = Integer.parseInt(User.checkRegex(walletArray.get(0),"\\d{1,9}"));
        ArrayList<String> columns = new ArrayList<>();
        columns.add("*");
        return WalletRepository.selectWhereStatic(columns,"stocks","id_wallet",String.format("%d",idWallet));
    }
    public static int updateWhereStatic(String table, String columnsValues, String columnCondition, String columnResult) throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://176.147.224.139:3306/MVGWallet_DBB", "MVGWallet", "wGtv[Db&Wymu*ht!YmKTxwFz5T;?vQ");
        Statement stmt = conn.createStatement();

        String sql = String.format("UPDATE %s SET %s WHERE %s = '%s';", table, columnsValues, columnCondition, columnResult);

        int result = stmt.executeUpdate(sql);
        conn.close();
        return result;
    }


}
