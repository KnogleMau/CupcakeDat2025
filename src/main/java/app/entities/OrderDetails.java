package app.entities;

public class OrderDetails {
    int orderId;
    String toppingName;
    String BottomName;
    int quantity;

    public OrderDetails(int orderId, String toppingName, String bottomName, int quantity) {
        this.orderId = orderId;
        this.toppingName = toppingName;
        BottomName = bottomName;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }
    public String getToppingName() {
        return toppingName;
    }

    public String getBottomName() {
        return BottomName;
    }
    public int getQuantity() {
        return quantity;
    }
}
