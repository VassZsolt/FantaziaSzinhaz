package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.ListItem;

public class ListItemController {

    @FXML
    private Label actorText;

    @FXML
    private Button bookButton;

    @FXML
    private Label dateText;

    @FXML
    private Label genreText;

    @FXML
    private Label titleText;

    public void setData(ListItem listitem){
        dateText.setText(listitem.getDate().toString());
        genreText.setText(listitem.getGenre());
        titleText.setText(listitem.getTitle());
        actorText.setText(listitem.getName());


    }

}
