package app.entities;

public class Cupcake {
    private int toppingId;
    private int bottomId;
    private int quantity;

    public Cupcake(int toppingId, int bottomId, int quantity){
        this.toppingId = toppingId;
        this.bottomId = bottomId;
        this.quantity = quantity;
    }

    public int getToppingId() {
        return toppingId;
    }

    public int getBottomId() {
        return bottomId;
    }

    public int getQuantity() {
        return quantity;
    }


}
