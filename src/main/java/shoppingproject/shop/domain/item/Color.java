package shoppingproject.shop.domain.item;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Color{
    @Id
    @GeneratedValue
    @Column(name = "color_id")
        private Long id;
        private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item ;

}


