package shoppingproject.shop.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//주문 테이블
@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
    private String orderedPhone;
    private String receivedPhone;
    private String receiver;
    private String addr1;
    private String addr2;
    private String deliveryMessage;
    private int totalItemPrice;
    private int deliveryPrice;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL) // orderItem 을 함께 저장해준다.
    private List<OrderItem> orderItem = new ArrayList<>();

    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now(); // 주문시간
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 배송 상태
    private int orderItemCnt;


    private int TotalPrice(){
      return this.totalItemPrice + this.deliveryPrice;
    }

    // 연관관계 메서드 -양반향 데이터를 원자적으로 묶음
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    // 원래라면
//    public static void main(){
//        Member mem = new Member();
//        Order order = new Order();
//        mem.getOrders().add(order);
//        order.setMember(mem);
//    }

    public void addOrderItem(OrderItem oItem){
        orderItem.add(oItem);
        oItem.setOrder(this);
    }


    // 생성 메서드//
    public static Order createOrder(Member member, List<OrderItem> orderItems){
        Order order = new Order();
        order.setMember(member);
        int totalPrice =0;
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
            totalPrice += orderItem.getTotalPrice();
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderItemCnt(orderItems.size());
        order.setTotalItemPrice(totalPrice);
        return order;
    }

    // 비지니스 로직//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(deliveryStatus.equals(DeliveryStatus.COMP)){
            throw new IllegalStateException("이미 배송완료된 상품입니다.");
        }
        this.setOrderStatus(OrderStatus.CANCEL); // 변경된 감지가 일어나서 바꾼 데이터 쿼리가 날라감.
    }
    


}
