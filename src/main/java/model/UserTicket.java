package model;

import lombok.Data;

import java.util.Date;

@Data
public class UserTicket {

    private Integer rowNumber;

    private Integer columnNumber;

    private Long price;

    private Integer eloadasId;

    private Date idopont;

    private String cim;

    private String mufaj;

    private String helyszin;

}
