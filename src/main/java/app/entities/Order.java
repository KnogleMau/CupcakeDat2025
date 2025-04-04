package app.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;


public class Order {
//LocalDate = new LocalDate.of(2025, 4, 2);
    private int userID;
    private int OrderID;
    private BigDecimal totalPrice;
    private LocalDate date;

    public Order(int userID, int orderID, BigDecimal totalPrice, LocalDate date) {
        this.userID = userID;
        OrderID = orderID;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }
}
