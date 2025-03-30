package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper {


    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "select * from users where email=? and password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                double balance = rs.getDouble("balance");
                boolean admin = rs.getBoolean("admin");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("phone_number");

                return new User(id, email, password, balance, admin, firstName, lastName, phoneNumber);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

}