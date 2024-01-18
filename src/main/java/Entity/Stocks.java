package Entity;

public class Stocks {

    private int id;
    private int quantity;
    private String name;
    private int walletId; // Corresponds to 'id_wallet' in your table
    private boolean followedStock; // This should correspond to a boolean field in your table

    public Stocks(int id, int quantity, String name, int walletId, boolean followedStock) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.walletId = walletId;
        this.followedStock = followedStock;
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

    public boolean isFollowedStock() {
        return followedStock;
    }

    public void setFollowedStock(boolean followedStock) {
        this.followedStock = followedStock;
    }
}
