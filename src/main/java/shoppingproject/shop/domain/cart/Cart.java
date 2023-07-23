package shoppingproject.shop.domain.cart;

import lombok.Data;
import shoppingproject.shop.domain.common.UploadFile;
import shoppingproject.shop.domain.item.Item;

import java.util.List;

@Data
public class Cart {
 private Long cartId;
 private Long itemId;
 private Long userId;
 private Item item;
 private Boolean isSelect;

}

