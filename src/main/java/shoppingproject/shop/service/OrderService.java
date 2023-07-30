package shoppingproject.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Order;
import shoppingproject.shop.domain.OrderItem;
import shoppingproject.shop.domain.common.PagesUtils;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.ItemRepository;
import shoppingproject.shop.repository.MemberRepository;
import shoppingproject.shop.repository.OrderRepository;

import java.util.*;

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
    public Long order(Order newOrder,Long memberId, List<OrderItem> orderItems) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Order order;
        // 주문 생성
        if(newOrder.getId() ==null) {
            order = Order.createOrder(newOrder ,member, orderItems);
            //주문 저장
            orderRepository.save(order);
            return order.getId();
        }else{
            order = orderRepository.findOne(newOrder.getId());
           // order = Order.updateOrder(findOne ,member, orderItems)

            order.setId(newOrder.getId());
           // order.setMember(member);
            order.setRecipientInfo(newOrder.getRecipientInfo());
            order.setSenderInfo(newOrder.getSenderInfo());
            order.setOrderItemCnt(orderItems.size());
                int totalPrice =0;
                int totalItemPrice=0;
                for(OrderItem orderItem : orderItems){
                    orderItem.setId(orderItem.getId());
                    order.addOrderItem(orderItem);
                    totalItemPrice = orderItem.getOrderPrice() * orderItem.getQuantity();
                    totalPrice += totalItemPrice;
                }
            order.setOrderItemCnt(orderItems.size());
            order.setTotalItemPrice(totalPrice);
            return order.getId();
        }


    }
    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    @Transactional
    public void deliveryComp(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.deliveryCompleted();
    }

    // 페이징 + 조회
    public Map<String, Object> findById (long memId, int currentPageNo) {
        List<Order> orders = null;
        if(memId ==0) orders = orderRepository.findAll();
        else orders = orderRepository.findByUserId(memId);

        PagesUtils pageUtils = new PagesUtils(orders.size(), currentPageNo);
        List<Order> ordersPaging =null;
        if(memId == 0) ordersPaging =orderRepository.findAllPaging(pageUtils);
        else ordersPaging = orderRepository.findByUserIdPaging(memId,pageUtils);

        Map resultMap = new LinkedHashMap();
        resultMap.put("orders",ordersPaging);
        resultMap.put("pageUtil",pageUtils);
        return resultMap;

    }







//    public List<Order> findAll(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }

}
