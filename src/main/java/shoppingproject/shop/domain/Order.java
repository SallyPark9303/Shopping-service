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

    private String email;
    private int totalItemPrice;
    private int deliveryPrice;
    //== 배송정보==/
    @Embedded
    private RecipientInfo recipientInfo = new RecipientInfo();
    @Embedded
    private SenderInfo senderInfo = new SenderInfo();
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL) // orderItem 을 함께 저장해준다.
    private List<OrderItem> orderItem = new ArrayList<>();


    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now(); // 주문시간
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDER; // 주문 상태
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus = DeliveryStatus.READY; // 배송 상태
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
    public static Order createOrder(Order nOrder, Member member, List<OrderItem> orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setRecipientInfo(nOrder.getRecipientInfo());
        order.setSenderInfo(nOrder.getSenderInfo());
        order.setOrderItemCnt(orderItems.size());
        int totalPrice =0;
        int totalItemPrice=0;
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
            totalItemPrice = orderItem.getOrderPrice() * orderItem.getQuantity();
            totalPrice += totalItemPrice;
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
    public  void deliveryCompleted(){
        this.setDeliveryStatus(DeliveryStatus.COMP);
    }
    


}
