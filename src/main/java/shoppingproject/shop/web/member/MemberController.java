package shoppingproject.shop.web.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.runtime.metaclass.NewMetaMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shoppingproject.shop.domain.Gender;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.UserType;
import shoppingproject.shop.domain.email.EmailUtil;
import shoppingproject.shop.repository.MemberFormRepository;
import shoppingproject.shop.repository.MemberRepository;
import shoppingproject.shop.service.MemberService;
import shoppingproject.shop.web.CommonConst;
import shoppingproject.shop.web.validation.MemberEditForm;
import shoppingproject.shop.web.validation.MemberSaveForm;
import shoppingproject.shop.web.validation.SignUpFormValidator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@ControllerAdvice
public class MemberController {

    @ModelAttribute("member")
    public Member checkMember(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        Member loginMember = (Member)  session.getAttribute(CommonConst.LOGIN_MEMBER);
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return null;
        }
        return loginMember;
    }

    public final MemberService memberService;
    public final MemberRepository memberRepository;
    public final SignUpFormValidator signUpFormValidator;
    public final MemberFormRepository memberFormRepository;
    private final EmailUtil emailUtil;


    @ModelAttribute("userTypes")
    public UserType[] GetUserTypes() {
        return UserType.values();
    }
    @ModelAttribute("gender")
    public Gender[] GetGender() {
        return Gender.values();
    }


    @GetMapping("/joinForm")
    public String joinForm( Model model){
        model.addAttribute("mem",new Member());
        model.addAttribute("userTypes",GetUserTypes());
        model.addAttribute("gender",GetGender());
        return "/member/joinForm";
    }


    // 회원 가입로직
    @PostMapping("/joinForm")
    public String joinSave(@Validated @ModelAttribute("mem") Member mem, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/member/joinForm";
        }

      /*  signUpFormValidator.validate(mem,bindingResult);
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/member/joinForm";
        }*/
        // 저장 로직
        try {
            Member member = memberService.join(mem);


            return "/member/joinForm";

        } catch (Exception e) {
            //model.addAttribute("loginIdError","중복된 아이디가 있습니다.");
            //mem.setLoginId("");

            log.info("오류={}",e.getClass(),e);
            return "/member/joinForm";

        }
    }

    // 회원 상세 폼
    @GetMapping("/{id}/detailForm")
    public String detailForm(@PathVariable("id") long id, Model model, HttpServletRequest request) throws Exception {
        Member mem = memberService.findOne(id);
        model.addAttribute("mem",mem);
        return "/member/detail";
    }
    @GetMapping("/listForm")
    public String list( Model model, HttpServletRequest request) throws Exception {
        List<Member> memlist = null;

            memlist = memberService.findMembers();
            model.addAttribute("members",memlist);
            return "/member/list";

    }
    // 회원 수정 폼
    @GetMapping("/editForm")
    public String editForm(@RequestParam("id") long id,HttpServletRequest request,Model model) throws Exception {
        Member mem = memberService.findOne(id);
        model.addAttribute("mem",mem);
        return "/member/editForm";
    }
    // 회원 수정 로직
    @PostMapping("/editForm")
    public String joinEdit(@Validated @ModelAttribute("mem") Member mem, BindingResult bindingResult,HttpServletRequest request) throws Exception {
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/member/editForm";
        }
        // 수정 로직
        memberService.update(mem.getId(),mem);
        Member findUser = memberService.findOne(mem.getId());
        // 세션에도 다시 넣어줘야함.
        request.getSession().invalidate();
        HttpSession session = request.getSession();
        session.setAttribute(CommonConst.LOGIN_MEMBER, findUser);
        return "/";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id,HttpServletRequest request){

        Member mem = null;
        try {
            mem = memberService.findOne(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        memberService.remove(mem);
        // 세션이 종료된다.
        request.getSession().invalidate();
        return "/home";
    }

    // 이메일 체크
    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){ // repo 를 domain 과 같은 레벨로 봄
       // Member member = memberFormRepository.findByEmail(email);
        Member member  = new Member();
        member.setEmail("won_1020@naver.com");
        member.setEmailCheckToken("0e445360-cdb6-43b4-a655-303b83f46315");
        member.setName("하늘이");

        String view =  "/member/checked-email";
        if(member == null){
            model.addAttribute("error","잘못된 이메일입니다.");
            return view;
        }
        if(!member.isValidToken(token)){
            model.addAttribute("error","잘못된 토큰입니다.");
            return view;
        }
        member.completeSignUp();
        memberService.login(member);
        model.addAttribute("nickname", member.getName());
        return view;
    }
}
