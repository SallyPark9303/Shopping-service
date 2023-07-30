package shoppingproject.shop.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.TestDto;
import shoppingproject.shop.domain.UserType;


@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(true);
        Member loginMember = (Member)  session.getAttribute(CommonConst.LOGIN_MEMBER);
        model.addAttribute("viewMode","home");
        //세션에 회원 데이터가 없으면 home
        log.info("session chk ={}",loginMember);
        if (loginMember == null) {
            model.addAttribute("member", null);
            return "/home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        if(loginMember.getUserType().equals(UserType.ADMIN)){
            return "/admin/main";
        }

        return "/home";
    }
}
