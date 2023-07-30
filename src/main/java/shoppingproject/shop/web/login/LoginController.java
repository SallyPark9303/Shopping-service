package shoppingproject.shop.web.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.UserType;
import shoppingproject.shop.repository.MemberRepository;
import shoppingproject.shop.service.MemberService;
import shoppingproject.shop.web.CommonConst;
import shoppingproject.shop.web.validation.LoginForm;

@Controller
@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    public final MemberService memberService;
    public final MemberRepository memberRepository;


    @GetMapping
    public String LoginForm(@ModelAttribute("login") LoginForm login){

        return "/member/login";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("login") LoginForm login, BindingResult bindingResult,
                        Model model,HttpServletRequest request) throws Exception {
        if(bindingResult.hasErrors()){
            return "/member/login";
        }
        // 세션 만들기
        HttpSession session = request.getSession();
        //로그인 아이디로 로그인 정보를 세션에 저장
        Member member;
        try {
            member =  memberService.getLogin(login.getLoginId(),login.getPassword());
        } catch (Exception e) {
            model.addAttribute("loginError","로그인 정보가 일치하지 않습니다.");
            return "/member/login";
        }

        session.setAttribute(CommonConst.LOGIN_MEMBER, member);

        model.addAttribute("member",member);
        // 어드민 유저는 관리화면으로 이동
        if(member.getUserType().equals(UserType.ADMIN)){
            return "/admin/main";
        }
        model.addAttribute("viewMode","home");
        memberService.login(member);
        return "/home";
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃");
        return "redirect:/";
    }


}
