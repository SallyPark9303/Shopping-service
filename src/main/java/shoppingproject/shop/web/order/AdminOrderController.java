package shoppingproject.shop.web.order;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Order;
import shoppingproject.shop.domain.OrderItem;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.ItemRepository;
import shoppingproject.shop.repository.MemberRepository;
import shoppingproject.shop.repository.OrderRepository;
import shoppingproject.shop.service.ItemService;
import shoppingproject.shop.service.MemberService;
import shoppingproject.shop.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/list/{pageNum}")
    public String list(@PathVariable("pageNum") int Num, Model model ){
        Map<String, Object> result = orderService.findById(0, Num);
        model.addAttribute("orders", result.get("orders"));
        model.addAttribute("pageUtil", result.get("pageUtil"));
        return "/admin/order/list";
    }

    @PostMapping("/deliveryComp/{orderId}")
    public String deliveryComp(@PathVariable("orderId") long orderId){

        orderService.deliveryComp(orderId);
        return "redirect:/admin/order/list/1";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancel(@PathVariable("orderId") long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/admin/order/list/1";
    }





}
