package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ListItem;
import model.Seat;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class SeatController {

    @FXML
    private AnchorPane seatBackground;

    private OnSeatClickListener onSeatClickListener;

    private Seat seat;

    public void setData(Seat seat, OnSeatClickListener onSeatClickListener) {
        this.seat = seat;
        this.onSeatClickListener = onSeatClickListener;
        if (seat.isReserved()) {
            seatBackground.setStyle("-fx-background-color: lightcoral");
        } else seatBackground.setStyle("-fx-background-color:  lightgreen");
    }

    public void onClick(javafx.scene.input.MouseEvent mouseEvent) {
        onSeatClickListener.onClickListener(seat);
        if (!seat.isReserved())
            if (seat.isSelected()) {
                seat.setSelected(false);
                seatBackground.setStyle("-fx-background-color:  lightgreen");
            } else {
                seat.setSelected(true);
                seatBackground.setStyle("-fx-background-color: Gold");
            };
    }
}
