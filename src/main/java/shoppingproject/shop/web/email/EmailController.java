package shoppingproject.shop.web.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shoppingproject.shop.domain.email.EmailUtil;

import java.util.Map;

/**
 * 이메일 컨트롤러
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class EmailController {

    private final EmailUtil emailUtil;

    @PostMapping("/emailTest")
    public Map<String, Object> sendEmail(){


        return emailUtil.sendEmail( (String) "won_1020@naver.com"
                , (String)"emailTest"
                , (String) "메일테스트"
        );
    }

}
