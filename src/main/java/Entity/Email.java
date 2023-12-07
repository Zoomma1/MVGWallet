package Entity;
/*
* Principe de fonctionnement:
* L'objet "Email" contient:
*   - Contenu du message, le "body" en quelques sortes
*   - Est rajouté par défaut, la signature du logiciel + logo
*   - La dsetinations de l'email
*
* Fonctionnement:
* Les parametres sont rajoutées au fur et a mesure avec des setter, une prêt, une méthode "send" sera utilisé pour envoyer le mail.
* Un boolean devra être retourné et possibilité de gérer les erreurs
*
* Type d'email:
* 1*.Alerte de connexion
* 2*.Alerte financière (chute de crypto ou autre)
* 3*.Alerte Baleine
* 4.Marketing (pronostique)
*
* Le signe * signifie obligatoire.
*
* Mathys Haubert @blackl0ok
*/
public class Email {

    private String destination;
    private String content;

    private String type;
}
