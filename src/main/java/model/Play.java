package model;

import javafx.scene.control.ListCell;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Play extends ListCell<String>{

    String title;
    Date date;
    String name;
    String genre;
    String hall;
    String description;
    int hallId;
    int eloadasId;

}
