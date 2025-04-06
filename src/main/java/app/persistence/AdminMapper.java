package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class AdminMapper {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcakes";

    public static List<Order> getOrders() {

        String moneySymbol;
        BigDecimal bigDecimalFromString;
        int userID;
        int orderID;
        String moneyDatatype;
        String moneyToCleanString;
        List<Order> orders = new ArrayList<>();
try(
        Connection CP = ConnectionPool.getInstance(USER, PASSWORD, URL, DB).getConnection();
        ) {
    PreparedStatement ps = CP.prepareStatement("SELECT * FROM orders");

    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        int i = rs.getInt("user_id");

    }
    while (rs.next()) {
        userID = rs.getInt("user_id");
        orderID = rs.getInt("order_id");
        moneyDatatype = rs.getString("total_price");
        moneyToCleanString = moneyDatatype.replaceAll("[^\\d.]", "");
        bigDecimalFromString = new BigDecimal(moneyToCleanString);

        java.sql.Date sqlDate = rs.getDate("order_date");
        LocalDate orderDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;
        orders.add(new Order(userID, orderID, bigDecimalFromString, orderDate));
    }
}
catch (SQLException e) {
    System.out.println(e.getMessage());
}
return orders;
    }

    public static List<OrderDetails> getOrderDetails(int orderID) {

        String toppingId = "";
        String bottomId = "";
        int quantity;

        List<OrderDetails> orderDetailList = new ArrayList<>();
        int orderIDForSQL = orderID;
        try(Connection CP = ConnectionPool.getInstance(USER, PASSWORD, URL, DB).getConnection();

        PreparedStatement ps = CP.prepareStatement("SELECT order_details.order_id, topping.topping_name, bottom.bottom_name, order_details.quantity FROM order_details INNER JOIN topping ON order_details.topping_id = topping.topping_id INNER JOIN bottom ON order_details.bottom_id = bottom.bottom_id WHERE order_details.order_id = ?"); ) {
            ps.setInt(1, orderIDForSQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orderID = rs.getInt("order_id");
                toppingId = rs.getString("topping_name");
                bottomId = rs.getString("bottom_name");
                quantity = rs.getInt("quantity");

                OrderDetails orderDetail = new OrderDetails(orderID, toppingId, bottomId, quantity);
                orderDetailList.add(orderDetail);

            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return orderDetailList;
    }
}
