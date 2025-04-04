package app.persistence;

import app.entities.Topping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToppingMapper {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcakes";

    public List<Topping> getAllToppings() throws SQLException {
        List<Topping> toppings = new ArrayList<>();
        try(
        Connection CP = ConnectionPool.getInstance(USER, PASSWORD, URL, DB).getConnection();
        ) {
            PreparedStatement ps = CP.prepareStatement("SELECT * FROM topping");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                toppings.add(new Topping(
                        rs.getInt("topping_id"),
                        rs.getString("topping_name"),
                        rs.getBigDecimal("topping_price")
                ));

            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return toppings;
    }

    public static String getToppingById(int id){
        try(Connection CP = ConnectionPool.getInstance(USER, PASSWORD, URL, DB).getConnection();
            PreparedStatement ps = CP.prepareStatement("SELECT topping_name FROM topping WHERE topping_id = ?");
            ) {


            ps.setInt(1, id);  // 🛠 Fixer fejlen
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("topping_name");
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
        }

    public static double getToppingPriceById(int id){
        try(Connection CP = ConnectionPool.getInstance(USER, PASSWORD, URL, DB).getConnection();
            PreparedStatement ps = CP.prepareStatement("SELECT topping_price FROM topping WHERE topping_id = ?")
        ) {


            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("topping_price");
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }


}
