package app.persistence;

import app.entities.Bottom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BottomMapper {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cupcakes";

    public List<Bottom> getAllBottoms() throws SQLException {
        List<Bottom> bottoms = new ArrayList<>();
        Connection CP = ConnectionPool.getInstance(USER, PASSWORD, URL, DB).getConnection();
        PreparedStatement ps = CP.prepareStatement("SELECT * FROM bottom");
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            bottoms.add(new Bottom(
                    rs.getInt("bottom_id"),
                    rs.getString("bottom_name"),
                    rs.getBigDecimal("bottom_price")
            ));

        }
        return bottoms;
    }

    public static String getBottomById(int id){
        try {

            Connection CP = ConnectionPool.getInstance(USER, PASSWORD, URL, DB).getConnection();
            PreparedStatement ps = CP.prepareStatement("SELECT bottom_name FROM bottom");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("bottom_name");
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
