package shoppingproject.shop.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shoppingproject.shop.domain.Member.Member;
import shoppingproject.shop.web.CommonConst;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/tempLogin")
    public String templogin(HttpServletRequest request){
        //테스트용 로그인 데이터 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        Member loginMember = new Member();
        loginMember.setLoginId("test");
        loginMember.setName("하늘");

        session.setAttribute(CommonConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request){
        //테스트용 로그인 데이터 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        Member loginMember = new Member();
        loginMember.setLoginId("test");
        loginMember.setName("관리자");

        session.setAttribute(CommonConst.LOGIN_MEMBER, loginMember);
        return "/admin/main";
    }
}
