package shoppingproject.shop.web.member;

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
import shoppingproject.shop.domain.Member.Member;
import shoppingproject.shop.web.CommonConst;
import shoppingproject.shop.web.validation.MemberEditForm;
import shoppingproject.shop.web.validation.MemberSaveForm;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/member")

public class MemberController {

    @GetMapping("/joinForm")
    public String joinForm( Model model){
        model.addAttribute("mem",new MemberSaveForm());
        return "/member/joinForm";
    }


    // 회원 가입로직
    @PostMapping("/joinForm")
    public String joinSave(@Validated @ModelAttribute("mem") MemberSaveForm mem, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/member/joinForm";
        }
        
        // 저장 로직
        return "/home";
    }

    // 회원 상세 폼
    @GetMapping("/detailForm")
    public String detailForm( Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(CommonConst.LOGIN_MEMBER);
        log.info("session={}",loginMember);
        model.addAttribute("mem",loginMember);
        return "/member/detail";
    }

    // 회원 조회 로직  추후 db로직 분리 필요... repogistory로
    @GetMapping("/listForm")
    public String list( Model model, HttpServletRequest request){
        ArrayList<Member> memlist = new ArrayList<>();
        for (int i=1; i<10; i++){
            Member mem = new Member();
            mem.setName("회원"+i);
            mem.setLoginId("adcd"+i);
            mem.setId((long)i);
            mem.setContact("1111111");
            memlist.add(mem);
        }
        model.addAttribute("members",memlist);
        return "/member/list";
    }
    // 회원 수정 폼
    @GetMapping("/editForm")
    public String editForm(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(CommonConst.LOGIN_MEMBER);
        MemberEditForm form = new MemberEditForm();
        form.setId(loginMember.getId());
        form.setContact(loginMember.getContact());
        form.setLoginId(loginMember.getLoginId());
        form.setEmail(loginMember.getEmail());
        form.setGender(loginMember.getGender());
        form.setName(loginMember.getName());
        form.setPassword(loginMember.getPassword());
        model.addAttribute("mem",form);
        return "/member/editForm";
    }
    // 회원 수정 로직
    @PostMapping("/editForm")
    public String joinEdit(@Validated @ModelAttribute("mem") MemberEditForm mem, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/member/joinForm";
        }

        // 수정 로직
        return "/home";
    }


}
