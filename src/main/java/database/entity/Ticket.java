package database.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jegy")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer playId;

    @Column(nullable = false)
    private Integer seatId;

    @Column(nullable = false)
    private long price;

    private String hallName;
    private String title;
    private Date date;
    private String email;
    private String nickname;

    public Ticket(Integer userId, Integer playId, Integer seatId, long price) {
        this.userId = userId;
        this.playId = playId;
        this.seatId = seatId;
        this.price = price;
    }

}
