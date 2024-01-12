package Entity;
import java.sql.*;
public class UserSQL {
    public static void main(String[] args) {
        new UserSQL();
    }
    public UserSQL() {
        try
        {
            //étape 2: créer l'objet de connexion
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/emp?useSSL=false", "root", "123456");
            //étape 3: créer l'objet statement
            Statement stmt = conn.createStatement();
            String sql = "SELECT id, identifiant FROM User";
            ResultSet res = stmt.executeQuery(sql);
            //étape 5: extraire les données
            while(res.next()){
                //Récupérer par nom de colonne
                int id = res.getInt("id");
                String identifiant = res.getString("identifiant");
                //Afficher les valeurs
                System.out.print("ID: " + id);
                System.out.print(", Nom: " + identifiant);
            }

            //étape 6: fermez l'objet de connexion
            conn.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
