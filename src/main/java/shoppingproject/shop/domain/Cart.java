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
import shoppingproject.shop.web.cart.CartController;
import shoppingproject.shop.web.cart.CartController.SendDTO;

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
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name="member_id")
 private Member member;
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name="item_id")
 private Item item ;
 private Integer orderPrice;
 private Integer quantity;
 private String color;
 private String size;
 @Transient
 private boolean isSelect;
 public static Cart createCart(SendDTO data, Item item , Member member){
  Cart newCart = new Cart();
  newCart.setMember(member);
  newCart.setItem(item);
  newCart.setColor(data.getColor());
  newCart.setSize(data.getSize());
  newCart.setQuantity(data.getQuantity());
  newCart.setOrderPrice(data.getOrderPrice());
  return newCart;
 }
 public void setMember(Member member){
  this.member = member;
  member.getCarts().add(this);
 }
}

