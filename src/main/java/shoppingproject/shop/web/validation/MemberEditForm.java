package shoppingproject.shop.web.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberEditForm {

    @NotNull
    private Long id;
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
