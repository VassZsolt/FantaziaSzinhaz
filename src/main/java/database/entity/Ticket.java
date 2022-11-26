package database.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
    private Integer rowNumber;

    @Column(nullable = false)
    private Integer columnNumber;

    @Column(nullable = false)
    private long price;

    public Ticket(Integer userId, Integer playId, Integer rowNumber, Integer columnNumber, long price) {
        this.userId = userId;
        this.playId = playId;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.price = price;
    }

}
