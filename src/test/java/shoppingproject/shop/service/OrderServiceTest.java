package shoppingproject.shop.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Order;
import shoppingproject.shop.domain.OrderItem;
import shoppingproject.shop.domain.OrderStatus;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.OrderRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @Rollback(value = false)
    public void 상품주문() throws Exception{
        Member member = new Member();
        member.setName("회원1");
        member.setLoginId("admin");
        member.setPassword("admin");
        member.setEmail("admin");
        em.persist(member);
        Item item = new Item();
        item.setItemName("티셔츠1");
        item.setPrice(100000);
        em.persist(item);
        OrderItem order1 = OrderItem.createOrderItem(item, item.getPrice(), 2,"blue","L");
        OrderItem order2 = OrderItem.createOrderItem(item, item.getPrice(), 4,"blue","L");
        List<OrderItem> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        Long orderId = orderService.order(member.getId(), item.getId(), orders);
        Order findOrder = orderRepository.findOne(orderId);
        Assert.assertEquals("상품 주문시 상태는 : ", OrderStatus.ORDER,findOrder.getOrderStatus());

    }

    @Test
    public void 주문취소() throws Exception{


    }

}