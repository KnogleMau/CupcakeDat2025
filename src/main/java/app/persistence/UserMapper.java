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


    public static void createUser(String email, String password, String firstName, String lastName, String phoneNumber, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "insert into users (email, password, first_name, last_name, phone_number) values (?,?,?,?,?)";

        try ( Connection connection = connectionPool.getConnection();
              PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, phoneNumber);


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        }
        catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
        /*catch (SQLException e) {
            System.out.println("=== FEJL VED INSERT ===");
            e.printStackTrace(); // hele stacktrace
            System.out.println("SQL Message: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());

            throw new DatabaseException("SQL FEJL: " + e.getMessage(), e.getMessage());
        }*/
    }

    public static void insertMoney(){

    }

    public static boolean userType(String email, ConnectionPool connectionPool) throws DatabaseException {
        boolean adminStatus = false;

        String sql = "select admin from users where email=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, email);


            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                adminStatus = rs.getBoolean("admin");

            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException | DatabaseException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }

        return adminStatus;
    }


}