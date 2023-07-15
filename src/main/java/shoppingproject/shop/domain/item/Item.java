package shoppingproject.shop.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
 private Long Id;
 private String ItemName;
 private Integer Price;
 private Integer Quantity;
 private String Description;

}
