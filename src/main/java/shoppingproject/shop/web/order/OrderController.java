package shoppingproject.shop.web.order;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Order;
import shoppingproject.shop.domain.OrderItem;
import shoppingproject.shop.domain.TestDto;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.ItemRepository;
import shoppingproject.shop.repository.MemberRepository;
import shoppingproject.shop.repository.OrderRepository;
import shoppingproject.shop.service.ItemService;
import shoppingproject.shop.service.MemberService;
import shoppingproject.shop.service.OrderService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @RequestMapping("/add")
    public String add(){
        return "/order/add";
    }

    // 주문 화면 호출
    @GetMapping("/orderForm")
    public String orderForm(Model model){

        return "/order/add";
    }

    List<OrderItem> orderItems = new ArrayList<>();
    @PostMapping("/addForm")
    public String addForm(@ModelAttribute("item")Item item,  Model model,HttpServletRequest request){
        Item finditem = itemService.findOne(item.getId());
        Order newOrder = new Order();

        OrderItem orderItem = OrderItem.createOrderItem(finditem,item.getOrderQuantity(),item.getSelectedColor(),item.getSelectedSize(), finditem.getPrice());
        orderItems.add(orderItem);
        newOrder.setOrderItem(orderItems);
        Member member =(Member)request.getSession().getAttribute("loginMember");
        model.addAttribute("member",member);
        model.addAttribute("order",newOrder);
        model.addAttribute("totalPrice",newOrder.getTotalItemPrice());
        return "/order/add";
    }

    @GetMapping("/add/{id}")
    public String addFrom1(@PathVariable("id") long id){
        Item finditem = itemService.findOne(id);
        List<OrderItem> orderItems = new ArrayList<>();
      //  OrderItem orderItem = OrderItem.createOrderItem(finditem,item.getOrderQuantity(),item.getSelectedColor(),item.getSelectedSize(), finditem.getPrice());
       // orderItems.add(orderItem);
        return "/order/add";
    }

    @PostMapping("/save")
    public String saveOrder(Order order, Model model){
                Member member = (Member)model.getAttribute("member");

        orderItems.stream().forEach(or -> {
                    System.out.println("member name : " +or.getItem().getItemName());

        });
        orderService.order(order,member.getId(),orderItems);

        return "redirect:/order/list";

    }

    @GetMapping("/list")
    public String orderList(Model model) throws Exception {
     //   Member member = (Member)model.getAttribute("member");
     //   memberService.findOne(member.getId());
     //   model.addAttribute("member",member);

        return "/order/list";
    }

}
