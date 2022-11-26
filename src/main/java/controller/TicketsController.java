package controller;
import model.UserTicket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketsController {

    Connection conn;
    List<UserTicket> tickets = new ArrayList<>();

    public TicketsController() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String initialQuery = "call getUserTickets(" + Main.LOGGED_IN_USER.getId() + ")";
            ResultSet rs = stmt.executeQuery(initialQuery);

            while (rs.next()) {
                setListItemData(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setListItemData(ResultSet rs) throws SQLException {
        UserTicket userTicket = new UserTicket();
        userTicket.setRowNumber(rs.getInt("sor"));
        userTicket.setColumnNumber(rs.getInt("oszlop"));
        userTicket.setPrice(rs.getLong("ar"));
        userTicket.setEloadasId(rs.getInt("szinid"));
        userTicket.setIdopont(rs.getDate("idopont"));
        userTicket.setCim(rs.getString("cim"));
        userTicket.setMufaj(rs.getString("mufaj"));
        userTicket.setHelyszin(rs.getString("helyszin"));
        tickets.add(userTicket);
    }

}
