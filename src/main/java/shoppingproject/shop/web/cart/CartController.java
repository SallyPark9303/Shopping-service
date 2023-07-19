package shoppingproject.shop.web.cart;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;
import shoppingproject.shop.domain.Member.Member;
import shoppingproject.shop.domain.cart.Cart;
import shoppingproject.shop.domain.cart.CartRepository;
import shoppingproject.shop.domain.item.ItemRepository;
import shoppingproject.shop.web.CommonConst;

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    @ModelAttribute("member")
    public String checkMember(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(true);
        Member loginMember = (Member)  session.getAttribute(CommonConst.LOGIN_MEMBER);
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            model.addAttribute("member", null);
            return "/home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        return "/home";
    }

    @GetMapping("/add")
    public String add(@RequestParam("itemId") long id, Model model){
        model.addAttribute("items",itemRepository.findAll());
        model.addAttribute("cartNum",2);
        return "/item/list";

    }

    @GetMapping("/delete")
    public String delete(Model model){
       // itemRepository.delete(model.get);
        return "/list";

    }


    // 장바구니 목록

    @GetMapping("/list")
    public String list(Model model){
        List<Cart> carts =  cartRepository.findAll();
        model.addAttribute("carts",carts);
        return "/cart/list";
    }

}
