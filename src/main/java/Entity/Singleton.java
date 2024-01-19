package Entity;

import java.util.ArrayList;

public class Singleton {
    private static final Singleton instance = new Singleton();

    private User currentUser;
    private ArrayList<String> wallets;
    private Singleton() {}

    public static Singleton getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public ArrayList<String> getWallets() { return this.wallets; }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public void setListWallet(ArrayList<String> listWalletString) { this.wallets = listWalletString; }
}
