package shoppingproject.shop.web.cart;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Cart;
import shoppingproject.shop.domain.common.UploadFile;
import shoppingproject.shop.domain.item.Category;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.CartRepository;
import shoppingproject.shop.repository.ItemRepository;
import shoppingproject.shop.service.CartService;
import shoppingproject.shop.web.CommonConst;

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final CartService cartService;
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
        // 상품 정보 찾기
        Item findOne = itemRepository.findOne(id);
        // cart 객체 생성
        //로그인안하면 session 값을 쿠키에 저장해서 비교...
        Cart newCart = Cart.createCart(findOne, (Member) model.getAttribute("member"));
        cartService.saveCart(newCart);

        // cart 정보를 넘긴다
        model.addAttribute("items",itemRepository.findAll());
        //model.addAttribute("cartNum",2);
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
