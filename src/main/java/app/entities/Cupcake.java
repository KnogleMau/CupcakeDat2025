package app.entities;

public class Cupcake {
    private int toppingId;
    private int bottomId;
    private int quantity;
    private String bottomName;
    private String toppingName;

    public Cupcake(int toppingId, int bottomId, int quantity,String bottomName, String toppingName){
        this.toppingId = toppingId;
        this.bottomId = bottomId;
        this.quantity = quantity;
        this.bottomName = bottomName;
        this.toppingName = toppingName;
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

    public String getBottomName() {
        return bottomName;
    }

    public String getToppingName() {
        return toppingName;
    }
}
