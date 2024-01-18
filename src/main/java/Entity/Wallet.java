package Entity;

import javafx.application.Application;
import javafx.stage.Stage;
import Entity.WalletRepository;

import java.sql.SQLException;

public class Wallet {

    private boolean selected;
    private int userId;
    private WalletRepository repository = new WalletRepository();

    public Wallet(int userId, boolean selected) {
        this.userId = userId;
        this.selected = selected;
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
}
