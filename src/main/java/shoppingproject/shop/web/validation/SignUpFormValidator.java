package shoppingproject.shop.web.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.repository.MemberFormRepository;
import shoppingproject.shop.repository.MemberRepository;


@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final MemberFormRepository memberformRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Member.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member memberForm = (Member)target;
        if(memberformRepository.existsByEmail(memberForm.getEmail())){
            errors.rejectValue("email","invalid.email",new Object[]{memberForm.getEmail()},"이미 사용중인 이메일입니다.");
        }
        if(memberformRepository.existsByLoginId(memberForm.getEmail())){
            errors.rejectValue("loginId","invalid.loginId",new Object[]{memberForm.getLoginId()},"이미 사용중인 사용자아이디 입니다.");
        }


    }
}
