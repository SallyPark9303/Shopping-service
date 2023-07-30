package shoppingproject.shop.web.cart;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Cart;
import shoppingproject.shop.domain.common.PagesUtils;
import shoppingproject.shop.domain.common.UploadFile;
import shoppingproject.shop.domain.item.Category;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.CartRepository;
import shoppingproject.shop.repository.ItemRepository;
import shoppingproject.shop.repository.MemberRepository;
import shoppingproject.shop.service.CartService;
import shoppingproject.shop.web.CommonConst;
import shoppingproject.shop.web.validation.MemberEditForm;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final MemberRepository memRepository;
    private final CartService cartService;
    private final ItemRepository itemRepository;
    @ModelAttribute("member")
    public String checkMember(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(true);
        Member loginMember = (Member)  session.getAttribute(CommonConst.LOGIN_MEMBER);
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            model.addAttribute("member", null);
            model.addAttribute("loginErrorMsg", "로그인 후 이용 가능합니다.");
            return "/home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        return "/home";
    }

    @PostMapping("/add") // 로그인 유무체크 해서 로그인안한 사용자한테는 알람을 띄운다.
    @ResponseBody // ajax 통신
    public void add(SendDTO data){
        // 상품 / 고객 정보 찾기
        Item findItem = itemRepository.findOne(data.getItemId());
        Member findUser = memRepository.findOne(data.getMemId());
        // cart 객체 생성

        Cart newCart = Cart.createCart(data,findItem, findUser);
        cartService.saveCart(newCart);

        // cart 정보를 넘긴다
        //model.addAttribute("items",itemRepository.findAll());

       // return "/item/list";
    }
    @ResponseBody
    @PostMapping("/delete")
    public String delete(SendDTO data, Model model){
        Cart findone = cartService.findOne(data.getCartId());
        cartRepository.delete(findone);
        return "redirect:cart/list/1";

    }
    // 수정 폼 모달 호출
    @ResponseBody
    @PostMapping("/findOne")
    public Cart detailForm(SendDTO data){
        long id = data.getCartId();
        Cart findOne = cartRepository.findOne(id);
        return findOne;

    }



    // 장바구니 목록
    @GetMapping("/list/{pageNum}")
    public String list(@PathVariable("pageNum") int Num ,Model model){
        Member mem = (Member)model.getAttribute("member");
        List<Cart> carts =  cartRepository.findAllWithUserId(mem.getId());
        PagesUtils pageUtil = new PagesUtils(carts.size(),Num);
        List<Cart> result  =cartRepository.findAllPaging(pageUtil,mem.getId());
        model.addAttribute("carts",result);
        model.addAttribute("pageUtil",pageUtil);
        return "/cart/list";
    }

    @Data
    public class SendDTO{
        private long cartId;
        private long memId;
        private long itemId;
        private String color;
        private String size;
        private int quantity;
        private int orderPrice;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
