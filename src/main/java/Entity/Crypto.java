package Entity;

public class Crypto {

    private int id;
    private int quantity;
    private String name;
    private int walletId; // Corresponds to 'id_wallet' in your table

    public Crypto(int id, int quantity, String name, int walletId) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.walletId = walletId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }
}
