package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Seat {

    private int id;
    private int typeId;
    private boolean reserved;
    private int row;
    private int column;
    private boolean isSelected;

}
