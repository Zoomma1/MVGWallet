package Entity;

import Entity.Email.EmailUtility;

import javax.mail.MessagingException;
import java.io.IOException;
/**
* Create User object if Login is true
*
* Fonctionnement:
* La classe User contient les informations de l'utilisateur coté "personne"
*
* Idée:
* Implementer un template différent lors de l'anniversaire
*
* Mathys Haubert
**/
public class User extends UserSQL{
    protected String userName;

    protected String email;

    protected int age;

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

    public void setEmail(String newEmail){
        if(EmailUtility.isEmailValid(newEmail)){
            /* faire le insert en SQL */
        }
        /*to do too*/
    }
    public void sendEmail(String subject, String content) throws MessagingException, IOException, InterruptedException {
        EmailUtility.sendEmail(this.email, subject,content);
    }
    public void sendLoginEmail() throws MessagingException, IOException, InterruptedException {
        String subject = String.format("[MVG Wallet] Alerte de sécurité pour %s",this.email);
        String content = String.format("Bonjour %s.\\n\\n" +
                "Nous avons détecté une nouvelle connexion à votre compte MVG Wallet. " +
                "Si c'était vous, aucune action de votre pas n'est requise. Dans le cas contraire, nous vous invitons à contacter un administrateur. \\n\\n" +
                "L'équipe MVG Wallet.",this.userName);
        EmailUtility.sendEmail(this.email, subject,content);
    }
}
