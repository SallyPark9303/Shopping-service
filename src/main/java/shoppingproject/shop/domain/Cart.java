package shoppingproject.shop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shoppingproject.shop.domain.common.UploadFile;
import shoppingproject.shop.domain.item.Category;
import shoppingproject.shop.domain.item.Color;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.domain.item.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart {
 @Id
 @GeneratedValue
 @Column(name = "cart_id") // pk 이름
 private Long id;
 @OneToOne
 @JoinColumn(name="member_id")
 private Member member;
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name="item_id")
 private Item item ;
 public static Cart createCart(Item item , Member member){
  Cart newCart = new Cart();
  newCart.setMember(member);
  newCart.setItem(item);

  return newCart;
 }
}

