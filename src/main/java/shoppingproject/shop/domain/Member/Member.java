package shoppingproject.shop.domain.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Member {
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String email;
    private String gender;
    private String contact;

}
