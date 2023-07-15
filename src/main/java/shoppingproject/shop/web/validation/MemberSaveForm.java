package shoppingproject.shop.web.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberSaveForm {


    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String contact;
    @NotBlank
    private String email;
    private String gender;
}
