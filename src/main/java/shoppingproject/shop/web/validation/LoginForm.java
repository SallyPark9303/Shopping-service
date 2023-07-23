package shoppingproject.shop.web.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
