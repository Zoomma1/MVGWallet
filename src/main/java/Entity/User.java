package Entity;
/*
* Create User object if Login is true
*
* Fonctionnement:
* La classe User contient les informations de l'utilisateur coté "personne"
*
* Idée:
* Implementer un template différent lors de l'anniversaire
*
* Mathys Haubert
*/
public class User {
    private String userName;

    private String email;

    private int age;

    public User(
            String userName,
            String email,
            int age
    ){
        this.userName = userName;
        this.email = email;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setPassword(String newPassword){
        /* to-do */
    }

    public void setEmail(Email email){
        /*to do too*/
    }
}
