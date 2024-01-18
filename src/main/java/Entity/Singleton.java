package Entity;

public class Singleton {
    private static final Singleton instance = new Singleton();

    private User currentUser;
    private Singleton() {}

    public static Singleton getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
