package shoppingproject.shop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Size{
    @Id
    @GeneratedValue
    @Column(name = "size_id")
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item ;

    public Size(Item item, String size) {
        this.setItem(item);
        this.setName(size);
    }

    public Size() {

    }
}
