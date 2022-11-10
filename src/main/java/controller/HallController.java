package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Seat;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallController {

    @FXML
    GridPane seatGrid;

    @FXML
    ListView listView = new ListView();

    List<Seat> seats = new ArrayList<>();

    @FXML
    Label priceLabel;
    @FXML
    Label columnLabel;
    @FXML
    Label rowLabel;

    @FXML
    Button confirmButton;
    OnSeatClickListener onSeatClickListener;
    public int choosenHallId;

    @FXML
    void initialize() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String initialQuery = "call getSeatsInHall("+choosenHallId+")";
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

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/SeatLayout.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                SeatController seatController = fxmlLoader.getController();
                seatController.setData(seats.get(i), onSeatClickListener);
                seatGrid.add(anchorPane, seats.get(i).getColumn(),seats.get(i).getRow());
                GridPane.setMargin(anchorPane, new Insets(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void confirmReservation(){

    }

    public void setListData(ResultSet rs) throws SQLException {
        Seat newSeat = new Seat();
        newSeat.setId(rs.getInt("ulohelyid"));
        newSeat.setReserved(rs.getBoolean("foglaltsag"));
        newSeat.setRow(rs.getInt("sor"));
        newSeat.setColumn(rs.getInt("oszlop"));
        seats.add(newSeat);
    }
    public void setChosenSeat(Seat seat){
        rowLabel.setText("Sor: " + seat.getRow());
        columnLabel.setText("Oszlop: " + seat.getColumn());
        //TODO: Ár kiírás
        confirmButton.setDisable(seat.isReserved());
    }

    public void setChoosenHallId(int choosenHallId) {
        this.choosenHallId = choosenHallId;
    }

    @FXML
    public void closeWindow(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
