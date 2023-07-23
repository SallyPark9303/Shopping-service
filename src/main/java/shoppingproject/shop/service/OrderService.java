package shoppingproject.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Order;
import shoppingproject.shop.domain.OrderItem;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.ItemRepository;
import shoppingproject.shop.repository.MemberRepository;
import shoppingproject.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, List<OrderItem> orderItems) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
       // Item item = itemRepository.findOne(itemId);
       // List<OrderItem> orders = new ArrayList<>();
        //주문상품 생성
//        for(OrderItem order : orderItems){
//            OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),order.getQuantity(),order.getColor(),order.getSize());
//            orders.add(orderItem);
//        }

        // 주문 생성
        Order order = Order.createOrder(member, orderItems);

        //주문 저장
        orderRepository.save(order);
        return order.getId();


    }
    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }


//    public List<Order> findAll(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }

}
