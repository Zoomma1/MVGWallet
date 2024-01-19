package Entity;

import javafx.application.Application;
import javafx.stage.Stage;
import Entity.WalletRepository;

import java.sql.SQLException;
import java.util.ArrayList;

import static Entity.UserRepository.checkRegex;

public class Wallet {

    private boolean selected;
    private int id;
    private int userId;
    private WalletRepository repository = new WalletRepository();

    public Wallet(int userId, boolean selected) {
        this.userId = userId;
        this.selected = selected;
    }
    public Wallet(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void createWallet() throws SQLException {
        repository.createWallet(this.userId,this.selected);
    }
    public static Wallet createEntity(int userId) throws SQLException {
        WalletRepository repo = new WalletRepository();
        String id = repo.findBySelectedAndId(userId).toString();
        String x = checkRegex(id,"\\d{1,9}");
        return new Wallet(Integer.parseInt(x));
    }
}
