package shoppingproject.shop.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shoppingproject.shop.domain.Member.Member;


@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        log.info("home");
        HttpSession session = request.getSession(true);
        Member loginMember = (Member)  session.getAttribute(CommonConst.LOGIN_MEMBER);
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "/home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        return "/home";
    }
}