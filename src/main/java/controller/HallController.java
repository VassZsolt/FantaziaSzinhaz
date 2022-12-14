package controller;

import database.entity.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Seat;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class HallController {

    Connection conn;

    @FXML
    Label hallNameLabel;
    @FXML
    GridPane seatGrid;

    @FXML
    ListView listView = new ListView();

    List<Seat> seats = new ArrayList<>();

    Map<Seat, Long> selectedSeats = new HashMap<>();

    long totalPrice = 0;

    @FXML
    Label priceLabel;
    @FXML
    Label columnLabel;
    @FXML
    Label rowLabel;

    @FXML
    Button confirmButton;
    OnSeatClickListener onSeatClickListener;
    private String choosenHallName;
    private int playId;

    @FXML
    void initialize() {
        System.out.println("eloadas: " +playId+" ");
        hallNameLabel.setText(choosenHallName);
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String initialQuery = "call getSeatsForPlay(" + playId + ")";
            System.out.println(initialQuery);
            ResultSet rs = stmt.executeQuery(initialQuery);

            while (rs.next()) {
                setListData(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listView.setOrientation(Orientation.HORIZONTAL);

        onSeatClickListener = this::setChosenSeat;

        try {
            for (int i = 0; i < seats.size(); i++) {
                System.out.println(seats.get(i));
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/SeatLayout.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                SeatController seatController = fxmlLoader.getController();
                seatController.setData(seats.get(i), onSeatClickListener);
                seatGrid.add(anchorPane, seats.get(i).getColumn(), seats.get(i).getRow());
                GridPane.setMargin(anchorPane, new Insets(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void confirmReservation(ActionEvent event) throws IOException {
        for (Map.Entry<Seat, Long> value : selectedSeats.entrySet()) {
            Ticket ticket = new Ticket(Main.LOGGED_IN_USER.getId(), playId, value.getKey().getId(), value.getValue());
            Main.TICKET_REPOSITORY.saveTicket(ticket);
        }
        showAlert(event);
    }

    private void showAlert(ActionEvent event) {
        ButtonType close = new ButtonType("OK");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeresen lefoglaltad a jegyedet!", close);
        alert.setTitle("Sikeres jegyfoglal??s!");
        alert.setHeaderText("A jegyfoglal??sod a megadott helyekre sikeres volt. A kezd??lapon, a Jegyeim f??l alatt megtal??lod a tov??bbi inform??ci??kat. ");
        Optional<ButtonType> result = alert.showAndWait();
        alert.close();
        closeWindow(event);
    }

    public void setListData(ResultSet rs) throws SQLException {
        Seat newSeat = new Seat();
        newSeat.setId(rs.getInt("ulohelyid"));
        newSeat.setTypeId(rs.getInt("helytipusid"));
        newSeat.setReserved(rs.getBoolean("foglaltsag"));
        newSeat.setRow(rs.getInt("sor"));
        newSeat.setColumn(rs.getInt("oszlop"));
        seats.add(newSeat);
    }

    public void setChosenSeat(Seat seat) {
        rowLabel.setText("Sor: " + seat.getRow());
        columnLabel.setText("Oszlop: " + seat.getColumn());
        long price = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("call getPriceOfSelectedSeat(" + seat.getTypeId() + ")");
            if (rs.next()) {
                price = rs.getLong("ar");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        confirmButton.setDisable(seat.isReserved());
        if (seat.isSelected()) {
            selectedSeats.put(seat, price);
            totalPrice += price;
        } else if (selectedSeats.containsKey(seat)) {
            selectedSeats.remove(seat);
            totalPrice -= price;
        }
        priceLabel.setText("??r: " + totalPrice);
    }

    public void setChoosenHallName(String choosenHallName) {
        this.choosenHallName = choosenHallName;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }

    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
