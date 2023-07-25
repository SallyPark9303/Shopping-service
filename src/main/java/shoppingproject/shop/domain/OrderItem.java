package shoppingproject.shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shoppingproject.shop.domain.item.Color;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.domain.item.Size;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private long id;
    private int quantity;
    private int orderPrice;
    private String color;
    private String size;
    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    
    // 생성 메서드
    public static OrderItem createOrderItem(Item item,int Quantity,String color,String size, int orderPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setQuantity(Quantity);
        orderItem.setColor(color);
        orderItem.setSize(size);
        return orderItem;

    }

    //주문상품 전체가격조회
    public int getTotalPrice(){
        return this.getQuantity() * this.getOrderPrice();

    }

}
