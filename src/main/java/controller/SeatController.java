package controller;


import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.Seat;


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
